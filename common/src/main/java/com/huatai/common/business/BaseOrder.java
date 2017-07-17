package com.huatai.common.business;

import com.huatai.common.data.SingleFinancialObject;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.type.Currency;
import com.huatai.common.type.OrderSide;

public abstract class BaseOrder extends SingleFinancialObject {
	private static final long serialVersionUID = 1L;
	protected String clOrdId;
	protected OrderSide orderSide;
	protected Double quantity;
	protected Double price;
	protected Currency currency;
	protected String tradingAccount;
	protected String securityAccount;
	
	protected abstract String generateId();
	
	protected BaseOrder() {
		super();
	}
	
	protected BaseOrder(SecurityTradingIndex securityTradingIndex, String symbol, OrderSide orderSide, Double quantity, Double price) {
		super(securityTradingIndex);
		this.symbol = symbol;
		tradingAccount = securityTradingIndex.getTradingAccount();
		securityAccount = securityTradingIndex.getSecurityAccount();
		this.orderSide = orderSide;
		this.quantity = quantity;
		this.price = price;
		id = generateId();
	}
	
	public SecurityTradingIndex extractSecurityTradingIndex() {
		return new SecurityTradingIndex(new AssetAccountIndex(investorAccount, productAccount, assetAccount, assetAccountType), tradingAccount,
				securityAccount);
	}
	
	public String getClOrdId() {
		return clOrdId;
	}
	
	public void setClOrdId(String clOrdId) {
		this.clOrdId = clOrdId;
	}
	
	public void setOrderSide(OrderSide orderSide) {
		this.orderSide = orderSide;
	}
	
	public OrderSide getOrderSide() {
		return orderSide;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	public Double getQuantity() {
		return quantity;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public String getTradingAccount() {
		return tradingAccount;
	}
	
	public void setTradingAccount(String tradingAccount) {
		this.tradingAccount = tradingAccount;
	}
	
	public String getSecurityAccount() {
		return securityAccount;
	}
	
	public void setSecurityAccount(String securityAccount) {
		this.securityAccount = securityAccount;
	}
	
}
