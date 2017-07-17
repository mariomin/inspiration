package com.huatai.common.event;

import com.huatai.common.event.base.AsyncEvent;

public interface IAsyncEventListener {
	void onEvent(AsyncEvent event);
}
