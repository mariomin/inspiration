package com.huatai.platform.upstream;

import com.huatai.platform.upstream.common.IUpStreamSender;

public abstract class UpStreamSender implements IUpStreamSender {
	protected boolean on;
	
	public UpStreamSender(boolean on) {
		this.on = on;
	}
	
	@Override
	public void onState(boolean on) {
		this.on = on;
	}
	
	@Override
	public boolean getState() {
		return on;
	}
}