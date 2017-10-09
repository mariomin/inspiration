package com.inspiration.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimestampUtil {
	private static final SimpleDateFormat timeformat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public static Date validate(int actionday, int time) {
		String timestamp = String.valueOf(time);
		if (timestamp.length() < 9) {
			timestamp = '0' + timestamp;
		}

		try {
			synchronized (timeformat) {
				return timeformat.parse(actionday + timestamp);
			}
		} catch (ParseException e) {
		}
		return null;
	}

	public static Timestamp late(Timestamp t1, Timestamp t2) {
		if (t1 == null)
			return t2;
		if (t2 == null)
			return t1;

		return t1.before(t2) ? t2 : t1;
	}

	public static Timestamp early(Timestamp t1, Timestamp t2) {
		if (t1 == null)
			return t2;
		if (t2 == null)
			return t1;

		return t1.before(t2) ? t1 : t2;
	}

	public static Timestamp getTodayBeginning() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 1);
		return new Timestamp(calendar.getTimeInMillis());
	}

	public static Timestamp getTodayEnding() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}

	public static Date convertStringToDate(String time) {
		try {
			synchronized (timeformat) {
				return timeformat.parse(time);
			}
		} catch (Exception e) {
		}

		return null;
	}

}
