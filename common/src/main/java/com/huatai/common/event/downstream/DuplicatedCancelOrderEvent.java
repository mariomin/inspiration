package com.huatai.common.event.downstream;

import com.huatai.common.event.base.AsyncEvent;

public class DuplicatedCancelOrderEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String cancelReportNo;
	
	public DuplicatedCancelOrderEvent(String cancelReportNo) {
		super(null);
		this.cancelReportNo = cancelReportNo;
	}
	
	public String getCancelReportNo() {
		return cancelReportNo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DuplicatedCancelOrderEvent [cancelReportNo=");
		builder.append(cancelReportNo);
		builder.append("]");
		return builder.toString();
	}
	
}
