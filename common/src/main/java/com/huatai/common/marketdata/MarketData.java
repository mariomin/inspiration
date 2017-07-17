package com.huatai.common.marketdata;

import java.util.Date;

import com.huatai.common.data.SingleFinancialData;

public abstract class MarketData extends SingleFinancialData {
	private static final long serialVersionUID = 1L;
	protected Date timestamp;
	protected Date timesent;
	protected Integer status;
	
	public MarketData(String symbol) {
		super(symbol);
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public Date getTimeSent() {
		return timesent;
	}
	
	public void setTimeSent(Date timeSent) {
		timesent = timeSent;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
