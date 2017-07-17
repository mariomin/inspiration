package com.huatai.strategy.intraday.breakout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.type.QtyPrice;
import com.huatai.common.util.DecimalUtil;
import com.huatai.strategy.intraday.common.ApplyData;
import com.huatai.strategy.intraday.common.ApplyData.Side;
import com.huatai.strategy.intraday.common.BoundaryPoint;
import com.huatai.strategy.intraday.common.EMAObject;
import com.huatai.strategy.intraday.common.IndexRecord;
import com.huatai.strategy.intraday.common.MarketAtCloseSignal;
import com.huatai.strategy.intraday.common.Record;
import com.huatai.strategy.intraday.util.IndexUtil;

public class BreakoutEPLSubscriber {
	private final Logger log = LoggerFactory.getLogger(BreakoutEPLSubscriber.class);
	// 前一时刻成交总额
	private final Map<String, Record> turnovers = new HashMap<String, Record>();
	// 前一时刻成交总量
	private final Map<String, Record> totalVolumes = new HashMap<String, Record>();
	// 秒级成交量
	private final Map<String, List<Record>> volumeSecs = new HashMap<String, List<Record>>();
	// 秒级中间价格
	private final Map<String, List<Record>> priceMids = new HashMap<String, List<Record>>();
	// 存放秒级成交量的ema值
	private final Map<String, EMAObject> volumeSecEMAs = new HashMap<String, EMAObject>();
	// 秒级买压
	private final Map<String, Record> accAmountBuys = new HashMap<String, Record>();
	// 秒级卖压
	private final Map<String, Record> accAmountSells = new HashMap<String, Record>();
	// 界点集合
	private final Map<String, List<BoundaryPoint>> boundPoints = new HashMap<String, List<BoundaryPoint>>();
	// 存放界点计算时的MA1数组
	private final Map<String, List<Record>> priceMidMA1s = new HashMap<String, List<Record>>();
	// 存放界点计算时的MA2数组
	private final Map<String, List<Record>> priceMidMA2s = new HashMap<String, List<Record>>();
	// 存放界点计算时MA1-MA2的数组
	private final Map<String, List<Record>> deltaMAs = new HashMap<String, List<Record>>();
	// 存放界点更新时的位置
	private final Map<String, Long> deltaEMATimes = new HashMap<String, Long>();
	// 存放上一时刻接收到的卖盘行情快照
	private final Map<String, List<QtyPrice>> preAskLists = new HashMap<String, List<QtyPrice>>();
	// 存放上一时刻接收到的卖盘行情快照
	private final Map<String, List<QtyPrice>> preBidLists = new HashMap<String, List<QtyPrice>>();
	
	//平仓时的中间数据
	private final Map<String, EMAObject> emaAvgPrices = new HashMap<String, EMAObject>();
	
	private final Map<String, Record> maxAvgPrices = new HashMap<String, Record>();
	
	private final Map<String, Record> maxDrops = new HashMap<String, Record>();
	
	private final Map<String, EMAObject> cloxeSignalEMAPressureRates = new HashMap<String, EMAObject>();
	
	// 放量比例
	private final Map<String, Record> volumeAmps = new HashMap<String, Record>();
	// 存放秒级成交量的ema(10)的值
	private final Map<String, EMAObject> volumeSecEMA10s = new HashMap<String, EMAObject>();
	// 秒级平均卖盘挂单量
	private final Map<String, EMAObject> volumeAskV10Aves = new HashMap<String, EMAObject>();
	// 突破点集合,包含突破点涨幅
	private final Map<String, List<BoundaryPoint>> breakPoints = new HashMap<String, List<BoundaryPoint>>();
	// 由卖盘计算出来的逐笔委托
	private final Map<String, List<ApplyData>> applyDatas = new HashMap<String, List<ApplyData>>();
	
	private final Map<String, EMAObject> emaAccAmountBuys = new HashMap<String, EMAObject>();
	private final Map<String, EMAObject> emaAccAmountSells = new HashMap<String, EMAObject>();
	private final Map<String, EMAObject> emaP3AccAmountBuys = new HashMap<String, EMAObject>();
	private final Map<String, EMAObject> emaP1PressureRates = new HashMap<String, EMAObject>();
	
	private final BreakoutDataCache dataCache;
	
	private final BreakoutStrategyParam params;
	
	private final BreakoutAnalysisResult result = new BreakoutAnalysisResult();
	
	public BreakoutEPLSubscriber(BreakoutDataCache dataCache) {
		this.dataCache = dataCache;
		params = dataCache.params;
		result.getCloseSignal().setCloseMaxDrop(params.getCloseMaxDrop());
	}
	
	public class FormalQuoteSubscriber {
		
		public void update(String symbol, double midPrice, List<QtyPrice> asks, List<QtyPrice> bids, double totalVolume, double turnover, Date date,
				long time) {
			calVolumeSec(symbol, totalVolume, turnover, date, time);
			calPriceMid(symbol, midPrice, date, time);
			calVolumeAskV10AveSet(symbol, asks, date, time);
			calAccAmountBuyAndSell(symbol, asks, bids, date, time);
			calBoundaryPoints(symbol, midPrice, date, time);
			closeSignalMonitor(symbol, date, time);
			dataCache.asks.put(symbol, asks);
			dataCache.bids.put(symbol, bids);
		}
		
