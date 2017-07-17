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
 * \u5408\u7ea6<br>
 * <i>native declaration : line 6650</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcInstrumentField")
@Library("thosttraderapi")
public class CThostFtdcInstrumentField extends StructObject {
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
	 * \u4ea4\u6613\u6240\u4ee3\u7801<br>
	 * C type : TThostFtdcExchangeIDType
	 */
	@Array({ 9 })
	@Field(1)
	public Pointer<Byte> ExchangeID() {
		return io.getPointerField(this, 1);
	}
	
	/**
	 * \u5408\u7ea6\u540d\u79f0<br>
	 * C type : TThostFtdcInstrumentNameType
	 */
	@Array({ 21 })
	@Field(2)
	public Pointer<Byte> InstrumentName() {
		return io.getPointerField(this, 2);
	}
	
	/**
	 * \u5408\u7ea6\u5728\u4ea4\u6613\u6240\u7684\u4ee3\u7801<br>
	 * C type : TThostFtdcExchangeInstIDType
	 */
	@Array({ 31 })
	@Field(3)
	public Pointer<Byte> ExchangeInstID() {
		return io.getPointerField(this, 3);
	}
	
	/**
	 * \u4ea7\u54c1\u4ee3\u7801<br>
	 * C type : TThostFtdcInstrumentIDType
	 */
	@Array({ 31 })
	@Field(4)
	public Pointer<Byte> ProductID() {
		return io.getPointerField(this, 4);
	}
	
	/**
	 * \u4ea7\u54c1\u7c7b\u578b<br>
	 * C type : TThostFtdcProductClassType
	 */
	@Field(5)
	public byte ProductClass() {
		return io.getByteField(this, 5);
	}
	
	/**
	 * \u4ea7\u54c1\u7c7b\u578b<br>
	 * C type : TThostFtdcProductClassType
	 */
	@Field(5)
	public CThostFtdcInstrumentField ProductClass(byte ProductClass) {
		io.setByteField(this, 5, ProductClass);
		return this;
	}
	
	/**
	 * \u4ea4\u5272\u5e74\u4efd<br>
	 * C type : TThostFtdcYearType
	 */
	@Field(6)
	public int DeliveryYear() {
		return io.getIntField(this, 6);
	}
	
	/**
	 * \u4ea4\u5272\u5e74\u4efd<br>
	 * C type : TThostFtdcYearType
	 */
	@Field(6)
	public CThostFtdcInstrumentField DeliveryYear(int DeliveryYear) {
		io.setIntField(this, 6, DeliveryYear);
		return this;
	}
	
	/**
	 * \u4ea4\u5272\u6708<br>
	 * C type : TThostFtdcMonthType
	 */
	@Field(7)
	public int DeliveryMonth() {
		return io.getIntField(this, 7);
	}
	
	/**
	 * \u4ea4\u5272\u6708<br>
	 * C type : TThostFtdcMonthType
	 */
	@Field(7)
	public CThostFtdcInstrumentField DeliveryMonth(int DeliveryMonth) {
		io.setIntField(this, 7, DeliveryMonth);
		return this;
	}
	
	/**
	 * \u5e02\u4ef7\u5355\u6700\u5927\u4e0b\u5355\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(8)
	public int MaxMarketOrderVolume() {
		return io.getIntField(this, 8);
	}
	
	/**
	 * \u5e02\u4ef7\u5355\u6700\u5927\u4e0b\u5355\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(8)
	public CThostFtdcInstrumentField MaxMarketOrderVolume(int MaxMarketOrderVolume) {
		io.setIntField(this, 8, MaxMarketOrderVolume);
		return this;
	}
	
	/**
	 * \u5e02\u4ef7\u5355\u6700\u5c0f\u4e0b\u5355\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(9)
	public int MinMarketOrderVolume() {
		return io.getIntField(this, 9);
	}
	
	/**
	 * \u5e02\u4ef7\u5355\u6700\u5c0f\u4e0b\u5355\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(9)
	public CThostFtdcInstrumentField MinMarketOrderVolume(int MinMarketOrderVolume) {
		io.setIntField(this, 9, MinMarketOrderVolume);
		return this;
	}
	
	/**
	 * \u9650\u4ef7\u5355\u6700\u5927\u4e0b\u5355\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(10)
	public int MaxLimitOrderVolume() {
		return io.getIntField(this, 10);
	}
	
	/**
	 * \u9650\u4ef7\u5355\u6700\u5927\u4e0b\u5355\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(10)
	public CThostFtdcInstrumentField MaxLimitOrderVolume(int MaxLimitOrderVolume) {
		io.setIntField(this, 10, MaxLimitOrderVolume);
		return this;
	}
	
	/**
	 * \u9650\u4ef7\u5355\u6700\u5c0f\u4e0b\u5355\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(11)
	public int MinLimitOrderVolume() {
		return io.getIntField(this, 11);
	}
	
	/**
	 * \u9650\u4ef7\u5355\u6700\u5c0f\u4e0b\u5355\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(11)
	public CThostFtdcInstrumentField MinLimitOrderVolume(int MinLimitOrderVolume) {
		io.setIntField(this, 11, MinLimitOrderVolume);
		return this;
	}
	
	/**
	 * \u5408\u7ea6\u6570\u91cf\u4e58\u6570<br>
	 * C type : TThostFtdcVolumeMultipleType
	 */
	@Field(12)
	public int VolumeMultiple() {
		return io.getIntField(this, 12);
	}
	
