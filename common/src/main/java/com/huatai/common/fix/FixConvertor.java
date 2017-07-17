package com.huatai.common.fix;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.huatai.common.algo.SecurityAccountDetail;
import com.huatai.common.business.AlgoOrder;
import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.business.Execution;
import com.huatai.common.business.Order;
import com.huatai.common.business.command.StrategyAlterCommand;
import com.huatai.common.data.DataObject;
import com.huatai.common.fix.message.StrategyAlterRequest;
import com.huatai.common.fix.message.StrategyQueryRequest;
import com.huatai.common.fix.message.StrategySubscribeRequest;
import com.huatai.common.fix.tag.FixTags;
import com.huatai.common.fund.TradingFund;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.index.TradingAccountIndex;
import com.huatai.common.marketdata.RequestMarketData;
import com.huatai.common.portfolio.PortfolioPosition;
import com.huatai.common.portfolio.TradingPosition;
import com.huatai.common.refdata.RefData;
import com.huatai.common.strategy.StrategyInstruction;
import com.huatai.common.type.AssetAccountType;
import com.huatai.common.type.ExchangeType;
import com.huatai.common.type.OrderField;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;
import com.huatai.common.util.FixUtil;
import com.huatai.common.util.IdGenerator;

import quickfix.BooleanField;
import quickfix.CharField;
import quickfix.DoubleField;
import quickfix.FieldNotFound;
import quickfix.Group;
import quickfix.IntField;
import quickfix.Message;
import quickfix.StringField;
import quickfix.UtcTimeStampField;
import quickfix.field.AvgPx;
import quickfix.field.BidType;
import quickfix.field.BusinessRejectReason;
import quickfix.field.ClOrdID;
import quickfix.field.ClearingBusinessDate;
import quickfix.field.CumQty;
import quickfix.field.Currency;
import quickfix.field.CxlRejResponseTo;
import quickfix.field.ExecID;
import quickfix.field.ExecType;
import quickfix.field.HighLimitPrice;
import quickfix.field.InterestAccrualDate;
import quickfix.field.LastPx;
import quickfix.field.LastShares;
import quickfix.field.LeavesQty;
import quickfix.field.ListID;
import quickfix.field.ListSeqNo;
import quickfix.field.LowLimitPrice;
import quickfix.field.MDEntryType;
import quickfix.field.MDReqID;
import quickfix.field.MarketDepth;
import quickfix.field.MarketID;
import quickfix.field.MassStatusReqID;
import quickfix.field.MassStatusReqType;
import quickfix.field.MaxPriceLevels;
import quickfix.field.MinQty;
import quickfix.field.OrdStatus;
import quickfix.field.OrdType;
import quickfix.field.OrderID;
import quickfix.field.OrderQty;
import quickfix.field.OrigClOrdID;
import quickfix.field.PosMaintRptID;
import quickfix.field.PrevClosePx;
import quickfix.field.Price;
import quickfix.field.RefMsgType;
import quickfix.field.SecurityListRequestType;
import quickfix.field.SecurityReqID;
import quickfix.field.SecurityType;
import quickfix.field.Side;
import quickfix.field.SubscriptionRequestType;
import quickfix.field.Symbol;
import quickfix.field.Text;
import quickfix.field.TimeInForce;
import quickfix.field.TotNoOrders;
import quickfix.field.TradSesReqID;
import quickfix.field.TradSesStatus;
import quickfix.field.TradingSessionID;
import quickfix.field.TransactTime;
import quickfix.field.UserRequestID;
import quickfix.fix50sp2.AdjustedPositionReport;
import quickfix.fix50sp2.BusinessMessageReject;
import quickfix.fix50sp2.ExecutionReport;
import quickfix.fix50sp2.NewOrderList;
import quickfix.fix50sp2.NewOrderSingle;
import quickfix.fix50sp2.OrderCancelReject;
import quickfix.fix50sp2.OrderCancelReplaceRequest;
import quickfix.fix50sp2.OrderCancelRequest;
import quickfix.fix50sp2.OrderMassStatusRequest;
import quickfix.fix50sp2.SecurityList;
import quickfix.fix50sp2.SecurityListRequest;
import quickfix.fix50sp2.TradingSessionStatus;
import quickfix.fix50sp2.TradingSessionStatusRequest;
import quickfix.fix50sp2.component.InstrmtMDReqGrp;
import quickfix.fix50sp2.component.MDReqGrp;

public class FixConvertor {
	private static final IdGenerator idGenerator = IdGenerator.getInstance();
	
	public static Execution parseExecutionSnapshot(Group group) throws FieldNotFound, FixConvertionException {
		Execution execution = new Execution();
		execution.setInvestorAccount(group.getString(FixTags.InvestorAccount));
		execution.setProductAccount(group.getString(FixTags.ProductAccount));
		execution.setAssetAccount(group.getString(FixTags.AssetAccount));
		execution.setAssetAccountType(AssetAccountType.valueOf(group.getString(FixTags.AssetAccountType)));
		execution.setTradingAccount(group.getString(FixTags.TradingAccount));
		execution.setSymbol(group.getString(Symbol.FIELD));
		execution.setSecurityAccount(group.getString(FixTags.SecurityAccount));
		
		if (group.isSetField(Symbol.FIELD)) {
			execution.setSymbol(group.getField(new StringField(Symbol.FIELD)).getValue());
		}
		if (group.isSetField(Side.FIELD)) {
			execution.setOrderSide(FixUtil.fromFixOrderSide(group.getField(new CharField(Side.FIELD)).getValue()));
		}
		if (group.isSetField(OrderQty.FIELD)) {
			execution.setQuantity(group.getField(new DoubleField(OrderQty.FIELD)).getValue());
		}
		if (group.isSetField(Price.FIELD)) {
			execution.setPrice(group.getField(new DoubleField(Price.FIELD)).getValue());
		}
		if (group.isSetField(OrderID.FIELD)) {
			execution.setOrderId(group.getField(new StringField(OrderID.FIELD)).getValue());
		}
		if (group.isSetField(ExecID.FIELD)) {
			execution.setExecId(group.getField(new StringField(ExecID.FIELD)).getValue());
		}
		if (group.isSetField(ClOrdID.FIELD)) {
			execution.setClOrdId(group.getString(ClOrdID.FIELD));
		}
		if (group.isSetField(FixTags.Created)) {
			execution.setCreated(new Timestamp(group.getField(new UtcTimeStampField(FixTags.Created)).getValue().getTime()));
		}
		if (group.isSetField(FixTags.Modified)) {
			execution.setModified(new Timestamp(group.getField(new UtcTimeStampField(FixTags.Modified)).getValue().getTime()));
		}
		
		return execution;
	}
	
	public static StrategyAlterCommand parseStrategyAlterCommand(Message message) throws FieldNotFound {
		SecurityTradingIndex index = FixConvertor.parseSecurityTradingIndex(message);
		StrategyAlterCommand strategyAlterCommand = new StrategyAlterCommand(index);
		strategyAlterCommand.setRequestID(message.getField(new UserRequestID()).getValue());
		strategyAlterCommand
				.setStrategyInstruction(StrategyInstruction.getStrategyInstructionByValue(message.getString(FixTags.StrategyInstruction)));
		strategyAlterCommand.setTransactTime(new Timestamp(message.getUtcTimeStamp(TransactTime.FIELD).getTime()));
		
		if (strategyAlterCommand.getStrategyInstruction() == StrategyInstruction.Create) {
			strategyAlterCommand.setStrategy(message.getString(FixTags.StrategyName));
			strategyAlterCommand.setStrategyParaStr(message.getString(FixTags.StrategyParas));
			
			int noSecurityAccounts = message.getGroupCount(FixTags.NoSecurityAccounts);
			List<SecurityAccountDetail> securityAccDetails = new ArrayList<SecurityAccountDetail>();
			strategyAlterCommand.setSecurityAccountDetails(securityAccDetails);
			for (int i = 1; i <= noSecurityAccounts; i++) {
				Group group = message.getGroup(i, FixTags.NoSecurityAccounts);
				String securityAccount = group.getString(FixTags.SecurityAccount);
				ExchangeType exchangeType = ExchangeType.getExchangeTypeByValue(group.getString(MarketID.FIELD));
				com.huatai.common.type.Currency currency = com.huatai.common.type.Currency.getType(group.getString(Currency.FIELD));
				securityAccDetails.add(new SecurityAccountDetail(securityAccount, exchangeType, currency));
			}
			
		} else {
			strategyAlterCommand.setStrategyId(message.getString(FixTags.StrategyID));
		}
		
		return strategyAlterCommand;
	}
	
