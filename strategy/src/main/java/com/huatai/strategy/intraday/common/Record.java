package com.huatai.strategy.intraday.common;

import java.util.Date;
import java.util.List;

public class Record {
	private Date date;
	private long time;
	private double value;
	private double tradeValue;
	
	public Record() {}
	
	public Record(Date date, long time, double value, double tradeValue) {
		this(date, time, value);
		setTradeValue(tradeValue);
	}
	
	public Record(Date date, long time, double value) {
		setDate(date);
		setTime(time);
		setValue(value);
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public static int getStartTimeIndex(List<Record> list, long startTime) {
		if ((list == null) || list.isEmpty()) return -1;
		
		int low = 0;
		int high = list.size() - 1;
		
		while (low <= high) {
			int middle = (low + high) / 2;
			
			if (startTime == list.get(middle).getTime())
				return middle;
			else if (startTime < list.get(middle).getTime()) {
				high = middle - 1;
			} else {
				low = middle + 1;
			}
		}
		
		return -1;
	}
	
	public double getTradeValue() {
		return tradeValue;
	}
	
	public void setTradeValue(double tradeValue) {
		this.tradeValue = tradeValue;
	}
	
}
