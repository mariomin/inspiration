package com.huatai.common.marketdata;

import java.util.HashSet;
import java.util.Set;

import com.huatai.common.event.base.AsyncEvent;

public abstract class AbstractMarketDataEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final Set<String> symbols;
	private Level level;
	private String mdReqID;
	
	public AbstractMarketDataEvent(String key, Set<String> symbols) {
		super(key);
		this.symbols = symbols;
	}
	
	public AbstractMarketDataEvent(String key, Set<String> symbols, Level level) {
		super(key);
		this.symbols = symbols;
		this.level = level;
	}
	
	public AbstractMarketDataEvent(String key, String symbol) {
		super(key);
		symbols = new HashSet<String>();
		symbols.add(symbol);
	}
	
	public Set<String> getSymbols() {
		return symbols;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void setMdReqID(String mdReqID) {
		this.mdReqID = mdReqID;
	}
	
	public String getMdReqID() {
		return mdReqID;
	}
	
}
