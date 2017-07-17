package com.huatai.common.marketdata.mdc;

import java.util.List;

import com.huatai.common.marketdata.Quote;
import com.huatai.common.type.QtyPrice;

public class MDCFuture extends Quote {
	private static final long serialVersionUID = 1L;
	
	public MDCFuture(String symbols) {
		super(symbols);
	}
	
	public MDCFuture(String symbols, List<QtyPrice> bids, List<QtyPrice> asks) {
		super(symbols, bids, asks);
	}
	
}
