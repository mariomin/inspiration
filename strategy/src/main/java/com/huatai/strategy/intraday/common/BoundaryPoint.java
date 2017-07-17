package com.huatai.strategy.intraday.common;

import java.util.ArrayList;
import java.util.List;

import com.huatai.common.util.DecimalUtil;
import com.huatai.strategy.intraday.util.IndexUtil;

public class BoundaryPoint implements Cloneable {
	private final long time;
	private final double value;
	private final boolean high;
	private double range;
	public double pressurePrice;
	public double pressureQty;
	public double avgAmplitude;
	public double avgAmplitudePct;
	public double avgAmplitudeTradeValue;
	public long createTime;
	
	public BoundaryPoint(Record timeValue, boolean high, double range) {
		time = timeValue.getTime();
		value = timeValue.getValue();
		this.high = high;
		this.range = range;
	}
	
	public long getTime() {
		return time;
	}
	
	public double getValue() {
		return value;
	}
	
	public double getRange() {
		return range;
	}
	
	public void setRange(double range) {
		this.range = range;
	}
	
	public boolean isHigh() {
		return high;
	}
	
	public static int getStartTimeIndex(List<BoundaryPoint> list, long startTime) {
		int low = 0;
		int high = list.size() - 1;
		
		while (low <= high) {
			int middle = (low + high) / 2;
			
			if (startTime == list.get(middle).getTime())
				return middle;
			else if (startTime < list.get(middle).getTime()) {
				high = middle - 1;
			} else {
				low = middle + 1;
			}
		}
		
		return -1;
	}
	
	public static BoundaryPoint calBoundaryPoint(List<Record> PriceMid, int startIndex, int endIndex, boolean high) {
		double value = PriceMid.get(startIndex + 1).getValue();
		int index = startIndex + 1;
		
		for (int i = startIndex + 1; i <= endIndex; i++) {
			if ((DecimalUtil.greaterThan(PriceMid.get(i).getValue(), value) && high)
					|| (DecimalUtil.lessThan(PriceMid.get(i).getValue(), value) && !high)) {
				value = PriceMid.get(i).getValue();
				index = i;
			}
		}
		
		return new BoundaryPoint(PriceMid.get(index), high, 0.0);
	}
	
	public static void updateDeltaMA(List<Record> MA1, List<Record> MA2, List<Record> deltaMA) {
		if ((MA1 == null) || (MA2 == null) || (MA1.size() != MA2.size())) return;
		
		for (int i = deltaMA.size(); i < MA1.size(); i++) {
			deltaMA.add(new Record(MA1.get(i).getDate(), MA1.get(i).getTime(), DecimalUtil.compare(MA1.get(i).getValue(), MA2.get(i).getValue())));
		}
	}
	
	public static double updateAverageAmplitudeVolumes(List<Record> volumePerSeconds, long startTime, long endTime) {
		int startIndex = Record.getStartTimeIndex(volumePerSeconds, startTime);
		int endIndex = Record.getStartTimeIndex(volumePerSeconds, endTime);
		if ((startIndex == -1) || (endIndex == -1)) return 0d;
		
		double sum = 0;
		for (int i = startIndex; i <= endIndex; i++) {
			sum += volumePerSeconds.get(i).getTradeValue();
		}
		
		return sum / ((endIndex - startIndex) + 1);
	}
	
