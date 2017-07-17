package com.huatai.common;

import org.springframework.beans.factory.BeanNameAware;

public interface IPlugin extends BeanNameAware {
	void init() throws Exception;
	
	void recover();
	
	void uninit();
	
	String getBeanName();
	
}
