package com.huatai.platform.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.huatai.common.event.IAsyncEventListener;
import com.huatai.common.event.IAsyncEventManager;
import com.huatai.common.event.IAsyncExecuteEventListener;
import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.platform.PlatformSettings;

public class AsyncEventManager extends PlatformSettings implements IAsyncEventManager {
	private final Map<Class<? extends AsyncEvent>, Map<String, List<IAsyncEventListener>>> subscriptions = new ConcurrentHashMap<Class<? extends AsyncEvent>, Map<String, List<IAsyncEventListener>>>();
	
	@Override
	public void sendEvent(AsyncEvent event) {
		Map<String, List<IAsyncEventListener>> listenerMap = subscriptions.get(event.getClass());
		if (listenerMap == null) return;
		
		if (event.getKey() != null) {
			List<IAsyncEventListener> eventListeners = listenerMap.get(event.getKey());
			callListeners(eventListeners, event);
		}
		
		List<IAsyncEventListener> eventListeners = listenerMap.get("*");
		callListeners(eventListeners, event);
	}
	
	private void callListeners(List<IAsyncEventListener> eventListeners, AsyncEvent event) {
		if (eventListeners != null) {
			
			synchronized (eventListeners) {
				eventListeners = new ArrayList<IAsyncEventListener>(eventListeners);
			}
			
			for (IAsyncEventListener listener : eventListeners) {
				if (listener instanceof IAsyncExecuteEventListener) {
					((IAsyncExecuteEventListener) listener).getInbox().addEvent(event, listener);
				} else {
					listener.onEvent(event);
				}
			}
		}
	}
	
	@Override
	public void subscribe(Class<? extends AsyncEvent> clazz, IAsyncEventListener listener) {
		subscribe(clazz, "*", listener);
	}
	
	@Override
	public void subscribe(Class<? extends AsyncEvent> clazz, String key, IAsyncEventListener listener) {
		if (listener == null) return;
		
		Map<String, List<IAsyncEventListener>> listenerMap;
		synchronized (subscriptions) {
			listenerMap = subscriptions.get(clazz);
			if (listenerMap == null) {
				listenerMap = new HashMap<String, List<IAsyncEventListener>>();
				subscriptions.put(clazz, listenerMap);
			}
		}
		
		if (key == null) {
			key = "*";
		}
		
		List<IAsyncEventListener> eventListeners;
		synchronized (listenerMap) {
			eventListeners = listenerMap.get(key);
			if (eventListeners == null) {
				eventListeners = new ArrayList<IAsyncEventListener>();
				listenerMap.put(key, eventListeners);
			}
		}
		eventListeners.add(listener);
	}
	
	@Override
	public void unsubscribe(Class<? extends AsyncEvent> clazz, IAsyncEventListener listener) {
		unsubscribe(clazz, "*", listener);
	}
	
	@Override
	public void unsubscribe(Class<? extends AsyncEvent> clazz, String key, IAsyncEventListener listener) {
		if ((key == null) || (listener == null)) return;
		
		Map<String, List<IAsyncEventListener>> listenerMap = subscriptions.get(clazz);
		if (listenerMap == null) return;
		
		List<IAsyncEventListener> eventListeners = listenerMap.get(key);
		if (eventListeners == null) return;
		
		eventListeners.remove(listener);
	}
	
	@Override
	public void clearAllSubscriptions() {
		subscriptions.clear();
	}
}
