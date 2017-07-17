package com.huatai.common.util;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

public class SequenceIdTest {
	private final Set<String> set = new ConcurrentHashSet<String>();
	
	private final Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			for (int i = 0; i < 100000; i++) {
				String id = IdGenerator.getInstance().getNextID();
				assertTrue(!set.contains(id));
				set.add(id);
			}
			synchronized (this) {
				notify();
			}
		}
	};
	
	@Test
	public void test() throws InterruptedException {
		Thread t1 = new Thread(runnable);
		Thread t2 = new Thread(runnable);
		t1.start();
		t2.start();
		synchronized (this) {
			wait(1000);
		}
	}
}
