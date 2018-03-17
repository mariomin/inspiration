package com.inspiration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inspiration.common.util.LoggerService;

public class App {
	private static Logger logger;

	public static void main(String[] args) {
		new App().startLogger().start();
	}

	public App start() {
		ExecutorService executor = Executors.newFixedThreadPool(20);
		for (int i = 0; i < 20; i++) {
			executor.submit(new Runnable() {
				public void run() {

				}
			});
		}
		executor.shutdown();
		for (;;) {
			if (executor.isTerminated()) {
				logger.info("All Finished.");
				break;
			}
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException localInterruptedException) {
			}
		}
		return this;
	}

	public App startLogger() {
		LoggerService log = new LoggerService();
		log.startLog();
		logger = LoggerFactory.getLogger(App.class);
		return this;
	}
}
