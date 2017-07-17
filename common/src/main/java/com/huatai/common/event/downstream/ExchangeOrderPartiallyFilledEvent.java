package com.huatai.common.event.downstream;

import com.huatai.common.event.base.AsyncEvent;

public class ExchangeOrderPartiallyFilledEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String reportNo;
	private final String execID;
	private final double lastQty;
	private final double price;
	
	public ExchangeOrderPartiallyFilledEvent(String reportNo, String execID, double lastQty, double price) {
		super(null);
		this.reportNo = reportNo;
		this.execID = execID;
		this.lastQty = lastQty;
		this.price = price;
	}
	
	public String getExecID() {
		return execID;
	}
	
	public double getLastQty() {
		return lastQty;
	}
	
	public double getPrice() {
		return price;
	}
	
	public String getReportNo() {
		return reportNo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExchangeOrderPartiallyFilledEvent [reportNo=");
		builder.append(reportNo);
		builder.append(", execID=");
		builder.append(execID);
		builder.append(", lastQty=");
		builder.append(lastQty);
		builder.append(", price=");
		builder.append(price);
		builder.append("]");
		return builder.toString();
	}
	
}