		private void calPriceMid(String symbol, double midPrice, Date date, long time) {
			List<Record> priceMidList = priceMids.get(symbol);
			if (priceMidList == null) {
				priceMidList = new ArrayList<Record>();
				priceMids.put(symbol, priceMidList);
			}
			
			if (priceMidList.isEmpty()) {
				priceMidList.add(new Record(date, time, midPrice));
				calEMAAvgPrice(symbol, midPrice, date, time);
				calMaxDrop(symbol, date, time);
			} else {
				Record preRecord = priceMidList.get(priceMidList.size() - 1);
				
				if (preRecord.getTime() == time) {
					preRecord.setDate(date);
					preRecord.setTime(time);
					preRecord.setValue(midPrice);
				} else {
					long gapNum = (time - preRecord.getTime()) / 1000;
					if (gapNum < 300) {
						for (long i = 1; i < gapNum; i++) {
							long gapTime = preRecord.getTime() + (i * 1000);
							Date gapDate = new Date(gapTime);
							Record gapRecord = new Record(new Date(gapTime), gapTime, preRecord.getValue());
							log.debug("update PriceMid: symbol={}, value={}, date={}, time={}", symbol, gapRecord.getValue(), gapRecord.getDate(),
									gapRecord.getTime());
							priceMidList.add(gapRecord);
							calEMAAvgPrice(symbol, preRecord.getValue(), gapDate, gapTime);
							calMaxDrop(symbol, gapDate, gapTime);
						}
					}
					priceMidList.add(new Record(date, time, midPrice));
					calEMAAvgPrice(symbol, midPrice, date, time);
					calMaxDrop(symbol, date, time);
				}
			}
			log.debug("update PriceMid: symbol={}, value={}, date={}, time={}", symbol, midPrice, date, time);
		}
		
		private void calVolumeSec(String symbol, double totalVolume, double turnover, Date date, long time) {
			Record totalVolumeRecord = totalVolumes.get(symbol);
			if (totalVolumeRecord == null) {
				totalVolumeRecord = new Record();
				totalVolumes.put(symbol, totalVolumeRecord);
			}
			
			Record turnoverRecord = turnovers.get(symbol);
			if (turnoverRecord == null) {
				turnoverRecord = new Record();
				turnovers.put(symbol, turnoverRecord);
			}
			
			EMAObject emaObject = volumeSecEMAs.get(symbol);
			if (emaObject == null) {
				emaObject = new EMAObject();
				volumeSecEMAs.put(symbol, emaObject);
			}
			
			EMAObject ema10Object = volumeSecEMA10s.get(symbol);
			if (ema10Object == null) {
				ema10Object = new EMAObject();
				volumeSecEMA10s.put(symbol, ema10Object);
			}
			
			List<Record> volumeSecList = volumeSecs.get(symbol);
			if (volumeSecList == null) {
				volumeSecList = new ArrayList<Record>();
				volumeSecs.put(symbol, volumeSecList);
			}
			
			double quantitySum = totalVolume - totalVolumeRecord.getValue();
			totalVolumeRecord.setValue(totalVolume);
			totalVolumeRecord.setDate(date);
			totalVolumeRecord.setTime(time);
			
			double sum = turnover - turnoverRecord.getValue();
			turnoverRecord.setValue(turnover);
			turnoverRecord.setDate(date);
			turnoverRecord.setTime(time);
			
			if (volumeSecList.isEmpty()) {
				volumeSecList.add(new Record(date, time, quantitySum, sum));
				emaObject.index++;
				emaObject.date = new Date(time);
				emaObject.ema = quantitySum;
				ema10Object.index++;
				ema10Object.date = new Date(time);
				ema10Object.ema = quantitySum;
				calVolumeAmp(emaObject, ema10Object, symbol, date, time);
			} else {
				Record preRecord = volumeSecList.get(volumeSecList.size() - 1);
				
				if (preRecord.getTime() == time) {
					preRecord.setDate(date);
					preRecord.setTime(time);
					preRecord.setValue(quantitySum);
					preRecord.setTradeValue(sum);
					
					emaObject.ema = IndexUtil.calEmaValue(emaObject.preEMA, quantitySum, emaObject.index);
					ema10Object.ema = IndexUtil.calEmaValue(ema10Object.preEMA, quantitySum, ema10Object.index, 10);
					calVolumeAmp(emaObject, ema10Object, symbol, date, time);
				} else {
					long gapNum = (time - preRecord.getTime()) / 1000;
					if (gapNum < 300) {
						for (long i = 1; i < gapNum; i++) {
							long gapTime = preRecord.getTime() + (i * 1000);
							Record gapRecord = new Record(new Date(gapTime), gapTime, 0d, 0d);
							volumeSecList.add(gapRecord);
							log.debug("update VolumeSec: symbol={}, value={}, date={}, time={}", symbol, gapRecord.getValue(), gapRecord.getDate(),
									gapRecord.getTime());
							
							emaObject.index++;
							emaObject.date = new Date(gapTime);
							emaObject.preEMA = emaObject.ema;
							emaObject.ema = IndexUtil.calEmaValue(emaObject.ema, 0d, emaObject.index);
							
							ema10Object.index++;
							ema10Object.date = new Date(gapTime);
							ema10Object.preEMA = ema10Object.ema;
							ema10Object.ema = IndexUtil.calEmaValue(ema10Object.ema, 0d, ema10Object.index, 10);
							
							calVolumeAmp(emaObject, ema10Object, symbol, date, time);
						}
					}
					volumeSecList.add(new Record(date, time, quantitySum, sum));
					
					emaObject.index++;
					emaObject.date = new Date(time);
					emaObject.preEMA = emaObject.ema;
					emaObject.ema = IndexUtil.calEmaValue(emaObject.ema, quantitySum, emaObject.index);
					
					ema10Object.index++;
					ema10Object.date = new Date(time);
					ema10Object.preEMA = ema10Object.ema;
					ema10Object.ema = IndexUtil.calEmaValue(ema10Object.ema, quantitySum, ema10Object.index, 10);
					
					calVolumeAmp(emaObject, ema10Object, symbol, date, time);
				}
			}
			log.debug("update VolumeSec: symbol={}, value={}, date={}, time={}", symbol, quantitySum, date, time);
		}
		
