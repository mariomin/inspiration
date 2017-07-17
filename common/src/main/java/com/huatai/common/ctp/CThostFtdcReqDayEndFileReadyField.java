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
 * \u65e5\u7ec8\u6587\u4ef6\u5c31\u7eea\u8bf7\u6c42<br>
 * <i>native declaration : line 12946</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcReqDayEndFileReadyField")
@Library("thosttraderapi")
public class CThostFtdcReqDayEndFileReadyField extends StructObject {
	static {
		BridJ.register();
	}
	
	/**
	 * \u4e1a\u52a1\u529f\u80fd\u7801<br>
	 * C type : TThostFtdcTradeCodeType
	 */
	@Array({ 7 })
	@Field(0)
	public Pointer<Byte> TradeCode() {
		return io.getPointerField(this, 0);
	}
	
	/**
	 * \u94f6\u884c\u4ee3\u7801<br>
	 * C type : TThostFtdcBankIDType
	 */
	@Array({ 4 })
	@Field(1)
	public Pointer<Byte> BankID() {
		return io.getPointerField(this, 1);
	}
	
	/**
	 * \u94f6\u884c\u5206\u652f\u673a\u6784\u4ee3\u7801<br>
	 * C type : TThostFtdcBankBrchIDType
	 */
	@Array({ 5 })
	@Field(2)
	public Pointer<Byte> BankBranchID() {
		return io.getPointerField(this, 2);
	}
	
	/**
	 * \u671f\u5546\u4ee3\u7801<br>
	 * C type : TThostFtdcBrokerIDType
	 */
	@Array({ 11 })
	@Field(3)
	public Pointer<Byte> BrokerID() {
		return io.getPointerField(this, 3);
	}
	
	/**
	 * \u671f\u5546\u5206\u652f\u673a\u6784\u4ee3\u7801<br>
	 * C type : TThostFtdcFutureBranchIDType
	 */
	@Array({ 31 })
	@Field(4)
	public Pointer<Byte> BrokerBranchID() {
		return io.getPointerField(this, 4);
	}
	
	/**
	 * \u4ea4\u6613\u65e5\u671f<br>
	 * C type : TThostFtdcTradeDateType
	 */
	@Array({ 9 })
	@Field(5)
	public Pointer<Byte> TradeDate() {
		return io.getPointerField(this, 5);
	}
	
	/**
	 * \u4ea4\u6613\u65f6\u95f4<br>
	 * C type : TThostFtdcTradeTimeType
	 */
	@Array({ 9 })
	@Field(6)
	public Pointer<Byte> TradeTime() {
		return io.getPointerField(this, 6);
	}
	
	/**
	 * \u94f6\u884c\u6d41\u6c34\u53f7<br>
	 * C type : TThostFtdcBankSerialType
	 */
	@Array({ 13 })
	@Field(7)
	public Pointer<Byte> BankSerial() {
		return io.getPointerField(this, 7);
	}
	
	/**
	 * \u4ea4\u6613\u7cfb\u7edf\u65e5\u671f<br>
	 * C type : TThostFtdcTradeDateType
	 */
	@Array({ 9 })
	@Field(8)
	public Pointer<Byte> TradingDay() {
		return io.getPointerField(this, 8);
	}
	
	/**
	 * \u94f6\u671f\u5e73\u53f0\u6d88\u606f\u6d41\u6c34\u53f7<br>
	 * C type : TThostFtdcSerialType
	 */
	@Field(9)
	public int PlateSerial() {
		return io.getIntField(this, 9);
	}
	
	/**
	 * \u94f6\u671f\u5e73\u53f0\u6d88\u606f\u6d41\u6c34\u53f7<br>
	 * C type : TThostFtdcSerialType
	 */
	@Field(9)
	public CThostFtdcReqDayEndFileReadyField PlateSerial(int PlateSerial) {
		io.setIntField(this, 9, PlateSerial);
		return this;
	}
	
	/**
	 * \u6700\u540e\u5206\u7247\u6807\u5fd7<br>
	 * C type : TThostFtdcLastFragmentType
	 */
	@Field(10)
	public byte LastFragment() {
		return io.getByteField(this, 10);
	}
	
	/**
	 * \u6700\u540e\u5206\u7247\u6807\u5fd7<br>
	 * C type : TThostFtdcLastFragmentType
	 */
	@Field(10)
	public CThostFtdcReqDayEndFileReadyField LastFragment(byte LastFragment) {
		io.setByteField(this, 10, LastFragment);
		return this;
	}
	
	/**
	 * \u4f1a\u8bdd\u53f7<br>
	 * C type : TThostFtdcSessionIDType
	 */
	@Field(11)
	public int SessionID() {
		return io.getIntField(this, 11);
	}
	
	/**
	 * \u4f1a\u8bdd\u53f7<br>
	 * C type : TThostFtdcSessionIDType
	 */
	@Field(11)
	public CThostFtdcReqDayEndFileReadyField SessionID(int SessionID) {
		io.setIntField(this, 11, SessionID);
		return this;
	}
	
	/**
	 * \u6587\u4ef6\u4e1a\u52a1\u529f\u80fd<br>
	 * C type : TThostFtdcFileBusinessCodeType
	 */
	@Field(12)
	public byte FileBusinessCode() {
		return io.getByteField(this, 12);
	}
	
	/**
	 * \u6587\u4ef6\u4e1a\u52a1\u529f\u80fd<br>
	 * C type : TThostFtdcFileBusinessCodeType
	 */
	@Field(12)
	public CThostFtdcReqDayEndFileReadyField FileBusinessCode(byte FileBusinessCode) {
		io.setByteField(this, 12, FileBusinessCode);
		return this;
	}
	
	/**
	 * \u6458\u8981<br>
	 * C type : TThostFtdcDigestType
	 */
	@Array({ 36 })
	@Field(13)
	public Pointer<Byte> Digest() {
		return io.getPointerField(this, 13);
	}
	
	public CThostFtdcReqDayEndFileReadyField() {
		super();
	}
	
	public CThostFtdcReqDayEndFileReadyField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
