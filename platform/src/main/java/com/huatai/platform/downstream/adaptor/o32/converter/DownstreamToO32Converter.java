package com.huatai.platform.downstream.adaptor.o32.converter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.o32.AccountServiceHelper;
import com.huatai.common.o32.tags.O32Tags;
import com.huatai.common.o32.tags.O32Tags.Field;
import com.huatai.common.type.ExchangeType;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;
import com.huatai.common.type.TimeInForce;
import com.huatai.platform.downstream.adaptor.o32.common.ExecutionPosition;
import com.huatai.platform.downstream.adaptor.o32.common.O32BeanHelper;
import com.huatai.platform.downstream.adaptor.o32.common.O32Util;
import com.huatai.platform.downstream.adaptor.o32.processor.O32CallBackMethod;
import com.huatai.platform.downstream.adaptor.o32.processor.O32DataCache;
import com.huatai.platform.downstream.adaptor.o32.processor.O32StreamProcessor;
import com.hundsun.t2sdk.common.core.context.ContextUtil;
import com.hundsun.t2sdk.common.share.dataset.DatasetService;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.EventType;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

/**
 *
 * @author 010350
 *
 */
public class DownstreamToO32Converter {
	private static final Logger logger = LoggerFactory.getLogger(DownstreamToO32Converter.class);
	
	public static IEvent toExecutionQueryRequest(ExecutionPosition position) {
		String assetNo = position.getAssetNo();
		String operatorNo = position.getOperatorNo();
		String positionStr = position.getPositionStr();
		
		IEvent request = ContextUtil.getServiceContext().getEventFactory().getEventByAlias(O32Tags.FunctionId.amRealDealQuery, EventType.ET_REQUEST);
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn(O32Tags.Field.operatorNo);
		dataset.addColumn(O32Tags.Field.password);
		dataset.addColumn(O32Tags.Field.userToken);
		dataset.addColumn(O32Tags.Field.assetNo);
		dataset.addColumn(O32Tags.Field.positionStr);
		dataset.addColumn(O32Tags.Field.requestNum);
		dataset.appendRow();
		
		dataset.updateString(O32Tags.Field.operatorNo, operatorNo);
		dataset.updateString(O32Tags.Field.password, AccountServiceHelper.getInstance().getPasswdByOperaterNo(operatorNo));
		dataset.updateString(O32Tags.Field.assetNo, assetNo);
		dataset.updateString(O32Tags.Field.positionStr, positionStr);
		dataset.updateInt(O32Tags.Field.requestNum, 10000);
		
		request.putEventData(dataset);
		return request;
	}
	
	public static IEvent toNewOrderRequest(ExchangeOrder order) {
		IEvent request = ContextUtil.getServiceContext().getEventFactory().getEventByAlias(O32Tags.FunctionId.newOrderSingle, EventType.ET_REQUEST);
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn(O32Tags.Field.operatorNo);
		dataset.addColumn(O32Tags.Field.password);
		dataset.addColumn(O32Tags.Field.userToken);
		dataset.addColumn(O32Tags.Field.accountCode);
		dataset.addColumn(O32Tags.Field.assetNo);
		dataset.addColumn(O32Tags.Field.combiNo);
		dataset.addColumn(O32Tags.Field.batchNo);
		dataset.addColumn(O32Tags.Field.instanceNo);
		dataset.addColumn(O32Tags.Field.stockHolderId);
		dataset.addColumn(O32Tags.Field.marketNo);
		dataset.addColumn(O32Tags.Field.stockCode);
		dataset.addColumn(O32Tags.Field.entrustDirection);
		dataset.addColumn(O32Tags.Field.priceType);
		dataset.addColumn(O32Tags.Field.entrustPrice);
		dataset.addColumn(O32Tags.Field.entrustAmount);
		dataset.addColumn(O32Tags.Field.extsystemId);
		dataset.appendRow();
		addNewOrderRow(dataset, order);
		request.putEventData(dataset);
		
		request.setStringAttributeValue(EventTagdef.TAG_SEQUECE_NO, order.getReportNo());
		request.setStringAttributeValue(EventTagdef.TAG_SENDERID, O32CallBackMethod.SENDER_ID); //async
		
		return request;
	}
	
