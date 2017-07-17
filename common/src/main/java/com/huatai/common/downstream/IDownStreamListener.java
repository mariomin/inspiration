package com.huatai.common.downstream;

/**
 * 交易所回调接口。<P>
 *
 * <strong>注意：</strong>上交所的成交回报与申报确认的reportIndex是独立的编号的，需要分别处理;该类的所有方法都携带一个参数唯一标识它对应的报盘
 * @author 010350
 *
 */
public interface IDownStreamListener {
	
	/**
	 * 接收新订单。
	 * @param reportNo 申报编号
	 *
	 */
	public void onNewOrderAccept(String reportNo);
	
	/**
	 * 拒绝新订单。<P>
	 * 解冻本次订单全部资金/证券
	 *  @param reportNo
	 */
	public void onNewOrderReject(String reportNo, String reason);
	
	/**
	 * 拒绝撤单。<P>
	 *
	 * @param reportNo 原委托申报号
	 * @param rejectCode
	 */
	public void onCancelOrderReject(String reportNo, String reason);
	
	/**
	 * 订单撤销成功，可能是全部撤销，也可能是未成交部分撤销
	 *
	 * @param reportNo 原委托申报号
	 * @param cancelQty
	 */
	public void onCancelled(String reportNo, double cancelQty);
	
	/**
	 * 全部成交。
	 * @param reportNo
	 * @param execID 执行编号
	 * @param lastQty 本次成交数量
	 * @param price 本次成交价格
	 */
	public void onFilled(String reportNo, String execID, double lastQty, double price);
	
	/**
	 * 部分成交。
	 * @param reportNo
	 *  @param execID 执行编号
	 * @param lastQty 本次成交数量
	 * @param price 本次成交价格
	 */
	public void onPartiallyFilled(String reportNo, String execID, double lastQty, double price);
	
	public void onDuplicateNewOrder(String reportNo);
	
	public void onDuplicateCancelOrder(String cancelReportNo);
}
