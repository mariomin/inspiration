package com.huatai.common.util;

import com.huatai.common.type.OrderSide;

public class PriceUtil {
	
	// sort by ascending, least priority order coming after
	public static int compareBySide(OrderSide side, double x, double y) {
		if (side == OrderSide.Buy)
			return DecimalUtil.compare(y, x);
		else return DecimalUtil.compare(x, y);
	}
	
	// x in the limit of y price
	public static boolean inLimit(double x, double y, OrderSide side) {
		if (side == OrderSide.Buy)
			return DecimalUtil.equalLessThan(x, y);
		else return DecimalUtil.equalGreaterThan(x, y);
	}
	
	public static boolean isValidPrice(double price) {
		return DecimalUtil.greaterThan(price, 0);
	}
	
}
