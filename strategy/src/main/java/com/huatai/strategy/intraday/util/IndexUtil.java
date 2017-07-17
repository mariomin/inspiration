package com.huatai.strategy.intraday.util;

import java.util.List;

import com.huatai.strategy.intraday.common.Record;

public class IndexUtil {
	
	public static double calEMA(List<Record> records, int period) {
		int listSize = records.size();
		int actualPeriod = Math.min(listSize, period);
		double ema = 0d;
		
		for (int i = 1; i <= actualPeriod; i++) {
			double value = records.get((listSize - 1 - actualPeriod) + i).getValue();
			ema = ema + ((2.0 / (i + 1)) * (value - ema));
		}
		
		return ema;
	}
	
	public static void calEMAListBase(List<Record> records, List<Record> ema, int period) {
		while (ema.size() < records.size()) {
			int actualPeriod = Math.min(ema.size() + 1, period);
			Record originalRecord = records.get(ema.size());
			double emaValue = 0d;
			
			for (int i = 1; i <= actualPeriod; i++) {
				double value = records.get((ema.size() - actualPeriod) + i).getValue();
				emaValue = emaValue + ((2.0 / (i + 1)) * (value - emaValue));
			}
			
			ema.add(new Record(originalRecord.getDate(), originalRecord.getTime(), emaValue));
		}
	}
	
	public static void calEMAList(List<Record> records, List<Record> ema, int period) {
		while (ema.size() < records.size()) {
			double emaValue = 0d;
			int emaSize = ema.size();
			Record originalRecord = records.get(emaSize);
			
			if (emaSize == 0) {
				emaValue = originalRecord.getValue();
			} else if ((emaSize + 1) <= period) {
				emaValue = ema.get(emaSize - 1).getValue() + ((2.0 / (emaSize + 1)) * (originalRecord.getValue() - ema.get(emaSize - 1).getValue()));
			} else {
				emaValue = ema.get(emaSize - 1).getValue() + ((2.0 / (period + 1)) * (originalRecord.getValue() - ema.get(emaSize - 1).getValue()));
			}
			
			ema.add(new Record(originalRecord.getDate(), originalRecord.getTime(), emaValue));
		}
	}
	
	public static void calMAList(List<Record> records, List<Record> ma, int period) {
		while (ma.size() < records.size()) {
			int actualPeriod = Math.min(ma.size() + 1, period);
			Record originalRecord = records.get(ma.size());
			double sum = 0d;
			
			for (int j = 0; j < actualPeriod; j++) {
				sum += records.get(ma.size() - j).getValue();
			}
			
			ma.add(new Record(originalRecord.getDate(), originalRecord.getTime(), sum / actualPeriod));
		}
	}
	
	public static double calEmaValue(double oldEma, double value, int index, int period) {
		if (index == 1)
			return value;
		else if (index <= period)
			return oldEma + ((2d / (index + 1d)) * (value - oldEma));
		else return oldEma + ((2d / (period + 1d)) * (value - oldEma));
	}
	
	public static double calEmaValue(double oldEma, double value, int index) {
		return oldEma + ((2d / (index + 1d)) * (value - oldEma));
	}
	
	public static double calPriceSec(double oldPriceSec, long totalVol, double tradePx, long tradeQty) {
		return ((oldPriceSec * totalVol) + (tradePx * tradeQty)) / (totalVol + tradeQty);
	}
	
	public static double calVolumeSec(double tradeQty, double volumeSec) {
		return tradeQty + volumeSec;
	}
	
	public static double calPriceMid(double bidPx1, double askPx1) {
		return (bidPx1 + askPx1) / 2d;
	}
	
}
