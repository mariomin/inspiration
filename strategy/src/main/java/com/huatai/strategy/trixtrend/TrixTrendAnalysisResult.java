package com.huatai.strategy.trixtrend;

public class TrixTrendAnalysisResult {
	private final String symbol;
	private final double quantity;
	
	public TrixTrendAnalysisResult(String symbol, double quantity) {
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
