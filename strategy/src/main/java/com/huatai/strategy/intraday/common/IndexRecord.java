package com.huatai.strategy.intraday.common;

import com.google.gson.annotations.SerializedName;

public class IndexRecord {
	@SerializedName("BoolValue")
	private boolean boolValue;
	
	@SerializedName("DoubleValue")
	private double doubleValue;
	
	public IndexRecord(boolean boolValue, double doubleValue) {
		setBoolValue(boolValue);
		setDoubleValue(doubleValue);
	}
	
	public boolean getBoolValue() {
		return boolValue;
	}
	
	public void setBoolValue(boolean boolValue) {
		this.boolValue = boolValue;
	}
	
	public double getDoubleValue() {
		return doubleValue;
	}
	
	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}
	
	@Override
	public String toString() {
		return "IndexRecord [boolValue=" + boolValue + ", doubleValue=" + doubleValue + "]";
	}
	
}