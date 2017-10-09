package com.inspiration.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

public class IdGenerator {
	private static final char delimiter = '-';
	private static final String[] suffix = new String[100];
	private static final Calendar calendar = Calendar.getInstance();
	private static final IdGenerator instance = new IdGenerator();
	private static String host;
	private String prefix = "";
	private String systemId;
	private long seq;
	private long lastTime = System.currentTimeMillis();

	static {
		suffix[0] = "";
		for (int i = 1; i < suffix.length; i++) {
			suffix[i] = "0" + suffix[i - 1];
		}
	}

	private IdGenerator() {
	}

	public static IdGenerator getInstance() {
		return instance;
	}

	private static void appendTime(StringBuilder builder, long time) {
		int year, month, day, hour, minute, second, millisecond;

		synchronized (calendar) {
			calendar.setTimeInMillis(time);
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH) + 1;
			day = calendar.get(Calendar.DATE);
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			minute = calendar.get(Calendar.MINUTE);
			second = calendar.get(Calendar.SECOND);
			millisecond = calendar.get(Calendar.MILLISECOND);
		}

		builder.append(year);
		appendWithPrefix(builder, 2, month);
		appendWithPrefix(builder, 2, day);
		builder.append(delimiter);
		appendWithPrefix(builder, 2, hour);
		appendWithPrefix(builder, 2, minute);
		appendWithPrefix(builder, 2, second);
		builder.append(delimiter);
		appendWithPrefix(builder, 3, millisecond);
	}

	private static void appendWithPrefix(StringBuilder builder, int size, long seq) {
		String str = String.valueOf(seq);
		int index = size - str.length();
		builder.append(suffix[index]);
		builder.append(str);
	}

	public String getNextID() {
		StringBuilder builder = new StringBuilder(prefix);
		long time, s = 0;

		synchronized (this) {
			time = System.currentTimeMillis();
			if (time == lastTime) {
				seq++;
				s = seq;
			} else {
				lastTime = time;
				seq = 0;
			}
		}

		appendTime(builder, time);
		builder.append(delimiter);
		appendWithPrefix(builder, 7, s);

		return builder.toString();
	}

	public static String getUid() throws UnknownHostException {
		if (host == null) {
			host = InetAddress.getLocalHost().getHostName();
		}
		return host + "." + IdGenerator.getInstance().getNextID();
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}