	public static long updateBoundaryPoints(List<Record> priceMids, List<Record> volumePerSeconds, List<Record> MA1, int P1, List<Record> MA2, int P2,
			List<Record> deltaMA, long time, List<BoundaryPoint> boundaryPoints, int waveNum, long createTime, int keepSize) {
		int cpSize = boundaryPoints.size();
		IndexUtil.calEMAList(priceMids, MA1, P1);
		IndexUtil.calEMAList(priceMids, MA2, P2);
		updateDeltaMA(MA1, MA2, deltaMA);
		
		int start = Record.getStartTimeIndex(deltaMA, time);
		if (start == -1) {
			start = 0;
		}
		double nowDelta = 0.0;
		
		while ((start < deltaMA.size()) && DecimalUtil.isZero(deltaMA.get(start).getValue())) {
			start++;
		}
		
		if (start >= deltaMA.size()) return time;
		nowDelta = deltaMA.get(start).getValue();
		long newTime = time;
		
		for (int i = start + 1; i < deltaMA.size(); i++) {
			if (DecimalUtil.lessThan(nowDelta * deltaMA.get(i).getValue(), 0)) {
				boolean high = DecimalUtil.greaterThan(nowDelta, 0);
				
				if (boundaryPoints.isEmpty()) {
					BoundaryPoint boundaryPoint = calBoundaryPoint(priceMids, start, i, high);
					boundaryPoints.add(boundaryPoint);
					newTime = deltaMA.get(i).getTime();
					boundaryPoint.avgAmplitude = 0d;
					boundaryPoint.avgAmplitudePct = 0d;
					boundaryPoint.avgAmplitudeTradeValue = 0d;
					boundaryPoint.createTime = createTime;
				} else {
					BoundaryPoint preCP = boundaryPoints.get(boundaryPoints.size() - 1);
					BoundaryPoint boundaryPoint = calBoundaryPoint(priceMids, start, i, high);
					boundaryPoints.add(boundaryPoint);
					newTime = deltaMA.get(i).getTime();
					
					double amplitudeVolume = updateAverageAmplitudeVolumes(volumePerSeconds, preCP.getTime(), boundaryPoint.getTime());
					boundaryPoint.avgAmplitudeTradeValue = IndexUtil.calEmaValue(preCP.avgAmplitudeTradeValue, amplitudeVolume, boundaryPoints.size(),
							waveNum);
					
					double amplitude = Math.abs((boundaryPoint.getValue() / preCP.getValue()) - 1);
					boundaryPoint.avgAmplitude = IndexUtil.calEmaValue(preCP.avgAmplitude, amplitude, boundaryPoints.size(), waveNum);
					
					double avgAmplitudePct = Math.abs(boundaryPoint.getValue() - preCP.getValue());
					boundaryPoint.avgAmplitudePct = IndexUtil.calEmaValue(preCP.avgAmplitudePct, avgAmplitudePct, boundaryPoints.size(), waveNum);
					boundaryPoint.createTime = createTime;
				}
				
				break;
			}
		}
		
		if (boundaryPoints.size() > cpSize) {
			BoundaryPoint boundaryPoint = boundaryPoints.get(boundaryPoints.size() - 1);
			int priceMidIndex = Math.min(priceMids.size() - Math.max(P1, P2), Record.getStartTimeIndex(priceMids, boundaryPoint.getTime()));
			priceMidIndex = Math.min(priceMidIndex, priceMids.size() - keepSize);
			for (int i = 0; i < priceMidIndex; i++) {
				priceMids.remove(0);
				MA1.remove(0);
				MA2.remove(0);
				deltaMA.remove(0);
				volumePerSeconds.remove(0);
				
			}
		}
		
		return newTime;
	}
	
	public static double getLowestValue(List<BoundaryPoint> boundaryPoints, int startIndex) {
		int index = startIndex;
		double value = boundaryPoints.get(startIndex).getValue();
		
		for (int i = startIndex; i < boundaryPoints.size(); i++) {
			if (DecimalUtil.greaterThan(value, boundaryPoints.get(i).getValue())) {
				value = boundaryPoints.get(i).getValue();
				index = i;
			}
		}
		return boundaryPoints.get(index).getValue();
	}
	
