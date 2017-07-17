package com.huatai.strategy.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.strategy.OrderMsg;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.SecurityType;
import com.huatai.platform.algo.StrategyTradeExecutor;

public class SampleTradeExecutor extends StrategyTradeExecutor<AnalysisResult> {
	private final static Logger log = LoggerFactory.getLogger(SampleTradeExecutor.class);
	
	public SampleTradeExecutor() {}
	
	@Override
	public void execute(AnalysisResult analysisRecord) throws Exception {
		// 执行SignalAnalyzer捕捉到的交易信号，每个股票各买1000股
		// 下限价订单
		OrderMsg orderMsg = placeLimitOrder("test", SecurityType.CS, analysisRecord.symbol, OrderSide.Buy, 7.7d, 1000d, 0l);
		// 如果下单成功则返回订单编号，若失败则返回对应的错误信息
		if (orderMsg.isSuccessed()) {
			log.info("place order successed, the clOrderId is:{}", orderMsg.getClOrdId());
		} else {
			log.error("place order failed:{}", orderMsg.getMessage());
		}
	}
	
	@Override
	public void init() {}
	
}
