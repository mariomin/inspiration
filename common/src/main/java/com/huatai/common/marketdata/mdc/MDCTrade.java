package com.huatai.common.marketdata.mdc;

import com.huatai.common.marketdata.Trade;

public class MDCTrade extends Trade {
	private static final long serialVersionUID = 1L;
	public static final int mdcBidSide = 0;
	public static final int mdcOfferSide = 1;
	
	public MDCTrade(String symbol) {
		super(symbol);
	}
	
}
