package com.huatai.common.event.downstream;

import com.huatai.common.event.base.AsyncEvent;

public class CancelExchangeOrderRejectEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String reportNo;
	private final String reason;
	
	public CancelExchangeOrderRejectEvent(String reportNo, String reason) {
		super(null);
		this.reportNo = reportNo;
		this.reason = reason;
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
		builder.append("CancelExchangeOrderRejectEvent [reportNo=");
		builder.append(reportNo);
		builder.append(", reason=");
		builder.append(reason);
		builder.append("]");
		return builder.toString();
	}
	
}
