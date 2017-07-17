package com.huatai.common.ctp;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Name;
import org.bridj.cpp.CPPObject;

/**
 * \u4fe1\u606f\u5206\u53d1<br>
 * <i>native declaration : line 6298</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcDisseminationField")
@Library("thosttraderapi")
public class CThostFtdcDisseminationField extends StructObject {
	static {
		BridJ.register();
	}
	
	/**
	 * \u5e8f\u5217\u7cfb\u5217\u53f7<br>
	 * C type : TThostFtdcSequenceSeriesType
	 */
	@Field(0)
	public short SequenceSeries() {
		return io.getShortField(this, 0);
	}
	
	/**
	 * \u5e8f\u5217\u7cfb\u5217\u53f7<br>
	 * C type : TThostFtdcSequenceSeriesType
	 */
	@Field(0)
	public CThostFtdcDisseminationField SequenceSeries(short SequenceSeries) {
		io.setShortField(this, 0, SequenceSeries);
		return this;
	}
	
	/**
	 * \u5e8f\u5217\u53f7<br>
	 * C type : TThostFtdcSequenceNoType
	 */
	@Field(1)
	public int SequenceNo() {
		return io.getIntField(this, 1);
	}
	
	/**
	 * \u5e8f\u5217\u53f7<br>
	 * C type : TThostFtdcSequenceNoType
	 */
	@Field(1)
	public CThostFtdcDisseminationField SequenceNo(int SequenceNo) {
		io.setIntField(this, 1, SequenceNo);
		return this;
	}
	
	public CThostFtdcDisseminationField() {
		super();
	}
	
	public CThostFtdcDisseminationField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
