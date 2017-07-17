package com.huatai.platform.client;

import quickfix.Application;
import quickfix.Message;

public interface IFixClientService extends Application {
	
	String getId();
	
	boolean getState();
	
	void sendMessage(Message message) throws Exception;
	
	void setFixClientListener(IFixClientListener listener);
}
