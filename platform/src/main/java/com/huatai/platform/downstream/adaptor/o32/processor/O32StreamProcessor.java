package com.huatai.platform.downstream.adaptor.o32.processor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.o32.tags.O32Tags.Field;
import com.huatai.common.o32.tags.O32Tags.FunctionId;
import com.hundsun.mcapi.MCServers;
import com.hundsun.mcapi.exception.MCException;
import com.hundsun.mcapi.exception.MCSubscribeException;
import com.hundsun.mcapi.interfaces.ISubscriber;
import com.hundsun.mcapi.subscribe.MCSubscribeParameter;
import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

public class O32StreamProcessor {
	private static final Logger logger = LoggerFactory.getLogger(O32StreamProcessor.class);
	private final ScheduledExecutorService scheduler;
	private final T2Services service;
	private final IClient client;
	
	private final ISubscriber subscriber;
	private final Set<String> subscribedOperators = new ConcurrentSkipListSet<>();
	private final HeartbeatTask heartbeatTask = new HeartbeatTask();
	
	// operatorNo:o32Account
	private final Map<String, AccountStatus> o32AccountStates = new ConcurrentHashMap<String, AccountStatus>();
	
	public O32StreamProcessor(String config, String serverName) throws T2SDKException, MCException, MCSubscribeException {
		
		//
		service = T2Services.getInstance();
		service.setT2sdkConfigString(config);
		service.init();
		service.start();
		client = service.getClient(serverName);
		
		scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "O32HeartbeatThread");
			}
		});
		scheduler.scheduleWithFixedDelay(heartbeatTask, 5, 5, TimeUnit.SECONDS);
		
		// 启动订阅
		MCServers.MCInit();
		subscriber = MCServers.GetSubscriber();
		
	}
	
	public void stop() {
		service.stop();
	}
	
	public T2Services getT2Services() {
		return service;
	}
	
	/**
	 * 异步调用接口
	 * @param request
	 * @throws T2SDKException
	 */
	public void send(IEvent request) throws T2SDKException {
		addUserToken(request);
		client.send(request);
	}
	
	/**
	 * 同步调用接口
	 * @param request
	 * @return
	 * @throws T2SDKException
	 */
	public IEvent sendRecieve(IEvent request) throws T2SDKException {
		addUserToken(request);
		return client.sendReceive(request);
	}
	
	private void subscriberTopicIfAbsent(String operatorNo) throws T2SDKException {
		try {
			if (!subscribedOperators.contains(operatorNo)) {
				synchronized (subscribedOperators) {
					if (!subscribedOperators.contains(operatorNo)) {
						int ret = subscriber.SubscribeTopic(createMCSubscribeParameter(operatorNo), 1000);
						if (ret <= 0) throw new T2SDKException("Subscriber execution for operator:" + operatorNo + " fail");
						subscribedOperators.add(operatorNo);
					}
				}
			}
		} catch (MCSubscribeException e) {
			logger.error("Subscriber execution for operator:{} fail", operatorNo, e);
			throw new T2SDKException("Subscriber execution for operator:" + operatorNo + " fail", e);
		}
	}
	
	private MCSubscribeParameter createMCSubscribeParameter(String operatorNo) {
		MCSubscribeParameter param = new MCSubscribeParameter();
		param.SetTopicName("ufx_topic");
		param.SetReplace(false);
		param.SetFromNow(true);
		byte[] data = { 1, 3, 5, 8, 9, 10, 2, 3, 0 };
		param.SetAppData(data);
		param.SetFilter("operator_no", operatorNo);
		return param;
	}
	
	private void addUserToken(IEvent request) throws T2SDKException {
		
		IDataset dataset = request.getEventDatas().getDataset(0);
		
		int count = dataset.getRowCount();
		for (int i = 0; i < count; i++) {
			addUserToken(i + 1, dataset);
		}
		
	}
	
	private void addUserToken(int rowNum, IDataset dataset) throws T2SDKException {
		dataset.locateLine(rowNum);
		String operatorNo = dataset.getString(Field.operatorNo);
		
		String userToken = null;
		AccountStatus status = o32AccountStates.get(operatorNo);
		if (null == status) {
			String password = dataset.getString(Field.password);
			userToken = login(operatorNo, password);
			if (null == userToken)
				throw new T2SDKException("o32 account[" + operatorNo + "] invalid");
			else {
				status = new AccountStatus();
				status.setUserToken(userToken);
				o32AccountStates.put(operatorNo, status);
			}
		} else {
			userToken = status.getUserToken();
		}
		dataset.updateString(Field.userToken, userToken);
		
	}
	
	private String login(String operatorNo, String passwd) throws T2SDKException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, FunctionId.login);
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("operator_no", operatorNo);
		parameters.put("password", passwd);
		parameters.put("mac_address", "28-6E-D4-88-CE-21");
		parameters.put("op_station", "xgateway");
		parameters.put("ip_address", "160.10.4.162");//TODO
		parameters.put("authorization_id", "C3FF0BCB0B15EA9F0E34F711CD640BCA");
		
		IEvent response = client.sendReceive(property, parameters);
		
		String errorCode = response.getEventDatas().getDataset(0).getString("ErrorCode");
		if (!"0".equals(errorCode)) {
			String errorMsg = response.getEventDatas().getDataset(0).getString("ErrorMsg");
			String error = "Login O32 fail, operator_no=" + operatorNo + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg;
			logger.error(error);
			throw new T2SDKException(error);
		}
		subscriberTopicIfAbsent(operatorNo);
		return response.getEventDatas().getDataset(1).getString(Field.userToken);
		
	}
	
	/**
	 *
	 * @author 010350
	 *
	 */
	private class HeartbeatTask implements Runnable {
		@Override
		public void run() {
			Set<Entry<String, AccountStatus>> entries = o32AccountStates.entrySet();
			for (Entry<String, AccountStatus> entry : entries) {
				String userToken = entry.getValue().getUserToken();
				
				Map<String, Object> property = new HashMap<String, Object>();
				property.put(EventTagdef.TAG_FUNCTION_ID, FunctionId.heartbeat);
				
				Map<Object, Object> parameters = new HashMap<Object, Object>();
				parameters.put(Field.userToken, userToken);
				try {
					IEvent response = client.sendReceive(property, parameters);
					String errorCode = response.getEventDatas().getDataset(0).getString("ErrorCode");
					if (!"0".equals(errorCode)) {
						logger.error("Flash heartbeat fail, errorCode=" + errorCode);
						o32AccountStates.remove(entry.getKey());
					}
				} catch (Exception e) {
					logger.error("Flash heartbeat fail", e);
					o32AccountStates.remove(entry.getKey());
				}
			}
		}
	}
	
	private class AccountStatus {
		private String userToken;
		
		public String getUserToken() {
			return userToken;
		}
		
		public void setUserToken(String userToken) {
			this.userToken = userToken;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("AccountStatus [userToken=");
			builder.append(userToken);
			builder.append("]");
			return builder.toString();
		}
		
	}
	
}
