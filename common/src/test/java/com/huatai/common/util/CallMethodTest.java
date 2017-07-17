package com.huatai.common.util;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallMethodTest {
	private static final Logger log = LoggerFactory.getLogger(CallMethodTest.class);
	public int i;
	
	public CallMethodTest() {}
	
	public int compareTo(int i) {
		return new Integer(this.i).compareTo(i);
	}
	
	public Long compareTo(Long i) {
		return (long) new Long(this.i).compareTo(i);
	}
	
	@Test
	public void testInt() {
		CallMethodTest t1 = new CallMethodTest();
		t1.i = 2;
		Object o1 = t1;
		
		int j = 3;
		int result = ReflectionUtil.callMethod(Integer.TYPE, o1, "compareTo", new Object[] { j });
		assertTrue(result == -1);
	}
	
	@Test
	public void testInteger() {
		Integer t1 = new Integer(2);
		Object o1 = t1;
		
		Integer result = ReflectionUtil.callMethod(Integer.TYPE, o1, "compareTo", new Object[] { new Integer(2) });
		assertTrue(result == 0);
	}
	
	@Test
	public void testLong() {
		CallMethodTest t1 = new CallMethodTest();
		t1.i = 3;
		Object o1 = t1;
		
		Long j = new Long(2);
		Long result = ReflectionUtil.callMethod(Long.class, o1, "compareTo", new Object[] { j });
		assertTrue(result == 1);
	}
	
	@Test
	public void testDate() {
		Date t1 = new Date();
		Object o1 = t1;
		Date t2 = new Date();
		t2.setTime(new Date().getTime() + 1);
		
		Integer result = ReflectionUtil.callMethod(Integer.TYPE, o1, "compareTo", new Object[] { t2 });
		log.info(t1.toString());
		log.info(t2.toString());
		log.info(result.toString());
		assertTrue(result == -1);
	}
	
	@Test
	public void testNoMethod() {
		CallMethodTest t1 = new CallMethodTest();
		t1.i = 3;
		Object o1 = t1;
		
		Long j = new Long(2);
		Long result = ReflectionUtil.callMethod(Long.class, o1, "CompareTo", new Object[] { j });
		assertTrue(result == null);
	}
	
	@Test
	public void testMismatchedParam() {
		CallMethodTest t1 = new CallMethodTest();
		t1.i = 3;
		Object o1 = t1;
		
		Double j = new Double(2);
		Long result = ReflectionUtil.callMethod(Long.class, o1, "CompareTo", new Object[] { j });
		assertTrue(result == null);
	}
}
