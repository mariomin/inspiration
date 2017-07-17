package com.huatai.platform.downstream.adaptor.o32;

import java.util.HashMap;
import java.util.Map;

import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

public class O32TmpService {
	
	private T2Services service;
	private IClient client;
	private String config;
	private String serverName;
	
	public void init() throws T2SDKException {
		if ((null == config) || (null == serverName)) throw new IllegalArgumentException("hundsun config or serverName can not be null");
		
		service = T2Services.getInstance();
		service.setT2sdkConfigString(config);
		service.init();
		service.start();
		client = service.getClient(serverName);
	}
	
	public IEvent login(String userid, String passwd) throws T2SDKException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "10001");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("operator_no", userid);
		parameters.put("password", passwd);
		parameters.put("mac_address", "28-6E-D4-88-CE-21");
		parameters.put("op_station", "ats");
		parameters.put("ip_address", "160.10.4.162");
		parameters.put("authorization_id", "C3FF0BCB0B15EA9F0E34F711CD640BCA");
		
		IEvent response = client.sendReceive(property, parameters);
		return response;
	}
	
	public IEvent heartbeat(String userToken) throws T2SDKException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "10000");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("user_token", userToken);
		IEvent response = client.sendReceive(property, parameters);
		return response;
		
	}
	
	public IEvent newOrder(String userToken, String clOrderId) throws T2SDKException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "91001");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("user_token", userToken);
		parameters.put("account_code", "830002");
		parameters.put("asset_no", "2102");
		parameters.put("combi_no", "2102a");
		parameters.put("market_no", "2");
		parameters.put("stock_code", "000627");
		parameters.put("entrust_direction", "1");
		parameters.put("price_type", "0");
		parameters.put("entrust_price", "8.13");
		parameters.put("entrust_amount", "100");
		parameters.put("extsystem_id", clOrderId);
		
		return client.sendReceive(property, parameters);
	}
	
	public IEvent cancelOrder(String userToken, String entrust_no) throws T2SDKException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "91101");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("user_token", userToken);
		parameters.put("entrust_no", entrust_no);
		
		return client.sendReceive(property, parameters);
	}
	
	public IEvent queryEntrustByEntrustNo(String userToken, String entrustNo) throws T2SDKException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "32001");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("user_token", userToken);
		parameters.put("account_code", "830002");
		parameters.put("asset_no", "2102");
		parameters.put("combi_no", "2102a");
		parameters.put("entrust_no", entrustNo);
		
		return client.sendReceive(property, parameters);
	}
	
	public IEvent queryEntrust(String userToken, String position) throws T2SDKException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "32001");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("user_token", userToken);
		parameters.put("account_code", "830002");
		parameters.put("asset_no", "2102");
		parameters.put("combi_no", "2102a");
		parameters.put("position_str", position);
		
		return client.sendReceive(property, parameters);
	}
	
	/**
	 * 查询成交
	 * @return
	 * @throws T2SDKException
	 */
	public IEvent queryExecution(String userToken, String position) throws T2SDKException {
		
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "33001");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("user_token", userToken);
		parameters.put("account_code", "830002");
		parameters.put("asset_no", "2102");
		parameters.put("combi_no", "2102a");
		parameters.put("position_str", position);
		
		return client.sendReceive(property, parameters);
	}
	
	public void setConfig(String config) {
		this.config = config;
	}
	
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
}
