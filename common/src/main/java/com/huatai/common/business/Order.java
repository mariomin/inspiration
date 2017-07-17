package com.huatai.common.business;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.huatai.common.data.DataObject;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.type.OrdStatus;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;
import com.huatai.common.type.SecurityType;
import com.huatai.common.util.GsonUtil;

public abstract class Order extends BaseOrder {
	private static final long serialVersionUID = 1L;
	private static final Gson gson = GsonUtil.getGsonInstance();
	protected DataObject fields = new DataObject();
	
	protected SecurityType securityType;
	protected OrdStatus ordStatus = OrdStatus.PENDING_NEW;
	protected Double cumQty = 0.0d;
	protected Double avgPx = 0.0d;
	protected Timestamp transactTime;
	protected String affiliatedListId;
	
	/*
	 * 该字段仅在撤单时使用，撤单时该字段表示撤单命令的clOrdId。<P>
	 * 注意该字段的含义与FIX协议中的OrigClOrdID含义刚好是相反的，因此在返回撤单响应时，需要将该字段赋值给ClOrdID，
	 * 而将该类中的clOrdId赋值给OrigClOrdID
	 */
	protected String origClOrdId;
	protected Double lastQty;
	protected Double lastPx;
	protected String branchNo;
	protected String branchCode;
	protected String pbu;
	
	protected Character timeInForce;
	protected Integer maxPriceLevels;
	protected Double minQty;
	
	protected OrderType orderType;
	
	protected String source;
	protected String sender;
	
	protected String portfolioNo;
	protected String portfolioType;
	
	protected Order() {
		super();
	}
	
	public Order(SecurityTradingIndex securityTradingIndex, String symbol, OrderSide side, Double quantity, Double price) {
		super(securityTradingIndex, symbol, side, quantity, price);
	}
	
	public DataObject getRawFields() {
		return fields;
	}
	
	public void setRawFields(DataObject fields) {
		this.fields = fields;
	}
	
	protected String getFields() {
		return gson.toJson(fields);
	}
	
	public void setFields(String json) {
		DataObject object = gson.fromJson(json, DataObject.class);
		fields.putAll(object);
	}
	
	public String getOrigClOrdId() {
		return origClOrdId;
	}
	
	public void setOrigClOrdId(String origClOrdId) {
		this.origClOrdId = origClOrdId;
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
	
	public SecurityType getSecurityType() {
		return securityType;
	}
	
	public void setSecurityType(SecurityType securityType) {
		this.securityType = securityType;
	}
	
	public void setLastQty(Double lastQty) {
		this.lastQty = lastQty;
	}
	
	public Double getLastQty() {
		return lastQty;
	}
	
	public void setLastPx(Double lastPx) {
		this.lastPx = lastPx;
	}
	
	public Double getLastPx() {
		return lastPx;
	}
	
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	
	public OrderType getOrderType() {
		return orderType;
	}
	
	public Double getRemainingQty() {
		return quantity - cumQty;
	}
	
	public void setOrdStatus(OrdStatus ordStatus) {
		this.ordStatus = ordStatus;
	}
	
	public OrdStatus getOrdStatus() {
		return ordStatus;
	}
	
	public void setCumQty(Double cumQty) {
		this.cumQty = cumQty;
	}
	
	public Double getCumQty() {
		return cumQty;
	}
	
	public void setAvgPx(Double avgPx) {
		this.avgPx = avgPx;
	}
	
	public Double getAvgPx() {
		return avgPx;
	}
	
	public String getBranchNo() {
		return branchNo;
	}
	
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	
	public String getBranchCode() {
		return branchCode;
	}
	
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	public String getPbu() {
		return pbu;
	}
	
	public void setPbu(String pbu) {
		this.pbu = pbu;
	}
	
	public Character getTimeInForce() {
		return timeInForce;
	}
	
	public void setTimeInForce(Character timeInForce) {
		this.timeInForce = timeInForce;
	}
	
	public Integer getMaxPriceLevels() {
		return maxPriceLevels;
	}
	
	public void setMaxPriceLevels(Integer maxPriceLevels) {
		this.maxPriceLevels = maxPriceLevels;
	}
	
	public Double getMinQty() {
		return minQty;
	}
	
	public void setMinQty(Double minQty) {
		this.minQty = minQty;
	}
	
	public Object putField(String name, Object value) {
		return fields.put(name, value);
	}
	
	public <T> T getField(Class<T> t, String fieldName) {
		return fields.get(t, fieldName);
	}
	
	public <T> T getField(T e, Class<T> t, String fieldName) {
		return fields.get(e, t, fieldName);
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public Timestamp getTransactTime() {
		return transactTime;
	}
	
	public void setTransactTime(Timestamp transactTime) {
		this.transactTime = transactTime;
	}
	
	public String getAffiliatedListId() {
		return affiliatedListId;
	}
	
	public void setAffiliatedListId(String affiliatedListId) {
		this.affiliatedListId = affiliatedListId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [fields=");
		builder.append(fields);
		builder.append(", ordStatus=");
		builder.append(ordStatus);
		builder.append(", cumQty=");
		builder.append(cumQty);
		builder.append(", avgPx=");
		builder.append(avgPx);
		builder.append(", clOrdId=");
		builder.append(clOrdId);
		builder.append(", transactTime=");
		builder.append(transactTime);
		builder.append(", affiliatedListId=");
		builder.append(affiliatedListId);
		builder.append(", origClOrdId=");
		builder.append(origClOrdId);
		builder.append(", lastQty=");
		builder.append(lastQty);
		builder.append(", lastPx=");
		builder.append(lastPx);
		builder.append(", branchNo=");
		builder.append(branchNo);
		builder.append(", branchCode=");
		builder.append(branchCode);
		builder.append(", pbu=");
		builder.append(pbu);
		builder.append(", timeInForce=");
		builder.append(timeInForce);
		builder.append(", maxPriceLevels=");
		builder.append(maxPriceLevels);
		builder.append(", minQty=");
		builder.append(minQty);
		builder.append(", orderType=");
		builder.append(orderType);
		builder.append(", source=");
		builder.append(source);
		builder.append(", sender=");
		builder.append(sender);
		builder.append(", BaseOrder()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
