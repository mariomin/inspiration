package com.huatai.strategy.mock;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class MockStrategyParams implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SerializedName("AutoSendReport")
	private boolean autoSendReport = true;
	
	public boolean isAutoSendReport() {
		return autoSendReport;
	}
	
	public void setAutoSendReport(boolean autoSendReport) {
		this.autoSendReport = autoSendReport;
	}
	
}
