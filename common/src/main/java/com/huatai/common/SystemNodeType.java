package com.huatai.common;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum SystemNodeType {
	Upstream("Upstream"), Persistence("Persistence"), Algo("AlgoEngine"), Downstream("Downstream"), OMS("OMS"),
	
	Combined("Combined"), Client("Client"), BackTest("BackTest"), Undefined("Undefined");
	
	private static final Set<SystemNodeType> serverSet = Collections.unmodifiableSet(EnumSet.of(Upstream, Persistence, Algo, Downstream));
	private String value;
	
	SystemNodeType(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public boolean isServer() {
		return serverSet.contains(this);
	}
}
