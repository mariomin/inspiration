package com.huatai.common.backtest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NetValueRecord {
	public final Map<String, NetValueDetail> netValueDetails = new HashMap<String, NetValueDetail>();
	public double netValue;
	public final long created;
	
	public class NetValueDetail {
		public double marketPrice;
		public double positionQty;
		public double avgCost;
	}
	
	public NetValueRecord(long created) {
		this.created = created;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("NetValueRecord [created=");
		sb.append(new Date(created).toString());
		sb.append(", netValue=");
		sb.append(netValue);
		sb.append(", positionDetails=");
		sb.append("[");
		for (String symbol : netValueDetails.keySet()) {
			sb.append("{");
			sb.append("Symbol=");
			sb.append(symbol);
			sb.append(", avgCost=");
			sb.append(netValueDetails.get(symbol).avgCost);
			sb.append(", marketPrice=");
			sb.append(netValueDetails.get(symbol).marketPrice);
			sb.append(", positionQty=");
			sb.append(netValueDetails.get(symbol).positionQty);
			sb.append("},");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		sb.append("]");
		return sb.toString();
	}
}