	public static StrategyAlterRequest convertToStrategyAlterRequest(String requestID, String strategyID, TradingAccountIndex tradingAccountIndex,
			List<SecurityAccountDetail> securityAccountDetails, String strategy, String strategyParas, String instruction) {
		StrategyAlterRequest strategyAlterRequest = new StrategyAlterRequest();
		strategyAlterRequest.setField(new UserRequestID(requestID));
		strategyAlterRequest.setField(new TransactTime());
		strategyAlterRequest.setString(FixTags.InvestorAccount, tradingAccountIndex.getInvestorAccount());
		strategyAlterRequest.setString(FixTags.ProductAccount, tradingAccountIndex.getProductAccount());
		strategyAlterRequest.setString(FixTags.AssetAccount, tradingAccountIndex.getAssetAccount());
		strategyAlterRequest.setString(FixTags.AssetAccountType, tradingAccountIndex.getAssetAccountType().name());
		strategyAlterRequest.setString(FixTags.TradingAccount, tradingAccountIndex.getTradingAccount());
		strategyAlterRequest.setString(FixTags.StrategyInstruction, instruction);
		
		if (null != strategyID) {
			strategyAlterRequest.setString(FixTags.StrategyID, strategyID);
		}
		if (null != strategy) {
			strategyAlterRequest.setString(FixTags.StrategyName, strategy);
		}
		if (null != strategyParas) {
			strategyAlterRequest.setString(FixTags.StrategyParas, strategyParas);
		}
		
		if (null != securityAccountDetails) {
			for (SecurityAccountDetail securityAccDetail : securityAccountDetails) {
				Group group = new Group(FixTags.NoSecurityAccounts, FixTags.NoSecurityAccounts);
				group.setString(FixTags.SecurityAccount, securityAccDetail.getSecurityAccount());
				group.setString(MarketID.FIELD, securityAccDetail.getExchangeType().value());
				group.setString(Currency.FIELD, securityAccDetail.getCurrency().value());
				strategyAlterRequest.addGroup(group);
			}
		}
		
		return strategyAlterRequest;
	}
	
	public static StrategySubscribeRequest convertToStrategySubscribeRequest(String requestID, String strategyID,
			TradingAccountIndex tradingAccountIndex, String subscribeID, char subscriptionType) {
		StrategySubscribeRequest request = new StrategySubscribeRequest();
		request.setField(new UserRequestID(requestID));
		request.setString(FixTags.InvestorAccount, tradingAccountIndex.getInvestorAccount());
		request.setString(FixTags.ProductAccount, tradingAccountIndex.getProductAccount());
		request.setString(FixTags.AssetAccount, tradingAccountIndex.getAssetAccount());
		request.setString(FixTags.AssetAccountType, tradingAccountIndex.getAssetAccountType().name());
		request.setString(FixTags.TradingAccount, tradingAccountIndex.getTradingAccount());
		request.setString(FixTags.SubscribeID, subscribeID);
		request.setField(new SubscriptionRequestType(subscriptionType));
		
		if (null != strategyID) {
			request.setString(FixTags.StrategyID, strategyID);
		}
		
		return request;
	}
	
	public static StrategyQueryRequest convertToStrategyQueryRequest(String requestID, TradingAccountIndex tradingAccountIndex) {
		StrategyQueryRequest request = new StrategyQueryRequest();
		request.setField(new UserRequestID(requestID));
		request.setString(FixTags.InvestorAccount, tradingAccountIndex.getInvestorAccount());
		request.setString(FixTags.ProductAccount, tradingAccountIndex.getProductAccount());
		request.setString(FixTags.AssetAccount, tradingAccountIndex.getAssetAccount());
		request.setString(FixTags.AssetAccountType, tradingAccountIndex.getAssetAccountType().name());
		request.setString(FixTags.TradingAccount, tradingAccountIndex.getTradingAccount());
		return request;
	}
	
	public static RequestMarketData parseMarketDataRequest(Message message) throws FieldNotFound {
		String mdReqID = message.getField(new MDReqID()).getValue();
		char subscriptionRequestType = message.getField(new SubscriptionRequestType()).getValue();
		int marketDepth = message.getField(new MarketDepth()).getValue();
		
		Set<String> symbols = new HashSet<String>();
		int size = message.getField(new quickfix.field.NoRelatedSym()).getValue();
		for (int i = 1; i <= size; i++) {
			InstrmtMDReqGrp.NoRelatedSym noRelatedSym = new InstrmtMDReqGrp.NoRelatedSym();
			message.getGroup(i, noRelatedSym);
			String symbol = noRelatedSym.get(new Symbol()).getValue();
			symbols.add(symbol);
		}
		
		size = message.getField(new quickfix.field.NoMDEntryTypes()).getValue();
		List<Character> entryTypes = new ArrayList<Character>();
		for (int i = 1; i <= size; i++) {
			MDReqGrp.NoMDEntryTypes noMDEntryTypes = new MDReqGrp.NoMDEntryTypes();
			message.getGroup(i, noMDEntryTypes);
			char entryType = noMDEntryTypes.get(new MDEntryType()).getValue();
			entryTypes.add(entryType);
		}
		
		return new RequestMarketData(mdReqID, subscriptionRequestType, marketDepth, symbols, entryTypes);
	}
	
	public static SecurityList convertToSecurityList(String requestID, Collection<RefData> refDatas, int offset) {
		SecurityList securitylist = new SecurityList();
		securitylist.set(new SecurityReqID(requestID));
		int index = offset;
		for (RefData refData : refDatas) {
			SecurityList.NoRelatedSym norelatedsym = new SecurityList.NoRelatedSym();
			String symbols = refData.getSymbol();
			if ((symbols == null) || symbols.isEmpty()) {
				continue;
			}
			norelatedsym.setInt(FixTags.Offset, index);
			index++;
			norelatedsym.set(new Symbol(symbols));
			if ((refData.getEnglishName() != null) && !refData.getEnglishName().isEmpty()) {
				norelatedsym.setField(new StringField(FixTags.EnglishName, refData.getEnglishName()));
			}
			if ((refData.getChineseName() != null) && !refData.getChineseName().isEmpty()) {
				norelatedsym.setField(new StringField(FixTags.ChineseName, refData.getChineseName()));
			}
			if ((refData.getIndustryClassification() != null) && !refData.getIndustryClassification().isEmpty()) {
				norelatedsym.setField(new StringField(FixTags.IndustryClassification, refData.getIndustryClassification()));
			}
			norelatedsym.setField(new IntField(FixTags.QtyUnit, refData.getQtyUnit()));
			norelatedsym.setField(new DoubleField(FixTags.ParValue, refData.getParValue()));
			norelatedsym.setField(new DoubleField(FixTags.OutstandingShare, refData.getOutstandingShare()));
			norelatedsym.setField(new DoubleField(FixTags.PublicFloatShareQuantity, refData.getPublicFloatShareQuantity()));
			if ((refData.getCrdBuyUnderlying() != null) && !refData.getCrdBuyUnderlying().isEmpty()) {
				norelatedsym.setField(new StringField(FixTags.CrdBuyUnderlying, refData.getCrdBuyUnderlying()));
			}
			if ((refData.getCrdSellUnderlying() != null) && !refData.getCrdSellUnderlying().isEmpty()) {
				norelatedsym.setField(new StringField(FixTags.CrdSellUnderlying, refData.getCrdSellUnderlying()));
			}
			norelatedsym.setField(new DoubleField(FixTags.GageRatio, refData.getGageRatio()));
			norelatedsym.setField(new DoubleField(PrevClosePx.FIELD, refData.getPreviousClosingPx()));
			if ((refData.getStatus() != null) && !refData.getStatus().isEmpty()) {
				norelatedsym.setField(new StringField(FixTags.SecurityStatusCN, refData.getStatus()));
			}
			if (refData.getSecurityType() != null) {
				norelatedsym.setField(new IntField(FixTags.SecurityTypeCN, refData.getSecurityType()));
			}
			if ((refData.getSpellCode() != null) && !refData.getSpellCode().isEmpty()) {
				norelatedsym.setField(new StringField(FixTags.SpellCode, refData.getSpellCode()));
			}
			
			norelatedsym.setField(new DoubleField(HighLimitPrice.FIELD, refData.getHighLimitPx()));
			norelatedsym.setField(new DoubleField(LowLimitPrice.FIELD, refData.getLowLimitPx()));
			
			if (refData.getInterestAccrualDate() != null) {
				norelatedsym.set(new InterestAccrualDate(refData.getInterestAccrualDate().toString()));
			}
			if (refData.getCurrency() != null) {
				norelatedsym.set(new Currency(refData.getCurrency().name()));
			}
			securitylist.addGroup(norelatedsym);
		}
		return securitylist;
	}
	
