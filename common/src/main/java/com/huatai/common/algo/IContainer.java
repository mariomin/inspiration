package com.huatai.common.algo;

import com.huatai.common.strategy.StrategyException;

public interface IContainer {
	
	void init() throws StrategyException;
	
	void uninit();
	
	void start();
	
	void stop();
	
	void pause();
	
}
