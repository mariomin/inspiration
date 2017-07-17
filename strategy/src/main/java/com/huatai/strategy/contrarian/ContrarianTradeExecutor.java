package com.huatai.strategy.contrarian;

import com.huatai.common.backtest.IMarketDataCalculateService;
import com.huatai.common.backtest.ITradingService;
import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.marketdata.TimeFrame;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;
import com.huatai.common.util.DecimalUtil;
import com.huatai.platform.algo.StrategyTradeExecutor;

public class ContrarianTradeExecutor extends StrategyTradeExecutor<ContrarianAnalysisResult> {
	private final IMarketDataCalculateService mdCalService;
	private final ITradingService tradingService;
	
	public ContrarianTradeExecutor(IMarketDataCalculateService mdCalService, ITradingService tradingService) {
		this.mdCalService = mdCalService;
		this.tradingService = tradingService;
	}
	
	@Override
	public void init() {}
	
	@Override
	public void execute(ContrarianAnalysisResult result) throws Exception {
		String symbol = result.getSymbol();
		double quantity = result.getQuantity();
		
		double availQty = tradingService.getTradableQty(symbol);
		double totalQty = tradingService.getHoldingQty(symbol);
		double price = mdCalService.getOpenPx(symbol, TimeFrame.PERIOD_D1, 0, 0).get(0);
		
		ExchangeOrder order = new ExchangeOrder();
		order.setSymbol(symbol);
		order.setOrderType(OrderType.MARKET);
		
		order.setQuantity(quantity);
		
		OrderSide side = OrderSide.Buy;
		Double qty = 0d;
		if (DecimalUtil.greaterThan(quantity, totalQty)) {
			side = OrderSide.Buy;
			qty = quantity - totalQty;
		} else if (DecimalUtil.lessThan(quantity, availQty)) {
			side = OrderSide.Sell;
			qty = availQty - quantity;
		}
		
		order.setOrderSide(side);
		order.setQuantity(qty);
		order.setPrice(price);
		tradingService.placeOrder(order);
	}
	
}
