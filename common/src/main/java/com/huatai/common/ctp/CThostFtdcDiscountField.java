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
 * \u4f1a\u5458\u8d44\u91d1\u6298\u6263<br>
 * <i>native declaration : line 10418</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcDiscountField")
@Library("thosttraderapi")
public class CThostFtdcDiscountField extends StructObject {
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
	 * \u6295\u8d44\u8005\u8303\u56f4<br>
	 * C type : TThostFtdcInvestorRangeType
	 */
	@Field(1)
	public byte InvestorRange() {
		return io.getByteField(this, 1);
	}
	
	/**
	 * \u6295\u8d44\u8005\u8303\u56f4<br>
	 * C type : TThostFtdcInvestorRangeType
	 */
	@Field(1)
	public CThostFtdcDiscountField InvestorRange(byte InvestorRange) {
		io.setByteField(this, 1, InvestorRange);
		return this;
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
	 * \u8d44\u91d1\u6298\u6263\u6bd4\u4f8b<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(3)
	public double Discount() {
		return io.getDoubleField(this, 3);
	}
	
	/**
	 * \u8d44\u91d1\u6298\u6263\u6bd4\u4f8b<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(3)
	public CThostFtdcDiscountField Discount(double Discount) {
		io.setDoubleField(this, 3, Discount);
		return this;
	}
	
	public CThostFtdcDiscountField() {
		super();
	}
	
	public CThostFtdcDiscountField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
