package com.huatai.common.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringUtilTest {
	
	@Test
	public void testCheckDecimalLength() {
		assertTrue(StringUtil.checkDecimalLength("16.31", 2));
		assertTrue(StringUtil.checkDecimalLength("16.310", 3));
		assertTrue(StringUtil.checkDecimalLength("16.313", 3));
		assertFalse(StringUtil.checkDecimalLength("16.", 0));
		assertFalse(StringUtil.checkDecimalLength("16", 0));
	}
}
