package com.huatai.common.util;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TimeUtilTest {
	
	@Test
	public void testDateOnly1() throws ParseException {
		String dateStr = "2011-07-21 15:20:25.234";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = dateFormat.parse(dateStr);
		Date dateOnly = TimeUtil.getOnlyDate(date);
		
		String strDate = dateFormat.format(dateOnly);
		assertTrue(strDate.equals("2011-07-21 00:00:00.000"));
	}
	
	@Test
	public void testDateOnly2() throws ParseException {
		String dateStr = "2011-07-21 04:20:25.234";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = dateFormat.parse(dateStr);
		Date dateOnly = TimeUtil.getOnlyDate(date);
		
		String strDate = dateFormat.format(dateOnly);
		assertTrue(strDate.equals("2011-07-21 00:00:00.000"));
	}
	
	@Test
	public void testDateOnly3() throws ParseException {
		String dateStr = "2011-07-21 20:20:25.234";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = dateFormat.parse(dateStr);
		Date dateOnly = TimeUtil.getOnlyDate(date);
		
		String strDate = dateFormat.format(dateOnly);
		assertTrue(strDate.equals("2011-07-21 00:00:00.000"));
	}
	
	@Test
	public void testParseTime() throws ParseException {
		String timeFormat = "HH:mm:ss.SSS";
		String timeStr = "15:20:25.356";
		Date date = TimeUtil.parseTime(timeFormat, timeStr);
		Date dateOnly = TimeUtil.getOnlyDate(date);
		assertTrue(dateOnly.equals(TimeUtil.getOnlyDate(new Date())));
	}
	
	@Test
	public void testPreviousDay() throws ParseException {
		String dateStr = "2011-07-21 20:20:25.234";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = dateFormat.parse(dateStr);
		Date previousDay = TimeUtil.getPreviousDay(date);
		String strPreviousDay = dateFormat.format(previousDay);
		assertTrue(strPreviousDay.equals("2011-07-20 20:20:25.234"));
	}
}
