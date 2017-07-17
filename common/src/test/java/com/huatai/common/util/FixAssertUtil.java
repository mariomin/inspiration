package com.huatai.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.business.Order;
import com.huatai.common.fix.FixConvertionException;
import com.huatai.common.fix.FixConvertor;
import com.huatai.common.fix.tag.FixTags;
import com.huatai.common.refdata.RefData;
import com.huatai.common.type.OrderType;
import com.huatai.exchange.common.Constant;

import quickfix.FieldNotFound;
import quickfix.Group;
import quickfix.Message;
import quickfix.StringField;
import quickfix.field.AvgPx;
import quickfix.field.ClOrdID;
import quickfix.field.CumQty;
import quickfix.field.Currency;
import quickfix.field.ExecID;
import quickfix.field.ExecType;
import quickfix.field.LastPx;
import quickfix.field.LastQty;
import quickfix.field.LeavesQty;
import quickfix.field.ListID;
import quickfix.field.ListOrderStatus;
import quickfix.field.ListStatusType;
import quickfix.field.MDReqID;
import quickfix.field.MDReqRejReason;
import quickfix.field.MassStatusReqID;
import quickfix.field.MaxPriceLevels;
import quickfix.field.MinQty;
import quickfix.field.MsgType;
import quickfix.field.NoRelatedSym;
import quickfix.field.OrdStatus;
import quickfix.field.OrdType;
import quickfix.field.OrderID;
import quickfix.field.OrderQty;
import quickfix.field.OrigClOrdID;
import quickfix.field.PosMaintRptID;
import quickfix.field.Price;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.field.Text;
import quickfix.field.TimeInForce;
import quickfix.field.TotNoOrders;
import quickfix.field.TradSesReqID;
import quickfix.field.TradSesStatus;
import quickfix.field.TradingSessionID;
import quickfix.fix50sp2.AdjustedPositionReport;
import quickfix.fix50sp2.ExecutionReport;
import quickfix.fix50sp2.ListStatus;
import quickfix.fix50sp2.OrderCancelReject;
import quickfix.fix50sp2.SecurityList;

public class FixAssertUtil {
	
