package com.huatai.platform.downstream.adaptor.uf;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.downstream.DownStreamException;
import com.huatai.common.type.ExchangeType;
import com.huatai.common.type.OrderField;
import com.huatai.common.type.OrderSide;
import com.huatai.platform.downstream.adaptor.uf.common.UFFunctionNo;
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

public class UFUtil {
	private static final Logger log = LoggerFactory.getLogger(UFUtil.class);
	private static final DecimalFormat pxFormat = new DecimalFormat("0.000");
	private static final String topicTradeReturn = "secu.deal_return";
	private static final String opEntrustWay = "7";
	private static final String opStation = "ATS";
	private static final String passwordType = "2";
	private static final String userToken = "";
	
	private T2Services service;
	private IClient instance;
	private ISubscriber subscriber;
	private final String config;
	private final String serviceName;
	private final Map<String, Integer> subscribedTopicClientId2Ret = new HashMap<String, Integer>();
	
	public UFUtil(String config, String serviceName) {
		this.config = config;
		this.serviceName = serviceName;
	}
	
	public void init() {
		try {
			service = T2Services.getInstance();
			service.setT2sdkConfigString(config);
			service.init();
			service.start();
			instance = service.getClient(serviceName);
		} catch (T2SDKException e) {
			log.error("Start hundsun service for uf exchange service error. {}", e);
		}
	}
	
	public void subscribe(String clientId) throws DownStreamException {
		if (subscribedTopicClientId2Ret.containsKey(clientId)) return;
		log.info("Subscribing");
		try {
			MCServers.MCInit();
			subscriber = MCServers.GetSubscriber();
		} catch (T2SDKException | MCException | MCSubscribeException e) {
			throw new DownStreamException("Initial subscriber failed.");
		}
		
		subscriberTopic(topicTradeReturn, clientId);
	}
	
	public void unsubscribe() throws MCSubscribeException {
		for (int ret : subscribedTopicClientId2Ret.values()) {
			subscriber.CancelSubscribeTopic(ret);
		}
	}
	
	private void subscriberTopic(String topicName, String clientId) throws DownStreamException {
		MCSubscribeParameter param = new MCSubscribeParameter();
		param.SetReplace(false);
		param.SetFromNow(true);
		param.SetTopicName(topicName);
		param.SetFilter("client_id", clientId);
		int ret = 0;
		try {
			ret = subscriber.SubscribeTopic(param, 1000);
		} catch (MCSubscribeException e) {
			log.error("Subscribe topic[topicName:{}] failed。", topicName);
			throw new DownStreamException("Subscribe topic failed.");
		}
		
		if (ret > 0) {
			log.info("Subscribe topic[topicName:{}, return:{}] success.", topicName, ret);
		} else {
			log.error("Subscribe topic[topicName:{}, return:{}({})] failed。", topicName, ret, MCServers.GetErrorMsg(ret));
			throw new DownStreamException("Subscribe topic failed.");
		}
		
		subscribedTopicClientId2Ret.put(clientId, ret);
	}
	
