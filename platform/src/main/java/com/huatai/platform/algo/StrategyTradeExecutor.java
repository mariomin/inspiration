package com.huatai.platform.algo;

import com.huatai.common.algo.IStrategyTradingService;
import com.huatai.common.algo.ITradeExecutor;
import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.strategy.OrderMsg;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;
import com.huatai.common.type.SecurityType;

public abstract class StrategyTradeExecutor<V> implements ITradeExecutor<V> {
	private IStrategyTradingService tradeService;
	
	public void setTradeService(IStrategyTradingService tradeService) {
		this.tradeService = tradeService;
	}
	
	protected OrderMsg placeLimitOrder(String securityAccount, SecurityType securityType, String symbol, OrderSide orderSide, Double price,
			Double quantity, Long timeout) {
		ExchangeOrder exchangeOrder = new ExchangeOrder();
		exchangeOrder.setOrderType(OrderType.LIMIT);
		exchangeOrder.setSecurityAccount(securityAccount);
		exchangeOrder.setSecurityType(securityType);
		exchangeOrder.setSymbol(symbol);
		exchangeOrder.setOrderSide(orderSide);
		exchangeOrder.setPrice(price);
		exchangeOrder.setQuantity(quantity);
		
		return tradeService.placeNewOrder(exchangeOrder, timeout);
	}
	
	protected OrderMsg placeLimitOrder(String tradingAccount, String securityAccount, String portfolioNo, String portfolioType,
			SecurityType securityType, String symbol, OrderSide orderSide, Double price, Double quantity, Long timeout) {
		ExchangeOrder exchangeOrder = new ExchangeOrder();
		exchangeOrder.setOrderType(OrderType.LIMIT);
		exchangeOrder.setTradingAccount(tradingAccount);
		exchangeOrder.setSecurityAccount(securityAccount);
		exchangeOrder.setPortfolioNo(portfolioNo);
		exchangeOrder.setPortfolioType(portfolioType);
		exchangeOrder.setSecurityType(securityType);
		exchangeOrder.setSymbol(symbol);
		exchangeOrder.setOrderSide(orderSide);
		exchangeOrder.setPrice(price);
		exchangeOrder.setQuantity(quantity);
		
		return tradeService.placeNewOrder(exchangeOrder, timeout);
	}
	
	protected OrderMsg placeMarketOrder(String securityAccount, SecurityType securityType, String symbol, OrderSide orderSide, Double quantity,
			Character timeInForce, Integer maxPriceLevels, Double minQty, Long timeout) {
		ExchangeOrder exchangeOrder = new ExchangeOrder();
		exchangeOrder.setOrderType(OrderType.MARKET);
		exchangeOrder.setSecurityAccount(securityAccount);
		exchangeOrder.setSecurityType(securityType);
		exchangeOrder.setSymbol(symbol);
		exchangeOrder.setOrderSide(orderSide);
		exchangeOrder.setQuantity(quantity);
		exchangeOrder.setTimeInForce(timeInForce);
		exchangeOrder.setMaxPriceLevels(maxPriceLevels);
		exchangeOrder.setMinQty(minQty);
		
		return tradeService.placeNewOrder(exchangeOrder, timeout);
	}
	
	protected OrderMsg placeMarketOrder(String tradingAccount, String securityAccount, String portfolioNo, String portfolioType,
			SecurityType securityType, String symbol, OrderSide orderSide, Double quantity, Character timeInForce, Integer maxPriceLevels,
			Double minQty, Long timeout) {
		ExchangeOrder exchangeOrder = new ExchangeOrder();
		exchangeOrder.setOrderType(OrderType.MARKET);
		exchangeOrder.setTradingAccount(tradingAccount);
		exchangeOrder.setSecurityAccount(securityAccount);
		exchangeOrder.setPortfolioNo(portfolioNo);
		exchangeOrder.setPortfolioType(portfolioType);
		exchangeOrder.setSecurityType(securityType);
		exchangeOrder.setSymbol(symbol);
		exchangeOrder.setOrderSide(orderSide);
		exchangeOrder.setQuantity(quantity);
		exchangeOrder.setTimeInForce(timeInForce);
		exchangeOrder.setMaxPriceLevels(maxPriceLevels);
		exchangeOrder.setMinQty(minQty);
		
		return tradeService.placeNewOrder(exchangeOrder, timeout);
	}
	
	protected OrderMsg cancelOrder(String clOrdId, Long timeout) {
		return tradeService.cancelOrder(null, null, clOrdId, timeout);
	}
	
	protected OrderMsg cancelOrder(String tradingAccount, String securityAccount, String clOrdId, Long timeout) {
		return tradeService.cancelOrder(tradingAccount, securityAccount, clOrdId, timeout);
	}
	
	protected void sendStrategyReport(String report) {
		tradeService.sendStrategyReport(report);
	}
}