	private static void addNewOrderRow(IDataset dataset, ExchangeOrder order) {
		addAccountInfo(dataset, order);
		
		//		String instanceNo = order.getPortfolioNo();
		//		if (StringUtils.isNotEmpty(instanceNo)) {
		//			dataset.updateString(O32Tags.Field.instanceNo, instanceNo);
		//		}
		dataset.updateString(O32Tags.Field.marketNo, convertExchangeToMarketNo(order.getExchangeType()));
		dataset.updateString(O32Tags.Field.stockCode, order.getSecurityId());
		dataset.updateString(O32Tags.Field.entrustDirection, convert2EntrustDirection(order.getOrderSide()));
		//		String positionEffect = event.getValue(XFields.Security.positionEffect);
		//		if (StringUtils.isNotEmpty(positionEffect)) {
		//			dataset.updateString(O32Tags.Field.futuresDirection, convert2FuturesDirection(positionEffect));
		//		}
		String priceType = convertToPriceType(order.getOrderType(), order.getTimeInForce(), order.getMaxPriceLevels(), order.getMinQty(),
				order.getExchangeType());
		dataset.updateString(O32Tags.Field.priceType, priceType);
		
		Double price = order.getPrice();
		if ((null != price) && priceType.equals(O32Tags.Value.PriceType.limit)) {
			dataset.updateDouble(O32Tags.Field.entrustPrice, price);
		} else {
			dataset.updateDouble(O32Tags.Field.entrustPrice, 0d);
		}
		
		Double amount = order.getQuantity();
		if (null != amount) {
			dataset.updateDouble(O32Tags.Field.entrustAmount, order.getQuantity());
		}
		dataset.updateString(O32Tags.Field.extsystemId, order.getReportNo());
		
	}
	
	private static String convertToPriceType(OrderType orderType, Character timeInForce, Integer maxPriceLevels, Double minQty,
			ExchangeType exchangeType) {
		if (orderType == OrderType.LIMIT) return O32Tags.Value.PriceType.limit;
		
		if (ExchangeType.SZ == exchangeType) {
			if (TimeInForce.DAY == timeInForce.charValue()) {
				if (1 == maxPriceLevels) // 对手方最优剩余转限价
					return O32Tags.Value.PriceType.counterPartyOptimal4SZMarket;
				else // 本方最优
					return O32Tags.Value.PriceType.optimal4SZMarket;
			} else if (TimeInForce.IMMEDIATE_OR_CANCEL == timeInForce.charValue()) {
				if (5 == maxPriceLevels)
					// 市价最优五档全额成交剩余撤销
					return O32Tags.Value.PriceType.fifthIsLeftOff4SZMarket;
				else {
					if (0 == minQty) // 市价立即成交剩余撤销
						return O32Tags.Value.PriceType.leftOff4SZMarket;
					else // 市价全额成交或撤销
						return O32Tags.Value.PriceType.fok4SZMarket;
					
				}
			}
			
		} else if (ExchangeType.SH == exchangeType) {
			if (TimeInForce.DAY == timeInForce) {
				if (5 == maxPriceLevels) // 最优五档即时成交剩余订单转限价
					return O32Tags.Value.PriceType.fifthIsLeftTurn4SHMarket;
			} else if (TimeInForce.IMMEDIATE_OR_CANCEL == timeInForce) {
				if (5 == maxPriceLevels) // 最优五档即时成交剩余订单转限价
					return O32Tags.Value.PriceType.fifthIsLeftOff4SHMarket;
			}
		}
		
		return null;
	}
	
	private static String convert2EntrustDirection(OrderSide orderSide) {
		if (OrderSide.Buy == orderSide)
			return O32Tags.Value.EntrustDirection.buy;
		else if (OrderSide.Sell == orderSide)
			return O32Tags.Value.EntrustDirection.sell;
		else throw new IllegalArgumentException("Unkown orderSide:" + orderSide);
	}
	
	private static String convertExchangeToMarketNo(ExchangeType exchangeType) {
		if (ExchangeType.SH == exchangeType)
			return O32Tags.Value.MarketNo.sh;
		else if (ExchangeType.SZ == exchangeType)
			return O32Tags.Value.MarketNo.sz;
		else if (ExchangeType.SHF == exchangeType)
			return O32Tags.Value.MarketNo.shf;
		else throw new IllegalArgumentException("Unsupported exchange:" + exchangeType);
	}
	
	private static void addAccountInfo(IDataset dataset, ExchangeOrder order) {
		String investorID = order.getInvestorAccount();
		String productId = order.getProductAccount();
		String assetAccount = order.getAssetAccount();
		String tradingAccount = order.getTradingAccount();
		String securityAccount = order.getSecurityAccount();
		
		String operatorNo = investorID;
		String accountCode = productId;
		String assetNo = assetAccount;
		String combiNo = tradingAccount;
		String stockHolderId = securityAccount;
		
		String passwd = getPassword(operatorNo);
		
		if (null == passwd) throw new IllegalArgumentException("Can not find passwd by operatorNo:" + operatorNo);
		
		dataset.updateString(O32Tags.Field.operatorNo, operatorNo);
		dataset.updateString(O32Tags.Field.password, passwd);
		updateString(dataset, O32Tags.Field.accountCode, accountCode);
		updateString(dataset, O32Tags.Field.assetNo, assetNo);
		updateString(dataset, O32Tags.Field.combiNo, combiNo);
		updateString(dataset, O32Tags.Field.stockHolderId, stockHolderId);
	}
	
