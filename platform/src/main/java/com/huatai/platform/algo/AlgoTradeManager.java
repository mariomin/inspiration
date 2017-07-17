package com.huatai.platform.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.thavam.util.concurrent.BlockingHashMap;
import org.thavam.util.concurrent.BlockingMap;

import com.huatai.common.SystemNodeType;
import com.huatai.common.algo.blockservice.AsyncBlockService;
import com.huatai.common.algo.blockservice.BlockSignal;
import com.huatai.common.algo.blockservice.BlockTask;
import com.huatai.common.business.command.StrategyAlterCommand;
import com.huatai.common.event.IAsyncEventManager;
import com.huatai.common.event.account.RequestFundEvent;
import com.huatai.common.event.account.RequestPositionEvent;
import com.huatai.common.event.account.ResponseFundEvent;
import com.huatai.common.event.account.ResponsePositionEvent;
import com.huatai.common.event.algo.CreateStrategyEvent;
import com.huatai.common.event.algo.RegisterredStrategyListRequestEvent;
import com.huatai.common.event.algo.RegisterredStrategyReplyEvent;
import com.huatai.common.event.algo.ResumeStrategyEvent;
import com.huatai.common.event.algo.StartStrategyEvent;
import com.huatai.common.event.algo.StopStrategyEvent;
import com.huatai.common.event.algo.StrategyAlterReplyEvent;
import com.huatai.common.event.algo.StrategyQueryEvent;
import com.huatai.common.event.algo.StrategyQueryReplyEvent;
import com.huatai.common.event.algo.StrategySubscribeEvent;
import com.huatai.common.event.algo.StrategySubscribeReplyEvent;
import com.huatai.common.event.algo.SuspendStrategyEvent;
import com.huatai.common.event.marketsession.MarketSessionReplyEvent;
import com.huatai.common.event.platform.PluginReadyEvent;
import com.huatai.common.fund.FundCache;
import com.huatai.common.fund.TradingFund;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.index.TradingFundIndex;
import com.huatai.common.marketdata.event.quote.QuoteReplyEvent;
import com.huatai.common.marketdata.event.trade.TradeReplyEvent;
import com.huatai.common.marketsession.MarketSessionCache;
import com.huatai.common.marketsession.MarketSessionTime;
import com.huatai.common.platform.ISystemNode;
import com.huatai.common.platform.PlatformSettings;
import com.huatai.common.portfolio.PortfolioCache;
import com.huatai.common.refdata.RefDataCache;
import com.huatai.common.refdata.event.ResponseRefDataEvent;
import com.huatai.common.strategy.StrategyException;
import com.huatai.common.strategy.StrategyQueryRecord;
import com.huatai.common.strategy.StrategySubscriptionType;
import com.huatai.common.type.Currency;
import com.huatai.common.type.ExchangeType;
import com.huatai.common.type.StrategyState;
import com.huatai.common.util.IdGenerator;
import com.huatai.platform.event.AsyncEventProcessor;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

public class AlgoTradeManager extends PlatformSettings implements ISystemNode {
	private static final Logger log = LoggerFactory.getLogger(AlgoTradeManager.class);
	private static final IdGenerator idGenerator = IdGenerator.getInstance();
	private static final int defaultTimeout = 10000;
	
	private final RefDataCache refDataCache = new RefDataCache();
	private final FundCache fundCache = new FundCache();
	private final PortfolioCache portfolioCache = new PortfolioCache();
	private final MarketSessionCache marketSesTimeCache = new MarketSessionCache();
	private final BlockingMap<String, String> blockMap = new BlockingHashMap<String, String>();
	private final AsyncBlockService blockService = new AsyncBlockService();
	
	private MarketDataSubscribeService marketDataSubscribeService;
	
	// key = strategyId, value = StrategyContainer
	private final Map<String, StrategyContainer> strategyToContainers = new ConcurrentHashMap<String, StrategyContainer>();
	
	// key = containerId, value = Container
	private final Map<String, Container> containers = new ConcurrentHashMap<String, Container>();
	
	private boolean skipRefData;
	private boolean skipMarketSesTime;
	
	@Autowired
	private IAsyncEventManager eventManager;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private StrategyFactory tradeModelFactory;
	
