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
 * \u7528\u6237\u4e8b\u4ef6\u901a\u77e5<br>
 * <i>native declaration : line 11105</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcTradingNoticeField")
@Library("thosttraderapi")
public class CThostFtdcTradingNoticeField extends StructObject {
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
	public CThostFtdcTradingNoticeField InvestorRange(byte InvestorRange) {
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
	 * \u5e8f\u5217\u7cfb\u5217\u53f7<br>
	 * C type : TThostFtdcSequenceSeriesType
	 */
	@Field(3)
	public short SequenceSeries() {
		return io.getShortField(this, 3);
	}
	
	/**
	 * \u5e8f\u5217\u7cfb\u5217\u53f7<br>
	 * C type : TThostFtdcSequenceSeriesType
	 */
	@Field(3)
	public CThostFtdcTradingNoticeField SequenceSeries(short SequenceSeries) {
		io.setShortField(this, 3, SequenceSeries);
		return this;
	}
	
	/**
	 * \u7528\u6237\u4ee3\u7801<br>
	 * C type : TThostFtdcUserIDType
	 */
	@Array({ 16 })
	@Field(4)
	public Pointer<Byte> UserID() {
		return io.getPointerField(this, 4);
	}
	
	/**
	 * \u53d1\u9001\u65f6\u95f4<br>
	 * C type : TThostFtdcTimeType
	 */
	@Array({ 9 })
	@Field(5)
	public Pointer<Byte> SendTime() {
		return io.getPointerField(this, 5);
	}
	
	/**
	 * \u5e8f\u5217\u53f7<br>
	 * C type : TThostFtdcSequenceNoType
	 */
	@Field(6)
	public int SequenceNo() {
		return io.getIntField(this, 6);
	}
	
	/**
	 * \u5e8f\u5217\u53f7<br>
	 * C type : TThostFtdcSequenceNoType
	 */
	@Field(6)
	public CThostFtdcTradingNoticeField SequenceNo(int SequenceNo) {
		io.setIntField(this, 6, SequenceNo);
		return this;
	}
	
	/**
	 * \u6d88\u606f\u6b63\u6587<br>
	 * C type : TThostFtdcContentType
	 */
	@Array({ 501 })
	@Field(7)
	public Pointer<Byte> FieldContent() {
		return io.getPointerField(this, 7);
	}
	
	public CThostFtdcTradingNoticeField() {
		super();
	}
	
	public CThostFtdcTradingNoticeField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
