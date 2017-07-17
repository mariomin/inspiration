package com.huatai.platform.downstream.adaptor.uf.common;

import org.springframework.beans.factory.annotation.Autowired;

import com.huatai.platform.downstream.adaptor.uf.UFExchangeService;

public class UFBeanHelper {
	private static final UFBeanHelper instance = new UFBeanHelper();
	
	@Autowired
	private UFExchangeService ufExchangeService;
	
	private UFBeanHelper() {}
	
	public static UFBeanHelper getInstance() {
		return instance;
	}
	
	public UFExchangeService getUFExchangeService() {
		return ufExchangeService;
	}
	
}