		private void calVolumeAmp(EMAObject emaObject, EMAObject ema10Object, String symbol, Date date, long time) {
			double volumeAmp = DecimalUtil.isZero(emaObject.ema) ? 0 : ema10Object.ema / emaObject.ema;
			Record newRecord = new Record(date, time, volumeAmp);
			volumeAmps.put(symbol, newRecord);
			
			log.debug("update VolumeAmp: symbol={}, value={}, date={}, time={}", symbol, newRecord.getValue(), date, time);
		}
		
		private void calVolumeAskV10AveSet(String symbol, List<QtyPrice> asks, Date date, long time) {
			EMAObject emaObject = volumeAskV10Aves.get(symbol);
			if (emaObject == null) {
				emaObject = new EMAObject();
				volumeAskV10Aves.put(symbol, emaObject);
			}
			
			int size = 0;
			double totalValue = 0d;
			for (QtyPrice qtyPrice : asks) {
				if (!DecimalUtil.isZero(qtyPrice.quantity)) {
					size++;
					totalValue += qtyPrice.quantity;
				}
			}
			
			double askVol10Ave = (size == 0) ? 0 : totalValue / size;
			emaObject.index++;
			emaObject.ema = IndexUtil.calEmaValue(emaObject.ema, askVol10Ave, emaObject.index);
			emaObject.date = date;
			emaObject.time = time;
			dataCache.volumeAskV10Aves.put(symbol, emaObject.ema);
			log.debug("update VolumeAskV10Ave: sybol={}, value={}, date={}, time={}", symbol, emaObject.ema, date, time);
		}
		
