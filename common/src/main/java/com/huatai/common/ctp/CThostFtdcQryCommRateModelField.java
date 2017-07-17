package com.huatai.common.ctp;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Name;
import org.bridj.cpp.CPPObject;

/**
 * \u8bf7\u6c42\u67e5\u8be2\u6295\u8d44\u8005\u624b\u7eed\u8d39\u7387\u6a21\u677f<br>
 * <i>native declaration : line 11624</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcQryCommRateModelField")
@Library("thosttraderapi")
public class CThostFtdcQryCommRateModelField extends StructObject {
	static {
		BridJ.register();
	}
	
	/**
	 * \u7ecf\u7eaa\u516c\u53f8\u4ee3\u7801<br>
	 * C type : TThostFtdcBrokerIDType
	 */
	@Array({ 11 })
	@Field(0)
	public Pointer<Byte> BrokerID() {
		return io.getPointerField(this, 0);
	}
	
	/**
	 * \u624b\u7eed\u8d39\u7387\u6a21\u677f\u4ee3\u7801<br>
	 * C type : TThostFtdcInvestorIDType
	 */
	@Array({ 13 })
	@Field(1)
	public Pointer<Byte> CommModelID() {
		return io.getPointerField(this, 1);
	}
	
	public CThostFtdcQryCommRateModelField() {
		super();
	}
	
	public CThostFtdcQryCommRateModelField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
