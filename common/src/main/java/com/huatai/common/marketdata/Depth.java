package com.huatai.common.marketdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Depth extends MarketData {
	private static final long serialVersionUID = 1L;
	protected DepthSide side;
	protected Double price;
	protected Long orders;
	protected List<Double> volumes;
	
	public enum DepthSide {
		Offer(65), Bid(66);
		private static Map<Integer, DepthSide> map = new HashMap<Integer, DepthSide>();
		private int value;
		
		static {
			for (DepthSide type : DepthSide.values()) {
				map.put(type.value(), type);
			}
		}
		
		DepthSide(int value) {
			this.value = value;
		}
		
		public int value() {
			return value;
		}
		
		static public DepthSide getType(int s) {
			return map.get(s);
		}
	}
	
	public Depth(String symbol) {
		super(symbol);
	}
	
	public DepthSide getSide() {
		return side;
	}
	
	public void setSide(int side) {
		this.side = DepthSide.getType(side);
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Long getOrders() {
		return orders;
	}
	
	public void setOrders(Long orders) {
		this.orders = orders;
	}
	
	public List<Double> getVolumes() {
		return volumes;
	}
	
	public void setVolumes(List<Double> volumes) {
		this.volumes = volumes;
	}
	
	public String getFixVolumes() {
		StringBuilder sb = new StringBuilder();
		for (double volume : volumes) {
			sb.append(volume + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Depth [symbol=").append(symbol);
		sb.append(", id=").append(id);
		sb.append(", side=").append(side);
		sb.append(", price=").append(price);
		sb.append(", orders=").append(orders);
		sb.append(", volumes=").append(volumes);
		sb.append("]");
		return sb.toString();
	}
	
}
