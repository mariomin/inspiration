package com.huatai.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.business.Execution;
import com.huatai.common.hundsun.HundsunInvokingException;
import com.huatai.common.refdata.RefData;
import com.huatai.common.type.OrderField;
import com.huatai.common.type.OrderSide;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.EventReturnCode;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

public class HundsunUtil {
	private static final Logger log = LoggerFactory.getLogger(HundsunUtil.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
	private static final String today = dateFormat.format(System.currentTimeMillis());
	private static final String sysnodeId = "2";
	private static final String op_station = "1234567890";
	private static final String op_entrust_way = "7";
	private static final String identity_type = "1";
	private static final String password_type = "2";
	private static final String user_token = " ";
	private static final String fund_company = "";
	private static final String fund_code = "";
	private static final String allotno = "";
	private static final String bank_no = "";
	private static final String remark = "";
	private static final String action_in = "";
	private static final String op_remark = "";
	private static final String money_type = "0";
	
	public static IDatasets queryFund(IClient instance, String fundAccount, String branchNo, String clientId, String password)
			throws HundsunInvokingException {
		
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "332255");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("identity_type", identity_type);
		parameters.put("op_branch_no", branchNo);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("op_station", op_station);
		parameters.put("branch_no", branchNo);
		parameters.put("client_id", clientId);
		parameters.put("fund_account", fundAccount);
		parameters.put("password", password);
		parameters.put("password_type", password_type);
		parameters.put("user_token", user_token);
		parameters.put("money_type", money_type);
		
		return HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static IDatasets queryAccountEntrusts(IClient instance, String clientId, String password, String branchNo, String fund_account)
			throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "331153");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("identity_type", identity_type);
		parameters.put("op_branch_no", branchNo);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("op_station", op_station);
		parameters.put("branch_no", branchNo);
		parameters.put("client_id", clientId);
		parameters.put("fund_account", fund_account);
		parameters.put("password", password);
		parameters.put("password_type", password_type);
		parameters.put("user_token", user_token);
		
