package com.huatai.platform.downstream.adaptor;

import com.huatai.common.downstream.IDownStreamReceiver;
import com.huatai.common.downstream.IDownStreamSender;

public interface IExchangeService extends IDownStreamReceiver, IDownStreamSender {
	
	@Override
	void stop();
	
}
