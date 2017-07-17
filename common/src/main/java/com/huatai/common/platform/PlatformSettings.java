package com.huatai.common.platform;

import com.huatai.common.IPlugin;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;

public abstract class PlatformSettings implements IPlugin {
	protected String name;
	protected int highPriorityProcessorThreads = 1;
	protected int normalPriorityProcessorThreads = 1;
	protected int bufferSize = 1024 * 1024;
	protected WaitStrategy waitStrategy = new BlockingWaitStrategy();
	
	@Override
	public void init() throws Exception {}
	
	@Override
	public void uninit() {}
	
	public void setHighPriorityProcessorThreads(int highPriorityProcessorThreads) {
		this.highPriorityProcessorThreads = highPriorityProcessorThreads;
	}
	
	public void setNormalPriorityProcessorThreads(int normalPriorityProcessorThreads) {
		this.normalPriorityProcessorThreads = normalPriorityProcessorThreads;
	}
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	@Override
	public void setBeanName(String name) {
		this.name = name;
	}
	
	@Override
	public String getBeanName() {
		return name;
	}
	
	@Override
	public void recover() {}
	
}
