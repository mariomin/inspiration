package com.huatai.common.algo.blockservice;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.util.ConcurrentHashSet;

public class AsyncBlockService {
	private static final Logger log = LoggerFactory.getLogger(AsyncBlockService.class);
	
	private static final long defaultInterval = 10;
	
	// key := taskId, value=BlockTasK
	private final Map<String, BlockTask> blockTasks = new ConcurrentHashMap<String, BlockTask>();
	
	// key := taskId, value= signal set which BlockTask waits for
	private final Map<String, ConcurrentHashSet<BlockSignal>> blockSignalMap = new ConcurrentHashMap<String, ConcurrentHashSet<BlockSignal>>();
	
	private boolean running = true;
	
	private final Thread monitor = new Thread(new Runnable() {
		
		@Override
		public void run() {
			while (running) {
				try {
					for (BlockTask blockTask : blockTasks.values()) {
						String id = blockTask.getId();
						Set<BlockSignal> receivedSignals = blockSignalMap.get(blockTask.getId());
						
						if ((System.currentTimeMillis() - blockTask.getStartTime()) > blockTask.getTimeout()) {
							blockTasks.remove(id);
							blockSignalMap.remove(id);
							blockTask.invoke(true);
						} else if (receivedSignals.containsAll(blockTask.getSignals())) {
							blockTasks.remove(id);
							blockSignalMap.remove(id);
							blockTask.invoke(false);
						}
					}
					Thread.sleep(defaultInterval);
				} catch (InterruptedException e) {
					log.error("thread is interrupted", e);
				}
			}
		}
	});
	
	public void init() {
		monitor.start();
	}
	
	public void uninit() {
		running = false;
	}
	
	public void addBlockTask(BlockTask blockTask) {
		String blockTaskId = blockTask.getId();
		BlockTask preBlockTask = blockTasks.putIfAbsent(blockTaskId, blockTask);
		if (preBlockTask == null) {
			blockSignalMap.put(blockTaskId, new ConcurrentHashSet<BlockSignal>());
		}
	}
	
	public void receiveSignal(String blockId, BlockSignal signal) {
		if (blockTasks.containsKey(blockId)) {
			blockSignalMap.get(blockId).add(signal);
		}
	}
}
