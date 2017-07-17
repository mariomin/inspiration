package com.huatai.common.event.base;

import java.io.Serializable;

public abstract class AsyncEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	private String key;
	private EventPriority priority = EventPriority.NORMAL;
	
	public AsyncEvent() {}
	
	public AsyncEvent(String key) {
		this();
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public EventPriority getPriority() {
		return priority;
	}
	
	public void setPriority(EventPriority priority) {
		this.priority = priority;
	}
	
}
