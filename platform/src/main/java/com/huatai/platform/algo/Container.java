package com.huatai.platform.algo;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.thavam.util.concurrent.BlockingHashMap;
import org.thavam.util.concurrent.BlockingMap;

import com.huatai.common.algo.IContainer;
import com.huatai.common.algo.ISubscribeService;
import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.disruptor.DisruptorExecuteEventThread;
import com.huatai.common.event.IAsyncEventListener;
import com.huatai.common.event.IAsyncEventManager;
import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.event.order.CancelOrderReplyEvent;
import com.huatai.common.event.order.ExecutionReportEvent;
import com.huatai.common.event.order.NewOrderReplyEvent;
import com.huatai.common.marketdata.MarketData;
import com.huatai.common.marketdata.Quote;
import com.huatai.common.marketdata.Trade;
import com.huatai.common.marketdata.event.quote.QuoteReplyEvent;
import com.huatai.common.marketdata.event.quote.QuoteSubscribeEvent;
import com.huatai.common.marketdata.event.trade.TradeReplyEvent;
import com.huatai.common.marketdata.event.trade.TradeSubscribeEvent;
import com.huatai.common.strategy.OrderMsg;
import com.huatai.common.strategy.StrategyException;
import com.huatai.common.type.StrategyState;
import com.huatai.common.util.IdGenerator;
import com.huatai.platform.schedule.ScheduleManager;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

public abstract class Container implements IContainer, IAsyncEventListener, ISubscribeService {
	protected static final Logger log = LoggerFactory.getLogger(Container.class);
	protected final String id = IdGenerator.getInstance().getNextID();
	protected String key;
	protected MarketDataSubscribeService marketDataSubscribeService;
	
	// key := clOrdId, value:=exchangeOrder
	protected final Map<String, ExchangeOrder> orders = new ConcurrentHashMap<String, ExchangeOrder>();
	
	// key :=clOrdId
	private final BlockingMap<String, OrderMsg> blockIds = new BlockingHashMap<String, OrderMsg>();
	
	private long defaultTimeout = 3000l;
	private int bufferSize = 1024 * 1024;
	
	@Autowired
	protected IAsyncEventManager eventManager;
	
	@Autowired
	protected ScheduleManager scheduleManager;
	
	protected Strategy<?, ?> strategy;
	
	protected String esperConfig;
	
	protected EsperEngine esperEngine;
	
	protected final DisruptorExecuteEventThread eventProcessor = new DisruptorExecuteEventThread(ProducerType.MULTI, new BlockingWaitStrategy(),
			bufferSize, 1, 1) {
		@Override
		public void onNormalEvent(AsyncEvent event) {
			if (event instanceof QuoteReplyEvent) {
				Quote quote = ((QuoteReplyEvent) event).getQuote();
				strategy.onMarketData(quote);
			} else if (event instanceof TradeReplyEvent) {
				Trade trade = ((TradeReplyEvent) event).getTrade();
				strategy.onMarketData(trade);
			} else if (event instanceof ExecutionReportEvent) {
				ExecutionReportEvent executionReportEvent = (ExecutionReportEvent) event;
				orders.put(executionReportEvent.getOrder().getClOrdId(), executionReportEvent.getOrder());
				strategy.onOrderUpdated(executionReportEvent.getOrder());
				strategy.onFundUpdated(executionReportEvent.getTradingFund());
				strategy.onPositionUpdated(executionReportEvent.getTradingPosition());
			} else if (event instanceof NewOrderReplyEvent) {
				NewOrderReplyEvent newOrderReplyEvent = (NewOrderReplyEvent) event;
				ExchangeOrder order = (ExchangeOrder) newOrderReplyEvent.getOrder();
				if (newOrderReplyEvent.isOk()) {
					orders.put(order.getClOrdId(), order);
					strategy.onOrderUpdated(order);
				} else {
					strategy.onNewOrderRejected(order.getClOrdId(), order.getSymbol(), newOrderReplyEvent.getText());
				}
			} else if (event instanceof CancelOrderReplyEvent) {
				CancelOrderReplyEvent cancelOrderReplyEvent = (CancelOrderReplyEvent) event;
				ExchangeOrder order = (ExchangeOrder) cancelOrderReplyEvent.getOrder();
				if (cancelOrderReplyEvent.isOk()) {
					orders.put(order.getClOrdId(), order);
					strategy.onOrderUpdated(order);
				} else {
					strategy.onCancelOrderRejected(order.getClOrdId(), order.getSymbol(), cancelOrderReplyEvent.getText());
				}
			} else {
				strategy.onTimeEvent(event);
			}
		}
	};
	
