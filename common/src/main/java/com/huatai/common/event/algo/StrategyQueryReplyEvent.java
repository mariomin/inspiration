package com.huatai.common.event.algo;

import java.util.List;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.strategy.StrategyQueryRecord;

public class StrategyQueryReplyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String requestID;
	private final List<StrategyQueryRecord> records;
	
	public StrategyQueryReplyEvent(String key, String requestID, List<StrategyQueryRecord> records) {
		super(key);
		this.requestID = requestID;
		this.records = records;
	}
	
	public String getRequestID() {
		return requestID;
	}
	
	public List<StrategyQueryRecord> getRecords() {
		return records;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StrategyQueryReplyEvent [requestID=");
		builder.append(requestID);
		builder.append(", records=");
		builder.append(records);
		builder.append("]");
		return builder.toString();
	}
}
