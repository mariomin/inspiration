package com.huatai.common.event.algo;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.data.DataObject;
import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.fund.TradingFund;
import com.huatai.common.portfolio.TradingPosition;
import com.huatai.common.type.ExecType;

public class AlgoExecutionReportEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final ExecType execType;
	private final String execID;
	private final ExchangeOrder order;
	private final TradingPosition tradingPosition;
	private final TradingFund tradingFund;
	private final Double canceledQty;
	private final DataObject dataObject;
	
	public AlgoExecutionReportEvent(String key, ExecType execType, String execID, ExchangeOrder order, TradingFund tradingFund,
			TradingPosition tradingPosition, DataObject dataObject, Double canceledQty) {
		super(key);
		this.execType = execType;
		this.execID = execID;
		this.order = order;
		this.tradingFund = tradingFund;
		this.tradingPosition = tradingPosition;
		this.dataObject = dataObject;
		this.canceledQty = canceledQty;
	}
	
	public ExecType getExecType() {
		return execType;
	}
	
	public String getExecID() {
		return execID;
	}
	
	public ExchangeOrder getOrder() {
		return order;
	}
	
	public TradingPosition getTradingPosition() {
		return tradingPosition;
	}
	
	public TradingFund getTradingFund() {
		return tradingFund;
	}
	
	public Double getCanceledQty() {
		return canceledQty;
	}
	
	public DataObject getDataObject() {
		return dataObject;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AlgoExecutionReportEvent [execType=");
		builder.append(execType);
		builder.append(", execID=");
		builder.append(execID);
		builder.append(", order=");
		builder.append(order);
		builder.append(", tradingPosition=");
		builder.append(tradingPosition);
		builder.append(", tradingFund=");
		builder.append(tradingFund);
		builder.append(", canceledQty=");
		builder.append(canceledQty);
		builder.append(", dataObject=");
		builder.append(dataObject);
		builder.append("]");
		return builder.toString();
	}
	
}
