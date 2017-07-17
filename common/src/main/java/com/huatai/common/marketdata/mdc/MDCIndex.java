/**
 * ATS_Platform
 */
package com.huatai.common.marketdata.mdc;

import com.huatai.common.marketdata.Quote;

/**
 * @author : 朱亚忱
 * 概要：数据中心的指数行情
 */
public class MDCIndex extends Quote {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 构造函数
	 * @param symbol
	 */
	public MDCIndex(String symbol) {
		super(symbol);
	}
	
}
