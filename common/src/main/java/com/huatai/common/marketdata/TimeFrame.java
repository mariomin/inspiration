package com.huatai.common.marketdata;

public enum TimeFrame {
	PERIOD_Tick(1L), PERIOD_M1(60000L), PERIOD_M5(300000L), PERIOD_M10(600000L), PERIOD_M15(900000L), PERIOD_M30(1800000L), PERIOD_H1(
			3600000L), PERIOD_H4(14400000L), PERIOD_D1(86400000L), PERIOD_W1(604800000L), PERIOD_MN1(2592000000L);
	public final long value;
	
	private TimeFrame(long value) {
		this.value = value;
	}
}
