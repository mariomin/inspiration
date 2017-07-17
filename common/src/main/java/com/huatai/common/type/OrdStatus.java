package com.huatai.common.type;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum OrdStatus {
	NEW('0'), PARTIALLY_FILLED('1'), FILLED('2'), DONE_FOR_DAY('3'), CANCELED('4'), REPLACED('5'), PENDING_CANCEL('6'), STOPPED('7'), REJECTED(
			'8'), SUSPENDED('9'), PENDING_NEW('A'), CALCULATED('B'), EXPIRED('C'), ACCEPTED_FOR_BIDDING('D'), PENDING_REPLACE('E'), WORKING('F');
	
	private static final Set<OrdStatus> openSet = Collections.unmodifiableSet(
			EnumSet.of(NEW, REPLACED, PARTIALLY_FILLED, SUSPENDED, ACCEPTED_FOR_BIDDING, STOPPED, PENDING_NEW, PENDING_CANCEL, PENDING_REPLACE));
	
	private static final Set<OrdStatus> readySet = Collections
			.unmodifiableSet(EnumSet.of(NEW, REPLACED, PARTIALLY_FILLED, SUSPENDED, ACCEPTED_FOR_BIDDING, STOPPED));
	
	private static final Set<OrdStatus> pendingSet = Collections.unmodifiableSet(EnumSet.of(PENDING_NEW, PENDING_CANCEL, PENDING_REPLACE));
	
	private static final Set<OrdStatus> completeSet = Collections
			.unmodifiableSet(EnumSet.of(CALCULATED, FILLED, DONE_FOR_DAY, CANCELED, EXPIRED, REJECTED, STOPPED));
	
	private static final Set<OrdStatus> cancelableSet = Collections.unmodifiableSet(EnumSet.of(NEW, PENDING_NEW, PARTIALLY_FILLED));
	
	private static Map<Character, OrdStatus> map = new HashMap<Character, OrdStatus>();
	private char value;
	
	static {
		for (OrdStatus status : OrdStatus.values()) {
			map.put(status.value(), status);
		}
	}
	
	OrdStatus(char value) {
		this.value = value;
	}
	
	public char value() {
		return value;
	}
	
	public static OrdStatus getType(char c) {
		return map.get(c);
	}
	
	public boolean isOpen() {
		return openSet.contains(this);
	}
	
	public boolean isPending() {
		return pendingSet.contains(this);
	}
	
	public boolean isCancelable() {
		return cancelableSet.contains(this);
	}
	
	public static OrdStatus pendingToReady(OrdStatus status) {
		if (status.equals(PENDING_NEW))
			return NEW;
		else if (status.equals(PENDING_REPLACE))
			return REPLACED;
		else if (status.equals(PENDING_CANCEL))
			return CANCELED;
		else return status;
	}
	
	public boolean isCompleted() {
		return completeSet.contains(this);
	}
	
	public boolean isReady() {
		return readySet.contains(this);
	}
	
}
