package com.huatai.common.account;

import java.io.Serializable;

import com.huatai.common.type.AccountStatus;

public class BaseAccountObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private AccountStatus status = AccountStatus.Good;
	
	public AccountStatus getStatus() {
		return status;
	}
	
	public void setStatus(AccountStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseAccountObject [status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
}
