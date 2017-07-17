package com.huatai.common.business;

import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.type.OrderSide;
import com.huatai.common.util.IdGenerator;

public class Execution extends BaseOrder {
	private static final long serialVersionUID = 1L;
	private String execId;
	private String orderId;
	private String algoOrderId;
	private String strategyId;
	private String portfolioNo;
	private String portfolioType;
	
	public Execution() {
		super();
	}
	
	public Execution(SecurityTradingIndex index, String symbol, OrderSide side, Double quantity, Double price, String orderId, String execId) {
		super(index, symbol, side, quantity, price);
		this.execId = execId;
		this.orderId = orderId;
	}
	
	@Override
	protected String generateId() {
		return IdGenerator.getInstance().getNextID() + "E";
	}
	
	public String getExecId() {
		return execId;
	}
	
	public void setExecId(String execId) {
		this.execId = execId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public String getAlgoOrderId() {
		return algoOrderId;
	}
	
	public void setAlgoOrderId(String algoOrderId) {
		this.algoOrderId = algoOrderId;
	}
	
	public String getStrategyId() {
		return strategyId;
	}
	
	public void setStrategyId(String strategyId) {
		this.strategyId = strategyId;
	}
	
	public String getPortfolioNo() {
		return portfolioNo;
	}
	
	public void setPortfolioNo(String portfolioNo) {
		this.portfolioNo = portfolioNo;
	}
	
	public String getPortfolioType() {
		return portfolioType;
	}
	
	public void setPortfolioType(String portfolioType) {
		this.portfolioType = portfolioType;
	}
	
	@Override
	public Execution clone() {
		try {
			return (Execution) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Execution [execId=");
		builder.append(execId);
		builder.append(", orderId=");
		builder.append(orderId);
		builder.append(", clOrdId=");
		builder.append(clOrdId);
		builder.append(", algoOrderId=");
		builder.append(algoOrderId);
		builder.append(", strategyId=");
		builder.append(strategyId);
		builder.append(", BaseOrder=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
