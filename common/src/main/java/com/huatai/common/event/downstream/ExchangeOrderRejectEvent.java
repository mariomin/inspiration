package com.huatai.common.event.downstream;

import com.huatai.common.event.base.AsyncEvent;

public class ExchangeOrderRejectEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String reportNo;
	private final String reason;
	
	public ExchangeOrderRejectEvent(String reportNo, String reason) {
		super(null);
		this.reason = reason;
		this.reportNo = reportNo;
	}
	
	public String getReportNo() {
		return reportNo;
	}
	
	public String getReason() {
		return reason;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExchangeOrderRejectEvent [reportNo=");
		builder.append(reportNo);
		builder.append(", reason=");
		builder.append(reason);
		builder.append("]");
		return builder.toString();
	}
	
}
