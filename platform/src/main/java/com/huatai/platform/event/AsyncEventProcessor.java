package com.huatai.platform.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.disruptor.DisruptorPriorityEventThread;
import com.huatai.common.event.IAsyncEventListener;
import com.huatai.common.event.IAsyncEventManager;
import com.huatai.common.event.base.AsyncEvent;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

public abstract class AsyncEventProcessor implements IAsyncEventListener {
	private static final Logger log = LoggerFactory.getLogger(AsyncEventProcessor.class);
	private Object handler;
	private Throwable lastThrowable;
	private final List<AsyncEventSub> subs = new ArrayList<AsyncEventSub>();
	private boolean sync;
	private final Map<Class<? extends AsyncEvent>, LambdaInnovationPack> lambdaMap = new HashMap<Class<? extends AsyncEvent>, LambdaInnovationPack>();
	private DisruptorPriorityEventThread thread;
	
	private class AsyncEventSub {
		private final Class<? extends AsyncEvent> eventClass;
		private final String key;
		
		public AsyncEventSub(Class<? extends AsyncEvent> eventClass, String key) {
			this.eventClass = eventClass;
			this.key = key;
		}
		
		public Class<? extends AsyncEvent> getEventClass() {
			return eventClass;
		}
		
		public String getKey() {
			return key;
		}
		
	}
	
	public abstract void subscribeToEvents();
	
	public abstract int getHighPriorityEventProcessorThreads();
	
	public abstract int getNormalPriorityProcessorThreads();
	
	public abstract int getBufferSize();
	
	public abstract ProducerType getDisruptorProducerType();
	
	public abstract WaitStrategy getWaitStrategy();
	
	public abstract IAsyncEventManager getEventManager();
	
	private static String getMethodName(Class<? extends AsyncEvent> clazz) {
		return "process" + clazz.getSimpleName();
	}
	
	public void subscribeToEvent(Class<? extends AsyncEvent> clazz, String key) {
		AsyncEventSub sub = new AsyncEventSub(clazz, key);
		subs.add(sub);
		try {
			registerSubscription(sub);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void init() throws Exception {
		if (handler == null) throw new RuntimeException("set handler before init");
		
		if (!sync && (thread == null)) {
			createThread();
		}
		
		subscribeToEvents();
	}
	
	public void registerSubscription(AsyncEventSub sub) throws Exception {
		IAsyncEventManager eventManager = getEventManager();
		String methodName = getMethodName(sub.getEventClass());
		try {
			LambdaInnovationPack lambdaInvo = new LambdaInnovationPack(getHandler(), sub.getEventClass(), methodName);
			lambdaMap.put(sub.getEventClass(), lambdaInvo);
		} catch (Throwable e) {
			if (e instanceof NoSuchMethodException)
				throw new Exception(
						"Event type " + sub.getEventClass().getName() + " is not handled. Do you have a public method with name " + methodName + "?");
			else {
				log.error(e.getMessage(), e);
			}
			
			lastThrowable = e;
		}
		eventManager.subscribe(sub.getEventClass(), sub.getKey(), this);
	}
	
	public void uninit() {
		IAsyncEventManager eventManager = getEventManager();
		for (AsyncEventSub sub : subs) {
			eventManager.unsubscribe(sub.getEventClass(), sub.getKey(), this);
		}
		lastThrowable = null;
		subs.clear();
		lambdaMap.clear();
		if (thread != null) {
			killThread();
		}
	}
	
	private void onAsyncEvent(AsyncEvent event) {
		LambdaInnovationPack lambdaInvo = lambdaMap.get(event.getClass());
		if (lambdaInvo == null) {
			// if handling method not found try to get outer class handler
			String methodName = getMethodName(event.getClass());
			try {
				lambdaInvo = new LambdaInnovationPack(getHandler(), event.getClass(), methodName);
			} catch (Throwable e) {
				log.error("Event type {} handling method is not found {}", event.getClass(), getHandler());
				return;
			}
			
			lambdaMap.put(event.getClass(), lambdaInvo);
			log.info("{} added in lambda method map", methodName);
		}
		
		try {
			lambdaInvo.getLambda().accept(event);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			lastThrowable = e;
		}
	}
	
	@Override
	public void onEvent(AsyncEvent event) {
		if (sync) {
			onAsyncEvent(event);
		} else {
			thread.addEvent(event);
		}
	}
	
	public Object getHandler() {
		return handler == null ? this : handler;
	}
	
	public void setHandler(Object handler) {
		this.handler = handler;
	}
	
	public Throwable getLastThrowable() {
		return lastThrowable;
	}
	
	public boolean isSync() {
		return sync;
	}
	
	private void createThread() {
		if ((getHighPriorityEventProcessorThreads() <= 0) && (getNormalPriorityProcessorThreads() <= 0)) {
			log.error("Must specify at least 1 type events");
			System.exit(-1);
		}
		
		thread = new DisruptorPriorityEventThread(getDisruptorProducerType(), getWaitStrategy(), getBufferSize(),
				getHighPriorityEventProcessorThreads(), getNormalPriorityProcessorThreads()) {
			
			@Override
			public void onEvent(AsyncEvent event) {
				onAsyncEvent(event);
			}
			
		};
		thread.start();
	}
	
	private void killThread() {
		thread.exit();
		thread = null;
	}
	
	public void setSync(boolean sync) {
		if (this.sync && !sync) {
			// set to asynchronously
			createThread();
		}
		if (!this.sync && sync && (null != thread)) {
			killThread();
		}
		this.sync = sync;
	}
	
	public DisruptorPriorityEventThread getThread() {
		return thread;
	}
	
	public int getHighPriorityThreadQueueSize() {
		return thread.getHighPriorityQueueSize();
	}
	
	public long getHighPriorityThreadQueueRemainingCapacity() {
		return thread.getHighPriorityQueueRemainingCapacity();
	}
	
	public int getNormalPriorityThreadQueueSize() {
		return thread.getNormalPriorityQueueSize();
	}
	
	public long getNormalPriorityThreadQueueRemainingCapacity() {
		return thread.getNormalPriorityQueueRemainingCapacity();
	}
	
}
