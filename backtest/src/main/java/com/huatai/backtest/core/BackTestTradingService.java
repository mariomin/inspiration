package com.huatai.backtest.core;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.backtest.Clock;
import com.huatai.common.backtest.DataTypeConvertor;
import com.huatai.common.backtest.IMarketDataCalculateService;
import com.huatai.common.backtest.ITradingService;
import com.huatai.common.backtest.NetValueRecord;
import com.huatai.common.backtest.TimeUtil;
import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.event.IAsyncEventListener;
import com.huatai.common.event.order.ExecutionReportEvent;
import com.huatai.common.fund.TradingFund;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.marketdata.TimeFrame;
import com.huatai.common.portfolio.Position;
import com.huatai.common.portfolio.TradingPosition;
import com.huatai.common.type.AssetAccountType;
import com.huatai.common.type.OrdStatus;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;
import com.huatai.common.util.DecimalUtil;
import com.huatai.common.util.OrderUtil;
import com.huatai.exchange.Exchange;
import com.huatai.exchange.IExchangeListener;
import com.huatai.exchange.common.business.BaseOrder.Side;
import com.huatai.exchange.common.business.Order;
import com.huatai.exchange.common.business.Order.STATUS;

public class BackTestTradingService implements ITradingService {
	private static final Logger log = LoggerFactory.getLogger(BackTestTradingService.class);
	private static final String PBU = "bt_PBU";
	private static final String BROKER = "bt_Broker";
	private boolean placedOrder;
	
	// key:= clOrdId, value = order
	private final Map<String, ExchangeOrder> orders = new HashMap<String, ExchangeOrder>();
	
	// newly filled orders, cannot trade.
	private final Map<String, Set<String>> filledOrders = new HashMap<String, Set<String>>();
	
	// key:= symbol, value = detailed position of each symbol
	private final Map<String, Position> portfolioes = new HashMap<String, Position>();
	
	private final Queue<NetValueRecord> netValues = new ConcurrentLinkedQueue<NetValueRecord>();
	
	private final Map<String, Double> basePortfolioes;
	private final double baseCash;
	private final double commission;
	private final double stampDuty;
	private final Exchange exchange;
	
	private IAsyncEventListener eventListener;
	
	private double totalCash;
	private double availableCash;
	private Clock clock;
	
	/**
	 * 按时间顺序的下单
	 */
	
	private final IExchangeListener<Order> exchangeOrderListener = new IExchangeListener<Order>() {
		
		@Override
		public String getPBU() {
			return PBU;
		}
		
		@Override
		public void onChangeEvent(Order order) {
			String clOrdId = order.getClOrdId();
			ExchangeOrder exchangeOrder = orders.get(clOrdId);
			
			if (orders.containsKey(clOrdId)) {
				exchangeOrder.update(order);
				
				exchangeOrder.setModified(new Timestamp(clock.now().getTime()));
				
				if (order.getStatus() == STATUS.NEW) {
					exchangeOrder.setId(order.getOrderId());
				} else if (order.getStatus() == STATUS.CANCELLED) {
					if (order.getSide() == Side.ASK) {
						unfreezePosition(order);
					} else {
						unfreezeFund(order);
					}
				} else if (order.getStatus() == STATUS.DONE) {
					exchangeOrder.setLastPx(order.getLastPx());
					exchangeOrder.setLastQty(order.getLastQty());
					exchangeOrder.setCumQty(order.getCumQty());
					settleWithCommision(order);
					ExecutionReportEvent event = createExecutionReportEvent(order);
					eventListener.onEvent(event);
				}
			} else {
				log.error("found an unknown order with ClOrdId ={} ", order.getClOrdId());
			}
		}
	};
	
	public BackTestTradingService(Exchange exchange, Map<String, Double> universe, double baseCash, double commission, double stampDuty) {
		basePortfolioes = universe;
		this.exchange = exchange;
		this.baseCash = baseCash;
		this.commission = commission;
		this.stampDuty = stampDuty;
	}
	
	private ExecutionReportEvent createExecutionReportEvent(Order order) {
		String symbol = order.getSymbol();
		OrderSide orderSide = DataTypeConvertor.exchangeOrdSide2OmsOrdSide(order.getSide());
		OrderType orderType = DataTypeConvertor.exchangeOrdType2omsOrdType(order.getType());
		OrdStatus ordStatus = DataTypeConvertor.exchangeOrdStatus2omsOrdStatus(order.getStatus());
		
		SecurityTradingIndex securityTradingIndex = new SecurityTradingIndex(new AssetAccountIndex(), null, null);
		ExchangeOrder exchangeOrder = new ExchangeOrder(securityTradingIndex, symbol, orderSide, order.getQuantity(), order.getPrice(), orderType,
				order.getClOrdId());
		exchangeOrder.setCumQty(order.getCumQty());
		exchangeOrder.setLastPx(order.getLastPx());
		exchangeOrder.setLastQty(order.getLastQty());
		exchangeOrder.setOrdStatus(ordStatus);
		
		TradingFund tradingFund = new TradingFund();
		tradingFund.setTradableCash(availableCash);
		tradingFund.setHoldingCash(totalCash);
		
		TradingPosition tradingPosition = new TradingPosition();
		tradingPosition.setSymbol(symbol);
		tradingPosition.setTradableQty(portfolioes.get(symbol).getTradableQty());
		tradingPosition.setHoldingQty(portfolioes.get(symbol).getHoldingQty());
		
		ExecutionReportEvent event = new ExecutionReportEvent(null, null, exchangeOrder, tradingFund, tradingPosition);
		return event;
	}
	
