package com.huatai.platform.upstream.common;

public interface IUpStreamConnection {
	String getId();
	
	boolean getState();
	
	IUpStreamSender setListener(IUpStreamListener listener);
}
