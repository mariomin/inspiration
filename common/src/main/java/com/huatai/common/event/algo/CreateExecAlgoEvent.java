package com.huatai.common.event.algo;

import com.huatai.common.business.AlgoOrder;
import com.huatai.common.event.base.AsyncEvent;

public class CreateExecAlgoEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final Object strategyParas;
	private final AlgoOrder algoOrder;
	
	public CreateExecAlgoEvent(String key, AlgoOrder algoOrder, Object strategyParas) {
		super(key);
		this.algoOrder = algoOrder;
		this.strategyParas = strategyParas;
	}
	
	public Object getStrategyParas() {
		return strategyParas;
	}
	
	public AlgoOrder getAlgoOrder() {
		return algoOrder;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CreateExecutionAlgoEvent [algoOrder=");
		builder.append(algoOrder);
		builder.append(",strategyParas");
		builder.append(strategyParas);
		builder.append("]");
		return builder.toString();
	}
}
