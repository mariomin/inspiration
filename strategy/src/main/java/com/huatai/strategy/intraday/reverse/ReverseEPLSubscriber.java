package com.huatai.strategy.intraday.reverse;

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

public class ReverseEPLSubscriber {
	private final Logger log = LoggerFactory.getLogger(ReverseEPLSubscriber.class);
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
	
	private final Map<String, List<Record>> emaRevLagAvgPrices = new HashMap<String, List<Record>>();
	
	private final Map<String, Record> maxDrops = new HashMap<String, Record>();
	
	private final Map<String, EMAObject> cloxeSignalEMAPressureRates = new HashMap<String, EMAObject>();
	
	// 秒级平均卖盘挂单量
	private final Map<String, EMAObject> volumeAskV10Aves = new HashMap<String, EMAObject>();
	// 由卖盘计算出来的逐笔委托
	private final Map<String, List<ApplyData>> applyDatas = new HashMap<String, List<ApplyData>>();
	
	private final Map<String, EMAObject> emaAccAmountBuys = new HashMap<String, EMAObject>();
	private final Map<String, EMAObject> emaAccAmountSells = new HashMap<String, EMAObject>();
	private final Map<String, EMAObject> emaPressureRates = new HashMap<String, EMAObject>();
	private final Map<String, EMAObject> emaOrderPressures = new HashMap<String, EMAObject>();
	
	// 支撑位
	private final Map<String, List<QtyPrice>> supportPoints = new HashMap<String, List<QtyPrice>>();
	// 跌破次数
	private final Map<String, Record> pierceNums = new HashMap<String, Record>();
	
	private final Map<String, Record> preOrderPressures = new HashMap<String, Record>();
	
	// 指数相关的数据
	private final List<Record> indexPrices = new ArrayList<Record>();
	private final List<Record> indexFastEMAPrices = new ArrayList<Record>();
	private final List<Record> indexSlowEMAPrices = new ArrayList<Record>();
	private final List<Record> indexDeltaEMAPrices = new ArrayList<Record>();
	private final List<BoundaryPoint> indexBoundPointList = new ArrayList<BoundaryPoint>();
	private boolean indexStatus = true;
	private long indexDeltaEMATime = 0l;
	private long indexSpeedEnlargeNums = 0;
	
	private final List<Record> indexVolumes = new ArrayList<Record>();
	private final Record indexTotalVolume = new Record();
	private final Record indexVolAmp = new Record();
	
	private final ReverseDataCache dataCache;
	
	private final ReverseStrategyParam params;
	
	private final ReverseAnalysisResult result = new ReverseAnalysisResult();
	
	public ReverseEPLSubscriber(ReverseDataCache dataCache) {
		this.dataCache = dataCache;
		params = dataCache.params;
		result.getCloseSignal().setCloseMaxDrop(params.getCloseMaxDrop());
	}
	
	public class QuoteCalculationSubscriber {
		
		public void update(String symbol, double midPrice, List<QtyPrice> asks, List<QtyPrice> bids, double totalVolume, double turnover, Date date,
				long time) {
			calVolumeSec(symbol, totalVolume, turnover, date, time);
			calPriceMid(symbol, midPrice, date, time);
			calVolumeAskV10AveSet(symbol, asks, date, time);
			calOrderPressure(symbol, asks, bids, date, time);
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
			} else {
				Record preRecord = volumeSecList.get(volumeSecList.size() - 1);
				
				if (preRecord.getTime() == time) {
					preRecord.setDate(date);
					preRecord.setTime(time);
					preRecord.setValue(quantitySum);
					preRecord.setTradeValue(sum);
					
					emaObject.ema = IndexUtil.calEmaValue(emaObject.preEMA, quantitySum, emaObject.index);
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
						}
					}
					volumeSecList.add(new Record(date, time, quantitySum, sum));
					
