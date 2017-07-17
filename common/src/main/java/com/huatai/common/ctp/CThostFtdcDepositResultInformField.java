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
 * \u9a8c\u8bc1\u671f\u8d27\u8d44\u91d1\u5bc6\u7801\u548c\u5ba2\u6237\u4fe1\u606f<br>
 * <i>native declaration : line 13063</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcDepositResultInformField")
@Library("thosttraderapi")
public class CThostFtdcDepositResultInformField extends StructObject {
	static {
		BridJ.register();
	}
	
	/**
	 * \u51fa\u5165\u91d1\u6d41\u6c34\u53f7\uff0c\u8be5\u6d41\u6c34\u53f7\u4e3a\u94f6\u671f\u62a5\u76d8\u8fd4\u56de\u7684\u6d41\u6c34\u53f7<br>
	 * C type : TThostFtdcDepositSeqNoType
	 */
	@Array({ 15 })
	@Field(0)
	public Pointer<Byte> DepositSeqNo() {
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
	 * \u6295\u8d44\u8005\u4ee3\u7801<br>
	 * C type : TThostFtdcInvestorIDType
	 */
	@Array({ 13 })
	@Field(2)
	public Pointer<Byte> InvestorID() {
		return io.getPointerField(this, 2);
	}
	
	/**
	 * \u5165\u91d1\u91d1\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(3)
	public double Deposit() {
		return io.getDoubleField(this, 3);
	}
	
	/**
	 * \u5165\u91d1\u91d1\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(3)
	public CThostFtdcDepositResultInformField Deposit(double Deposit) {
		io.setDoubleField(this, 3, Deposit);
		return this;
	}
	
	/**
	 * \u8bf7\u6c42\u7f16\u53f7<br>
	 * C type : TThostFtdcRequestIDType
	 */
	@Field(4)
	public int RequestID() {
		return io.getIntField(this, 4);
	}
	
	/**
	 * \u8bf7\u6c42\u7f16\u53f7<br>
	 * C type : TThostFtdcRequestIDType
	 */
	@Field(4)
	public CThostFtdcDepositResultInformField RequestID(int RequestID) {
		io.setIntField(this, 4, RequestID);
		return this;
	}
	
	/**
	 * \u8fd4\u56de\u4ee3\u7801<br>
	 * C type : TThostFtdcReturnCodeType
	 */
	@Array({ 7 })
	@Field(5)
	public Pointer<Byte> ReturnCode() {
		return io.getPointerField(this, 5);
	}
	
	/**
	 * \u8fd4\u56de\u7801\u63cf\u8ff0<br>
	 * C type : TThostFtdcDescrInfoForReturnCodeType
	 */
	@Array({ 129 })
	@Field(6)
	public Pointer<Byte> DescrInfoForReturnCode() {
		return io.getPointerField(this, 6);
	}
	
	public CThostFtdcDepositResultInformField() {
		super();
	}
	
	public CThostFtdcDepositResultInformField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
