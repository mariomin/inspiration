package com.huatai.common.type;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * This class defines all valid exchange session types and provide a method to retrieve valid exchange
 * sessions for a particular exchange
 */

public enum MarketSessionType {
	// add market sessions for other exchanges here
	DEFAULT("DEFAULT"), // not a real market session but used by strategy framework for default handling
	AM_NO_TRADING("AM_NO_TRADING"), AM_OPEN_AUCTION("AM_OPEN_AUCTION"), AM_OPEN_AUCTION_NEW_ORDER_ONLY(
			"AM_OPEN_AUCTION_NEW_ORDER_ONLY"), AM_OPEN_AUCTION_BLOCKING("AM_OPEN_AUCTION_BLOCKING"), AM_CONTINUOUS_TRADING(
					"AM_CONTINUOUS_TRADING"), LUNCH_BLOCKING("LUNCH_BLOCKING"), PM_CONTINUOUS_TRADING("PM_CONTINUOUS_TRADING"), PM_CLOSING_AUCTION(
							"PM_CLOSING_AUCTION"), PM_SETTLEMENT("PM_SETTLEMENT"), PM_NO_TRADING("PM_NO_TRADING");
	
	private static Map<ExchangeType, Set<MarketSessionType>> map = new HashMap<ExchangeType, Set<MarketSessionType>>();
	
	static {
		map.put(ExchangeType.SH, Collections.unmodifiableSet(EnumSet.of(AM_NO_TRADING, AM_OPEN_AUCTION, AM_OPEN_AUCTION_NEW_ORDER_ONLY,
				AM_OPEN_AUCTION_BLOCKING, AM_CONTINUOUS_TRADING, LUNCH_BLOCKING, PM_CONTINUOUS_TRADING, PM_SETTLEMENT, PM_NO_TRADING)));
		
		map.put(ExchangeType.SZ,
				Collections.unmodifiableSet(EnumSet.of(AM_NO_TRADING, AM_OPEN_AUCTION, AM_OPEN_AUCTION_NEW_ORDER_ONLY, AM_OPEN_AUCTION_BLOCKING,
						AM_CONTINUOUS_TRADING, LUNCH_BLOCKING, PM_CONTINUOUS_TRADING, PM_CLOSING_AUCTION, PM_SETTLEMENT, PM_NO_TRADING)));
	}
	
	public Set<MarketSessionType> getMarketSessionTypes(ExchangeType exchangeType) {
		return map.get(exchangeType);
	}
	
	private String value;
	
	MarketSessionType(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
