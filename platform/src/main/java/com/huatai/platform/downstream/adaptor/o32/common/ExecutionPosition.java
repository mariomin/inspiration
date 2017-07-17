package com.huatai.platform.downstream.adaptor.o32.common;

public class ExecutionPosition {
	private String assetNo;
	private String operatorNo;
	private String positionStr;
	// 其在DBF文件中的存储位置。
	private int index;
	
	public ExecutionPosition(String assetNo, String operatorNo, String positionStr) {
		super();
		this.assetNo = assetNo;
		this.operatorNo = operatorNo;
		this.positionStr = positionStr;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getAssetNo() {
		return assetNo;
	}
	
	public String getOperatorNo() {
		return operatorNo;
	}
	
	public String getPositionStr() {
		return positionStr;
	}
	
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	
	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}
	
	public void setPositionStr(String positionStr) {
		this.positionStr = positionStr;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExecutionPosition [assetNo=");
		builder.append(assetNo);
		builder.append(", operatorNo=");
		builder.append(operatorNo);
		builder.append(", positionStr=");
		builder.append(positionStr);
		builder.append(", index=");
		builder.append(index);
		builder.append("]");
		return builder.toString();
	}
	
}