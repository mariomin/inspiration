package com.huatai.common.business;

import com.huatai.common.type.ExchangeType;

/**
 * 交易所标识
 * @author 010350
 *
 */
public class ExchangeIdentifier {
	private final ExchangeType exchangeType;
	private final String exchangeId;
	
	public ExchangeIdentifier(ExchangeType exchangeType, String exchangeId) {
		this.exchangeType = exchangeType;
		this.exchangeId = exchangeId;
	}
	
	public ExchangeType getExchangeType() {
		return exchangeType;
	}
	
	public String getExchangeId() {
		return exchangeId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExchangeIdentifier [exchangeType=");
		builder.append(exchangeType);
		builder.append(", exchangeId=");
		builder.append(exchangeId);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((exchangeId == null) ? 0 : exchangeId.hashCode());
		result = (prime * result) + ((exchangeType == null) ? 0 : exchangeType.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ExchangeIdentifier other = (ExchangeIdentifier) obj;
		if (exchangeId == null) {
			if (other.exchangeId != null) return false;
		} else if (!exchangeId.equals(other.exchangeId)) return false;
		if (exchangeType != other.exchangeType) return false;
		return true;
	}
	
}
