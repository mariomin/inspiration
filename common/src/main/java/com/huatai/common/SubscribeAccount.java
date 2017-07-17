package com.huatai.common;

import com.huatai.common.index.SecurityTradingIndex;

public class SubscribeAccount {
	private final String key;
	private final SecurityTradingIndex securityTradingIndex;
	private final String subscribeID;
	
	public SubscribeAccount(String key, SecurityTradingIndex securityTradingIndex, String subscribeID) {
		this.key = key;
		this.securityTradingIndex = securityTradingIndex;
		this.subscribeID = subscribeID;
	}
	
	public String getKey() {
		return key;
	}
	
	public SecurityTradingIndex getSecurityTradingIndex() {
		return securityTradingIndex;
	}
	
	public String getSubscribeID() {
		return subscribeID;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((key == null) ? 0 : key.hashCode());
		result = (prime * result) + ((securityTradingIndex == null) ? 0 : securityTradingIndex.hashCode());
		result = (prime * result) + ((subscribeID == null) ? 0 : subscribeID.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		SubscribeAccount other = (SubscribeAccount) obj;
		if (key == null) {
			if (other.key != null) return false;
		} else if (!key.equals(other.key)) return false;
		if (securityTradingIndex == null) {
			if (other.securityTradingIndex != null) return false;
		} else if (!securityTradingIndex.equals(other.securityTradingIndex)) return false;
		if (subscribeID == null) {
			if (other.subscribeID != null) return false;
		} else if (!subscribeID.equals(other.subscribeID)) return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "SubscribeAccount [key=" + key + ", securityTradingIndex=" + securityTradingIndex + ", subscribeID=" + subscribeID + "]";
	}
	
}