					emaObject.index++;
					emaObject.date = new Date(time);
					emaObject.preEMA = emaObject.ema;
					emaObject.ema = IndexUtil.calEmaValue(emaObject.ema, quantitySum, emaObject.index);
				}
			}
			log.debug("update VolumeSec: symbol={}, value={}, date={}, time={}", symbol, quantitySum, date, time);
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
		
		private void calOrderPressure(String symbol, List<QtyPrice> asks, List<QtyPrice> bids, Date date, long time) {
			EMAObject emaOrderPressure = emaOrderPressures.get(symbol);
			if (emaOrderPressure == null) {
				emaOrderPressure = new EMAObject();
				emaOrderPressures.put(symbol, emaOrderPressure);
			}
			
			double avgBidVolume = calAvgQuantity(bids.subList(0, params.getOrderPressureN()));
			avgBidVolume = DecimalUtil.isZero(avgBidVolume) ? 1 : avgBidVolume;
			double avgAskVolume = calAvgQuantity(asks.subList(0, params.getOrderPressureN()));
			avgAskVolume = DecimalUtil.isZero(avgAskVolume) ? 1 : avgAskVolume;
			double orderPressure = Math.log(avgBidVolume) - Math.log(avgAskVolume);
			
			Record record = preOrderPressures.get(symbol);
			if (record == null) {
				record = new Record();
				preOrderPressures.put(symbol, record);
				record.setValue(orderPressure);
				record.setDate(date);
			}
			
			if (emaOrderPressure.date != null) {
				long gapNum = (time - emaOrderPressure.time) / 1000;
				if (gapNum < 300) {
					for (long i = 1; i <= (gapNum - 1); i++) {
						long gapTime = emaOrderPressure.time + (i * 1000);
						Date gapDate = new Date(gapTime);
						emaOrderPressure.date = gapDate;
						emaOrderPressure.time = gapTime;
						emaOrderPressure.index++;
						emaOrderPressure.ema = IndexUtil.calEmaValue(emaOrderPressure.ema, record.getValue(), emaOrderPressure.index,
								params.getOrderPressureLag());
					}
				}
			}
			
			record.setDate(date);
			record.setValue(orderPressure);
			emaOrderPressure.date = date;
			emaOrderPressure.time = time;
			emaOrderPressure.index++;
			emaOrderPressure.ema = IndexUtil.calEmaValue(emaOrderPressure.ema, orderPressure, emaOrderPressure.index, params.getOrderPressureLag());
		}
		
		private double calAvgQuantity(List<QtyPrice> qtyPrices) {
			double totalVolums = 0d;
			int num = 0;
			for (QtyPrice price : qtyPrices) {
				if (!DecimalUtil.isZero(price.quantity)) {
					totalVolums += price.quantity;
					num++;
				}
			}
			
			if (num > 0)
				return totalVolums / num;
			else return 0d;
			
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
			
			accAmountBuys.put(symbol, accBuyRecord);
			accAmountSells.put(symbol, accSellRecord);
			
			log.debug("update AccAmountBuy: symbol={}, value={}, date={}, time={}", symbol, accBuy, date, time);
			log.debug("update AccAmountSell: symbol={}, value={}, date={}, time={}", symbol, accSell, date, time);
		}
		
		private void calPressureRate(EMAObject emaAccAmountBuy, EMAObject emaAccAmountSell, String symbol, Date date, long time) {
			EMAObject emaPressureRate = emaPressureRates.get(symbol);
			if (emaPressureRate == null) {
				emaPressureRate = new EMAObject();
				emaPressureRates.put(symbol, emaPressureRate);
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
			
			emaPressureRate.index++;
			emaPressureRate.ema = IndexUtil.calEmaValue(emaPressureRate.ema, pressureRate, emaPressureRate.index, params.getEmaPressureRatioLag());
			emaPressureRate.date = date;
			emaPressureRate.time = time;
			log.debug("update emaP1PressureRate: symbol={}, value={}, date={}, time{}", symbol, emaPressureRate.ema, date, time);
			
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
			
			int preSize = boundPointList.size();
			deltaEMATime = BoundaryPoint.updateBoundaryPoints(priceMids.get(symbol), volumeSecs.get(symbol), listMA1, params.getBoundaryPointN1(),
					listMA2, params.getBoundaryPointN2(), listDeltaMA, deltaEMATime, boundPointList, params.getWaveNum(), time, 0);
			deltaEMATimes.put(symbol, deltaEMATime);
			if (boundPointList.size() > preSize) {
				supportPoints.get(symbol).clear();
			}
			
			log.debug("update BoundPoints:symbol={}, boundPoints={}", symbol, boundPointList);
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
			
			List<Record> emaRevLagAvgPriceList = emaRevLagAvgPrices.get(symbol);
			if (null == emaRevLagAvgPriceList) {
				emaRevLagAvgPriceList = new ArrayList<Record>();
				emaRevLagAvgPrices.put(symbol, emaRevLagAvgPriceList);
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
			
			if (emaRevLagAvgPriceList.size() > params.getRevLag()) {
				emaRevLagAvgPriceList.remove(0);
			}
			emaRevLagAvgPriceList.add(new Record(date, time, avgPrice.ema));
			
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
			ReverseCloseSignal closeSignal = result.getCloseSignal();
			closeSignal.setSymbol(symbol);
			closeSignal.setDate(date);
			closeSignal.setTime(time);
			closeSignal.setEmaOrderPressure(emaOrderPressures.get(symbol).ema);
			closeSignal.setEmaPressureRatio(cloxeSignalEMAPressureRates.get(symbol).ema);
			closeSignal.setMaxDrop(maxDrops.get(symbol).getValue());
		}
	}
	
	public class SignalAnalysisSubscriber {
		
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
			openSignalMonitor(symbol, bids, preClosePx);
			preAskLists.put(symbol, asks);
			preBidLists.put(symbol, bids);
		}
		
		private void openSignalMonitor(String symbol, List<QtyPrice> bids, double preClosePx) {
			List<QtyPrice> supportPointList = supportPoints.get(symbol);
			if (supportPointList == null) {
				supportPointList = new ArrayList<QtyPrice>();
				supportPoints.put(symbol, supportPointList);
			}
			
			Record pierceNum = pierceNums.get(symbol);
			if (pierceNum == null) {
				pierceNum = new Record();
				pierceNums.put(symbol, pierceNum);
			}
			
			List<BoundaryPoint> boundPointList = boundPoints.get(symbol);
			List<ApplyData> applyDataList = applyDatas.get(symbol);
			
			List<Record> priceMidList = priceMids.get(symbol);
			Record priceMid = priceMidList.get(priceMidList.size() - 1);
			List<Record> volumeSecList = volumeSecs.get(symbol);
			
			ReverseOpenSignal openSignal = new ReverseOpenSignal();
			openSignal.setTimeStamp(priceMid.getTime());
			
			if ((boundPointList.size() > 0) && boundPointList.get(boundPointList.size() - 1).isHigh()) {
				if (DecimalUtil.lessThan(pierceNum.getValue(), params.getMinPierceNum())) {
					updatePierceNum(symbol, supportPointList, applyDataList, bids, pierceNum);
				}
				
				if (DecimalUtil.equalGreaterThan(pierceNum.getValue(), params.getMinPierceNum())) {
					openSignal.setPierceNum(new IndexRecord(true, pierceNum.getValue()));
				} else {
					openSignal.setPierceNum(new IndexRecord(false, pierceNum.getValue()));
				}
				
				openSignal.setIndexStatus(indexStatus);
				
				BoundaryPoint boundPoint = boundPointList.get(boundPointList.size() - 1);
				int index = Record.getStartTimeIndex(priceMidList, boundPoint.createTime);
				double drop = ((priceMidList.get(index).getValue() / (priceMid.getValue())) - 1) * 1000;
				double speed = (((priceMidList.get(index).getValue() / priceMid.getValue()) - 1) / (priceMidList.size() - index)) * 60 * 1000;
				
				double sum = 0d;
				for (int i = index; i < volumeSecList.size(); i++) {
					sum += volumeSecList.get(i).getValue();
				}
				double volumeAmp = (sum / (volumeSecList.size() - index)) / volumeSecEMAs.get(symbol).ema;
				
				updateOpenSignalWithBP(openSignal, symbol, boundPoint, priceMid.getValue(), drop, volumeAmp, speed);
			} else {
				supportPointList.clear();
				pierceNum.setValue(0d);
				updateOpenSignalWithoutBP(openSignal, symbol, priceMid.getValue());
			}
			result.add(openSignal);
		}
		
		private void updatePierceNum(String symbol, List<QtyPrice> supportPointList, List<ApplyData> applyDataList, List<QtyPrice> bids,
				Record pierceNum) {
			List<QtyPrice> keptSupportPointList = new ArrayList<QtyPrice>();
			for (QtyPrice supportPoint : supportPointList) {
				if (!existSupportPointInBids(supportPoint, bids)) {
					if (existPierceSellOrder(supportPoint, applyDataList)) {
						pierceNum.setValue(pierceNum.getValue() + 1);
					}
				} else {
					if (existPierceSellOrder(supportPoint, applyDataList)) {
						keptSupportPointList.add(supportPoint);
					}
				}
			}
			supportPointList.clear();
			supportPointList.addAll(keptSupportPointList);
			
			List<Integer> satisfiedIndexes = new ArrayList<Integer>();
			double avgBidVolume = calAvgSixQty(bids);
			for (int i = 0; i < bids.size(); i++) {
				QtyPrice qtyPrice = bids.get(i);
				if (DecimalUtil.equalGreaterThan(qtyPrice.getQuantity(), params.getMinSupportRate() * avgBidVolume)
						&& DecimalUtil.equalGreaterThan(qtyPrice.getQuantity() * qtyPrice.getPrice(), params.getMinSupportAmount() * 10000)) {
					satisfiedIndexes.add(i);
				}
			}
			
			for (int i = 0; i < satisfiedIndexes.size(); i++) {
				if (i == (satisfiedIndexes.size() - 1)) {
					supportPointList.add(bids.get(satisfiedIndexes.get(i)));
				} else if ((satisfiedIndexes.get(i + 1) - satisfiedIndexes.get(i)) > 1) {
					supportPointList.add(bids.get(satisfiedIndexes.get(i)));
				}
			}
			
		}
		
		private void updateOpenSignalWithBP(ReverseOpenSignal openSignal, String symbol, BoundaryPoint boundPoint, double priceMid, double drop,
				double volumeAmp, double speed) {
			openSignal.setSymbol(symbol);
			
			if (DecimalUtil.equalGreaterThan(priceMid, params.getMinPrice())) {
				openSignal.setPriceMid(new IndexRecord(true, priceMid));
			} else {
				openSignal.setPriceMid(new IndexRecord(false, priceMid));
			}
			
			double emaAvgPrice = emaAvgPrices.get(symbol).ema;
			if (DecimalUtil.greaterThan(emaAvgPrice, emaRevLagAvgPrices.get(symbol).get(0).getValue())) {
				openSignal.setEmaAvgPrice(new IndexRecord(true, emaAvgPrice));
			} else {
				openSignal.setEmaAvgPrice(new IndexRecord(false, emaAvgPrice));
			}
			
			double emaOrderPressure = emaOrderPressures.get(symbol).ema;
			if (DecimalUtil.greaterThan(emaOrderPressure, Math.log(params.getEmaOrderPressureTriggerRate()))) {
				openSignal.setEmaOrderPressure(new IndexRecord(true, emaOrderPressure));
			} else {
				openSignal.setEmaOrderPressure(new IndexRecord(false, emaOrderPressure));
			}
			
			double emaPressureRatio = emaPressureRates.get(symbol).ema;
			if (DecimalUtil.greaterThan(emaPressureRatio, params.getTriggerPressure())) {
				openSignal.setEmaPressureRatio(new IndexRecord(true, emaPressureRatio));
			} else {
				openSignal.setEmaPressureRatio(new IndexRecord(false, emaPressureRatio));
			}
			
			if (DecimalUtil.equalGreaterThan(drop, params.getDropRate())) {
				openSignal.setDrop(new IndexRecord(true, drop));
			} else {
				openSignal.setDrop(new IndexRecord(false, drop));
			}
			
			if (DecimalUtil.equalGreaterThan(volumeAmp, params.getVolumeRate())) {
				openSignal.setVolumeAmp(new IndexRecord(true, volumeAmp));
			} else {
				openSignal.setVolumeAmp(new IndexRecord(false, volumeAmp));
			}
			
			if (DecimalUtil.equalGreaterThan(speed, params.getSpeedRate())) {
				openSignal.setSpeed(new IndexRecord(true, speed));
			} else {
				openSignal.setSpeed(new IndexRecord(false, speed));
			}
			
			// 更新突破点时刻的平均振幅
			double avgAmplitude = boundPoint.avgAmplitude;
			if (DecimalUtil.greaterThan(avgAmplitude, params.getDyRatio())) {
				openSignal.setAvgVariation(new IndexRecord(true, avgAmplitude));
			} else {
				openSignal.setAvgVariation(new IndexRecord(false, avgAmplitude));
			}
			
			// 更新突破点时刻平均振幅成交额
			double avgAmplitudeTradeVol = boundPoint.avgAmplitudeTradeValue;
			if (DecimalUtil.greaterThan(avgAmplitudeTradeVol, params.getDyAmountRatio())) {
				openSignal.setAvgTradeValue(new IndexRecord(true, avgAmplitudeTradeVol));
			} else {
				openSignal.setAvgTradeValue(new IndexRecord(false, avgAmplitudeTradeVol));
			}
			
			// 更新平均百分位幅度
			double avgAmplitudePct = boundPoint.avgAmplitudePct;
			if (DecimalUtil.greaterThan(avgAmplitudePct, params.getDyRatio2() / 100d)) {
				openSignal.setAvgVariationAbs(new IndexRecord(true, avgAmplitudePct));
			} else {
				openSignal.setAvgVariationAbs(new IndexRecord(false, avgAmplitudePct));
			}
			
		}
		
		private boolean existSupportPointInBids(QtyPrice supportPoint, List<QtyPrice> bids) {
			for (QtyPrice qtyPrice : bids) {
				if (DecimalUtil.greaterThan(qtyPrice.price, supportPoint.getPrice()))
					return false;
				else if (DecimalUtil.equal(qtyPrice.price, supportPoint.getPrice())) return true;
			}
			
			return false;
		}
		
		private boolean existPierceSellOrder(QtyPrice supportPoint, List<ApplyData> applyDatas) {
			for (ApplyData applyData : applyDatas) {
				if ((applyData.side == ApplyData.Side.Ask) && DecimalUtil.equalLessThan(applyData.price, supportPoint.getPrice())) return true;
			}
			
			return false;
		}
		
		private void updateOpenSignalWithoutBP(ReverseOpenSignal openSignal, String symbol, double priceMid) {
			openSignal.setSymbol(symbol);
			
			openSignal.setIndexStatus(indexStatus);
			openSignal.setPierceNum(new IndexRecord(false, 0));
			
			if (DecimalUtil.equalGreaterThan(priceMid, params.getMinPrice())) {
				openSignal.setPriceMid(new IndexRecord(true, priceMid));
			} else {
				openSignal.setPriceMid(new IndexRecord(false, priceMid));
			}
			
			double emaAvgPrice = emaAvgPrices.get(symbol).ema;
			if (DecimalUtil.greaterThan(emaAvgPrice, emaRevLagAvgPrices.get(symbol).get(0).getValue())) {
				openSignal.setEmaAvgPrice(new IndexRecord(true, emaAvgPrice));
			} else {
				openSignal.setEmaAvgPrice(new IndexRecord(false, emaAvgPrice));
			}
			
			double emaOrderPressure = emaOrderPressures.get(symbol).ema;
			if (DecimalUtil.greaterThan(emaOrderPressure, Math.log(params.getEmaOrderPressureTriggerRate()))) {
				openSignal.setEmaOrderPressure(new IndexRecord(true, emaOrderPressure));
			} else {
				openSignal.setEmaOrderPressure(new IndexRecord(false, emaOrderPressure));
			}
			
			double emaPressureRatio = emaPressureRates.get(symbol).ema;
			if (DecimalUtil.greaterThan(emaPressureRatio, params.getTriggerPressure())) {
				openSignal.setEmaPressureRatio(new IndexRecord(true, emaPressureRatio));
			} else {
				openSignal.setEmaPressureRatio(new IndexRecord(false, emaPressureRatio));
			}
			
			openSignal.setDrop(new IndexRecord(false, 0d));
			openSignal.setVolumeAmp(new IndexRecord(false, 0d));
			openSignal.setSpeed(new IndexRecord(false, 0d));
			openSignal.setAvgVariation(new IndexRecord(false, 0d));
			openSignal.setAvgTradeValue(new IndexRecord(false, 0d));
			openSignal.setAvgVariationAbs(new IndexRecord(false, 0d));
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
	
	public class IndexCalculationSubscriber {
		public void update(String symbol, double lastPx, double totalVolume, double turnover, Date date, long time) {
			calIndexPrice(symbol, lastPx, date, time);
			calIndexVolume(symbol, totalVolume, date, time);
			calBoundaryPoints(symbol, lastPx, date, time);
			calIndexSpeedStatus();
			if (!indexStatus) {
				log.info("index is unsatisfied:" + date);
			}
		}
		
		private void calIndexEMAPrice(String symbol, double indexPrice, Date date, long time) {
			int size = indexFastEMAPrices.size();
			double preEMA = size > 0 ? indexFastEMAPrices.get(size - 1).getValue() : 0;
			Record record = new Record();
			record.setDate(date);
			record.setTime(time);
			record.setValue(IndexUtil.calEmaValue(preEMA, indexPrice, size + 1, params.getParaFast()));
			
			indexFastEMAPrices.add(record);
			log.debug("update indexFastEMAPrice: Symbol={}, value={}, date={}, time={}", symbol, record.getValue(), date, time);
			
			size = indexSlowEMAPrices.size();
			preEMA = size > 0 ? indexSlowEMAPrices.get(size - 1).getValue() : 0;
			record = new Record();
			record.setDate(date);
			record.setTime(time);
			record.setValue(IndexUtil.calEmaValue(preEMA, indexPrice, size + 1, params.getParaSlow()));
			indexSlowEMAPrices.add(record);
			log.debug("update indexSlowEMAPrice: Symbol={}, value={}, date={}, time={}", symbol, record.getValue(), date, time);
			
		}
		
		private void calIndexPrice(String symbol, double indexPrice, Date date, long time) {
			if (!indexPrices.isEmpty()) {
				Record preRecord = indexPrices.get(indexPrices.size() - 1);
				long gapNum = (time - preRecord.getTime()) / 1000;
				if (gapNum < 300) {
					for (long i = 1; i < gapNum; i++) {
						long gapTime = preRecord.getTime() + (i * 1000);
						Record gapRecord = new Record(new Date(gapTime), gapTime, preRecord.getValue());
						log.debug("update indexPrice: symbol={}, value={}, date={}, time={}", symbol, gapRecord.getValue(), gapRecord.getDate(),
								gapRecord.getTime());
						indexPrices.add(gapRecord);
						calIndexEMAPrice(symbol, gapRecord.getValue(), gapRecord.getDate(), gapRecord.getTime());
					}
				}
			}
			indexPrices.add(new Record(date, time, indexPrice));
			calIndexEMAPrice(symbol, indexPrice, date, time);
			log.debug("update PriceMid: symbol={}, value={}, date={}, time={}", symbol, indexPrice, date, time);
		}
		
		private void calIndexVolume(String symbol, double totalVolume, Date date, long time) {
			double quantitySum = totalVolume - indexTotalVolume.getValue();
			indexTotalVolume.setValue(totalVolume);
			indexTotalVolume.setDate(date);
			indexTotalVolume.setTime(time);
			
			if (!indexVolumes.isEmpty()) {
				Record preRecord = indexVolumes.get(indexVolumes.size() - 1);
				long gapNum = (time - preRecord.getTime()) / 1000;
				if (gapNum < 300) {
					for (long i = 1; i < gapNum; i++) {
						long gapTime = preRecord.getTime() + (i * 1000);
						Record gapRecord = new Record(new Date(gapTime), gapTime, 0d, 0d);
						indexVolumes.add(gapRecord);
						log.debug("update VolumeSec: symbol={}, value={}, date={}, time={}", symbol, gapRecord.getValue(), gapRecord.getDate(),
								gapRecord.getTime());
					}
				}
			}
			indexVolumes.add(new Record(date, time, quantitySum));
			log.debug("update VolumeSec: symbol={}, value={}, date={}, time={}", symbol, quantitySum, date, time);
		}
		
		private void calBoundaryPoints(String symbol, double indexPrice, Date date, long time) {
			indexDeltaEMATime = BoundaryPoint.updateBoundaryPoints(indexPrices, indexVolumes, indexFastEMAPrices, params.getParaFast(),
					indexSlowEMAPrices, params.getParaSlow(), indexDeltaEMAPrices, indexDeltaEMATime, indexBoundPointList, params.getWaveNum(), time,
					params.getVolumePeriod() * 2);
			
			log.debug("update BoundPoints:symbol={}, boundPoints={}", symbol, indexBoundPointList);
		}
		
		private void calIndexSpeedStatus() {
			int boundSize = indexBoundPointList.size();
			if ((boundSize < 1) || !indexBoundPointList.get(boundSize - 1).isHigh()) {
				indexStatus = true;
				indexSpeedEnlargeNums = 0;
				return;
			}
			
			int size = indexPrices.size();
			Record indexPrice = indexPrices.get(size - 1);
			
			int index = Record.getStartTimeIndex(indexPrices, indexBoundPointList.get(boundSize - 1).createTime);
			if (index == -1) {
				log.warn("not found the time index of BoundPoint in priceList");
				indexStatus = true;
				indexSpeedEnlargeNums = 0;
				return;
			}
			
			double indexSpeed = calIndexSpeed();
			if ((size < (params.getVolumePeriod() * 2)) || DecimalUtil.greaterThan(indexSpeed, params.getIndexDropSpeedRate())) {
				indexStatus = true;
				indexSpeedEnlargeNums = 0;
			} else {
				if (Math.floorMod(indexSpeedEnlargeNums, params.getVolumePeriod()) == 0) {
					double volAmp = sumIndexVolumes(size - params.getVolumePeriod(), size)
							/ sumIndexVolumes(size - (2 * params.getVolumePeriod()), size - params.getVolumePeriod());
					indexVolAmp.setValue(volAmp);
					indexVolAmp.setDate(indexPrice.getDate());
					indexVolAmp.setTime(indexPrice.getTime());
				}
				
				if (DecimalUtil.equalGreaterThan(indexVolAmp.getValue(), 1)) {
					indexStatus = false;
					indexSpeedEnlargeNums++;
				} else {
					indexStatus = true;
					indexSpeedEnlargeNums = 0;
				}
				
			}
			
		}
		
		private double sumIndexVolumes(int startIndex, int endIndex) {
			double sum = 0d;
			for (int i = startIndex; i < endIndex; i++) {
				sum += indexVolumes.get(i).getValue();
			}
			return sum;
		}
		
		private double calIndexSpeed() {
			int size = indexFastEMAPrices.size();
			int index = size > params.getSpeedLag() ? size - 1 - params.getSpeedLag() : 0;
			return (((indexFastEMAPrices.get(size - 1).getValue() / indexFastEMAPrices.get(index).getValue()) - 1) / (size - index)) * 60 * 1000;
		}
		
	}
	
	public void clearResult() {
		result.clearAll();
	}
	
	public ReverseAnalysisResult getResult() {
		return result;
	}
}
