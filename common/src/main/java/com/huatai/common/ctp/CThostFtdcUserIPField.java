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
 * \u7528\u6237IP<br>
 * <i>native declaration : line 11073</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcUserIPField")
@Library("thosttraderapi")
public class CThostFtdcUserIPField extends StructObject {
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
	 * IP\u5730\u5740<br>
	 * C type : TThostFtdcIPAddressType
	 */
	@Array({ 16 })
	@Field(2)
	public Pointer<Byte> IPAddress() {
		return io.getPointerField(this, 2);
	}
	
	/**
	 * IP\u5730\u5740\u63a9\u7801<br>
	 * C type : TThostFtdcIPAddressType
	 */
	@Array({ 16 })
	@Field(3)
	public Pointer<Byte> IPMask() {
		return io.getPointerField(this, 3);
	}
	
	/**
	 * Mac\u5730\u5740<br>
	 * C type : TThostFtdcMacAddressType
	 */
	@Array({ 21 })
	@Field(4)
	public Pointer<Byte> MacAddress() {
		return io.getPointerField(this, 4);
	}
	
	public CThostFtdcUserIPField() {
		super();
	}
	
	public CThostFtdcUserIPField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
