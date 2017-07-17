package com.huatai.common.backtest.report;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.huatai.common.backtest.BenchMark;
import com.huatai.common.marketdata.TimeFrame;

public class BackTestRequest {
	@SerializedName("RequestID")
	private String requestID;
	
	@SerializedName("Strategy")
	private String strategy;
	
	@SerializedName("BaseCash")
	private double baseCash;
	
	@SerializedName("StartDate")
	private Date startDate;
	
	@SerializedName("EndDate")
	private Date endDate;
	
	@SerializedName("Universe")
	private Map<String, Double> universe;
	
	@SerializedName("Commission")
	private double commission;
	
	@SerializedName("StampDuty")
	private double stampDuty;
	
	@SerializedName("BenchMarks")
	private Set<BenchMark> benchMarks;
	
	@SerializedName("BackTestTimeFrame")
	private TimeFrame backTestTimeFrame;
	
	@SerializedName("ReportTimeFrame")
	private TimeFrame reportTimeFrame;
	
	@SerializedName("Compress")
	private boolean compress;
	
	@SerializedName("StrategyParam")
	private JsonObject strategyParam;
	
	public BackTestRequest() {}
	
	public BackTestRequest(String requestID, String strategy, double baseCash, Date startDate, Date endDate, Map<String, Double> universe,
			double commission, double stampDuty, Set<BenchMark> benchMarks, TimeFrame backTestTimeFrame, TimeFrame reportTimeFrame,
			boolean compress) {
		this.requestID = requestID;
		this.strategy = strategy;
		this.baseCash = baseCash;
		this.startDate = startDate;
		this.endDate = endDate;
		this.universe = universe;
		this.commission = commission;
		this.stampDuty = stampDuty;
		this.benchMarks = benchMarks;
		this.backTestTimeFrame = backTestTimeFrame;
		this.reportTimeFrame = reportTimeFrame;
		this.compress = compress;
	}
	
	public boolean isValid() {
		//		if ((requestID == null) || (strategy == null) || DecimalUtil.equalLessThan(baseCash, 0.0) || (startDate == null) || (endDate == null)
		//				|| (universe == null) || DecimalUtil.equalLessThan(commission, 0.0) || DecimalUtil.equalLessThan(stampDuty, 0.0)
		//				|| (benchMarks == null) || (backTestTimeFrame == null) || (reportTimeFrame == null))
		//			return false;
		return true;
	}
	
	public String getRequestID() {
		return requestID;
	}
	
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	
	public String getStrategy() {
		return strategy;
	}
	
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	
	public double getBaseCash() {
		return baseCash;
	}
	
	public void setBaseCash(double baseCash) {
		this.baseCash = baseCash;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Map<String, Double> getUniverse() {
		return universe;
	}
	
	public void setUniverse(Map<String, Double> universe) {
		this.universe = universe;
	}
	
	public double getCommission() {
		return commission;
	}
	
	public void setCommission(double commission) {
		this.commission = commission;
	}
	
	public double getStampDuty() {
		return stampDuty;
	}
	
	public void setStampDuty(double stampDuty) {
		this.stampDuty = stampDuty;
	}
	
	public Set<BenchMark> getBenchMarks() {
		return benchMarks;
	}
	
	public void setBenchMarks(Set<BenchMark> benchMarks) {
		this.benchMarks = benchMarks;
	}
	
	public TimeFrame getBackTestTimeFrame() {
		return backTestTimeFrame;
	}
	
	public void setBackTestTimeFrame(TimeFrame backTestTimeFrame) {
		this.backTestTimeFrame = backTestTimeFrame;
	}
	
	public TimeFrame getReportTimeFrame() {
		return reportTimeFrame;
	}
	
	public void setReportTimeFrame(TimeFrame reportTimeFrame) {
		this.reportTimeFrame = reportTimeFrame;
	}
	
	public boolean isCompress() {
		return compress;
	}
	
	public void setCompress(boolean compress) {
		this.compress = compress;
	}
	
	public JsonObject getStrategyParam() {
		return strategyParam;
	}
	
	public void setStrategyParam(JsonObject strategyParam) {
		this.strategyParam = strategyParam;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BackTestRequest [requestID=");
		builder.append(requestID);
		builder.append(", strategy=");
		builder.append(strategy);
		builder.append(", baseCash=");
		builder.append(baseCash);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", universe=");
		builder.append(universe);
		builder.append(", commission=");
		builder.append(commission);
		builder.append(", stampDuty=");
		builder.append(stampDuty);
		builder.append(", benchMarks=");
		builder.append(benchMarks);
		builder.append(", backTestTimeFrame=");
		builder.append(backTestTimeFrame);
		builder.append(", reportTimeFrame=");
		builder.append(reportTimeFrame);
		builder.append(", compress=");
		builder.append(compress);
		builder.append(", strategyParam=");
		builder.append(strategyParam);
		builder.append("]");
		return builder.toString();
	}
	
}
