package com.huatai.common.o32;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.huatai.usercenter.api.xusers.IXUsersManagerService;
import com.huatai.usercenter.api.xusers.vo.O32UsrBinding;

public class AccountServiceHelper {
	private static final AccountServiceHelper instance = new AccountServiceHelper();
	
	@Autowired
	private IXUsersManagerService usersManagerService;
	
	private final Map<String, String> operatorNo2Passwd = new ConcurrentHashMap<String, String>();
	
	private AccountServiceHelper() {}
	
	public static final AccountServiceHelper getInstance() {
		return instance;
	}
	
	public String getPasswdByOperaterNo(String operatorNo) {
		String passwd = operatorNo2Passwd.get(operatorNo);
		if (null == passwd) {
			O32UsrBinding binding = usersManagerService.getO32UsrBindingByOperName(operatorNo);
			if ((null != binding) && (binding.getO32OperatorPassword() != null)) {
				passwd = binding.getO32OperatorPassword();
				operatorNo2Passwd.put(operatorNo, passwd);
			}
		}
		
		if (null == passwd) throw new IllegalArgumentException("Can not find passwd by operatorNo:" + operatorNo);
		
		return passwd;
	}
}
