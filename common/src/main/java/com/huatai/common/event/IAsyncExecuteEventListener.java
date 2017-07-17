package com.huatai.common.event;

public interface IAsyncExecuteEventListener extends IAsyncEventListener {
	IAsyncEventInbox getInbox();
}
