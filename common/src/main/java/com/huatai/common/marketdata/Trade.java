package com.huatai.common.marketdata;

import java.util.HashMap;
import java.util.Map;

public class Trade extends MarketData {
	private static final long serialVersionUID = 1L;
	protected Side side;
	protected Double price;
	protected Double quantity;
	protected Double turnover;
	
	public enum Side {
		Bid(66), Offer(83), Unknown(32);
		private static Map<Integer, Side> map = new HashMap<Integer, Side>();
		private int value;
		
		static {
			for (Side type : Side.values()) {
				map.put(type.value(), type);
			}
		}
		
		Side(int value) {
			this.value = value;
		}
		
		public int value() {
			return value;
		}
		
		static public Side getType(int s) {
			return map.get(s);
		}
	}
	
	public Trade(String symbol) {
		super(symbol);
	}
	
	public Side getSide() {
		return side;
	}
	
	public void setSide(int side) {
		this.side = Side.getType(side);
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	public Double getTurnover() {
		return turnover;
	}
	
	public void setTurnover(Double turnover) {
		this.turnover = turnover;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Trade [symbol=").append(symbol);
		sb.append(", id=").append(id);
		sb.append(", side=").append(side);
		sb.append(", price=").append(price);
		sb.append(", quantity=").append(quantity);
		sb.append(", turnover=").append(turnover);
		sb.append("]");
		return sb.toString();
	}
	
	@Override
	public Trade clone() {
		try {
			return (Trade) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
