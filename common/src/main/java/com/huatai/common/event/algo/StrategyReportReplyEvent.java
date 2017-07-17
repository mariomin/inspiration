package com.huatai.common.event.algo;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.index.SecurityTradingIndex;

public class StrategyReportReplyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String strategyId;
	private final String clOrderId;
	private final SecurityTradingIndex securityTradingIndex;
	private final String subscribeId;
	private final String strategyReport;
	
	public StrategyReportReplyEvent(String key, String strategyId, String strategyReport, String clOrderId, SecurityTradingIndex securityTradingIndex,
			String subscribeId) {
		super(key);
		this.strategyId = strategyId;
		this.strategyReport = strategyReport;
		this.clOrderId = clOrderId;
		this.securityTradingIndex = securityTradingIndex;
		this.subscribeId = subscribeId;
	}
	
	public StrategyReportReplyEvent(String strategyId, String strategyReport) {
		this(null, strategyId, strategyReport, null, null, null);
	}
	
	public SecurityTradingIndex getSecurityTradingIndex() {
		return securityTradingIndex;
	}
	
	public String getStrategyId() {
		return strategyId;
	}
	
	public String getSubscribeId() {
		return subscribeId;
	}
	
	public String getStrategyReport() {
		return strategyReport;
	}
	
	public String getClOrderId() {
		return clOrderId;
	}
	
}
