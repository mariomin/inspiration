package com.huatai.common.type;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum ExecType {
	// '0' New
	// '3' Done for day
	// '4' Canceled
	// '5' Replace
	// '6' Pending Cancel (e.g. result of Order Cancel Request (F) )
	// '7' Stopped
	// '8' Rejected
	// '9' Suspended
	// 'A' Pending New
	// 'B' Calculated
	// 'C' Expired
	// 'D' Restated ( ExecutionRpt (8) sent unsolicited by sellside, with
	// ExecRestatementReason (378) set)
	// 'E' Pending Replace (e.g. result of Order Cancel/Replace Request (G) )
	// 'F' Trade (partial fill or fill)
	
	NEW('0'), DONE_FOR_DAY('3'), CANCELED('4'), REPLACE('5'), PENDING_CANCEL('6'), STOPPED('7'), REJECTED('8'), SUSPENDED('9'), PENDING_NEW(
			'A'), CALCULATED('B'), EXPIRED('C'), RESTATED('D'), PENDING_REPLACE('E'), TRADE('F');
	
	private static final Set<ExecType> pendingSet = Collections.unmodifiableSet(EnumSet.of(PENDING_NEW, PENDING_CANCEL, PENDING_REPLACE));
	private static final Set<ExecType> completeSet = Collections.unmodifiableSet(EnumSet.of(CALCULATED, DONE_FOR_DAY, CANCELED, EXPIRED, REJECTED));
	
	private static Map<Character, ExecType> map = new HashMap<Character, ExecType>();
	private char value;
	
	static {
		for (ExecType type : ExecType.values()) {
			map.put(type.value(), type);
		}
	}
	
	ExecType(char value) {
		this.value = value;
	}
	
	public char value() {
		return value;
	}
	
	public static ExecType getType(char c) {
		return map.get(c);
	}
	
	public boolean isPending() {
		return pendingSet.contains(this);
	}
	
	public static ExecType pendingToReady(OrdStatus status) {
		if (status.equals(OrdStatus.PENDING_NEW))
			return NEW;
		else if (status.equals(OrdStatus.PENDING_REPLACE))
			return REPLACE;
		else if (status.equals(OrdStatus.PENDING_CANCEL))
			return CANCELED;
		else return RESTATED;
	}
	
	public boolean isCompleted() {
		return completeSet.contains(this);
	}
	
}
