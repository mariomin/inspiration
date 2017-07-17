package com.huatai.common.marketsession;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import com.huatai.common.type.MarketSessionType;
import com.huatai.common.util.TimeUtil;

public class MarketSessionTime implements Serializable {
	private static final long serialVersionUID = 1L;
	private static String timeFormat = "HH:mm:ss";
	public final MarketSessionType session;
	public final Date start;
	public final Date end;
	public final boolean tradeTime;
	
	public MarketSessionTime(String session, String start, String end, boolean tradeTime) throws ParseException {
		this.session = MarketSessionType.valueOf(session);
		this.start = TimeUtil.parseTime(timeFormat, start);
		this.end = TimeUtil.parseTime(timeFormat, end);
		this.tradeTime = tradeTime;
	}
	
	public static String getTimeFormat() {
		return timeFormat;
	}
	
	public static void setTimeFormat(String timeFormat) {
		MarketSessionTime.timeFormat = timeFormat;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MarketSessionTime [session=");
		sb.append(session);
		sb.append(", start=");
		sb.append(start);
		sb.append(", end=");
		sb.append(end);
		sb.append("]");
		return sb.toString();
	}
	
}