	public static void assertPendingNewExecutionReport(Message message, Order order) throws FieldNotFound, FixConvertionException {
		StringField msgType = message.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), ExecutionReport.MSGTYPE);
		assertTrue(message.getField(new ExecType()).valueEquals(ExecType.PENDING_NEW));
		assertEquals(message.getField(new OrdStatus()).getValue(), OrdStatus.PENDING_NEW);
		assertEquals(message.getField(new AvgPx()).getValue(), 0, Constant.delta);
		assertEquals(message.getField(new CumQty()).getValue(), 0, Constant.delta);
		assertNotNull(message.getField(new ExecID()));
		assertNotNull(message.getField(new OrderID()));
		assertEquals(message.getField(new OrderQty()).getValue(), order.getQuantity(), Constant.delta);
		assertTrue(message.getField(new Side()).valueEquals(FixUtil.toFixOrderSide(order.getOrderSide())));
		assertEquals(message.getField(new Symbol()).getValue(), order.getSymbol());
		assertEquals(message.getString(FixTags.InvestorAccount), order.getInvestorAccount());
		assertEquals(message.getString(FixTags.ProductAccount), order.getProductAccount());
		assertEquals(message.getString(FixTags.AssetAccount), order.getAssetAccount());
		assertEquals(message.getString(FixTags.AssetAccountType), order.getAssetAccountType().name());
		assertEquals(message.getString(FixTags.TradingAccount), order.getTradingAccount());
		assertEquals(message.getString(FixTags.SecurityAccount), order.getSecurityAccount());
		
		if (message.isSetField(Currency.FIELD)) {
			assertEquals(message.getString(Currency.FIELD), order.getCurrency().name());
		}
		if (message.isSetField(FixTags.YesterdaySettledCash)) {
			assertNotNull(message.getString(FixTags.YesterdaySettledCash));
		}
		if (message.isSetField(FixTags.HoldingCash)) {
			assertNotNull(message.getString(FixTags.HoldingCash));
		}
		if (message.isSetField(FixTags.TradableCash)) {
			assertNotNull(message.getString(FixTags.TradableCash));
		}
		if (message.isSetField(FixTags.YesterdaySettledBoughtQty)) {
			assertNotNull(message.getString(FixTags.YesterdaySettledBoughtQty));
		}
		if (message.isSetField(FixTags.IntradayBoughtQty)) {
			assertNotNull(message.getString(FixTags.IntradayBoughtQty));
		}
		if (message.isSetField(FixTags.HoldingQty)) {
			assertNotNull(message.getString(FixTags.HoldingQty));
		}
		if (message.isSetField(FixTags.PortfolioNo)) {
			assertNotNull(message.getString(FixTags.PortfolioNo));
		}
	}
	
	public static void assertUnreportedExecutionReport(Message message, Order order) throws FieldNotFound, FixConvertionException {
		StringField msgType = message.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), ExecutionReport.MSGTYPE);
		assertTrue(message.getField(new ExecType()).valueEquals(ExecType.PENDING_NEW));
		assertEquals(message.getField(new OrdStatus()).getValue(), com.huatai.common.type.OrdStatus.SUSPENDED.value());
		assertEquals(message.getField(new AvgPx()).getValue(), 0, Constant.delta);
		assertEquals(message.getField(new CumQty()).getValue(), 0, Constant.delta);
		assertNotNull(message.getField(new ExecID()));
		assertNotNull(message.getField(new OrderID()));
		assertEquals(message.getField(new OrderQty()).getValue(), order.getQuantity(), Constant.delta);
		if (message.isSetField(Price.FIELD)) {
			assertEquals(message.getField(new Price()).getValue(), order.getPrice(), Constant.delta);
		}
		assertTrue(message.getField(new Side()).valueEquals(FixUtil.toFixOrderSide(order.getOrderSide())));
		assertEquals(message.getField(new Symbol()).getValue(), order.getSymbol());
		
		assertEquals(message.getString(FixTags.InvestorAccount), order.getInvestorAccount());
		assertEquals(message.getString(FixTags.ProductAccount), order.getProductAccount());
		assertEquals(message.getString(FixTags.AssetAccount), order.getAssetAccount());
		assertEquals(message.getString(FixTags.AssetAccountType), order.getAssetAccountType().name());
		assertEquals(message.getString(FixTags.TradingAccount), order.getTradingAccount());
		assertEquals(message.getString(FixTags.SecurityAccount), order.getSecurityAccount());
		
		if (message.isSetField(Currency.FIELD)) {
			assertEquals(message.getString(Currency.FIELD), order.getCurrency().name());
		}
		if (message.isSetField(FixTags.YesterdaySettledCash)) {
			assertNotNull(message.getString(FixTags.YesterdaySettledCash));
		}
		if (message.isSetField(FixTags.HoldingCash)) {
			assertNotNull(message.getString(FixTags.HoldingCash));
		}
		if (message.isSetField(FixTags.TradableCash)) {
			assertNotNull(message.getString(FixTags.TradableCash));
		}
		
		if (message.isSetField(FixTags.YesterdaySettledBoughtQty)) {
			assertNotNull(message.getString(FixTags.YesterdaySettledBoughtQty));
		}
		if (message.isSetField(FixTags.IntradayBoughtQty)) {
			assertNotNull(message.getString(FixTags.IntradayBoughtQty));
		}
		if (message.isSetField(FixTags.HoldingQty)) {
			assertNotNull(message.getString(FixTags.HoldingQty));
		}
		
		// assertOrderType(message, order);
	}
	
	public static void assertNewExecutionReport(Message message, Order order) throws FieldNotFound, FixConvertionException {
		StringField msgType = message.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), ExecutionReport.MSGTYPE);
		assertEquals(message.getField(new ExecType()).getValue(), ExecType.NEW);
		assertEquals(message.getField(new OrdStatus()).getValue(), OrdStatus.NEW);
		assertEquals(message.getField(new AvgPx()).getValue(), 0, Constant.delta);
		assertNotNull(message.getField(new ExecID()));
		assertNotNull(message.getField(new OrderID()));
		assertEquals(message.getField(new OrderQty()).getValue(), order.getQuantity(), Constant.delta);
		assertEquals(message.getField(new Price()).getValue(), order.getPrice(), Constant.delta);
		assertTrue(message.getField(new Side()).valueEquals(FixUtil.toFixOrderSide(order.getOrderSide())));
		assertEquals(message.getField(new Symbol()).getValue(), order.getSymbol());
	}
	
	public static void assertFilledExecutionReport(Message message, Order order) throws FieldNotFound, FixConvertionException {
		StringField msgType = message.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), ExecutionReport.MSGTYPE);
		assertTrue(message.getField(new ExecType()).valueEquals(ExecType.TRADE));
		assertTrue(message.getField(new OrdStatus()).valueEquals(OrdStatus.FILLED));
		// assertEquals(message.getField(new AvgPx()).getValue(),
		// order.getPrice(), Constant.delta);
		assertEquals(message.getField(new ClOrdID()).getValue(), order.getClOrdId());
		assertEquals(message.getField(new CumQty()).getValue(), order.getQuantity(), Constant.delta);
		assertNotNull(message.getField(new ExecID()));
		assertEquals(message.getField(new LastPx()).getValue(), order.getPrice(), Constant.delta);
		assertEquals(message.getField(new LastQty()).getValue(), order.getQuantity(), Constant.delta);
		assertNotNull(message.getField(new OrderID()));
		assertEquals(message.getField(new OrderQty()).getValue(), order.getQuantity(), Constant.delta);
		assertEquals(message.getField(new Price()).getValue(), order.getPrice(), Constant.delta);
		assertTrue(message.getField(new Side()).valueEquals(FixUtil.toFixOrderSide(order.getOrderSide())));
		assertEquals(message.getField(new Symbol()).getValue(), order.getSymbol());
		assertEquals(message.getField(new LeavesQty()).getValue(), 0, Constant.delta);
		// assertNotNull(message.getString(FixTags.BranchNo));
		// assertOrderType(message, order);
	}
	
	public static void assertPartiallyFilledExecutionReport(Message message, Order order) throws FieldNotFound, FixConvertionException {
		StringField msgType = message.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), ExecutionReport.MSGTYPE);
		assertTrue(message.getField(new ExecType()).valueEquals(ExecType.TRADE));
		assertTrue(message.getField(new OrdStatus()).valueEquals(OrdStatus.PARTIALLY_FILLED));
		// assertEquals(message.getField(new AvgPx()).getValue(),
		// order.getPrice(), Constant.delta);
		assertEquals(message.getField(new ClOrdID()).getValue(), order.getClOrdId());
		long execQty = (long) message.getField(new CumQty()).getValue();
		assertTrue(DecimalUtil.lessThan(execQty, order.getQuantity()) && DecimalUtil.greaterThan(execQty, 0));
		assertNotNull(message.getField(new ExecID()));
		assertEquals(message.getField(new LastPx()).getValue(), order.getPrice(), Constant.delta);
		assertEquals(message.getField(new LastQty()).getValue(), execQty, Constant.delta);
		assertNotNull(message.getField(new OrderID()));
		assertEquals(message.getField(new OrderQty()).getValue(), order.getQuantity(), Constant.delta);
		assertEquals(message.getField(new Price()).getValue(), order.getPrice(), Constant.delta);
		assertTrue(message.getField(new Side()).valueEquals(FixUtil.toFixOrderSide(order.getOrderSide())));
		assertEquals(message.getField(new Symbol()).getValue(), order.getSymbol());
		assertEquals(message.getField(new LeavesQty()).getValue(), 0, Constant.delta);
		// assertNotNull(message.getString(FixTags.BranchNo));
		assertOrderType(message, order);
	}
	
	public static void assertCancelReject(Message message, OrigClOrdID origClOrdId, ClOrdID clOrdId) throws FieldNotFound {
		StringField msgType = message.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), OrderCancelReject.MSGTYPE);
		assertTrue(message.getField(new OrdStatus()).valueEquals(OrdStatus.FILLED));
		assertEquals(message.getField(new ClOrdID()).getValue(), clOrdId.getValue());
		assertNotNull(message.getField(new OrderID()));
		assertEquals(message.getField(new OrigClOrdID()).getValue(), origClOrdId.getValue());
		assertNotNull(message.getField(new Text()));
	}
	
	public static void assertCancelExecutionReport(Message message, Order order, ClOrdID clOrdId, OrigClOrdID origClOrdId, double avgPx, long execQty)
			throws FieldNotFound, FixConvertionException {
		StringField msgType = message.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), ExecutionReport.MSGTYPE);
		// assertEquals(message.getField(new ExecType()).getValue(),
		// ExecType.CANCELED);
		assertTrue(message.getField(new OrdStatus()).valueEquals(OrdStatus.CANCELED));
		// assertEquals(message.getField(new AvgPx()).getValue(), avgPx,
		// Constant.delta);
		assertEquals(message.getField(new ClOrdID()).getValue(), clOrdId.getValue());
		assertEquals(message.getField(new OrigClOrdID()).getValue(), origClOrdId.getValue());
		assertEquals(message.getField(new CumQty()).getValue(), execQty, Constant.delta);
		assertNotNull(message.getField(new ExecID()));
		assertNotNull(message.getField(new OrderID()));
		assertEquals(message.getField(new OrderQty()).getValue(), order.getQuantity(), Constant.delta);
		assertEquals(message.getField(new Price()).getValue(), order.getPrice(), Constant.delta);
		assertTrue(message.getField(new Side()).valueEquals(FixUtil.toFixOrderSide(order.getOrderSide())));
		assertEquals(message.getField(new Symbol()).getValue(), order.getSymbol());
		assertEquals(message.getField(new LeavesQty()).getValue(), order.getQuantity() - execQty, Constant.delta);
		// assertNotNull(message.getString(FixTags.BranchNo));
		assertOrderType(message, order);
	}
	
	public static void assertCancelExecutionReportWithOrderID(Message message, Order order, ClOrdID clOrdId, OrderID orderID, double avgPx,
			long execQty) throws FieldNotFound, FixConvertionException {
		StringField msgType = message.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), ExecutionReport.MSGTYPE);
		assertTrue(message.getField(new ExecType()).valueEquals(ExecType.CANCELED));
		assertTrue(message.getField(new OrdStatus()).valueEquals(OrdStatus.CANCELED));
		assertEquals(message.getField(new AvgPx()).getValue(), avgPx, Constant.delta);
		assertEquals(message.getField(new ClOrdID()).getValue(), clOrdId.getValue());
		assertEquals(message.getField(new OrderID()).getValue(), orderID.getValue());
		assertEquals(message.getField(new CumQty()).getValue(), execQty, Constant.delta);
		assertNotNull(message.getField(new ExecID()));
		assertNotNull(message.getField(new OrderID()));
		assertEquals(message.getField(new OrderQty()).getValue(), order.getQuantity(), Constant.delta);
		assertEquals(message.getField(new Price()).getValue(), order.getPrice(), Constant.delta);
		assertTrue(message.getField(new Side()).valueEquals(FixUtil.toFixOrderSide(order.getOrderSide())));
		assertEquals(message.getField(new Symbol()).getValue(), order.getSymbol());
		assertEquals(message.getField(new LeavesQty()).getValue(), order.getQuantity() - execQty, Constant.delta);
		// assertNotNull(message.getString(FixTags.BranchNo));
		assertOrderType(message, order);
	}
	
	public static void assertQueryCancelExecutionReport(Message message, Order order, ClOrdID clOrdId, OrigClOrdID origClOrdId, double avgPx,
			long execQty) throws FieldNotFound, FixConvertionException {
		StringField msgType = message.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), ExecutionReport.MSGTYPE);
		assertTrue(message.getField(new ExecType()).valueEquals(ExecType.ORDER_STATUS));
		assertTrue(message.getField(new OrdStatus()).valueEquals(OrdStatus.CANCELED));
		// assertEquals(message.getField(new AvgPx()).getValue(), avgPx,
		// Constant.delta);
		assertEquals(message.getField(new ClOrdID()).getValue(), clOrdId.getValue());
		// assertEquals(message.getField(new OrigClOrdID()).getValue(),
		// origClOrdId.getValue());
		assertEquals(message.getField(new CumQty()).getValue(), execQty, Constant.delta);
		assertNotNull(message.getField(new ExecID()));
		assertNotNull(message.getField(new OrderID()));
		assertEquals(message.getField(new OrderQty()).getValue(), order.getQuantity(), Constant.delta);
		assertEquals(message.getField(new Price()).getValue(), order.getPrice(), Constant.delta);
		assertTrue(message.getField(new Side()).valueEquals(FixUtil.toFixOrderSide(order.getOrderSide())));
		assertEquals(message.getField(new Symbol()).getValue(), order.getSymbol());
		assertEquals(message.getField(new LeavesQty()).getValue(), order.getQuantity() - execQty, Constant.delta);
		// assertNotNull(message.getString(FixTags.BranchNo));
		assertOrderType(message, order);
	}
	
	public static void assertRejectExecutionReport(Message message, String errorCode) throws FieldNotFound {
		StringField msgType = message.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), ExecutionReport.MSGTYPE);
		assertTrue(message.getField(new ExecType()).valueEquals(ExecType.REJECTED));
		assertTrue(message.getField(new OrdStatus()).valueEquals(OrdStatus.REJECTED));
		assertEquals(message.getField(new CumQty()).getValue(), 0, Constant.delta);
		assertEquals(message.getField(new LeavesQty()).getValue(), 0, Constant.delta);
		assertNotNull(message.getField(new ClOrdID()));
		assertNotNull(message.getField(new ExecID()));
		// assertEquals(message.getField(new OrderID()).getValue(), "NONE");
		assertNotNull(message.getField(new Side()).getValue());
		assertNotNull(message.getField(new Symbol()).getValue());
		if (message.isSetField(Text.FIELD)) {
			assertTrue(message.getField(new Text()).getValue().startsWith(errorCode));
		}
	}
	
	public static void assertRefData(Map<String, RefData> actual, Map<String, RefData> expect) throws FieldNotFound, FixConvertionException {
		for (RefData actualRefData : actual.values()) {
			RefData expectRefData = expect.get(actualRefData.getSymbol());
			
			assertEquals(expectRefData.getSymbol(), actualRefData.getSymbol());
			assertEquals(expectRefData.getChineseName(), actualRefData.getChineseName());
			assertEquals(expectRefData.getSpellCode(), actualRefData.getSpellCode());
			assertEquals(expectRefData.getEnglishName(), actualRefData.getEnglishName());
			assertEquals(expectRefData.getIndustryClassification(), actualRefData.getIndustryClassification());
			assertEquals(expectRefData.getParValue(), actualRefData.getParValue(), Constant.delta);
			assertEquals(expectRefData.getCurrency(), actualRefData.getCurrency());
			assertEquals(expectRefData.getOutstandingShare(), actualRefData.getOutstandingShare(), Constant.delta);
			assertEquals(expectRefData.getPublicFloatShareQuantity(), actualRefData.getPublicFloatShareQuantity(), Constant.delta);
			assertEquals(expectRefData.getInterestAccrualDate(), actualRefData.getInterestAccrualDate());
			assertEquals(expectRefData.getPreviousClosingPx(), actualRefData.getPreviousClosingPx(), Constant.delta);
			assertEquals(expectRefData.getHighLimitPx(), actualRefData.getHighLimitPx(), Constant.delta);
			assertEquals(expectRefData.getLowLimitPx(), actualRefData.getLowLimitPx(), Constant.delta);
			assertEquals(expectRefData.getCrdBuyUnderlying(), actualRefData.getCrdBuyUnderlying());
			assertEquals(expectRefData.getCrdSellUnderlying(), actualRefData.getCrdSellUnderlying());
			assertEquals(expectRefData.getGageRatio(), actualRefData.getGageRatio(), Constant.delta);
			assertEquals(expectRefData.getStatus(), actualRefData.getStatus());
			assertEquals(expectRefData.getSecurityType(), actualRefData.getSecurityType());
			assertEquals(expectRefData.getQtyUnit(), actualRefData.getQtyUnit());
		}
	}
	
	public static void assertRefDataByAllSecurity(Message message, Map<String, RefData> expect) throws FieldNotFound, FixConvertionException {
		StringField msgType = message.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), SecurityList.MSGTYPE);
		Collection<RefData> result = FixConvertor.parseSecurityList(message);
		Map<String, RefData> actual = new HashMap<String, RefData>();
		for (RefData refData : result) {
			actual.put(refData.getSymbol(), refData);
		}
		
		assertRefData(actual, expect);
	}
	
	public static void assertRefDataBySymbol(Message message, Map<String, RefData> expect, String symbol)
			throws FieldNotFound, FixConvertionException {
		Collection<RefData> result = FixConvertor.parseSecurityList(message);
		Map<String, RefData> actual = new HashMap<String, RefData>();
		for (RefData refData : result) {
			actual.put(refData.getSymbol(), refData);
			assertEquals(refData.getSymbol(), symbol);
		}
		
		assertRefData(actual, expect);
	}
	
	public static void assertRefDataByMarketID(Message message, Map<String, RefData> expect, String marketId)
			throws FieldNotFound, FixConvertionException {
		Collection<RefData> result = FixConvertor.parseSecurityList(message);
		Map<String, RefData> actual = new HashMap<String, RefData>();
		for (RefData refData : result) {
			actual.put(refData.getSymbol(), refData);
			assertEquals(refData.getSymbol().split(RegularExpressionUtil.dot)[1], marketId);
		}
		
		assertRefData(actual, expect);
	}
	
	public static void assertNonTradingSessionOrder(Message message, String requestId, Map<String, ExchangeOrder> orders)
			throws FieldNotFound, FixConvertionException {
		StringField msgType = message.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), ExecutionReport.MSGTYPE);
		assertEquals(message.getField(new MassStatusReqID()).getValue(), requestId);
		int orderNums = message.isSetField(FixTags.NoExecutionReports) ? message.getInt(FixTags.NoExecutionReports) : 0;
		
		for (int i = 1; i <= orderNums; i++) {
			Group group = message.getGroup(i, FixTags.NoExecutionReports);
			String orderId = group.getField(new StringField(OrderID.FIELD)).getValue();
			ExchangeOrder expect = orders.get(orderId);
			
			assertEquals(group.getField(new TimeInForce()).getValue(), expect.getTimeInForce().charValue());
			assertEquals(group.getField(new MaxPriceLevels()).getValue(), expect.getMaxPriceLevels().intValue());
			assertEquals(group.getField(new MinQty()).getValue(), expect.getMinQty(), Constant.delta);
			
			ExchangeOrder actual = FixConvertor.parseExchangeOrderSnapshot(group);
			assertEquals(expect.getSymbol(), actual.getSymbol());
			assertEquals(expect.getOrderSide(), actual.getOrderSide());
			assertEquals(expect.getPrice(), actual.getPrice());
			assertEquals(expect.getQuantity(), actual.getQuantity(), Constant.delta);
			assertEquals(expect.getOrderType(), actual.getOrderType());
			assertEquals(com.huatai.common.type.OrdStatus.PENDING_NEW, actual.getOrdStatus());
			// assertEquals(expect.getAvgPx(), actual.getAvgPx(),
			// Constant.delta);
			assertEquals(expect.getCumQty(), actual.getCumQty(), Constant.delta);
			assertEquals(expect.getClOrdId(), actual.getClOrdId());
			assertEquals(expect.getTransactTime(), actual.getTransactTime());
		}
		
	}
	
	public static void assertNewOrderListAck(Message actual, Message expect, Map<String, Order> orders) throws FieldNotFound, FixConvertionException {
		StringField msgType = actual.getHeader().getField(new MsgType());
		if (msgType.getValue() == ListStatus.MSGTYPE) {
			assertEquals(actual.getField(new ListID()).getValue(), expect.getField(new ListID()).getValue());
			assertTrue(actual.getField(new ListStatusType()).valueEquals(ListStatusType.ACK));
			assertTrue(actual.getField(new ListOrderStatus()).valueEquals(ListOrderStatus.EXECUTING));
			assertEquals(actual.getField(new TotNoOrders()).getValue(), expect.getField(new TotNoOrders()).getValue());
		} else if (msgType.getValue() == ExecutionReport.MSGTYPE) {
			
		}
		
	}
	
	public static void assertFinishedListStatus(Message actual, Message expect) throws FieldNotFound, FixConvertionException {
		StringField msgType = actual.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), ListStatus.MSGTYPE);
		assertEquals(actual.getField(new ListID()).getValue(), expect.getField(new ListID()).getValue());
		assertTrue(actual.getField(new ListStatusType()).valueEquals(ListStatusType.ALLDONE));
		assertTrue(actual.getField(new ListOrderStatus()).valueEquals(ListOrderStatus.ALL_DONE));
	}
	
	public static void assertCancelingListStatus(Message actual, Message expect) throws FieldNotFound, FixConvertionException {
		StringField msgType = actual.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), ListStatus.MSGTYPE);
		assertEquals(actual.getField(new ListID()).getValue(), expect.getField(new ListID()).getValue());
		// assertTrue(actual.getField(new
		// ListStatusType()).valueEquals(ListStatusType.ALLDONE));
		assertTrue(actual.getField(new ListOrderStatus()).valueEquals(ListOrderStatus.CANCELING));
	}
	
	public static void assertRejectMarketDataRequest(Message message, String requestId, Set<String> symbols, char mdEntryType) throws FieldNotFound {
		String msgType = message.getHeader().getField(new MsgType()).getValue();
		String mDReqID = message.getField(new MDReqID()).getValue();
		char mDRejReason = message.getField(new MDReqRejReason()).getValue();
		String text = message.getField(new Text()).getValue();
		
		assertEquals(MsgType.MARKET_DATA_REQUEST_REJECT, msgType);
		assertEquals(requestId, mDReqID);
		assertEquals(MDReqRejReason.UNKNOWN_SYMBOL, mDRejReason);
		assertTrue(symbols.contains(text));
	}
	
	public static void assertAdjustedPositionReport(Message message, String posReqId, int symbols) throws FieldNotFound {
		StringField msgType = message.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), AdjustedPositionReport.MSGTYPE);
		assertEquals(message.getField(new PosMaintRptID()).getValue(), posReqId);
		
		int noRelatedSym = message.getGroupCount(NoRelatedSym.FIELD);
		assertEquals(noRelatedSym, symbols);
		for (int i = 1; i <= noRelatedSym; i++) {
			AdjustedPositionReport.NoRelatedSym group = new AdjustedPositionReport.NoRelatedSym();
			message.getGroup(i, group);
			assertTrue(group.isSetField(FixTags.SecurityAccount));
			assertTrue(group.isSetField(FixTags.YesterdaySettledBoughtQty));
			assertTrue(group.isSetField(FixTags.IntradayBoughtQty));
			assertTrue(group.isSetField(FixTags.HoldingQty));
		}
	}
	
	public static void assertOrderType(Message message, Order order) throws FieldNotFound {
		assertEquals(message.getField(new TimeInForce()).getValue(), order.getTimeInForce().charValue());
		assertEquals(message.getField(new MaxPriceLevels()).getValue(), order.getMaxPriceLevels().intValue());
		assertEquals(message.getField(new MinQty()).getValue(), order.getMinQty(), Constant.delta);
		
		if (order.getOrderType() == OrderType.LIMIT) {
			assertEquals(message.getField(new OrdType()).getValue(), '2');
		} else {
			if (order.getTimeInForce().equals('0') && order.getMaxPriceLevels().equals(0) && DecimalUtil.isZero(order.getMinQty())) {
				// assertEquals(message.getField(new OrdType()).getValue(),
				// 'U');
			} else {
				assertEquals(message.getField(new OrdType()).getValue(), '1');
			}
		}
	}
	
	public static void assertInvalidMarketSessionRequest(Message message, String marketID, String tradSesReqID) throws FieldNotFound {
		assertEquals(message.getString(TradingSessionID.FIELD), "1");
		assertEquals(message.getInt(TradSesStatus.FIELD), TradSesStatus.REQUEST_REJECTED);
		//assertEquals(message.getString(MarketID.FIELD), marketID);
		assertEquals(message.getString(TradSesReqID.FIELD), tradSesReqID);
	}
	
	public static void assertExecutionReportWithPortfolioPosition(Message message) throws FieldNotFound, FixConvertionException {
		StringField msgType = message.getHeader().getField(new MsgType());
		assertEquals(msgType.getValue(), ExecutionReport.MSGTYPE);
		assertNotNull(message.getString(FixTags.PortfolioNo));
		assertNotNull(message.getString(FixTags.YesterdaySettledBoughtQty));
		assertNotNull(message.getString(FixTags.YesterdaySettledSoldAmount));
		assertNotNull(message.getString(FixTags.YesterdaySettledSoldQty));
		assertNotNull(message.getString(FixTags.IntradayBoughtAmount));
		assertNotNull(message.getString(FixTags.IntradayBoughtQty));
		assertNotNull(message.getString(FixTags.IntradaySoldAmount));
		assertNotNull(message.getString(FixTags.IntradaySoldQty));
		assertNotNull(message.getString(FixTags.HoldingQty));
		assertNotNull(message.getString(FixTags.TradableQty));
		assertNotNull(message.getString(FixTags.AvgCost));
	}
	
}
