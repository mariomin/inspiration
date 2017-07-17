package com.huatai.platform.downstream.adaptor.file;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.downstream.IDownStreamSender;
import com.huatai.common.type.OrdStatus;
import com.huatai.common.type.OrderField;
import com.huatai.common.type.OrderType;
import com.huatai.common.util.FixUtil;
import com.huatai.common.util.IdGenerator;

import quickfix.Message;
import quickfix.field.ClOrdID;
import quickfix.field.HandlInst;
import quickfix.field.OrdType;
import quickfix.field.OrderQty;
import quickfix.field.OrigClOrdID;
import quickfix.field.Price;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.field.TransactTime;
import quickfix.fix50sp2.NewOrderSingle;
import quickfix.fix50sp2.OrderCancelRequest;

public class FileService implements IDownStreamSender {
	private static final Logger log = LoggerFactory.getLogger(FileService.class);
	private final String name;
	private BufferedWriter writer;
	
	public FileService(String name) throws Exception {
		this.name = name;
	}
	
	@Override
	public boolean addReportNoIfAbsent(String reportNo) {
		return true;
	}
	
	@Override
	public void newOrder(ExchangeOrder order) {
		
		String clOrdId = getNextClOrderId();
		order.setClOrdId(clOrdId);
		order.setOrdStatus(OrdStatus.PENDING_NEW);
		
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
			order.putField(OrderField.QuitOMS.name(), System.nanoTime());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			
		}
		
		log.debug("New FIX order, id={}, clOrdId={}", order.getId(), clOrdId);
		sendMessage(nos);
	}
	
	@Override
	public void cancelOrder(ExchangeOrder order) {
		
		OrdStatus ordStatus = order.getOrdStatus();
		order.setOrdStatus(OrdStatus.PENDING_CANCEL);
		String clOrdId = order.getClOrdId();
		try {
			OrderCancelRequest message = new OrderCancelRequest(new ClOrdID(getNextClOrderId()),
					new Side(FixUtil.toFixOrderSide(order.getOrderSide())), new TransactTime());
			message.set(new OrigClOrdID(clOrdId));
			message.set(new Symbol(order.getSymbol()));
			log.debug("Cancel FIX order, id={}, clOrdId={}", order.getId(), clOrdId);
			sendMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			order.setOrdStatus(ordStatus);
		}
	}
	
	private void sendMessage(Message message) {
		try {
			writer.write(message.toString());
			writer.newLine();
			writer.flush();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	private static String getNextClOrderId() {
		return IdGenerator.getInstance().getNextID() + "X";
	}
	
	@Override
	public String getId() {
		return name;
	}
	
	@Override
	public boolean getState() {
		return true;
	}
	
	@Override
	public void start() throws Exception {
		FileUtil.mkdirs(name);
		writer = new BufferedWriter(new FileWriter(name));
	}
	
	@Override
	public void stop() throws Exception {
		writer.close();
	}
	
}
