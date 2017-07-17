package com.huatai.platform.schedule;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class Scheduler {
	private final ScheduledExecutorService scheduler;
	
	public Scheduler(String name) {
		scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, name);
			}
		});
	}
	
	public Scheduler(String name, int threadCount) {
		scheduler = Executors.newScheduledThreadPool(threadCount, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, name);
			}
		});
	}
	
	public void schedule(Runnable command, long delay, TimeUnit unit) {
		scheduler.schedule(command, delay, unit);
	}
	
	public void scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		scheduler.scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}
	
	public List<Runnable> shutdownNow() {
		return scheduler.shutdownNow();
	}
	
}
