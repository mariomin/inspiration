package com.huatai.common.disruptor;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.event.base.AsyncEventContainer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.ProducerType;

public abstract class DisruptorPriorityEventThread implements WorkHandler<AsyncEventContainer> {
	private final DisruptorDispatcher disruptorDispatcher;
	
	public DisruptorPriorityEventThread(ProducerType producerType, WaitStrategy waitStrategy, int bufferSize, int highPriorityEventThreads,
			int normalPriorityEventThreads) {
		disruptorDispatcher = new DisruptorDispatcher(this, bufferSize, producerType, waitStrategy, highPriorityEventThreads,
				normalPriorityEventThreads);
	}
	
	@Override
	public void onEvent(AsyncEventContainer container) throws Exception {
		onEvent(container.getEvent());
	}
	
	public abstract void onEvent(AsyncEvent event);
	
	public void setName(String name) {
		disruptorDispatcher.setName(name);
	}
	
	public void exit() {
		disruptorDispatcher.exit();
	}
	
	public void addEvent(AsyncEvent event) {
		disruptorDispatcher.addEvent(event);
	}
	
	public void start() {
		disruptorDispatcher.start();
	}
	
	public boolean isAlive() {
		return disruptorDispatcher.isAlive();
	}
	
	public int getHighPriorityQueueSize() {
		return disruptorDispatcher.getHighPriorityQueueSize();
	}
	
	public long getHighPriorityQueueRemainingCapacity() {
		return disruptorDispatcher.getHighPriorityQueueRemainingCapacity();
	}
	
	public int getNormalPriorityQueueSize() {
		return disruptorDispatcher.getNormalPriorityQueueSize();
	}
	
	public long getNormalPriorityQueueRemainingCapacity() {
		return disruptorDispatcher.getNormalPriorityQueueRemainingCapacity();
	}
}
