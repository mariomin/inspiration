package com.huatai.common.marketdata;

import java.util.ArrayList;
import java.util.List;

import com.htsc.mdc.model.EMDLevelProtos.EMDLevel;
import com.htsc.mdc.model.MDFutureRecordProtos.MDFutureRecord;
import com.htsc.mdc.model.MDIndexRecordProtos.MDIndexRecord;
import com.htsc.mdc.model.MDOptionRecordProtos.MDOptionRecord;
import com.htsc.mdc.model.MDStockRecordProtos.MDStockRecord;
import com.htsc.mdc.model.MDTransactionRecordProtos.MDTransactionRecord;
import com.huatai.common.marketdata.Depth.DepthSide;
import com.huatai.common.marketdata.Trade.Side;
import com.huatai.common.marketdata.mdc.MDCDepth;
import com.huatai.common.marketdata.mdc.MDCFuture;
import com.huatai.common.marketdata.mdc.MDCIndex;
import com.huatai.common.marketdata.mdc.MDCOption;
import com.huatai.common.marketdata.mdc.MDCQuote;
import com.huatai.common.marketdata.mdc.MDCTrade;
import com.huatai.common.type.QtyPrice;
import com.huatai.common.type.SecurityType;
import com.huatai.common.util.TimestampUtil;

public class MDCMarketDataUtil {
	
	public static MDCDepth createDepth(MDStockRecord mdRecord, DepthSide side) {
		MDCDepth depth = new MDCDepth(mdRecord.getHTSCSecurityID());
		depth.setTimestamp(TimestampUtil.convertStringToDate(mdRecord.getMDDate() + mdRecord.getMDTime()));
		
		if (side == DepthSide.Bid) {
			depth.setSide(DepthSide.Bid.value());
			depth.setPrice(mdRecord.getBuy1Price());
			depth.setOrders((long) mdRecord.getBuy1OrderQty());
			depth.setVolumes(mdRecord.getBuy1OrderDetailList());
		} else {
			depth.setSide(DepthSide.Offer.value());
			depth.setPrice(mdRecord.getSell1Price());
			depth.setOrders((long) mdRecord.getSell1OrderQty());
			depth.setVolumes(mdRecord.getSell1OrderDetailList());
		}
		
		return depth;
	}
	
	public static MDCFuture createMDCFuture(MDFutureRecord mdRecord, String id) {
		MDCFuture mdcFuture = new MDCFuture(mdRecord.getHTSCSecurityID());
		mdcFuture.setId(id);
		mdcFuture.bids = createBidQtyPriceList(mdRecord);
		mdcFuture.asks = createAskQtyPriceList(mdRecord);
		mdcFuture.setTimestamp(TimestampUtil.convertStringToDate(mdRecord.getMDDate() + mdRecord.getMDTime()));
		mdcFuture.setStatus(Integer.parseInt(mdRecord.getTradingPhaseCode()));
		mdcFuture.setPreviousClosingPx(mdRecord.getPreClosePx());
		mdcFuture.setOpenPx(mdRecord.getOpenPx());
		mdcFuture.setHighPx(mdRecord.getHighPx());
		mdcFuture.setLowPx(mdRecord.getLowPx());
		mdcFuture.setLastPx(mdRecord.getLastPx());
		mdcFuture.setTotalVolume((mdRecord.getTotalVolumeTrade()));
		mdcFuture.setTurnover(mdRecord.getTotalValueTrade());
		mdcFuture.setHighLimitedPx(mdRecord.getMaxPx());
		mdcFuture.setLowLimitedPx(mdRecord.getMinPx());
		mdcFuture.setPreviousOpenInterest(mdRecord.getPreOpenInterest());
		mdcFuture.setPreviousSettlementPx(mdRecord.getPreSettlePrice());
		mdcFuture.setOpenInterest(mdRecord.getOpenInterest());
		mdcFuture.setSettlementPx(mdRecord.getSettlePrice());
		mdcFuture.setSecurityType(SecurityType.FUT);
		
		return mdcFuture;
	}
	
