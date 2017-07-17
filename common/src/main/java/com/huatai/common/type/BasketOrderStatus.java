package com.huatai.common.type;

public enum BasketOrderStatus {
	In_Bidding_Process(1), Received_For_Execution(2), Executing(3), Cancelling(4), Alert(5), All_Done(6), Reject(7);
	private int value;
	
	BasketOrderStatus(int value) {
		this.value = value;
	}
	
	public int value() {
		return value;
	}
}