	public static SecurityListRequest convertToRefDataRequest(String securityReqID, int securityListRequestType, String symbol, String marketID,
			int pageSize) {
		SecurityListRequest refDataRequest = new SecurityListRequest(new SecurityReqID(securityReqID),
				new SecurityListRequestType(securityListRequestType));
		if (symbol != null) {
			refDataRequest.set(new Symbol(symbol));
		}
		if (marketID != null) {
			refDataRequest.set(new MarketID(marketID));
		}
		refDataRequest.setField(new IntField(FixTags.PageSize, pageSize));
		
		return refDataRequest;
	}
	
	public static BusinessMessageReject convertToBusinessMessageReject(String refMsgType, String text) {
		BusinessMessageReject reject = new BusinessMessageReject(new RefMsgType(refMsgType),
				new BusinessRejectReason(BusinessRejectReason.UNSUPPORTED_MESSAGE_TYPE));
		reject.setField(new Text(text));
		return reject;
	}
	
	public static ExecutionReport convertToNewOrderReject(String clOrdID, Order order, String text) {
		char side;
		
		OrderSide orderSide = order.getOrderSide();
		if (orderSide.equals(OrderSide.Buy)) {
			side = Side.BUY;
		} else if (orderSide.equals(OrderSide.Sell)) {
			side = Side.SELL;
		} else if (orderSide.equals(OrderSide.ShortSell)) {
			side = Side.SELL_SHORT;
		} else {
			side = Side.UNDISCLOSED;
		}
		
		ExecutionReport executionReport = new ExecutionReport(new OrderID(null == order.getId() ? "NONE" : order.getId()),
				new ExecID(IdGenerator.getInstance().getNextID()), new ExecType(ExecType.REJECTED), new OrdStatus(OrdStatus.REJECTED), new Side(side),
				new LeavesQty(), new CumQty());
		executionReport.set(new Symbol(order.getSymbol()));
		executionReport.set(new AvgPx());
		executionReport.setField(new ClOrdID(clOrdID));
		if (text != null) {
			executionReport.setField(new Text(text));
		}
		executionReport.setField(new OrderQty(order.getQuantity()));
		executionReport.set(new AvgPx(order.getAvgPx()));
		executionReport.set(new Symbol(order.getSymbol()));
		
		if (order instanceof ExchangeOrder) {
			ExchangeOrder exchangeOrder = (ExchangeOrder) order;
			if (exchangeOrder.getAlgoOrderId() != null) {
				executionReport.setString(FixTags.AlgoOrderID, exchangeOrder.getAlgoOrderId());
			}
			if (exchangeOrder.getStrategyId() != null) {
				executionReport.setString(FixTags.StrategyID, exchangeOrder.getStrategyId());
			}
		}
		
		String origClOrdID = order.getOrigClOrdId();
		if ((origClOrdID != null) && (order.getOrdStatus() == com.huatai.common.type.OrdStatus.CANCELED)) {
			executionReport.setField(new ClOrdID(origClOrdID));
			executionReport.setField(new OrigClOrdID(clOrdID));
		} else {
			executionReport.setField(new ClOrdID(clOrdID));
		}
		
		Character timeInForce = order.getTimeInForce();
		if (timeInForce != null) {
			executionReport.set(new TimeInForce(timeInForce));
		}
		
		Integer maxPriceLevels = order.getMaxPriceLevels();
		if (maxPriceLevels != null) {
			executionReport.set(new MaxPriceLevels(maxPriceLevels));
		}
		
		Double minQty = order.getMinQty();
		if (minQty != null) {
			executionReport.set(new MinQty(minQty));
		}
		
		executionReport.setField(new StringField(FixTags.InvestorAccount, order.getInvestorAccount()));
		executionReport.setField(new StringField(FixTags.ProductAccount, order.getProductAccount()));
		executionReport.setField(new StringField(FixTags.AssetAccount, order.getAssetAccount()));
		executionReport.setField(new StringField(FixTags.AssetAccountType, order.getAssetAccountType().name()));
		executionReport.setField(new StringField(FixTags.TradingAccount, order.getTradingAccount()));
		executionReport.setField(new StringField(FixTags.SecurityAccount, order.getSecurityAccount()));
		executionReport.setField(new StringField(Currency.FIELD, order.getCurrency().name()));
		
		if (order.getPortfolioNo() != null) {
			executionReport.setString(FixTags.PortfolioNo, order.getPortfolioNo());
		}
		
		if (order.getPortfolioType() != null) {
			executionReport.setString(FixTags.PortfolioType, order.getPortfolioType());
		}
		
		UtcTimeStampField created = new UtcTimeStampField(FixTags.Created, true);
		created.setValue(order.getCreated());
		executionReport.setField(created);
		
		UtcTimeStampField modified = new UtcTimeStampField(FixTags.Modified, true);
		modified.setValue(order.getModified());
		executionReport.setField(modified);
		executionReport.setUtcTimeStamp(FixTags.UserCreatedTime, order.getTransactTime(), true);
		
		return executionReport;
	}
	
	public static ExecutionReport convertToNewOrderReject(Message message, String text) {
		ExecutionReport executionReport = null;
		
		try {
			char side = message.getField(new Side()).getValue();
			String symbol = message.getField(new Symbol()).getValue();
			
			executionReport = new ExecutionReport(new OrderID("NONE"), new ExecID(IdGenerator.getInstance().getNextID()),
					new ExecType(ExecType.REJECTED), new OrdStatus(OrdStatus.REJECTED), new Side(side), new LeavesQty(), new CumQty());
			executionReport.set(new Symbol(symbol));
			executionReport.set(new AvgPx());
			
			if (message.isSetField(FixTags.InvestorAccount)) {
				executionReport.setField(new StringField(FixTags.InvestorAccount, message.getString(FixTags.InvestorAccount)));
			}
			if (message.isSetField(FixTags.ProductAccount)) {
				executionReport.setField(new StringField(FixTags.ProductAccount, message.getString(FixTags.ProductAccount)));
			}
			if (message.isSetField(FixTags.AssetAccount)) {
				executionReport.setField(new StringField(FixTags.AssetAccount, message.getString(FixTags.AssetAccount)));
			}
			executionReport.setField(new StringField(FixTags.AssetAccountType, message.getString(FixTags.AssetAccountType)));
			if (message.isSetField(FixTags.TradingAccount)) {
				executionReport.setField(new StringField(FixTags.TradingAccount, message.getString(FixTags.TradingAccount)));
			}
			if (message.isSetField(FixTags.SecurityAccount)) {
				executionReport.setField(new StringField(FixTags.SecurityAccount, message.getString(FixTags.SecurityAccount)));
			}
			if (message.isSetField(Currency.FIELD)) {
				executionReport.setField(new StringField(Currency.FIELD, message.getString(Currency.FIELD)));
			}
			
			if (message.isSetField(ClOrdID.FIELD)) {
				executionReport.setField(message.getField(new ClOrdID()));
			}
			executionReport.setField(new Text(text));
		} catch (FieldNotFound e) {
			e.printStackTrace();
		}
		
		return executionReport;
	}
	
	public static ExecutionReport convertToAmendOrderReject(Message message, String text) {
		ExecutionReport executionReport = null;
		
		try {
			char side = message.getField(new Side()).getValue();
			String symbol = message.getField(new Symbol()).getValue();
			
			executionReport = new ExecutionReport(new OrderID("NONE"), new ExecID(IdGenerator.getInstance().getNextID()),
					new ExecType(ExecType.REJECTED), new OrdStatus(OrdStatus.REJECTED), new Side(side), new LeavesQty(), new CumQty());
			executionReport.set(new Symbol(symbol));
			executionReport.set(new AvgPx());
			
			if (message.isSetField(ClOrdID.FIELD)) {
				executionReport.setField(message.getField(new ClOrdID()));
			}
			executionReport.setField(new Text(text));
		} catch (FieldNotFound e) {
			e.printStackTrace();
		}
		
		return executionReport;
	}
	
	public static OrderCancelReject convertToOrderCancelReject(String origClOrderId, String clOrdId, Order order, String text) {
		String orderID = "NONE";
		char ordStatus = OrdStatus.REJECTED;
		
		if (order != null) {
			orderID = order.getId();
			ordStatus = order.getOrdStatus().value();
		}
		
		OrderCancelReject orderCancelReject = new OrderCancelReject(new OrderID(orderID), new ClOrdID(clOrdId), new OrdStatus(ordStatus),
				new CxlRejResponseTo(CxlRejResponseTo.ORDER_CANCEL_REQUEST));
		orderCancelReject.set(new OrigClOrdID(origClOrderId));
		orderCancelReject.setField(new Text(text));
		
		return orderCancelReject;
	}
	
