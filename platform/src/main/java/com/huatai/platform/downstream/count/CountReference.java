package com.huatai.platform.downstream.count;

import com.huatai.common.data.PersistentObject;

public class CountReference extends PersistentObject {
	private static final long serialVersionUID = 1L;
	private String countId;
	private int count;
	
	public String getCountId() {
		return countId;
	}
	
	public void setCountId(String countId) {
		this.countId = countId;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
}