	protected static void updateString(IDataset dataset, String field, String value) {
		if (StringUtils.isNotEmpty(value)) {
			dataset.updateString(field, value);
		}
	}
	
	private static String getPassword(String operatorNo) {
		return AccountServiceHelper.getInstance().getPasswdByOperaterNo(operatorNo);
	}
	
	public static IEvent toCancelOrderRequest(ExchangeOrder order) {
		IEvent request = ContextUtil.getServiceContext().getEventFactory().getEventByAlias(O32Tags.FunctionId.cancelOrder, EventType.ET_REQUEST);
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn(O32Tags.Field.operatorNo);
		dataset.addColumn(O32Tags.Field.password);
		dataset.addColumn(O32Tags.Field.userToken);
		//dataset.addColumn(Field.batchNo);
		dataset.addColumn(O32Tags.Field.entrustNo);
		dataset.addColumn(O32Tags.Field.accountCode);
		dataset.addColumn(O32Tags.Field.assetNo);
		dataset.addColumn(O32Tags.Field.combiNo);
		//dataset.addColumn(Field.instanceNo);
		dataset.addColumn(O32Tags.Field.stockHolderId);
		dataset.appendRow();
		addAccountInfo(dataset, order);
		dataset.updateString(O32Tags.Field.entrustNo, getEntrustNoByReportNo(order.getReportNo(), order));
		request.putEventData(dataset);
		
		request.setStringAttributeValue(EventTagdef.TAG_SEQUECE_NO, order.getReportNo());
		request.setStringAttributeValue(EventTagdef.TAG_SENDERID, O32CallBackMethod.SENDER_ID); //async
		return request;
	}
	
	/**
	 *
	 * @param reportNo
	 * @return
	 */
	private static String getEntrustNoByReportNo(String reportNo, ExchangeOrder order) {
		String entrustNo = O32DataCache.getInstance().getEntrustNoByExtsystemId(reportNo);
		if (null == entrustNo) {
			// 从O32查询
			IEvent queryRequest = toQueryEntrustByExtsystemIdRequest(reportNo, order);
			O32StreamProcessor o32StreamProcessor = O32BeanHelper.getInstance().getO32ExchangeService().getO32StreamProcessor();
			try {
				IEvent queryResponse = o32StreamProcessor.sendRecieve(queryRequest);
				if (O32Util.isSuccess(queryResponse)) {
					IDatasets datasets = queryResponse.getEventDatas();
					if (datasets.getDatasetCount() > 1) {
						IDataset dataset = datasets.getDataset(1);
						dataset.beforeFirst();
						while (dataset.hasNext()) {
							dataset.next();
							entrustNo = dataset.getString(Field.entrustNo);
						}
					}
				}
				
			} catch (T2SDKException e) {
				logger.error("Can not find entrustNo by extsystemId:" + reportNo, e);
				throw new IllegalArgumentException("Can not find entrustNo by extsystemId:" + reportNo);
			}
			
		}
		
		if (null == entrustNo) throw new IllegalArgumentException("Can not find entrustNo by extsystemId:" + reportNo);
		return entrustNo;
	}
	
	private static IEvent toQueryEntrustByExtsystemIdRequest(String reportNo, ExchangeOrder order) {
		IEvent request = ContextUtil.getServiceContext().getEventFactory().getEventByAlias(O32Tags.FunctionId.amEntrustQuery, EventType.ET_REQUEST);
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn(O32Tags.Field.operatorNo);
		dataset.addColumn(O32Tags.Field.password);
		dataset.addColumn(O32Tags.Field.userToken);
		dataset.addColumn(O32Tags.Field.batchNo);
		dataset.addColumn(O32Tags.Field.entrustNo);
		dataset.addColumn(O32Tags.Field.accountCode);
		dataset.addColumn(O32Tags.Field.assetNo);
		dataset.addColumn(O32Tags.Field.combiNo);
		dataset.addColumn(O32Tags.Field.instanceNo);
		dataset.addColumn(O32Tags.Field.stockHolderId);
		dataset.addColumn(O32Tags.Field.marketNo);
		dataset.addColumn(O32Tags.Field.stockCode);
		dataset.addColumn(O32Tags.Field.entrustDirection);
		dataset.addColumn(O32Tags.Field.entrustStateList);
		dataset.addColumn(O32Tags.Field.positionStr);
		dataset.addColumn(O32Tags.Field.requestNum);
		dataset.addColumn(O32Tags.Field.extsystemId);
		dataset.appendRow();
		addAccountInfo(dataset, order);
		//updateString(dataset, O32Tags.Field.instanceNo, order.getPortfolioNo());
		updateString(dataset, O32Tags.Field.marketNo, convertExchangeToMarketNo(order.getExchangeType()));
		updateString(dataset, O32Tags.Field.stockCode, order.getSecurityId());
		updateString(dataset, O32Tags.Field.extsystemId, reportNo);
		request.putEventData(dataset);
		return request;
	}
	
}
