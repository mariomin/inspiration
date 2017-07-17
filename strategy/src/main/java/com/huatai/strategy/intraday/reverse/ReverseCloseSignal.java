package com.huatai.strategy.intraday.reverse;

import java.util.Date;

import com.huatai.common.util.DecimalUtil;

public class ReverseCloseSignal {
	private double closeMaxDrop;
	private String symbol;
	private double emaOrderPressure;
	private double maxDrop;
	private double emaPressureRatio;
	private Date date;
	private long time;
	
	public boolean isCloseSignal(double openPrice, double ask1) {
		boolean profitable = DecimalUtil.greaterThan(ask1 - 0.01, openPrice * (1 + 0.001));
		return ((symbol != null) && !DecimalUtil.isZero(maxDrop)
				&& (profitable || DecimalUtil.equalLessThan(emaOrderPressure, 0) || DecimalUtil.equalGreaterThan(maxDrop, closeMaxDrop))
				&& (!profitable || DecimalUtil.equalLessThan(emaPressureRatio, 0)));
	}
	
	public double getCloseMaxDrop() {
		return closeMaxDrop;
	}
	
	public void setCloseMaxDrop(double closeMaxDrop) {
		this.closeMaxDrop = closeMaxDrop;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public double getEmaOrderPressure() {
		return emaOrderPressure;
	}
	
	public void setEmaOrderPressure(double emaOrderPressure) {
		this.emaOrderPressure = emaOrderPressure;
	}
	
	public double getMaxDrop() {
		return maxDrop;
	}
	
	public void setMaxDrop(double maxDrop) {
		this.maxDrop = maxDrop;
	}
	
	public double getEmaPressureRatio() {
		return emaPressureRatio;
	}
	
	public void setEmaPressureRatio(double emaPressureRatio) {
		this.emaPressureRatio = emaPressureRatio;
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
	
}
