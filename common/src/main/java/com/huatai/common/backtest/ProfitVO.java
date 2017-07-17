package com.huatai.common.backtest;

public class ProfitVO {
	private String date;
	private double value;
	private double volume;
	
	public ProfitVO(String date, double value, double volume) {
		this.date = date;
		this.value = value;
		this.volume = volume;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public double getVolume() {
		return volume;
	}
	
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
}