package com.huatai.common.event.order;

import com.huatai.common.business.Order;
import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.fund.TradingFund;
import com.huatai.common.portfolio.TradingPosition;

public abstract class OrderReplyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private String origClOrdId;
	private final boolean ok;
	private final String text;
	private final String clOrdId;
	private final Order order;
	private TradingPosition tradingPosition;
	private TradingFund tradingFund;
	
	public OrderReplyEvent(String key, boolean ok, String text, String clOrdId, Order order) {
		super(key);
		this.ok = ok;
		this.text = text;
		this.clOrdId = clOrdId;
		this.order = order;
	}
	
	public OrderReplyEvent(String key, String origClOrderId, boolean ok, String text, String clOrdId, Order order) {
		this(key, ok, text, clOrdId, order);
		origClOrdId = origClOrderId;
	}
	
	public TradingPosition getTradingPosition() {
		return tradingPosition;
	}
	
	public void setTradingPosition(TradingPosition tradingPosition) {
		this.tradingPosition = tradingPosition;
	}
	
	public TradingFund getTradingFund() {
		return tradingFund;
	}
	
	public void setTradingFund(TradingFund tradingFund) {
		this.tradingFund = tradingFund;
	}
	
	public void setOrigClOrdId(String origClOrdId) {
		this.origClOrdId = origClOrdId;
	}
	
	public boolean isOk() {
		return ok;
	}
	
	public String getText() {
		return text;
	}
	
	public String getClOrdId() {
		return clOrdId;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public String getOrigClOrdId() {
		return origClOrdId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderReplyEvent [origClOrdId=");
		builder.append(origClOrdId);
		builder.append(", ok=");
		builder.append(ok);
		builder.append(", text=");
		builder.append(text);
		builder.append(", clOrdId=");
		builder.append(clOrdId);
		builder.append(", order=");
		builder.append(order);
		builder.append(", tradingPosition=");
		builder.append(tradingPosition);
		builder.append(", tradingFund=");
		builder.append(tradingFund);
		builder.append(", BaseResponse=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
