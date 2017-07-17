package com.huatai.platform.client;

import quickfix.Message;
import quickfix.SessionID;

public interface IFixClientListener {
	
	void processsFixMessage(Message message, SessionID sessionId) throws Exception;
	
}
