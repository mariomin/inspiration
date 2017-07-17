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
 * \u8f93\u5165\u62a5\u5355<br>
 * <i>native declaration : line 7497</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcInputOrderField")
@Library("thosttraderapi")
public class CThostFtdcInputOrderField extends StructObject {
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
	 * \u62a5\u5355\u5f15\u7528<br>
	 * C type : TThostFtdcOrderRefType
	 */
	@Array({ 13 })
	@Field(3)
	public Pointer<Byte> OrderRef() {
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
	 * \u62a5\u5355\u4ef7\u683c\u6761\u4ef6<br>
	 * C type : TThostFtdcOrderPriceTypeType
	 */
	@Field(5)
	public byte OrderPriceType() {
		return io.getByteField(this, 5);
	}
	
	/**
	 * \u62a5\u5355\u4ef7\u683c\u6761\u4ef6<br>
	 * C type : TThostFtdcOrderPriceTypeType
	 */
	@Field(5)
	public CThostFtdcInputOrderField OrderPriceType(byte OrderPriceType) {
		io.setByteField(this, 5, OrderPriceType);
		return this;
	}
	
	/**
	 * \u4e70\u5356\u65b9\u5411<br>
	 * C type : TThostFtdcDirectionType
	 */
	@Field(6)
	public byte Direction() {
		return io.getByteField(this, 6);
	}
	
	/**
	 * \u4e70\u5356\u65b9\u5411<br>
	 * C type : TThostFtdcDirectionType
	 */
	@Field(6)
	public CThostFtdcInputOrderField Direction(byte Direction) {
		io.setByteField(this, 6, Direction);
		return this;
	}
	
	/**
	 * \u7ec4\u5408\u5f00\u5e73\u6807\u5fd7<br>
	 * C type : TThostFtdcCombOffsetFlagType
	 */
	@Array({ 5 })
	@Field(7)
	public Pointer<Byte> CombOffsetFlag() {
		return io.getPointerField(this, 7);
	}
	
	/**
	 * \u7ec4\u5408\u6295\u673a\u5957\u4fdd\u6807\u5fd7<br>
	 * C type : TThostFtdcCombHedgeFlagType
	 */
	@Array({ 5 })
	@Field(8)
	public Pointer<Byte> CombHedgeFlag() {
		return io.getPointerField(this, 8);
	}
	
	/**
	 * \u4ef7\u683c<br>
	 * C type : TThostFtdcPriceType
	 */
	@Field(9)
	public double LimitPrice() {
		return io.getDoubleField(this, 9);
	}
	
	/**
	 * \u4ef7\u683c<br>
	 * C type : TThostFtdcPriceType
	 */
	@Field(9)
	public CThostFtdcInputOrderField LimitPrice(double LimitPrice) {
		io.setDoubleField(this, 9, LimitPrice);
		return this;
	}
	
	/**
	 * \u6570\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(10)
	public int VolumeTotalOriginal() {
		return io.getIntField(this, 10);
	}
	
	/**
	 * \u6570\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(10)
	public CThostFtdcInputOrderField VolumeTotalOriginal(int VolumeTotalOriginal) {
		io.setIntField(this, 10, VolumeTotalOriginal);
		return this;
	}
	
	/**
	 * \u6709\u6548\u671f\u7c7b\u578b<br>
	 * C type : TThostFtdcTimeConditionType
	 */
	@Field(11)
	public byte TimeCondition() {
		return io.getByteField(this, 11);
	}
	
	/**
	 * \u6709\u6548\u671f\u7c7b\u578b<br>
	 * C type : TThostFtdcTimeConditionType
	 */
	@Field(11)
	public CThostFtdcInputOrderField TimeCondition(byte TimeCondition) {
		io.setByteField(this, 11, TimeCondition);
		return this;
	}
	
	/**
	 * GTD\u65e5\u671f<br>
	 * C type : TThostFtdcDateType
	 */
	@Array({ 9 })
	@Field(12)
	public Pointer<Byte> GTDDate() {
		return io.getPointerField(this, 12);
	}
	
	/**
	 * \u6210\u4ea4\u91cf\u7c7b\u578b<br>
	 * C type : TThostFtdcVolumeConditionType
	 */
	@Field(13)
	public byte VolumeCondition() {
		return io.getByteField(this, 13);
	}
	
	/**
	 * \u6210\u4ea4\u91cf\u7c7b\u578b<br>
	 * C type : TThostFtdcVolumeConditionType
	 */
	@Field(13)
	public CThostFtdcInputOrderField VolumeCondition(byte VolumeCondition) {
		io.setByteField(this, 13, VolumeCondition);
		return this;
	}
	
	/**
	 * \u6700\u5c0f\u6210\u4ea4\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(14)
	public int MinVolume() {
		return io.getIntField(this, 14);
	}
	
	/**
	 * \u6700\u5c0f\u6210\u4ea4\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(14)
	public CThostFtdcInputOrderField MinVolume(int MinVolume) {
		io.setIntField(this, 14, MinVolume);
		return this;
	}
	
	/**
	 * \u89e6\u53d1\u6761\u4ef6<br>
	 * C type : TThostFtdcContingentConditionType
	 */
	@Field(15)
	public byte ContingentCondition() {
		return io.getByteField(this, 15);
	}
	
	/**
	 * \u89e6\u53d1\u6761\u4ef6<br>
	 * C type : TThostFtdcContingentConditionType
	 */
	@Field(15)
	public CThostFtdcInputOrderField ContingentCondition(byte ContingentCondition) {
		io.setByteField(this, 15, ContingentCondition);
		return this;
	}
	
	/**
	 * \u6b62\u635f\u4ef7<br>
	 * C type : TThostFtdcPriceType
	 */
	@Field(16)
	public double StopPrice() {
		return io.getDoubleField(this, 16);
	}
	
	/**
	 * \u6b62\u635f\u4ef7<br>
	 * C type : TThostFtdcPriceType
	 */
	@Field(16)
	public CThostFtdcInputOrderField StopPrice(double StopPrice) {
		io.setDoubleField(this, 16, StopPrice);
		return this;
	}
	
	/**
	 * \u5f3a\u5e73\u539f\u56e0<br>
	 * C type : TThostFtdcForceCloseReasonType
	 */
	@Field(17)
	public byte ForceCloseReason() {
		return io.getByteField(this, 17);
	}
	
	/**
	 * \u5f3a\u5e73\u539f\u56e0<br>
	 * C type : TThostFtdcForceCloseReasonType
	 */
	@Field(17)
	public CThostFtdcInputOrderField ForceCloseReason(byte ForceCloseReason) {
		io.setByteField(this, 17, ForceCloseReason);
		return this;
	}
	
	/**
	 * \u81ea\u52a8\u6302\u8d77\u6807\u5fd7<br>
	 * C type : TThostFtdcBoolType
	 */
	@Field(18)
	public int IsAutoSuspend() {
		return io.getIntField(this, 18);
	}
	
	/**
	 * \u81ea\u52a8\u6302\u8d77\u6807\u5fd7<br>
	 * C type : TThostFtdcBoolType
	 */
	@Field(18)
	public CThostFtdcInputOrderField IsAutoSuspend(int IsAutoSuspend) {
		io.setIntField(this, 18, IsAutoSuspend);
		return this;
	}
	
	/**
	 * \u4e1a\u52a1\u5355\u5143<br>
	 * C type : TThostFtdcBusinessUnitType
	 */
	@Array({ 21 })
	@Field(19)
	public Pointer<Byte> BusinessUnit() {
		return io.getPointerField(this, 19);
	}
	
	/**
	 * \u8bf7\u6c42\u7f16\u53f7<br>
	 * C type : TThostFtdcRequestIDType
	 */
	@Field(20)
	public int RequestID() {
		return io.getIntField(this, 20);
	}
	
	/**
	 * \u8bf7\u6c42\u7f16\u53f7<br>
	 * C type : TThostFtdcRequestIDType
	 */
	@Field(20)
	public CThostFtdcInputOrderField RequestID(int RequestID) {
		io.setIntField(this, 20, RequestID);
		return this;
	}
	
	/**
	 * \u7528\u6237\u5f3a\u8bc4\u6807\u5fd7<br>
	 * C type : TThostFtdcBoolType
	 */
	@Field(21)
	public int UserForceClose() {
		return io.getIntField(this, 21);
	}
	
	/**
	 * \u7528\u6237\u5f3a\u8bc4\u6807\u5fd7<br>
	 * C type : TThostFtdcBoolType
	 */
	@Field(21)
	public CThostFtdcInputOrderField UserForceClose(int UserForceClose) {
		io.setIntField(this, 21, UserForceClose);
		return this;
	}
	
	/**
	 * \u4e92\u6362\u5355\u6807\u5fd7<br>
	 * C type : TThostFtdcBoolType
	 */
	@Field(22)
	public int IsSwapOrder() {
		return io.getIntField(this, 22);
	}
	
	/**
	 * \u4e92\u6362\u5355\u6807\u5fd7<br>
	 * C type : TThostFtdcBoolType
	 */
	@Field(22)
	public CThostFtdcInputOrderField IsSwapOrder(int IsSwapOrder) {
		io.setIntField(this, 22, IsSwapOrder);
		return this;
	}
	
	public CThostFtdcInputOrderField() {
		super();
	}
	
	public CThostFtdcInputOrderField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
	
	public void setBrokerID(String brokerID) {
		io.getPointerField(this, 0).setCString(brokerID);
	}
	
	public void setInvestorID(String investorID) {
		io.getPointerField(this, 1).setCString(investorID);
	}
	
	public void setInstrumentID(String instrumentID) {
		io.getPointerField(this, 2).setCString(instrumentID);
	}
	
	public void setOrderRef(String orderRef) {
		io.getPointerField(this, 3).setCString(orderRef);
	}
	
	public void setUserID(String userID) {
		io.getPointerField(this, 4).setCString(userID);
	}
	
	public void setOrderPriceType(byte orderPriceType) {
		io.setByteField(this, 5, orderPriceType);
	}
	
	public void setDirection(byte direction) {
		io.setByteField(this, 6, direction);
	}
	
	public void setCombOffsetFlag(String combOffsetFlag) {
		io.getPointerField(this, 7).setCString(combOffsetFlag);
	}
	
	public void setCombHedgeFlag(String combHedgeFlag) {
		io.getPointerField(this, 8).setCString(combHedgeFlag);
	}
	
	public void setLimitPrice(double limitPrice) {
		io.setDoubleField(this, 9, limitPrice);
	}
	
	public void setVolumeTotalOriginal(int volumeTotalOriginal) {
		io.setIntField(this, 10, volumeTotalOriginal);
	}
	
	public void setTimeCondition(byte timeCondition) {
		io.setByteField(this, 11, timeCondition);
	}
	
	public void setGTDDate(String gtdDate) {
		io.getPointerField(this, 12).setCString(gtdDate);
	}
	
	public void setVolumeCondition(byte volumeCondition) {
		io.setByteField(this, 13, volumeCondition);
	}
	
	public void setMinVolume(int minVolume) {
		io.setIntField(this, 14, minVolume);
	}
	
	public void setContingentCondition(byte contingentCondition) {
		io.setByteField(this, 15, contingentCondition);
	}
	
	public void setStopPrice(double stopPrice) {
		io.setDoubleField(this, 16, stopPrice);
	}
	
	public void setForceCloseReason(byte forceCloseReason) {
		io.setByteField(this, 17, forceCloseReason);
	}
	
	public void setIsAutoSuspend(int isAutoSuspend) {
		io.setIntField(this, 18, isAutoSuspend);
	}
	
	public void setBusinessUnit(String businessUnit) {
		io.getPointerField(this, 19).setCString(businessUnit);
	}
	
	public void setRequestID(int requestID) {
		io.setIntField(this, 20, requestID);
	}
	
	public void setUserForceClose(int userForceClose) {
		io.setIntField(this, 21, userForceClose);
	}
	
	public void setIsSwapOrder(int isSwapOrder) {
		io.setIntField(this, 22, isSwapOrder);
	}
}
