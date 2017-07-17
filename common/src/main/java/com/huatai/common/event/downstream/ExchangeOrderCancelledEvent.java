package com.huatai.common.event.downstream;

import com.huatai.common.event.base.AsyncEvent;

public class ExchangeOrderCancelledEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final double cancelQty;
	private final String reportNo;
	
	public ExchangeOrderCancelledEvent(String reportNo, double cancelQty) {
		super(null);
		this.cancelQty = cancelQty;
		this.reportNo = reportNo;
	}
	
	public double getCancelQty() {
		return cancelQty;
	}
	
	public String getReportNo() {
		return reportNo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExchangeOrderCanceledEvent [cancelQty=");
		builder.append(cancelQty);
		builder.append(", reportNo=");
		builder.append(reportNo);
		builder.append("]");
		return builder.toString();
	}
	
}
