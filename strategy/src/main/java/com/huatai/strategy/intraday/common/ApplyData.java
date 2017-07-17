package com.huatai.strategy.intraday.common;

import java.util.ArrayList;
import java.util.List;

import com.huatai.common.type.QtyPrice;
import com.huatai.common.util.DecimalUtil;

public class ApplyData {
	public final double price;
	public final double quantity;
	public final Side side;
	
	public enum Side {
		Bid, Ask, CancelBid, CancelAsk
	}
	
	public ApplyData(double price, double quantity, Side side) {
		this.price = price;
		this.quantity = quantity;
		this.side = side;
	}
	
	public static ApplyData cancelApply(boolean buy, QtyPrice apply) {
		return new ApplyData(apply.getPrice(), apply.getQuantity(), buy ? Side.CancelBid : Side.CancelAsk);
	}
	
	public static ApplyData tradeApply(boolean buy, QtyPrice apply) {
		return new ApplyData(apply.getPrice(), apply.getQuantity(), buy ? Side.Ask : Side.Bid);
	}
	
	public static ApplyData untradeApply(boolean buy, double price, double preQuantity, double nowQuantity) {
		if (DecimalUtil.equal(preQuantity, nowQuantity)) return null;
		
		if (buy) {
			if (DecimalUtil.lessThan(preQuantity, nowQuantity))
				return new ApplyData(price, nowQuantity - preQuantity, Side.Bid);
			else return new ApplyData(price, preQuantity - nowQuantity, Side.CancelAsk);
		} else {
			if (DecimalUtil.lessThan(preQuantity, nowQuantity))
				return new ApplyData(price, nowQuantity - preQuantity, Side.Ask);
			else return new ApplyData(price, preQuantity - nowQuantity, Side.CancelAsk);
		}
	}
	
	public static List<ApplyData> calUntradedApplys(boolean buy, List<QtyPrice> preApplys, List<QtyPrice> nowApplys) {
		List<ApplyData> applys = new ArrayList<ApplyData>();
		for (int i = 0; i < nowApplys.size(); i++) {
			double price = nowApplys.get(i).getPrice();
			boolean existPrePrice = false;
			for (int j = 0; j < preApplys.size(); j++) {
				if (DecimalUtil.equal(price, preApplys.get(j).getPrice())) {
					ApplyData apply = untradeApply(buy, price, preApplys.get(j).getQuantity(), nowApplys.get(i).getQuantity());
					if (apply != null) {
						applys.add(apply);
					}
					preApplys.remove(j);
					existPrePrice = true;
					break;
				}
			}
			if (!existPrePrice) {
				applys.add(calApplyData(buy, nowApplys.get(i)));
			}
		}
		
		for (int k = 0; k < preApplys.size(); k++) {
			applys.add(cancelApply(buy, preApplys.get(k)));
		}
		return applys;
	}
	
	public static List<ApplyData> calTradedApplys(boolean buy, List<QtyPrice> preApplys) {
		List<ApplyData> applys = new ArrayList<ApplyData>();
		for (int i = 0; i < preApplys.size(); i++) {
			applys.add(tradeApply(buy, preApplys.get(i)));
		}
		return applys;
	}
	
	public static int getIndexOfApply(List<QtyPrice> applys, double price) {
		double start = applys.get(0).getPrice() - price;
		for (int i = 0; i < applys.size(); i++) {
			if (DecimalUtil.equalLessThan((applys.get(i).getPrice() - price) * start, 0)) return i;
		}
		return -1;
	}
	
	public static boolean containsPrice(List<QtyPrice> applys, double price) {
		for (QtyPrice apply : applys) {
			if (DecimalUtil.equal(apply.getPrice(), price)) return true;
		}
		return false;
	}
	
	public static ApplyData calApplyData(boolean isBuy, QtyPrice apply) {
		return new ApplyData(apply.getPrice(), apply.getQuantity(), isBuy ? Side.CancelBid : Side.CancelAsk);
	}
	
	public static List<ApplyData> calApplyDatas(boolean buy, List<QtyPrice> qps) {
		List<ApplyData> applys = new ArrayList<ApplyData>();
		Side side = buy ? Side.Bid : Side.Ask;
		for (QtyPrice qp : qps) {
			applys.add(new ApplyData(qp.getPrice(), qp.getQuantity(), side));
		}
		return applys;
	}
	
