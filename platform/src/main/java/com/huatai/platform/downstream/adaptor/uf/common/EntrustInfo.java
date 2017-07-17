package com.huatai.platform.downstream.adaptor.uf.common;

import java.util.ArrayList;
import java.util.List;

import com.huatai.common.business.ExchangeOrder;

public class EntrustInfo {
	public ExchangeOrder order;
	public double remainingQty;
	public List<String> execIds = new ArrayList<String>();
	
	public EntrustInfo(ExchangeOrder order) {
		this.order = order;
		remainingQty = order.getQuantity();
	}
}
