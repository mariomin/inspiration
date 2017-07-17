package com.huatai.platform.downstream.adaptor.direct;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.downstream.IDownStreamListener;
import com.huatai.common.type.OrdStatus;
import com.huatai.common.util.IdGenerator;
import com.huatai.exchange.Exchange;
import com.huatai.exchange.IExchangeListener;
import com.huatai.exchange.common.business.Order;
import com.huatai.exchange.common.business.Order.STATUS;
import com.huatai.exchange.common.business.Trade;
import com.huatai.exchange.orderbook.IOrderBook;
import com.huatai.platform.downstream.adaptor.AbstractDownStreamService;

public class DirectService extends AbstractDownStreamService {
	private static final String PBU = "DirectService";
	private static final IdGenerator idGenerator = IdGenerator.getInstance();
	private IDownStreamListener downstreamListener;
	private final Exchange exchange;
	private final Map<String, ExchangeOrder> exchangeOrders = new ConcurrentHashMap<String, ExchangeOrder>();
	private final Map<String, Order> simulatorOrders = new ConcurrentHashMap<String, Order>();
	
	public DirectService(Exchange exchange) {
		this.exchange = exchange;
		exchange.orderBookListenerKeeper.addExchangeListener(orderBookListener);
		exchange.orderListenerKeeper.addExchangeListener(orderListener);
		exchange.tradeListenerKeeper.addExchangeListener(tradeListener);
	}
	
	private final IExchangeListener<Order> orderListener = new IExchangeListener<Order>() {
		
		@Override
		public void onChangeEvent(Order order) {}
		
		@Override
		public String getPBU() {
			return PBU;
		}
	};
	
	private final IExchangeListener<Trade> tradeListener = new IExchangeListener<Trade>() {
		
		@Override
		public void onChangeEvent(Trade trade) {
			Order matched;
			ExchangeOrder exchangeOrder;
			
			matched = trade.getBidOrder();
			exchangeOrder = exchangeOrders.get(matched.getPBU());
			if (exchangeOrder != null) {
				updateOrder(exchangeOrder, matched);
			}
			
			matched = trade.getAskOrder();
			exchangeOrder = exchangeOrders.get(matched.getPBU());
			if (exchangeOrder != null) {
				updateOrder(exchangeOrder, matched);
			}
			
		}
		
		@Override
		public String getPBU() {
			return PBU;
		}
	};
	
	private final IExchangeListener<IOrderBook> orderBookListener = new IExchangeListener<IOrderBook>() {
		
		@Override
		public void onChangeEvent(IOrderBook orderBook) {}
		
		@Override
		public String getPBU() {
			return PBU;
		}
	};
	
	private void updateOrder(ExchangeOrder exchangeOrder, Order matched) {
		STATUS ordStatus = matched.getStatus();
		String reportNo = exchangeOrder.getReportNo();
		
		if (ordStatus == STATUS.NEW) {
			downstreamListener.onNewOrderAccept(reportNo);
		} else if (ordStatus == STATUS.REJECTED) {
			downstreamListener.onNewOrderReject(reportNo, "rejected");
		} else if (ordStatus == STATUS.CANCELLED) {
			double canceledQty = matched.getQuantity() - matched.getCumQty();
			downstreamListener.onCancelled(matched.getOrigClOrdId(), canceledQty);
		} else if (ordStatus == STATUS.FILLING) {
			String execID = idGenerator.getNextID();
			downstreamListener.onPartiallyFilled(reportNo, execID, matched.getLastQty(), matched.getLastPx());
		} else if (ordStatus == STATUS.DONE) {
			String execID = idGenerator.getNextID();
			downstreamListener.onFilled(reportNo, execID, matched.getLastQty(), matched.getLastPx());
		} else {
			// TODO 其他状态不处理？
		}
	}
	
	@Override
	public void newOrder(ExchangeOrder order) {
		exchangeOrders.put(order.getId(), order);
		order.setOrdStatus(OrdStatus.PENDING_NEW);
		
		double price = (order.getPrice() != null) ? order.getPrice() : 0d;
		Order simulatorOrder = new Order(order.getSymbol(), DirectUtil.mapOrderType(order.getOrderType()), DirectUtil.mapSide(order.getOrderSide()),
				order.getQuantity(), price, "DirectService", order.getId());
		boolean placeOrder = exchange.enterOrder(simulatorOrder, true);
		if (placeOrder) {
			simulatorOrders.put(order.getId(), simulatorOrder);
			downstreamListener.onNewOrderAccept(order.getReportNo());
		} else {
			order.setOrdStatus(OrdStatus.REJECTED);
			downstreamListener.onNewOrderReject(order.getReportNo(), "rejected");
		}
	}
	
	@Override
	public void cancelOrder(ExchangeOrder order) {
		ExchangeOrder exchangeOrder = exchangeOrders.get(order.getId());
		Order simulatorOrder = simulatorOrders.get(order.getId());
		if ((null == exchangeOrder) || (null == simulatorOrder)) {
			downstreamListener.onCancelOrderReject(order.getOrigClOrdId(), "Can't find exchange order in downstream connection");
			return;
		}
		
		exchange.cancelOrder(simulatorOrder.getOrderId(), order.getSymbol(), simulatorOrder.getSide(), order.getId());
	}
	
	@Override
	public boolean addReportNoIfAbsent(String reportNo) {
		return true;
	}
	
	@Override
	public String getId() {
		return PBU;
	}
	
	@Override
	public boolean getState() {
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
