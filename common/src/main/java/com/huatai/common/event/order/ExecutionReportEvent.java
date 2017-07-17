package com.huatai.common.event.order;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.data.DataObject;
import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.fund.TradingFund;
import com.huatai.common.portfolio.TradingPosition;
import com.huatai.common.type.ExecType;

public class ExecutionReportEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final ExecType execType;
	private final String execID;
	private final ExchangeOrder order;
	private final TradingPosition tradingPosition;
	private final TradingFund tradingFund;
	private final Double canceledQty;
	private final DataObject dataObject;
	
	public ExecutionReportEvent(String key, ExecType execType, ExchangeOrder order, TradingFund tradingFund, TradingPosition tradingPosition) {
		this(key, execType, null, order, tradingFund, tradingPosition, null, null);
	}
	
	public ExecutionReportEvent(String key, ExecType execType, String execID, ExchangeOrder order, TradingFund tradingFund,
			TradingPosition tradingPosition, DataObject dataObject) {
		this(key, execType, execID, order, tradingFund, tradingPosition, dataObject, null);
	}
	
	public ExecutionReportEvent(String key, ExecType execType, String execID, ExchangeOrder order, TradingFund tradingFund,
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
		builder.append("ExecutionReportEvent [execType=");
		builder.append(execType);
		builder.append(", order=");
		builder.append(order);
		builder.append(", tradingPosition=");
		builder.append(tradingPosition);
		builder.append(", tradingFund=");
		builder.append(tradingFund);
		builder.append(", canceledQty=");
		builder.append(canceledQty);
		builder.append(", BaseResponse=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
