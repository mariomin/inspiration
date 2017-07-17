package com.huatai.common.hundsun;

import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;

public class O32InvokingException extends T2SDKException {
	private static final long serialVersionUID = 1L;
	private IDatasets datasets;
	
	public O32InvokingException(T2SDKException e) {
		super(e.getErrorNo(), e.getErrorInfo());
	}
	
	public O32InvokingException(String errorNo, IDatasets datasets) {
		super(errorNo);
		this.datasets = datasets;
	}
	
	public IDatasets getDatasets() {
		return datasets;
	}
	
}
