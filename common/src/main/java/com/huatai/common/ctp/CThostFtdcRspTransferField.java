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
 * \u94f6\u884c\u53d1\u8d77\u94f6\u884c\u8d44\u91d1\u8f6c\u671f\u8d27\u54cd\u5e94<br>
 * <i>native declaration : line 12165</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcRspTransferField")
@Library("thosttraderapi")
public class CThostFtdcRspTransferField extends StructObject {
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
	public CThostFtdcRspTransferField PlateSerial(int PlateSerial) {
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
	public CThostFtdcRspTransferField LastFragment(byte LastFragment) {
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
	public CThostFtdcRspTransferField SessionID(int SessionID) {
		io.setIntField(this, 11, SessionID);
		return this;
	}
	
	/**
	 * \u5ba2\u6237\u59d3\u540d<br>
	 * C type : TThostFtdcIndividualNameType
	 */
	@Array({ 51 })
	@Field(12)
	public Pointer<Byte> CustomerName() {
		return io.getPointerField(this, 12);
	}
	
	/**
	 * \u8bc1\u4ef6\u7c7b\u578b<br>
	 * C type : TThostFtdcIdCardTypeType
	 */
	@Field(13)
	public byte IdCardType() {
		return io.getByteField(this, 13);
	}
	
	/**
	 * \u8bc1\u4ef6\u7c7b\u578b<br>
	 * C type : TThostFtdcIdCardTypeType
	 */
	@Field(13)
	public CThostFtdcRspTransferField IdCardType(byte IdCardType) {
		io.setByteField(this, 13, IdCardType);
		return this;
	}
	
	/**
	 * \u8bc1\u4ef6\u53f7\u7801<br>
	 * C type : TThostFtdcIdentifiedCardNoType
	 */
	@Array({ 51 })
	@Field(14)
	public Pointer<Byte> IdentifiedCardNo() {
		return io.getPointerField(this, 14);
	}
	
	/**
	 * \u5ba2\u6237\u7c7b\u578b<br>
	 * C type : TThostFtdcCustTypeType
	 */
	@Field(15)
	public byte CustType() {
		return io.getByteField(this, 15);
	}
	
	/**
	 * \u5ba2\u6237\u7c7b\u578b<br>
	 * C type : TThostFtdcCustTypeType
	 */
	@Field(15)
	public CThostFtdcRspTransferField CustType(byte CustType) {
		io.setByteField(this, 15, CustType);
		return this;
	}
	
	/**
	 * \u94f6\u884c\u5e10\u53f7<br>
	 * C type : TThostFtdcBankAccountType
	 */
	@Array({ 41 })
	@Field(16)
	public Pointer<Byte> BankAccount() {
		return io.getPointerField(this, 16);
	}
	
	/**
	 * \u94f6\u884c\u5bc6\u7801<br>
	 * C type : TThostFtdcPasswordType
	 */
	@Array({ 41 })
	@Field(17)
	public Pointer<Byte> BankPassWord() {
		return io.getPointerField(this, 17);
	}
	
	/**
	 * \u6295\u8d44\u8005\u5e10\u53f7<br>
	 * C type : TThostFtdcAccountIDType
	 */
	@Array({ 13 })
	@Field(18)
	public Pointer<Byte> AccountID() {
		return io.getPointerField(this, 18);
	}
	
	/**
	 * \u671f\u8d27\u5bc6\u7801<br>
	 * C type : TThostFtdcPasswordType
	 */
	@Array({ 41 })
	@Field(19)
	public Pointer<Byte> Password() {
		return io.getPointerField(this, 19);
	}
	
	/**
	 * \u5b89\u88c5\u7f16\u53f7<br>
	 * C type : TThostFtdcInstallIDType
	 */
	@Field(20)
	public int InstallID() {
		return io.getIntField(this, 20);
	}
	
	/**
	 * \u5b89\u88c5\u7f16\u53f7<br>
	 * C type : TThostFtdcInstallIDType
	 */
	@Field(20)
	public CThostFtdcRspTransferField InstallID(int InstallID) {
		io.setIntField(this, 20, InstallID);
		return this;
	}
	
	/**
	 * \u671f\u8d27\u516c\u53f8\u6d41\u6c34\u53f7<br>
	 * C type : TThostFtdcFutureSerialType
	 */
	@Field(21)
	public int FutureSerial() {
		return io.getIntField(this, 21);
	}
	
	/**
	 * \u671f\u8d27\u516c\u53f8\u6d41\u6c34\u53f7<br>
	 * C type : TThostFtdcFutureSerialType
	 */
	@Field(21)
	public CThostFtdcRspTransferField FutureSerial(int FutureSerial) {
		io.setIntField(this, 21, FutureSerial);
		return this;
	}
	
	/**
	 * \u7528\u6237\u6807\u8bc6<br>
	 * C type : TThostFtdcUserIDType
	 */
	@Array({ 16 })
	@Field(22)
	public Pointer<Byte> UserID() {
		return io.getPointerField(this, 22);
	}
	
	/**
	 * \u9a8c\u8bc1\u5ba2\u6237\u8bc1\u4ef6\u53f7\u7801\u6807\u5fd7<br>
	 * C type : TThostFtdcYesNoIndicatorType
	 */
	@Field(23)
	public byte VerifyCertNoFlag() {
		return io.getByteField(this, 23);
	}
	
	/**
	 * \u9a8c\u8bc1\u5ba2\u6237\u8bc1\u4ef6\u53f7\u7801\u6807\u5fd7<br>
	 * C type : TThostFtdcYesNoIndicatorType
	 */
	@Field(23)
	public CThostFtdcRspTransferField VerifyCertNoFlag(byte VerifyCertNoFlag) {
		io.setByteField(this, 23, VerifyCertNoFlag);
		return this;
	}
	
	/**
	 * \u5e01\u79cd\u4ee3\u7801<br>
	 * C type : TThostFtdcCurrencyIDType
	 */
	@Array({ 4 })
	@Field(24)
	public Pointer<Byte> CurrencyID() {
		return io.getPointerField(this, 24);
	}
	
	/**
	 * \u8f6c\u5e10\u91d1\u989d<br>
	 * C type : TThostFtdcTradeAmountType
	 */
	@Field(25)
	public double TradeAmount() {
		return io.getDoubleField(this, 25);
	}
	
	/**
	 * \u8f6c\u5e10\u91d1\u989d<br>
	 * C type : TThostFtdcTradeAmountType
	 */
	@Field(25)
	public CThostFtdcRspTransferField TradeAmount(double TradeAmount) {
		io.setDoubleField(this, 25, TradeAmount);
		return this;
	}
	
	/**
	 * \u671f\u8d27\u53ef\u53d6\u91d1\u989d<br>
	 * C type : TThostFtdcTradeAmountType
	 */
	@Field(26)
	public double FutureFetchAmount() {
		return io.getDoubleField(this, 26);
	}
	
	/**
	 * \u671f\u8d27\u53ef\u53d6\u91d1\u989d<br>
	 * C type : TThostFtdcTradeAmountType
	 */
	@Field(26)
	public CThostFtdcRspTransferField FutureFetchAmount(double FutureFetchAmount) {
		io.setDoubleField(this, 26, FutureFetchAmount);
		return this;
	}
	
	/**
	 * \u8d39\u7528\u652f\u4ed8\u6807\u5fd7<br>
	 * C type : TThostFtdcFeePayFlagType
	 */
	@Field(27)
	public byte FeePayFlag() {
		return io.getByteField(this, 27);
	}
	
	/**
	 * \u8d39\u7528\u652f\u4ed8\u6807\u5fd7<br>
	 * C type : TThostFtdcFeePayFlagType
	 */
	@Field(27)
	public CThostFtdcRspTransferField FeePayFlag(byte FeePayFlag) {
		io.setByteField(this, 27, FeePayFlag);
		return this;
	}
	
	/**
	 * \u5e94\u6536\u5ba2\u6237\u8d39\u7528<br>
	 * C type : TThostFtdcCustFeeType
	 */
	@Field(28)
	public double CustFee() {
		return io.getDoubleField(this, 28);
	}
	
	/**
	 * \u5e94\u6536\u5ba2\u6237\u8d39\u7528<br>
	 * C type : TThostFtdcCustFeeType
	 */
	@Field(28)
	public CThostFtdcRspTransferField CustFee(double CustFee) {
		io.setDoubleField(this, 28, CustFee);
		return this;
	}
	
	/**
	 * \u5e94\u6536\u671f\u8d27\u516c\u53f8\u8d39\u7528<br>
	 * C type : TThostFtdcFutureFeeType
	 */
	@Field(29)
	public double BrokerFee() {
		return io.getDoubleField(this, 29);
	}
	
	/**
	 * \u5e94\u6536\u671f\u8d27\u516c\u53f8\u8d39\u7528<br>
	 * C type : TThostFtdcFutureFeeType
	 */
	@Field(29)
	public CThostFtdcRspTransferField BrokerFee(double BrokerFee) {
		io.setDoubleField(this, 29, BrokerFee);
		return this;
	}
	
	/**
	 * \u53d1\u9001\u65b9\u7ed9\u63a5\u6536\u65b9\u7684\u6d88\u606f<br>
	 * C type : TThostFtdcAddInfoType
	 */
	@Array({ 129 })
	@Field(30)
	public Pointer<Byte> Message() {
		return io.getPointerField(this, 30);
	}
	
	/**
	 * \u6458\u8981<br>
	 * C type : TThostFtdcDigestType
	 */
	@Array({ 36 })
	@Field(31)
	public Pointer<Byte> Digest() {
		return io.getPointerField(this, 31);
	}
	
	/**
	 * \u94f6\u884c\u5e10\u53f7\u7c7b\u578b<br>
	 * C type : TThostFtdcBankAccTypeType
	 */
	@Field(32)
	public byte BankAccType() {
		return io.getByteField(this, 32);
	}
	
	/**
	 * \u94f6\u884c\u5e10\u53f7\u7c7b\u578b<br>
	 * C type : TThostFtdcBankAccTypeType
	 */
	@Field(32)
	public CThostFtdcRspTransferField BankAccType(byte BankAccType) {
		io.setByteField(this, 32, BankAccType);
		return this;
	}
	
	/**
	 * \u6e20\u9053\u6807\u5fd7<br>
	 * C type : TThostFtdcDeviceIDType
	 */
	@Array({ 3 })
	@Field(33)
	public Pointer<Byte> DeviceID() {
		return io.getPointerField(this, 33);
	}
	
	/**
	 * \u671f\u8d27\u5355\u4f4d\u5e10\u53f7\u7c7b\u578b<br>
	 * C type : TThostFtdcBankAccTypeType
	 */
	@Field(34)
	public byte BankSecuAccType() {
		return io.getByteField(this, 34);
	}
	
	/**
	 * \u671f\u8d27\u5355\u4f4d\u5e10\u53f7\u7c7b\u578b<br>
	 * C type : TThostFtdcBankAccTypeType
	 */
	@Field(34)
	public CThostFtdcRspTransferField BankSecuAccType(byte BankSecuAccType) {
		io.setByteField(this, 34, BankSecuAccType);
		return this;
	}
	
	/**
	 * \u671f\u8d27\u516c\u53f8\u94f6\u884c\u7f16\u7801<br>
	 * C type : TThostFtdcBankCodingForFutureType
	 */
	@Array({ 33 })
	@Field(35)
	public Pointer<Byte> BrokerIDByBank() {
		return io.getPointerField(this, 35);
	}
	
	/**
	 * \u671f\u8d27\u5355\u4f4d\u5e10\u53f7<br>
	 * C type : TThostFtdcBankAccountType
	 */
	@Array({ 41 })
	@Field(36)
	public Pointer<Byte> BankSecuAcc() {
		return io.getPointerField(this, 36);
	}
	
	/**
	 * \u94f6\u884c\u5bc6\u7801\u6807\u5fd7<br>
	 * C type : TThostFtdcPwdFlagType
	 */
	@Field(37)
	public byte BankPwdFlag() {
		return io.getByteField(this, 37);
	}
	
	/**
	 * \u94f6\u884c\u5bc6\u7801\u6807\u5fd7<br>
	 * C type : TThostFtdcPwdFlagType
	 */
	@Field(37)
	public CThostFtdcRspTransferField BankPwdFlag(byte BankPwdFlag) {
		io.setByteField(this, 37, BankPwdFlag);
		return this;
	}
	
	/**
	 * \u671f\u8d27\u8d44\u91d1\u5bc6\u7801\u6838\u5bf9\u6807\u5fd7<br>
	 * C type : TThostFtdcPwdFlagType
	 */
	@Field(38)
	public byte SecuPwdFlag() {
		return io.getByteField(this, 38);
	}
	
	/**
	 * \u671f\u8d27\u8d44\u91d1\u5bc6\u7801\u6838\u5bf9\u6807\u5fd7<br>
	 * C type : TThostFtdcPwdFlagType
	 */
	@Field(38)
	public CThostFtdcRspTransferField SecuPwdFlag(byte SecuPwdFlag) {
		io.setByteField(this, 38, SecuPwdFlag);
		return this;
	}
	
	/**
	 * \u4ea4\u6613\u67dc\u5458<br>
	 * C type : TThostFtdcOperNoType
	 */
	@Array({ 17 })
	@Field(39)
	public Pointer<Byte> OperNo() {
		return io.getPointerField(this, 39);
	}
	
	/**
	 * \u8bf7\u6c42\u7f16\u53f7<br>
	 * C type : TThostFtdcRequestIDType
	 */
	@Field(40)
	public int RequestID() {
		return io.getIntField(this, 40);
	}
	
	/**
	 * \u8bf7\u6c42\u7f16\u53f7<br>
	 * C type : TThostFtdcRequestIDType
	 */
	@Field(40)
	public CThostFtdcRspTransferField RequestID(int RequestID) {
		io.setIntField(this, 40, RequestID);
		return this;
	}
	
	/**
	 * \u4ea4\u6613ID<br>
	 * C type : TThostFtdcTIDType
	 */
	@Field(41)
	public int TID() {
		return io.getIntField(this, 41);
	}
	
	/**
	 * \u4ea4\u6613ID<br>
	 * C type : TThostFtdcTIDType
	 */
	@Field(41)
	public CThostFtdcRspTransferField TID(int TID) {
		io.setIntField(this, 41, TID);
		return this;
	}
	
	/**
	 * \u8f6c\u8d26\u4ea4\u6613\u72b6\u6001<br>
	 * C type : TThostFtdcTransferStatusType
	 */
	@Field(42)
	public byte TransferStatus() {
		return io.getByteField(this, 42);
	}
	
	/**
	 * \u8f6c\u8d26\u4ea4\u6613\u72b6\u6001<br>
	 * C type : TThostFtdcTransferStatusType
	 */
	@Field(42)
	public CThostFtdcRspTransferField TransferStatus(byte TransferStatus) {
		io.setByteField(this, 42, TransferStatus);
		return this;
	}
	
	/**
	 * \u9519\u8bef\u4ee3\u7801<br>
	 * C type : TThostFtdcErrorIDType
	 */
	@Field(43)
	public int ErrorID() {
		return io.getIntField(this, 43);
	}
	
	/**
	 * \u9519\u8bef\u4ee3\u7801<br>
	 * C type : TThostFtdcErrorIDType
	 */
	@Field(43)
	public CThostFtdcRspTransferField ErrorID(int ErrorID) {
		io.setIntField(this, 43, ErrorID);
		return this;
	}
	
	/**
	 * \u9519\u8bef\u4fe1\u606f<br>
	 * C type : TThostFtdcErrorMsgType
	 */
	@Array({ 81 })
	@Field(44)
	public Pointer<Byte> ErrorMsg() {
		return io.getPointerField(this, 44);
	}
	
	public CThostFtdcRspTransferField() {
		super();
	}
	
	public CThostFtdcRspTransferField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
