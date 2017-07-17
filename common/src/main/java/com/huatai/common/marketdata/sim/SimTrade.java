package com.huatai.common.marketdata.sim;

import java.util.Date;

import com.huatai.common.marketdata.Trade;
import com.huatai.common.marketdata.mdc.MDCTrade;

public class SimTrade {
	private String symbol;
	private long time;
	private double price;
	private double quantity;
	
	public SimTrade() {}
	
	public SimTrade(String symbol, long time, double price, double quantity) {
		super();
		this.symbol = symbol;
		this.time = time;
		this.price = price;
		this.quantity = quantity;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public static SimTrade getSimpleTrade(MDCTrade mdcTrade) {
		SimTrade st = new SimTrade();
		st.setSymbol(mdcTrade.getSymbol());
		st.setTime(mdcTrade.getTimestamp().getTime());
		
		st.setPrice(mdcTrade.getPrice());
		st.setQuantity(mdcTrade.getQuantity());
		
		return st;
	}
	
	public static Trade createTrade(SimTrade st) {
		Trade trade = new Trade(st.getSymbol());
		trade.setTimestamp(new Date(st.getTime()));
		trade.setSide(66);
		trade.setPrice(st.getPrice());
		trade.setQuantity(st.getQuantity());
		trade.setTurnover(10d);
		return trade;
	}
	
}
