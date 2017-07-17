package com.huatai.common.event.order;

import com.huatai.common.business.Order;
import com.huatai.common.data.DataObject;

public final class NewOrderReplyEvent extends OrderReplyEvent {
	private static final long serialVersionUID = 1L;
	private DataObject dataObject;
	
	public NewOrderReplyEvent(String key, String receiver, boolean ok, String message, String clOrdId, Order order) {
		super(key, receiver, ok, message, clOrdId, order);
	}
	
	public void setDataObject(DataObject dataObject) {
		this.dataObject = dataObject;
	}
	
	public DataObject getDataObject() {
		return dataObject;
	}
	
}