	public static OrderCancelRequest convertToOrderCancelRequest(Order order) throws FixConvertionException {
		OrderCancelRequest orderCancelRequest = new OrderCancelRequest(new ClOrdID(idGenerator.getNextID()),
				new Side(FixUtil.toFixOrderSide(order.getOrderSide())), new TransactTime());
		orderCancelRequest.setField(new StringField(FixTags.InvestorAccount, order.getInvestorAccount()));
		orderCancelRequest.setField(new StringField(FixTags.ProductAccount, order.getProductAccount()));
		orderCancelRequest.setField(new StringField(FixTags.AssetAccount, order.getAssetAccount()));
		orderCancelRequest.setField(new StringField(FixTags.AssetAccountType, order.getAssetAccountType().name()));
		orderCancelRequest.setField(new StringField(FixTags.TradingAccount, order.getTradingAccount()));
		orderCancelRequest.setString(FixTags.SecurityAccount, order.getSecurityAccount());
		orderCancelRequest.set(new OrigClOrdID(order.getClOrdId()));
		
		return orderCancelRequest;
	}
	
	public static OrderCancelReplaceRequest convertToOrderCancelReplaceRequest(String clOrdId, Order order, Double newPx, Double newQty)
			throws FixConvertionException {
		return convertToOrderCancelReplaceRequest(clOrdId, order, order.getOrderSide(), order.getOrderType(), newPx, newQty);
	}
	
	public static OrderCancelReplaceRequest convertToOrderCancelReplaceRequest(String clOrderID, Order order, OrderSide orderSide,
			OrderType orderType, Double newPx, Double newQty) throws FixConvertionException {
		OrderCancelReplaceRequest orderCancelReplaceRequest = new OrderCancelReplaceRequest(new ClOrdID(idGenerator.getNextID()),
				new Side(FixUtil.toFixOrderSide(order.getOrderSide())), new TransactTime(), new OrdType(OrdType.LIMIT));
		orderCancelReplaceRequest.setField(new StringField(FixTags.InvestorAccount, order.getInvestorAccount()));
		orderCancelReplaceRequest.setField(new StringField(FixTags.ProductAccount, order.getProductAccount()));
		orderCancelReplaceRequest.setField(new StringField(FixTags.AssetAccount, order.getAssetAccount()));
		orderCancelReplaceRequest.setField(new StringField(FixTags.AssetAccountType, order.getAssetAccountType().name()));
		orderCancelReplaceRequest.setField(new StringField(FixTags.TradingAccount, order.getTradingAccount()));
		
		orderCancelReplaceRequest.set(new ClOrdID(clOrderID));
		orderCancelReplaceRequest.set(new OrigClOrdID(order.getClOrdId()));
		orderCancelReplaceRequest.set(new Currency(order.getCurrency().name()));
		if (order.getSecurityType() != null) {
			orderCancelReplaceRequest.set(new SecurityType(order.getSecurityType().name()));
		}
		orderCancelReplaceRequest.set(new Symbol(order.getSymbol()));
		orderCancelReplaceRequest.set(new Side(FixUtil.toFixOrderSide(orderSide)));
		orderCancelReplaceRequest.set(new OrdType(FixUtil.toFixOrderType(orderType)));
		
		if (newQty != null) {
			orderCancelReplaceRequest.set(new OrderQty(newQty));
		}
		
		if (newPx != null) {
			orderCancelReplaceRequest.set(new Price(newPx));
		}
		
		if (order.getTimeInForce() != null) {
			orderCancelReplaceRequest.set(new TimeInForce(order.getTimeInForce()));
		}
		
		if (order.getMaxPriceLevels() != null) {
			orderCancelReplaceRequest.set(new MaxPriceLevels(order.getMaxPriceLevels()));
		}
		
		if (order.getMinQty() != null) {
			orderCancelReplaceRequest.set(new MinQty(order.getMinQty()));
		}
		
		orderCancelReplaceRequest.setField(new StringField(FixTags.SecurityAccount, order.getSecurityAccount()));
		
		return orderCancelReplaceRequest;
	}
	
	public static OrderMassStatusRequest convertToOrderMassStatusRequest(String requestId, int snapshotType,
			SecurityTradingIndex securityTradingIndex, Date from, Date to, int offset, int pageSize) {
		OrderMassStatusRequest orderMassStatusRequest = new OrderMassStatusRequest(new MassStatusReqID(requestId),
				new MassStatusReqType(snapshotType));
		orderMassStatusRequest.setField(new StringField(FixTags.InvestorAccount, securityTradingIndex.getInvestorAccount()));
		orderMassStatusRequest.setField(new StringField(FixTags.ProductAccount, securityTradingIndex.getProductAccount()));
		orderMassStatusRequest.setField(new StringField(FixTags.AssetAccount, securityTradingIndex.getAssetAccount()));
		orderMassStatusRequest.setField(new StringField(FixTags.AssetAccountType, securityTradingIndex.getAssetAccountType().name()));
		orderMassStatusRequest.setField(new StringField(FixTags.TradingAccount, securityTradingIndex.getTradingAccount()));
		
		if (securityTradingIndex.getSecurityAccount() != null) {
			orderMassStatusRequest.setString(FixTags.SecurityAccount, securityTradingIndex.getSecurityAccount());
		}
		
		UtcTimeStampField fromTime = new UtcTimeStampField(FixTags.FromTime, true);
		fromTime.setValue(from);
		orderMassStatusRequest.setField(fromTime);
		
		UtcTimeStampField toTime = new UtcTimeStampField(FixTags.ToTime, true);
		toTime.setValue(to);
		orderMassStatusRequest.setField(toTime);
		
		orderMassStatusRequest.setField(new IntField(FixTags.Offset, offset));
		orderMassStatusRequest.setField(new IntField(FixTags.PageSize, pageSize));
		return orderMassStatusRequest;
	}
	
