package com.huatai.platform.downstream;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Ignore;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.downstream.IDownStreamListener;
import com.huatai.common.type.OrdStatus;
import com.huatai.common.type.OrderField;
import com.huatai.common.util.DecimalUtil;

@Ignore
public class TestDownStreamListener implements IDownStreamListener {
	protected final LinkedBlockingQueue<ExchangeOrder> queue = new LinkedBlockingQueue<ExchangeOrder>();
	// reportNo:order
	private final Map<String, ExchangeOrder> orders = new ConcurrentHashMap<String, ExchangeOrder>();
	
	public void addExchangeOrder(ExchangeOrder order) {
		orders.put(order.getReportNo(), order);
	}
	
	public ExchangeOrder getExchangeOrderByReportNo(String reportNo) {
		return orders.get(reportNo);
	}
	
	@Override
	public void onNewOrderAccept(String reportNo) {
		ExchangeOrder order = getExchangeOrderByReportNo(reportNo);
		order.setOrdStatus(OrdStatus.NEW);
		queue.offer(order.clone());
	}
	
	@Override
	public void onNewOrderReject(String reportNo, String reason) {
		ExchangeOrder order = getExchangeOrderByReportNo(reportNo);
		order.setOrdStatus(OrdStatus.REJECTED);
		order.putField(OrderField.Reason.name(), reason);
		queue.offer(order.clone());
	}
	
	@Override
	public void onCancelOrderReject(String reportNo, String reason) {
		ExchangeOrder order = getExchangeOrderByReportNo(reportNo);
		order.setOrdStatus(OrdStatus.REJECTED);
		order.putField(OrderField.Reason.name(), reason);
		queue.offer(order.clone());
	}
	
	@Override
	public void onCancelled(String reportNo, double cancelQty) {
		ExchangeOrder order = getExchangeOrderByReportNo(reportNo);
		order.setOrdStatus(OrdStatus.CANCELED);
		order.setCumQty(cancelQty);
		queue.offer(order.clone());
	}
	
	@Override
	public void onFilled(String reportNo, String execID, double lastQty, double price) {
		ExchangeOrder order = getExchangeOrderByReportNo(reportNo);
		order.setCumQty(order.getCumQty() + lastQty);
		order.setLastQty(lastQty);
		order.setLastPx(price);
		order.setOrdStatus(OrdStatus.FILLED);
		queue.offer(order.clone());
	}
	
	@Override
	public void onPartiallyFilled(String reportNo, String execID, double lastQty, double price) {
		ExchangeOrder order = getExchangeOrderByReportNo(reportNo);
		if (null != order) {
			order.setCumQty(order.getCumQty() + lastQty);
			double leaveQty = order.getQuantity() - order.getCumQty();
			order.setLastQty(lastQty);
			order.setLastPx(price);
			
			OrdStatus ordStatus = DecimalUtil.greaterThan(leaveQty, 0) ? OrdStatus.PARTIALLY_FILLED : OrdStatus.FILLED;
			order.setOrdStatus(ordStatus);
			queue.offer(order.clone());
		}
	}
	
	@Override
	public void onDuplicateCancelOrder(String cancelReportNo) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onDuplicateNewOrder(String reportNo) {
		// TODO Auto-generated method stub
		
	}
	
	public ExchangeOrder takeOrder() throws InterruptedException {
		return queue.take();
	}
	
}
