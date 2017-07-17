package com.huatai.strategy.strongtrend;

public class StrongTrendAnalysisResult {
	private final String symbol;
	private final double quantity;
	
	public StrongTrendAnalysisResult(String symbol, double quantity) {
		this.symbol = symbol;
		this.quantity = quantity;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
}
