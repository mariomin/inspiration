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
 * \u6295\u8d44\u8005\u5408\u7ea6\u4ea4\u6613\u6743\u9650<br>
 * <i>native declaration : line 7172</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcInstrumentTradingRightField")
@Library("thosttraderapi")
public class CThostFtdcInstrumentTradingRightField extends StructObject {
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
	public CThostFtdcInstrumentTradingRightField InvestorRange(byte InvestorRange) {
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
	 * \u4ea4\u6613\u6743\u9650<br>
	 * C type : TThostFtdcTradingRightType
	 */
	@Field(4)
	public byte TradingRight() {
		return io.getByteField(this, 4);
	}
	
	/**
	 * \u4ea4\u6613\u6743\u9650<br>
	 * C type : TThostFtdcTradingRightType
	 */
	@Field(4)
	public CThostFtdcInstrumentTradingRightField TradingRight(byte TradingRight) {
		io.setByteField(this, 4, TradingRight);
		return this;
	}
	
	public CThostFtdcInstrumentTradingRightField() {
		super();
	}
	
	public CThostFtdcInstrumentTradingRightField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}