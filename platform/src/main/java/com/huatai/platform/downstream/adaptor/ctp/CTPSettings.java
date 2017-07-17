package com.huatai.platform.downstream.adaptor.ctp;

public class CTPSettings {
	private String serverAddress;
	private String brokerID;
	private String userID;
	private String password;
	private String userProductInfo;
	private String authCode;
	
	public String getServerAddress() {
		return serverAddress;
	}
	
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	
	public String getBrokerID() {
		return brokerID;
	}
	
	public void setBrokerID(String brokerID) {
		this.brokerID = brokerID;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserProductInfo() {
		return userProductInfo;
	}
	
	public void setUserProductInfo(String userProductInfo) {
		this.userProductInfo = userProductInfo;
	}
	
	public String getAuthCode() {
		return authCode;
	}
	
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
}
