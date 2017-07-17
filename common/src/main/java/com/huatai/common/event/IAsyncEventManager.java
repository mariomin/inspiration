package com.huatai.common.event;

import com.huatai.common.event.base.AsyncEvent;

public interface IAsyncEventManager {
	void subscribe(Class<? extends AsyncEvent> clazz, IAsyncEventListener listener);
	
	void subscribe(Class<? extends AsyncEvent> clazz, String key, IAsyncEventListener listener);
	
	void unsubscribe(Class<? extends AsyncEvent> clazz, IAsyncEventListener listener);
	
	void unsubscribe(Class<? extends AsyncEvent> clazz, String key, IAsyncEventListener listener);
	
	void clearAllSubscriptions();
	
	void sendEvent(AsyncEvent event);
}
