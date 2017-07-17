package com.huatai.common.marketdata;

public class SubscriptionEntry {
	public enum Type {
		Request, Subscribe
	};
	
	private final String key;
	private final String receiver;
	private final Type type;
	private final Level level;
	
	public SubscriptionEntry(String key, String receiver, Type type, Level level) {
		this.key = key;
		this.receiver = receiver;
		this.type = type;
		this.level = level;
	}
	
	public static String getSubscriber(String key, String receiver) {
		return (receiver == null) ? key : receiver;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getReceiver() {
		return receiver;
	}
	
	public Level getLevel() {
		return level;
	}
	
}