	public IDataset login(ExchangeOrder order) throws DownStreamException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, UFFunctionNo.ClientLoginRequest.getFunctionNo());
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("op_branch_no", Integer.parseInt(order.getBranchNo()));
		parameters.put("op_entrust_way", opEntrustWay);
		parameters.put("op_station", opStation);
		parameters.put("branch_no", Integer.parseInt(order.getBranchNo()));
		parameters.put("password", order.getField(String.class, OrderField.Password.name()));
		parameters.put("password_type", passwordType);
		parameters.put("input_content", "6");
		parameters.put("account_content", order.getField(String.class, OrderField.ClientId.name()));
		
		IDataset dataset = invokeHundsunService(property, parameters).get(0);
		int errorNo = dataset.getInt("error_no");
		if (errorNo < 0) {
			log.error("Invoke login function error. errorNo[{}], errorInfo[{}]", errorNo,
					dataset.getString("error_info").replace("\n", "").replace("\r", ""));
			throw new DownStreamException("Invoke login function error.");
		} else return dataset;
	}
	
	public IDataset sendNewOrder(int sysnodeId, ExchangeOrder order) throws DownStreamException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, UFFunctionNo.NewOrderRequest.getFunctionNo());
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("op_branch_no", Integer.parseInt(order.getBranchNo()));
		parameters.put("op_entrust_way", opEntrustWay);
		parameters.put("op_station", opStation);
		parameters.put("branch_no", Integer.parseInt(order.getBranchNo()));
		parameters.put("client_id", order.getField(String.class, OrderField.ClientId.name()));
		parameters.put("fund_account", order.getTradingAccount());
		parameters.put("password", order.getField(String.class, OrderField.Password.name()));
		parameters.put("password_type", passwordType);
		parameters.put("user_token", userToken);
		parameters.put("exchange_type", order.getExchangeType() == ExchangeType.SH ? "1" : "2");
		parameters.put("stock_account", order.getSecurityAccount());
		parameters.put("stock_code", order.getSecurityId());
		parameters.put("entrust_amount", order.getQuantity().longValue());
		parameters.put("entrust_price", pxFormat.format(order.getPrice()));
		parameters.put("entrust_bs", order.getOrderSide().equals(OrderSide.Buy) ? "1" : "2");
		parameters.put("entrust_prop", "0");
		
		return invokeHundsunService(property, parameters).get(0);
	}
	
	public IDataset sendCancelOrder(int sysnodeId, ExchangeOrder order) throws DownStreamException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, UFFunctionNo.CancelOrderRequest.getFunctionNo());
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("op_branch_no", Integer.parseInt(order.getBranchNo()));
		parameters.put("op_entrust_way", opEntrustWay);
		parameters.put("op_station", opStation);
		parameters.put("branch_no", Integer.parseInt(order.getBranchNo()));
		parameters.put("client_id", order.getField(String.class, OrderField.ClientId.name()));
		parameters.put("fund_account", order.getTradingAccount());
		parameters.put("password", order.getField(String.class, OrderField.Password.name()));
		parameters.put("password_type", passwordType);
		parameters.put("user_token", userToken);
		parameters.put("entrust_no", order.getField(Integer.class, OrderField.EntrustNo.name()));
		
		return invokeHundsunService(property, parameters).get(0);
	}
	
	public List<IDataset> searchDealReturn(int sysnodeId, ExchangeOrder order) {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, UFFunctionNo.SearchDealRequest);
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("op_branch_no", Integer.parseInt(order.getBranchNo()));
		parameters.put("op_entrust_way", opEntrustWay);
		parameters.put("op_station", opStation);
		parameters.put("branch_no", Integer.parseInt(order.getBranchNo()));
		parameters.put("client_id", order.getField(String.class, OrderField.ClientId.name()));
		parameters.put("fund_account", order.getTradingAccount());
		parameters.put("password", order.getField(String.class, OrderField.Password.name()));
		parameters.put("password_type", passwordType);
		parameters.put("user_token", userToken);
		parameters.put("entrust_no", order.getField(Integer.class, OrderField.EntrustNo.name()));
		
		List<IDataset> list = null;
		try {
			list = invokeHundsunService(property, parameters);
		} catch (DownStreamException e) {
			log.error("search deal return failed. order - {}", order.toString());
			list = new ArrayList<IDataset>();
		}
		return list;
	}
	
	public List<IDataset> searchEntrust(int sysnodeId, int batchNo, ExchangeOrder order) throws DownStreamException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, UFFunctionNo.SearchDealRequest);
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("op_branch_no", Integer.parseInt(order.getBranchNo()));
		parameters.put("op_entrust_way", opEntrustWay);
		parameters.put("op_station", opStation);
		parameters.put("branch_no", Integer.parseInt(order.getBranchNo()));
		parameters.put("client_id", order.getField(String.class, OrderField.ClientId.name()));
		parameters.put("fund_account", order.getTradingAccount());
		parameters.put("password", order.getField(String.class, OrderField.Password.name()));
		parameters.put("password_type", passwordType);
		parameters.put("user_token", userToken);
		// 以batchNo作为关键字查询订单信息
		parameters.put("action_in", "2");
		parameters.put("locate_entrust_no", batchNo);
		
		List<IDataset> list = null;
		try {
			list = invokeHundsunService(property, parameters);
		} catch (DownStreamException e) {
			log.error("search entrust failed. order - {}", order.toString());
			list = new ArrayList<IDataset>();
		}
		return list;
	}
	
	private List<IDataset> invokeHundsunService(Map<String, Object> property, Map<Object, Object> parameters) throws DownStreamException {
		List<IDataset> datasetList = new ArrayList<IDataset>();
		IEvent event = null;
		try {
			event = instance.sendReceive(property, parameters);
		} catch (T2SDKException e) {
			log.error("Invoke Hundsun service error. ");
			log.error(property.toString());
			log.error(parameters.toString());
			throw new DownStreamException("Invoke Hundsun service error. " + e);
		}
		// 当传输链路正常，返回码小于0时，dataset的第一个记录为错误数据，同样传往前端
		//		if (event.getReturnCode() != EventReturnCode.I_OK) {
		//			log.error(new UFErrorInfo(event.getEventDatas().getDataset(0)).toString());
		//			throw new DownStreamException("Invoke Hundsun service error -- " + event.getErrorNo());
		//		}
		
		for (int i = 0; i < event.getEventDatas().getDatasetCount(); i++) {
			datasetList.add(event.getEventDatas().getDataset(i));
		}
		
		return datasetList;
	}
	
	public static String convertExchangeType(ExchangeType exchangeType) {
		return exchangeType == ExchangeType.SH ? "1" : "2";
	}
	
	public static ExchangeType antiConvertExchangeType(String exchangeType) {
		switch (exchangeType) {
			case "1":
				return ExchangeType.SH;
			case "2":
				return ExchangeType.SZ;
			default:
				return null;
		}
	}
	
}
