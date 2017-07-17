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
 * \u7ecf\u7eaa\u516c\u53f8\u7528\u6237\u529f\u80fd\u6743\u9650<br>
 * <i>native declaration : line 7215</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcBrokerUserFunctionField")
@Library("thosttraderapi")
public class CThostFtdcBrokerUserFunctionField extends StructObject {
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
	 * \u7528\u6237\u4ee3\u7801<br>
	 * C type : TThostFtdcUserIDType
	 */
	@Array({ 16 })
	@Field(1)
	public Pointer<Byte> UserID() {
		return io.getPointerField(this, 1);
	}
	
	/**
	 * \u7ecf\u7eaa\u516c\u53f8\u529f\u80fd\u4ee3\u7801<br>
	 * C type : TThostFtdcBrokerFunctionCodeType
	 */
	@Field(2)
	public byte BrokerFunctionCode() {
		return io.getByteField(this, 2);
	}
	
	/**
	 * \u7ecf\u7eaa\u516c\u53f8\u529f\u80fd\u4ee3\u7801<br>
	 * C type : TThostFtdcBrokerFunctionCodeType
	 */
	@Field(2)
	public CThostFtdcBrokerUserFunctionField BrokerFunctionCode(byte BrokerFunctionCode) {
		io.setByteField(this, 2, BrokerFunctionCode);
		return this;
	}
	
	public CThostFtdcBrokerUserFunctionField() {
		super();
	}
	
	public CThostFtdcBrokerUserFunctionField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