	public static MDCQuote createMDCQuote(MDStockRecord mdRecord, String id) {
		MDCQuote quote = new MDCQuote(mdRecord.getHTSCSecurityID());
		quote.setId(id);
		quote.bids = createBidQtyPriceList(mdRecord);
		quote.asks = createAskQtyPriceList(mdRecord);
		quote.setTimestamp(TimestampUtil.convertStringToDate(mdRecord.getMDDate() + mdRecord.getMDTime()));
		quote.setStatus(Integer.parseInt(mdRecord.getTradingPhaseCode()));
		quote.setPreviousClosingPx(mdRecord.getPreClosePx());
		quote.setOpenPx(mdRecord.getOpenPx());
		quote.setHighPx(mdRecord.getHighPx());
		quote.setLowPx(mdRecord.getLowPx());
		quote.setLastPx(mdRecord.getLastPx());
		quote.setTrades(mdRecord.getNumTrades());
		quote.setTotalVolume((mdRecord.getTotalVolumeTrade()));
		quote.setTurnover(mdRecord.getTotalValueTrade());
		quote.setBidVol(mdRecord.getTotalBidQty());
		quote.setAskVol(mdRecord.getTotalOfferQty());
		quote.setBid(mdRecord.getWeightedAvgBidPx());
		quote.setAsk(mdRecord.getWeightedAvgOfferPx());
		quote.setHighLimitedPx(mdRecord.getMaxPx());
		quote.setLowLimitedPx(mdRecord.getMinPx());
		quote.setSecurityType(SecurityType.CS);
		
		if (mdRecord.getMDLevel() == EMDLevel.LevelOne) {
			quote.setLevel(Level.five);
		} else {
			quote.setLevel(Level.full);
		}
		
		return quote;
	}
	
	public static MDCOption createMDCQuote(MDOptionRecord mdRecord, String id) {
		MDCOption quote = new MDCOption(mdRecord.getHTSCSecurityID());
		quote.setId(id);
		quote.bids = createBidQtyPriceList(mdRecord);
		quote.asks = createAskQtyPriceList(mdRecord);
		quote.setTimestamp(TimestampUtil.convertStringToDate(mdRecord.getMDDate() + mdRecord.getMDTime()));
		quote.setStatus(Integer.parseInt(mdRecord.getTradingPhaseCode()));
		quote.setPreviousClosingPx(mdRecord.getPreClosePx());
		quote.setOpenPx(mdRecord.getOpenPx());
		quote.setHighPx(mdRecord.getHighPx());
		quote.setLowPx(mdRecord.getLowPx());
		quote.setLastPx(mdRecord.getLastPx());
		quote.setTrades(mdRecord.getNumTrades());
		quote.setTotalVolume((mdRecord.getTotalVolumeTrade()));
		quote.setTurnover(mdRecord.getTotalValueTrade());
		quote.setBidVol(mdRecord.getTotalBidQty());
		quote.setAskVol(mdRecord.getTotalOfferQty());
		quote.setBid(mdRecord.getWeightedAvgBidPx());
		quote.setAsk(mdRecord.getWeightedAvgOfferPx());
		quote.setHighLimitedPx(mdRecord.getMaxPx());
		quote.setLowLimitedPx(mdRecord.getMinPx());
		quote.setPreviousOpenInterest(mdRecord.getPreOpenInterest());
		quote.setPreviousSettlementPx(mdRecord.getPreSettlePrice());
		quote.setOpenInterest(mdRecord.getOpenInterest());
		quote.setSettlementPx(mdRecord.getSettlePrice());
		quote.setSecurityType(SecurityType.OPT);
		
		return quote;
	}
	
	public static MDCTrade createTrade(MDTransactionRecord mdTransctRecord) {
		MDCTrade trade = new MDCTrade(mdTransctRecord.getHTSCSecurityID());
		trade.setTimestamp(TimestampUtil.convertStringToDate(mdTransctRecord.getMDDate() + mdTransctRecord.getMDTime()));
		
		if (mdTransctRecord.getTradeBSFlag() == MDCTrade.mdcBidSide) {
			trade.setSide(Side.Bid.value());
		} else if (mdTransctRecord.getTradeBSFlag() == MDCTrade.mdcOfferSide) {
			trade.setSide(Side.Offer.value());
		} else {
			trade.setSide(Side.Unknown.value());
		}
		
		trade.setPrice(mdTransctRecord.getTradePrice());
		trade.setQuantity(mdTransctRecord.getTradeQty());
		trade.setTurnover(mdTransctRecord.getTradeMoney());
		
		return trade;
	}
	