	// send an order. if successful, return the unique orderId; otherwise,
	// return null.
	@Override
	public String placeOrder(ExchangeOrder exchangeOrder) {
		Order order = OrderUtil.createFromExchangeOrder(exchangeOrder, BROKER, PBU);
		
		if (exchangeOrder.getOrderSide() == OrderSide.Buy) {
			if (!freezeFund(order)) return "fund is not enough";
		} else {
			if (!freezePosition(order)) return "security is not enough";
		}
		
		orders.put(exchangeOrder.getClOrdId(), exchangeOrder);
		
		if (exchange.enterOrder(order, true)) {
			placedOrder = true;
			return order.getClOrdId();
		} else {
			if (exchangeOrder.getOrderSide() == OrderSide.Sell) {
				unfreezeFund(order);
			} else {
				unfreezePosition(order);
			}
			orders.remove(order.getOrderId());
			unfreezeFund(order);
			return null;
		}
	}
	
	// cancel an order with the unique order Id, if successful return true, else
	// return false;
	@Override
	public boolean cancelOrder(String clOrdId) {
		ExchangeOrder exchangeOrder = orders.get(clOrdId);
		Side side = exchangeOrder.getOrderSide() == OrderSide.Buy ? Side.BID : Side.ASK;
		
		if ((null != exchangeOrder)
				&& (null != exchange.cancelOrder(exchangeOrder.getId(), exchangeOrder.getSymbol(), side, exchangeOrder.getClOrdId())))
			return true;
		else return false;
	}
	
	public boolean freezeFund(Order order) {
		double deltaCash = order.getQuantity() * order.getPrice() * (1 + commission);
		if (deltaCash <= availableCash) {
			availableCash -= deltaCash;
			return true;
		} else return false;
	}
	
	public void unfreezeFund(Order order) {
		availableCash += order.getQuantity() * order.getPrice() * (1 + commission);
	}
	
	public boolean freezePosition(Order order) {
		double quantity = order.getQuantity();
		
		Position position = portfolioes.get(order.getSymbol());
		if ((position != null) && (position.getTradableQty() >= quantity)) {
			position.setTradableQty(position.getTradableQty() - quantity);
			return true;
		} else return false;
	}
	
	public void unfreezePosition(Order order) {
		Position position = portfolioes.get(order.getSymbol());
		position.setTradableQty(position.getTradableQty() + order.getQuantity());
	}
	
	public void settleWithCommision(Order order) {
		double quantity = order.getCumQty();
		double price = order.getLastPx();
		String symbol = order.getSymbol();
		
		if (order.getSide() == Side.BID) {
			// update cash
			double freezedCash = quantity * orders.get(order.getClOrdId()).getPrice() * (1 + commission);
			double commissionCost = quantity * price * commission;
			commissionCost = DecimalUtil.greaterThan(commissionCost, 5.0) ? commissionCost : 5.0;
			double actualDeltaCash = (quantity * price) + commissionCost;
			availableCash += (freezedCash - actualDeltaCash);
			totalCash -= actualDeltaCash;
			orders.get(order.getClOrdId()).setCommissionCost(commissionCost);
			
			// update position
			Position position = portfolioes.get(symbol);
			if (position == null) {
				position = new Position(new AssetAccountIndex("BtInvestor", "BtProduct", "BtAsset", AssetAccountType.cash), "BtTrading", symbol);
				portfolioes.put(symbol, position);
			}
			position.setHoldingQty(position.getHoldingQty() + quantity);
			Set<String> clOrderIDs = filledOrders.get(symbol);
			
			if (null == clOrderIDs) {
				clOrderIDs = new HashSet<String>();
				filledOrders.put(symbol, clOrderIDs);
			}
			clOrderIDs.add(order.getClOrdId());
			
		} else {
			double commissionCost = quantity * price * commission;
			commissionCost = DecimalUtil.greaterThan(commissionCost, 5.0) ? commissionCost : 5.0;
			double stampDutyCost = quantity * price * stampDuty;
			double deltaCash = (quantity * price) - commissionCost - stampDutyCost;
			availableCash += deltaCash;
			totalCash += deltaCash;
			Position position = portfolioes.get(order.getSymbol());
			
			orders.get(order.getClOrdId()).setCommissionCost(commissionCost);
			orders.get(order.getClOrdId()).setStampDutyCost(stampDutyCost);
			
			position.setHoldingQty(position.getHoldingQty() - quantity);
		}
	}
	
