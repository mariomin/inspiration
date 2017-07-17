package com.huatai.platform.upstream;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huatai.common.SystemInfo;
import com.huatai.common.SystemNodeType;
import com.huatai.common.event.IAsyncEventManager;
import com.huatai.common.event.account.ResponsePositionEvent;
import com.huatai.common.event.algo.AlgoExecutionReportEvent;
import com.huatai.common.event.algo.RegisterredStrategyReplyEvent;
import com.huatai.common.event.algo.StrategyAlterReplyEvent;
import com.huatai.common.event.algo.StrategyQueryReplyEvent;
import com.huatai.common.event.algo.StrategyReportReplyEvent;
import com.huatai.common.event.algo.StrategySubscribeReplyEvent;
import com.huatai.common.event.platform.PluginReadyEvent;
import com.huatai.common.platform.ISystemNode;
import com.huatai.common.platform.PlatformSettings;
import com.huatai.platform.event.AsyncEventProcessor;
import com.huatai.platform.upstream.common.IUpStreamAdaptor;
import com.huatai.platform.upstream.common.IUpStreamCallbackHandler;
import com.huatai.platform.upstream.common.IUpStreamConnection;
import com.huatai.platform.upstream.common.IUpStreamSender;
import com.huatai.platform.upstream.common.UpStreamException;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

import quickfix.field.RefMsgType;
import quickfix.fix50sp2.NewOrderSingle;

public class UpStreamManager extends PlatformSettings implements ISystemNode, IUpStreamCallbackHandler {
	private static final Logger log = LoggerFactory.getLogger(UpStreamManager.class);
	
	@Autowired
	private IAsyncEventManager eventManager;
	
	@Autowired
	private SystemInfo systemInfo;
	
	private final List<IUpStreamAdaptor<IUpStreamConnection>> adaptors;
	private final Map<String, IUpStreamSender> senders = new HashMap<String, IUpStreamSender>();
	private final UpStreamAsyncEventProcessor eventProcessor = new UpStreamAsyncEventProcessor();
	
	private final class UpStreamAsyncEventProcessor extends AsyncEventProcessor {
		@Override
		public void subscribeToEvents() {
			for (IUpStreamAdaptor<IUpStreamConnection> adaptor : adaptors) {
				for (IUpStreamConnection connection : adaptor.getReceivers()) {
					subscribeToEvents(connection.getId());
				}
			}
		}
		
		public void subscribeToEvents(String key) {
			subscribeToEvent(ResponsePositionEvent.class, key);
			subscribeToEvent(StrategyAlterReplyEvent.class, key);
			subscribeToEvent(StrategyReportReplyEvent.class, key);
			subscribeToEvent(StrategySubscribeReplyEvent.class, key);
			subscribeToEvent(StrategyQueryReplyEvent.class, key);
			subscribeToEvent(RegisterredStrategyReplyEvent.class, key);
			subscribeToEvent(AlgoExecutionReportEvent.class, key);
		}
		
		@Override
		public IAsyncEventManager getEventManager() {
			return eventManager;
		}
		
		@Override
		public ProducerType getDisruptorProducerType() {
			return ProducerType.MULTI;
		}
		
		@Override
		public int getBufferSize() {
			return bufferSize;
		}
		
		@Override
		public int getHighPriorityEventProcessorThreads() {
			return 1;
		}
		
		@Override
		public int getNormalPriorityProcessorThreads() {
			return normalPriorityProcessorThreads;
		}
		
		@Override
		public WaitStrategy getWaitStrategy() {
			return waitStrategy;
		}
		
	};
	
	public UpStreamManager(List<IUpStreamAdaptor<IUpStreamConnection>> adaptors) {
		this.adaptors = adaptors;
	}
	
