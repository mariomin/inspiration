package com.huatai.platform.downstream.adaptor.o32.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.o32.tags.O32Tags;
import com.huatai.common.o32.tags.O32Tags.Field;
import com.huatai.common.o32.tags.O32Tags.FunctionId;
import com.huatai.platform.downstream.adaptor.o32.common.O32BeanHelper;
import com.huatai.platform.downstream.adaptor.o32.common.O32Util;
import com.hundsun.t2sdk.impl.client.ClientSocket;
import com.hundsun.t2sdk.interfaces.ICallBackMethod;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

public class O32CallBackMethod implements ICallBackMethod {
	
	private static final Logger logger = LoggerFactory.getLogger(O32CallBackMethod.class);
	
	public static final String SENDER_ID = "1";
	
	@Override
	public void execute(IEvent event, ClientSocket clientsocket) {
		logger.debug("Receive CallBackMethod response:{}", O32Util.toString(event));
		
		try {
			String functionId = event.getServiceAlias();
			if (FunctionId.newOrderSingle.equals(functionId)) {
				processNewOrderResponse(event);
			} else if (FunctionId.cancelOrder.equals(functionId)) {
				processCancelOrderResponse(event);
			} else {
				logger.error("Unsupport functionId" + functionId);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	private void processCancelOrderResponse(IEvent event) {
		if (!isSuccess(event)) {
			String reportNo = event.getStringAttributeValue(EventTagdef.TAG_SEQUECE_NO);
			IDataset dataset = event.getEventDatas().getDataset(0);
			String errorMsg = dataset.getString(Field.errorMsg);
			O32BeanHelper.getInstance().getO32ExchangeService().onCancelOrderReject(reportNo, errorMsg);
		} else if (O32Tags.Value.SuccessFlag.fail.equals(event.getEventDatas().getDataset(1).getString(Field.successFlag))) {
			String reportNo = event.getStringAttributeValue(EventTagdef.TAG_SEQUECE_NO);
			IDataset dataset = event.getEventDatas().getDataset(1);
			String errorMsg = dataset.getString(Field.failCause);
			O32BeanHelper.getInstance().getO32ExchangeService().onCancelOrderReject(reportNo, errorMsg);
		}
	}
	
	private void processNewOrderResponse(IEvent event) {
		
		if (!isSuccess(event)) {
			String reportNo = event.getStringAttributeValue(EventTagdef.TAG_SEQUECE_NO);
			IDataset dataset = event.getEventDatas().getDataset(0);
			String errorMsg = dataset.getString(Field.errorMsg);
			O32BeanHelper.getInstance().getO32ExchangeService().onNewOrderReject(reportNo, errorMsg);
		} else {
			String reportNo = event.getStringAttributeValue(EventTagdef.TAG_SEQUECE_NO);
			IDataset dataset = event.getEventDatas().getDataset(1);
			String entrustNo = dataset.getString(O32Tags.Field.entrustNo);
			O32DataCache.getInstance().putEntrustNo(reportNo, entrustNo);
		}
	}
	
	private boolean isSuccess(IEvent event) {
		String errorCode = event.getEventDatas().getDataset(0).getString(Field.errorCode);
		return "0".equals(errorCode);
	}
	
}