		private void calAccAmountBuyAndSell(String symbol, List<QtyPrice> asks, List<QtyPrice> bids, Date date, long time) {
			EMAObject emaAccAmountBuy = emaAccAmountBuys.get(symbol);
			if (emaAccAmountBuy == null) {
				emaAccAmountBuy = new EMAObject();
				emaAccAmountBuys.put(symbol, emaAccAmountBuy);
			}
			
			EMAObject emaAccAmountSell = emaAccAmountSells.get(symbol);
			if (emaAccAmountSell == null) {
				emaAccAmountSell = new EMAObject();
				emaAccAmountSells.put(symbol, emaAccAmountSell);
			}
			
			EMAObject emaP3AccAmountBuy = emaP3AccAmountBuys.get(symbol);
			if (emaP3AccAmountBuy == null) {
				emaP3AccAmountBuy = new EMAObject();
				emaP3AccAmountBuys.put(symbol, emaP3AccAmountBuy);
			}
			
			List<QtyPrice> preAsks = preAskLists.get(symbol);
			List<QtyPrice> preBids = preBidLists.get(symbol);
			if ((preAsks == null) || (preBids == null)) {
				accAmountBuys.put(symbol, new Record(date, time, 0d));
				accAmountSells.put(symbol, new Record(date, time, 0d));
				preAsks = new ArrayList<QtyPrice>();
				preBids = new ArrayList<QtyPrice>();
			}
			
			List<ApplyData> applyDataList = new ArrayList<ApplyData>();
			if (preAsks != null) {
				applyDataList.addAll(ApplyData.calAccAmountAsk(preAsks, asks));
				applyDataList.addAll(ApplyData.calAccAmountBid(preBids, bids));
			}
			applyDatas.put(symbol, applyDataList);
			
			double accBuy = 0d, accSell = 0d;
			List<Record> priceMidList = priceMids.get(symbol);
			double priceMid = priceMidList.get(priceMidList.size() - 1).getValue();
			
			for (ApplyData applyData : applyDataList) {
				if (applyData.side == Side.Bid) {
					accBuy += (applyData.price * applyData.quantity) / (1 + Math.exp((-1000 * (applyData.price - priceMid)) / priceMid));
				} else if (applyData.side == Side.Ask) {
					accSell += (applyData.price * applyData.quantity) / (1 + Math.exp((1000 * (applyData.price - priceMid)) / priceMid));
				}
			}
			
			Record accBuyRecord = new Record(date, time, accBuy);
			Record accSellRecord = new Record(date, time, accSell);
			
			Record preRecord = accAmountBuys.get(symbol);
			if (preRecord != null) {
				long gapNum = (time - preRecord.getTime()) / 1000;
				if (gapNum < 300) {
					for (long i = 1; i <= (gapNum - 1); i++) {
						long gapTime = preRecord.getTime() + (i * 1000);
						Date gapDate = new Date(gapTime);
						Record gapRecord = new Record(new Date(gapTime), gapTime, 0d);
						emaAccAmountBuy.index++;
						emaAccAmountBuy.date = new Date(gapTime);
						emaAccAmountBuy.ema = IndexUtil.calEmaValue(emaAccAmountBuy.ema, 0, emaAccAmountBuy.index, params.getPressureRatePeriod());
						
						emaAccAmountSell.index++;
						emaAccAmountSell.date = new Date(gapTime);
						emaAccAmountSell.ema = IndexUtil.calEmaValue(emaAccAmountSell.ema, 0, emaAccAmountSell.index, params.getPressureRatePeriod());
						
						calPressureRate(emaAccAmountBuy, emaAccAmountSell, symbol, gapDate, gapTime);
						
						emaP3AccAmountBuy.index++;
						emaP3AccAmountBuy.date = new Date(gapTime);
						emaP3AccAmountBuy.ema = IndexUtil.calEmaValue(emaP3AccAmountBuy.ema, 0, emaP3AccAmountBuy.index, params.getSignalParaP3());
						
						log.debug("update AccAmountBuy: symbol={}, value={}, date={}, time={}", symbol, gapRecord.getValue(), gapRecord.getDate(),
								gapRecord.getTime());
						log.debug("update AccAmountSell: symbol={}, value={}, date={}, time={}", symbol, gapRecord.getValue(), gapRecord.getDate(),
								gapRecord.getTime());
					}
				}
			}
			
			emaAccAmountBuy.index++;
			emaAccAmountBuy.date = new Date(time);
			emaAccAmountBuy.ema = IndexUtil.calEmaValue(emaAccAmountBuy.ema, accBuy, emaAccAmountBuy.index, params.getPressureRatePeriod());
			
			emaAccAmountSell.index++;
			emaAccAmountSell.date = new Date(time);
			emaAccAmountSell.ema = IndexUtil.calEmaValue(emaAccAmountSell.ema, accSell, emaAccAmountSell.index, params.getPressureRatePeriod());
			
			calPressureRate(emaAccAmountBuy, emaAccAmountSell, symbol, date, time);
			
			emaP3AccAmountBuy.index++;
			emaP3AccAmountBuy.date = new Date(time);
			emaP3AccAmountBuy.ema = IndexUtil.calEmaValue(emaP3AccAmountBuy.ema, accBuy, emaP3AccAmountBuy.index, params.getSignalParaP3());
			
			accAmountBuys.put(symbol, accBuyRecord);
			accAmountSells.put(symbol, accSellRecord);
			
			log.debug("update AccAmountBuy: symbol={}, value={}, date={}, time={}", symbol, accBuy, date, time);
			log.debug("update AccAmountSell: symbol={}, value={}, date={}, time={}", symbol, accSell, date, time);
		}
		
		private void calPressureRate(EMAObject emaAccAmountBuy, EMAObject emaAccAmountSell, String symbol, Date date, long time) {
			EMAObject emaP1PressureRate = emaP1PressureRates.get(symbol);
			if (emaP1PressureRate == null) {
				emaP1PressureRate = new EMAObject();
				emaP1PressureRates.put(symbol, emaP1PressureRate);
			}
			
			EMAObject closeSignalEMAPressureRate = cloxeSignalEMAPressureRates.get(symbol);
			if (null == closeSignalEMAPressureRate) {
				closeSignalEMAPressureRate = new EMAObject();
				cloxeSignalEMAPressureRates.put(symbol, closeSignalEMAPressureRate);
			}
			
			double pressureRate = 0;
			if (!DecimalUtil.isZero(emaAccAmountBuy.ema) || !DecimalUtil.isZero(emaAccAmountSell.ema)) {
				pressureRate = Math.log(emaAccAmountBuy.ema) - Math.log(emaAccAmountSell.ema);
			}
			
			if (pressureRate == Double.POSITIVE_INFINITY) {
				pressureRate = 0d;
			} else if (pressureRate == Double.NEGATIVE_INFINITY) {
				pressureRate = 0d;
			}
			
			emaP1PressureRate.index++;
			emaP1PressureRate.ema = IndexUtil.calEmaValue(emaP1PressureRate.ema, pressureRate, emaP1PressureRate.index, params.getSignalParaP1());
			emaP1PressureRate.date = date;
			emaP1PressureRate.time = time;
			log.debug("update emaP1PressureRate: symbol={}, value={}, date={}, time{}", symbol, emaP1PressureRate.ema, date, time);
			
			closeSignalEMAPressureRate.index++;
			closeSignalEMAPressureRate.ema = IndexUtil.calEmaValue(closeSignalEMAPressureRate.ema, pressureRate, closeSignalEMAPressureRate.index,
					params.getEmaPressureRatioLag());
			closeSignalEMAPressureRate.date = date;
			closeSignalEMAPressureRate.time = time;
			log.debug("emaPressureRate: symbol={}, value={}, date={}, time{}", symbol, closeSignalEMAPressureRate.ema, date, time);
		}
		
