package com.huatai.common.business;

import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;
import com.huatai.common.util.IdGenerator;

public class AlgoOrder extends Order {
	private static final long serialVersionUID = 1L;
	protected String algo;
	
	@Override
	protected String generateId() {
		return IdGenerator.getInstance().getNextID() + "A";
	}
	
	public AlgoOrder() {
		super();
	}
	
	public AlgoOrder(SecurityTradingIndex securityTradingIndex, String symbol, OrderSide side, Double quantity, Double price, OrderType orderType,
			String clOrdId) {
		super(securityTradingIndex, symbol, side, quantity, price);
		this.orderType = orderType;
		this.clOrdId = clOrdId;
	}
	
	public ExchangeOrder createExchangeOrder(Double quantity, Double price, OrderType orderType) {
		ExchangeOrder order = new ExchangeOrder(extractSecurityTradingIndex(), getSymbol(), getOrderSide(), quantity, price, orderType, getClOrdId());
		order.setAlgoOrderId(getId());
		order.setCurrency(currency);
		order.setBranchNo(branchNo);
		order.setBranchCode(branchCode);
		order.setPbu(pbu);
		order.setTimeInForce(timeInForce);
		order.setMaxPriceLevels(maxPriceLevels);
		order.setMinQty(minQty);
		order.setOrderType(orderType);
		order.fields = fields.clone();
		return order;
	}
	
	public void setAlgo(String algo) {
		this.algo = algo;
	}
	
	public String getAlgo() {
		return algo;
	}
	
}
