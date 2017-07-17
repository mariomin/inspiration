package com.huatai.platform.downstream.adaptor.uf;

import com.huatai.common.downstream.IDownStreamListener;
import com.huatai.platform.downstream.adaptor.uf.common.UFBeanHelper;
import com.hundsun.mcapi.interfaces.ISubCallback;
import com.hundsun.mcapi.subscribe.MCSubscribeParameter;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

public class UFInvokeCallback implements ISubCallback {
	public static final int RealType_BuySell = 0;
	public static final int RealType_Cancel = 2;
	public static final int RealStatus_Fill = 0;
	public static final int RealStatus_Reject = 2;
	
	public UFInvokeCallback() {}
	
	@Override
	public void OnReceived(String topicName, IEvent event) {
		if ((event.getEventDatas() == null) || (event.getEventDatas().getDatasetCount() == 0)) return;
		IDataset dataset = event.getEventDatas().getDataset(0);
		processEvent(dataset);
	}
	
	private void processEvent(IDataset dataset) {
		int type = dataset.getInt("real_type");
		int status = dataset.getInt("real_status");
		switch (type) {
			case RealType_BuySell:
				switch (status) {
					case RealStatus_Reject:
						processNewOrderReject(dataset);
						break;
					case RealStatus_Fill:
						processPartiallyFilled(dataset);
						break;
					default:
				}
				break;
			case RealType_Cancel:
				switch (status) {
					case RealStatus_Reject:
						processCancelOrderReject(dataset);
						break;
					case RealStatus_Fill:
						processCancelled(dataset);
						break;
					default:
				}
				break;
			default:
		}
		
	}
	
	private void processNewOrderReject(IDataset dataset) {
		int entrustNo = dataset.getInt("entrust_no");
		String exchangeType = dataset.getString("exchange_type");
		String rejectReason = dataset.getString("reject_reason");
		getUFExchangeService().onNewOrderReject(dataset, entrustNo, exchangeType, rejectReason);
	}
	
	private void processCancelOrderReject(IDataset dataset) {
		int entrustNo = dataset.getInt("entrust_no");
		String exchangeType = dataset.getString("exchange_type");
		String rejectReason = dataset.getString("reject_reason");
		getUFExchangeService().onCancelOrderReject(dataset, entrustNo, exchangeType, rejectReason);
	}
	
	private void processPartiallyFilled(IDataset dataset) {
		int entrustNo = dataset.getInt("entrust_no");
		String exchangeType = dataset.getString("exchange_type");
		String execID = dataset.getString("business_id");
		double lastQty = dataset.getDouble("business_amount");
		double lastPx = dataset.getDouble("business_price");
		getUFExchangeService().onPartiallyFilled(dataset, entrustNo, exchangeType, execID, lastQty, lastPx);
	}
	
	private void processCancelled(IDataset dataset) {
		int entrustNo_old = dataset.getInt("entrust_no");
		int entrustNo = dataset.getInt("withdraw_no");
		String exchangeType = dataset.getString("exchange_type");
		double cancelQty = -dataset.getDouble("business_amount");
		getUFExchangeService().onCancelled(dataset, entrustNo, entrustNo_old, exchangeType, cancelQty);
	}
	
	@Override
	public void OnRecvTickMsg(MCSubscribeParameter param, String tickMsgInfo) {}
	
	private UFExchangeService getUFExchangeService() {
		return UFBeanHelper.getInstance().getUFExchangeService();
	}
	
	public void setListener(IDownStreamListener listener) {}
}