	@Override
	public void init() throws StrategyException {}
	
	@Override
	public void uninit() {
		strategy.uninit();
		eventProcessor.exit();
		esperEngine.uninit();
	}
	
	@Override
	public void start() {
		synchronized (strategy) {
			StrategyState state = strategy.getState();
			if ((state == StrategyState.Stopped) || (state == StrategyState.Running)) return;
			strategy.setState(StrategyState.Running);
			strategy.onStateUpdated();
		}
		
		log.info("Start strategy: StrategyName = {}, StrategyID = {}", strategy.getName(), id);
	}
	
	@Override
	public void stop() {
		synchronized (strategy) {
			StrategyState state = strategy.getState();
			
			if (state == StrategyState.Stopped) return;
			strategy.setState(StrategyState.Stopped);
			strategy.onStateUpdated();
		}
		uninit();
		
		log.info("Stop strategy: StrategyName = {}, StrategyID = {}", strategy.getName(), id);
	}
	
	@Override
	public void pause() {
		synchronized (strategy) {
			StrategyState state = strategy.getState();
			if ((state == StrategyState.Stopped) || (state == StrategyState.Paused)) return;
			strategy.setState(StrategyState.Paused);
			strategy.onStateUpdated();
		}
		
		log.info("Pause strategy: StrategyName = {}, StrategyID = {}", strategy.getName(), id);
	}
	
	public StrategyState getStrategyState() {
		return strategy.getState();
	}
	
	@Override
	public void subscribeMarketDataEvent(Class<? extends MarketData> clazz, Set<String> symbols) throws Exception {
		if (clazz == Quote.class) {
			marketDataSubscribeService.subscribeMarketDateEvent(QuoteSubscribeEvent.class, symbols, id);
		} else if (clazz == Trade.class) {
			marketDataSubscribeService.subscribeMarketDateEvent(TradeSubscribeEvent.class, symbols, id);
		}
	}
	
	@Override
	public void unsubscribeMarketDataEvent() {
		marketDataSubscribeService.unsubscribeMarketDataEvent(id);
	}
	
	@Override
	public void subscribeTimerEvent(long time, AsyncEvent event) {
		scheduleManager.scheduleTimerEvent(time, this, event);
	}
	
	@Override
	public void subscribeTimerEvent(Date time, AsyncEvent event) {
		scheduleManager.scheduleTimerEvent(time, this, event);
	}
	
	@Override
	public void subscribeRepeatTimerEvent(long time, AsyncEvent event) {
		scheduleManager.scheduleRepeatTimerEvent(time, this, event);
	}
	
	@Override
	public void unsubscribeTimerEvent(AsyncEvent event) {
		scheduleManager.cancelTimerEvent(event);
	}
	
	public void sendLocalOrRemoteEvent(AsyncEvent event) throws Exception {
		event.setKey(key);
		eventManager.sendEvent(event);
	}
	
	@Override
	public void onEvent(AsyncEvent event) {
		eventProcessor.addEvent(event);
	}
	
	public void offerClOrdId(String clOrdId, boolean successed, String text) {
		try {
			blockIds.offer(clOrdId, new OrderMsg(successed, clOrdId, text), defaultTimeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			log.error(" failed to offfer new OrderMsg to blcokIds");
		}
	}
	
	public String getId() {
		return id;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setDefaultTimeout(long defaultTimeout) {
		this.defaultTimeout = defaultTimeout;
	}
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	public void setEsperConfig(String esperConfig) {
		this.esperConfig = esperConfig;
	}
	
	public void setMarketDataSubscribeService(MarketDataSubscribeService marketDataSubscribeService) {
		this.marketDataSubscribeService = marketDataSubscribeService;
	}
	
	public MarketDataSubscribeService getMarketDataSubscribeService() {
		return marketDataSubscribeService;
	}
	
}
