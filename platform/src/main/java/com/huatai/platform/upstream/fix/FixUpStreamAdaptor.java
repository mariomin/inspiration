package com.huatai.platform.upstream.fix;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.huatai.common.SystemInfo;
import com.huatai.common.fix.FixAdaptor;
import com.huatai.common.fix.FixException;
import com.huatai.platform.upstream.common.IFixUpStreamConnection;
import com.huatai.platform.upstream.common.IUpStreamAdaptor;
import com.huatai.platform.upstream.common.IUpStreamCallbackHandler;

import quickfix.SessionID;

public class FixUpStreamAdaptor extends FixAdaptor<IFixUpStreamConnection> implements IUpStreamAdaptor<IFixUpStreamConnection> {
	private final List<String> upStreamProcessorClasses;
	protected IUpStreamCallbackHandler upStreamCallbackHandler;
	@Autowired
	private SystemInfo systemInfo;
	
	public FixUpStreamAdaptor(List<String> upStreamProcessorClasses, String fixSettings) throws FixException, UnsupportedEncodingException {
		super(fixSettings);
		this.upStreamProcessorClasses = upStreamProcessorClasses;
	}
	
	@Override
	protected IFixUpStreamConnection createFixSession(SessionID session) {
		return new FixUpStreamConnection(session, upStreamProcessorClasses, systemInfo);
	}
	
	@Override
	public void init(IUpStreamCallbackHandler upStreamCallbackHandler) throws FixException {
		this.upStreamCallbackHandler = upStreamCallbackHandler;
		super.init();
	}
	
	@Override
	public void uninit() {
		super.uninit();
	}
	
	@Override
	public void onCreate(SessionID sessionID) {
		super.onCreate(sessionID);
		IFixUpStreamConnection connection = map.get(sessionID);
		upStreamCallbackHandler.setEventListener(connection);
	}
	
	@Override
	public void onLogout(SessionID sessionID) {
		super.onLogout(sessionID);
	}
	
}
