package com.huatai.platform.downstream.adaptor.ctp;

import com.huatai.common.ctp.CThostFtdcOrderField;

public interface ICTPTradeListener {
	
	void onLogin(long orderRef, int frontID, int sessionID);
	
	void onOrderConfirmed(CThostFtdcOrderField order);
	
}
