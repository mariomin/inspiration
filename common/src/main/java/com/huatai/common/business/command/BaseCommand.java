package com.huatai.common.business.command;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.huatai.common.data.DataObject;
import com.huatai.common.data.SingleFinancialObject;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.type.Currency;
import com.huatai.common.util.GsonUtil;
import com.huatai.common.util.IdGenerator;

public abstract class BaseCommand extends SingleFinancialObject {
	private static final long serialVersionUID = 1L;
	private static final Gson gson = GsonUtil.getGsonInstance();
	protected final DataObject fields = new DataObject();
	protected Timestamp transactTime;
	protected String clOrdId;
	protected Currency currency;
	protected String source;
	protected String sender;
	
	protected String tradingAccount;
	protected String securityAccount;
	
	protected BaseCommand() {
		super();
		id = IdGenerator.getInstance().getNextID();
	}
	
	protected BaseCommand(SecurityTradingIndex index) {
		super(index);
		tradingAccount = index.getTradingAccount();
		securityAccount = index.getSecurityAccount();
		id = IdGenerator.getInstance().getNextID();
	}
	
	public SecurityTradingIndex extractSecurityTradingIndex() {
		return new SecurityTradingIndex(new AssetAccountIndex(investorAccount, productAccount, assetAccount, assetAccountType), tradingAccount,
				securityAccount);
	}
	
	public Timestamp getTransactTime() {
		return transactTime;
	}
	
	public void setTransactTime(Timestamp transactTime) {
		this.transactTime = transactTime;
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
	
	public DataObject getRawFields() {
		return fields;
	}
	
	public String getFields() {
		return gson.toJson(fields);
	}
	
	public void setFields(String json) {
		DataObject object = gson.fromJson(json, DataObject.class);
		fields.putAll(object);
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
	
	public String getClOrdId() {
		return clOrdId;
	}
	
	public void setClOrdId(String clOrdId) {
		this.clOrdId = clOrdId;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
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
	
}
