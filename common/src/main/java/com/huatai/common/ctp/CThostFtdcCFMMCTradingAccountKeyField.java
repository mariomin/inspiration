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
 * \u4fdd\u8bc1\u91d1\u76d1\u7ba1\u7cfb\u7edf\u7ecf\u7eaa\u516c\u53f8\u8d44\u91d1\u8d26\u6237\u5bc6\u94a5<br>
 * <i>native declaration : line 11553</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcCFMMCTradingAccountKeyField")
@Library("thosttraderapi")
public class CThostFtdcCFMMCTradingAccountKeyField extends StructObject {
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
	 * \u7ecf\u7eaa\u516c\u53f8\u7edf\u4e00\u7f16\u7801<br>
	 * C type : TThostFtdcParticipantIDType
	 */
	@Array({ 11 })
	@Field(1)
	public Pointer<Byte> ParticipantID() {
		return io.getPointerField(this, 1);
	}
	
	/**
	 * \u6295\u8d44\u8005\u5e10\u53f7<br>
	 * C type : TThostFtdcAccountIDType
	 */
	@Array({ 13 })
	@Field(2)
	public Pointer<Byte> AccountID() {
		return io.getPointerField(this, 2);
	}
	
	/**
	 * \u5bc6\u94a5\u7f16\u53f7<br>
	 * C type : TThostFtdcSequenceNoType
	 */
	@Field(3)
	public int KeyID() {
		return io.getIntField(this, 3);
	}
	
	/**
	 * \u5bc6\u94a5\u7f16\u53f7<br>
	 * C type : TThostFtdcSequenceNoType
	 */
	@Field(3)
	public CThostFtdcCFMMCTradingAccountKeyField KeyID(int KeyID) {
		io.setIntField(this, 3, KeyID);
		return this;
	}
	
	/**
	 * \u52a8\u6001\u5bc6\u94a5<br>
	 * C type : TThostFtdcCFMMCKeyType
	 */
	@Array({ 21 })
	@Field(4)
	public Pointer<Byte> CurrentKey() {
		return io.getPointerField(this, 4);
	}
	
	public CThostFtdcCFMMCTradingAccountKeyField() {
		super();
	}
	
	public CThostFtdcCFMMCTradingAccountKeyField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}