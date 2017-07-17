package com.huatai.platform.schedule;

import java.util.Date;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huatai.common.event.IAsyncEventListener;
import com.huatai.common.event.IAsyncEventManager;
import com.huatai.common.event.IAsyncExecuteEventListener;
import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.event.platform.PluginReadyEvent;
import com.huatai.common.platform.PlatformSettings;

public class ScheduleManager extends PlatformSettings {
	private static final Logger log = LoggerFactory.getLogger(ScheduleManager.class);
	private final Map<AsyncEvent, EventTimerTask> schedules = new ConcurrentHashMap<AsyncEvent, EventTimerTask>();
	private final Scheduler timer = new Scheduler("scheduleManager");
	
	@Autowired
	private IAsyncEventManager eventManager;
	
	@Override
	public void init() throws Exception {
		log.info("initialising");
		super.init();
		
		eventManager.sendEvent(new PluginReadyEvent(null, name, true));
	}
	
	@Override
	public void uninit() {
		log.info("uninitialising");
		timer.shutdownNow();
		super.uninit();
	}
	
	private class EventTimerTask extends TimerTask {
		private final boolean once;
		private final IAsyncEventListener listener;
		private final AsyncEvent event;
		
		EventTimerTask(IAsyncEventListener listener, AsyncEvent event, boolean once) {
			super();
			this.listener = listener;
			this.event = event;
			this.once = once;
		}
		
		@Override
		public void run() {
			if (once) {
				schedules.remove(event);
			}
			if (listener instanceof IAsyncExecuteEventListener) {
				IAsyncExecuteEventListener al = (IAsyncExecuteEventListener) listener;
				al.getInbox().addEvent(event, listener);
			} else {
				listener.onEvent(event);
			}
		}
	}
	
	private boolean cancelIfExists(AsyncEvent event) {
		EventTimerTask task = schedules.remove(event);
		if (task != null) {
			task.cancel();
			return true;
		}
		return false;
	}
	
	public boolean scheduleTimerEvent(long time, IAsyncEventListener listener, AsyncEvent event) {
		boolean result = cancelIfExists(event);
		EventTimerTask task = new EventTimerTask(listener, event, true);
		schedules.put(event, task);
		timer.schedule(task, time, TimeUnit.MILLISECONDS);
		return result;
	}
	
	public boolean scheduleTimerEvent(Date time, IAsyncEventListener listener, AsyncEvent event) {
		long delay = time.getTime() - System.currentTimeMillis();
		if (delay < 0)
			return false;
		else return scheduleTimerEvent(delay, listener, event);
	}
	
	public boolean scheduleRepeatTimerEvent(long delay, long period, IAsyncEventListener listener, AsyncEvent event) {
		boolean result = cancelIfExists(event);
		EventTimerTask task = new EventTimerTask(listener, event, false);
		schedules.put(event, task);
		timer.scheduleWithFixedDelay(task, delay, period, TimeUnit.MILLISECONDS);
		return result;
	}
	
	public boolean scheduleRepeatTimerEvent(long period, IAsyncEventListener listener, AsyncEvent event) {
		return scheduleRepeatTimerEvent(0, period, listener, event);
	}
	
	public boolean cancelTimerEvent(AsyncEvent event) {
		return cancelIfExists(event);
	}
	
}