	private final AsyncEventProcessor eventProcessor = new AsyncEventProcessor() {
		
		@Override
		public void subscribeToEvents() {
			subscribeToEvent(CreateStrategyEvent.class, null);
			subscribeToEvent(StartStrategyEvent.class, null);
			subscribeToEvent(SuspendStrategyEvent.class, null);
			subscribeToEvent(ResumeStrategyEvent.class, null);
			subscribeToEvent(StopStrategyEvent.class, null);
			subscribeToEvent(QuoteReplyEvent.class, null);
			subscribeToEvent(TradeReplyEvent.class, null);
			subscribeToEvent(StrategySubscribeEvent.class, null);
			subscribeToEvent(StrategyQueryEvent.class, null);
			subscribeToEvent(ResponseRefDataEvent.class, null);
			subscribeToEvent(ResponsePositionEvent.class, null);
			subscribeToEvent(RegisterredStrategyListRequestEvent.class, null);
			subscribeToEvent(ResponseFundEvent.class, null);
		}
		
		@Override
		public IAsyncEventManager getEventManager() {
			return eventManager;
		}
		
		@Override
		public ProducerType getDisruptorProducerType() {
			return ProducerType.MULTI;
		}
		
		@Override
		public int getBufferSize() {
			return bufferSize;
		}
		
		@Override
		public int getHighPriorityEventProcessorThreads() {
			return highPriorityProcessorThreads;
		}
		
		@Override
		public int getNormalPriorityProcessorThreads() {
			return normalPriorityProcessorThreads;
		}
		
		@Override
		public WaitStrategy getWaitStrategy() {
			return waitStrategy;
		}
	};
	
	public class AlgoBlockTask extends BlockTask {
		private final CreateStrategyEvent event;
		
		public AlgoBlockTask(CreateStrategyEvent event, String id, long timeout) {
			super(id, timeout);
			this.event = event;
		}
		
		@Override
		public void invoke(boolean timeout) {
			createStrategy(event, timeout);
		}
		
	}
	
	public void processResponseRefDataEvent(ResponseRefDataEvent event) {
		log.debug("received ResponseRefDataEvent: {}", event);
		refDataCache.updateRefDatas(event.getRefDatas());
		
		try {
			blockMap.offer(event.getRequestID(), event.getRequestID());
		} catch (InterruptedException e) {
			log.error("failed to offer object to blockMap: {}", e);
		}
	}
	
	public void processResponsePositionEvent(ResponsePositionEvent event) {
		portfolioCache.updateAlgoPositions(event.getTradingPositions());
		blockService.receiveSignal(event.getRequestID(), BlockSignal.Position);
	}
	
	public void processResponseFundEvent(ResponseFundEvent event) {
		for (TradingFund tradingFund : event.getTradingFunds()) {
			fundCache.updateTradingFund(tradingFund);
		}
		if (event.getRequestID() != null) {
			blockService.receiveSignal(event.getRequestID(), BlockSignal.Account);
		}
	}
	
	public void processMarketSessionReplyEvent(MarketSessionReplyEvent event) {
		Map<ExchangeType, MarketSessionTime> marketSesTimes = event.getMarketSessionTimes();
		for (ExchangeType exchangeType : marketSesTimes.keySet()) {
			marketSesTimeCache.updateMarketSesTime(exchangeType, marketSesTimes.get(exchangeType));
		}
		
		try {
			blockMap.offer(event.getRequestId(), event.getRequestId());
		} catch (InterruptedException e) {
			log.error("failed to offer object to blockMap: {}", e);
		}
	}
	
	public void processStrategySubscribeEvent(StrategySubscribeEvent event) {
		log.debug("receive StrategySubscribeEvent event={}", event);
		
		StrategySubscribeReplyEvent reply = new StrategySubscribeReplyEvent(event.getKey(), event.getRequestID());
		
		if ((null != event.getSecurityTradingIndex()) && !StringUtils.isEmpty(event.getSubscribeID()) && (null != event.getType())) {
			reply.setSuccess(true);
		} else {
			log.error("invalid request, event={}", event);
			reply.setSuccess(false);
		}
		
		String strategyId = event.getStrategyID();
		for (StrategyContainer container : strategyToContainers.values()) {
			AssetAccountIndex index = container.getAssetAccountIndex();
			log.debug("StrategyContainer info : tradingAccountKey={}, strategyId={}", index, container.getStrategy().getId());
			
			if ((index != null) && index.equals(event.getSecurityTradingIndex().extractAssetAccountIndex())
					&& ((strategyId == null) || (container.getStrategy().getId().equals(strategyId)))) {
				if (event.getType() == StrategySubscriptionType.Subscribe) {
					container.subscribeReport(event.getKey(), event.getSubscribeID());
				} else if (event.getType() == StrategySubscriptionType.Unsubscribe) {
					container.unsubscribeReport(event.getSubscribeID());
				}
			}
		}
		
		eventManager.sendEvent(reply);
	}
	
	public void processStrategyQueryEvent(StrategyQueryEvent event) {
		log.debug("receive StrategyQueryEvent: {}", event);
		
		AssetAccountIndex index = event.getSecurityTradingIndex().extractAssetAccountIndex();
		List<StrategyQueryRecord> records = new ArrayList<StrategyQueryRecord>();
		for (StrategyContainer container : strategyToContainers.values()) {
			if (container.getAssetAccountIndex().equals(index)) {
				Strategy<?, ?> strategy = container.getStrategy();
				records.add(new StrategyQueryRecord(strategy.getClStrategyId(), strategy.getId(), strategy.getName(), strategy.getState().value()));
			}
		}
		
		eventManager.sendEvent(new StrategyQueryReplyEvent(event.getKey(), event.getRequestID(), records));
	}
	
