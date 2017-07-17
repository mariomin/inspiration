package com.huatai.common.downstream;

import com.huatai.common.business.ExchangeOrder;

public interface IDownStreamSender extends IDownStreamService {
	
	/**
	 * 发送新订单
	 * @param order
	 * @throws DownStreamException
	 */
	void newOrder(ExchangeOrder order);
	
	/**
	 * 对原订单进行撤单
	 * @param order
	 * @throws DownStreamException
	 */
	void cancelOrder(ExchangeOrder order);
	
	/**
	 * 新增申报号。<P>
	 * 如果已存在则返回false,如果不存在则添加该申报号并返回true
	 * @param reportNo
	 * @return
	 */
	boolean addReportNoIfAbsent(String reportNo);
}
