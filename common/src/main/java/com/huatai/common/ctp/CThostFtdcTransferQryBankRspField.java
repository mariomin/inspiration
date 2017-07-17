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
 * \u67e5\u8be2\u94f6\u884c\u8d44\u91d1\u8bf7\u6c42\u54cd\u5e94<br>
 * <i>native declaration : line 6532</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcTransferQryBankRspField")
@Library("thosttraderapi")
public class CThostFtdcTransferQryBankRspField extends StructObject {
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
	 * \u94f6\u884c\u4f59\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(3)
	public double TradeAmt() {
		return io.getDoubleField(this, 3);
	}
	
	/**
	 * \u94f6\u884c\u4f59\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(3)
	public CThostFtdcTransferQryBankRspField TradeAmt(double TradeAmt) {
		io.setDoubleField(this, 3, TradeAmt);
		return this;
	}
	
	/**
	 * \u94f6\u884c\u53ef\u7528\u4f59\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(4)
	public double UseAmt() {
		return io.getDoubleField(this, 4);
	}
	
	/**
	 * \u94f6\u884c\u53ef\u7528\u4f59\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(4)
	public CThostFtdcTransferQryBankRspField UseAmt(double UseAmt) {
		io.setDoubleField(this, 4, UseAmt);
		return this;
	}
	
	/**
	 * \u94f6\u884c\u53ef\u53d6\u4f59\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(5)
	public double FetchAmt() {
		return io.getDoubleField(this, 5);
	}
	
	/**
	 * \u94f6\u884c\u53ef\u53d6\u4f59\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(5)
	public CThostFtdcTransferQryBankRspField FetchAmt(double FetchAmt) {
		io.setDoubleField(this, 5, FetchAmt);
		return this;
	}
	
	/**
	 * \u5e01\u79cd<br>
	 * C type : TThostFtdcCurrencyCodeType
	 */
	@Array({ 4 })
	@Field(6)
	public Pointer<Byte> CurrencyCode() {
		return io.getPointerField(this, 6);
	}
	
	public CThostFtdcTransferQryBankRspField() {
		super();
	}
	
	public CThostFtdcTransferQryBankRspField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}