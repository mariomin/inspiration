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
 * \u4e8c\u7ea7\u4ee3\u7406\u64cd\u4f5c\u5458\u94f6\u671f\u6743\u9650<br>
 * <i>native declaration : line 13827</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcSecAgentACIDMapField")
@Library("thosttraderapi")
public class CThostFtdcSecAgentACIDMapField extends StructObject {
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
	 * \u8d44\u91d1\u8d26\u6237<br>
	 * C type : TThostFtdcAccountIDType
	 */
	@Array({ 13 })
	@Field(2)
	public Pointer<Byte> AccountID() {
		return io.getPointerField(this, 2);
	}
	
	/**
	 * \u5e01\u79cd<br>
	 * C type : TThostFtdcCurrencyIDType
	 */
	@Array({ 4 })
	@Field(3)
	public Pointer<Byte> CurrencyID() {
		return io.getPointerField(this, 3);
	}
	
	/**
	 * \u5883\u5916\u4e2d\u4ecb\u673a\u6784\u8d44\u91d1\u5e10\u53f7<br>
	 * C type : TThostFtdcAccountIDType
	 */
	@Array({ 13 })
	@Field(4)
	public Pointer<Byte> BrokerSecAgentID() {
		return io.getPointerField(this, 4);
	}
	
	public CThostFtdcSecAgentACIDMapField() {
		super();
	}
	
	public CThostFtdcSecAgentACIDMapField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
