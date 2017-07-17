package com.huatai.platform.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.huatai.common.event.AsyncTimerEvent;
import com.huatai.common.event.IAsyncEventListener;
import com.huatai.common.event.base.AsyncEvent;

public class ScheduleManagerTest {
	class TimerListener implements IAsyncEventListener {
		public int count = 0;;
		
		@Override
		public void onEvent(AsyncEvent event) {
			synchronized (this) {
				notify();
				count++;
			}
		}
	}
	
	class FiveTimerListener implements IAsyncEventListener {
		public int count = 0;;
		
		@Override
		public void onEvent(AsyncEvent event) {
			count++;
			if (count == 5) {
				synchronized (this) {
					notify();
				}
			}
		}
	}
	
	private final ScheduleManager scheduleManager = new ScheduleManager();
	
	@Test
	public void testOneTime() throws InterruptedException {
		TimerListener listener = new TimerListener();
		AsyncTimerEvent event = new AsyncTimerEvent();
		scheduleManager.scheduleTimerEvent(10, listener, event);
		// schedule the timer twice, but previous one is overwritten
		// so only one should fire.
		scheduleManager.scheduleTimerEvent(20, listener, event);
		synchronized (listener) {
			listener.wait(100);
		}
		assertTrue(listener.count == 1);
		
		// already auto removed by timer firing, so it should return false
		assertFalse(scheduleManager.cancelTimerEvent(event));
	}
	
	@Test
	public void testRepeat() throws InterruptedException {
		FiveTimerListener listener = new FiveTimerListener();
		AsyncTimerEvent event = new AsyncTimerEvent();
		scheduleManager.scheduleRepeatTimerEvent(10, listener, event);
		synchronized (listener) {
			listener.wait(100);
		}
		
		assertTrue(listener.count == 5);
		assertTrue(scheduleManager.cancelTimerEvent(event));
	}
	
}
