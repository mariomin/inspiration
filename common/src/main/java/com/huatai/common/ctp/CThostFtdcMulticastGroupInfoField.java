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
 * UDP\u7ec4\u64ad\u7ec4\u4fe1\u606f<br>
 * <i>native declaration : line 13928</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcMulticastGroupInfoField")
@Library("thosttraderapi")
public class CThostFtdcMulticastGroupInfoField extends StructObject {
	static {
		BridJ.register();
	}
	
	/**
	 * \u7ec4\u64ad\u7ec4IP\u5730\u5740<br>
	 * C type : TThostFtdcIPAddressType
	 */
	@Array({ 16 })
	@Field(0)
	public Pointer<Byte> GroupIP() {
		return io.getPointerField(this, 0);
	}
	
	/**
	 * \u7ec4\u64ad\u7ec4IP\u7aef\u53e3<br>
	 * C type : TThostFtdcIPPortType
	 */
	@Field(1)
	public int GroupPort() {
		return io.getIntField(this, 1);
	}
	
	/**
	 * \u7ec4\u64ad\u7ec4IP\u7aef\u53e3<br>
	 * C type : TThostFtdcIPPortType
	 */
	@Field(1)
	public CThostFtdcMulticastGroupInfoField GroupPort(int GroupPort) {
		io.setIntField(this, 1, GroupPort);
		return this;
	}
	
	/**
	 * \u6e90\u5730\u5740<br>
	 * C type : TThostFtdcIPAddressType
	 */
	@Array({ 16 })
	@Field(2)
	public Pointer<Byte> SourceIP() {
		return io.getPointerField(this, 2);
	}
	
	public CThostFtdcMulticastGroupInfoField() {
		super();
	}
	
	public CThostFtdcMulticastGroupInfoField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}