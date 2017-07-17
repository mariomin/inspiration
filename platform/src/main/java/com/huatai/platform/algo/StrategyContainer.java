package com.huatai.platform.algo;

import java.util.Set;

import com.huatai.common.SubscribeAccount;
import com.huatai.common.algo.IStrategyQueryService;
import com.huatai.common.algo.IStrategyTradingService;
import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.event.algo.StrategyReportReplyEvent;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.strategy.OrderMsg;
import com.huatai.common.strategy.StrategyException;
import com.huatai.common.util.ConcurrentHashSet;

public class StrategyContainer extends Container implements IStrategyTradingService, IStrategyQueryService {
	private SecurityTradingIndex securityTradingIndex;
	private AssetAccountIndex assetAccountIndex;
	private final Set<SubscribeAccount> subscribers = new ConcurrentHashSet<SubscribeAccount>();
	
	@Override
	public void init() throws StrategyException {
		super.init();
		esperEngine = new EsperEngine(esperConfig, id);
		esperEngine.init();
		eventProcessor.start();
		
		Strategy<?, ?> strategy = getStrategy();
		strategy.setEsperEngine(esperEngine);
		strategy.setStrategyTradingService(this);
		strategy.setSubscribeService(this);
		strategy.setStrategyQueryService(this);
		
		strategy.init();
	}
	
	@Override
	public ExchangeOrder getExchangeOrder(String ordId) {
		return orders.get(ordId);
	}
	
	public void subscribeReport(String key, String subscribeID) {
		subscribers.add(new SubscribeAccount(key, securityTradingIndex, subscribeID));
	}
	
	public void unsubscribeReport(String subscribeID) {
		subscribers.remove(new SubscribeAccount(key, securityTradingIndex, subscribeID));
	}
	
	public SecurityTradingIndex getSecurityTradingIndex() {
		return securityTradingIndex;
	}
	
	public void setSecurityTradingIndex(SecurityTradingIndex securityTradingIndex) {
		this.securityTradingIndex = securityTradingIndex;
	}
	
	public AssetAccountIndex getAssetAccountIndex() {
		return assetAccountIndex;
	}
	
	public void setAssetAccountIndex(AssetAccountIndex assetAccountIndex) {
		this.assetAccountIndex = assetAccountIndex;
	}
	
	public void setStrategy(Strategy<?, ?> strategy) {
		this.strategy = strategy;
	}
	
	public Strategy<?, ?> getStrategy() {
		return strategy;
	}
	
	@Override
	public OrderMsg placeNewOrder(ExchangeOrder exchangeOrder, Long timeout) {
		return null;
	}
	
	@Override
	public OrderMsg cancelOrder(String tradingAccount, String securityAccount, String origClOrdId, Long timeout) {
		return null;
	}
	
	@Override
	public void sendStrategyReport(String report) {
		for (SubscribeAccount subscriber : subscribers) {
			Strategy<?, ?> strategy = getStrategy();
			eventManager.sendEvent(new StrategyReportReplyEvent(subscriber.getKey(), strategy.getId(), report, strategy.getClStrategyId(),
					subscriber.getSecurityTradingIndex(), subscriber.getSubscribeID()));
		}
	}
	
}
