package com.huatai.platform.client;

import java.io.UnsupportedEncodingException;

import com.huatai.common.fix.FixAdaptor;

import quickfix.SessionID;

public class FixClientAdaptor extends FixAdaptor<IFixClientService> {
	
	public FixClientAdaptor(String fixSettings) throws UnsupportedEncodingException {
		super(fixSettings);
	}
	
	@Override
	protected IFixClientService createFixSession(SessionID session) {
		return new FixClientService(session);
	}
}
