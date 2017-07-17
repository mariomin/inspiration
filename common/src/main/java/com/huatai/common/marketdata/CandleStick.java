package com.huatai.common.marketdata;

import java.util.Date;

public class CandleStick extends MarketData implements Cloneable {
	private static final long serialVersionUID = 1L;
	private double openPx;
	private double closePx;
	private double highPx;
	private double lowPx;
	private double volume;
	private double turnover;
	
	public CandleStick(String symbol) {
		super(symbol);
	}
	
	public CandleStick(String symbol, double openPx, double closePx, double highPx, double lowPx, double volume, Date time) {
		super(symbol);
		this.symbol = symbol;
		this.openPx = openPx;
		this.closePx = closePx;
		this.highPx = highPx;
		this.lowPx = lowPx;
		this.volume = volume;
		timestamp = time;
	}
	
	public CandleStick(String symbol, double openPx, double closePx, double highPx, double lowPx, double volume, Date time, double turnover) {
		super(symbol);
		this.symbol = symbol;
		this.openPx = openPx;
		this.closePx = closePx;
		this.highPx = highPx;
		this.lowPx = lowPx;
		this.volume = volume;
		timestamp = time;
		this.turnover = turnover;
	}
	
	public double getOpenPx() {
		return openPx;
	}
	
	public void setOpenPx(double openPx) {
		this.openPx = openPx;
	}
	
	public double getClosePx() {
		return closePx;
	}
	
	public void setClosePx(double closePx) {
		this.closePx = closePx;
	}
	
	public double getHighPx() {
		return highPx;
	}
	
	public void setHighPx(double highPx) {
		this.highPx = highPx;
	}
	
	public double getLowPx() {
		return lowPx;
	}
	
	public void setLowPx(double lowPx) {
		this.lowPx = lowPx;
	}
	
	public double getVolume() {
		return volume;
	}
	
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
	public double getTurnover() {
		return turnover;
	}
	
	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}
	
	@Override
	public CandleStick clone() {
		try {
			CandleStick obj = (CandleStick) super.clone();
			obj.setTimestamp(new Date(obj.getTimestamp().getTime()));
			return obj;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CandleStick [symbol=");
		sb.append(symbol);
		sb.append(", openPx=");
		sb.append(openPx);
		sb.append(", closePx=");
		sb.append(closePx);
		sb.append(", highPx=");
		sb.append(highPx);
		sb.append(", lowPx=");
		sb.append(lowPx);
		sb.append(", volume=");
		sb.append(volume);
		sb.append(", timestamp=");
		sb.append(timestamp);
		sb.append(", turnover=");
		sb.append(turnover);
		sb.append("]");
		return sb.toString();
	}
}
