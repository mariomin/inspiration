package com.huatai.platform.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.event.IAsyncEventListener;
import com.huatai.common.event.base.AsyncEvent;

public class MockScheduleManager extends ScheduleManager {
	private static final Logger log = LoggerFactory.getLogger(MockScheduleManager.class);
	
	@Override
	public void init() throws Exception {
		log.info("initialising mockScheduleManager");
	}
	
	@Override
	public void uninit() {
		log.info("uninitialising mockScheduleManager");
	}
	
	@Override
	public boolean scheduleTimerEvent(long time, IAsyncEventListener listener, AsyncEvent event) {
		return true;
	}
	
	@Override
	public boolean scheduleTimerEvent(Date time, IAsyncEventListener listener, AsyncEvent event) {
		return true;
	}
	
	@Override
	public boolean scheduleRepeatTimerEvent(long delay, long period, IAsyncEventListener listener, AsyncEvent event) {
		return true;
	}
	
	@Override
	public boolean scheduleRepeatTimerEvent(long period, IAsyncEventListener listener, AsyncEvent event) {
		return true;
	}
	
	@Override
	public boolean cancelTimerEvent(AsyncEvent event) {
		return true;
	}
}
