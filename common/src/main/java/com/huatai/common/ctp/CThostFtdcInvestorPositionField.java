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
 * \u6295\u8d44\u8005\u6301\u4ed3<br>
 * <i>native declaration : line 6938</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcInvestorPositionField")
@Library("thosttraderapi")
public class CThostFtdcInvestorPositionField extends StructObject {
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
	 * \u7ecf\u7eaa\u516c\u53f8\u4ee3\u7801<br>
	 * C type : TThostFtdcBrokerIDType
	 */
	@Array({ 11 })
	@Field(1)
	public Pointer<Byte> BrokerID() {
		return io.getPointerField(this, 1);
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
	 * \u6301\u4ed3\u591a\u7a7a\u65b9\u5411<br>
	 * C type : TThostFtdcPosiDirectionType
	 */
	@Field(3)
	public byte PosiDirection() {
		return io.getByteField(this, 3);
	}
	
	/**
	 * \u6301\u4ed3\u591a\u7a7a\u65b9\u5411<br>
	 * C type : TThostFtdcPosiDirectionType
	 */
	@Field(3)
	public CThostFtdcInvestorPositionField PosiDirection(byte PosiDirection) {
		io.setByteField(this, 3, PosiDirection);
		return this;
	}
	
	/**
	 * \u6295\u673a\u5957\u4fdd\u6807\u5fd7<br>
	 * C type : TThostFtdcHedgeFlagType
	 */
	@Field(4)
	public byte HedgeFlag() {
		return io.getByteField(this, 4);
	}
	
	/**
	 * \u6295\u673a\u5957\u4fdd\u6807\u5fd7<br>
	 * C type : TThostFtdcHedgeFlagType
	 */
	@Field(4)
	public CThostFtdcInvestorPositionField HedgeFlag(byte HedgeFlag) {
		io.setByteField(this, 4, HedgeFlag);
		return this;
	}
	
	/**
	 * \u6301\u4ed3\u65e5\u671f<br>
	 * C type : TThostFtdcPositionDateType
	 */
	@Field(5)
	public byte PositionDate() {
		return io.getByteField(this, 5);
	}
	
	/**
	 * \u6301\u4ed3\u65e5\u671f<br>
	 * C type : TThostFtdcPositionDateType
	 */
	@Field(5)
	public CThostFtdcInvestorPositionField PositionDate(byte PositionDate) {
		io.setByteField(this, 5, PositionDate);
		return this;
	}
	
	/**
	 * \u4e0a\u65e5\u6301\u4ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(6)
	public int YdPosition() {
		return io.getIntField(this, 6);
	}
	
	/**
	 * \u4e0a\u65e5\u6301\u4ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(6)
	public CThostFtdcInvestorPositionField YdPosition(int YdPosition) {
		io.setIntField(this, 6, YdPosition);
		return this;
	}
	
	/**
	 * \u4eca\u65e5\u6301\u4ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(7)
	public int Position() {
		return io.getIntField(this, 7);
	}
	
	/**
	 * \u4eca\u65e5\u6301\u4ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(7)
	public CThostFtdcInvestorPositionField Position(int Position) {
		io.setIntField(this, 7, Position);
		return this;
	}
	
	/**
	 * \u591a\u5934\u51bb\u7ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(8)
	public int LongFrozen() {
		return io.getIntField(this, 8);
	}
	
	/**
	 * \u591a\u5934\u51bb\u7ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(8)
	public CThostFtdcInvestorPositionField LongFrozen(int LongFrozen) {
		io.setIntField(this, 8, LongFrozen);
		return this;
	}
	
	/**
	 * \u7a7a\u5934\u51bb\u7ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(9)
	public int ShortFrozen() {
		return io.getIntField(this, 9);
	}
	
	/**
	 * \u7a7a\u5934\u51bb\u7ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(9)
	public CThostFtdcInvestorPositionField ShortFrozen(int ShortFrozen) {
		io.setIntField(this, 9, ShortFrozen);
		return this;
	}
	
	/**
	 * \u5f00\u4ed3\u51bb\u7ed3\u91d1\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(10)
	public double LongFrozenAmount() {
		return io.getDoubleField(this, 10);
	}
	
	/**
	 * \u5f00\u4ed3\u51bb\u7ed3\u91d1\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(10)
	public CThostFtdcInvestorPositionField LongFrozenAmount(double LongFrozenAmount) {
		io.setDoubleField(this, 10, LongFrozenAmount);
		return this;
	}
	
	/**
	 * \u5f00\u4ed3\u51bb\u7ed3\u91d1\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(11)
	public double ShortFrozenAmount() {
		return io.getDoubleField(this, 11);
	}
	
	/**
	 * \u5f00\u4ed3\u51bb\u7ed3\u91d1\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(11)
	public CThostFtdcInvestorPositionField ShortFrozenAmount(double ShortFrozenAmount) {
		io.setDoubleField(this, 11, ShortFrozenAmount);
		return this;
	}
	
	/**
	 * \u5f00\u4ed3\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(12)
	public int OpenVolume() {
		return io.getIntField(this, 12);
	}
	
	/**
	 * \u5f00\u4ed3\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(12)
	public CThostFtdcInvestorPositionField OpenVolume(int OpenVolume) {
		io.setIntField(this, 12, OpenVolume);
		return this;
	}
	
	/**
	 * \u5e73\u4ed3\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(13)
	public int CloseVolume() {
		return io.getIntField(this, 13);
	}
	
	/**
	 * \u5e73\u4ed3\u91cf<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(13)
	public CThostFtdcInvestorPositionField CloseVolume(int CloseVolume) {
		io.setIntField(this, 13, CloseVolume);
		return this;
	}
	
	/**
	 * \u5f00\u4ed3\u91d1\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(14)
	public double OpenAmount() {
		return io.getDoubleField(this, 14);
	}
	
	/**
	 * \u5f00\u4ed3\u91d1\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(14)
	public CThostFtdcInvestorPositionField OpenAmount(double OpenAmount) {
		io.setDoubleField(this, 14, OpenAmount);
		return this;
	}
	
	/**
	 * \u5e73\u4ed3\u91d1\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(15)
	public double CloseAmount() {
		return io.getDoubleField(this, 15);
	}
	
	/**
	 * \u5e73\u4ed3\u91d1\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(15)
	public CThostFtdcInvestorPositionField CloseAmount(double CloseAmount) {
		io.setDoubleField(this, 15, CloseAmount);
		return this;
	}
	
	/**
	 * \u6301\u4ed3\u6210\u672c<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(16)
	public double PositionCost() {
		return io.getDoubleField(this, 16);
	}
	
	/**
	 * \u6301\u4ed3\u6210\u672c<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(16)
	public CThostFtdcInvestorPositionField PositionCost(double PositionCost) {
		io.setDoubleField(this, 16, PositionCost);
		return this;
	}
	
	/**
	 * \u4e0a\u6b21\u5360\u7528\u7684\u4fdd\u8bc1\u91d1<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(17)
	public double PreMargin() {
		return io.getDoubleField(this, 17);
	}
	
	/**
	 * \u4e0a\u6b21\u5360\u7528\u7684\u4fdd\u8bc1\u91d1<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(17)
	public CThostFtdcInvestorPositionField PreMargin(double PreMargin) {
		io.setDoubleField(this, 17, PreMargin);
		return this;
	}
	
	/**
	 * \u5360\u7528\u7684\u4fdd\u8bc1\u91d1<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(18)
	public double UseMargin() {
		return io.getDoubleField(this, 18);
	}
	
	/**
	 * \u5360\u7528\u7684\u4fdd\u8bc1\u91d1<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(18)
	public CThostFtdcInvestorPositionField UseMargin(double UseMargin) {
		io.setDoubleField(this, 18, UseMargin);
		return this;
	}
	
	/**
	 * \u51bb\u7ed3\u7684\u4fdd\u8bc1\u91d1<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(19)
	public double FrozenMargin() {
		return io.getDoubleField(this, 19);
	}
	
	/**
	 * \u51bb\u7ed3\u7684\u4fdd\u8bc1\u91d1<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(19)
	public CThostFtdcInvestorPositionField FrozenMargin(double FrozenMargin) {
		io.setDoubleField(this, 19, FrozenMargin);
		return this;
	}
	
	/**
	 * \u51bb\u7ed3\u7684\u8d44\u91d1<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(20)
	public double FrozenCash() {
		return io.getDoubleField(this, 20);
	}
	
	/**
	 * \u51bb\u7ed3\u7684\u8d44\u91d1<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(20)
	public CThostFtdcInvestorPositionField FrozenCash(double FrozenCash) {
		io.setDoubleField(this, 20, FrozenCash);
		return this;
	}
	
	/**
	 * \u51bb\u7ed3\u7684\u624b\u7eed\u8d39<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(21)
	public double FrozenCommission() {
		return io.getDoubleField(this, 21);
	}
	
	/**
	 * \u51bb\u7ed3\u7684\u624b\u7eed\u8d39<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(21)
	public CThostFtdcInvestorPositionField FrozenCommission(double FrozenCommission) {
		io.setDoubleField(this, 21, FrozenCommission);
		return this;
	}
	
	/**
	 * \u8d44\u91d1\u5dee\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(22)
	public double CashIn() {
		return io.getDoubleField(this, 22);
	}
	
	/**
	 * \u8d44\u91d1\u5dee\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(22)
	public CThostFtdcInvestorPositionField CashIn(double CashIn) {
		io.setDoubleField(this, 22, CashIn);
		return this;
	}
	
	/**
	 * \u624b\u7eed\u8d39<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(23)
	public double Commission() {
		return io.getDoubleField(this, 23);
	}
	
	/**
	 * \u624b\u7eed\u8d39<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(23)
	public CThostFtdcInvestorPositionField Commission(double Commission) {
		io.setDoubleField(this, 23, Commission);
		return this;
	}
	
	/**
	 * \u5e73\u4ed3\u76c8\u4e8f<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(24)
	public double CloseProfit() {
		return io.getDoubleField(this, 24);
	}
	
	/**
	 * \u5e73\u4ed3\u76c8\u4e8f<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(24)
	public CThostFtdcInvestorPositionField CloseProfit(double CloseProfit) {
		io.setDoubleField(this, 24, CloseProfit);
		return this;
	}
	
	/**
	 * \u6301\u4ed3\u76c8\u4e8f<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(25)
	public double PositionProfit() {
		return io.getDoubleField(this, 25);
	}
	
	/**
	 * \u6301\u4ed3\u76c8\u4e8f<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(25)
	public CThostFtdcInvestorPositionField PositionProfit(double PositionProfit) {
		io.setDoubleField(this, 25, PositionProfit);
		return this;
	}
	
	/**
	 * \u4e0a\u6b21\u7ed3\u7b97\u4ef7<br>
	 * C type : TThostFtdcPriceType
	 */
	@Field(26)
	public double PreSettlementPrice() {
		return io.getDoubleField(this, 26);
	}
	
	/**
	 * \u4e0a\u6b21\u7ed3\u7b97\u4ef7<br>
	 * C type : TThostFtdcPriceType
	 */
	@Field(26)
	public CThostFtdcInvestorPositionField PreSettlementPrice(double PreSettlementPrice) {
		io.setDoubleField(this, 26, PreSettlementPrice);
		return this;
	}
	
	/**
	 * \u672c\u6b21\u7ed3\u7b97\u4ef7<br>
	 * C type : TThostFtdcPriceType
	 */
	@Field(27)
	public double SettlementPrice() {
		return io.getDoubleField(this, 27);
	}
	
	/**
	 * \u672c\u6b21\u7ed3\u7b97\u4ef7<br>
	 * C type : TThostFtdcPriceType
	 */
	@Field(27)
	public CThostFtdcInvestorPositionField SettlementPrice(double SettlementPrice) {
		io.setDoubleField(this, 27, SettlementPrice);
		return this;
	}
	
	/**
	 * \u4ea4\u6613\u65e5<br>
	 * C type : TThostFtdcDateType
	 */
	@Array({ 9 })
	@Field(28)
	public Pointer<Byte> TradingDay() {
		return io.getPointerField(this, 28);
	}
	
	/**
	 * \u7ed3\u7b97\u7f16\u53f7<br>
	 * C type : TThostFtdcSettlementIDType
	 */
	@Field(29)
	public int SettlementID() {
		return io.getIntField(this, 29);
	}
	
	/**
	 * \u7ed3\u7b97\u7f16\u53f7<br>
	 * C type : TThostFtdcSettlementIDType
	 */
	@Field(29)
	public CThostFtdcInvestorPositionField SettlementID(int SettlementID) {
		io.setIntField(this, 29, SettlementID);
		return this;
	}
	
	/**
	 * \u5f00\u4ed3\u6210\u672c<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(30)
	public double OpenCost() {
		return io.getDoubleField(this, 30);
	}
	
	/**
	 * \u5f00\u4ed3\u6210\u672c<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(30)
	public CThostFtdcInvestorPositionField OpenCost(double OpenCost) {
		io.setDoubleField(this, 30, OpenCost);
		return this;
	}
	
	/**
	 * \u4ea4\u6613\u6240\u4fdd\u8bc1\u91d1<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(31)
	public double ExchangeMargin() {
		return io.getDoubleField(this, 31);
	}
	
	/**
	 * \u4ea4\u6613\u6240\u4fdd\u8bc1\u91d1<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(31)
	public CThostFtdcInvestorPositionField ExchangeMargin(double ExchangeMargin) {
		io.setDoubleField(this, 31, ExchangeMargin);
		return this;
	}
	
	/**
	 * \u7ec4\u5408\u6210\u4ea4\u5f62\u6210\u7684\u6301\u4ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(32)
	public int CombPosition() {
		return io.getIntField(this, 32);
	}
	
	/**
	 * \u7ec4\u5408\u6210\u4ea4\u5f62\u6210\u7684\u6301\u4ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(32)
	public CThostFtdcInvestorPositionField CombPosition(int CombPosition) {
		io.setIntField(this, 32, CombPosition);
		return this;
	}
	
	/**
	 * \u7ec4\u5408\u591a\u5934\u51bb\u7ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(33)
	public int CombLongFrozen() {
		return io.getIntField(this, 33);
	}
	
	/**
	 * \u7ec4\u5408\u591a\u5934\u51bb\u7ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(33)
	public CThostFtdcInvestorPositionField CombLongFrozen(int CombLongFrozen) {
		io.setIntField(this, 33, CombLongFrozen);
		return this;
	}
	
	/**
	 * \u7ec4\u5408\u7a7a\u5934\u51bb\u7ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(34)
	public int CombShortFrozen() {
		return io.getIntField(this, 34);
	}
	
	/**
	 * \u7ec4\u5408\u7a7a\u5934\u51bb\u7ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(34)
	public CThostFtdcInvestorPositionField CombShortFrozen(int CombShortFrozen) {
		io.setIntField(this, 34, CombShortFrozen);
		return this;
	}
	
	/**
	 * \u9010\u65e5\u76ef\u5e02\u5e73\u4ed3\u76c8\u4e8f<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(35)
	public double CloseProfitByDate() {
		return io.getDoubleField(this, 35);
	}
	
	/**
	 * \u9010\u65e5\u76ef\u5e02\u5e73\u4ed3\u76c8\u4e8f<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(35)
	public CThostFtdcInvestorPositionField CloseProfitByDate(double CloseProfitByDate) {
		io.setDoubleField(this, 35, CloseProfitByDate);
		return this;
	}
	
	/**
	 * \u9010\u7b14\u5bf9\u51b2\u5e73\u4ed3\u76c8\u4e8f<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(36)
	public double CloseProfitByTrade() {
		return io.getDoubleField(this, 36);
	}
	
	/**
	 * \u9010\u7b14\u5bf9\u51b2\u5e73\u4ed3\u76c8\u4e8f<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(36)
	public CThostFtdcInvestorPositionField CloseProfitByTrade(double CloseProfitByTrade) {
		io.setDoubleField(this, 36, CloseProfitByTrade);
		return this;
	}
	
	/**
	 * \u4eca\u65e5\u6301\u4ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(37)
	public int TodayPosition() {
		return io.getIntField(this, 37);
	}
	
	/**
	 * \u4eca\u65e5\u6301\u4ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(37)
	public CThostFtdcInvestorPositionField TodayPosition(int TodayPosition) {
		io.setIntField(this, 37, TodayPosition);
		return this;
	}
	
	/**
	 * \u4fdd\u8bc1\u91d1\u7387<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(38)
	public double MarginRateByMoney() {
		return io.getDoubleField(this, 38);
	}
	
	/**
	 * \u4fdd\u8bc1\u91d1\u7387<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(38)
	public CThostFtdcInvestorPositionField MarginRateByMoney(double MarginRateByMoney) {
		io.setDoubleField(this, 38, MarginRateByMoney);
		return this;
	}
	
	/**
	 * \u4fdd\u8bc1\u91d1\u7387(\u6309\u624b\u6570)<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(39)
	public double MarginRateByVolume() {
		return io.getDoubleField(this, 39);
	}
	
	/**
	 * \u4fdd\u8bc1\u91d1\u7387(\u6309\u624b\u6570)<br>
	 * C type : TThostFtdcRatioType
	 */
	@Field(39)
	public CThostFtdcInvestorPositionField MarginRateByVolume(double MarginRateByVolume) {
		io.setDoubleField(this, 39, MarginRateByVolume);
		return this;
	}
	
	/**
	 * \u6267\u884c\u51bb\u7ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(40)
	public int StrikeFrozen() {
		return io.getIntField(this, 40);
	}
	
	/**
	 * \u6267\u884c\u51bb\u7ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(40)
	public CThostFtdcInvestorPositionField StrikeFrozen(int StrikeFrozen) {
		io.setIntField(this, 40, StrikeFrozen);
		return this;
	}
	
	/**
	 * \u6267\u884c\u51bb\u7ed3\u91d1\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(41)
	public double StrikeFrozenAmount() {
		return io.getDoubleField(this, 41);
	}
	
	/**
	 * \u6267\u884c\u51bb\u7ed3\u91d1\u989d<br>
	 * C type : TThostFtdcMoneyType
	 */
	@Field(41)
	public CThostFtdcInvestorPositionField StrikeFrozenAmount(double StrikeFrozenAmount) {
		io.setDoubleField(this, 41, StrikeFrozenAmount);
		return this;
	}
	
	/**
	 * \u653e\u5f03\u6267\u884c\u51bb\u7ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(42)
	public int AbandonFrozen() {
		return io.getIntField(this, 42);
	}
	
	/**
	 * \u653e\u5f03\u6267\u884c\u51bb\u7ed3<br>
	 * C type : TThostFtdcVolumeType
	 */
	@Field(42)
	public CThostFtdcInvestorPositionField AbandonFrozen(int AbandonFrozen) {
		io.setIntField(this, 42, AbandonFrozen);
		return this;
	}
	
	public CThostFtdcInvestorPositionField() {
		super();
	}
	
	public CThostFtdcInvestorPositionField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}