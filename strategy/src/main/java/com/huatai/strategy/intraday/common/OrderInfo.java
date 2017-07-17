package com.huatai.strategy.intraday.common;

import com.huatai.common.type.OrdStatus;
import com.huatai.common.type.OrderSide;

public class OrderInfo {
	private final String clOrdId;
	private final double quantity;
	private final long transactionTime;
	private final OrderSide side;
	private int allowedTicks;
	private OrdStatus orderStatus;
	
	public OrderInfo(String clOrdId, double quantity, long transactionTime, OrderSide side) {
		this.clOrdId = clOrdId;
		this.quantity = quantity;
		this.transactionTime = transactionTime;
		this.side = side;
	}
	
	public String getClOrdId() {
		return clOrdId;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public long getTransactionTime() {
		return transactionTime;
	}
	
	public OrderSide getSide() {
		return side;
	}
	
	public int getAllowedTicks() {
		return allowedTicks;
	}
	
	public void setAllowedTicks(int allowedTicks) {
		this.allowedTicks = allowedTicks;
	}
	
	public OrdStatus getOrderStatus() {
		return orderStatus;
	}
	
	public void setOrderStatus(OrdStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
