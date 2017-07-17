package com.huatai.common.marketdata;

import java.util.ArrayList;
import java.util.List;

import com.huatai.common.type.QtyPrice;
import com.huatai.common.type.SecurityType;

public class Quote extends MarketData {
	private static final long serialVersionUID = 1L;
	protected Double bid;
	protected Double ask;
	protected Double bidVol;
	protected Double askVol;
	protected Double lastPx;
	protected Double lastVol;
	protected Double highPx;
	protected Double lowPx;
	protected Double highLimitedPx;
	protected Double lowLimitedPx;
	protected Double openPx;
	protected Double previousClosingPx;
	protected Double totalVolume;
	protected Double turnover;
	protected Long trades;
	public List<QtyPrice> bids;
	public List<QtyPrice> asks;
	private Double previousOpenInterest;
	private Double previousSettlementPx;
	private Double openInterest;
	private Double settlementPx;
	protected SecurityType securityType;
	private Level level;
	
	public Quote(String symbol) {
		super(symbol);
	}
	
	public Quote(String symbol, List<QtyPrice> bids, List<QtyPrice> asks) {
		this(symbol);
		this.bids = bids;
		this.asks = asks;
	}
	
	public Double getBid() {
		return bid;
	}
	
	public void setBid(Double bid) {
		this.bid = bid;
	}
	
	public Double getAsk() {
		return ask;
	}
	
	public void setAsk(Double ask) {
		this.ask = ask;
	}
	
	public Double getBidVol() {
		return bidVol;
	}
	
	public void setBidVol(Double bidVol) {
		this.bidVol = bidVol;
	}
	
	public Double getAskVol() {
		return askVol;
	}
	
	public void setAskVol(Double askVol) {
		this.askVol = askVol;
	}
	
	public Double getLastPx() {
		return lastPx;
	}
	
	public void setLastPx(Double lastPx) {
		this.lastPx = lastPx;
	}
	
	public Double getLastVol() {
		return lastVol;
	}
	
	public void setLastVol(Double lastVol) {
		this.lastVol = lastVol;
	}
	
	public Double getHighPx() {
		return highPx;
	}
	
	public void setHighPx(Double highPx) {
		this.highPx = highPx;
	}
	
	public Double getLowPx() {
		return lowPx;
	}
	
	public void setLowPx(Double lowPx) {
		this.lowPx = lowPx;
	}
	
	public Double getHighLimitedPx() {
		return highLimitedPx;
	}
	
	public void setHighLimitedPx(Double highLimitedPx) {
		this.highLimitedPx = highLimitedPx;
	}
	
	public Double getLowLimitedPx() {
		return lowLimitedPx;
	}
	
	public void setLowLimitedPx(Double lowLimitedPx) {
		this.lowLimitedPx = lowLimitedPx;
	}
	
	public Double getOpenPx() {
		return openPx;
	}
	
	public void setOpenPx(Double openPx) {
		this.openPx = openPx;
	}
	
	public Double getPreviousClosingPx() {
		return previousClosingPx;
	}
	
	public void setPreviousClosingPx(Double previousClosingPx) {
		this.previousClosingPx = previousClosingPx;
	}
	
	public List<QtyPrice> getBids() {
		return bids;
	}
	
	public List<QtyPrice> getAsks() {
		return asks;
	}
	
	public Double getTotalVolume() {
		return totalVolume;
	}
	
	public void setTotalVolume(Double totalVolume) {
		this.totalVolume = totalVolume;
	}
	
	public Double getTurnover() {
		return turnover;
	}
	
	public void setTurnover(Double turnover) {
		this.turnover = turnover;
	}
	
	public Long getTrades() {
		return trades;
	}
	
	public void setTrades(Long trades) {
		this.trades = trades;
	}
	
	public SecurityType getSecurityType() {
		return securityType;
	}
	
	public void setSecurityType(SecurityType securityType) {
		this.securityType = securityType;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Quote [symbol=").append(symbol);
		sb.append(", id=").append(id);
		sb.append(", bid=").append(bid);
		sb.append(", bidVol=").append(bidVol);
		sb.append(", ask=").append(ask);
		sb.append(", askVol=").append(askVol);
		sb.append(", lastPx=").append(lastPx);
		sb.append(", lastVol=").append(lastVol);
		sb.append(", highPx=").append(highPx);
		sb.append(", lowPx=").append(lowPx);
		sb.append(", highLimitedPx=").append(highLimitedPx);
		sb.append(", lowLimitedPx=").append(lowLimitedPx);
		sb.append(", openPx=").append(openPx);
		sb.append(", previousClosingPx=").append(previousClosingPx);
		sb.append(", totalVolume=").append(totalVolume);
		sb.append(", turnover=").append(turnover);
		sb.append(", trades=").append(trades);
		sb.append(", bids=").append(bids);
		sb.append(", asks=").append(asks);
		sb.append("]");
		return sb.toString();
	}
	
	public Quote clone(Level level) { // deep copy
		Quote obj;
		try {
			obj = (Quote) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
		
		obj.bids = new ArrayList<QtyPrice>();
		obj.asks = new ArrayList<QtyPrice>();
		
		int len = (level == null) ? Math.max(bids.size(), asks.size()) : level.getValue();
		for (int i = 0; i < len; i++) {
			if (i < bids.size()) {
				obj.bids.add(bids.get(i));
			}
			if (i < asks.size()) {
				obj.asks.add(asks.get(i));
			}
		}
		return obj;
	}
	
	public Double getPreviousOpenInterest() {
		return previousOpenInterest;
	}
	
	public void setPreviousOpenInterest(Double previousOpenInterest) {
		this.previousOpenInterest = previousOpenInterest;
	}
	
	public Double getPreviousSettlementPx() {
		return previousSettlementPx;
	}
	
	public void setPreviousSettlementPx(Double previousSettlementPx) {
		this.previousSettlementPx = previousSettlementPx;
	}
	
	public Double getOpenInterest() {
		return openInterest;
	}
	
	public void setOpenInterest(Double openInterest) {
		this.openInterest = openInterest;
	}
	
	public Double getSettlementPx() {
		return settlementPx;
	}
	
	public void setSettlementPx(Double settlementPx) {
		this.settlementPx = settlementPx;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
}
