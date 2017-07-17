package com.huatai.common.downstream;

import com.huatai.common.type.ExchangeType;

/**
 *
 * @author 010328
 *
 */
public class DownStreamIndex {
	private ExchangeType exchangeType;
	private String exchangeId;
	private long reportIndex;
	
	// 仅上交所使用该字段
	private long ordwth2Index;
	
	public DownStreamIndex(ExchangeType exchangeType, String exchangeId) {
		this.exchangeType = exchangeType;
		this.exchangeId = exchangeId;
	}
	
	public ExchangeType getExchangeType() {
		return exchangeType;
	}
	
	public void setExchangeType(ExchangeType exchangeType) {
		this.exchangeType = exchangeType;
	}
	
	public String getExchangeId() {
		return exchangeId;
	}
	
	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}
	
	public long getReportIndex() {
		return reportIndex;
	}
	
	public void setReportIndex(long reportIndex) {
		this.reportIndex = reportIndex;
	}
	
	public long getOrdwth2Index() {
		if (ExchangeType.SH != exchangeType) throw new RuntimeException("ordwth2Index is restricted to SH exchange");
		
		return ordwth2Index;
	}
	
	public void setOrdwth2Index(long ordwth2Index) {
		this.ordwth2Index = ordwth2Index;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DownStreamIndex [exchangeType=");
		builder.append(exchangeType);
		builder.append(", exchangeId=");
		builder.append(exchangeId);
		builder.append(", reportIndex=");
		builder.append(reportIndex);
		builder.append(", ordwth2Index=");
		builder.append(ordwth2Index);
		builder.append("]");
		return builder.toString();
	}
	
}
