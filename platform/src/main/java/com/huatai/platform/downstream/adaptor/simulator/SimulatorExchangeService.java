package com.huatai.platform.downstream.adaptor.simulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.downstream.IDownStreamListener;
import com.huatai.common.downstream.IDownStreamSender;
import com.huatai.common.downstream.fix.IFixReceiver;
import com.huatai.common.fix.FixConvertionException;
import com.huatai.common.type.OrderType;
import com.huatai.common.util.FixUtil;

import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.StringField;
import quickfix.field.ClOrdID;
import quickfix.field.CumQty;
import quickfix.field.ExecID;
import quickfix.field.HandlInst;
import quickfix.field.LastPx;
import quickfix.field.LastQty;
import quickfix.field.MsgType;
import quickfix.field.OrdType;
import quickfix.field.OrderQty;
import quickfix.field.OrigClOrdID;
import quickfix.field.Price;
import quickfix.field.RejectText;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.field.TransactTime;
import quickfix.fix50sp2.ExecutionReport;
import quickfix.fix50sp2.NewOrderSingle;
import quickfix.fix50sp2.OrderCancelReject;
import quickfix.fix50sp2.OrderCancelRequest;

public class SimulatorExchangeService implements IDownStreamSender, IFixReceiver {
	private static final Logger log = LoggerFactory.getLogger(SimulatorExchangeService.class);
	
	private final SessionID sessionID;
	//private final String id = "simulatorExchange";
	
	private IDownStreamListener downstreamListener;
	
	public SimulatorExchangeService(SessionID sessionID) {
		this.sessionID = sessionID;
	}
	
	@Override
	public void fromApp(Message message, SessionID sessionID) throws FieldNotFound {
		try {
			// 暂停10ms，避免测试时经常出现异步导致的乱序问题
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		StringField msgType = message.getHeader().getField(new MsgType());
		
		if (msgType.valueEquals(ExecutionReport.MSGTYPE)) {
			processExecutionReport((ExecutionReport) message);
		} else if (msgType.valueEquals(OrderCancelReject.MSGTYPE)) {
			processCancelReject((OrderCancelReject) message);
		} else {
			log.warn("Message cannot be processed by downstream, msgType: {}, {}", msgType, message);
		}
	}
	
	private void processExecutionReport(ExecutionReport executionReport) throws FieldNotFound {
		log.info("Recevie executionReport:{}", executionReport);
		char ordStatus = executionReport.get(new quickfix.field.OrdStatus()).getValue();
		String clOrdId = executionReport.getField(new ClOrdID()).getValue();
		
		if (ordStatus == quickfix.field.OrdStatus.NEW) {
			downstreamListener.onNewOrderAccept(clOrdId);
		} else if (ordStatus == quickfix.field.OrdStatus.REJECTED) {
			String rejectText = executionReport.getString(RejectText.FIELD);
			downstreamListener.onNewOrderReject(clOrdId, rejectText);
		} else if (ordStatus == quickfix.field.OrdStatus.CANCELED) {
			long cumQty = (long) executionReport.get(new CumQty()).getValue();
			long orderQty = (long) executionReport.get(new OrderQty()).getValue();
			long cancelQty = orderQty - cumQty;
			String origClOrdId = executionReport.get(new OrigClOrdID()).getValue();
			downstreamListener.onCancelled(origClOrdId, cancelQty);
		} else if (ordStatus == quickfix.field.OrdStatus.FILLED) {
			// 全部成交
			long lastQty = (long) executionReport.get(new LastQty()).getValue();
			double price = executionReport.get(new LastPx()).getValue();
			String execID = executionReport.get(new ExecID()).getValue();
			downstreamListener.onFilled(clOrdId, execID, lastQty, price);
		} else if (ordStatus == quickfix.field.OrdStatus.PARTIALLY_FILLED) {
			// 部分成交
			long lastQty = (long) executionReport.get(new LastQty()).getValue();
			double price = executionReport.get(new LastPx()).getValue();
			String execID = executionReport.get(new ExecID()).getValue();
			downstreamListener.onPartiallyFilled(clOrdId, execID, lastQty, price);
		} else {
			// TODO 其他状态不处理？
		}
		
	}
	
	private void processCancelReject(OrderCancelReject message) throws FieldNotFound {
		String origClOrdId = message.get(new OrigClOrdID()).getValue();
		String reason = message.isSetField(quickfix.field.Text.FIELD) ? message.getString(quickfix.field.Text.FIELD) : null;
		downstreamListener.onCancelOrderReject(origClOrdId, reason);
	}
	
	@Override
	public void onLogon(SessionID arg0) {}
	
	@Override
	public void onLogout(SessionID arg0) {}
	
	@Override
	public String getId() {
		return sessionID.toString();
	}
	
	@Override
	public boolean getState() {
		return true;
	}
	
	@Override
	public void toApp(Message arg0, SessionID arg1) {}
	
	@Override
	public void onCreate(SessionID arg0) {}
	
	@Override
	public void fromAdmin(Message message, SessionID sessionID) {}
	
	@Override
	public void toAdmin(Message arg0, SessionID arg1) {}
	
	@Override
	public void newOrder(ExchangeOrder order) {
		String clOrdId = order.getReportNo();
		
		NewOrderSingle nos = null;
		try {
			nos = new NewOrderSingle(new ClOrdID(clOrdId), new Side(FixUtil.toFixOrderSide(order.getOrderSide())), new TransactTime(),
					new OrdType(FixUtil.toFixExchangeOrderType(order.getOrderType())));
			nos.set(new HandlInst('1'));
			nos.set(new Symbol(order.getSymbol()));
			if (order.getOrderType() != OrderType.MARKET) {
				nos.set(new Price(order.getPrice()));
			}
			nos.set(new OrderQty(order.getQuantity()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		log.debug("New FIX order, id={}, clOrdId={}", order.getId(), clOrdId);
		
		sendMessage(nos);
		
	}
	
	private void sendMessage(Message message) {
		try {
			boolean result = Session.sendToTarget(message, sessionID);
			String remoteAddress = Session.lookupSession(sessionID).getRemoteAddress();
			
			if (result) {
				log.info("Send newOrder success,sessionID={}, remoteAddress ={}, message={}", sessionID, remoteAddress, message);
			} else {
				log.error("Send newOrder failed,sessionID={}, remoteAddress ={}, message={}", sessionID, remoteAddress, message);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	@Override
	public void cancelOrder(ExchangeOrder order) {
		
		try {
			OrderCancelRequest orderCancelRequest = new OrderCancelRequest(new ClOrdID(order.getCancelReportNo()),
					new Side(FixUtil.toFixOrderSide(order.getOrderSide())), new TransactTime());
			
			orderCancelRequest.set(new Symbol(order.getSymbol()));
			orderCancelRequest.set(new OrigClOrdID(order.getReportNo()));
			Session.sendToTarget(orderCancelRequest, sessionID);
		} catch (FixConvertionException | SessionNotFound e) {
			String errorMsg = "Send cancel order[" + order + "] fail";
			log.error(errorMsg, e);
		}
		
	}
	
	@Override
	public boolean addReportNoIfAbsent(String reportNo) {
		return true;
	}
	
	@Override
	public void start() {}
	
	@Override
	public void stop() {}
	
	@Override
	public void setDownstreamListener(IDownStreamListener downstreamListener) {
		this.downstreamListener = downstreamListener;
	}
	
}
