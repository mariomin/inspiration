package com.huatai.common.event.base;

import com.huatai.common.event.IAsyncEventListener;

public class AsyncExecuteEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final IAsyncEventListener innerListener;
	private final AsyncEvent innerEvent;
	
	public AsyncExecuteEvent(IAsyncEventListener listener, AsyncEvent event) {
		super();
		innerListener = listener;
		innerEvent = event;
	}
	
	public IAsyncEventListener getInnerListener() {
		return innerListener;
	}
	
	public AsyncEvent getInnerEvent() {
		return innerEvent;
	}
	
}
