package com.huatai.common.backtest;

import java.util.Calendar;
import java.util.Date;

import com.huatai.common.marketdata.TimeFrame;

public class TimeUtil {
	
	public static boolean isDifferentDay(long date1, long date2) {
		return isDifferentDay(new Date(date1), new Date(date2));
	}
	
	public static boolean isDifferentDay(Date date1, Date date2) {
		return isDifferentTimeFrame(date1.getTime(), date2.getTime(), TimeFrame.PERIOD_D1);
	}
	
	public static boolean isSameDay(Date date1, Date date2) {
		return !isDifferentTimeFrame(date1.getTime(), date2.getTime(), TimeFrame.PERIOD_D1);
	}
	
	public static boolean isBeforeDay(Date date1, Date date2) {
		return isDifferentDay(date1, date2) && date1.before(date2);
	}
	
	public static boolean isAfterDay(Date date1, Date date2) {
		return isDifferentDay(date1, date2) && date1.after(date2);
	}
	
	public static boolean isDifferentMonth(Date date1, Date date2) {
		return isDifferentTimeFrame(date1.getTime(), date2.getTime(), TimeFrame.PERIOD_MN1);
	}
	
	public static boolean isSameMonth(Date date1, Date date2) {
		return !isDifferentMonth(date1, date2);
	}
	
	public static Date getNextDay(Date date) {
		long time = date.getTime();
		long nexTime = time + (24 * 60 * 60 * 1000);
		return new Date(nexTime);
	}
	
	public static boolean isDifferentTimeFrame(Date newTime, Date oldTime, TimeFrame timeFrame) {
		long timeUnit = timeFrame.value;
		Calendar newCalendar = Calendar.getInstance();
		newCalendar.setTime(newTime);
		Calendar oldCalendar = Calendar.getInstance();
		oldCalendar.setTime(oldTime);
		
		if (newCalendar.get(Calendar.YEAR) != oldCalendar.get(Calendar.YEAR))
			return true;
		else if ((timeFrame == TimeFrame.PERIOD_MN1) && (newCalendar.get(Calendar.MONTH) != oldCalendar.get(Calendar.MONTH)))
			return true;
		else if ((timeFrame == TimeFrame.PERIOD_W1) && (newCalendar.get(Calendar.WEEK_OF_YEAR) != oldCalendar.get(Calendar.WEEK_OF_YEAR)))
			return true;
		else if ((timeFrame == TimeFrame.PERIOD_D1) && (newCalendar.get(Calendar.DAY_OF_YEAR) != oldCalendar.get(Calendar.DAY_OF_YEAR)))
			return true;
		else if ((timeFrame.value < TimeFrame.PERIOD_D1.value) && ((newTime.getTime() / timeUnit) != (oldTime.getTime() / timeUnit)))
			return true;
		else return false;
	}
	
	public static boolean isDifferentTimeFrame(long newTime, long oldTime, TimeFrame timeFrame) {
		return isDifferentTimeFrame(new Date(newTime), new Date(oldTime), timeFrame);
	}
	
	public static Date formatCandleStickTime(Date time, TimeFrame timeFrame) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		
		if (timeFrame == TimeFrame.PERIOD_MN1) {
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		} else if (timeFrame == TimeFrame.PERIOD_W1) {
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		} else if (timeFrame.value < TimeFrame.PERIOD_D1.value) return new Date((time.getTime() / timeFrame.value) * timeFrame.value);
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
	public static Date getAssignedYearsAgoByDate(Date endDay, int years) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDay);
		calendar.add(Calendar.YEAR, -1 * years);
		return calendar.getTime();
	}
}