	public static ExecutionReport orderToExecutionReport(com.huatai.common.type.ExecType execType, String execID, Order order,
			TradingFund tradingFund, TradingPosition tradingPosition, DataObject dataObject) throws FixConvertionException {
		double leavesQty = order.getOrdStatus().isCompleted() ? order.getQuantity() - order.getCumQty() : 0;
		ExecID executionID = (execID != null) ? new ExecID(execID) : new ExecID(idGenerator.getNextID());
		ExecutionReport executionReport = new ExecutionReport(new OrderID(order.getId()), executionID, new ExecType(execType.value()),
				new OrdStatus(order.getOrdStatus().value()), new Side(FixUtil.toFixOrderSide(order.getOrderSide())), new LeavesQty(leavesQty),
				new CumQty(order.getCumQty()));
		executionReport.set(new AvgPx(order.getAvgPx()));
		executionReport.set(new Symbol(order.getSymbol()));
		
		if (order instanceof ExchangeOrder) {
			ExchangeOrder exchangeOrder = (ExchangeOrder) order;
			if (exchangeOrder.getAlgoOrderId() != null) {
				executionReport.setString(FixTags.AlgoOrderID, exchangeOrder.getAlgoOrderId());
			}
			if (exchangeOrder.getStrategyId() != null) {
				executionReport.setString(FixTags.StrategyID, exchangeOrder.getStrategyId());
			}
		}
		
		String clOrdID = order.getClOrdId();
		String origClOrdID = order.getOrigClOrdId();
		if ((origClOrdID != null) && (order.getOrdStatus() == com.huatai.common.type.OrdStatus.CANCELED)) {
			executionReport.setField(new ClOrdID(origClOrdID));
			executionReport.setField(new OrigClOrdID(clOrdID));
		} else {
			executionReport.setField(new ClOrdID(clOrdID));
		}
		
		Character timeInForce = order.getTimeInForce();
		if (timeInForce != null) {
			executionReport.set(new TimeInForce(timeInForce));
		}
		
		Integer maxPriceLevels = order.getMaxPriceLevels();
		if (maxPriceLevels != null) {
			executionReport.set(new MaxPriceLevels(maxPriceLevels));
		}
		
		Double minQty = order.getMinQty();
		if (minQty != null) {
			executionReport.set(new MinQty(minQty));
		}
		
		executionReport.setField(new OrderQty(order.getQuantity()));
		executionReport.setField(new ExecType(execType.value()));
		
		if (order.getPrice() != null) {
			executionReport.setField(new Price(order.getPrice()));
		}
		
		if (execType == com.huatai.common.type.ExecType.TRADE) {
			executionReport.setField(new LastShares(order.getLastQty()));
			executionReport.setField(new LastPx(order.getLastPx()));
		}
		
		executionReport.setField(new StringField(FixTags.InvestorAccount, order.getInvestorAccount()));
		executionReport.setField(new StringField(FixTags.ProductAccount, order.getProductAccount()));
		executionReport.setField(new StringField(FixTags.AssetAccount, order.getAssetAccount()));
		executionReport.setField(new StringField(FixTags.AssetAccountType, order.getAssetAccountType().name()));
		executionReport.setField(new StringField(FixTags.TradingAccount, order.getTradingAccount()));
		executionReport.setField(new StringField(FixTags.SecurityAccount, order.getSecurityAccount()));
		executionReport.setField(new StringField(Currency.FIELD, order.getCurrency().name()));
		
		if (tradingFund != null) {
			executionReport.setField(new DoubleField(FixTags.YesterdaySettledCash, tradingFund.getYesterdaySettledCash()));
			executionReport.setField(new DoubleField(FixTags.HoldingCash, tradingFund.getHoldingCash()));
			executionReport.setField(new DoubleField(FixTags.TradableCash, tradingFund.getTradableCash()));
		}
		
		if (tradingPosition != null) {
			executionReport.setField(new DoubleField(FixTags.YesterdaySettledBoughtAmount, tradingPosition.getYesterdaySettledBoughtAmount()));
			executionReport.setField(new DoubleField(FixTags.YesterdaySettledBoughtQty, tradingPosition.getYesterdaySettledBoughtQty()));
			executionReport.setField(new DoubleField(FixTags.YesterdaySettledSoldAmount, tradingPosition.getYesterdaySettledSoldAmount()));
			executionReport.setField(new DoubleField(FixTags.YesterdaySettledSoldQty, tradingPosition.getYesterdaySettledSoldQty()));
			executionReport.setField(new DoubleField(FixTags.IntradayBoughtAmount, tradingPosition.getIntradayBoughtAmount()));
			executionReport.setField(new DoubleField(FixTags.IntradayBoughtQty, tradingPosition.getIntradayBoughtQty()));
			executionReport.setField(new DoubleField(FixTags.IntradaySoldAmount, tradingPosition.getIntradaySoldAmount()));
			executionReport.setField(new DoubleField(FixTags.IntradaySoldQty, tradingPosition.getIntradaySoldQty()));
			executionReport.setField(new DoubleField(FixTags.HoldingQty, tradingPosition.getHoldingQty()));
			executionReport.setField(new DoubleField(FixTags.AvgCost, tradingPosition.getAvgCost()));
			executionReport.setField(new DoubleField(FixTags.TradableQty, tradingPosition.getTradableQty()));
		}
		
		if (order.getPortfolioNo() != null) {
			executionReport.setString(FixTags.PortfolioNo, order.getPortfolioNo());
		}
		
		if (order.getPortfolioType() != null) {
			executionReport.setString(FixTags.PortfolioType, order.getPortfolioType());
		}
		
		UtcTimeStampField created = new UtcTimeStampField(FixTags.Created, true);
		created.setValue(order.getCreated());
		executionReport.setField(created);
		
		UtcTimeStampField modified = new UtcTimeStampField(FixTags.Modified, true);
		modified.setValue(order.getModified());
		executionReport.setField(modified);
		
		executionReport.setUtcTimeStamp(FixTags.UserCreatedTime, order.getTransactTime(), true);
		
		if (dataObject != null) {
			if (dataObject.fieldExists(OrderField.IntradayTradingCumBuyFreezedQty.name())) {
				executionReport.setDouble(FixTags.IntradayTradingCumBuyFreezedQty,
						dataObject.get(Double.class, OrderField.IntradayTradingCumBuyFreezedQty.name()));
			}
		}
		if (tradingPosition != null) {
			executionReport.setDouble(FixTags.IntradayTradingCumSellFreezedQty, tradingPosition.getFreezedQty());
		}
		
		return executionReport;
	}
	
	public static NewOrderSingle convertToNewOrderSingle(Order order) throws FixConvertionException {
		NewOrderSingle newOrderSingle = new NewOrderSingle(new ClOrdID(order.getClOrdId()), new Side(FixUtil.toFixOrderSide(order.getOrderSide())),
				new TransactTime(), new OrdType(FixUtil.toFixOrderType(order.getOrderType())));
		newOrderSingle.set(new Currency(order.getCurrency().name()));
		
		if (order.getSecurityType() != null) {
			newOrderSingle.set(new SecurityType(order.getSecurityType().name()));
		}
		newOrderSingle.set(new Symbol(order.getSymbol()));
		newOrderSingle.set(new OrderQty(order.getQuantity()));
		
		if (order.getPrice() != null) {
			newOrderSingle.set(new Price(order.getPrice()));
		}
		
		if (order.getTimeInForce() != null) {
			newOrderSingle.set(new TimeInForce(order.getTimeInForce()));
		}
		
		if (order.getMaxPriceLevels() != null) {
			newOrderSingle.set(new MaxPriceLevels(order.getMaxPriceLevels()));
		}
		
		if (order.getMinQty() != null) {
			newOrderSingle.set(new MinQty(order.getMinQty()));
		}
		
		newOrderSingle.setField(new StringField(FixTags.InvestorAccount, order.getInvestorAccount()));
		newOrderSingle.setField(new StringField(FixTags.ProductAccount, order.getProductAccount()));
		newOrderSingle.setField(new StringField(FixTags.AssetAccount, order.getAssetAccount()));
		newOrderSingle.setField(new StringField(FixTags.AssetAccountType, order.getAssetAccountType().name()));
		newOrderSingle.setField(new StringField(FixTags.TradingAccount, order.getTradingAccount()));
		newOrderSingle.setField(new StringField(FixTags.SecurityAccount, order.getSecurityAccount()));
		
		if (order.getPortfolioNo() != null) {
			newOrderSingle.setString(FixTags.PortfolioNo, order.getPortfolioNo());
		}
		
		return newOrderSingle;
	}
	