		private void calBoundaryPoints(String symbol, double midPrice, Date date, long time) {
			List<Record> listMA1 = priceMidMA1s.get(symbol);
			if (listMA1 == null) {
				listMA1 = new ArrayList<Record>();
				priceMidMA1s.put(symbol, listMA1);
			}
			
			List<Record> listMA2 = priceMidMA2s.get(symbol);
			if (listMA2 == null) {
				listMA2 = new ArrayList<Record>();
				priceMidMA2s.put(symbol, listMA2);
			}
			
			List<Record> listDeltaMA = deltaMAs.get(symbol);
			if (listDeltaMA == null) {
				listDeltaMA = new ArrayList<Record>();
				deltaMAs.put(symbol, listDeltaMA);
			}
			
			List<BoundaryPoint> boundPointList = boundPoints.get(symbol);
			if (boundPointList == null) {
				boundPointList = new ArrayList<BoundaryPoint>();
				boundPoints.put(symbol, boundPointList);
			}
			
			Long deltaEMATime = deltaEMATimes.get(symbol);
			if (deltaEMATime == null) {
				deltaEMATime = 0l;
			}
			
			int preBoundPointSize = boundPointList.size();
			deltaEMATime = BoundaryPoint.updateBoundaryPoints(priceMids.get(symbol), volumeSecs.get(symbol), listMA1, params.getBoundaryPointN1(),
					listMA2, params.getBoundaryPointN2(), listDeltaMA, deltaEMATime, boundPointList, params.getWaveNum(), time, 0);
			deltaEMATimes.put(symbol, deltaEMATime);
			
			if (preBoundPointSize < boundPointList.size()) {
				calBreakPoints(symbol, boundPointList, midPrice, date, time);
			}
			
			log.debug("update BoundPoints:symbol={}, boundPoints={}", symbol, boundPointList);
		}
		
		private void calBreakPoints(String symbol, List<BoundaryPoint> boundPointList, double midPrice, Date date, long time) {
			List<BoundaryPoint> newBreakPoints = BoundaryPoint.calBreakPoints(boundPointList, time, midPrice, params.getBreakPointR(),
					params.getBreakPointP());
			for (BoundaryPoint newBreakPoint : newBreakPoints) {
				for (BoundaryPoint oldBreakPoint : breakPoints.get(symbol)) {
					if (newBreakPoint.getTime() == oldBreakPoint.getTime()) {
						newBreakPoint.pressurePrice = oldBreakPoint.pressurePrice;
						newBreakPoint.pressureQty = oldBreakPoint.pressureQty;
						break;
					}
				}
			}
			breakPoints.put(symbol, newBreakPoints);
			
			log.debug("update BreakPoints:symbol={}, breakPoints={}", symbol, newBreakPoints);
		}
		
		private void calEMAAvgPrice(String symbol, double midPrice, Date date, long time) {
			EMAObject avgPrice = emaAvgPrices.get(symbol);
			if (avgPrice == null) {
				avgPrice = new EMAObject();
				emaAvgPrices.put(symbol, avgPrice);
			}
			
			Record maxAvgPrice = maxAvgPrices.get(symbol);
			if (null == maxAvgPrice) {
				maxAvgPrice = new Record();
				maxAvgPrices.put(symbol, maxAvgPrice);
			}
			
			avgPrice.index++;
			avgPrice.ema = IndexUtil.calEmaValue(avgPrice.ema, midPrice, avgPrice.index, params.getAvgPriceLag());
			avgPrice.date = date;
			avgPrice.time = time;
			log.debug("update EMAAvgPrice: Symbol={}, value={}, date={}, time={}", symbol, avgPrice.ema, date, time);
			
			if (!DecimalUtil.isZero(dataCache.holdingPositions.get(symbol)) || dataCache.orderInfoSet.containsKey(symbol)) {
				maxAvgPrice.setValue(Math.max(maxAvgPrice.getValue(), avgPrice.ema));
				log.debug("at {} EMAAvgPrice={}, maxDrop={}", avgPrice.date, avgPrice.ema, (maxAvgPrice.getValue() / avgPrice.ema) - 1);
			} else {
				maxAvgPrice.setValue(avgPrice.ema);
			}
			maxAvgPrice.setTime(time);
			maxAvgPrice.setDate(date);
			log.debug("update MaxAvgPrice: Symbol={}, value={}, date={}, time={}", symbol, maxAvgPrice.getValue(), date, time);
		}
		
