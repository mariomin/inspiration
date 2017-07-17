package com.huatai.strategy.intraday.common;

public class TickData {
	private double asks[];
	private double bids[];
	private long time;
	
	public TickData(long time, double[] asks, double[] bids) {
		this.time = time;
		this.asks = asks;
		this.bids = bids;
	}
	
	public double[] getAsks() {
		return asks;
	}
	
	public void setAsks(double[] asks) {
		this.asks = asks;
	}
	
	public double[] getBids() {
		return bids;
	}
	
	public void setBids(double[] bids) {
		this.bids = bids;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public double getAskVol10Ave() {
		double sum = 0.0;
		for (double ask : asks) {
			sum += ask;
		}
		return sum / 10;
	}
}