	public static AdjustedPositionReport convertToAdjustedPositionReport(String requestID, TradingAccountIndex tradingAccountIndex,
			Collection<TradingPosition> tradingPositions, Collection<PortfolioPosition> portfolioPositions, DataObject dataObject) {
		AdjustedPositionReport adjustPositionReport = new AdjustedPositionReport(new PosMaintRptID(requestID),
				new ClearingBusinessDate(new Date().toString()));
		
		adjustPositionReport.setField(new StringField(FixTags.InvestorAccount, tradingAccountIndex.getInvestorAccount()));
		adjustPositionReport.setField(new StringField(FixTags.ProductAccount, tradingAccountIndex.getProductAccount()));
		adjustPositionReport.setField(new StringField(FixTags.AssetAccount, tradingAccountIndex.getAssetAccount()));
		adjustPositionReport.setField(new StringField(FixTags.AssetAccountType, tradingAccountIndex.getAssetAccountType().name()));
		adjustPositionReport.setField(new StringField(FixTags.TradingAccount, tradingAccountIndex.getTradingAccount()));
		
		if (tradingPositions != null) {
			for (TradingPosition tradingPosition : tradingPositions) {
				AdjustedPositionReport.NoRelatedSym noRelatedSym = new AdjustedPositionReport.NoRelatedSym();
				noRelatedSym.setField(new Symbol(tradingPosition.getSymbol()));
				noRelatedSym.setField(new StringField(FixTags.SecurityAccount, tradingPosition.getSecurityAccount()));
				noRelatedSym.setField(new DoubleField(FixTags.YesterdaySettledBoughtAmount, tradingPosition.getYesterdaySettledBoughtAmount()));
				noRelatedSym.setField(new DoubleField(FixTags.YesterdaySettledBoughtQty, tradingPosition.getYesterdaySettledBoughtQty()));
				noRelatedSym.setField(new DoubleField(FixTags.YesterdaySettledSoldAmount, tradingPosition.getYesterdaySettledSoldAmount()));
				noRelatedSym.setField(new DoubleField(FixTags.YesterdaySettledSoldQty, tradingPosition.getYesterdaySettledSoldQty()));
				noRelatedSym.setField(new DoubleField(FixTags.IntradayBoughtAmount, tradingPosition.getIntradayBoughtAmount()));
				noRelatedSym.setField(new DoubleField(FixTags.IntradayBoughtQty, tradingPosition.getIntradayBoughtQty()));
				noRelatedSym.setField(new DoubleField(FixTags.IntradaySoldAmount, tradingPosition.getIntradaySoldAmount()));
				noRelatedSym.setField(new DoubleField(FixTags.IntradaySoldQty, tradingPosition.getIntradaySoldQty()));
				noRelatedSym.setField(new DoubleField(FixTags.HoldingQty, tradingPosition.getHoldingQty()));
				noRelatedSym.setField(new DoubleField(FixTags.AvgCost, tradingPosition.getAvgCost()));
				noRelatedSym.setField(new DoubleField(FixTags.TradableQty, tradingPosition.getTradableQty()));
				appendPositionExtensionData(noRelatedSym, dataObject);
				adjustPositionReport.addGroup(noRelatedSym);
			}
		}
		
		if (portfolioPositions != null) {
			for (PortfolioPosition portfolioPosition : portfolioPositions) {
				AdjustedPositionReport.NoRelatedSym noRelatedSym = new AdjustedPositionReport.NoRelatedSym();
				noRelatedSym.setField(new Symbol(portfolioPosition.getSymbol()));
				noRelatedSym.setField(new StringField(FixTags.PortfolioNo, portfolioPosition.getPortfolioNo()));
				noRelatedSym.setField(new StringField(FixTags.SecurityAccount, portfolioPosition.getSecurityAccount()));
				noRelatedSym.setField(new DoubleField(FixTags.YesterdaySettledBoughtAmount, portfolioPosition.getYesterdaySettledBoughtAmount()));
				noRelatedSym.setField(new DoubleField(FixTags.YesterdaySettledBoughtQty, portfolioPosition.getYesterdaySettledBoughtQty()));
				noRelatedSym.setField(new DoubleField(FixTags.YesterdaySettledSoldAmount, portfolioPosition.getYesterdaySettledSoldAmount()));
				noRelatedSym.setField(new DoubleField(FixTags.YesterdaySettledSoldQty, portfolioPosition.getYesterdaySettledSoldQty()));
				noRelatedSym.setField(new DoubleField(FixTags.IntradayBoughtAmount, portfolioPosition.getIntradayBoughtAmount()));
				noRelatedSym.setField(new DoubleField(FixTags.IntradayBoughtQty, portfolioPosition.getIntradayBoughtQty()));
				noRelatedSym.setField(new DoubleField(FixTags.IntradaySoldAmount, portfolioPosition.getIntradaySoldAmount()));
				noRelatedSym.setField(new DoubleField(FixTags.IntradaySoldQty, portfolioPosition.getIntradaySoldQty()));
				noRelatedSym.setField(new DoubleField(FixTags.HoldingQty, portfolioPosition.getHoldingQty()));
				noRelatedSym.setField(new DoubleField(FixTags.AvgCost, portfolioPosition.getAvgCost()));
				noRelatedSym.setField(new DoubleField(FixTags.TradableQty, portfolioPosition.getTradableQty()));
				appendPositionExtensionData(noRelatedSym, dataObject);
				adjustPositionReport.addGroup(noRelatedSym);
			}
		}
		
		return adjustPositionReport;
	}
	
	private static void appendPositionExtensionData(AdjustedPositionReport.NoRelatedSym noRelatedSym, DataObject dataObject) {
		
	}
	
	public static Collection<TradingPosition> parseAdjustedPositionReportForTradingPosition(Message message) throws FieldNotFound {
		Collection<TradingPosition> tradingPositions = new ArrayList<TradingPosition>();
		
		int noRelatedSym = message.getGroupCount(quickfix.field.NoRelatedSym.FIELD);
		for (int i = 1; i <= noRelatedSym; i++) {
			AdjustedPositionReport.NoRelatedSym group = new AdjustedPositionReport.NoRelatedSym();
			message.getGroup(i, group);
			TradingPosition tradingPosition = new TradingPosition();
			tradingPosition.setInvestorAccount(message.getString(FixTags.InvestorAccount));
			tradingPosition.setProductAccount(message.getString(FixTags.ProductAccount));
			tradingPosition.setAssetAccount(message.getString(FixTags.AssetAccount));
			tradingPosition.setAssetAccountType(AssetAccountType.valueOf(message.getString(FixTags.AssetAccountType)));
			tradingPosition.setTradingAccount(message.getString(FixTags.TradingAccount));
			tradingPosition.setSecurityAccount(group.getString(FixTags.SecurityAccount));
			tradingPosition.setSymbol(group.getString(Symbol.FIELD));
			tradingPosition.setYesterdaySettledBoughtAmount(group.getDouble(FixTags.YesterdaySettledBoughtAmount));
			tradingPosition.setYesterdaySettledBoughtQty(group.getDouble(FixTags.YesterdaySettledBoughtQty));
			tradingPosition.setYesterdaySettledSoldAmount(group.getDouble(FixTags.YesterdaySettledSoldAmount));
			tradingPosition.setYesterdaySettledSoldQty(group.getDouble(FixTags.YesterdaySettledSoldQty));
			tradingPosition.setIntradayBoughtAmount(group.getDouble(FixTags.IntradayBoughtAmount));
			tradingPosition.setIntradayBoughtQty(group.getDouble(FixTags.IntradayBoughtQty));
			tradingPosition.setIntradaySoldAmount(group.getDouble(FixTags.IntradaySoldAmount));
			tradingPosition.setIntradaySoldQty(group.getDouble(FixTags.IntradaySoldQty));
			tradingPosition.setHoldingQty(group.getDouble(FixTags.HoldingQty));
			tradingPosition.setAvgCost(group.getDouble(FixTags.AvgCost));
			tradingPosition.setTradableQty(group.getDouble(FixTags.TradableQty));
			tradingPositions.add(tradingPosition);
		}
		
		return tradingPositions;
	}
	
	public static Collection<PortfolioPosition> parseAdjustedPositionReportForPortfolioPosition(Message message) throws FieldNotFound {
		Collection<PortfolioPosition> portfolioPositions = new ArrayList<PortfolioPosition>();
		
		int noRelatedSym = message.getGroupCount(quickfix.field.NoRelatedSym.FIELD);
		for (int i = 1; i <= noRelatedSym; i++) {
			AdjustedPositionReport.NoRelatedSym group = new AdjustedPositionReport.NoRelatedSym();
			message.getGroup(i, group);
			PortfolioPosition portfolioPosition = new PortfolioPosition();
			portfolioPosition.setInvestorAccount(message.getString(FixTags.InvestorAccount));
			portfolioPosition.setProductAccount(message.getString(FixTags.ProductAccount));
			portfolioPosition.setAssetAccount(message.getString(FixTags.AssetAccount));
			portfolioPosition.setAssetAccountType(AssetAccountType.valueOf(message.getString(FixTags.AssetAccountType)));
			portfolioPosition.setTradingAccount(message.getString(FixTags.TradingAccount));
			portfolioPosition.setSymbol(group.getString(Symbol.FIELD));
			portfolioPosition.setPortfolioNo(group.getString(FixTags.PortfolioNo));
			portfolioPosition.setSecurityAccount(group.getString(FixTags.SecurityAccount));
			portfolioPosition.setYesterdaySettledBoughtAmount(group.getDouble(FixTags.YesterdaySettledBoughtAmount));
			portfolioPosition.setYesterdaySettledBoughtQty(group.getDouble(FixTags.YesterdaySettledBoughtQty));
			portfolioPosition.setYesterdaySettledSoldAmount(group.getDouble(FixTags.YesterdaySettledSoldAmount));
			portfolioPosition.setYesterdaySettledSoldQty(group.getDouble(FixTags.YesterdaySettledSoldQty));
			portfolioPosition.setIntradayBoughtAmount(group.getDouble(FixTags.IntradayBoughtAmount));
			portfolioPosition.setIntradayBoughtQty(group.getDouble(FixTags.IntradayBoughtQty));
			portfolioPosition.setIntradaySoldAmount(group.getDouble(FixTags.IntradaySoldAmount));
			portfolioPosition.setIntradaySoldQty(group.getDouble(FixTags.IntradaySoldQty));
			portfolioPosition.setHoldingQty(group.getDouble(FixTags.HoldingQty));
			portfolioPosition.setTradableQty(group.getDouble(FixTags.TradableQty));
			portfolioPosition.setAvgCost(group.getDouble(FixTags.AvgCost));
			portfolioPositions.add(portfolioPosition);
		}
		
		return portfolioPositions;
	}
	
