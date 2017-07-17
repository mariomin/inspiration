package com.huatai.platform.algo;

import com.huatai.common.algo.ISignalAnalyzer;

public abstract class SignalAnalyzer<V> implements ISignalAnalyzer<V> {
	protected EsperEngine esperEngine;
	
	public void setEsperEngine(EsperEngine esperEngine) {
		this.esperEngine = esperEngine;
	}
}