		private void calMaxDrop(String symbol, Date date, long time) {
			EMAObject avgPrice = emaAvgPrices.get(symbol);
			Record maxAvgPrice = maxAvgPrices.get(symbol);
			
			Record maxDrop = maxDrops.get(symbol);
			if (null == maxDrop) {
				maxDrop = new Record();
				maxDrops.put(symbol, maxDrop);
			}
			
			maxDrop.setValue((maxAvgPrice.getValue() / avgPrice.ema) - 1);
			maxDrop.setDate(date);
			maxDrop.setTime(time);
			
			log.debug("update MaxDrop: symbol={}, value={}, date={}, time={}", symbol, maxDrop.getValue(), date, time);
		}
		
		private void closeSignalMonitor(String symbol, Date date, long time) {
			BreakoutCloseSignal closeSignal = result.getCloseSignal();
			closeSignal.setSymbol(symbol);
			closeSignal.setDate(date);
			closeSignal.setTime(time);
			closeSignal.setEmaAvgPrice(emaAvgPrices.get(symbol).ema);
			closeSignal.setEmaPressureRatio(cloxeSignalEMAPressureRates.get(symbol).ema);
			closeSignal.setMaxDrop(maxDrops.get(symbol).getValue());
		}
	}
	
	public class TradeIndexSubscriber {
		
		private final Comparator<QtyPrice> comparator = new Comparator<QtyPrice>() {
			
			@Override
			public int compare(QtyPrice o1, QtyPrice o2) {
				if (DecimalUtil.greaterThan(o1.quantity, o2.quantity))
					return 1;
				else if (DecimalUtil.lessThan(o1.quantity, o2.quantity))
					return -1;
				else return 0;
			}
		};
		
		public void update(String symbol, double preClosePx, List<QtyPrice> asks, List<QtyPrice> bids) {
			List<QtyPrice> preAsks = preAskLists.get(symbol);
			openSignalMonitor(symbol, preAsks, asks, preClosePx);
			preAskLists.put(symbol, asks);
			preBidLists.put(symbol, bids);
		}
		
		private void openSignalMonitor(String symbol, List<QtyPrice> preAsks, List<QtyPrice> asks, double preClosePx) {
			List<BoundaryPoint> boundPointList = boundPoints.get(symbol);
			List<BoundaryPoint> breakPointList = breakPoints.get(symbol);
			
			List<Record> priceMidList = priceMids.get(symbol);
			double priceMid = priceMidList.get(priceMidList.size() - 1).getValue();
			Date date = priceMidList.get(priceMidList.size() - 1).getDate();
			
			if ((boundPointList == null) || boundPointList.isEmpty() || breakPointList.isEmpty()) {
				BreakoutOpenSignal openSignal = createReportWithoutBP(symbol, priceMid, preClosePx);
				openSignal.setTimeStamp(date.getTime());
				result.add(openSignal);
				log.debug("at {} creating StrategyReport: {}", date, openSignal);
			} else {
				for (BoundaryPoint breakPoint : breakPointList) {
					BreakoutOpenSignal openSignal = new BreakoutOpenSignal();
					openSignal.setTimeStamp(date.getTime());
					if (DecimalUtil.greaterThan(priceMid, breakPoint.getValue() * (1 + params.getLowBoundary1()))
							|| DecimalUtil.lessThan(priceMid, breakPoint.getValue() * (1 - params.getLowBoundary1()))) {
						breakPoint.pressurePrice = 0d;
						breakPoint.pressureQty = 0d;
						updateStrategyReport(openSignal, symbol, breakPoint, priceMid, asks, preClosePx, 0);
						result.add(openSignal);
						log.debug("at {} creating StrategyReport: {}", date, openSignal);
					} else if (boundPointList.get(boundPointList.size() - 1).getTime() != breakPoint.getTime()) {
						stockBet(symbol, priceMid, breakPoint, preAsks, asks, preClosePx, date, openSignal);
					}
				}
			}
		}
		