	@Override
	public void init() throws Exception {
		log.info("initialising");
		super.init();
		
		eventProcessor.setHandler(this);
		eventProcessor.init();
		if (eventProcessor.getThread() != null) {
			eventProcessor.getThread().setName(name);
		}
		
		Set<String> dupCheck = new HashSet<String>();
		for (IUpStreamAdaptor<IUpStreamConnection> adaptor : adaptors) {
			try {
				adaptor.init(this);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new UpStreamException(e.getMessage());
			}
			for (IUpStreamConnection connection : adaptor.getReceivers()) {
				if (dupCheck.contains(connection.getId())) throw new UpStreamException("This connection id already exists: " + connection.getId());
				dupCheck.add(connection.getId());
				
				UpStreamListener listener = new UpStreamListener(systemInfo, connection, eventManager);
				IUpStreamSender sender = connection.setListener(listener);
				senders.put(connection.getId(), sender);
			}
		}
		
		eventManager.sendEvent(new PluginReadyEvent(null, name, true));
	}
	
	@Override
	public void setEventListener(IUpStreamConnection connection) {
		eventProcessor.subscribeToEvents(connection.getId());
		IUpStreamSender sender = connection.setListener(new UpStreamListener(systemInfo, connection, eventManager));
		senders.put(connection.getId(), sender);
	}
	
	@Override
	public void uninit() {
		log.info("uninitialising");
		
		eventProcessor.uninit();
		senders.clear();
		super.uninit();
	}
	
	public void processRegisterredStrategyReplyEvent(RegisterredStrategyReplyEvent event) {
		log.debug("processRegisterredStrategyReplyEvent, event={}", event);
		IUpStreamSender sender = senders.get(event.getKey());
		sender.responseStrategyNameQuery(event.getRequestId(), event.getStrategyNames());
	}
	
	public void processStrategyQueryReplyEvent(StrategyQueryReplyEvent event) {
		log.debug("processsStrategyQueryReplyEvent, event={}", event);
		IUpStreamSender sender = senders.get(event.getKey());
		sender.responseStrategyQuery(event.getRequestID(), event.getRecords());
	}
	
	public void processStrategyReportReplyEvent(StrategyReportReplyEvent event) {
		log.debug("processStrategyReportReply, event={}", event);
		IUpStreamSender sender = senders.get(event.getKey());
		sender.responseStrategyReport(event.getStrategyId(), event.getClOrderId(), event.getSecurityTradingIndex(), event.getSubscribeId(),
				event.getStrategyReport());
	}
	
	public void processStrategyAlterReplyEvent(StrategyAlterReplyEvent event) {
		log.debug("processStrategyAlterReply, event={}", event);
		IUpStreamSender sender = senders.get(event.getKey());
		sender.responseStrategyAlter(event.getRequestID(), event.isSuccess(), event.getStrategyID(), event.getClOrdId(), event.getStrategyState(),
				event.getText());
	}
	
	public void processStrategySubscribeReplyEvent(StrategySubscribeReplyEvent event) {
		log.debug("processStrategySubscribeReply, event={}", event);
		IUpStreamSender sender = senders.get(event.getKey());
		sender.responseStrategySubscribe(event.getRequestID(), event.isSuccess());
	}
	
	public void processResponsePositionEvent(ResponsePositionEvent event) {
		log.debug("processResponsePositionEvent, key={}, requestId={}, securityTradingIndex={}, text={}", event.getKey(), event.getRequestID(),
				event.getTradingAccountIndex(), event.getText());
		
		IUpStreamSender sender = senders.get(event.getKey());
		sender.responsePositionRequest(event.getRequestID(), event.getTradingAccountIndex(), event.getTradingPositions(),
				event.getPortfolioPositions(), event.getDataObject());
	}
	
	public void processAlgoExecutionReportEvent(AlgoExecutionReportEvent event) {
		log.debug("processAlgoExecutionReportEvent event={}", event);
		IUpStreamSender sender = senders.get(event.getKey());
		sender.updateOrder(event.getExecType(), event.getExecID(), new RefMsgType(NewOrderSingle.MSGTYPE), event.getOrder(), event.getTradingFund(),
				event.getTradingPosition(), event.getDataObject(), null);
	}
	
	@Override
	public SystemNodeType getNodeType() {
		return SystemNodeType.Upstream;
	}
	
}
