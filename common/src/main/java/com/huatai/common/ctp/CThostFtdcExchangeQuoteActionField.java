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
 * \u4ea4\u6613\u6240\u62a5\u4ef7\u64cd\u4f5c<br>
 * <i>native declaration : line 9871</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcExchangeQuoteActionField")
@Library("thosttraderapi")
public class CThostFtdcExchangeQuoteActionField extends StructObject {
	static {
		BridJ.register();
	}
	
	/**
	 * \u4ea4\u6613\u6240\u4ee3\u7801<br>
	 * C type : TThostFtdcExchangeIDType
	 */
	@Array({ 9 })
	@Field(0)
	public Pointer<Byte> ExchangeID() {
		return io.getPointerField(this, 0);
	}
	
	/**
	 * \u62a5\u4ef7\u64cd\u4f5c\u7f16\u53f7<br>
	 * C type : TThostFtdcOrderSysIDType
	 */
	@Array({ 21 })
	@Field(1)
	public Pointer<Byte> QuoteSysID() {
		return io.getPointerField(this, 1);
	}
	
	/**
	 * \u64cd\u4f5c\u6807\u5fd7<br>
	 * C type : TThostFtdcActionFlagType
	 */
	@Field(2)
	public byte ActionFlag() {
		return io.getByteField(this, 2);
	}
	
	/**
	 * \u64cd\u4f5c\u6807\u5fd7<br>
	 * C type : TThostFtdcActionFlagType
	 */
	@Field(2)
	public CThostFtdcExchangeQuoteActionField ActionFlag(byte ActionFlag) {
		io.setByteField(this, 2, ActionFlag);
		return this;
	}
	
	/**
	 * \u64cd\u4f5c\u65e5\u671f<br>
	 * C type : TThostFtdcDateType
	 */
	@Array({ 9 })
	@Field(3)
	public Pointer<Byte> ActionDate() {
		return io.getPointerField(this, 3);
	}
	
	/**
	 * \u64cd\u4f5c\u65f6\u95f4<br>
	 * C type : TThostFtdcTimeType
	 */
	@Array({ 9 })
	@Field(4)
	public Pointer<Byte> ActionTime() {
		return io.getPointerField(this, 4);
	}
	
	/**
	 * \u4ea4\u6613\u6240\u4ea4\u6613\u5458\u4ee3\u7801<br>
	 * C type : TThostFtdcTraderIDType
	 */
	@Array({ 21 })
	@Field(5)
	public Pointer<Byte> TraderID() {
		return io.getPointerField(this, 5);
	}
	
	/**
	 * \u5b89\u88c5\u7f16\u53f7<br>
	 * C type : TThostFtdcInstallIDType
	 */
	@Field(6)
	public int InstallID() {
		return io.getIntField(this, 6);
	}
	
	/**
	 * \u5b89\u88c5\u7f16\u53f7<br>
	 * C type : TThostFtdcInstallIDType
	 */
	@Field(6)
	public CThostFtdcExchangeQuoteActionField InstallID(int InstallID) {
		io.setIntField(this, 6, InstallID);
		return this;
	}
	
	/**
	 * \u672c\u5730\u62a5\u4ef7\u7f16\u53f7<br>
	 * C type : TThostFtdcOrderLocalIDType
	 */
	@Array({ 13 })
	@Field(7)
	public Pointer<Byte> QuoteLocalID() {
		return io.getPointerField(this, 7);
	}
	
	/**
	 * \u64cd\u4f5c\u672c\u5730\u7f16\u53f7<br>
	 * C type : TThostFtdcOrderLocalIDType
	 */
	@Array({ 13 })
	@Field(8)
	public Pointer<Byte> ActionLocalID() {
		return io.getPointerField(this, 8);
	}
	
	/**
	 * \u4f1a\u5458\u4ee3\u7801<br>
	 * C type : TThostFtdcParticipantIDType
	 */
	@Array({ 11 })
	@Field(9)
	public Pointer<Byte> ParticipantID() {
		return io.getPointerField(this, 9);
	}
	
	/**
	 * \u5ba2\u6237\u4ee3\u7801<br>
	 * C type : TThostFtdcClientIDType
	 */
	@Array({ 11 })
	@Field(10)
	public Pointer<Byte> ClientID() {
		return io.getPointerField(this, 10);
	}
	
	/**
	 * \u4e1a\u52a1\u5355\u5143<br>
	 * C type : TThostFtdcBusinessUnitType
	 */
	@Array({ 21 })
	@Field(11)
	public Pointer<Byte> BusinessUnit() {
		return io.getPointerField(this, 11);
	}
	
	/**
	 * \u62a5\u5355\u64cd\u4f5c\u72b6\u6001<br>
	 * C type : TThostFtdcOrderActionStatusType
	 */
	@Field(12)
	public byte OrderActionStatus() {
		return io.getByteField(this, 12);
	}
	
	/**
	 * \u62a5\u5355\u64cd\u4f5c\u72b6\u6001<br>
	 * C type : TThostFtdcOrderActionStatusType
	 */
	@Field(12)
	public CThostFtdcExchangeQuoteActionField OrderActionStatus(byte OrderActionStatus) {
		io.setByteField(this, 12, OrderActionStatus);
		return this;
	}
	
	/**
	 * \u7528\u6237\u4ee3\u7801<br>
	 * C type : TThostFtdcUserIDType
	 */
	@Array({ 16 })
	@Field(13)
	public Pointer<Byte> UserID() {
		return io.getPointerField(this, 13);
	}
	
	public CThostFtdcExchangeQuoteActionField() {
		super();
	}
	
	public CThostFtdcExchangeQuoteActionField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}