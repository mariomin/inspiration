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
 * \u671f\u8d27\u8d44\u91d1\u8f6c\u94f6\u884c\u8bf7\u6c42\u54cd\u5e94<br>
 * <i>native declaration : line 6502</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcTransferFutureToBankRspField")
@Library("thosttraderapi")
public class CThostFtdcTransferFutureToBankRspField extends StructObject {
	static {
		BridJ.register();
	}
	
	/**
	 * \u54cd\u5e94\u4ee3\u7801<br>
	 * C type : TThostFtdcRetCodeType
	 */
	@Array({ 5 })
	@Field(0)
	public Pointer<Byte> RetCode() {
		return io.getPointerField(this, 0);
	}
	
	/**
	 * \u54cd\u5e94\u4fe1\u606f<br>
	 * C type : TThostFtdcRetInfoType
	 */
	@Array({ 129 })
	@Field(1)
	public Pointer<Byte> RetInfo() {
		return io.getPointerField(this, 1);
	}
	
	/**
	 * \u8d44\u91d1\u8d26\u6237<br>
	 * C type : TThostFtdcAccountIDType
	 */
	@Array({ 13 })
	@Field(2)
	public Pointer<Byte> FutureAccount() {
		return io.getPointerField(this, 2);
	}
	
	/**
	 * \u8f6c\u5e10\u91d1\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(3)
	public double TradeAmt() {
		return io.getDoubleField(this, 3);
	}
	
	/**
	 * \u8f6c\u5e10\u91d1\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(3)
	public CThostFtdcTransferFutureToBankRspField TradeAmt(double TradeAmt) {
		io.setDoubleField(this, 3, TradeAmt);
		return this;
	}
	
	/**
	 * \u5e94\u6536\u5ba2\u6237\u624b\u7eed\u8d39<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(4)
	public double CustFee() {
		return io.getDoubleField(this, 4);
	}
	
	/**
	 * \u5e94\u6536\u5ba2\u6237\u624b\u7eed\u8d39<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(4)
	public CThostFtdcTransferFutureToBankRspField CustFee(double CustFee) {
		io.setDoubleField(this, 4, CustFee);
		return this;
	}
	
	/**
	 * \u5e01\u79cd<br>
	 * C type : TThostFtdcCurrencyCodeType
	 */
	@Array({ 4 })
	@Field(5)
	public Pointer<Byte> CurrencyCode() {
		return io.getPointerField(this, 5);
	}
	
	public CThostFtdcTransferFutureToBankRspField() {
		super();
	}
	
	public CThostFtdcTransferFutureToBankRspField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
