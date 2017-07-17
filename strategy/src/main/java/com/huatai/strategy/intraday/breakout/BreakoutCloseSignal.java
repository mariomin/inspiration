package com.huatai.strategy.intraday.breakout;

import java.util.Date;

import com.huatai.common.util.DecimalUtil;

public class BreakoutCloseSignal {
	private double closeMaxDrop;
	private String symbol;
	private double emaAvgPrice;
	private double maxDrop;
	private double emaPressureRatio;
	private Date date;
	private long time;
	
	public boolean isCloseSignal() {
		return ((symbol != null) && !DecimalUtil.isZero(maxDrop)
				&& (DecimalUtil.equalGreaterThan(maxDrop, closeMaxDrop) || DecimalUtil.equalLessThan(emaPressureRatio, 0d)));
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
	
	public double getEmaAvgPrice() {
		return emaAvgPrice;
	}
	
	public void setEmaAvgPrice(double emaAvgPrice) {
		this.emaAvgPrice = emaAvgPrice;
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
