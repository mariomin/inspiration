package com.huatai.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

import com.huatai.common.Clock;

public class TimeUtil {
	public static final long MilliSecondInDay = 60 * 60 * 24 * 1000;
	
	public static String getTodaybyFormat(String pattern) {
		FastDateFormat dateFormat = FastDateFormat.getInstance(pattern);
		return dateFormat.format(System.currentTimeMillis());
	}
	
	public static long getPassedTime(Date time) {
		return getPassedTime(Clock.getInstance().now(), time);
	}
	
	public static long getPassedTime(Date now, Date time) {
		return now.getTime() - time.getTime();
	}
	
	public static Date parseTime(String format, String time) throws ParseException {
		Calendar today, adjust;
		today = Calendar.getInstance();
		today.setTime(new Date());
		adjust = Calendar.getInstance();
		adjust.setTime(new SimpleDateFormat(format).parse(time));
		adjust.set(Calendar.YEAR, today.get(Calendar.YEAR));
		adjust.set(Calendar.MONTH, today.get(Calendar.MONTH));
		adjust.set(Calendar.DATE, today.get(Calendar.DATE));
		return adjust.getTime();
	}
	
	public static Date getOnlyDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static Date getPreviousDay(Date date) {
		Date result = new Date(date.getTime() - (24 * 60 * 60 * 1000));
		return result;
	}
	
	public static boolean sameDate(Date d1, Date d2) {
		if ((null == d1) || (null == d2)) return false;
		return getOnlyDate(d1).equals(getOnlyDate(d2));
	}
}
