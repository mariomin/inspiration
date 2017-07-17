package com.huatai.platform.event;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.event.IAsyncEventManager;
import com.huatai.common.event.base.AsyncEvent;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

public class AsyncEventProcessorPerformanceTest {
	private static final Logger log = LoggerFactory.getLogger(AsyncEventProcessorPerformanceTest.class);
	private final LambdaEvent lambdaEvent = new LambdaEvent("");
	private final AsyncEventManager eventManager = new AsyncEventManager();
	private final AsyncEventProcessor lambdaEventProcessor = new AsyncEventProcessor() {
		
		@Override
		public void subscribeToEvents() {
			subscribeToEvent(LambdaEvent.class, null);
		}
		
		@Override
		public IAsyncEventManager getEventManager() {
			return eventManager;
		}
		
		@Override
		public ProducerType getDisruptorProducerType() {
			return ProducerType.MULTI;
		}
		
		@Override
		public int getBufferSize() {
			return 1024;
		}
		
		@Override
		public int getHighPriorityEventProcessorThreads() {
			return 0;
		}
		
		@Override
		public int getNormalPriorityProcessorThreads() {
			return 1;
		}
		
		@Override
		public WaitStrategy getWaitStrategy() {
			return new SleepingWaitStrategy();
		}
	};
	
	private final long loop = 10000000;
	
	public AsyncEventProcessorPerformanceTest() throws Exception {
		
		lambdaEventProcessor.setSync(true);
		lambdaEventProcessor.setHandler(this);
		lambdaEventProcessor.init();
	}
	
	@Test
	public void testPerformance() throws InterruptedException {
		long duration = System.currentTimeMillis();
		for (long i = 0; i < loop; i++) {
			lambdaEventProcessor.getEventManager().sendEvent(lambdaEvent);
		}
		duration = System.currentTimeMillis() - duration;
		log.info("Running {} onAsyncLambdaEvent performance: {} ms", loop, duration);
		
		lambdaEventProcessor.uninit();
	}
	
	public void processReflectiveEvent(ReflectiveEvent event) {}
	
	public void processLambdaEvent(LambdaEvent event) {}
	
	final class ReflectiveEvent extends AsyncEvent {
		private static final long serialVersionUID = 1L;
		String message;
		
		public String getMessage() {
			return message;
		}
		
		public ReflectiveEvent(String message) {
			this.message = message;
		}
	}
	
	final class LambdaEvent extends AsyncEvent {
		private static final long serialVersionUID = 1L;
		String message;
		
		public String getMessage() {
			return message;
		}
		
		public LambdaEvent(String message) {
			this.message = message;
		}
	}
	
}
