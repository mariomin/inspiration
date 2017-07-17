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
 * \u67e5\u8be2\u7ecf\u7eaa\u516c\u53f8\u4f1a\u5458\u4ee3\u7801<br>
 * <i>native declaration : line 8611</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcQryPartBrokerField")
@Library("thosttraderapi")
public class CThostFtdcQryPartBrokerField extends StructObject {
	static {
		BridJ.register();
	}
	
	/**
	 * \u4ea4\u6613\u6240\u4ee3\u7801<br>
	 * C type : TThostFtdcExchangeIDType
	 */
	@Array({ 9 })
	@Field(0)
	public Pointer<Byte> ExchangeID() {
		return io.getPointerField(this, 0);
	}
	
	/**
	 * \u7ecf\u7eaa\u516c\u53f8\u4ee3\u7801<br>
	 * C type : TThostFtdcBrokerIDType
	 */
	@Array({ 11 })
	@Field(1)
	public Pointer<Byte> BrokerID() {
		return io.getPointerField(this, 1);
	}
	
	/**
	 * \u4f1a\u5458\u4ee3\u7801<br>
	 * C type : TThostFtdcParticipantIDType
	 */
	@Array({ 11 })
	@Field(2)
	public Pointer<Byte> ParticipantID() {
		return io.getPointerField(this, 2);
	}
	
	public CThostFtdcQryPartBrokerField() {
		super();
	}
	
	public CThostFtdcQryPartBrokerField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
