package com.huatai.common.marketdata.sim;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.huatai.common.marketdata.Level;
import com.huatai.common.marketdata.Quote;
import com.huatai.common.marketdata.mdc.MDCIndex;
import com.huatai.common.marketdata.mdc.MDCQuote;
import com.huatai.common.type.QtyPrice;

public class SimQuote {
	@SerializedName("Symbol")
	private String symbol;
	
	@SerializedName("Time")
	private long time;
	
	@SerializedName("LastPx")
	private double lastPx;
	
	@SerializedName("Bids")
	private List<QtyPrice> bids;
	
	@SerializedName("Asks")
	private List<QtyPrice> asks;
	
	@SerializedName("preClosePrice")
	private double preClosePrice;
	
	@SerializedName("totalVolume")
	private double totalVolume;
	
	@SerializedName("turnover")
	private double turnover;
	
	@SerializedName("Level")
	private Level level;
	
	public SimQuote(String symbol, long time, List<QtyPrice> bids, List<QtyPrice> asks, double preClosePrice, double totalVolume, double turnover) {
		this.symbol = symbol;
		this.time = time;
		this.bids = bids;
		this.asks = asks;
		this.preClosePrice = preClosePrice;
		this.totalVolume = totalVolume;
		this.turnover = turnover;
	}
	
	public SimQuote() {}
	
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
	
	public double getLastPx() {
		return lastPx;
	}
	
	public void setLastPx(double lastPx) {
		this.lastPx = lastPx;
	}
	
	public List<QtyPrice> getBids() {
		return bids;
	}
	
	public void setBids(List<QtyPrice> bids) {
		this.bids = bids;
	}
	
	public List<QtyPrice> getAsks() {
		return asks;
	}
	
	public void setAsks(List<QtyPrice> asks) {
		this.asks = asks;
	}
	
	public double getPreClosePrice() {
		return preClosePrice;
	}
	
	public void setPreClosePrice(double preClosePrice) {
		this.preClosePrice = preClosePrice;
	}
	
	public double getTotalVolume() {
		return totalVolume;
	}
	
	public void setTotalVolume(double totalVolume) {
		this.totalVolume = totalVolume;
	}
	
	public double getTurnover() {
		return turnover;
	}
	
	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public static SimQuote getSimpleQuote(MDCQuote mdcQuote) {
		SimQuote sm = new SimQuote();
		
		sm.setSymbol(mdcQuote.getSymbol());
		sm.setTime(mdcQuote.getTimestamp().getTime());
		sm.setPreClosePrice(mdcQuote.getPreviousClosingPx());
		sm.setBids(mdcQuote.getBids());
		sm.setAsks(mdcQuote.getAsks());
		sm.setTurnover(mdcQuote.getTurnover());
		sm.setTotalVolume(mdcQuote.getTotalVolume());
		if (mdcQuote.getLevel() == null) {
			sm.setLevel(Level.full);
		} else {
			sm.setLevel(mdcQuote.getLevel());
		}
		return sm;
	}
	
	public static SimQuote getSimpleQuote(MDCIndex mdcIndex) {
		SimQuote sm = new SimQuote();
		sm.setSymbol(mdcIndex.getSymbol());
		sm.setTime(mdcIndex.getTimestamp().getTime());
		sm.setLastPx(mdcIndex.getLastPx());
		sm.setTurnover(mdcIndex.getTurnover());
		sm.setTotalVolume(mdcIndex.getTotalVolume());
		if (mdcIndex.getLevel() == null) {
			sm.setLevel(Level.full);
		} else {
			sm.setLevel(mdcIndex.getLevel());
		}
		return sm;
	}
	
	public static Quote createQuote(SimQuote sq, String id) {
		Quote quote = new Quote(sq.getSymbol(), sq.getBids(), sq.getAsks());
		quote.setLastPx(sq.getLastPx());
		quote.setTimestamp(new Date(sq.getTime()));
		quote.setPreviousClosingPx(sq.getPreClosePrice());
		quote.setTotalVolume(sq.getTotalVolume());
		quote.setTurnover(sq.getTurnover());
		quote.setLevel(sq.getLevel());
		return quote;
	}
}
