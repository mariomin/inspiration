package com.huatai.platform.upstream;

import com.huatai.common.SystemInfo;
import com.huatai.platform.upstream.common.IUpStreamListener;
import com.huatai.platform.upstream.common.IUpStreamProcessor;
import com.huatai.platform.upstream.common.IUpStreamSender;

public abstract class UpStreamProcessor implements IUpStreamProcessor {
	protected IUpStreamListener listener;
	protected IUpStreamSender sender;
	protected SystemInfo systemInfo;
	
	@Override
	public void setListener(IUpStreamListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void setSender(IUpStreamSender sender) {
		this.sender = sender;
	}
	
	@Override
	public void setSystemInfo(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
	}
	
}
