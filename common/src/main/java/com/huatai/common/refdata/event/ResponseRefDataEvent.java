package com.huatai.common.refdata.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.huatai.common.event.base.BaseResponseEvent;
import com.huatai.common.refdata.RefData;

public class ResponseRefDataEvent extends BaseResponseEvent {
	private static final long serialVersionUID = 1L;
	private final List<RefData> refDatas = new ArrayList<RefData>();
	private int offset;
	
	public ResponseRefDataEvent(String key, String requestID, Collection<RefData> refDatas, String text) {
		super(key, requestID);
		setText(text);
		this.refDatas.addAll(refDatas);
	}
	
	public Collection<RefData> getRefDatas() {
		return refDatas;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}
