package com.huatai.common.marketdata;

public enum Level {
	one(1), five(5), full(10);
	
	private final int value;
	
	private Level(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static Level valueOf(int i) {
		switch (i) {
			case 1:
				return one;
			case 5:
				return five;
			case 0:
				return full;
			default:
				return null;
		}
	}
	
	public int getFixValue() {
		if (10 == value)
			return 0;
		else return value;
	}
};