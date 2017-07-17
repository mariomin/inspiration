package com.huatai.strategy.intraday;

import com.huatai.strategy.intraday.breakout.BreakoutAnalysisResult;
import com.huatai.strategy.intraday.reverse.ReverseAnalysisResult;

public class IntradayAnalysisResult {
	private final BreakoutAnalysisResult breakoutResult;
	private final ReverseAnalysisResult reverseResult;
	
	public IntradayAnalysisResult(BreakoutAnalysisResult breakoutResult, ReverseAnalysisResult reverseResult) {
		this.breakoutResult = breakoutResult;
		this.reverseResult = reverseResult;
	}
	
	public BreakoutAnalysisResult getBreakoutResult() {
		return breakoutResult;
	}
	
	public ReverseAnalysisResult getReverseResult() {
		return reverseResult;
	}
	
	public void clearAll() {
		breakoutResult.clearAll();
		reverseResult.clearAll();
	}
	
}
