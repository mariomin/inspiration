package com.huatai.platform.downstream.adaptor.o32.common;

import org.springframework.beans.factory.annotation.Autowired;

import com.huatai.platform.downstream.adaptor.o32.O32ExchangeService;

public class O32BeanHelper {
	private static final O32BeanHelper instance = new O32BeanHelper();
	
	@Autowired
	private O32ExchangeService o32ExchangeService;
	
	private O32BeanHelper() {}
	
	public static O32BeanHelper getInstance() {
		return instance;
	}
	
	public O32ExchangeService getO32ExchangeService() {
		return o32ExchangeService;
	}
	
	public void setO32ExchangeService(O32ExchangeService o32ExchangeService) {
		this.o32ExchangeService = o32ExchangeService;
	}
	
}
