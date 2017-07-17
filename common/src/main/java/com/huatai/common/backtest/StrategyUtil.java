package com.huatai.common.backtest;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

public class StrategyUtil {
	
	public static RetCode bp(int startIdx, int ma5EndIdx, int ma30EndIdx, double ma5[], double ma30[], MInteger outBegIdx, MInteger outNBElement,
			double outReal[]) {
		int outIdx, i;
		int endIdx = Math.min(ma5EndIdx, ma30EndIdx);
		if (startIdx < 0) return RetCode.OutOfRangeStartIndex;
		if ((endIdx < 0) || (endIdx < startIdx)) return RetCode.OutOfRangeEndIndex;
		outIdx = 0;
		for (i = endIdx; i >= 0; i--) {
			if ((ma30[i] < 0.00000001)) {
				outReal[i] = 0.0;
			} else {
				outReal[i] = (ma5[ma5EndIdx - outIdx] - ma30[i]) / ma30[i];
			}
			outIdx++;
		}
		outNBElement.value = outIdx;
		outBegIdx.value = startIdx;
		return RetCode.Success;
	}
	
	public static RetCode bpv(int startIdx, int endIdx, double real[], int optInTimePeriod, MInteger outBegIdx, MInteger outNBElement,
			double outReal[]) {
		int dataLength = endIdx + 1;
		if (startIdx < 0) return RetCode.OutOfRangeStartIndex;
		if ((endIdx < 0) || (endIdx < startIdx)) return RetCode.OutOfRangeEndIndex;
		
		MInteger BP_MA_Length = new MInteger();
		MInteger BP_STEDDEV_Length = new MInteger();
		double BP_MA[] = new double[dataLength];
		double BP_STEDEV[] = new double[dataLength];
		Core core = new Core();
		core.sma(startIdx, dataLength - 1, real, optInTimePeriod, new MInteger(), BP_MA_Length, BP_MA);
		core.stdDev(startIdx, dataLength - 1, real, optInTimePeriod, 1.0, new MInteger(), BP_STEDDEV_Length, BP_STEDEV);
		
		int outIdx, i;
		outIdx = 0;
		for (i = BP_MA_Length.value - 1; i >= 0; i--) {
			
			if (BP_STEDEV[i] < 0.00000001) {
				outReal[i] = 0.0;
			} else {
				outReal[i] = (real[endIdx - outIdx] - BP_MA[i]) / BP_STEDEV[i];
			}
			outIdx++;
		}
		outNBElement.value = outIdx;
		outBegIdx.value = startIdx;
		return RetCode.Success;
	}
}
