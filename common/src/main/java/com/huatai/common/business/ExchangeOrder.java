package com.huatai.common.business;

import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.type.OrdStatus;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;
import com.huatai.common.util.DecimalUtil;
import com.huatai.common.util.IdGenerator;
import com.huatai.exchange.common.business.Order.STATUS;

public class ExchangeOrder extends Order {
	private static final long serialVersionUID = 1L;
	private String algoOrderId;
	private String strategyId;
	
	private double cumAmount; // 累计交易金额(quantity * price)
	private double freezedCash; // 预冻结金额
	private double cumNetMoney; // 累计交易金额(含佣金和其他交易相关费用)
	private double cancelledQty;
	
	private double commissionCost;
	private double stampDutyCost;
	
	// 申报编号
	private String reportNo;
	
	// 用于撤单请求的申报编号
	private String cancelReportNo;
	
	@Override
	protected String generateId() {
		return IdGenerator.getInstance().getNextID() + "E";
	}
	
	public ExchangeOrder() {
		super();
	}
	
	public ExchangeOrder(SecurityTradingIndex securityTradingIndex, String symbol, OrderSide side, Double quantity, Double price, OrderType orderType,
			String clOrdId) {
		super(securityTradingIndex, symbol, side, quantity, price);
		this.symbol = symbol;
		this.orderType = orderType;
		this.clOrdId = clOrdId;
	}
	
	public String getCancelReportNo() {
		return cancelReportNo;
	}
	
	public void setCancelReportNo(String cancelReportNo) {
		this.cancelReportNo = cancelReportNo;
	}
	
	public String getReportNo() {
		return reportNo;
	}
	
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	
	public boolean completed() {
		return DecimalUtil.isZero(quantity - cumQty - cancelledQty);
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
	
	public double getCumAmount() {
		return cumAmount;
	}
	
	public void setCumAmount(double cumAmount) {
		this.cumAmount = cumAmount;
	}
	
	public double getFreezedCash() {
		return freezedCash;
	}
	
	public void setFreezedCash(double freezedCash) {
		this.freezedCash = freezedCash;
	}
	
	public double getCumNetMoney() {
		return cumNetMoney;
	}
	
	public void setCumNetMoney(double cumNetMoney) {
		this.cumNetMoney = cumNetMoney;
	}
	
	public double getCancelledQty() {
		return cancelledQty;
	}
	
	public void setCancelledQty(double cancelledQty) {
		this.cancelledQty = cancelledQty;
	}
	
	public double getCommissionCost() {
		return commissionCost;
	}
	
	public void setCommissionCost(double commissionCost) {
		this.commissionCost = commissionCost;
	}
	
	public double getStampDutyCost() {
		return stampDutyCost;
	}
	
	public void setStampDutyCost(double stampDutyCost) {
		this.stampDutyCost = stampDutyCost;
	}
	
	public void update(com.huatai.exchange.common.business.Order order) {
		STATUS status = order.getStatus();
		if (status == STATUS.NEW) {
			setOrdStatus(OrdStatus.NEW);
		} else if (status == STATUS.CANCELLED) {
			setOrdStatus(OrdStatus.CANCELED);
		} else if (status == STATUS.DONE) {
			setOrdStatus(OrdStatus.DONE_FOR_DAY);
		} else if (status == STATUS.FILLING) {
			setOrdStatus(OrdStatus.FILLED);
		} else if (status == STATUS.REJECTED) {
			setOrdStatus(OrdStatus.REJECTED);
		}
	}
	
	@Override
	public ExchangeOrder clone() {
		try {
			return (ExchangeOrder) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExchangeOrder [algoOrderId=");
		builder.append(algoOrderId);
		builder.append(", strategyId=");
		builder.append(strategyId);
		builder.append(", cumAmount=");
		builder.append(cumAmount);
		builder.append(", freezedCash=");
		builder.append(freezedCash);
		builder.append(", cumNetMoney=");
		builder.append(cumNetMoney);
		builder.append(", cancelledQty=");
		builder.append(cancelledQty);
		builder.append(", AbstractOrder=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