	public static List<ApplyData> calAccAmountBid(List<QtyPrice> preBids, List<QtyPrice> nowBids) {
		List<ApplyData> results = new ArrayList<ApplyData>();
		List<QtyPrice> inBoundList = new ArrayList<QtyPrice>();
		double upBound = Double.NEGATIVE_INFINITY, lowBound = Double.POSITIVE_INFINITY;
		double bid1Price = nowBids.get(0).price;
		double bid1Qty = nowBids.get(0).quantity;
		boolean inboundContainBid1 = false;
		boolean bid1Processed = false;
		
		for (QtyPrice qtyPrice : preBids) {
			if (!DecimalUtil.isZero(qtyPrice.price)) {
				upBound = Math.max(upBound, qtyPrice.price);
				lowBound = Math.min(lowBound, qtyPrice.price);
			}
		}
		
		// preBids跌停
		if (upBound == Double.NEGATIVE_INFINITY) {
			for (QtyPrice qtyPrice : nowBids) {
				if (!DecimalUtil.isZero(qtyPrice.price)) {
					results.add(new ApplyData(qtyPrice.price, qtyPrice.quantity, Side.Bid));
				}
			}
		} else {
			for (QtyPrice qtyPrice : nowBids) {
				if (!DecimalUtil.isZero(qtyPrice.price)) {
					if (DecimalUtil.greaterThan(qtyPrice.price, upBound)) {
						results.add(new ApplyData(qtyPrice.price, qtyPrice.quantity, Side.Bid));
					} else if (DecimalUtil.equalGreaterThan(qtyPrice.price, lowBound)) {
						inBoundList.add(qtyPrice);
					}
				}
			}
		}
		// 检查当前切片重叠区域inbound
		for (QtyPrice qtyPrice : inBoundList) {
			QtyPrice preQtyPrice = search(preBids, qtyPrice.price);
			if (DecimalUtil.equal(qtyPrice.price, bid1Price)) {
				inboundContainBid1 = true;
				continue;
			} else if (preQtyPrice == null) {
				results.add(new ApplyData(qtyPrice.price, qtyPrice.quantity, Side.Bid));
			} else {
				if (DecimalUtil.greaterThan(preQtyPrice.quantity, qtyPrice.quantity)) {
					results.add(new ApplyData(qtyPrice.price, preQtyPrice.quantity - qtyPrice.quantity, Side.CancelBid));
				} else if (DecimalUtil.lessThan(preQtyPrice.quantity, qtyPrice.quantity)) {
					results.add(new ApplyData(qtyPrice.price, qtyPrice.quantity - preQtyPrice.quantity, Side.Bid));
				}
			}
		}
		
		// 检查前一个切片的重叠区域inbound
		if (!inBoundList.isEmpty()) {
			upBound = Double.NEGATIVE_INFINITY;
			lowBound = Double.POSITIVE_INFINITY;
			for (QtyPrice qtyPrice : nowBids) {
				if (!DecimalUtil.isZero(qtyPrice.price)) {
					upBound = Math.max(upBound, qtyPrice.price);
					lowBound = Math.min(lowBound, qtyPrice.price);
				}
			}
			double sellQty = 0d;
			double sellPrice = 0d;
			
			for (QtyPrice preQtyPrice : preBids) {
				if (DecimalUtil.greaterThan(preQtyPrice.price, bid1Price)) {
					sellQty += preQtyPrice.quantity;
					sellPrice = preQtyPrice.price;
				} else if (DecimalUtil.equal(preQtyPrice.price, bid1Price)) {
					bid1Processed = true;
					if (DecimalUtil.greaterThan(preQtyPrice.quantity, bid1Qty)) {
						sellQty += preQtyPrice.quantity - bid1Qty;
						sellPrice = preQtyPrice.price;
					} else if (DecimalUtil.lessThan(preQtyPrice.quantity, bid1Qty)) {
						results.add(new ApplyData(preQtyPrice.price, bid1Qty - preQtyPrice.quantity, Side.Bid));
					}
				} else if (DecimalUtil.equalGreaterThan(preQtyPrice.price, lowBound)) {
					QtyPrice qtyPrice = search(nowBids, preQtyPrice.price);
					if (qtyPrice == null) {
						results.add(new ApplyData(preQtyPrice.price, preQtyPrice.quantity, Side.CancelBid));
					}
				}
			}
			
			if (inboundContainBid1 && !bid1Processed) {
				results.add(new ApplyData(bid1Price, bid1Qty, Side.Bid));
			}
			
			if (!DecimalUtil.isZero(sellQty)) {
				results.add(new ApplyData(sellPrice, sellQty, Side.Ask));
			}
		} else if (DecimalUtil.greaterThan(lowBound, bid1Price) || DecimalUtil.isZero(bid1Price)) {
			double sellQty = 0d;
			double sellPrice = 0d;
			for (QtyPrice preQtyPrice : preBids) {
				sellQty += preQtyPrice.quantity;
				sellPrice = Math.min(sellPrice, preQtyPrice.price);
			}
			if (!DecimalUtil.isZero(sellQty)) {
				results.add(new ApplyData(sellPrice, sellQty, Side.Bid));
			}
		}
		
		return results;
	}
	
