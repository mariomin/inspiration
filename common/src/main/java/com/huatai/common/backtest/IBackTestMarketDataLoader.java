package com.huatai.common.backtest;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.huatai.common.marketdata.MarketData;
import com.huatai.common.marketdata.TimeFrame;

public interface IBackTestMarketDataLoader extends IBackTestService {
	List<MarketData> loadMarketData(Set<String> symbols, Date start, Date end, TimeFrame timeFrame);
}
