package com.huatai.common.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.hundsun.O32InvokingException;
import com.huatai.common.o32.account.O32NewOrderRequest;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.EventReturnCode;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

public class O32Util {
	private static final Logger log = LoggerFactory.getLogger(O32Util.class);
	
	private static IDatasets invokeO32Service(IClient instance, Map<String, Object> arg0, Map<Object, Object> arg1) throws O32InvokingException {
		IEvent resultEvent;
		try {
			resultEvent = instance.sendReceive(arg0, arg1);
		} catch (T2SDKException e) {
			throw new O32InvokingException(e);
		}
		
		if (resultEvent.getReturnCode() != EventReturnCode.I_OK)
			throw new O32InvokingException(resultEvent.getErrorNo(), resultEvent.getEventDatas());
		return resultEvent.getEventDatas();
	}
	
	public static void showPack(IDatasets result) {
		if (result == null) return;
		
		StringBuilder strBuf = new StringBuilder();
		int datasetCount = result.getDatasetCount();
		for (int i = 0; i < datasetCount; i++) {
			
			IDataset ds = result.getDataset(i);
			int columnCount = ds.getColumnCount();
			for (int j = 1; j <= columnCount; j++) {
				strBuf.append("\"" + ds.getColumnName(j) + "\",");
			}
			strBuf.deleteCharAt(strBuf.length() - 1);
			strBuf.append("\n");
			
			ds.beforeFirst();
			while (ds.hasNext()) {
				ds.next();
				for (int j = 1; j <= columnCount; j++) {
					strBuf.append("\"" + ds.getString(j) + "\",");
				}
				strBuf.deleteCharAt(strBuf.length() - 1);
				strBuf.append("\n");
			}
			
		}
		log.info(strBuf.toString());
	}
	
	public static String login(IClient client, String operatorNo, String passwd) throws O32InvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "10001");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("operator_no", operatorNo);
		parameters.put("password", passwd);
		parameters.put("mac_address", "28-6E-D4-88-CE-21");
		parameters.put("op_station", "ats");
		parameters.put("ip_address", "160.10.4.162");
		parameters.put("authorization_id", "C3FF0BCB0B15EA9F0E34F711CD640BCA");
		
		IDatasets datasets = invokeO32Service(client, property, parameters);
		showPack(datasets);
		if (datasets.getDatasetCount() > 1)
			return datasets.getDataset(1).getString("user_token");
		else {
			String errorMsg = datasets.getDataset(0).getString("ErrorMsg");
			throw new IllegalArgumentException(errorMsg);
		}
	}
	
	public static IDatasets newOrder(IClient client, O32NewOrderRequest request) throws O32InvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "91001");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("user_token", request.getUserToken());
		parameters.put("account_code", request.getAccountCode());
		parameters.put("asset_no", request.getAssetNo());
		parameters.put("combi_no", request.getCombiNo());
		parameters.put("market_no", request.getMarketNo());
		parameters.put("stock_code", request.getStockCode());
		parameters.put("entrust_direction", request.getEntrustDirection());
		parameters.put("price_type", request.getPriceType());
		parameters.put("entrust_price", request.getEntrustPrice());
		parameters.put("entrust_amount", request.getEntrustAmount());
		
		return invokeO32Service(client, property, parameters);
	}
	
	public static IDatasets cancelOrder(IClient client, String entrustNo, String userToken) throws O32InvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "91101");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("user_token", userToken);
		parameters.put("entrust_no", entrustNo);
		
		return invokeO32Service(client, property, parameters);
	}
	
	public static IDatasets queryAccountCode(IClient client, String userToken) throws O32InvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "30001");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("user_token", userToken);
		
		return invokeO32Service(client, property, parameters);
	}
	
	public static IDatasets queryAssetUnit(IClient client, String userToken) throws O32InvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "30002");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("user_token", userToken);
		
		return invokeO32Service(client, property, parameters);
	}
	
	public static IDatasets queryCombination(IClient client, String userToken, String combiNo) throws O32InvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "30003");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("user_token", userToken);
		parameters.put("combi_no", combiNo);
		
		return invokeO32Service(client, property, parameters);
	}
	
	public static IDatasets queryFundAccountDetails(IClient client, String userToken, String assertNo) throws O32InvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "35011");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("user_token", userToken);
		parameters.put("asset_no", assertNo);
		
		return invokeO32Service(client, property, parameters);
	}
	
	public static IDatasets querySecurityAccountDetails(IClient client, String userToken) throws O32InvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "30004");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("user_token", userToken);
		
		return invokeO32Service(client, property, parameters);
	}
	
	public static IDatasets queryPositionDetails(IClient client, String userToken, String combiNo) throws O32InvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_FUNCTION_ID, "31001");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("user_token", userToken);
		parameters.put("combi_no", combiNo);
		return invokeO32Service(client, property, parameters);
	}
}
