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
 * \u6b63\u5728\u540c\u6b65\u4e2d\u7684\u4ea4\u6613\u4ee3\u7801<br>
 * <i>native declaration : line 8166</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcSyncingTradingCodeField")
@Library("thosttraderapi")
public class CThostFtdcSyncingTradingCodeField extends StructObject {
	static {
		BridJ.register();
	}
	
	/**
	 * \u6295\u8d44\u8005\u4ee3\u7801<br>
	 * C type : TThostFtdcInvestorIDType
	 */
	@Array({ 13 })
	@Field(0)
	public Pointer<Byte> InvestorID() {
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
	 * \u4ea4\u6613\u6240\u4ee3\u7801<br>
	 * C type : TThostFtdcExchangeIDType
	 */
	@Array({ 9 })
	@Field(2)
	public Pointer<Byte> ExchangeID() {
		return io.getPointerField(this, 2);
	}
	
	/**
	 * \u5ba2\u6237\u4ee3\u7801<br>
	 * C type : TThostFtdcClientIDType
	 */
	@Array({ 11 })
	@Field(3)
	public Pointer<Byte> ClientID() {
		return io.getPointerField(this, 3);
	}
	
	/**
	 * \u662f\u5426\u6d3b\u8dc3<br>
	 * C type : TThostFtdcBoolType
	 */
	@Field(4)
	public int IsActive() {
		return io.getIntField(this, 4);
	}
	
	/**
	 * \u662f\u5426\u6d3b\u8dc3<br>
	 * C type : TThostFtdcBoolType
	 */
	@Field(4)
	public CThostFtdcSyncingTradingCodeField IsActive(int IsActive) {
		io.setIntField(this, 4, IsActive);
		return this;
	}
	
	/**
	 * \u4ea4\u6613\u7f16\u7801\u7c7b\u578b<br>
	 * C type : TThostFtdcClientIDTypeType
	 */
	@Field(5)
	public byte ClientIDType() {
		return io.getByteField(this, 5);
	}
	
	/**
	 * \u4ea4\u6613\u7f16\u7801\u7c7b\u578b<br>
	 * C type : TThostFtdcClientIDTypeType
	 */
	@Field(5)
	public CThostFtdcSyncingTradingCodeField ClientIDType(byte ClientIDType) {
		io.setByteField(this, 5, ClientIDType);
		return this;
	}
	
	public CThostFtdcSyncingTradingCodeField() {
		super();
	}
	
	public CThostFtdcSyncingTradingCodeField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
