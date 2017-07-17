package com.huatai.platform.algo;

import com.huatai.common.algo.IStrategyQueryService;
import com.huatai.common.algo.IStrategyTradingService;
import com.huatai.common.algo.ISubscribeService;
import com.huatai.common.backtest.IMarketDataCalculateService;
import com.huatai.common.backtest.ITradingService;
import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.fund.TradingFund;
import com.huatai.common.portfolio.TradingPosition;
import com.huatai.common.strategy.IStrategy;
import com.huatai.common.type.StrategyState;

public abstract class Strategy<K, V> implements IStrategy {
	protected StrategyState state = StrategyState.Undefined;
	protected String id;
	protected String name;
	protected K params;
	protected ISubscribeService subscribeService;
	protected EsperEngine esperEngine;
	
	protected IMarketDataCalculateService mdCalService;
	protected ITradingService tradingService;
	protected IStrategyQueryService strategyQueryService;
	protected IStrategyTradingService strategyTradingService;
	protected SignalAnalyzer<V> signalAnalyzer;
	protected StrategyTradeExecutor<V> tradeExecutor;
	protected String clStrategyId;
	
	@Override
	public void onNewOrderRejected(String clOrdId, String symbol, String text) {}
	
	@Override
	public void onCancelOrderRejected(String clOrdId, String symbol, String text) {}
	
	@Override
	public void onOrderUpdated(ExchangeOrder order) {}
	
	@Override
	public void onFundUpdated(TradingFund fund) {}
	
	@Override
	public void onPositionUpdated(TradingPosition position) {}
	
	@Override
	public void init() {
		setState(StrategyState.Initializing);
		
		createSignalAnalyzer();
		createTradeExecutor();
		
		signalAnalyzer.setEsperEngine(esperEngine);
		signalAnalyzer.init();
		
		tradeExecutor.setTradeService(strategyTradingService);
		tradeExecutor.init();
		
		subscribe();
	}
	
	@Override
	public void uninit() {
		setState(StrategyState.Uninitializing);
	}
	
	public void setMdCalService(IMarketDataCalculateService mdCalService) {
		this.mdCalService = mdCalService;
	}
	
	public void setStrategyQueryService(IStrategyQueryService strategyQueryService) {
		this.strategyQueryService = strategyQueryService;
	}
	
	public void setStrategyTradingService(IStrategyTradingService strategyTradingService) {
		this.strategyTradingService = strategyTradingService;
	}
	
	public void setClStrategyId(String clStrategyId) {
		this.clStrategyId = clStrategyId;
	}
	
	public String getClStrategyId() {
		return clStrategyId;
	}
	
	@Override
	public StrategyState getState() {
		return state;
	}
	
	@Override
	public void setState(StrategyState state) {
		this.state = state;
	}
	
	public K getParams() {
		return params;
	}
	
	public void setParams(K arams) {
		params = arams;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public EsperEngine getEsperEngine() {
		return esperEngine;
	}
	
	public void setEsperEngine(EsperEngine esperEngine) {
		this.esperEngine = esperEngine;
	}
	
	public void setSubscribeService(ISubscribeService subscribeService) {
		this.subscribeService = subscribeService;
	}
	
	public void setTradingService(ITradingService tradingService) {
		this.tradingService = tradingService;
	}
	
}
