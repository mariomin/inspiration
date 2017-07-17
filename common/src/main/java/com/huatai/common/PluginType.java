package com.huatai.common;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum PluginType {
	UpstreamManager("UpstreamManager"), PersistenceManager("PersistenceManager"), DownstreamManager("DownstreamManager"), EventManager(
			"EventManager"), ScheduleManager("ScheduleManager"), None("None"), Undefined("Undefined");
	
	private String value;
	
	PluginType(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	private static final Set<PluginType> topoManagers = Collections
			.unmodifiableSet(EnumSet.of(UpstreamManager, PersistenceManager, DownstreamManager));
	
	public static boolean isTopoManager(PluginType pluginName) {
		return topoManagers.contains(pluginName);
	}
}
