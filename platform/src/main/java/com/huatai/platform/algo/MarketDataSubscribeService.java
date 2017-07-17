package com.huatai.platform.algo;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.marketdata.AbstractMarketDataEvent;
import com.huatai.common.marketdata.Level;
import com.huatai.common.marketdata.event.quote.QuoteSubscribeEvent;
import com.huatai.common.marketdata.event.trade.TradeSubscribeEvent;
import com.huatai.common.util.ConcurrentHashSet;

public class MarketDataSubscribeService {
	private static final Logger log = LoggerFactory.getLogger(MarketDataSubscribeService.class);
	
	// key = symbol, value = set of container id
	private final Map<String, Set<String>> quoteSubscriptions = new ConcurrentHashMap<String, Set<String>>();
	private final Map<String, Set<String>> tradeSubscriptions = new ConcurrentHashMap<String, Set<String>>();
	
	public void subscribeMarketDateEvent(Class<? extends AbstractMarketDataEvent> clazz, Set<String> symbols, String containerId) throws Exception {
		if (symbols != null) {
			if (clazz == QuoteSubscribeEvent.class) {
				new QuoteSubscribeEvent(null, symbols, Level.five);
				addSubscriptions(quoteSubscriptions, symbols, containerId);
			} else if (clazz == TradeSubscribeEvent.class) {
				new TradeSubscribeEvent(null, symbols);
				addSubscriptions(tradeSubscriptions, symbols, containerId);
			} else return;
			
			//eventManager.sendLocalOrAsyncRemoteEvent(event, SystemNodeType.Algo);
			
			log.debug("container {} subscribes to {} : symbol={}", containerId, clazz.getName(), symbols);
		}
	}
	
	public void unsubscribeMarketDataEvent(String containerId) {
		removeSubscriptions(quoteSubscriptions, containerId);
		removeSubscriptions(tradeSubscriptions, containerId);
	}
	
	private void addSubscriptions(Map<String, Set<String>> subscriptions, Set<String> symbols, String containerId) {
		synchronized (subscriptions) {
			for (String symbol : symbols) {
				Set<String> containerIds = subscriptions.get(symbol);
				if (containerIds == null) {
					containerIds = new ConcurrentHashSet<String>();
					subscriptions.put(symbol, containerIds);
				}
				containerIds.add(containerId);
			}
		}
	}
	
	private void removeSubscriptions(Map<String, Set<String>> subscriptions, String containerId) {
		for (String symbol : subscriptions.keySet()) {
			Set<String> containerIds = subscriptions.get(symbol);
			containerIds.remove(containerId);
		}
	}
	
	public Set<String> getQuoteSubscriptions(String symbol) {
		return quoteSubscriptions.get(symbol);
	}
	
	public Set<String> getTradeSubscriptions(String symbol) {
		return tradeSubscriptions.get(symbol);
	}
	
}
