package com.huatai.common.algo.blockservice;

import java.util.HashSet;
import java.util.Set;

public abstract class BlockTask {
	private final String id;
	private final long startTime;
	private final long timeout;
	private final Set<BlockSignal> signals = new HashSet<BlockSignal>();
	
	public BlockTask(String id, long timeout) {
		this.id = id;
		this.timeout = timeout;
		startTime = System.currentTimeMillis();
	}
	
	public abstract void invoke(boolean isTimeout);
	
	public String getId() {
		return id;
	}
	
	public void addSignal(BlockSignal signal) {
		signals.add(signal);
	}
	
	public Set<BlockSignal> getSignals() {
		return signals;
	}
	
	public long getTimeout() {
		return timeout;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
}
