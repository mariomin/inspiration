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
 * \u5f53\u524d\u671f\u6743\u5408\u7ea6\u624b\u7eed\u8d39\u7684\u8be6\u7ec6\u5185\u5bb9<br>
 * <i>native declaration : line 8871</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcOptionInstrCommRateField")
@Library("thosttraderapi")
public class CThostFtdcOptionInstrCommRateField extends StructObject {
	static {
		BridJ.register();
	}
	
	/**
	 * \u5408\u7ea6\u4ee3\u7801<br>
	 * C type : TThostFtdcInstrumentIDType
	 */
	@Array({ 31 })
	@Field(0)
	public Pointer<Byte> InstrumentID() {
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
	public CThostFtdcOptionInstrCommRateField InvestorRange(byte InvestorRange) {
		io.setByteField(this, 1, InvestorRange);
		return this;
	}
	
	/**
	 * \u7ecf\u7eaa\u516c\u53f8\u4ee3\u7801<br>
	 * C type : TThostFtdcBrokerIDType
	 */
	@Array({ 11 })
	@Field(2)
	public Pointer<Byte> BrokerID() {
		return io.getPointerField(this, 2);
	}
	
	/**
	 * \u6295\u8d44\u8005\u4ee3\u7801<br>
	 * C type : TThostFtdcInvestorIDType
	 */
	@Array({ 13 })
	@Field(3)
	public Pointer<Byte> InvestorID() {
		return io.getPointerField(this, 3);
	}
	
	/**
	 * \u5f00\u4ed3\u624b\u7eed\u8d39\u7387<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(4)
	public double OpenRatioByMoney() {
		return io.getDoubleField(this, 4);
	}
	
	/**
	 * \u5f00\u4ed3\u624b\u7eed\u8d39\u7387<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(4)
	public CThostFtdcOptionInstrCommRateField OpenRatioByMoney(double OpenRatioByMoney) {
		io.setDoubleField(this, 4, OpenRatioByMoney);
		return this;
	}
	
	/**
	 * \u5f00\u4ed3\u624b\u7eed\u8d39<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(5)
	public double OpenRatioByVolume() {
		return io.getDoubleField(this, 5);
	}
	
	/**
	 * \u5f00\u4ed3\u624b\u7eed\u8d39<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(5)
	public CThostFtdcOptionInstrCommRateField OpenRatioByVolume(double OpenRatioByVolume) {
		io.setDoubleField(this, 5, OpenRatioByVolume);
		return this;
	}
	
	/**
	 * \u5e73\u4ed3\u624b\u7eed\u8d39\u7387<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(6)
	public double CloseRatioByMoney() {
		return io.getDoubleField(this, 6);
	}
	
	/**
	 * \u5e73\u4ed3\u624b\u7eed\u8d39\u7387<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(6)
	public CThostFtdcOptionInstrCommRateField CloseRatioByMoney(double CloseRatioByMoney) {
		io.setDoubleField(this, 6, CloseRatioByMoney);
		return this;
	}
	
	/**
	 * \u5e73\u4ed3\u624b\u7eed\u8d39<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(7)
	public double CloseRatioByVolume() {
		return io.getDoubleField(this, 7);
	}
	
	/**
	 * \u5e73\u4ed3\u624b\u7eed\u8d39<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(7)
	public CThostFtdcOptionInstrCommRateField CloseRatioByVolume(double CloseRatioByVolume) {
		io.setDoubleField(this, 7, CloseRatioByVolume);
		return this;
	}
	
	/**
	 * \u5e73\u4eca\u624b\u7eed\u8d39\u7387<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(8)
	public double CloseTodayRatioByMoney() {
		return io.getDoubleField(this, 8);
	}
	
	/**
	 * \u5e73\u4eca\u624b\u7eed\u8d39\u7387<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(8)
	public CThostFtdcOptionInstrCommRateField CloseTodayRatioByMoney(double CloseTodayRatioByMoney) {
		io.setDoubleField(this, 8, CloseTodayRatioByMoney);
		return this;
	}
	
	/**
	 * \u5e73\u4eca\u624b\u7eed\u8d39<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(9)
	public double CloseTodayRatioByVolume() {
		return io.getDoubleField(this, 9);
	}
	
	/**
	 * \u5e73\u4eca\u624b\u7eed\u8d39<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(9)
	public CThostFtdcOptionInstrCommRateField CloseTodayRatioByVolume(double CloseTodayRatioByVolume) {
		io.setDoubleField(this, 9, CloseTodayRatioByVolume);
		return this;
	}
	
	/**
	 * \u6267\u884c\u624b\u7eed\u8d39\u7387<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(10)
	public double StrikeRatioByMoney() {
		return io.getDoubleField(this, 10);
	}
	
	/**
	 * \u6267\u884c\u624b\u7eed\u8d39\u7387<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(10)
	public CThostFtdcOptionInstrCommRateField StrikeRatioByMoney(double StrikeRatioByMoney) {
		io.setDoubleField(this, 10, StrikeRatioByMoney);
		return this;
	}
	
	/**
	 * \u6267\u884c\u624b\u7eed\u8d39<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(11)
	public double StrikeRatioByVolume() {
		return io.getDoubleField(this, 11);
	}
	
	/**
	 * \u6267\u884c\u624b\u7eed\u8d39<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(11)
	public CThostFtdcOptionInstrCommRateField StrikeRatioByVolume(double StrikeRatioByVolume) {
		io.setDoubleField(this, 11, StrikeRatioByVolume);
		return this;
	}
	
	public CThostFtdcOptionInstrCommRateField() {
		super();
	}
	
	public CThostFtdcOptionInstrCommRateField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
