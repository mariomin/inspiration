package com.huatai.platform.downstream.adaptor.o32.common;

import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;

public class O32OrderUtil {
	
	public static O32NewOrderResponse updateNewOrderResponse(IDatasets datasets) {
		String errorCode = datasets.getDataset(0).getString("ErrorCode");
		O32NewOrderResponse o32NewOrderResponse = new O32NewOrderResponse();
		if ("0".equals(errorCode)) {
			IDataset dataset = datasets.getDataset(1);
			String entrustNo = dataset.getString("entrust_no");
			o32NewOrderResponse.setEntrustNo(entrustNo);
			return o32NewOrderResponse;
			
		} else {
			String errorMessage = datasets.getDataset(0).getString("ErrorMsg");
			o32NewOrderResponse.setErrorCode(Integer.valueOf(errorCode));
			o32NewOrderResponse.setErrorMsg(errorMessage);
			return o32NewOrderResponse;
		}
	}
	
	public static O32CancelOrderResponse updateCancelOrderResponse(IDatasets datasets) {
		String errorCode = datasets.getDataset(0).getString("ErrorCode");
		O32CancelOrderResponse response = new O32CancelOrderResponse();
		if ("0".equals(errorCode)) {
			IDataset dataset = datasets.getDataset(1);
			String flag = dataset.getString("success_flag");
			String failCause = dataset.getString("fail_cause");
			response.setSuccessFlag(flag);
			response.setFailCause(failCause);
		} else {
			String errorMessage = datasets.getDataset(0).getString("ErrorMsg");
			response.setErrorCode(Integer.valueOf(errorCode));
			response.setErrorMsg(errorMessage);
		}
		
		return response;
	}
}
