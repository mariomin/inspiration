package com.huatai.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;

import org.junit.Test;

public class TimestampUtilTest {
	private final Timestamp t1 = new Timestamp(System.currentTimeMillis() - 100000);
	private final Timestamp t2 = new Timestamp(System.currentTimeMillis());
	
	@Test
	public void testLate() {
		assertNull(TimestampUtil.late(null, null));
		assertEquals(TimestampUtil.late(t1, null), t1);
		assertEquals(TimestampUtil.late(null, t1), t1);
		assertEquals(TimestampUtil.late(t1, t2), t2);
	}
	
	@Test
	public void testEarly() {
		assertNull(TimestampUtil.early(null, null));
		assertEquals(TimestampUtil.early(t1, null), t1);
		assertEquals(TimestampUtil.early(null, t1), t1);
		assertEquals(TimestampUtil.early(t1, t2), t1);
	}
}
