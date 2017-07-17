package com.huatai.strategy.contrarian;

public class ContrarianAnalysisResult {
	private final String symbol;
	private final double quantity;
	
	public ContrarianAnalysisResult(String symbol, double quantity) {
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
