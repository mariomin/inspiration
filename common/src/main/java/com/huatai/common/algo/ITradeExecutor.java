package com.huatai.common.algo;

public interface ITradeExecutor<V> {
	void init();
	
	void execute(V objects) throws Exception;
}
