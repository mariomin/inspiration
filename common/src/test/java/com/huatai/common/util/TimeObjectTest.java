package com.huatai.common.util;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

public class TimeObjectTest {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	@Test
	public void testConstructor() throws ParseException {
		String dateStr = "2011-07-21 15:20:25.234";
		Date date = dateFormat.parse(dateStr);
		Time time = new Time(date);
		
		assertTrue(time.getHour() == 15);
		assertTrue(time.getMinute() == 20);
		assertTrue(time.getSecond() == 25);
		assertTrue(time.getMs() == 234);
		
		date = dateFormat.parse("2011-07-21 04:20:25.234");
		time = new Time(date);
		
		assertTrue(time.getHour() == 04);
		assertTrue(time.getMinute() == 20);
		assertTrue(time.getSecond() == 25);
		assertTrue(time.getMs() == 234);
		
		date = dateFormat.parse("2011-07-21 21:20:25.234");
		time = new Time(date);
		
		assertTrue(time.getHour() == 21);
		assertTrue(time.getMinute() == 20);
		assertTrue(time.getSecond() == 25);
		assertTrue(time.getMs() == 234);
		
	}
	
	@Test
	public void testGMTMinusTimeZone() throws ParseException {
		TimeZone.setDefault(TimeZone.getTimeZone("PST"));
		String dateStr = "2011-07-21 15:20:25.234";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = dateFormat.parse(dateStr);
		Time time = new Time(date);
		
		assertTrue(time.getHour() == 15);
		assertTrue(time.getMinute() == 20);
		assertTrue(time.getSecond() == 25);
		assertTrue(time.getMs() == 234);
		
		date = dateFormat.parse("2011-07-21 04:20:25.234");
		time = new Time(date);
		
		assertTrue(time.getHour() == 04);
		assertTrue(time.getMinute() == 20);
		assertTrue(time.getSecond() == 25);
		assertTrue(time.getMs() == 234);
		
		date = dateFormat.parse("2011-07-21 21:20:25.234");
		time = new Time(date);
		
		assertTrue(time.getHour() == 21);
		assertTrue(time.getMinute() == 20);
		assertTrue(time.getSecond() == 25);
		assertTrue(time.getMs() == 234);
		
	}
	
	@Test
	public void testBeforeAfter() throws ParseException {
		String dateStr = "2011-07-21 15:20:25.234";
		Date date = dateFormat.parse(dateStr);
		Time time = new Time(date);
		
		date = dateFormat.parse("2011-07-21 04:20:25.234");
		Time time1 = new Time(date);
		
		date = dateFormat.parse("2011-07-21 21:20:25.234");
		Time time2 = new Time(date);
		
		assertTrue(time.after(time1));
		assertTrue(time.before(time2));
		
		date = dateFormat.parse("2011-07-21 15:20:25.233");
		Time time3 = new Time(date);
		assertTrue(time.after(time3));
		
		date = dateFormat.parse("2011-07-21 15:20:25.235");
		Time time4 = new Time(date);
		assertTrue(time.before(time4));
		
		date = dateFormat.parse("2011-07-21 15:20:25.234");
		Time time5 = new Time(date);
		assertTrue(time.equals(time5));
		
	}
	
	@Test
	public void testBeforeAfter2() throws ParseException {
		TimeZone.setDefault(TimeZone.getTimeZone("PST"));
		String dateStr = "2011-07-21 15:20:25.234";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		Date date = dateFormat.parse(dateStr);
		Time time = new Time(date);
		
		date = dateFormat.parse("2011-07-21 04:20:25.234");
		Time time1 = new Time(date);
		
		date = dateFormat.parse("2011-07-21 21:20:25.234");
		Time time2 = new Time(date);
		
		assertTrue(time.after(time1));
		assertTrue(time.before(time2));
		
		date = dateFormat.parse("2011-07-21 15:20:25.233");
		Time time3 = new Time(date);
		assertTrue(time.after(time3));
		
		date = dateFormat.parse("2011-07-21 15:20:25.235");
		Time time4 = new Time(date);
		assertTrue(time.before(time4));
		
		date = dateFormat.parse("2011-07-21 15:20:25.234");
		Time time5 = new Time(date);
		assertTrue(time.equals(time5));
		
	}
	
}