	public static SecurityList convertToSecurityList(String requestID, Collection<RefData> refDatas, boolean endRecord) {
		SecurityList securitylist = new SecurityList();
		securitylist.set(new SecurityReqID(requestID));
		securitylist.setField(new BooleanField(FixTags.EndRecord, endRecord));
		for (RefData refData : refDatas) {
			SecurityList.NoRelatedSym norelatedsym = new SecurityList.NoRelatedSym();
			String symbols = refData.getSymbol();
			if ((symbols == null) || symbols.isEmpty()) {
				continue;
			}
			norelatedsym.set(new Symbol(symbols));
			if ((refData.getEnglishName() != null) && !refData.getEnglishName().isEmpty()) {
				norelatedsym.setField(new StringField(FixTags.EnglishName, refData.getEnglishName()));
			}
			if ((refData.getChineseName() != null) && !refData.getChineseName().isEmpty()) {
				norelatedsym.setField(new StringField(FixTags.ChineseName, refData.getChineseName()));
			}
			if ((refData.getIndustryClassification() != null) && !refData.getIndustryClassification().isEmpty()) {
				norelatedsym.setField(new StringField(FixTags.IndustryClassification, refData.getIndustryClassification()));
			}
			norelatedsym.setField(new IntField(FixTags.QtyUnit, refData.getQtyUnit()));
			norelatedsym.setField(new DoubleField(FixTags.ParValue, refData.getParValue()));
			norelatedsym.setField(new DoubleField(FixTags.OutstandingShare, refData.getOutstandingShare()));
			norelatedsym.setField(new DoubleField(FixTags.PublicFloatShareQuantity, refData.getPublicFloatShareQuantity()));
			if ((refData.getCrdBuyUnderlying() != null) && !refData.getCrdBuyUnderlying().isEmpty()) {
				norelatedsym.setField(new StringField(FixTags.CrdBuyUnderlying, refData.getCrdBuyUnderlying()));
			}
			if ((refData.getCrdSellUnderlying() != null) && !refData.getCrdSellUnderlying().isEmpty()) {
				norelatedsym.setField(new StringField(FixTags.CrdSellUnderlying, refData.getCrdSellUnderlying()));
			}
			norelatedsym.setField(new DoubleField(FixTags.GageRatio, refData.getGageRatio()));
			norelatedsym.setField(new DoubleField(PrevClosePx.FIELD, refData.getPreviousClosingPx()));
			if ((refData.getStatus() != null) && !refData.getStatus().isEmpty()) {
				norelatedsym.setField(new StringField(FixTags.SecurityStatusCN, refData.getStatus()));
			}
			if (refData.getSecurityType() != null) {
				norelatedsym.setField(new IntField(FixTags.SecurityTypeCN, refData.getSecurityType()));
			}
			if ((refData.getSpellCode() != null) && !refData.getSpellCode().isEmpty()) {
				norelatedsym.setField(new StringField(FixTags.SpellCode, refData.getSpellCode()));
			}
			
			norelatedsym.setField(new DoubleField(HighLimitPrice.FIELD, refData.getHighLimitPx()));
			norelatedsym.setField(new DoubleField(LowLimitPrice.FIELD, refData.getLowLimitPx()));
			
			if (refData.getInterestAccrualDate() != null) {
				norelatedsym.set(new InterestAccrualDate(refData.getInterestAccrualDate().toString()));
			}
			if (refData.getCurrency() != null) {
				norelatedsym.set(new Currency(refData.getCurrency().name()));
			}
			securitylist.addGroup(norelatedsym);
		}
		return securitylist;
	}
	
	public static Collection<RefData> parseSecurityList(Message message) throws FieldNotFound, FixConvertionException {
		Collection<RefData> refDatas = new ArrayList<RefData>();
		
		int size = 0;
		if (message.isSetField(quickfix.field.NoRelatedSym.FIELD)) {
			size = message.getField(new quickfix.field.NoRelatedSym()).getValue();
		}
		
		for (int i = 1; i <= size; i++) {
			SecurityList.NoRelatedSym norelatedSym = new SecurityList.NoRelatedSym();
			message.getGroup(i, norelatedSym);
			
			RefData refData = new RefData();
			refData.setSymbol(norelatedSym.getSymbol().getValue());
			if (norelatedSym.isSetField(FixTags.ChineseName)) {
				refData.setChineseName(norelatedSym.getField(new StringField(FixTags.ChineseName)).getValue());
			}
			if (norelatedSym.isSetField(FixTags.SpellCode)) {
				refData.setSpellCode(norelatedSym.getField(new StringField(FixTags.SpellCode)).getValue());
			}
			if (norelatedSym.isSetField(FixTags.EnglishName)) {
				refData.setEnglishName(norelatedSym.getField(new StringField(FixTags.EnglishName)).getValue());
			}
			if (norelatedSym.isSetField(FixTags.IndustryClassification)) {
				refData.setIndustryClassification(norelatedSym.getField(new StringField(FixTags.IndustryClassification)).getValue());
			}
			if (norelatedSym.isSetField(FixTags.OutstandingShare)) {
				refData.setOutstandingShare(norelatedSym.getField(new DoubleField(FixTags.OutstandingShare)).getValue());
			}
			if (norelatedSym.isSetField(FixTags.PublicFloatShareQuantity)) {
				refData.setPublicFloatShareQuantity(norelatedSym.getField(new DoubleField(FixTags.PublicFloatShareQuantity)).getValue());
			}
			if (norelatedSym.isSetField(FixTags.QtyUnit)) {
				refData.setQtyUnit(norelatedSym.getField(new IntField(FixTags.QtyUnit)).getValue());
			}
			if (norelatedSym.isSetField(FixTags.ParValue)) {
				refData.setParValue(norelatedSym.getField(new DoubleField(FixTags.ParValue)).getValue());
			}
			if (norelatedSym.isSetField(FixTags.CrdBuyUnderlying)) {
				refData.setCrdBuyUnderlying(norelatedSym.getField(new StringField(FixTags.CrdBuyUnderlying)).getValue());
			}
			if (norelatedSym.isSetField(FixTags.CrdSellUnderlying)) {
				refData.setCrdSellUnderlying(norelatedSym.getField(new StringField(FixTags.CrdSellUnderlying)).getValue());
			}
			if (norelatedSym.isSetField(FixTags.GageRatio)) {
				refData.setGageRatio(norelatedSym.getField(new DoubleField(FixTags.GageRatio)).getValue());
			}
			if (norelatedSym.isSetField(PrevClosePx.FIELD)) {
				refData.setPreviousClosingPx(norelatedSym.getField(new PrevClosePx()).getValue());
			}
			if (norelatedSym.isSetField(HighLimitPrice.FIELD)) {
				refData.setHighLimitPx(norelatedSym.getField(new HighLimitPrice()).getValue());
			}
			if (norelatedSym.isSetField(LowLimitPrice.FIELD)) {
				refData.setLowLimitPx(norelatedSym.getField(new LowLimitPrice()).getValue());
			}
			if (norelatedSym.isSetField(FixTags.SecurityStatusCN)) {
				refData.setStatus(norelatedSym.getField(new StringField(FixTags.SecurityStatusCN)).getValue());
			}
			if (norelatedSym.isSetField(FixTags.SecurityTypeCN)) {
				refData.setSecurityType(norelatedSym.getField(new IntField(FixTags.SecurityTypeCN)).getValue());
			}
			if (norelatedSym.isSetField(InterestAccrualDate.FIELD)) {
				refData.setInterestAccrualDate(Integer.getInteger(norelatedSym.getInterestAccrualDate().getValue()));
			}
			if (norelatedSym.isSetField(Currency.FIELD)) {
				refData.setCurrency(com.huatai.common.type.Currency.valueOf(norelatedSym.getCurrency().getValue()));
			}
			refDatas.add(refData);
		}
		return refDatas;
	}
	
	public static AlgoOrder parseAlgoOrderSnapshot(Group group) throws FieldNotFound, FixConvertionException {
		AlgoOrder order = new AlgoOrder();
		order.setInvestorAccount(group.getString(FixTags.InvestorAccount));
		order.setProductAccount(group.getString(FixTags.ProductAccount));
		order.setAssetAccount(group.getString(FixTags.AssetAccount));
		order.setAssetAccountType(AssetAccountType.valueOf(group.getString(FixTags.AssetAccountType)));
		order.setTradingAccount(group.getString(FixTags.TradingAccount));
		
		order.setId(group.getField(new StringField(OrderID.FIELD)).getValue());
		order.setSymbol(group.getField(new StringField(Symbol.FIELD)).getValue());
		order.setOrderSide(FixUtil.fromFixOrderSide(group.getField(new CharField(Side.FIELD)).getValue()));
		order.setOrderType(FixUtil.fromFixOrderType(group.getField(new CharField(OrdType.FIELD)).getValue()));
		order.setOrdStatus(com.huatai.common.type.OrdStatus.getType((group.getField(new CharField(OrdStatus.FIELD)).getValue())));
		order.setPrice(group.getField(new DoubleField(Price.FIELD)).getValue());
		order.setQuantity(group.getField(new DoubleField(OrderQty.FIELD)).getValue());
		order.setAvgPx(group.getField(new DoubleField(AvgPx.FIELD)).getValue());
		order.setCumQty(group.getField(new DoubleField(CumQty.FIELD)).getValue());
		order.setClOrdId(group.getField(new StringField(ClOrdID.FIELD)).getValue());
		
		if (group.isSetField(FixTags.UserCreatedTime)) {
			order.setTransactTime(new Timestamp(group.getUtcTimeStamp(FixTags.UserCreatedTime).getTime()));
		}
		
		if (group.isSetField(FixTags.Algo)) {
			order.setAlgo(group.getField(new StringField(FixTags.Algo)).getValue());
		}
		
		if (group.isSetField(FixTags.SecurityAccount)) {
			order.setSecurityAccount(group.getField(new StringField(FixTags.SecurityAccount)).getValue());
		}
		
		order.setCreated(new Timestamp(group.getField(new UtcTimeStampField(FixTags.Created)).getValue().getTime()));
		order.setModified(new Timestamp(group.getField(new UtcTimeStampField(FixTags.Modified)).getValue().getTime()));
		
		return order;
	}
	
