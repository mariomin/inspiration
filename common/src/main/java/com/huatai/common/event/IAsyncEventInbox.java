package com.huatai.common.event;

import com.huatai.common.event.base.AsyncEvent;

public interface IAsyncEventInbox {
	void addEvent(AsyncEvent event, IAsyncEventListener listener);
}
