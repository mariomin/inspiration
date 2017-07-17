package com.huatai.common.marketdata.event.quote;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.marketdata.Level;
import com.huatai.common.marketdata.Quote;

public class QuoteReplyEvent extends AsyncEvent implements Cloneable {
	private static final long serialVersionUID = 1L;
	private Quote quote;
	private Level level;
	
	public QuoteReplyEvent(String key, Quote quote) {
		super(key);
		this.quote = quote;
	}
	
	public QuoteReplyEvent(String key, Quote quote, Level level) {
		super(key);
		this.quote = quote;
		this.level = level;
	}
	
	public Quote getQuote() {
		return quote;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public QuoteReplyEvent clone(Level level) {
		QuoteReplyEvent obj = null;
		try {
			obj = (QuoteReplyEvent) super.clone();
			obj.quote = quote.clone(level);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
}