	public static void calAverageAmplitude(List<BoundaryPoint> boundaryPoints, BoundaryPoint breakPoint, int period) {
		int index = getStartTimeIndex(boundaryPoints, breakPoint.getTime());
		int length = (index >= period) ? period : index;
		if (length <= 1) return;
		
		double sum = 0d;
		for (int i = 0; i < length; i++) {
			sum += Math.abs((boundaryPoints.get(index - i).getValue() / boundaryPoints.get(index - i - 1).getValue()) - 1);
		}
		
		breakPoint.avgAmplitude = sum / length;
	}
	
	public static void calAveragePercentRange(List<BoundaryPoint> boundaryPoints, BoundaryPoint breakPoint, int period) {
		int index = getStartTimeIndex(boundaryPoints, breakPoint.getTime());
		int length = (index >= period) ? period : index;
		if (length <= 1) return;
		
		double sum = 0d;
		for (int i = 0; i < length; i++) {
			sum += Math.abs(boundaryPoints.get(index - i).getValue() - boundaryPoints.get(index - 1 - i).getValue());
		}
		
		breakPoint.avgAmplitudePct = sum / length;
	}
	
	private static boolean existHigherPointsAfter(List<BoundaryPoint> boundaryPoints, int index) {
		double value = boundaryPoints.get(index).getValue();
		for (int i = index; i < (boundaryPoints.size() - 1); i++) {
			if (DecimalUtil.greaterThan(boundaryPoints.get(i).getValue(), value)) return true;
		}
		
		return false;
	}
	
	public static List<BoundaryPoint> calBreakPoints(List<BoundaryPoint> boundaryPoints, long nowTime, double nowValue, double R, double P) {
		List<BoundaryPoint> breakPoints = new ArrayList<BoundaryPoint>();
		int cpsLength = boundaryPoints.size();
		int highIndex;
		int lowIndex;
		
		if (cpsLength < 2) return breakPoints;
		
		if (boundaryPoints.get(cpsLength - 1).isHigh()) {
			highIndex = cpsLength - 1;
		} else {
			highIndex = cpsLength - 2;
		}
		lowIndex = highIndex - 1;
		
		while (highIndex > 0) {
			BoundaryPoint highBP = boundaryPoints.get(highIndex);
			BoundaryPoint lowBP = boundaryPoints.get(lowIndex);
			double highValue = highBP.getValue();
			double lowestValue = lowBP.getValue();
			
			while (lowIndex >= 0) {
				if (DecimalUtil.lessThan(boundaryPoints.get(highIndex).getValue(), boundaryPoints.get(lowIndex + 1).getValue())) {
					break;
				}
				lowestValue = Math.min(lowestValue, boundaryPoints.get(lowIndex).getValue());
				lowIndex -= 2;
			}
			
			double maxRaiseRange = (highValue / lowestValue) - 1;
			if (DecimalUtil.greaterThan(maxRaiseRange, R)) {
				double lowerValue = getLowestValue(boundaryPoints, highIndex);
				double backRange = (highValue - lowerValue) / (highValue - lowestValue);
				
				if (DecimalUtil.equalLessThan(backRange, P) && !existHigherPointsAfter(boundaryPoints, highIndex)) {
					BoundaryPoint breakPoint = highBP.clone();
					breakPoint.setRange(maxRaiseRange);
					breakPoints.add(breakPoint);
				}
			}
			
			highIndex -= 2;
			lowIndex = highIndex - 1;
		}
		
		return breakPoints;
	}
	
	@Override
	public BoundaryPoint clone() {
		try {
			BoundaryPoint obj = (BoundaryPoint) super.clone();
			return obj;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		if (range != 0.0d)
			return "{value:" + value + " , range:" + range + " , pressurePrice:" + pressurePrice + " , pressureQty:" + pressureQty
					+ " , avgAmplitude:" + avgAmplitude + " , avgAmplitudePct:" + avgAmplitudePct + " , avgAmplitudeTradeValue:"
					+ avgAmplitudeTradeValue + " , time:" + time + "}";
		else return "{value:" + value + " , high:" + high + " , time:" + time + "}";
	}
}
