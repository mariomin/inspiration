package com.huatai.common.downstream;

public interface IDownStreamReceiver extends IDownStreamService {
	
	/**
	 * 设置执行回报的回调函数。<P>
	 *
	 */
	void setDownstreamListener(IDownStreamListener downstreamListener);
}
