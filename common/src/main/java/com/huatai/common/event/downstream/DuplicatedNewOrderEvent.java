package com.huatai.common.event.downstream;

import com.huatai.common.event.base.AsyncEvent;

public class DuplicatedNewOrderEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String reportNo;
	
	public DuplicatedNewOrderEvent(String reportNo) {
		super(null);
		this.reportNo = reportNo;
	}
	
	public String getReportNo() {
		return reportNo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DuplicatedNewOrderEvent [reportNo=");
		builder.append(reportNo);
		builder.append("]");
		return builder.toString();
	}
	
}