		private void stockBet(String symbol, double priceMid, BoundaryPoint breakPoint, List<QtyPrice> preAsks, List<QtyPrice> asks,
				double preClosePx, Date date, BreakoutOpenSignal openSignal) {
			log.debug("entering into bet analysis: symbol={}", symbol);
			
			double qtyStd = calQtyStandarlization(preAsks, openSignal);
			
			if (DecimalUtil.lessThan(qtyStd, params.getQtyStandardThreshold())) {
				double avgSixQty = calAvgSixQty(preAsks);
				boolean crtPBExisted = false;
				
				List<QtyPrice> upBoundPressures = new ArrayList<QtyPrice>();
				List<QtyPrice> inBoundPressures = new ArrayList<QtyPrice>();
				double askQtyThreshold = Math.max(breakPoint.pressureQty * params.getParaRate(),
						params.getMinPressureRate() * Math.min(avgSixQty, volumeAskV10Aves.get(symbol).ema));
				for (QtyPrice ask : preAsks) {
					if (DecimalUtil.equal(breakPoint.pressurePrice, ask.price)) {
						crtPBExisted = true;
					}
					
					if (DecimalUtil.greaterThan(ask.getQuantity(), askQtyThreshold)) {
						if (DecimalUtil.greaterThan(ask.price, breakPoint.getValue() * (1 + params.getLowBoundary2()))) {
							upBoundPressures.add(ask);
						} else if (DecimalUtil.equalGreaterThan(ask.price, breakPoint.getValue() * (1 - params.getLowBoundary2()))) {
							inBoundPressures.add(ask);
						}
					}
				}
				
				if (!inBoundPressures.isEmpty()) {
					QtyPrice candidatePressure = inBoundPressures.get(inBoundPressures.size() - 1);
					if ((!crtPBExisted || DecimalUtil.greaterThan(candidatePressure.price, breakPoint.pressurePrice))
							&& DecimalUtil.greaterThan(candidatePressure.price * candidatePressure.quantity, params.getAskValueThreshold())) {
						breakPoint.pressurePrice = candidatePressure.price;
						breakPoint.pressureQty = candidatePressure.quantity;
					} else if (DecimalUtil.equal(candidatePressure.price, breakPoint.pressurePrice)) {
						breakPoint.pressureQty = Math.max(breakPoint.pressureQty, candidatePressure.quantity);
					}
					
					if (!upBoundPressures.isEmpty()) {
						double upBoundPrice = upBoundPressures.get(0).price;
						double profiteRate = (1d + params.getPressureUpdatePara()) * breakPoint.pressurePrice;
						profiteRate = Math.floor(profiteRate * 100d) / 100d;
						if (DecimalUtil.lessThan(upBoundPrice, profiteRate)) {
							breakPoint.pressurePrice = 0d;
							breakPoint.pressureQty = 0d;
						}
					}
				}
				
				updateStrategyReport(openSignal, symbol, breakPoint, priceMid, asks, preClosePx, qtyStd);
				result.add(openSignal);
				
				log.debug("at {} creating StrategyReport: {}", date, openSignal);
				if (openSignal.isOpenOrderSignal()) {
					log.debug("at {} hitting one open signal: {}", date, openSignal);
					breakPoint.pressurePrice = 0d;
					breakPoint.pressureQty = 0d;
				}
			}
		}
		
		private void updateStrategyReport(BreakoutOpenSignal report, String symbol, BoundaryPoint breakPoint, Double priceMid, List<QtyPrice> asks,
				double preClosePx, double qtyStd) {
			report.setSymbol(symbol);
			report.setDistPriceMidToBreakPoint(priceMid - breakPoint.getValue());
			report.setBreakPrice(breakPoint.getValue());
			report.setPressurePrice(breakPoint.pressurePrice);
			
			if (DecimalUtil.lessThan(qtyStd, params.getQtyStandardThreshold())) {
				report.setSumStandardAskVolume(new IndexRecord(true, qtyStd));
			} else {
				report.setSumStandardAskVolume(new IndexRecord(false, qtyStd));
			}
			
			// 更新阻力位挂单量
			List<ApplyData> applyDataList = applyDatas.get(symbol);
			boolean isValid = true;
			double maxPrice = 0d;
			
			for (QtyPrice ask : asks) {
				if (DecimalUtil.equal(breakPoint.pressurePrice, ask.price) && DecimalUtil.greaterThan(ask.quantity, breakPoint.pressureQty / 2)) {
					isValid = false;
					break;
				}
			}
			
			for (ApplyData applyData : applyDataList) {
				if (applyData.side == Side.Bid) {
					maxPrice = Math.max(maxPrice, applyData.price);
				}
			}
			
			if (!DecimalUtil.isZero(breakPoint.pressurePrice) && isValid && DecimalUtil.equalGreaterThan(maxPrice, breakPoint.pressurePrice)) {
				report.setPressureVolume(new IndexRecord(true, breakPoint.pressureQty));
				breakPoint.pressurePrice = 0d;
				breakPoint.pressureQty = 0d;
			} else {
				report.setPressureVolume(new IndexRecord(false, breakPoint.pressureQty));
			}
			
			// 更新 EMA(PressureRate,P1)
			double emaPressureRateP1 = emaP1PressureRates.get(symbol).ema;
			
			if (DecimalUtil.greaterThan(emaPressureRateP1, params.getSignalParaP2())) {
				report.setEmaPressureRatio(new IndexRecord(true, emaPressureRateP1));
			} else {
				report.setEmaPressureRatio(new IndexRecord(false, emaPressureRateP1));
			}
			
			// 更新EMA(AccAmountBuy,P3)
			double emaAccAmountBuyP4 = emaP3AccAmountBuys.get(symbol).ema;
			if (DecimalUtil.greaterThan(emaAccAmountBuyP4,
					params.getSignalParaP4() * 1000 * Math.max(Math.abs((breakPoint.getValue() - priceMid) / priceMid), 0.001d))) {
				report.setEmaAccAmountBuy(new IndexRecord(true, emaAccAmountBuyP4));
			} else {
				report.setEmaAccAmountBuy(new IndexRecord(false, emaAccAmountBuyP4));
			}
			
			// 更新前一个低界点到当前中间价的涨幅
			List<BoundaryPoint> boundPointList = boundPoints.get(symbol);
			double lowBoundChangePct = (priceMid / boundPointList.get(boundPointList.size() - 1).getValue()) - 1;
			if (DecimalUtil.lessThan(lowBoundChangePct, params.getSignalParaP5() * breakPoint.getRange())) {
				report.setLowBoundaryChangePct(new IndexRecord(true, lowBoundChangePct));
			} else {
				report.setLowBoundaryChangePct(new IndexRecord(false, lowBoundChangePct));
			}
			
			// 更新VolumeAmp
			double volumeAmp = volumeAmps.get(symbol).getValue();
			if (DecimalUtil.greaterThan(volumeAmp, params.getSignalParaP6())) {
				report.setVolumeAmp(new IndexRecord(true, volumeAmp));
			} else {
				report.setVolumeAmp(new IndexRecord(false, volumeAmp));
			}
			
			// 更新突破点时刻的平均振幅
			double avgAmplitude = breakPoint.avgAmplitude;
			if (DecimalUtil.greaterThan(avgAmplitude, params.getSignalParaP7())) {
				report.setAvgVariation(new IndexRecord(true, avgAmplitude));
			} else {
				report.setAvgVariation(new IndexRecord(false, avgAmplitude));
			}
			
			// 更新突破点时刻平均振幅成交额
			double avgAmplitudeTradeVol = breakPoint.avgAmplitudeTradeValue;
			if (DecimalUtil.greaterThan(avgAmplitudeTradeVol, params.getSignalParaP8())) {
				report.setAvgTradeValue(new IndexRecord(true, avgAmplitudeTradeVol));
			} else {
				report.setAvgTradeValue(new IndexRecord(false, avgAmplitudeTradeVol));
			}
			
			// 更新平均百分位幅度
			double avgAmplitudePct = breakPoint.avgAmplitudePct;
			if (DecimalUtil.greaterThan(avgAmplitudePct, params.getSignalParaP9() / 100d)) {
				report.setAvgVariationAbs(new IndexRecord(true, avgAmplitudePct));
			} else {
				report.setAvgVariationAbs(new IndexRecord(false, avgAmplitudePct));
			}
			
			// 更新当前中间价涨幅
			double pxChangePct = (priceMid / preClosePx) - 1;
			if (DecimalUtil.lessThan(priceMid, preClosePx * (1 + params.getSignalParaP10()))) {
				report.setChangePct(new IndexRecord(true, pxChangePct));
			} else {
				report.setChangePct(new IndexRecord(false, pxChangePct));
			}
		}
		
