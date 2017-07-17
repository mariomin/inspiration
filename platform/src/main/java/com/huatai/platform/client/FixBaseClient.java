package com.huatai.platform.client;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.stream.IStreamAdaptor;

import quickfix.Message;
import quickfix.SessionID;

public class FixBaseClient implements IFixClientListener {
	private static final Logger log = LoggerFactory.getLogger(FixBaseClient.class);
	protected final List<IStreamAdaptor<IFixClientService>> adaptors;
	protected final LinkedBlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
	protected long initPeriod;
	protected long runPeriod;
	
	public FixBaseClient(List<IStreamAdaptor<IFixClientService>> adaptors) {
		this.adaptors = adaptors;
	}
	
	public void init() throws Exception {
		log.info("initialising");
		
		for (IStreamAdaptor<IFixClientService> adaptor : adaptors) {
			try {
				adaptor.init();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage());
			}
			for (IFixClientService service : adaptor.getReceivers()) {
				service.setFixClientListener(this);
			}
		}
	}
	
	public void waitServerReady() throws InterruptedException {
		log.info("waiting for platform to be ready");
		
		synchronized (this) {
			wait(initPeriod);
		}
	}
	
	public void standby() throws InterruptedException {
		log.info("thread standby...");
		
		synchronized (this) {
			if (runPeriod < 0) {
				wait();
			} else if (runPeriod > 0) {
				wait(runPeriod);
			}
		}
	}
	
	public void uninit() {
		log.info("uninitialising");
		
		for (IStreamAdaptor<IFixClientService> adaptor : adaptors) {
			adaptor.uninit();
		}
	}
	
	@Override
	public void processsFixMessage(Message message, SessionID sessionId) throws Exception {
		queue.offer(message);
	}
	
	public Message takeFixMessage() throws InterruptedException {
		Message message = queue.poll(10, TimeUnit.SECONDS);
		if (null == message) throw new RuntimeException("Time out for waiting response...");
		return message;
	}
	
	public void sendFixMessage(Message message) throws Exception {
		adaptors.get(0).getReceivers().get(0).sendMessage(message);
	}
	
	public void setInitPeriod(long initPeriod) {
		this.initPeriod = initPeriod;
	}
	
	public long getRunPeriod() {
		return runPeriod;
	}
	
	public void setRunPeriod(long runPeriod) {
		this.runPeriod = runPeriod;
	}
	
}
