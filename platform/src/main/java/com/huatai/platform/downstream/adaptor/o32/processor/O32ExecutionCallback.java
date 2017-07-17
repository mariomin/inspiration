package com.huatai.platform.downstream.adaptor.o32.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.o32.tags.O32Tags;
import com.huatai.platform.downstream.adaptor.o32.O32ExchangeService;
import com.huatai.platform.downstream.adaptor.o32.common.O32BeanHelper;
import com.huatai.platform.downstream.adaptor.o32.common.O32Util;
import com.hundsun.mcapi.interfaces.ISubCallback;
import com.hundsun.mcapi.subscribe.MCSubscribeParameter;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

/**
 * O32委托与成交的回调处理类
 * @author 010350
 *
 */
public class O32ExecutionCallback implements ISubCallback {
	private static final Logger logger = LoggerFactory.getLogger(O32ExecutionCallback.class);
	
	@Override
	public void OnReceived(String topic, IEvent event) {
		
		logger.debug("Receive O32ExecutionCallback response:{}", O32Util.toString(event));
		try {
			IDataset dataset = event.getEventDatas().getDataset(0);
			if (null == dataset) return;
			String msgType = dataset.getString("msgtype");
			switch (msgType) {
				case "a": // 委托下达
					break;
				case "b": // 委托确认
					onNew(event);
					break;
				case "c": // 委托废单
					onNewOrderReject(event);
					break;
				case "d": // 委托撤单
					break;
				case "e": // 委托撤成
					onCancelled(event);
					break;
				case "f": // 委托撤废
					onCancelOrderReject(event);
					break;
				case "g": // 委托成交
					onPartiallyFilled(event);
					break;
				default:
					break;
			}
			
		} catch (RuntimeException e) {
			logger.error("Error occurs", e);
		}
	}
	
	private void onNew(IEvent event) {
		IDataset dataset = event.getEventDatas().getDataset(0);
		String extSystemId = dataset.getString(O32Tags.Field.extsystemId);
		getDownStreamListener().onNewOrderAccept(extSystemId);
		
	}
	
	private void onPartiallyFilled(IEvent event) {
		IDataset dataset = event.getEventDatas().getDataset(0);
		String reportNo = dataset.getString(O32Tags.Field.extsystemId);
		String execId = dataset.getString(O32Tags.Field.dealNo);
		String dealAmountStr = dataset.getString(O32Tags.Field.dealAmount);
		String dealPriceStr = dataset.getString(O32Tags.Field.dealPrice);
		
		getDownStreamListener().onPartiallyFilled(reportNo, execId, Double.valueOf(dealAmountStr), Double.valueOf(dealPriceStr));
	}
	
	private void onCancelOrderReject(IEvent event) {
		
		IDataset dataset = event.getEventDatas().getDataset(0);
		
		String reportNo = dataset.getString(O32Tags.Field.extsystemId);
		String reason = dataset.getString(O32Tags.Field.revokeCause);
		
		getDownStreamListener().onCancelOrderReject(reportNo, reason);
		
	}
	
	private void onCancelled(IEvent event) {
		IDataset dataset = event.getEventDatas().getDataset(0);
		
		String cancelAmountStr = dataset.getString(O32Tags.Field.cancelAmount);
		double cancelAmount = Double.valueOf(cancelAmountStr);
		
		String reportNo = dataset.getString(O32Tags.Field.extsystemId);
		getDownStreamListener().onCancelled(reportNo, cancelAmount);
	}
	
	private void onNewOrderReject(IEvent event) {
		IDataset dataset = event.getEventDatas().getDataset(0);
		String reportNo = dataset.getString(O32Tags.Field.extsystemId);
		String errorMessage = dataset.getString(O32Tags.Field.revokeCause);
		getDownStreamListener().onNewOrderReject(reportNo, errorMessage);
	}
	
	private O32ExchangeService getDownStreamListener() {
		return O32BeanHelper.getInstance().getO32ExchangeService();
	}
	
	@Override
	public void OnRecvTickMsg(MCSubscribeParameter arg0, String arg1) {}
}