		private BreakoutOpenSignal createReportWithoutBP(String symbol, double priceMid, double preClosePx) {
			BreakoutOpenSignal report = new BreakoutOpenSignal();
			report.setSymbol(symbol);
			report.setDistPriceMidToBreakPoint(0d);
			report.setBreakPrice(0d);
			report.setPressurePrice(0d);
			report.setSumStandardAskVolume(new IndexRecord(false, 0d));
			report.setPressureVolume(new IndexRecord(false, 0d));
			report.setEmaPressureRatio(new IndexRecord(false, 0d));
			report.setEmaAccAmountBuy(new IndexRecord(false, 0d));
			report.setLowBoundaryChangePct(new IndexRecord(false, 0d));
			
			double volumeAmp = volumeAmps.get(symbol).getValue();
			report.setVolumeAmp(new IndexRecord(DecimalUtil.greaterThan(volumeAmp, params.getSignalParaP6()), volumeAmp));
			report.setAvgVariation(new IndexRecord(false, 0d));
			report.setAvgTradeValue(new IndexRecord(false, 0d));
			report.setAvgVariationAbs(new IndexRecord(false, 0d));
			
			double pxChangePct = (priceMid / preClosePx) - 1;
			if (DecimalUtil.lessThan(priceMid, preClosePx * (1 + params.getSignalParaP10()))) {
				report.setChangePct(new IndexRecord(true, pxChangePct));
			} else {
				report.setChangePct(new IndexRecord(false, pxChangePct));
			}
			
			return report;
		}
		
		private double calQtyStandarlization(List<QtyPrice> asks, BreakoutOpenSignal report) {
			double max = 0d, min = Double.POSITIVE_INFINITY, sum = 0d;
			int num = 0;
			for (QtyPrice qtyPrice : asks) {
				if (!DecimalUtil.isZero(qtyPrice.price)) {
					max = Math.max(qtyPrice.price, max);
					min = Math.min(qtyPrice.price, min);
					sum += qtyPrice.price;
					num++;
				}
			}
			
			return (sum - (min * num)) / (max - min) / num;
		}
		
		private double calAvgSixQty(List<QtyPrice> asks) {
			List<QtyPrice> orderAsks = new ArrayList<QtyPrice>(asks);
			orderAsks.sort(comparator);
			double sum = 0d;
			for (int i = 2; i < 8; i++) {
				sum += orderAsks.get(i).quantity;
			}
			return sum / 6;
		}
	}
	
	public class MarketCloseSubscriber {
		public void update(Date timestamp) {
			MarketAtCloseSignal signal = result.getMarketAtCloseSignal();
			signal.setDate(timestamp);
			signal.setMarketAtClose(true);
		}
	}
	
	public void clearResult() {
		result.clearAll();
	}
	
	public BreakoutAnalysisResult getResult() {
		return result;
	}
}
