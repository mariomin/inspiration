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
 * \u7528\u6237\u767b\u5f55\u8bf7\u6c42<br>
 * <i>native declaration : line 6307</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcReqUserLoginField")
@Library("thosttraderapi")
public class CThostFtdcReqUserLoginField extends StructObject {
	static {
		BridJ.register();
	}
	
	/**
	 * \u4ea4\u6613\u65e5<br>
	 * C type : TThostFtdcDateType
	 */
	@Array({ 9 })
	@Field(0)
	public Pointer<Byte> TradingDay() {
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
	 * \u7528\u6237\u4ee3\u7801<br>
	 * C type : TThostFtdcUserIDType
	 */
	@Array({ 16 })
	@Field(2)
	public Pointer<Byte> UserID() {
		return io.getPointerField(this, 2);
	}
	
	/**
	 * \u5bc6\u7801<br>
	 * C type : TThostFtdcPasswordType
	 */
	@Array({ 41 })
	@Field(3)
	public Pointer<Byte> Password() {
		return io.getPointerField(this, 3);
	}
	
	/**
	 * \u7528\u6237\u7aef\u4ea7\u54c1\u4fe1\u606f<br>
	 * C type : TThostFtdcProductInfoType
	 */
	@Array({ 11 })
	@Field(4)
	public Pointer<Byte> UserProductInfo() {
		return io.getPointerField(this, 4);
	}
	
	/**
	 * \u63a5\u53e3\u7aef\u4ea7\u54c1\u4fe1\u606f<br>
	 * C type : TThostFtdcProductInfoType
	 */
	@Array({ 11 })
	@Field(5)
	public Pointer<Byte> InterfaceProductInfo() {
		return io.getPointerField(this, 5);
	}
	
	/**
	 * \u534f\u8bae\u4fe1\u606f<br>
	 * C type : TThostFtdcProtocolInfoType
	 */
	@Array({ 11 })
	@Field(6)
	public Pointer<Byte> ProtocolInfo() {
		return io.getPointerField(this, 6);
	}
	
	/**
	 * Mac\u5730\u5740<br>
	 * C type : TThostFtdcMacAddressType
	 */
	@Array({ 21 })
	@Field(7)
	public Pointer<Byte> MacAddress() {
		return io.getPointerField(this, 7);
	}
	
	/**
	 * \u52a8\u6001\u5bc6\u7801<br>
	 * C type : TThostFtdcPasswordType
	 */
	@Array({ 41 })
	@Field(8)
	public Pointer<Byte> OneTimePassword() {
		return io.getPointerField(this, 8);
	}
	
	/**
	 * \u7ec8\u7aefIP\u5730\u5740<br>
	 * C type : TThostFtdcIPAddressType
	 */
	@Array({ 16 })
	@Field(9)
	public Pointer<Byte> ClientIPAddress() {
		return io.getPointerField(this, 9);
	}
	
	public CThostFtdcReqUserLoginField() {
		super();
	}
	
	public CThostFtdcReqUserLoginField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
	
	public void setBrokerID(String brokerID) {
		io.getPointerField(this, 1).setCString(brokerID);
	}
	
	public void setUserID(String userID) {
		io.getPointerField(this, 2).setCString(userID);
	}
	
	public void setPassword(String password) {
		io.getPointerField(this, 3).setCString(password);
	}
	
	public void setUserProductInfo(String userProductInfo) {
		io.getPointerField(this, 4).setCString(userProductInfo);
	}
}