	public static List<ApplyData> calAccAmountAsk(List<QtyPrice> preAsks, List<QtyPrice> nowAsks) {
		List<ApplyData> results = new ArrayList<ApplyData>();
		List<QtyPrice> inBoundList = new ArrayList<QtyPrice>();
		double upBound = Double.NEGATIVE_INFINITY, lowBound = Double.POSITIVE_INFINITY;
		double ask1Price = nowAsks.get(0).price;
		double ask1Qty = nowAsks.get(0).quantity;
		
		boolean inboundContainAsk1 = false;
		boolean ask1Processed = false;
		
		for (QtyPrice qtyPrice : preAsks) {
			if (!DecimalUtil.isZero(qtyPrice.price)) {
				upBound = Math.max(upBound, qtyPrice.price);
				lowBound = Math.min(lowBound, qtyPrice.price);
			}
		}
		
		// preAsks涨停
		if (upBound == Double.NEGATIVE_INFINITY) {
			for (QtyPrice qtyPrice : nowAsks) {
				if (!DecimalUtil.isZero(qtyPrice.price)) {
					results.add(new ApplyData(qtyPrice.price, qtyPrice.quantity, Side.Ask));
				}
			}
		} else {
			for (QtyPrice qtyPrice : nowAsks) {
				if (!DecimalUtil.isZero(qtyPrice.price)) {
					if (DecimalUtil.lessThan(qtyPrice.price, lowBound)) {
						results.add(new ApplyData(qtyPrice.price, qtyPrice.quantity, Side.Ask));
					} else if (DecimalUtil.equalLessThan(qtyPrice.price, upBound)) {
						inBoundList.add(qtyPrice);
					}
				}
			}
		}
		// 检查新的切片的重叠区域
		for (QtyPrice qtyPrice : inBoundList) {
			QtyPrice preQtyPrice = search(preAsks, qtyPrice.price);
			if (DecimalUtil.equal(qtyPrice.price, ask1Price)) {
				inboundContainAsk1 = true;
				continue;
			} else if (preQtyPrice == null) {
				results.add(new ApplyData(qtyPrice.price, qtyPrice.quantity, Side.Ask));
			} else {
				if (DecimalUtil.greaterThan(preQtyPrice.quantity, qtyPrice.quantity)) {
					results.add(new ApplyData(qtyPrice.price, preQtyPrice.quantity - qtyPrice.quantity, Side.CancelAsk));
				} else if (DecimalUtil.lessThan(preQtyPrice.quantity, qtyPrice.quantity)) {
					results.add(new ApplyData(qtyPrice.price, qtyPrice.quantity - preQtyPrice.quantity, Side.Ask));
				}
			}
		}
		// 检查前一个切片的重叠区域inbound
		if (!inBoundList.isEmpty()) {
			upBound = Double.NEGATIVE_INFINITY;
			lowBound = Double.POSITIVE_INFINITY;
			for (QtyPrice qtyPrice : nowAsks) {
				if (!DecimalUtil.isZero(qtyPrice.price)) {
					upBound = Math.max(upBound, qtyPrice.price);
					lowBound = Math.min(lowBound, qtyPrice.price);
				}
			}
			double buyQty = 0d;
			double buyPrice = 0d;
			
			for (QtyPrice preQtyPrice : preAsks) {
				if (DecimalUtil.lessThan(preQtyPrice.price, ask1Price)) {
					buyQty += preQtyPrice.quantity;
					buyPrice = preQtyPrice.price;
				} else if (DecimalUtil.equal(preQtyPrice.price, ask1Price)) {
					ask1Processed = true;
					if (DecimalUtil.greaterThan(preQtyPrice.quantity, ask1Qty)) {
						buyQty += preQtyPrice.quantity - ask1Qty;
						buyPrice = preQtyPrice.price;
					} else if (DecimalUtil.lessThan(preQtyPrice.quantity, ask1Qty)) {
						results.add(new ApplyData(preQtyPrice.price, ask1Qty - preQtyPrice.quantity, Side.Ask));
					}
				} else if (DecimalUtil.equalLessThan(preQtyPrice.price, upBound)) {
					QtyPrice qtyPrice = search(nowAsks, preQtyPrice.price);
					if (qtyPrice == null) {
						results.add(new ApplyData(preQtyPrice.price, preQtyPrice.quantity, Side.CancelAsk));
					}
				}
			}
			
			if (inboundContainAsk1 && !ask1Processed) {
				results.add(new ApplyData(ask1Price, ask1Qty, Side.Ask));
			}
			
			if (!DecimalUtil.isZero(buyQty)) {
				results.add(new ApplyData(buyPrice, buyQty, Side.Bid));
			}
		} else if (DecimalUtil.lessThan(upBound, ask1Price) || DecimalUtil.isZero(ask1Price)) {
			double buyQty = 0d;
			double buyPrice = 0d;
			for (QtyPrice preQtyPrice : preAsks) {
				buyQty += preQtyPrice.quantity;
				buyPrice = Math.max(buyPrice, preQtyPrice.price);
			}
			if (!DecimalUtil.isZero(buyQty)) {
				results.add(new ApplyData(buyPrice, buyQty, Side.Bid));
			}
		}
		
		return results;
	}
	
	private static QtyPrice search(List<QtyPrice> asks, double price) {
		for (QtyPrice qtyPrice : asks) {
			if (DecimalUtil.equal(qtyPrice.price, price)) return qtyPrice;
		}
		return null;
	}
	
}
