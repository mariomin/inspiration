package com.huatai.platform.downstream.adaptor.o32;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.EventReturnCode;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

@Ignore
public class O32Test {
	
	private O32TmpService o32Service;
	
	@Before
	public void setUp() throws Exception {
		o32Service = new O32TmpService();
		o32Service.setServerName("as_ufx");
		o32Service.setConfig("classpath:conf/common/o32/o32.xml");
		o32Service.init();
	}
	
	@Test
	public void testLogin() throws T2SDKException {
		IEvent response = o32Service.login("3162", "zy333");
		showPack("10001", response.getEventDatas());
	}
	
	@Test
	public void testNewOrder() throws Exception {
		String clOrdId = "12345678";
		IEvent response = o32Service.login("3162", "zy333");
		//showPack("10001", response.getEventDatas());
		
		if (response.getReturnCode() == EventReturnCode.I_OK) {
			String userToken = response.getEventDatas().getDataset(1).getString("user_token");
			System.out.println("user_token=" + userToken);
			
			IEvent event = o32Service.newOrder(userToken, clOrdId);
			showPack("91001", event.getEventDatas());
		} else {
			System.out.println("login fail");
		}
		
	}
	
	@Test
	public void testCancelOrder() throws T2SDKException {
		IEvent response = o32Service.login("3162", "zy333");
		//showPack("10001", response.getEventDatas());
		
		if (response.getReturnCode() == EventReturnCode.I_OK) {
			String userToken = response.getEventDatas().getDataset(1).getString("user_token");
			System.out.println("user_token=" + userToken);
			
			IEvent event = o32Service.cancelOrder(userToken, "683625");
			showPack("91101", event.getEventDatas());
		} else {
			System.out.println("login fail");
		}
	}
	
	@Test
	public void testQueryEntrustByEntrustNo() throws T2SDKException {
		String entrustNo = "683625";
		
		IEvent response = o32Service.login("3162", "zy333");
		showPack("10001", response.getEventDatas());
		
		if (response.getReturnCode() == EventReturnCode.I_OK) {
			String errorCode = response.getEventDatas().getDataset(0).getString("ErrorCode");
			System.out.println("errorCode=" + errorCode);
			if ("0".equals(errorCode)) {
				String userToken = response.getEventDatas().getDataset(1).getString("user_token");
				System.out.println("user_token=" + userToken);
				
				IEvent event = o32Service.queryEntrustByEntrustNo(userToken, entrustNo);
				IDataset result = event.getEventDatas().getDataset(1);
				String entrustStatus = result.getString("entrust_state");
				System.out.println("entrust_state=" + entrustStatus);
				showPack("33001", event.getEventDatas());
			} else {
				System.out.println("login fail");
			}
			
		} else {
			System.out.println("login fail");
		}
	}
	
	@Test
	public void testQueryEntrust() throws T2SDKException {
		
		IEvent response = o32Service.login("3162", "zy333");
		showPack("10001", response.getEventDatas());
		
		if (response.getReturnCode() == EventReturnCode.I_OK) {
			String errorCode = response.getEventDatas().getDataset(0).getString("ErrorCode");
			System.out.println("errorCode=" + errorCode);
			if ("0".equals(errorCode)) {
				String userToken = response.getEventDatas().getDataset(1).getString("user_token");
				System.out.println("user_token=" + userToken);
				
				IEvent event = o32Service.queryEntrust(userToken, "0");
				showPack("33001", event.getEventDatas());
			} else {
				System.out.println("login fail");
			}
			
		} else {
			System.out.println("login fail");
		}
	}
	
	/**
	 * @throws T2SDKException
	 * @throws InterruptedException
	 *
	 */
	@Test
	public void testEntrustAndQuery() throws T2SDKException, InterruptedException {
		String position = "0";
		String userToken = "";
		/*
		 * 查委托
		 */
		IEvent response = o32Service.login("3162", "zy333");
		showPack("10001", response.getEventDatas());
		
		if (response.getReturnCode() == EventReturnCode.I_OK) {
			String errorCode = response.getEventDatas().getDataset(0).getString("ErrorCode");
			System.out.println("errorCode=" + errorCode);
			if ("0".equals(errorCode)) {
				userToken = response.getEventDatas().getDataset(1).getString("user_token");
				System.out.println("user_token=" + userToken);
				
				IEvent event = o32Service.queryEntrust(userToken, position);
				showPack("33001", event.getEventDatas());
				position = event.getEventDatas().getDataset(1).getString("position_str");
				System.out.println("position=" + position);
			} else {
				System.out.println("login fail");
			}
			
		} else {
			System.out.println("login fail");
		}
		
		/*
		 * 下委托
		 */
		
		String clOrdId = "12345678";
		
		IEvent event = o32Service.newOrder(userToken, clOrdId);
		showPack("91001", event.getEventDatas());
		String entrustID = event.getEventDatas().getDataset(1).getString("entrust_no");
		System.out.println("entrust_no=" + entrustID);
		Thread.sleep(5000);
		
		/*
		 * 查委托
		 */
		//		System.out.println("query entrust again");
		//		event = o32Service.queryEntrust(userToken, position);
		//		showPack("33001", event.getEventDatas());
		//		position = event.getEventDatas().getDataset(1).getString("position_str");
		//		System.out.println("position=" + position);
		
		/*
		 * 撤单
		 */
		
		o32Service.cancelOrder(userToken, entrustID);
		showPack("91001", event.getEventDatas());
		
		Thread.sleep(5000);
		
		/*
		 * 查委托
		 */
		System.out.println("query entrust again after cancel");
		event = o32Service.queryEntrust(userToken, position);
		showPack("33001", event.getEventDatas());
		position = event.getEventDatas().getDataset(1).getString("position_str");
		System.out.println("position=" + position);
		
	}
	
	@Test
	public void testQueryExecution() throws T2SDKException {
		IEvent response = o32Service.login("3162", "zy333");
		showPack("10001", response.getEventDatas());
		
		if (response.getReturnCode() == EventReturnCode.I_OK) {
			String errorCode = response.getEventDatas().getDataset(0).getString("ErrorCode");
			System.out.println("errorCode=" + errorCode);
			if ("0".equals(errorCode)) {
				String userToken = response.getEventDatas().getDataset(1).getString("user_token");
				System.out.println("user_token=" + userToken);
				
				IEvent event = o32Service.queryExecution(userToken, "0");
				showPack("33001", event.getEventDatas());
			} else {
				System.out.println("login fail");
			}
			
		} else {
			System.out.println("login fail");
		}
	}
	
	private void showPack(String functionNo, IDatasets result) {
		StringBuilder strBuf = new StringBuilder();
		strBuf.append("\"" + functionNo + "\"\n");
		// 获得结果集
		
		// 获得结果集总数
		int datasetCount = result.getDatasetCount();
		// 遍历所有的结果集
		
		for (int i = 0; i < datasetCount; i++) {
			// 开始读取单个结果集的信息
			IDataset ds = result.getDataset(i);
			int columnCount = ds.getColumnCount();
			// 遍历单个结果集列信息
			for (int j = 1; j <= columnCount; j++) {
				strBuf.append("\"" + ds.getColumnName(j) + "\",");
			}
			strBuf.deleteCharAt(strBuf.length() - 1);
			strBuf.append("\n");
			
			// 遍历单个结果集记录，遍历前首先将指针置到开始
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
		//log.info(strBuf.toString());
		System.out.println(strBuf.toString());
	}
	
}
