package com.huatai.strategy.intraday.common;

import java.util.Date;

public class MarketAtCloseSignal {
	private boolean marketAtClose;
	private Date date;
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public boolean isMarketAtClose() {
		return marketAtClose;
	}
	
	public void setMarketAtClose(boolean marketAtClose) {
		this.marketAtClose = marketAtClose;
	}
}