	private void createStrategy(CreateStrategyEvent event, boolean timeout) {
		StrategyAlterCommand command = event.getStrategyAlterCommand();
		String strategyId = command.getId();
		String strategyName = command.getStrategy();
		
		boolean flag = true;
		StrategyContainer strategyContainer = null;
		StrategyState strategyState = StrategyState.Undefined;
		
		try {
			if (timeout) throw new StrategyException("failed to load data from OMS");
			
			Strategy<?, ?> strategy = tradeModelFactory.createStrategy(strategyName, command.getStrategyParas());
			strategy.setId(strategyId);
			strategy.setClStrategyId(command.getClOrdId());
			
			strategyContainer = (StrategyContainer) applicationContext.getBean("strategyContainer");
			strategyContainer.setKey(command.getSource());
			strategyContainer.setSecurityTradingIndex(command.extractSecurityTradingIndex());
			strategyContainer.setAssetAccountIndex(command.extractAssetAccountIndex());
			strategyContainer.setStrategy(strategy);
			strategyContainer.setMarketDataSubscribeService(marketDataSubscribeService);
			strategyContainer.init();
			
			strategyToContainers.put(strategyId, strategyContainer);
			containers.put(strategyContainer.getId(), strategyContainer);
			
			strategyState = strategyContainer.getStrategyState();
		} catch (StrategyException e) {
			log.error("failed to create new strategy: {}", strategyName, e);
			flag = false;
			if (strategyContainer != null) {
				strategyContainer.uninit();
			}
		}
		
		eventManager.sendEvent(new StrategyAlterReplyEvent(command.getSource(), event.getRequestID(), strategyId, strategyState, flag));
	}
	
	public void processCreateStrategyEvent(CreateStrategyEvent event) {
		log.debug("received CreateStrategyEvent, event={}", event);
		
		String requestId = idGenerator.getNextID();
		BlockTask blockTask = new AlgoBlockTask(event, requestId, defaultTimeout);
		StrategyAlterCommand command = event.getStrategyAlterCommand();
		
		try {
			SecurityTradingIndex securityTradingIndex = command.extractSecurityTradingIndex();
			if (event.isLoadAccount()
					&& !fundCache.contains(new TradingFundIndex(securityTradingIndex, securityTradingIndex.getTradingAccount(), Currency.CNY))) {
				blockTask.addSignal(BlockSignal.Account);
				eventManager.sendEvent(new RequestFundEvent(null, requestId, securityTradingIndex));
			}
			
			if (event.isLoadPosition() && !portfolioCache.contains(securityTradingIndex)) {
				blockTask.addSignal(BlockSignal.Position);
				eventManager.sendEvent(new RequestPositionEvent(null, requestId, 0, securityTradingIndex));
			}
			
		} catch (Exception e) {
			log.error("failed to send RequestAccountDataEvent:{}", e);
		}
		
		if (blockTask.getSignals().isEmpty()) {
			createStrategy(event, false);
		} else {
			blockService.addBlockTask(blockTask);
		}
	}
	
	public void processStartStrategyEvent(StartStrategyEvent event) {
		log.debug("received StartStrategyEvent, event={}", event);
		
		StrategyAlterCommand command = event.getStrategyAlterCommand();
		String strategyId = command.getStrategyId();
		
		StrategyAlterReplyEvent reply = new StrategyAlterReplyEvent(event.getKey(), command.getRequestID(), strategyId, null, true);
		
		StrategyContainer strategyContainer = strategyToContainers.get(strategyId);
		if (strategyContainer != null) {
			strategyContainer.start();
			reply.setStrategyState(strategyContainer.getStrategyState());
			reply.setClOrdId(strategyContainer.getStrategy().getClStrategyId());
		} else {
			reply.setSuccess(false);
			reply.setText("strategyId:" + strategyId + " not exsit");
		}
		
		eventManager.sendEvent(reply);
	}
	
	public void processSuspendStrategyEvent(SuspendStrategyEvent event) {
		log.debug("received SuspendStrategyEvent, event={}", event);
		
		StrategyAlterCommand command = event.getStrategyAlterCommand();
		String strategyId = command.getStrategyId();
		
		StrategyAlterReplyEvent reply = new StrategyAlterReplyEvent(event.getKey(), command.getRequestID(), strategyId, null, true);
		
		StrategyContainer strategyContainer = strategyToContainers.get(strategyId);
		if (strategyContainer != null) {
			strategyContainer.pause();
			reply.setStrategyState(strategyContainer.getStrategyState());
			reply.setClOrdId(strategyContainer.getStrategy().getClStrategyId());
		} else {
			reply.setSuccess(false);
			reply.setText("strategyId:" + strategyId + " not exsit");
		}
		
		eventManager.sendEvent(reply);
	}
	
