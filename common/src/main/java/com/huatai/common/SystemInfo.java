package com.huatai.common;

import java.io.Serializable;

public class SystemInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String env = "dev";
	private String category = "htsc";
	private String id = "ats";
	private boolean combined;
	private SystemNodeType type = SystemNodeType.Undefined;
	
	public String getLogChannel() {
		StringBuilder builder = new StringBuilder();
		builder.append(env).append(".").append(category).append(".").append("log");
		return builder.toString();
	}
	
	// nodeInfoChannel is used for broadcasting administrative level message
	public String getAdminChannel() {
		StringBuilder builder = new StringBuilder();
		builder.append(env).append(".").append(category).append(".").append("admin");
		return builder.toString();
	}
	
	// inbox is used for specifically receiving message (instance independent)
	public String getInbox() {
		StringBuilder builder = new StringBuilder();
		builder.append(env).append(".").append(category).append(".").append(id);
		return builder.toString();
	}
	
	public String getExectuionSyncChannel() {
		StringBuilder builder = new StringBuilder("executionSyncChannel.");
		builder.append(env).append(".").append(category);
		return builder.toString();
	}
	
	public String getIndividualChannel(SystemNodeType nodeType) {
		StringBuilder builder = new StringBuilder("individualChannel.");
		builder.append(category).append(".").append(nodeType.name());
		return builder.toString();
	}
	
	public String getSyncChannel(String channel) {
		StringBuilder builder = new StringBuilder("BASE.SYNC.");
		builder.append(env).append(".").append(category).append(".").append(channel);
		return builder.toString();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setEnv(String env) {
		this.env = env;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getEnv() {
		return env;
	}
	
	public String getCategory() {
		return category;
	}
	
	public SystemNodeType getType() {
		return type;
	}
	
	public void setType(SystemNodeType type) {
		this.type = type;
	}
	
	public boolean isCombined() {
		return combined;
	}
	
	public void setCombined(boolean combined) {
		this.combined = combined;
	}
	
	@Override
	public String toString() {
		return getInbox();
	}
}
