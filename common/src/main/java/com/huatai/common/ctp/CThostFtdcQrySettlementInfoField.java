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
 * \u67e5\u8be2\u6295\u8d44\u8005\u7ed3\u7b97\u7ed3\u679c<br>
 * <i>native declaration : line 8749</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcQrySettlementInfoField")
@Library("thosttraderapi")
public class CThostFtdcQrySettlementInfoField extends StructObject {
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
	 * \u6295\u8d44\u8005\u4ee3\u7801<br>
	 * C type : TThostFtdcInvestorIDType
	 */
	@Array({ 13 })
	@Field(1)
	public Pointer<Byte> InvestorID() {
		return io.getPointerField(this, 1);
	}
	
	/**
	 * \u4ea4\u6613\u65e5<br>
	 * C type : TThostFtdcDateType
	 */
	@Array({ 9 })
	@Field(2)
	public Pointer<Byte> TradingDay() {
		return io.getPointerField(this, 2);
	}
	
	public CThostFtdcQrySettlementInfoField() {
		super();
	}
	
	public CThostFtdcQrySettlementInfoField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
