package com.huatai.platform.event;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huatai.common.event.IAsyncEventListener;
import com.huatai.common.event.base.AsyncEvent;

public class AsyncEventTest {
	private final AsyncEventManager eventManager = new AsyncEventManager();
	private final AsyncListener listener1 = new AsyncListener();
	private final AsyncListener listener11 = new AsyncListener();
	
	class AsyncEvent1 extends AsyncEvent {
		private static final long serialVersionUID = 1L;
		
		public AsyncEvent1(String string) {
			super(string);
		}
		
		public AsyncEvent1() {
			super();
		}
	}
	
	class AsyncEvent2 extends AsyncEvent {
		private static final long serialVersionUID = 1L;
	}
	
	class AsyncListener implements IAsyncEventListener {
		public AsyncEvent event;
		
		@Override
		public void onEvent(AsyncEvent event) {
			this.event = event;
		}
	};
	
	@Before
	public void Before() {
		eventManager.clearAllSubscriptions();
	}
	
	@After
	public void After() {}
	
	@Test
	public void testBasic() {
		eventManager.subscribe(AsyncEvent1.class, listener1);
		eventManager.subscribe(AsyncEvent1.class, listener11);
		eventManager.sendEvent(new AsyncEventTest.AsyncEvent1());
		assertTrue(listener1.event instanceof AsyncEventTest.AsyncEvent1);
		assertTrue(listener11.event instanceof AsyncEventTest.AsyncEvent1);
		
		listener1.event = null;
		listener11.event = null;
		eventManager.sendEvent(new AsyncEventTest.AsyncEvent2());
		assertTrue(listener1.event == null);
		assertTrue(listener11.event == null);
		
		eventManager.unsubscribe(AsyncEvent1.class, listener1);
		eventManager.sendEvent(new AsyncEventTest.AsyncEvent1());
		assertTrue(listener1.event == null);
		assertTrue(listener11.event instanceof AsyncEventTest.AsyncEvent1);
	}
	
	@Test
	public void testKeySubscription() {
		eventManager.subscribe(AsyncEvent1.class, "ABC", listener1);
		eventManager.subscribe(AsyncEvent1.class, listener11);
		eventManager.sendEvent(new AsyncEventTest.AsyncEvent1("ABC"));
		assertTrue(listener1.event instanceof AsyncEventTest.AsyncEvent1);
		assertTrue(listener11.event instanceof AsyncEventTest.AsyncEvent1);
		
		listener1.event = null;
		listener11.event = null;
		eventManager.sendEvent(new AsyncEventTest.AsyncEvent1());
		assertTrue(listener1.event == null);
		assertTrue(listener11.event instanceof AsyncEventTest.AsyncEvent1);
		
		eventManager.unsubscribe(AsyncEvent1.class, "ABC", listener1);
		eventManager.sendEvent(new AsyncEventTest.AsyncEvent1("ABC"));
		assertTrue(listener1.event == null);
		assertTrue(listener11.event instanceof AsyncEventTest.AsyncEvent1);
	}
	
}
