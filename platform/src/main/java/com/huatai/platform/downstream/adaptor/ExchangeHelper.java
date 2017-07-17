package com.huatai.platform.downstream.adaptor;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.downstream.DownStreamException;
import com.huatai.common.type.OrderField;

public class ExchangeHelper {
	private static final Logger log = LoggerFactory.getLogger(ExchangeHelper.class);
	private static final DecimalFormat entrustNoFormatter = new DecimalFormat("000000");
	private Map<String, AtomicInteger> branchCode2EntrustNo = new ConcurrentHashMap<String, AtomicInteger>();
	private static int MIN_BEGIN_ORDER_ID_IN_BRANCH = 500000;
	private static int MAX_END_ORDER_ID_IN_BRANCH = 999999;
	
	private static int increase = 1000;
	
	public void allocateInternalOrderId(ExchangeOrder order) throws DownStreamException {
		String branchCode = order.getBranchCode();
		AtomicInteger atomicInt = branchCode2EntrustNo.get(branchCode);
		
		if (atomicInt == null) {
			atomicInt = new AtomicInteger(MIN_BEGIN_ORDER_ID_IN_BRANCH);
			AtomicInteger oldInt = branchCode2EntrustNo.putIfAbsent(branchCode, atomicInt);
			if (oldInt != null) {
				atomicInt = oldInt;
			}
		} else if (atomicInt.get() >= MAX_END_ORDER_ID_IN_BRANCH) {
			int MAX_ORDER_QUANTITY = MAX_END_ORDER_ID_IN_BRANCH - MIN_BEGIN_ORDER_ID_IN_BRANCH;
			log.error("Too many order for this branch[{}]. Order quantity is {}.", branchCode, MAX_ORDER_QUANTITY);
			throw new DownStreamException("Too many order for this branch[" + branchCode + "].");
		}
		int entrustNo = atomicInt.incrementAndGet();
		
		order.putField(OrderField.EntrustNo.name(), entrustNo);
		order.putField(OrderField.ExchangeOrdId.name(), branchCode + entrustNoFormatter.format(entrustNo));
	}
	
	public static int getMAX_END_ORDER_ID_IN_BRANCH() {
		return MAX_END_ORDER_ID_IN_BRANCH;
	}
	
	public static void setMAX_END_ORDER_ID_IN_BRANCH(int MAX_END_ORDER_ID_IN_BRANCH) {
		ExchangeHelper.MAX_END_ORDER_ID_IN_BRANCH = MAX_END_ORDER_ID_IN_BRANCH;
	}
	
	public static int getMIN_BEGIN_ORDER_ID_IN_BRANCH() {
		return MIN_BEGIN_ORDER_ID_IN_BRANCH;
	}
	
	public static void setMIN_BEGIN_ORDER_ID_IN_BRANCH(int begin) {
		ExchangeHelper.MIN_BEGIN_ORDER_ID_IN_BRANCH = begin;
	}
	
	public static int getIncrease() {
		return increase;
	}
	
	public static void setIncrease(int increase) {
		ExchangeHelper.increase = increase;
	}
	
	public void setBranchCode2EntrustNo(Map<String, AtomicInteger> branchCode2EntrustNo) {
		this.branchCode2EntrustNo = branchCode2EntrustNo;
	}
	
	public void updateEntrustNoIndex(ExchangeOrder exchangeOrder) {
		String exchangeOrderId = exchangeOrder.getField(String.class, OrderField.ExchangeOrdId.name());
		String branchCode = exchangeOrderId.substring(0, exchangeOrderId.length() - 6);
		int entrustNo = Integer.parseInt(exchangeOrderId.substring(exchangeOrderId.length() - 6));
		AtomicInteger old = branchCode2EntrustNo.get(branchCode);
		if ((old == null)) {
			branchCode2EntrustNo.put(branchCode, new AtomicInteger(entrustNo));
		} else if (old.get() < entrustNo) {
			branchCode2EntrustNo.get(branchCode).set(entrustNo);
		}
	}
	
	public void increaseAll() {
		MIN_BEGIN_ORDER_ID_IN_BRANCH += increase;
		for (Entry<String, AtomicInteger> entry : branchCode2EntrustNo.entrySet()) {
			entry.getValue().set(entry.getValue().intValue() + increase);
		}
	}
}