	/**
	 * \u5408\u7ea6\u6570\u91cf\u4e58\u6570<br>
	 * C type : TThostFtdcVolumeMultipleType
	 */
	@Field(12)
	public CThostFtdcInstrumentField VolumeMultiple(int VolumeMultiple) {
		io.setIntField(this, 12, VolumeMultiple);
		return this;
	}
	
	/**
	 * \u6700\u5c0f\u53d8\u52a8\u4ef7\u4f4d<br>
	 * C type : TThostFtdcPriceType
	 */
	@Field(13)
	public double PriceTick() {
		return io.getDoubleField(this, 13);
	}
	
	/**
	 * \u6700\u5c0f\u53d8\u52a8\u4ef7\u4f4d<br>
	 * C type : TThostFtdcPriceType
	 */
	@Field(13)
	public CThostFtdcInstrumentField PriceTick(double PriceTick) {
		io.setDoubleField(this, 13, PriceTick);
		return this;
	}
	
	/**
	 * \u521b\u5efa\u65e5<br>
	 * C type : TThostFtdcDateType
	 */
	@Array({ 9 })
	@Field(14)
	public Pointer<Byte> CreateDate() {
		return io.getPointerField(this, 14);
	}
	
	/**
	 * \u4e0a\u5e02\u65e5<br>
	 * C type : TThostFtdcDateType
	 */
	@Array({ 9 })
	@Field(15)
	public Pointer<Byte> OpenDate() {
		return io.getPointerField(this, 15);
	}
	
	/**
	 * \u5230\u671f\u65e5<br>
	 * C type : TThostFtdcDateType
	 */
	@Array({ 9 })
	@Field(16)
	public Pointer<Byte> ExpireDate() {
		return io.getPointerField(this, 16);
	}
	
	/**
	 * \u5f00\u59cb\u4ea4\u5272\u65e5<br>
	 * C type : TThostFtdcDateType
	 */
	@Array({ 9 })
	@Field(17)
	public Pointer<Byte> StartDelivDate() {
		return io.getPointerField(this, 17);
	}
	
	/**
	 * \u7ed3\u675f\u4ea4\u5272\u65e5<br>
	 * C type : TThostFtdcDateType
	 */
	@Array({ 9 })
	@Field(18)
	public Pointer<Byte> EndDelivDate() {
		return io.getPointerField(this, 18);
	}
	
	/**
	 * \u5408\u7ea6\u751f\u547d\u5468\u671f\u72b6\u6001<br>
	 * C type : TThostFtdcInstLifePhaseType
	 */
	@Field(19)
	public byte InstLifePhase() {
		return io.getByteField(this, 19);
	}
	
	/**
	 * \u5408\u7ea6\u751f\u547d\u5468\u671f\u72b6\u6001<br>
	 * C type : TThostFtdcInstLifePhaseType
	 */
	@Field(19)
	public CThostFtdcInstrumentField InstLifePhase(byte InstLifePhase) {
		io.setByteField(this, 19, InstLifePhase);
		return this;
	}
	
	/**
	 * \u5f53\u524d\u662f\u5426\u4ea4\u6613<br>
	 * C type : TThostFtdcBoolType
	 */
	@Field(20)
	public int IsTrading() {
		return io.getIntField(this, 20);
	}
	
	/**
	 * \u5f53\u524d\u662f\u5426\u4ea4\u6613<br>
	 * C type : TThostFtdcBoolType
	 */
	@Field(20)
	public CThostFtdcInstrumentField IsTrading(int IsTrading) {
		io.setIntField(this, 20, IsTrading);
		return this;
	}
	
	/**
	 * \u6301\u4ed3\u7c7b\u578b<br>
	 * C type : TThostFtdcPositionTypeType
	 */
	@Field(21)
	public byte PositionType() {
		return io.getByteField(this, 21);
	}
	
	/**
	 * \u6301\u4ed3\u7c7b\u578b<br>
	 * C type : TThostFtdcPositionTypeType
	 */
	@Field(21)
	public CThostFtdcInstrumentField PositionType(byte PositionType) {
		io.setByteField(this, 21, PositionType);
		return this;
	}
	
	/**
	 * \u6301\u4ed3\u65e5\u671f\u7c7b\u578b<br>
	 * C type : TThostFtdcPositionDateTypeType
	 */
	@Field(22)
	public byte PositionDateType() {
		return io.getByteField(this, 22);
	}
	
