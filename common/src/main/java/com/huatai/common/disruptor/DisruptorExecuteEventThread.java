package com.huatai.common.disruptor;

import com.huatai.common.event.IAsyncEventInbox;
import com.huatai.common.event.IAsyncEventListener;
import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.event.base.AsyncExecuteEvent;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

public abstract class DisruptorExecuteEventThread extends DisruptorPriorityEventThread implements IAsyncEventInbox {
	
	public DisruptorExecuteEventThread(ProducerType producerType, WaitStrategy waitStrategy, int bufferSize, int highPriorityEventThreads,
			int normalPriorityEventThreads) {
		super(producerType, waitStrategy, bufferSize, highPriorityEventThreads, normalPriorityEventThreads);
	}
	
	@Override
	public void addEvent(AsyncEvent event, IAsyncEventListener listener) {
		addEvent(new AsyncExecuteEvent(listener, event));
	}
	
	protected abstract void onNormalEvent(AsyncEvent event);
	
	@Override
	public void onEvent(AsyncEvent event) {
		if (event instanceof AsyncExecuteEvent) {
			AsyncExecuteEvent executeEvent = (AsyncExecuteEvent) event;
			executeEvent.getInnerListener().onEvent(executeEvent.getInnerEvent());
		} else {
			onNormalEvent(event);
		}
	}
	
}