	/**
	 * 将MDIndexRecord转换为ATS_Business内部Model
	 * @param mdIndexRecord MDIndexRecord
	 * @return ATS_Business内部的Model
	 */
	public static MDCIndex createIndex(MDIndexRecord mdIndexRecord) {
		MDCIndex record = new MDCIndex(mdIndexRecord.getHTSCSecurityID());
		record.setLevel(Level.full);
		record.setTimestamp(TimestampUtil.convertStringToDate(mdIndexRecord.getMDDate() + mdIndexRecord.getMDTime()));
		record.setLastPx(mdIndexRecord.getLastPx());
		record.setHighPx(mdIndexRecord.getHighPx());
		record.setLowPx(mdIndexRecord.getLowPx());
		record.setOpenPx(mdIndexRecord.getOpenPx());
		record.setPreviousClosingPx(mdIndexRecord.getPreClosePx());
		record.setTotalVolume(mdIndexRecord.getTotalVolumeTrade());
		record.setTurnover(mdIndexRecord.getTotalValueTrade());
		
		return record;
	}
	
	private static List<QtyPrice> createBidQtyPriceList(MDOptionRecord mdRecord) {
		List<QtyPrice> qtyPriceList = new ArrayList<QtyPrice>();
		
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy1OrderQty(), mdRecord.getBuy1Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy2OrderQty(), mdRecord.getBuy2Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy3OrderQty(), mdRecord.getBuy3Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy4OrderQty(), mdRecord.getBuy4Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy5OrderQty(), mdRecord.getBuy5Price()));
		
		return qtyPriceList;
	}
	
	private static List<QtyPrice> createAskQtyPriceList(MDOptionRecord mdRecord) {
		List<QtyPrice> qtyPriceList = new ArrayList<QtyPrice>();
		
		qtyPriceList.add(new QtyPrice(mdRecord.getSell1OrderQty(), mdRecord.getSell1Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell2OrderQty(), mdRecord.getSell2Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell3OrderQty(), mdRecord.getSell3Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell4OrderQty(), mdRecord.getSell4Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell5OrderQty(), mdRecord.getSell5Price()));
		
		return qtyPriceList;
	}
	
	private static List<QtyPrice> createBidQtyPriceList(MDStockRecord mdRecord) {
		List<QtyPrice> qtyPriceList = new ArrayList<QtyPrice>();
		
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy1OrderQty(), mdRecord.getBuy1Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy2OrderQty(), mdRecord.getBuy2Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy3OrderQty(), mdRecord.getBuy3Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy4OrderQty(), mdRecord.getBuy4Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy5OrderQty(), mdRecord.getBuy5Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy6OrderQty(), mdRecord.getBuy6Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy7OrderQty(), mdRecord.getBuy7Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy8OrderQty(), mdRecord.getBuy8Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy9OrderQty(), mdRecord.getBuy9Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy10OrderQty(), mdRecord.getBuy10Price()));
		
		return qtyPriceList;
	}
	
	private static List<QtyPrice> createAskQtyPriceList(MDStockRecord mdRecord) {
		List<QtyPrice> qtyPriceList = new ArrayList<QtyPrice>();
		
		qtyPriceList.add(new QtyPrice(mdRecord.getSell1OrderQty(), mdRecord.getSell1Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell2OrderQty(), mdRecord.getSell2Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell3OrderQty(), mdRecord.getSell3Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell4OrderQty(), mdRecord.getSell4Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell5OrderQty(), mdRecord.getSell5Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell6OrderQty(), mdRecord.getSell6Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell7OrderQty(), mdRecord.getSell7Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell8OrderQty(), mdRecord.getSell8Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell9OrderQty(), mdRecord.getSell9Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell10OrderQty(), mdRecord.getSell10Price()));
		
		return qtyPriceList;
	}
	
	private static List<QtyPrice> createBidQtyPriceList(MDFutureRecord mdRecord) {
		List<QtyPrice> qtyPriceList = new ArrayList<QtyPrice>();
		
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy1OrderQty(), mdRecord.getBuy1Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy2OrderQty(), mdRecord.getBuy2Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy3OrderQty(), mdRecord.getBuy3Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy4OrderQty(), mdRecord.getBuy4Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getBuy5OrderQty(), mdRecord.getBuy5Price()));
		
		return qtyPriceList;
	}
	
	private static List<QtyPrice> createAskQtyPriceList(MDFutureRecord mdRecord) {
		List<QtyPrice> qtyPriceList = new ArrayList<QtyPrice>();
		
		qtyPriceList.add(new QtyPrice(mdRecord.getSell1OrderQty(), mdRecord.getSell1Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell2OrderQty(), mdRecord.getSell2Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell3OrderQty(), mdRecord.getSell3Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell4OrderQty(), mdRecord.getSell4Price()));
		qtyPriceList.add(new QtyPrice(mdRecord.getSell5OrderQty(), mdRecord.getSell5Price()));
		
		return qtyPriceList;
	}
}
