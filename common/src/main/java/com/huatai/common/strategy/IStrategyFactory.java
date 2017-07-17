package com.huatai.common.strategy;

public interface IStrategyFactory {
	
	void init() throws StrategyException;
	
	void uninit();
	
	IStrategy createStrategy(String name, Object strategyParams) throws StrategyException;
}
