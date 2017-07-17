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
 * \u9519\u8bef\u6267\u884c\u5ba3\u544a<br>
 * <i>native declaration : line 9310</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcErrExecOrderField")
@Library("thosttraderapi")
public class CThostFtdcErrExecOrderField extends StructObject {
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
	 * \u5408\u7ea6\u4ee3\u7801<br>
	 * C type : TThostFtdcInstrumentIDType
	 */
	@Array({ 31 })
	@Field(2)
	public Pointer<Byte> InstrumentID() {
		return io.getPointerField(this, 2);
	}
	
	/**
	 * \u6267\u884c\u5ba3\u544a\u5f15\u7528<br>
	 * C type : TThostFtdcOrderRefType
	 */
	@Array({ 13 })
	@Field(3)
	public Pointer<Byte> ExecOrderRef() {
		return io.getPointerField(this, 3);
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
	 * \u6570\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(5)
	public int Volume() {
		return io.getIntField(this, 5);
	}
	
	/**
	 * \u6570\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(5)
	public CThostFtdcErrExecOrderField Volume(int Volume) {
		io.setIntField(this, 5, Volume);
		return this;
	}
	
	/**
	 * \u8bf7\u6c42\u7f16\u53f7<br>
	 * C type : TThostFtdcRequestIDType
	 */
	@Field(6)
	public int RequestID() {
		return io.getIntField(this, 6);
	}
	
	/**
	 * \u8bf7\u6c42\u7f16\u53f7<br>
	 * C type : TThostFtdcRequestIDType
	 */
	@Field(6)
	public CThostFtdcErrExecOrderField RequestID(int RequestID) {
		io.setIntField(this, 6, RequestID);
		return this;
	}
	
	/**
	 * \u4e1a\u52a1\u5355\u5143<br>
	 * C type : TThostFtdcBusinessUnitType
	 */
	@Array({ 21 })
	@Field(7)
	public Pointer<Byte> BusinessUnit() {
		return io.getPointerField(this, 7);
	}
	
	/**
	 * \u5f00\u5e73\u6807\u5fd7<br>
	 * C type : TThostFtdcOffsetFlagType
	 */
	@Field(8)
	public byte OffsetFlag() {
		return io.getByteField(this, 8);
	}
	
	/**
	 * \u5f00\u5e73\u6807\u5fd7<br>
	 * C type : TThostFtdcOffsetFlagType
	 */
	@Field(8)
	public CThostFtdcErrExecOrderField OffsetFlag(byte OffsetFlag) {
		io.setByteField(this, 8, OffsetFlag);
		return this;
	}
	
	/**
	 * \u6295\u673a\u5957\u4fdd\u6807\u5fd7<br>
	 * C type : TThostFtdcHedgeFlagType
	 */
	@Field(9)
	public byte HedgeFlag() {
		return io.getByteField(this, 9);
	}
	
	/**
	 * \u6295\u673a\u5957\u4fdd\u6807\u5fd7<br>
	 * C type : TThostFtdcHedgeFlagType
	 */
	@Field(9)
	public CThostFtdcErrExecOrderField HedgeFlag(byte HedgeFlag) {
		io.setByteField(this, 9, HedgeFlag);
		return this;
	}
	
	/**
	 * \u6267\u884c\u7c7b\u578b<br>
	 * C type : TThostFtdcActionTypeType
	 */
	@Field(10)
	public byte ActionType() {
		return io.getByteField(this, 10);
	}
	
	/**
	 * \u6267\u884c\u7c7b\u578b<br>
	 * C type : TThostFtdcActionTypeType
	 */
	@Field(10)
	public CThostFtdcErrExecOrderField ActionType(byte ActionType) {
		io.setByteField(this, 10, ActionType);
		return this;
	}
	
	/**
	 * \u4fdd\u7559\u5934\u5bf8\u7533\u8bf7\u7684\u6301\u4ed3\u65b9\u5411<br>
	 * C type : TThostFtdcPosiDirectionType
	 */
	@Field(11)
	public byte PosiDirection() {
		return io.getByteField(this, 11);
	}
	
	/**
	 * \u4fdd\u7559\u5934\u5bf8\u7533\u8bf7\u7684\u6301\u4ed3\u65b9\u5411<br>
	 * C type : TThostFtdcPosiDirectionType
	 */
	@Field(11)
	public CThostFtdcErrExecOrderField PosiDirection(byte PosiDirection) {
		io.setByteField(this, 11, PosiDirection);
		return this;
	}
	
	/**
	 * \u671f\u6743\u884c\u6743\u540e\u662f\u5426\u4fdd\u7559\u671f\u8d27\u5934\u5bf8\u7684\u6807\u8bb0<br>
	 * C type : TThostFtdcExecOrderPositionFlagType
	 */
	@Field(12)
	public byte ReservePositionFlag() {
		return io.getByteField(this, 12);
	}
	
	/**
	 * \u671f\u6743\u884c\u6743\u540e\u662f\u5426\u4fdd\u7559\u671f\u8d27\u5934\u5bf8\u7684\u6807\u8bb0<br>
	 * C type : TThostFtdcExecOrderPositionFlagType
	 */
	@Field(12)
	public CThostFtdcErrExecOrderField ReservePositionFlag(byte ReservePositionFlag) {
		io.setByteField(this, 12, ReservePositionFlag);
		return this;
	}
	
	/**
	 * \u671f\u6743\u884c\u6743\u540e\u751f\u6210\u7684\u5934\u5bf8\u662f\u5426\u81ea\u52a8\u5e73\u4ed3<br>
	 * C type : TThostFtdcExecOrderCloseFlagType
	 */
	@Field(13)
	public byte CloseFlag() {
		return io.getByteField(this, 13);
	}
	
	/**
	 * \u671f\u6743\u884c\u6743\u540e\u751f\u6210\u7684\u5934\u5bf8\u662f\u5426\u81ea\u52a8\u5e73\u4ed3<br>
	 * C type : TThostFtdcExecOrderCloseFlagType
	 */
	@Field(13)
	public CThostFtdcErrExecOrderField CloseFlag(byte CloseFlag) {
		io.setByteField(this, 13, CloseFlag);
		return this;
	}
	
	/**
	 * \u9519\u8bef\u4ee3\u7801<br>
	 * C type : TThostFtdcErrorIDType
	 */
	@Field(14)
	public int ErrorID() {
		return io.getIntField(this, 14);
	}
	
	/**
	 * \u9519\u8bef\u4ee3\u7801<br>
	 * C type : TThostFtdcErrorIDType
	 */
	@Field(14)
	public CThostFtdcErrExecOrderField ErrorID(int ErrorID) {
		io.setIntField(this, 14, ErrorID);
		return this;
	}
	
	/**
	 * \u9519\u8bef\u4fe1\u606f<br>
	 * C type : TThostFtdcErrorMsgType
	 */
	@Array({ 81 })
	@Field(15)
	public Pointer<Byte> ErrorMsg() {
		return io.getPointerField(this, 15);
	}
	
	public CThostFtdcErrExecOrderField() {
		super();
	}
	
	public CThostFtdcErrExecOrderField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
