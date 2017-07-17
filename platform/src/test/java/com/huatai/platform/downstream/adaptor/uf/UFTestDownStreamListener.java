package com.huatai.platform.downstream.adaptor.uf;

import com.huatai.common.downstream.IDownStreamListener;

public class UFTestDownStreamListener implements IDownStreamListener {
	
	@Override
	public void onNewOrderAccept(String reportNo) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onNewOrderReject(String reportNo, String reason) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onCancelOrderReject(String reportNo, String reason) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onCancelled(String reportNo, double cancelQty) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onFilled(String reportNo, String execID, double lastQty, double price) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onPartiallyFilled(String reportNo, String execID, double lastQty, double price) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onDuplicateNewOrder(String reportNo) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onDuplicateCancelOrder(String cancelReportNo) {
		// TODO Auto-generated method stub
		
	}
	
}