	// create a new net value record.
	@Override
	public void updatePnL(TimeFrame timeFrame, IMarketDataCalculateService mdCalService) {
		NetValueRecord netValueRecord = new NetValueRecord(clock.now().getTime());
		netValues.add(netValueRecord);
		
		netValueRecord.netValue = 0.0;
		for (Position position : portfolioes.values()) {
			NetValueRecord.NetValueDetail netValueDetail = netValueRecord.new NetValueDetail();
			netValueRecord.netValueDetails.put(position.getSymbol(), netValueDetail);
			netValueDetail.positionQty = position.getHoldingQty();
			List<Double> closePrices = mdCalService.getClosePx(position.getSymbol(), timeFrame, 0, 0);
			netValueDetail.marketPrice = (closePrices != null) ? closePrices.get(0) : 0.0;
			double positionValue = netValueDetail.marketPrice * position.getHoldingQty();
			netValueRecord.netValue += positionValue;
		}
		netValueRecord.netValue += totalCash;
	}
	
	// check whether some positions turn to be negotiable.
	@Override
	public void updateAvailablePosition() {
		for (Position position : portfolioes.values()) {
			if (!DecimalUtil.equal(position.getHoldingQty(), position.getTradableQty())) {
				Set<String> clOrderIds = filledOrders.get(position.getSymbol());
				Set<String> removeSet = new HashSet<String>();
				for (String clOrdId : clOrderIds) {
					ExchangeOrder exchangeOrder = orders.get(clOrdId);
					if (TimeUtil.isDifferentDay(exchangeOrder.getCreated().getTime(), clock.now().getTime())) {
						position.setTradableQty(position.getTradableQty() + exchangeOrder.getQuantity());
						removeSet.add(clOrdId);
					}
				}
				clOrderIds.removeAll(removeSet);
			}
		}
	}
	
	@Override
	public void cancelYesterdayOrders() {
		for (String clOrdId : orders.keySet()) {
			ExchangeOrder exchangeOrder = orders.get(clOrdId);
			if ((exchangeOrder.getOrdStatus() == OrdStatus.NEW)
					&& TimeUtil.isDifferentDay(exchangeOrder.getModified().getTime(), clock.now().getTime())) {
				cancelOrder(clOrdId);
			}
		}
	}
	
	@Override
	public void init() {
		exchange.init();
		totalCash = baseCash;
		availableCash = baseCash;
		exchange.orderListenerKeeper.addExchangeListener(exchangeOrderListener);
		
		for (String symbol : basePortfolioes.keySet()) {
			Position position = new Position(new AssetAccountIndex("BtInvestor", "BtProduct", "BtAsset", AssetAccountType.cash), "BtTrading", symbol);
			position.setTradableQty(basePortfolioes.get(symbol));
			position.setHoldingQty(position.getTradableQty());
			portfolioes.put(symbol, position);
		}
	}
	
	@Override
	public void uninit() {}
	
	public double getCommission() {
		return commission;
	}
	
	public double getStampDuty() {
		return stampDuty;
	}
	
	@Override
	public boolean isPlacedOrder() {
		return placedOrder;
	}
	
	@Override
	public void resetPlacedOrder() {
		placedOrder = false;
	}
	
	@Override
	public double getBaseCash() {
		return baseCash;
	}
	
	@Override
	public double getAvailableCash() {
		return availableCash;
	}
	
	@Override
	public double getTotalCash() {
		return totalCash;
	}
	
	@Override
	public double getHoldingQty(String symbol) {
		Position position = portfolioes.get(symbol);
		if (position == null)
			return 0;
		else return position.getHoldingQty();
	}
	
	@Override
	public double getTradableQty(String symbol) {
		Position position = portfolioes.get(symbol);
		if (position == null) return 0;
		return position.getTradableQty();
	}
	
	@Override
	public Collection<String> getPositionSymbols() {
		return portfolioes.keySet();
	}
	
	@Override
	public void bindClock(Clock clock) {
		this.clock = clock;
	}
	
	@Override
	public Map<String, ExchangeOrder> getExchangeOrders() {
		return orders;
	}
	
	public Queue<NetValueRecord> getNetValues() {
		return netValues;
	}
	
	public Exchange getExchange() {
		return exchange;
	}
	
	public Collection<Position> getPositions() {
		return portfolioes.values();
	}
	
	public void setEventListener(IAsyncEventListener eventListener) {
		this.eventListener = eventListener;
	}
	
}
