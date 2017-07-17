package com.huatai.common.o32;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.hundsun.O32InvokingException;
import com.huatai.common.o32.account.O32FundDetailsMapping;
import com.huatai.common.o32.account.O32PositionDetailsMapping;
import com.huatai.common.o32.account.O32TradingAccountMapping;
import com.huatai.common.util.O32Util;
import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;

public class O32SynchronizationService {
	private static final Logger log = LoggerFactory.getLogger(O32SynchronizationService.class);
	private T2Services service;
	private IClient instance;
	
	public void init(String config) throws T2SDKException {
		service = T2Services.getInstance();
		service.setT2sdkConfigString(config);
		
		service.init();
		service.start();
		instance = service.getClient("as_ufx");
	}
	
	public void uninit() {
		if (instance != null) {
			instance.stop();
		}
		if (service != null) {
			service.stop();
		}
	}
	
	public String login(String operatorNo, String operatorPassword) {
		try {
			return O32Util.login(instance, operatorNo, operatorPassword);
		} catch (O32InvokingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<O32TradingAccountMapping> queryCombiNo(String userToken, String combiNo) {
		List<O32TradingAccountMapping> o32TradingAccountMappings = new ArrayList<O32TradingAccountMapping>();
		
		try {
			IDatasets datasets = O32Util.queryCombination(instance, userToken, combiNo);
			o32TradingAccountMappings.addAll(O32SynchronizationServiceUtil.parseCombination(datasets));
		} catch (O32InvokingException e) {
			log.warn("failed to query combination: [userToken={}, combiNo={}]", userToken, combiNo);
			O32Util.showPack(e.getDatasets());
		}
		
		return o32TradingAccountMappings;
	}
	
	public O32FundDetailsMapping queryFundAccountDetails(String userToken, String assertNo) {
		IDatasets datasets = null;
		try {
			datasets = O32Util.queryFundAccountDetails(instance, userToken, assertNo);
		} catch (O32InvokingException e) {
			log.warn("failed to query fund account details: [userToken={}]", userToken);
			O32Util.showPack(e.getDatasets());
		}
		
		if (datasets != null) {
			List<O32FundDetailsMapping> mappings = O32SynchronizationServiceUtil.parseFundAccountDetails(datasets);
			if (CollectionUtils.isNotEmpty(mappings)) return mappings.get(0);
		}
		return null;
	}
	
	public List<O32PositionDetailsMapping> queryPositionDetails(String userToken, String combiNo) {
		IDatasets datasets = null;
		try {
			datasets = O32Util.queryPositionDetails(instance, userToken, combiNo);
		} catch (O32InvokingException e) {
			log.warn("failed to query fund account details: [userToken={}]", userToken);
			O32Util.showPack(e.getDatasets());
		}
		
		if (datasets != null) {
			List<O32PositionDetailsMapping> mappings = O32SynchronizationServiceUtil.parsePositionDetails(datasets);
			return mappings;
		}
		return null;
	}
}
