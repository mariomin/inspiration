package com.huatai.common.backtest.report;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class BenchMarkRecord {
	@SerializedName("Date")
	private Date date;
	
	@SerializedName("IndexValue")
	private double indexValue;
	
	public BenchMarkRecord(Date date, double indexValue) {
		this.date = date;
		this.indexValue = indexValue;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public double getIndexValue() {
		return indexValue;
	}
	
	public void setIndexValue(double indexValue) {
		this.indexValue = indexValue;
	}
	
}
