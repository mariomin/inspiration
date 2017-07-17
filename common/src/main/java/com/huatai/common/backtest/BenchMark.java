package com.huatai.common.backtest;

import java.util.HashSet;
import java.util.Set;

public enum BenchMark {
	HS300("000300.SH"), ZZ500("000905.SH"), SZ50("000016.SH");
	public String value;
	
	private BenchMark(String value) {
		this.value = value;
	}
	
	public static BenchMark getBenchMark(String value) {
		for (BenchMark benchMark : BenchMark.values()) {
			if (benchMark.value.equals(value)) return benchMark;
		}
		
		return null;
	}
	
	public static Set<String> convertBenchMark(Set<BenchMark> benchMarks) {
		Set<String> benchMarkSymbols = new HashSet<String>();
		
		for (BenchMark benchMark : benchMarks) {
			benchMarkSymbols.add(benchMark.value);
		}
		
		return benchMarkSymbols;
	}
}
