package com.huatai.common.disruptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.event.base.AsyncEventContainer;
import com.huatai.common.event.base.EventPriority;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class DisruptorDispatcher {
	private static final Logger logger = LoggerFactory.getLogger(DisruptorDispatcher.class);
	private DisruptorPack highPriorityPack;
	private DisruptorPack normalPriorityPack;
	
	private final class DisruptorPack {
		private final DisruptorThreadFactory threadFactory = new DisruptorThreadFactory();
		private final ExecutorService executor;
		private final Disruptor<AsyncEventContainer> disruptor;
		private RingBuffer<AsyncEventContainer> ringBuffer;
		
		@SuppressWarnings({ "unchecked", "deprecation" })
		public DisruptorPack(WorkHandler<AsyncEventContainer> handler, int nThreads, int bufferSize, ProducerType producerType,
				WaitStrategy waitStrategy) {
			executor = Executors.newFixedThreadPool(nThreads, threadFactory);
			disruptor = new Disruptor<AsyncEventContainer>(new AsyncEventContainerFactory(), bufferSize, executor, producerType, waitStrategy);
			
			WorkHandler<AsyncEventContainer>[] handlers = new WorkHandler[nThreads];
			for (int i = 0; i < nThreads; i++) {
				handlers[i] = handler;
			}
			
			// 打印错误场景，便于问题定位，
			// 注意：改方法必须在handleEventsWithWorkerPool方法之前被调用
			disruptor.handleExceptionsWith(new ExceptionHandler<Object>() {
				
				@Override
				public void handleEventException(Throwable ex, long sequence, Object event) {
					logger.error("handleEventException", ex);
					
				}
				
				@Override
				public void handleOnStartException(Throwable ex) {
					logger.error("handleOnStartException", ex);
					
				}
				
				@Override
				public void handleOnShutdownException(Throwable ex) {
					logger.error("handleOnShutdownException", ex);
					
				}
			});
			
			disruptor.handleEventsWithWorkerPool(handlers);
		}
		
		public void setName(String name) {
			for (int i = 0; i < threadFactory.threads.size(); i++) {
				Thread thread = threadFactory.threads.get(i);
				thread.setName(name + "-" + i);
			}
		}
	}
	
	public final class AsyncEventContainerFactory implements EventFactory<AsyncEventContainer> {
		@Override
		public AsyncEventContainer newInstance() {
			return new AsyncEventContainer();
		}
	}
	
	private final class DisruptorThreadFactory implements ThreadFactory {
		private final List<Thread> threads = new ArrayList<Thread>();
		
		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			threads.add(thread);
			return thread;
		}
	}
	
	public DisruptorDispatcher(WorkHandler<AsyncEventContainer> handler, int bufferSize, ProducerType producerType, WaitStrategy waitStrategy,
			int highPriorityEventThreads, int normalPriorityEventThreads) {
		if (highPriorityEventThreads > 0) {
			highPriorityPack = new DisruptorPack(handler, highPriorityEventThreads, bufferSize, producerType, waitStrategy);
		}
		
		if (normalPriorityEventThreads > 0) {
			normalPriorityPack = new DisruptorPack(handler, normalPriorityEventThreads, bufferSize, producerType, waitStrategy);
		}
	}
	
	public void addEvent(AsyncEvent event) {
		RingBuffer<AsyncEventContainer> ringBuffer;
		EventPriority priority = event.getPriority();
		
		if (priority == EventPriority.HIGH) {
			ringBuffer = highPriorityPack.ringBuffer;
		} else if (priority == EventPriority.NORMAL) {
			ringBuffer = normalPriorityPack.ringBuffer;
		} else throw new RuntimeException("Event priority not defined");
		
		long sequence = ringBuffer.next();
		try {
			AsyncEventContainer container = ringBuffer.get(sequence);
			container.setEvent(event);
		}
		finally {
			ringBuffer.publish(sequence);
		}
	}
	
	public void setName(String name) {
		if (highPriorityPack != null) {
			highPriorityPack.setName(name + "-highPriority");
		}
		
		if (normalPriorityPack != null) {
			normalPriorityPack.setName(name + "-normalPriority");
		}
	}
	
	public void start() {
		if (highPriorityPack != null) {
			highPriorityPack.ringBuffer = highPriorityPack.disruptor.start();
		}
		
		if (normalPriorityPack != null) {
			normalPriorityPack.ringBuffer = normalPriorityPack.disruptor.start();
		}
		
	}
	
	public void exit() {
		if (highPriorityPack != null) {
			highPriorityPack.disruptor.shutdown();
			highPriorityPack.executor.shutdown();
		}
		
		if (normalPriorityPack != null) {
			normalPriorityPack.disruptor.shutdown();
			normalPriorityPack.executor.shutdown();
		}
	}
	
	public boolean isAlive() {
		boolean alive = false;
		if (highPriorityPack != null) {
			alive = !highPriorityPack.executor.isTerminated();
		}
		
		if (normalPriorityPack != null) {
			alive = alive || !normalPriorityPack.executor.isTerminated();
		}
		
		return alive;
	}
	
	public int getHighPriorityQueueSize() {
		return highPriorityPack.ringBuffer.getBufferSize();
	}
	
	public long getHighPriorityQueueRemainingCapacity() {
		return highPriorityPack.ringBuffer.remainingCapacity();
	}
	
	public int getNormalPriorityQueueSize() {
		return normalPriorityPack.ringBuffer.getBufferSize();
	}
	
	public long getNormalPriorityQueueRemainingCapacity() {
		return normalPriorityPack.ringBuffer.remainingCapacity();
	}
	
}
