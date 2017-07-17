package com.huatai.platform.event;

import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.event.AsyncTimerEvent;

public class InnovationPerfTest {
	private static final Logger log = LoggerFactory.getLogger(InnovationPerfTest.class);
	private final AsyncTimerEvent event = new AsyncTimerEvent();
	private static final String methodName = "processAsyncTimerEvent";
	private final long loop = 100000000;
	
	@Test
	public void testInnovationPerf() throws Throwable {
		LambdaInnovationPack lambdaInvo = new LambdaInnovationPack(this, event.getClass(), methodName);
		Method method = InnovationPerfTest.class.getMethod(methodName, event.getClass());
		
		long duration = System.currentTimeMillis();
		for (long i = 0; i < loop; i++) {
			processAsyncTimerEvent(event);
		}
		duration = System.currentTimeMillis() - duration;
		log.info("Running {} direct call performance: {} ms", loop, duration);
		
		duration = System.currentTimeMillis();
		for (long i = 0; i < loop; i++) {
			lambdaInvo.getLambda().accept(event);
		}
		duration = System.currentTimeMillis() - duration;
		log.info("Running {} lambda performance: {} ms", loop, duration);
		
		duration = System.currentTimeMillis();
		for (long i = 0; i < loop; i++) {
			method.invoke(this, event);
		}
		duration = System.currentTimeMillis() - duration;
		log.info("Running {} reflection performance: {} ms", loop, duration);
	}
	
	public void processAsyncTimerEvent(AsyncTimerEvent event) {}
}