		return HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static IDatasets queryFundAccountRestriction(IClient instance, String clientId, String password, String branchNo, String fund_account)
			throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "331154");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("identity_type", identity_type);
		parameters.put("op_branch_no", branchNo);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("op_station", op_station);
		parameters.put("branch_no", branchNo);
		parameters.put("client_id", clientId);
		parameters.put("fund_account", fund_account);
		parameters.put("password", password);
		parameters.put("password_type", password_type);
		parameters.put("user_token", user_token);
		
		return HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static IDatasets queryCommissionBySide(IClient instance, String clientId, String branchNo, String password, String side,
			String fund_account) throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "331156");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("identity_type", identity_type);
		parameters.put("op_branch_no", branchNo);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("op_station", op_station);
		parameters.put("branch_no", branchNo);
		parameters.put("client_id", clientId);
		parameters.put("fund_account", fund_account);
		parameters.put("password", password);
		parameters.put("password_type", password_type);
		parameters.put("entrust_bs", side);
		
		return HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static IDatasets queryAccount(IClient instance, String branchNo, String clientId, String password) throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "331150");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("identity_type", identity_type);
		parameters.put("op_branch_no", branchNo);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("op_station", op_station);
		parameters.put("branch_no", branchNo);
		parameters.put("client_id", clientId);
		parameters.put("password", password);
		parameters.put("password_type", password_type);
		
		return HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static void freezeFund(IClient instance, String clientId, String password, String branchNo, String fundAccountId, String currency,
			double balance) throws HundsunInvokingException {
		if ((fundAccountId == null) || (currency == null)) throw new IllegalArgumentException();
		
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "332211");
		
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("identity_type", identity_type);
		parameters.put("op_branch_no", branchNo);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("op_station", op_station);
		parameters.put("password", password);
		parameters.put("password_type", password_type);
		synchronized (dateFormat) {
			parameters.put("date", dateFormat.format(date));
			parameters.put("time", timeFormat.format(date));
		}
		parameters.put("business_flag", "2028");
		parameters.put("money_type", currency);
		parameters.put("balance", balance);
		parameters.put("fund_company", fund_company);
		parameters.put("fund_code", fund_code);
		parameters.put("allotno", allotno);
		parameters.put("bank_no", bank_no);
		parameters.put("remark", remark);
		parameters.put("user_token", user_token);
		parameters.put("branch_no", branchNo);
		parameters.put("client_id", clientId);
		parameters.put("fund_account", fundAccountId);
		
		HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static void unfreezeFund(IClient instance, String clientId, String password, String branchNo, String fundAccountId, String currency,
			double balance) throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "332211");
		
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("identity_type", identity_type);
		parameters.put("op_branch_no", branchNo);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("op_station", op_station);
		parameters.put("password", password);
		parameters.put("password_type", password_type);
		synchronized (dateFormat) {
			parameters.put("date", dateFormat.format(date));
			parameters.put("time", timeFormat.format(date));
		}
		parameters.put("business_flag", "2027");
		parameters.put("money_type", currency);
		parameters.put("balance", balance);
		parameters.put("fund_company", fund_company);
		parameters.put("fund_code", fund_code);
		parameters.put("allotno", allotno);
		parameters.put("bank_no", bank_no);
		parameters.put("remark", remark);
		parameters.put("user_token", user_token);
		parameters.put("branch_no", branchNo);
		parameters.put("client_id", clientId);
		parameters.put("fund_account", fundAccountId);
		
		HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static IDatasets queryFundAccounts(IClient instance, String clientId, String password, String branchNo) throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "331155");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("identity_type", identity_type);
		parameters.put("op_branch_no", branchNo);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("op_station", op_station);
		parameters.put("branch_no", branchNo);
		parameters.put("client_id", clientId);
		parameters.put("password", password);
		parameters.put("password_type", password_type);
		parameters.put("user_token", user_token);
		
		return HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static void freezePosition(IClient instance, String clientId, String password, String branchNo, String fund_account, String exchangeType,
			String stockAccount, String stockCode, double occurAmount) throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "571600");
		
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("identity_type", identity_type);
		parameters.put("op_branch_no", branchNo);
		parameters.put("op_station", op_station);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("branch_no", branchNo);
		parameters.put("client_id", clientId);
		parameters.put("fund_account", fund_account);
		parameters.put("password", password);
		parameters.put("password_type", password_type);
		parameters.put("exchange_type", exchangeType);
		parameters.put("stock_account", stockAccount);
		parameters.put("stock_code", stockCode);
		parameters.put("occur_amount", occurAmount);
		synchronized (dateFormat) {
			parameters.put("valid_date", dateFormat.format(date));
		}
		parameters.put("remark", remark);
		parameters.put("user_token", user_token);
		parameters.put("action_in", action_in);
		
		HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static void unfreezePosition(IClient instance, String clientId, String password, String branchNo, String fund_account, String exchangeType,
			String stockAccount, String stockCode, double treatAmount) throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "571603");
		
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("identity_type", identity_type);
		parameters.put("op_branch_no", branchNo);
		parameters.put("op_station", op_station);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("branch_no", branchNo);
		parameters.put("client_id", clientId);
		parameters.put("fund_account", fund_account);
		parameters.put("password", password);
		parameters.put("password_type", password_type);
		parameters.put("exchange_type", exchangeType);
		parameters.put("stock_account", stockAccount);
		parameters.put("stock_code", stockCode);
		synchronized (dateFormat) {
			parameters.put("occur_date", dateFormat.format(date));
		}
		parameters.put("treat_amount", treatAmount);
		parameters.put("remark", remark);
		parameters.put("op_remark", op_remark);
		
		HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static IDatasets querySecurityAccount(IClient instance, String clientId, String password, String branchNo, String fund_account)
			throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "331300");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("identity_type", identity_type);
		parameters.put("op_branch_no", branchNo);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("op_station", op_station);
		parameters.put("branch_no", branchNo);
		parameters.put("client_id", clientId);
		parameters.put("fund_account", fund_account);
		parameters.put("password", password);
		parameters.put("password_type", password_type);
		parameters.put("user_token", user_token);
		
		return HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static IDatasets queryFundAccountPermissions(IClient instance, String clientId, String password, String branchNo, String fund_account)
			throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "331152");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("identity_type", identity_type);
		parameters.put("op_branch_no", branchNo);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("op_station", op_station);
		parameters.put("branch_no", branchNo);
		parameters.put("client_id", clientId);
		parameters.put("fund_account", fund_account);
		parameters.put("password", password);
		parameters.put("password_type", password_type);
		parameters.put("user_token", user_token);
		
		return HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static IDatasets queryPosition(IClient instance, String clientId, String password, String branchNo, String fund_account,
			String exchange_type, String stock_account) throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "333104");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("identity_type", identity_type);
		parameters.put("op_branch_no", branchNo);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("op_station", op_station);
		parameters.put("branch_no", branchNo);
		parameters.put("client_id", clientId);
		parameters.put("fund_account", fund_account);
		parameters.put("password", password);
		parameters.put("password_type", password_type);
		parameters.put("exchange_type", exchange_type);
		parameters.put("stock_account", stock_account);
		
		return HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static IDatasets syncExchangeOrder(IClient instance, ExchangeOrder order, String exchangeType, List<String> records)
			throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "571601");
		
		String created, now;
		synchronized (dateFormat) {
			created = timeFormat.format(order.getCreated());
			now = timeFormat.format(System.currentTimeMillis());
		}
		
		int entrustNo = order.getField(Integer.class, OrderField.EntrustNo.name());
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("op_station", op_station);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("branch_no", order.getBranchCode());
		parameters.put("client_id", order.getInvestorAccount());
		parameters.put("fund_account", order.getAssetAccount());
		parameters.put("exchange_type", exchangeType);
		parameters.put("stock_account", order.getSecurityAccount());
		parameters.put("stock_code", order.getSecurityId());
		parameters.put("stock_type", "0");
		parameters.put("batch_no", entrustNo);
		parameters.put("entrust_prop", "0");
		parameters.put("entrust_price", order.getPrice());
		parameters.put("entrust_amount", order.getQuantity());
		parameters.put("entrust_type", "0");
		parameters.put("entrust_bs", order.getOrderSide().equals(OrderSide.Buy) ? "1" : "2");
		parameters.put("seat_no", order.getPbu());
		parameters.put("entrust_no", entrustNo);
		parameters.put("entrust_time", created);
		parameters.put("report_no", entrustNo);
		parameters.put("report_time", created);
		parameters.put("curr_date", today);
		parameters.put("curr_time", now);
		parameters.put("op_remark", "");
		
		records.add("order:" + parameters.toString());
		
		return HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static IDatasets syncExecution(IClient instance, ExchangeOrder order, Execution execution, String exchangeType, List<String> records)
			throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "571602");
		
		String created, now;
		synchronized (dateFormat) {
			created = timeFormat.format(execution.getCreated());
			now = timeFormat.format(System.currentTimeMillis());
		}
		
		int entrustNo = order.getField(Integer.class, OrderField.EntrustNo.name());
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("op_station", op_station);
		parameters.put("op_entrust_way", op_entrust_way);
		parameters.put("init_date", today);
		parameters.put("branch_no", order.getBranchCode());
		parameters.put("entrust_no", entrustNo);
		parameters.put("report_bs", order.getOrderSide().equals(OrderSide.Buy) ? "1" : "2");
		parameters.put("entrust_prop", "0");
		parameters.put("report_seat", order.getPbu());
		parameters.put("exchange_type", exchangeType);
		parameters.put("stock_code", order.getSecurityId());
		parameters.put("report_account", order.getSecurityAccount());
		parameters.put("report_time", created);
		parameters.put("fund_account", order.getAssetAccount());
		parameters.put("client_id", order.getInvestorAccount());
		parameters.put("real_type", "0");
		parameters.put("real_status", "0");
		parameters.put("seat_no", order.getPbu());
		parameters.put("report_no", entrustNo);
		parameters.put("curr_date", today);
		parameters.put("curr_time", now);
		parameters.put("op_remark", "");
		
		records.add("execution:" + parameters.toString());
		
		return HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static IDatasets queryBranchCodes(IClient instance) throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "570482");
		
		return HundsunUtil.invokeHundsunService(instance, property, null);
	}
	
	public static IDatasets querySecuritiesHighLowLimitPrice(IClient instance, Map<String, RefData> records, String hundsunExchangeCode,
			String positionStr) throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "330300");
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("identity_type", "1");
		parameters.put("op_branch_no", "1");
		parameters.put("op_entrust_way", "7");
		parameters.put("op_station", "1234567890");
		parameters.put("query_type", "1");
		parameters.put("exchange_type", hundsunExchangeCode);
		parameters.put("stock_type", "0");
		parameters.put("position_str", positionStr);
		
		return HundsunUtil.invokeHundsunService(instance, property, parameters);
	}
	
	public static IDatasets querySecuritiesShortCode(IClient instance) throws HundsunInvokingException {
		Map<String, Object> property = new HashMap<String, Object>();
		property.put(EventTagdef.TAG_SYSTEM_NO, sysnodeId);
		property.put(EventTagdef.TAG_FUNCTION_ID, "590089");
		
		return HundsunUtil.invokeHundsunService(instance, property, null);
	}
	
	private static IDatasets invokeHundsunService(IClient instance, Map<String, Object> arg0, Map<Object, Object> arg1)
			throws HundsunInvokingException {
		IEvent resultEvent;
		try {
			resultEvent = instance.sendReceive(arg0, arg1, 10000);
		} catch (T2SDKException e) {
			throw new HundsunInvokingException(e);
		}
		
		if (resultEvent.getReturnCode() != EventReturnCode.I_OK)
			throw new HundsunInvokingException(resultEvent.getErrorNo(), resultEvent.getEventDatas());
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
}
