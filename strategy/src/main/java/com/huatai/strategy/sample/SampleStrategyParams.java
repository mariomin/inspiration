package com.huatai.strategy.sample;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import com.google.gson.annotations.SerializedName;

public class SampleStrategyParams implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SerializedName("Universe")
	private Set<String> universe;
	
	@SerializedName("BuyLimitSet")
	private Map<String, Double> buyLimitSet;
	
	public Set<String> getUniverse() {
		return universe;
	}
	
	public void setUniverse(Set<String> universe) {
		this.universe = universe;
	}
	
	public Map<String, Double> getBuyLimitSet() {
		return buyLimitSet;
	}
	
	public void setBuyLimitSet(Map<String, Double> buyLimitSet) {
		this.buyLimitSet = buyLimitSet;
	}
}
