package com.huatai.strategy.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.huatai.common.type.QtyPrice;
import com.huatai.common.util.DecimalUtil;

public class EPLFunctionUtil {
	private static final Calendar now = Calendar.getInstance();
	
	public static long formalizeTime(Date date) {
		long time = date.getTime();
		return (time / 1000) * 1000;
	}
	
	public static double calMidPrice(List<QtyPrice> asks, List<QtyPrice> bids) {
		QtyPrice askPrice = asks.get(0);
		QtyPrice bidPrice = bids.get(0);
		
		if (DecimalUtil.equal(askPrice.price, 0d))
			return bidPrice.price;
		else if (DecimalUtil.equal(bidPrice.price, 0d))
			return askPrice.price;
		else return (askPrice.price + bidPrice.price) / 2;
	}
	
	public static boolean isValidTime(Date timestamp) {
		now.setTime(timestamp);
		int time = (now.get(Calendar.HOUR_OF_DAY) * 10000) + (now.get(Calendar.MINUTE) * 100) + now.get(Calendar.SECOND);
		return ((time >= 93000) && (time <= 113000)) || ((time >= 130000) && (time <= 145500));
	}
	
	public static boolean isMarketAtClose(Date timestamp) {
		now.setTime(timestamp);
		int time = (now.get(Calendar.HOUR_OF_DAY) * 10000) + (now.get(Calendar.MINUTE) * 100) + now.get(Calendar.SECOND);
		return (time > 145500);
	}
}
