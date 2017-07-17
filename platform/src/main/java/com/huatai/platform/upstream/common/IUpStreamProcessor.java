package com.huatai.platform.upstream.common;

import com.huatai.common.SystemInfo;
import com.huatai.common.fix.message.FixMessage;

public interface IUpStreamProcessor {
	void setSender(IUpStreamSender sender);
	
	void setListener(IUpStreamListener listener);
	
	void setSystemInfo(SystemInfo systemInfo);
	
	boolean processFixMessage(FixMessage fixMessage) throws Exception;
}