	public static ExchangeOrder parseExchangeOrderSnapshot(Group group) throws FieldNotFound, FixConvertionException {
		String symbol = group.getField(new StringField(Symbol.FIELD)).getValue();
		OrderSide side = FixUtil.fromFixOrderSide(group.getField(new CharField(Side.FIELD)).getValue());
		double quantity = group.getField(new DoubleField(OrderQty.FIELD)).getValue();
		double price = group.getField(new DoubleField(Price.FIELD)).getValue();
		OrderType orderType = FixUtil.fromFixExchangeOrderType(group.getField(new CharField(OrdType.FIELD)).getValue());
		String clOrdId = group.getField(new StringField(ClOrdID.FIELD)).getValue();
		String algoOrderId = group.isSetField(FixTags.AlgoOrderID) ? group.getString(FixTags.AlgoOrderID) : null;
		
		String investorAccount = group.getString(FixTags.InvestorAccount);
		String productAccount = group.getString(FixTags.ProductAccount);
		String assetAccount = group.getString(FixTags.AssetAccount);
		AssetAccountType assetAccountType = AssetAccountType.valueOf(group.getString(FixTags.AssetAccountType));
		String tradingAccount = group.getString(FixTags.TradingAccount);
		String securityAccount = group.getString(FixTags.SecurityAccount);
		
		SecurityTradingIndex index = new SecurityTradingIndex(new AssetAccountIndex(investorAccount, productAccount, assetAccount, assetAccountType),
				tradingAccount, securityAccount);
		ExchangeOrder order = new ExchangeOrder(index, symbol, side, quantity, price, orderType, clOrdId);
		order.setAlgoOrderId(algoOrderId);
		order.setId(group.getField(new StringField(OrderID.FIELD)).getValue());
		order.setOrdStatus(com.huatai.common.type.OrdStatus.getType((group.getField(new CharField(OrdStatus.FIELD)).getValue())));
		order.setAvgPx(group.getField(new DoubleField(AvgPx.FIELD)).getValue());
		order.setCumQty(group.getField(new DoubleField(CumQty.FIELD)).getValue());
		order.setCreated(new Timestamp(group.getField(new UtcTimeStampField(FixTags.Created)).getValue().getTime()));
		order.setModified(new Timestamp(group.getField(new UtcTimeStampField(FixTags.Modified)).getValue().getTime()));
		order.setTransactTime(new Timestamp(group.getUtcTimeStamp(FixTags.UserCreatedTime).getTime()));
		
		return order;
	}
	
	public static NewOrderList convertToNewOrderList(Collection<Order> orders, TradingAccountIndex tradingAccountIndex)
			throws FixConvertionException {
		NewOrderList newOrderList = new NewOrderList(new ListID(idGenerator.getNextID()), new BidType(BidType.NO_BIDDING_PROCESS),
				new TotNoOrders(orders.size()));
		int i = 1;
		for (Order order : orders) {
			NewOrderList.NoOrders noOrders = new NewOrderList.NoOrders();
			noOrders.set(new ListSeqNo(i++));
			noOrders.set(new Symbol(order.getSymbol()));
			if (order.getSecurityType() != null) {
				noOrders.set(new SecurityType(order.getSecurityType().name()));
			}
			noOrders.set(new OrdType(FixUtil.toFixOrderType(order.getOrderType())));
			noOrders.set(new Side(FixUtil.toFixOrderSide(order.getOrderSide())));
			noOrders.set(new OrderQty(order.getQuantity()));
			noOrders.set(new TransactTime());
			noOrders.set(new Currency(order.getCurrency().name()));
			noOrders.set(new ClOrdID(order.getClOrdId()));
			if (null != order.getPrice()) {
				noOrders.set(new Price(order.getPrice()));
			}
			noOrders.setString(FixTags.SecurityAccount, order.getSecurityAccount());
			newOrderList.addGroup(noOrders);
		}
		
		newOrderList.setField(new StringField(FixTags.InvestorAccount, tradingAccountIndex.getInvestorAccount()));
		newOrderList.setField(new StringField(FixTags.ProductAccount, tradingAccountIndex.getProductAccount()));
		newOrderList.setField(new StringField(FixTags.AssetAccount, tradingAccountIndex.getAssetAccount()));
		newOrderList.setField(new StringField(FixTags.AssetAccountType, tradingAccountIndex.getAssetAccountType().name()));
		newOrderList.setField(new StringField(FixTags.TradingAccount, tradingAccountIndex.getTradingAccount()));
		
		return newOrderList;
	}
	
	public static TradingSessionStatus convertToTradingSessionStatus(String tradSesReqID, ExchangeType exchangeType, int tradSesStatus, String text) {
		String DAY = "1";
		TradingSessionStatus tradingSessionStatus = new TradingSessionStatus();
		tradingSessionStatus.set(new TradingSessionID(DAY));
		tradingSessionStatus.set(new TradSesStatus(tradSesStatus));
		tradingSessionStatus.set(new TradSesReqID(tradSesReqID));
		if (exchangeType != null) {
			tradingSessionStatus.set(new MarketID(exchangeType.name()));
		}
		tradingSessionStatus.set(new Text(text));
		
		return tradingSessionStatus;
	}
	
	public static TradingSessionStatusRequest convertToTradingSessionStatusRequest(String tradSesReqID, char subscriptionType, String marketID) {
		TradingSessionStatusRequest tradSesStatusRequest = new TradingSessionStatusRequest();
		tradSesStatusRequest.set(new TradSesReqID(tradSesReqID));
		tradSesStatusRequest.set(new SubscriptionRequestType(subscriptionType));
		tradSesStatusRequest.set(new MarketID(marketID));
		
		return tradSesStatusRequest;
	}
	
	public static AssetAccountIndex parseAssetAccountIndex(Message message) throws FieldNotFound {
		AssetAccountIndex assetAccountIndex = new AssetAccountIndex();
		
		if (message.isSetField(FixTags.InvestorAccount)) {
			assetAccountIndex.setInvestorAccount(message.getString(FixTags.InvestorAccount));
		}
		if (message.isSetField(FixTags.ProductAccount)) {
			assetAccountIndex.setProductAccount(message.getString(FixTags.ProductAccount));
		}
		if (message.isSetField(FixTags.AssetAccount)) {
			assetAccountIndex.setAssetAccount(message.getString(FixTags.AssetAccount));
		}
		if (message.isSetField(FixTags.AssetAccountType)) {
			assetAccountIndex.setAssetAccountType(AssetAccountType.valueOf(message.getString(FixTags.AssetAccountType)));
		}
		
		return assetAccountIndex;
	}
	
	public static TradingAccountIndex parseTradingAccountIndex(Message message) throws FieldNotFound {
		AssetAccountIndex assetAccountIndex = parseAssetAccountIndex(message);
		
		String tradingAccount = null;
		if (message.isSetField(FixTags.TradingAccount)) {
			tradingAccount = message.getString(FixTags.TradingAccount);
		}
		
		return new TradingAccountIndex(assetAccountIndex, tradingAccount);
	}
	
	public static SecurityTradingIndex parseSecurityTradingIndex(Message message) throws FieldNotFound {
		TradingAccountIndex tradingAccountIndex = parseTradingAccountIndex(message);
		
		String securityAccount = null;
		if (message.isSetField(FixTags.SecurityAccount)) {
			securityAccount = message.getString(FixTags.SecurityAccount);
		}
		
		return new SecurityTradingIndex(tradingAccountIndex, tradingAccountIndex.getTradingAccount(), securityAccount);
	}
	
}
