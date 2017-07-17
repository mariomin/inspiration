package com.huatai.platform.event;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.huatai.common.event.IAsyncEventManager;
import com.huatai.common.event.base.AsyncEvent;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

public class AsyncEventProcessorTest extends AsyncEventProcessor {
	private final IAsyncEventManager eventManager = new AsyncEventManager();
	private String message;
	
	final class TestEvent extends AsyncEvent {
		private static final long serialVersionUID = 1L;
		String message;
		
		public String getMessage() {
			return message;
		}
		
		public TestEvent(String message) {
			super();
			this.message = message;
		}
	}
	
	final class TestEvent2 extends AsyncEvent {
		private static final long serialVersionUID = 1L;
		String message;
		
		public String getMessage() {
			return message;
		}
		
		public TestEvent2(String message) {
			super();
			this.message = message;
		}
	}
	
	public String getMessage() {
		return message;
	}
	
	public void processTestEvent(TestEvent event) {
		message = event.getMessage();
	}
	
	@Override
	public IAsyncEventManager getEventManager() {
		return eventManager;
	}
	
	@Override
	public void subscribeToEvents() {
		subscribeToEvent(TestEvent.class, null);
	}
	
	@Before
	public void before() {
		message = "";
	}
	
	@Test
	public void testEventProcessingSync() throws Exception {
		AsyncEventProcessorTest eventProcessor = new AsyncEventProcessorTest();
		eventProcessor.setSync(true);
		eventProcessor.setHandler(eventProcessor);
		eventProcessor.init();
		TestEvent event = new TestEvent("hello");
		eventProcessor.getEventManager().sendEvent(event);
		assertTrue(eventProcessor.getMessage().equals(event.getMessage()));
		eventProcessor.uninit();
		
	}
	
	@Test
	public void testEventProcessingAsync() throws Exception {
		AsyncEventProcessorTest eventProcessor = new AsyncEventProcessorTest();
		eventProcessor.setHandler(eventProcessor);
		eventProcessor.init();
		TestEvent event = new TestEvent("hello");
		eventProcessor.getEventManager().sendEvent(event);
		Thread.sleep(1);
		assertTrue(eventProcessor.getMessage().equals(event.getMessage()));
		eventProcessor.uninit();
	}
	
	@Test
	public void testEventProcessingNotHandling() throws Exception {
		AsyncEventProcessorTest eventProcessor = new AsyncEventProcessorTest();
		eventProcessor.setSync(true);
		eventProcessor.setHandler(this);
		eventProcessor.init();
		TestEvent2 event = new TestEvent2("hello");
		eventProcessor.getEventManager().sendEvent(event);
		assertTrue(eventProcessor.getMessage() == null);
		eventProcessor.uninit();
		
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
	public ProducerType getDisruptorProducerType() {
		return ProducerType.MULTI;
	}
	
	@Override
	public WaitStrategy getWaitStrategy() {
		return new SleepingWaitStrategy();
	}
	
	@Override
	public int getBufferSize() {
		return 1024;
	}
	
}
