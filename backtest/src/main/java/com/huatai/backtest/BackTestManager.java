package com.huatai.backtest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.google.gson.Gson;
import com.huatai.backtest.core.BackTestContainer;
import com.huatai.common.backtest.StrategyException;
import com.huatai.common.backtest.report.BackTestRequest;
import com.huatai.common.util.GsonUtil;
import com.huatai.exchange.Exchange;
import com.huatai.platform.algo.Container;
import com.huatai.platform.algo.FakeMarketDataSubscribeService;
import com.huatai.platform.algo.Strategy;
import com.huatai.platform.algo.StrategyFactory;

public class BackTestManager {
	private static final Logger log = LoggerFactory.getLogger(BackTestManager.class);
	private static final Gson gson = GsonUtil.getGsonInstance();
	private final Map<String, Container> containers = new HashMap<>();
	
	@Autowired
	private StrategyFactory strategyFactory;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public void init() throws Exception {
		log.info("initialising");
		
		strategyFactory.init();
	}
	
	public void uninit() throws Exception {
		log.info("uninitialising");
		
		strategyFactory.uninit();
	}
	
	public BackTestContainer createBackTestContainer(BackTestRequest request) throws StrategyException {
		String strategyName = request.getStrategy();
		Strategy<?, ?> strategy = null;
		
		try {
			Object strategyParas = gson.fromJson(request.getStrategyParam(), strategyFactory.getParam(strategyName));
			strategy = strategyFactory.createStrategy(strategyName, strategyParas);
		} catch (Exception e) {
			log.error("failed to create strategy", e);
		}
		
		if (strategy != null) {
			BackTestContainer container = applicationContext.getBean("backTestContainer", BackTestContainer.class);
			Exchange exchange = applicationContext.getBean("exchange", Exchange.class);
			container.setExchange(exchange);
			container.setBackTestRequest(request);
			container.setStrategy(strategy);
			container.setMarketDataSubscribeService(new FakeMarketDataSubscribeService());
			
			containers.put(container.getId(), container);
			try {
				container.init();
			} catch (com.huatai.common.strategy.StrategyException e) {
				log.error(e.getMessage());
			}
			return container;
		}
		
		throw new StrategyException("backtest strategy:{} failed" + strategyName);
	}
	
	public Map<String, Container> getContainers() {
		return containers;
	}
	
}
