package com.huatai.platform.event;

import com.huatai.common.event.IAsyncEventListener;
import com.huatai.common.event.IAsyncEventManager;
import com.huatai.common.event.base.AsyncEvent;

public class MockEventManager implements IAsyncEventManager {
	
	@Override
	public void subscribe(Class<? extends AsyncEvent> clazz, IAsyncEventListener listener) {}
	
	@Override
	public void subscribe(Class<? extends AsyncEvent> clazz, String key, IAsyncEventListener listener) {}
	
	@Override
	public void unsubscribe(Class<? extends AsyncEvent> clazz, IAsyncEventListener listener) {}
	
	@Override
	public void unsubscribe(Class<? extends AsyncEvent> clazz, String key, IAsyncEventListener listener) {}
	
	@Override
	public void clearAllSubscriptions() {}
	
	@Override
	public void sendEvent(AsyncEvent event) {}
	
}
