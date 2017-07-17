package com.huatai.common.type;

import java.util.HashMap;
import java.util.Map;

public enum OrderSide {
	Buy("买"), Sell("卖"), ShortSell("融券卖"), Undisclosed("不揭露");
	
	private static Map<String, OrderSide> map = new HashMap<String, OrderSide>();
	private String value;
	
	static {
		for (OrderSide field : OrderSide.values()) {
			map.put(field.value(), field);
		}
	}
	
	OrderSide(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public boolean isBuy() {
		return equals(Buy);
	}
	
	public boolean isSell() {
		return equals(Sell);
	}
	
	public static String[] displayNames() {
		return map.keySet().toArray(new String[] {});
	}
	
	public static OrderSide getOrderSideByValue(String value) {
		return map.get(value);
	}
	
}
