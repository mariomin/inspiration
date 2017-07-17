package com.huatai.common.stream;

import java.util.List;

public interface IStreamAdaptor<T> {
	void init() throws Exception;
	
	void uninit();
	
	List<T> getReceivers();
	
}
