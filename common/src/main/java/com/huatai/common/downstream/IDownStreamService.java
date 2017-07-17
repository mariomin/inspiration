package com.huatai.common.downstream;

public interface IDownStreamService {
	/**
	 * 获取报盘唯一标识
	 * @return
	 */
	String getId();
	
	/**
	 * 启动报盘服务。
	 */
	void start() throws Exception;
	
	/**
	 * 停止报盘服务。<P>
	 *
	 */
	void stop() throws Exception;
	
	boolean getState();
}
