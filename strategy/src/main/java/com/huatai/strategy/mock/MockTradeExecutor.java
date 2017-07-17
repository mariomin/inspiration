package com.huatai.strategy.mock;

import java.util.Random;

import com.google.gson.Gson;
import com.huatai.common.util.GsonUtil;
import com.huatai.platform.algo.StrategyTradeExecutor;
import com.huatai.strategy.intraday.breakout.BreakoutOpenSignal;
import com.huatai.strategy.intraday.common.IndexRecord;

public class MockTradeExecutor extends StrategyTradeExecutor<Object> {
	private final Random random = new Random(10);
	private final Gson gson = GsonUtil.getGsonInstance();
	
	public MockTradeExecutor(String strategyId) {
		super();
	}
	
	@Override
	public void init() {}
	
	@Override
	public void execute(Object object) throws Exception {
		BreakoutOpenSignal reports = new BreakoutOpenSignal();
		reports.setAvgTradeValue(new IndexRecord(getBool(), 10.0 + random.nextDouble()));
		reports.setAvgVariation(new IndexRecord(getBool(), 10 + random.nextDouble()));
		reports.setAvgVariationAbs(new IndexRecord(getBool(), 10 + random.nextDouble()));
		reports.setBreakPrice(10 + random.nextDouble());
		reports.setChangePct(new IndexRecord(getBool(), 10 + random.nextDouble()));
		reports.setDistPriceMidToBreakPoint(10 + random.nextDouble());
		reports.setEmaAccAmountBuy(new IndexRecord(getBool(), 10));
		reports.setEmaPressureRatio(new IndexRecord(getBool(), 10 + random.nextDouble()));
		reports.setLowBoundaryChangePct(new IndexRecord(getBool(), 10));
		reports.setPressurePrice(10 + random.nextDouble());
		reports.setPressureVolume(new IndexRecord(getBool(), 10));
		reports.setSumStandardAskVolume(new IndexRecord(getBool(), 10));
		reports.setVolumeAmp(new IndexRecord(getBool(), 10 + random.nextDouble()));
		reports.setSymbol("000001.SZ");
		sendStrategyReport(gson.toJson(reports));
	}
	
	private boolean getBool() {
		int i = random.nextInt(10);
		return (i % 2) == 0 ? true : false;
	}
}