	public void processResumeStrategyEvent(ResumeStrategyEvent event) {
		log.debug("received ResumeStrategyEvent, event={}", event);
		
		StrategyAlterCommand command = event.getStrategyAlterCommand();
		String strategyId = command.getStrategyId();
		
		StrategyAlterReplyEvent reply = new StrategyAlterReplyEvent(event.getKey(), command.getRequestID(), strategyId, null, true);
		
		StrategyContainer strategyContainer = strategyToContainers.get(strategyId);
		if (strategyContainer != null) {
			strategyContainer.start();
			reply.setStrategyState(strategyContainer.getStrategyState());
			reply.setClOrdId(strategyContainer.getStrategy().getClStrategyId());
		} else {
			reply.setSuccess(false);
			reply.setText("strategyId:" + strategyId + " not exsit");
		}
		
		eventManager.sendEvent(reply);
	}
	
	public void processStopStrategyEvent(StopStrategyEvent event) {
		log.debug("received StopStrategyEvent, event={}", event);
		
		StrategyAlterCommand command = event.getStrategyAlterCommand();
		String strategyId = command.getStrategyId();
		
		StrategyAlterReplyEvent reply = new StrategyAlterReplyEvent(event.getKey(), command.getRequestID(), strategyId, null, true);
		
		StrategyContainer strategyContainer = strategyToContainers.get(strategyId);
		if (strategyContainer != null) {
			strategyContainer.stop();
			strategyToContainers.remove(strategyContainer.getStrategy().getId());
			containers.remove(strategyContainer.getId());
			reply.setStrategyState(strategyContainer.getStrategyState());
			reply.setClOrdId(strategyContainer.getStrategy().getClStrategyId());
		} else {
			reply.setSuccess(false);
			reply.setText("strategyId:" + strategyId + " not exsit");
		}
		
		eventManager.sendEvent(reply);
	}
	
	public void processRegisterredStrategyListRequestEvent(RegisterredStrategyListRequestEvent event) {
		log.debug("received RegisterredStrategyListRequestEvent: {}", event);
		
		eventManager.sendEvent(new RegisterredStrategyReplyEvent(event.getKey(), event.getRequestId(), tradeModelFactory.getStrategies()));
	}
	
	public void processQuoteReplyEvent(QuoteReplyEvent event) {
		log.debug("received quoteReplyEvent: event={}", event);
		Set<String> containerIds = marketDataSubscribeService.getQuoteSubscriptions(event.getQuote().getSymbol());
		if (containerIds != null) {
			for (String containerId : containerIds) {
				Container container = containers.get(containerId);
				if (container != null) {
					container.onEvent(event);
				}
			}
		}
	}
	
	public void processTradeReplyEvent(TradeReplyEvent event) {
		log.debug("received tradeReplyEvent: event={}", event);
		Set<String> containerIds = marketDataSubscribeService.getTradeSubscriptions(event.getTrade().getSymbol());
		if (containerIds != null) {
			for (String containerId : containerIds) {
				Container container = containers.get(containerId);
				if (container != null) {
					container.onEvent(event);
				}
			}
		}
	}
	
	private boolean loadRefData() {
		return true;
	}
	
	@Override
	public void init() throws Exception {
		log.info("initialising");
		super.init();
		
		marketDataSubscribeService = new MarketDataSubscribeService();
		tradeModelFactory.init();
		
		eventProcessor.setHandler(this);
		eventProcessor.init();
		if (eventProcessor.getThread() != null) {
			eventProcessor.getThread().setName(name);
		}
		
		if (!skipRefData && !loadRefData() && !skipMarketSesTime) {
			log.error("failed to load RefData, system exits");
			System.exit(-1);
		} else {
			eventManager.sendEvent(new PluginReadyEvent(null, name, true));
		}
		
		blockService.init();
		Thread.sleep(3000);
	}
	
	@Override
	public void uninit() {
		log.info("uninitialising");
		
		eventProcessor.uninit();
		tradeModelFactory.uninit();
		
		super.uninit();
	}
	
	public StrategyContainer getStrategyContainer(String strategyId) {
		return strategyToContainers.get(strategyId);
	}
	
	public void setSkipRefData(boolean skipRefData) {
		this.skipRefData = skipRefData;
	}
	
	public void setSkipMarketSesTime(boolean skipMarketSesTime) {
		this.skipMarketSesTime = skipMarketSesTime;
	}
	
	@Override
	public SystemNodeType getNodeType() {
		return SystemNodeType.Algo;
	}
	
}
