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
 * \u67e5\u8be2\u6307\u5b9a\u6d41\u6c34\u53f7\u7684\u4ea4\u6613\u7ed3\u679c\u8bf7\u6c42<br>
 * <i>native declaration : line 12832</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcReqQueryTradeResultBySerialField")
@Library("thosttraderapi")
public class CThostFtdcReqQueryTradeResultBySerialField extends StructObject {
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
	public CThostFtdcReqQueryTradeResultBySerialField PlateSerial(int PlateSerial) {
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
	public CThostFtdcReqQueryTradeResultBySerialField LastFragment(byte LastFragment) {
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
	public CThostFtdcReqQueryTradeResultBySerialField SessionID(int SessionID) {
		io.setIntField(this, 11, SessionID);
		return this;
	}
	
	/**
	 * \u6d41\u6c34\u53f7<br>
	 * C type : TThostFtdcSerialType
	 */
	@Field(12)
	public int Reference() {
		return io.getIntField(this, 12);
	}
	
	/**
	 * \u6d41\u6c34\u53f7<br>
	 * C type : TThostFtdcSerialType
	 */
	@Field(12)
	public CThostFtdcReqQueryTradeResultBySerialField Reference(int Reference) {
		io.setIntField(this, 12, Reference);
		return this;
	}
	
	/**
	 * \u672c\u6d41\u6c34\u53f7\u53d1\u5e03\u8005\u7684\u673a\u6784\u7c7b\u578b<br>
	 * C type : TThostFtdcInstitutionTypeType
	 */
	@Field(13)
	public byte RefrenceIssureType() {
		return io.getByteField(this, 13);
	}
	
	/**
	 * \u672c\u6d41\u6c34\u53f7\u53d1\u5e03\u8005\u7684\u673a\u6784\u7c7b\u578b<br>
	 * C type : TThostFtdcInstitutionTypeType
	 */
	@Field(13)
	public CThostFtdcReqQueryTradeResultBySerialField RefrenceIssureType(byte RefrenceIssureType) {
		io.setByteField(this, 13, RefrenceIssureType);
		return this;
	}
	
	/**
	 * \u672c\u6d41\u6c34\u53f7\u53d1\u5e03\u8005\u673a\u6784\u7f16\u7801<br>
	 * C type : TThostFtdcOrganCodeType
	 */
	@Array({ 36 })
	@Field(14)
	public Pointer<Byte> RefrenceIssure() {
		return io.getPointerField(this, 14);
	}
	
	/**
	 * \u5ba2\u6237\u59d3\u540d<br>
	 * C type : TThostFtdcIndividualNameType
	 */
	@Array({ 51 })
	@Field(15)
	public Pointer<Byte> CustomerName() {
		return io.getPointerField(this, 15);
	}
	
	/**
	 * \u8bc1\u4ef6\u7c7b\u578b<br>
	 * C type : TThostFtdcIdCardTypeType
	 */
	@Field(16)
	public byte IdCardType() {
		return io.getByteField(this, 16);
	}
	
	/**
	 * \u8bc1\u4ef6\u7c7b\u578b<br>
	 * C type : TThostFtdcIdCardTypeType
	 */
	@Field(16)
	public CThostFtdcReqQueryTradeResultBySerialField IdCardType(byte IdCardType) {
		io.setByteField(this, 16, IdCardType);
		return this;
	}
	
	/**
	 * \u8bc1\u4ef6\u53f7\u7801<br>
	 * C type : TThostFtdcIdentifiedCardNoType
	 */
	@Array({ 51 })
	@Field(17)
	public Pointer<Byte> IdentifiedCardNo() {
		return io.getPointerField(this, 17);
	}
	
	/**
	 * \u5ba2\u6237\u7c7b\u578b<br>
	 * C type : TThostFtdcCustTypeType
	 */
	@Field(18)
	public byte CustType() {
		return io.getByteField(this, 18);
	}
	
	/**
	 * \u5ba2\u6237\u7c7b\u578b<br>
	 * C type : TThostFtdcCustTypeType
	 */
	@Field(18)
	public CThostFtdcReqQueryTradeResultBySerialField CustType(byte CustType) {
		io.setByteField(this, 18, CustType);
		return this;
	}
	
	/**
	 * \u94f6\u884c\u5e10\u53f7<br>
	 * C type : TThostFtdcBankAccountType
	 */
	@Array({ 41 })
	@Field(19)
	public Pointer<Byte> BankAccount() {
		return io.getPointerField(this, 19);
	}
	
	/**
	 * \u94f6\u884c\u5bc6\u7801<br>
	 * C type : TThostFtdcPasswordType
	 */
	@Array({ 41 })
	@Field(20)
	public Pointer<Byte> BankPassWord() {
		return io.getPointerField(this, 20);
	}
	
	/**
	 * \u6295\u8d44\u8005\u5e10\u53f7<br>
	 * C type : TThostFtdcAccountIDType
	 */
	@Array({ 13 })
	@Field(21)
	public Pointer<Byte> AccountID() {
		return io.getPointerField(this, 21);
	}
	
	/**
	 * \u671f\u8d27\u5bc6\u7801<br>
	 * C type : TThostFtdcPasswordType
	 */
	@Array({ 41 })
	@Field(22)
	public Pointer<Byte> Password() {
		return io.getPointerField(this, 22);
	}
	
	/**
	 * \u5e01\u79cd\u4ee3\u7801<br>
	 * C type : TThostFtdcCurrencyIDType
	 */
	@Array({ 4 })
	@Field(23)
	public Pointer<Byte> CurrencyID() {
		return io.getPointerField(this, 23);
	}
	
	/**
	 * \u8f6c\u5e10\u91d1\u989d<br>
	 * C type : TThostFtdcTradeAmountType
	 */
	@Field(24)
	public double TradeAmount() {
		return io.getDoubleField(this, 24);
	}
	
	/**
	 * \u8f6c\u5e10\u91d1\u989d<br>
	 * C type : TThostFtdcTradeAmountType
	 */
	@Field(24)
	public CThostFtdcReqQueryTradeResultBySerialField TradeAmount(double TradeAmount) {
		io.setDoubleField(this, 24, TradeAmount);
		return this;
	}
	
	/**
	 * \u6458\u8981<br>
	 * C type : TThostFtdcDigestType
	 */
	@Array({ 36 })
	@Field(25)
	public Pointer<Byte> Digest() {
		return io.getPointerField(this, 25);
	}
	
	public CThostFtdcReqQueryTradeResultBySerialField() {
		super();
	}
	
	public CThostFtdcReqQueryTradeResultBySerialField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
