package com.huatai.common.hundsun;

import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;

public class HundsunInvokingException extends T2SDKException {
	private static final long serialVersionUID = 1L;
	private IDatasets datasets;
	
	public HundsunInvokingException(T2SDKException e) {
		super(e.getErrorNo(), e.getErrorInfo());
	}
	
	public HundsunInvokingException(String errorNo, IDatasets datasets) {
		super(errorNo);
		this.datasets = datasets;
	}
	
	public IDatasets getDatasets() {
		return datasets;
	}
	
}
