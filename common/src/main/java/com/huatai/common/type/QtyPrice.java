package com.huatai.common.type;

import java.io.Serializable;
import java.text.DecimalFormat;

public class QtyPrice implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
	private static DecimalFormat df = new DecimalFormat("#.00");
	private static long CountPerUnit = 10000 * 100;
	public Double quantity;
	public Double price;
	
	public QtyPrice(Double quantity, Double price) {
		this.quantity = quantity;
		this.price = price;
	}
	
	public Double getQuantity() {
		return quantity;
	}
	
	public String getQuantityShow() {
		if (quantity >= CountPerUnit) return df.format((quantity * 1D) / CountPerUnit) + "万";
		return String.valueOf(quantity / 100);
	}
	
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public String getPriceShow() {
		return df.format(price);
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[quantity=");
		sb.append(quantity);
		sb.append(", price=");
		sb.append(price);
		sb.append("]");
		return sb.toString();
	}
	
	@Override
	public QtyPrice clone() {
		try {
			return (QtyPrice) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