	/**
	 * \u6301\u4ed3\u65e5\u671f\u7c7b\u578b<br>
	 * C type : TThostFtdcPositionDateTypeType
	 */
	@Field(22)
	public CThostFtdcInstrumentField PositionDateType(byte PositionDateType) {
		io.setByteField(this, 22, PositionDateType);
		return this;
	}
	
	/**
	 * \u591a\u5934\u4fdd\u8bc1\u91d1\u7387<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(23)
	public double LongMarginRatio() {
		return io.getDoubleField(this, 23);
	}
	
	/**
	 * \u591a\u5934\u4fdd\u8bc1\u91d1\u7387<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(23)
	public CThostFtdcInstrumentField LongMarginRatio(double LongMarginRatio) {
		io.setDoubleField(this, 23, LongMarginRatio);
		return this;
	}
	
	/**
	 * \u7a7a\u5934\u4fdd\u8bc1\u91d1\u7387<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(24)
	public double ShortMarginRatio() {
		return io.getDoubleField(this, 24);
	}
	
	/**
	 * \u7a7a\u5934\u4fdd\u8bc1\u91d1\u7387<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(24)
	public CThostFtdcInstrumentField ShortMarginRatio(double ShortMarginRatio) {
		io.setDoubleField(this, 24, ShortMarginRatio);
		return this;
	}
	
	/**
	 * \u662f\u5426\u4f7f\u7528\u5927\u989d\u5355\u8fb9\u4fdd\u8bc1\u91d1\u7b97\u6cd5<br>
	 * C type : TThostFtdcMaxMarginSideAlgorithmType
	 */
	@Field(25)
	public byte MaxMarginSideAlgorithm() {
		return io.getByteField(this, 25);
	}
	
	/**
	 * \u662f\u5426\u4f7f\u7528\u5927\u989d\u5355\u8fb9\u4fdd\u8bc1\u91d1\u7b97\u6cd5<br>
	 * C type : TThostFtdcMaxMarginSideAlgorithmType
	 */
	@Field(25)
	public CThostFtdcInstrumentField MaxMarginSideAlgorithm(byte MaxMarginSideAlgorithm) {
		io.setByteField(this, 25, MaxMarginSideAlgorithm);
		return this;
	}
	
	/**
	 * \u57fa\u7840\u5546\u54c1\u4ee3\u7801<br>
	 * C type : TThostFtdcInstrumentIDType
	 */
	@Array({ 31 })
	@Field(26)
	public Pointer<Byte> UnderlyingInstrID() {
		return io.getPointerField(this, 26);
	}
	
	/**
	 * \u6267\u884c\u4ef7<br>
	 * C type : TThostFtdcPriceType
	 */
	@Field(27)
	public double StrikePrice() {
		return io.getDoubleField(this, 27);
	}
	
	/**
	 * \u6267\u884c\u4ef7<br>
	 * C type : TThostFtdcPriceType
	 */
	@Field(27)
	public CThostFtdcInstrumentField StrikePrice(double StrikePrice) {
		io.setDoubleField(this, 27, StrikePrice);
		return this;
	}
	
	/**
	 * \u671f\u6743\u7c7b\u578b<br>
	 * C type : TThostFtdcOptionsTypeType
	 */
	@Field(28)
	public byte OptionsType() {
		return io.getByteField(this, 28);
	}
	
	/**
	 * \u671f\u6743\u7c7b\u578b<br>
	 * C type : TThostFtdcOptionsTypeType
	 */
	@Field(28)
	public CThostFtdcInstrumentField OptionsType(byte OptionsType) {
		io.setByteField(this, 28, OptionsType);
		return this;
	}
	
	/**
	 * \u5408\u7ea6\u57fa\u7840\u5546\u54c1\u4e58\u6570<br>
	 * C type : TThostFtdcUnderlyingMultipleType
	 */
	@Field(29)
	public double UnderlyingMultiple() {
		return io.getDoubleField(this, 29);
	}
	
	/**
	 * \u5408\u7ea6\u57fa\u7840\u5546\u54c1\u4e58\u6570<br>
	 * C type : TThostFtdcUnderlyingMultipleType
	 */
	@Field(29)
	public CThostFtdcInstrumentField UnderlyingMultiple(double UnderlyingMultiple) {
		io.setDoubleField(this, 29, UnderlyingMultiple);
		return this;
	}
	
	/**
	 * \u7ec4\u5408\u7c7b\u578b<br>
	 * C type : TThostFtdcCombinationTypeType
	 */
	@Field(30)
	public byte CombinationType() {
		return io.getByteField(this, 30);
	}
	
	/**
	 * \u7ec4\u5408\u7c7b\u578b<br>
	 * C type : TThostFtdcCombinationTypeType
	 */
	@Field(30)
	public CThostFtdcInstrumentField CombinationType(byte CombinationType) {
		io.setByteField(this, 30, CombinationType);
		return this;
	}
	
	public CThostFtdcInstrumentField() {
		super();
	}
	
	public CThostFtdcInstrumentField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
