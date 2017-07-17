package com.huatai.common.business.command;

import java.util.List;

import com.huatai.common.algo.SecurityAccountDetail;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.strategy.StrategyInstruction;

public class StrategyAlterCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;
	private String requestID;
	private String strategy;
	private String strategyId;
	private String strategyParaStr;
	private Object strategyParas;
	private StrategyInstruction strategyInstruction;
	private List<SecurityAccountDetail> securityAccountDetails;
	
	protected StrategyAlterCommand() {
		super();
	}
	
	public StrategyAlterCommand(SecurityTradingIndex index) {
		super(index);
	}
	
	public String getRequestID() {
		return requestID;
	}
	
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	
	public String getStrategy() {
		return strategy;
	}
	
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	
	public String getStrategyId() {
		return strategyId;
	}
	
	public void setStrategyId(String strategyId) {
		this.strategyId = strategyId;
	}
	
	public String getStrategyParaStr() {
		return strategyParaStr;
	}
	
	public void setStrategyParaStr(String strategyParaStr) {
		this.strategyParaStr = strategyParaStr;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getStrategyParas(Class<T> clazz) {
		return (T) strategyParas;
	}
	
	public Object getStrategyParas() {
		return strategyParas;
	}
	
	public void setStrategyParas(Object strategyParas) {
		this.strategyParas = strategyParas;
	}
	
	public StrategyInstruction getStrategyInstruction() {
		return strategyInstruction;
	}
	
	public void setStrategyInstruction(StrategyInstruction strategyInstruction) {
		this.strategyInstruction = strategyInstruction;
	}
	
	public List<SecurityAccountDetail> getSecurityAccountDetails() {
		return securityAccountDetails;
	}
	
	public void setSecurityAccountDetails(List<SecurityAccountDetail> securityAccountDetails) {
		this.securityAccountDetails = securityAccountDetails;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StrategyAlterCommand [requestID=");
		builder.append(requestID);
		builder.append(", transactTime=");
		builder.append(transactTime);
		builder.append(", strategy=");
		builder.append(strategy);
		builder.append(", strategyId=");
		builder.append(strategyId);
		builder.append(", strategyParaStr=");
		builder.append(strategyParaStr);
		builder.append(", strategyParas=");
		builder.append(strategyParas);
		builder.append(", strategyInstruction=");
		builder.append(strategyInstruction);
		builder.append(", BaseCommand=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
