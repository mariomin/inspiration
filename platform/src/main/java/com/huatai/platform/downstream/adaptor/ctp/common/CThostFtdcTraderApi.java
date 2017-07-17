package com.huatai.platform.downstream.adaptor.ctp.common;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Library;
import org.bridj.ann.Name;
import org.bridj.ann.Virtual;
import org.bridj.cpp.CPPObject;

import com.huatai.common.ctp.CThostFtdcFensUserInfoField;
import com.huatai.common.ctp.CThostFtdcInputCombActionField;
import com.huatai.common.ctp.CThostFtdcInputExecOrderActionField;
import com.huatai.common.ctp.CThostFtdcInputExecOrderField;
import com.huatai.common.ctp.CThostFtdcInputForQuoteField;
import com.huatai.common.ctp.CThostFtdcInputOrderActionField;
import com.huatai.common.ctp.CThostFtdcInputOrderField;
import com.huatai.common.ctp.CThostFtdcInputQuoteActionField;
import com.huatai.common.ctp.CThostFtdcInputQuoteField;
import com.huatai.common.ctp.CThostFtdcParkedOrderActionField;
import com.huatai.common.ctp.CThostFtdcParkedOrderField;
import com.huatai.common.ctp.CThostFtdcQryAccountregisterField;
import com.huatai.common.ctp.CThostFtdcQryBrokerTradingAlgosField;
import com.huatai.common.ctp.CThostFtdcQryBrokerTradingParamsField;
import com.huatai.common.ctp.CThostFtdcQryCFMMCTradingAccountKeyField;
import com.huatai.common.ctp.CThostFtdcQryCombActionField;
import com.huatai.common.ctp.CThostFtdcQryCombInstrumentGuardField;
import com.huatai.common.ctp.CThostFtdcQryContractBankField;
import com.huatai.common.ctp.CThostFtdcQryDepthMarketDataField;
import com.huatai.common.ctp.CThostFtdcQryEWarrantOffsetField;
import com.huatai.common.ctp.CThostFtdcQryExchangeField;
import com.huatai.common.ctp.CThostFtdcQryExchangeMarginRateAdjustField;
import com.huatai.common.ctp.CThostFtdcQryExchangeMarginRateField;
import com.huatai.common.ctp.CThostFtdcQryExchangeRateField;
import com.huatai.common.ctp.CThostFtdcQryExecOrderField;
import com.huatai.common.ctp.CThostFtdcQryForQuoteField;
import com.huatai.common.ctp.CThostFtdcQryInstrumentCommissionRateField;
import com.huatai.common.ctp.CThostFtdcQryInstrumentField;
import com.huatai.common.ctp.CThostFtdcQryInstrumentMarginRateField;
import com.huatai.common.ctp.CThostFtdcQryInstrumentOrderCommRateField;
import com.huatai.common.ctp.CThostFtdcQryInvestorField;
import com.huatai.common.ctp.CThostFtdcQryInvestorPositionCombineDetailField;
import com.huatai.common.ctp.CThostFtdcQryInvestorPositionDetailField;
import com.huatai.common.ctp.CThostFtdcQryInvestorPositionField;
import com.huatai.common.ctp.CThostFtdcQryInvestorProductGroupMarginField;
import com.huatai.common.ctp.CThostFtdcQryNoticeField;
import com.huatai.common.ctp.CThostFtdcQryOptionInstrCommRateField;
import com.huatai.common.ctp.CThostFtdcQryOptionInstrTradeCostField;
import com.huatai.common.ctp.CThostFtdcQryOrderField;
import com.huatai.common.ctp.CThostFtdcQryParkedOrderActionField;
import com.huatai.common.ctp.CThostFtdcQryParkedOrderField;
import com.huatai.common.ctp.CThostFtdcQryProductField;
import com.huatai.common.ctp.CThostFtdcQryProductGroupField;
import com.huatai.common.ctp.CThostFtdcQryQuoteField;
import com.huatai.common.ctp.CThostFtdcQrySecAgentACIDMapField;
import com.huatai.common.ctp.CThostFtdcQrySettlementInfoConfirmField;
import com.huatai.common.ctp.CThostFtdcQrySettlementInfoField;
import com.huatai.common.ctp.CThostFtdcQryTradeField;
import com.huatai.common.ctp.CThostFtdcQryTradingAccountField;
import com.huatai.common.ctp.CThostFtdcQryTradingCodeField;
import com.huatai.common.ctp.CThostFtdcQryTradingNoticeField;
import com.huatai.common.ctp.CThostFtdcQryTransferBankField;
import com.huatai.common.ctp.CThostFtdcQryTransferSerialField;
import com.huatai.common.ctp.CThostFtdcQueryCFMMCTradingAccountTokenField;
import com.huatai.common.ctp.CThostFtdcQueryMaxOrderVolumeField;
import com.huatai.common.ctp.CThostFtdcRemoveParkedOrderActionField;
import com.huatai.common.ctp.CThostFtdcRemoveParkedOrderField;
import com.huatai.common.ctp.CThostFtdcReqAuthenticateField;
import com.huatai.common.ctp.CThostFtdcReqQueryAccountField;
import com.huatai.common.ctp.CThostFtdcReqTransferField;
import com.huatai.common.ctp.CThostFtdcReqUserLoginField;
import com.huatai.common.ctp.CThostFtdcSettlementInfoConfirmField;
import com.huatai.common.ctp.CThostFtdcTradingAccountPasswordUpdateField;
import com.huatai.common.ctp.CThostFtdcUserLogoutField;
import com.huatai.common.ctp.CThostFtdcUserPasswordUpdateField;
import com.huatai.common.ctp.ThosttraderapiLibrary.THOST_TE_RESUME_TYPE;

/**
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcTraderApi")
@Library("thosttraderapi")
public class CThostFtdcTraderApi extends CPPObject {
	static {
		BridJ.register();
	}
	
	@Name("CreateFtdcTraderApi")
	public static native Pointer<CThostFtdcTraderApi> CreateFtdcTraderApi(Pointer<Byte> pszFlowPath);
	
	@Name("Release")
	@Virtual(0)
	public native void Release();
	
	@Name("Init")
	@Virtual(1)
	public native void Init();
	
	@Name("Join")
	@Virtual(2)
	public native int Join();
	
	@Name("GetTradingDay")
	@Virtual(3)
	public native Pointer<Byte> GetTradingDay();
	
	@Name("RegisterFront")
	@Virtual(4)
	public native void RegisterFront(Pointer<Byte> pszFrontAddress);
	
	@Name("RegisterNameServer")
	@Virtual(5)
	public native void RegisterNameServer(Pointer<Byte> pszNsAddress);
	
	@Name("RegisterFensUserInfo")
	@Virtual(6)
	public native void RegisterFensUserInfo(Pointer<CThostFtdcFensUserInfoField> pFensUserInfo);
	
	@Name("RegisterSpi")
	@Virtual(7)
	public native void RegisterSpi(Pointer<CThostFtdcTraderSpi> pSpi);
	
	@Name("SubscribePrivateTopic")
	@Virtual(8)
	public native void SubscribePrivateTopic(THOST_TE_RESUME_TYPE nResumeType);
	
	@Name("SubscribePublicTopic")
	@Virtual(9)
	public native void SubscribePublicTopic(THOST_TE_RESUME_TYPE nResumeType);
	
	@Name("ReqAuthenticate")
	@Virtual(10)
	public native int ReqAuthenticate(Pointer<CThostFtdcReqAuthenticateField> pReqAuthenticateField, int nRequestID);
	
	@Name("ReqUserLogin")
	@Virtual(11)
	public native int ReqUserLogin(Pointer<CThostFtdcReqUserLoginField> pReqUserLoginField, int nRequestID);
	
	@Name("ReqUserLogout")
	@Virtual(12)
	public native int ReqUserLogout(Pointer<CThostFtdcUserLogoutField> pUserLogout, int nRequestID);
	
	@Name("ReqUserPasswordUpdate")
	@Virtual(13)
	public native int ReqUserPasswordUpdate(Pointer<CThostFtdcUserPasswordUpdateField> pUserPasswordUpdate, int nRequestID);
	
	@Name("ReqTradingAccountPasswordUpdate")
	@Virtual(14)
	public native int ReqTradingAccountPasswordUpdate(Pointer<CThostFtdcTradingAccountPasswordUpdateField> pTradingAccountPasswordUpdate,
			int nRequestID);
	
	@Name("ReqOrderInsert")
	@Virtual(15)
	public native int ReqOrderInsert(Pointer<CThostFtdcInputOrderField> pInputOrder, int nRequestID);
	
	@Name("ReqParkedOrderInsert")
	@Virtual(16)
	public native int ReqParkedOrderInsert(Pointer<CThostFtdcParkedOrderField> pParkedOrder, int nRequestID);
	
	@Name("ReqParkedOrderAction")
	@Virtual(17)
	public native int ReqParkedOrderAction(Pointer<CThostFtdcParkedOrderActionField> pParkedOrderAction, int nRequestID);
	
	@Name("ReqOrderAction")
	@Virtual(18)
	public native int ReqOrderAction(Pointer<CThostFtdcInputOrderActionField> pInputOrderAction, int nRequestID);
	
	@Name("ReqQueryMaxOrderVolume")
	@Virtual(19)
	public native int ReqQueryMaxOrderVolume(Pointer<CThostFtdcQueryMaxOrderVolumeField> pQueryMaxOrderVolume, int nRequestID);
	
	@Name("ReqSettlementInfoConfirm")
	@Virtual(20)
	public native int ReqSettlementInfoConfirm(Pointer<CThostFtdcSettlementInfoConfirmField> pSettlementInfoConfirm, int nRequestID);
	
	@Name("ReqRemoveParkedOrder")
	@Virtual(21)
	public native int ReqRemoveParkedOrder(Pointer<CThostFtdcRemoveParkedOrderField> pRemoveParkedOrder, int nRequestID);
	
	@Name("ReqRemoveParkedOrderAction")
	@Virtual(22)
	public native int ReqRemoveParkedOrderAction(Pointer<CThostFtdcRemoveParkedOrderActionField> pRemoveParkedOrderAction, int nRequestID);
	
	@Name("ReqExecOrderInsert")
	@Virtual(23)
	public native int ReqExecOrderInsert(Pointer<CThostFtdcInputExecOrderField> pInputExecOrder, int nRequestID);
	
	@Name("ReqExecOrderAction")
	@Virtual(24)
	public native int ReqExecOrderAction(Pointer<CThostFtdcInputExecOrderActionField> pInputExecOrderAction, int nRequestID);
	
	@Name("ReqForQuoteInsert")
	@Virtual(25)
	public native int ReqForQuoteInsert(Pointer<CThostFtdcInputForQuoteField> pInputForQuote, int nRequestID);
	
	@Name("ReqQuoteInsert")
	@Virtual(26)
	public native int ReqQuoteInsert(Pointer<CThostFtdcInputQuoteField> pInputQuote, int nRequestID);
	
	@Name("ReqQuoteAction")
	@Virtual(27)
	public native int ReqQuoteAction(Pointer<CThostFtdcInputQuoteActionField> pInputQuoteAction, int nRequestID);
	
	@Name("ReqCombActionInsert")
	@Virtual(28)
	public native int ReqCombActionInsert(Pointer<CThostFtdcInputCombActionField> pInputCombAction, int nRequestID);
	
	@Name("ReqQryOrder")
	@Virtual(29)
	public native int ReqQryOrder(Pointer<CThostFtdcQryOrderField> pQryOrder, int nRequestID);
	
	@Name("ReqQryTrade")
	@Virtual(30)
	public native int ReqQryTrade(Pointer<CThostFtdcQryTradeField> pQryTrade, int nRequestID);
	
	@Name("ReqQryInvestorPosition")
	@Virtual(31)
	public native int ReqQryInvestorPosition(Pointer<CThostFtdcQryInvestorPositionField> pQryInvestorPosition, int nRequestID);
	
	@Name("ReqQryTradingAccount")
	@Virtual(32)
	public native int ReqQryTradingAccount(Pointer<CThostFtdcQryTradingAccountField> pQryTradingAccount, int nRequestID);
	
	@Name("ReqQryInvestor")
	@Virtual(33)
	public native int ReqQryInvestor(Pointer<CThostFtdcQryInvestorField> pQryInvestor, int nRequestID);
	
	@Name("ReqQryTradingCode")
	@Virtual(34)
	public native int ReqQryTradingCode(Pointer<CThostFtdcQryTradingCodeField> pQryTradingCode, int nRequestID);
	
	@Name("ReqQryInstrumentMarginRate")
	@Virtual(35)
	public native int ReqQryInstrumentMarginRate(Pointer<CThostFtdcQryInstrumentMarginRateField> pQryInstrumentMarginRate, int nRequestID);
	
	@Name("ReqQryInstrumentCommissionRate")
	@Virtual(36)
	public native int ReqQryInstrumentCommissionRate(Pointer<CThostFtdcQryInstrumentCommissionRateField> pQryInstrumentCommissionRate,
			int nRequestID);
	
	@Name("ReqQryExchange")
	@Virtual(37)
	public native int ReqQryExchange(Pointer<CThostFtdcQryExchangeField> pQryExchange, int nRequestID);
	
	@Name("ReqQryProduct")
	@Virtual(38)
	public native int ReqQryProduct(Pointer<CThostFtdcQryProductField> pQryProduct, int nRequestID);
	
	@Name("ReqQryInstrument")
	@Virtual(39)
	public native int ReqQryInstrument(Pointer<CThostFtdcQryInstrumentField> pQryInstrument, int nRequestID);
	
	@Name("ReqQryDepthMarketData")
	@Virtual(40)
	public native int ReqQryDepthMarketData(Pointer<CThostFtdcQryDepthMarketDataField> pQryDepthMarketData, int nRequestID);
	
	@Name("ReqQrySettlementInfo")
	@Virtual(41)
	public native int ReqQrySettlementInfo(Pointer<CThostFtdcQrySettlementInfoField> pQrySettlementInfo, int nRequestID);
	
	@Name("ReqQryTransferBank")
	@Virtual(42)
	public native int ReqQryTransferBank(Pointer<CThostFtdcQryTransferBankField> pQryTransferBank, int nRequestID);
	
	@Name("ReqQryInvestorPositionDetail")
	@Virtual(43)
	public native int ReqQryInvestorPositionDetail(Pointer<CThostFtdcQryInvestorPositionDetailField> pQryInvestorPositionDetail, int nRequestID);
	
	@Name("ReqQryNotice")
	@Virtual(44)
	public native int ReqQryNotice(Pointer<CThostFtdcQryNoticeField> pQryNotice, int nRequestID);
	
	@Name("ReqQrySettlementInfoConfirm")
	@Virtual(45)
	public native int ReqQrySettlementInfoConfirm(Pointer<CThostFtdcQrySettlementInfoConfirmField> pQrySettlementInfoConfirm, int nRequestID);
	
	@Name("ReqQryInvestorPositionCombineDetail")
	@Virtual(46)
	public native int ReqQryInvestorPositionCombineDetail(Pointer<CThostFtdcQryInvestorPositionCombineDetailField> pQryInvestorPositionCombineDetail,
			int nRequestID);
	
	@Name("ReqQryCFMMCTradingAccountKey")
	@Virtual(47)
	public native int ReqQryCFMMCTradingAccountKey(Pointer<CThostFtdcQryCFMMCTradingAccountKeyField> pQryCFMMCTradingAccountKey, int nRequestID);
	
	@Name("ReqQryEWarrantOffset")
	@Virtual(48)
	public native int ReqQryEWarrantOffset(Pointer<CThostFtdcQryEWarrantOffsetField> pQryEWarrantOffset, int nRequestID);
	
	@Name("ReqQryInvestorProductGroupMargin")
	@Virtual(49)
	public native int ReqQryInvestorProductGroupMargin(Pointer<CThostFtdcQryInvestorProductGroupMarginField> pQryInvestorProductGroupMargin,
			int nRequestID);
	
	@Name("ReqQryExchangeMarginRate")
	@Virtual(50)
	public native int ReqQryExchangeMarginRate(Pointer<CThostFtdcQryExchangeMarginRateField> pQryExchangeMarginRate, int nRequestID);
	
	@Name("ReqQryExchangeMarginRateAdjust")
	@Virtual(51)
	public native int ReqQryExchangeMarginRateAdjust(Pointer<CThostFtdcQryExchangeMarginRateAdjustField> pQryExchangeMarginRateAdjust,
			int nRequestID);
	
	@Name("ReqQryExchangeRate")
	@Virtual(52)
	public native int ReqQryExchangeRate(Pointer<CThostFtdcQryExchangeRateField> pQryExchangeRate, int nRequestID);
	
	@Name("ReqQrySecAgentACIDMap")
	@Virtual(53)
	public native int ReqQrySecAgentACIDMap(Pointer<CThostFtdcQrySecAgentACIDMapField> pQrySecAgentACIDMap, int nRequestID);
	
	@Name("ReqQryProductGroup")
	@Virtual(54)
	public native int ReqQryProductGroup(Pointer<CThostFtdcQryProductGroupField> pQryProductGroup, int nRequestID);
	
	@Name("ReqQryInstrumentOrderCommRate")
	@Virtual(55)
	public native int ReqQryInstrumentOrderCommRate(Pointer<CThostFtdcQryInstrumentOrderCommRateField> pQryInstrumentOrderCommRate, int nRequestID);
	
	@Name("ReqQryOptionInstrTradeCost")
	@Virtual(56)
	public native int ReqQryOptionInstrTradeCost(Pointer<CThostFtdcQryOptionInstrTradeCostField> pQryOptionInstrTradeCost, int nRequestID);
	
	@Name("ReqQryOptionInstrCommRate")
	@Virtual(57)
	public native int ReqQryOptionInstrCommRate(Pointer<CThostFtdcQryOptionInstrCommRateField> pQryOptionInstrCommRate, int nRequestID);
	
	@Name("ReqQryExecOrder")
	@Virtual(58)
	public native int ReqQryExecOrder(Pointer<CThostFtdcQryExecOrderField> pQryExecOrder, int nRequestID);
	
	@Name("ReqQryForQuote")
	@Virtual(59)
	public native int ReqQryForQuote(Pointer<CThostFtdcQryForQuoteField> pQryForQuote, int nRequestID);
	
	@Name("ReqQryQuote")
	@Virtual(60)
	public native int ReqQryQuote(Pointer<CThostFtdcQryQuoteField> pQryQuote, int nRequestID);
	
	@Name("ReqQryCombInstrumentGuard")
	@Virtual(61)
	public native int ReqQryCombInstrumentGuard(Pointer<CThostFtdcQryCombInstrumentGuardField> pQryCombInstrumentGuard, int nRequestID);
	
	@Name("ReqQryCombAction")
	@Virtual(62)
	public native int ReqQryCombAction(Pointer<CThostFtdcQryCombActionField> pQryCombAction, int nRequestID);
	
	@Name("ReqQryTransferSerial")
	@Virtual(63)
	public native int ReqQryTransferSerial(Pointer<CThostFtdcQryTransferSerialField> pQryTransferSerial, int nRequestID);
	
	@Name("ReqQryAccountregister")
	@Virtual(64)
	public native int ReqQryAccountregister(Pointer<CThostFtdcQryAccountregisterField> pQryAccountregister, int nRequestID);
	
	@Name("ReqQryContractBank")
	@Virtual(65)
	public native int ReqQryContractBank(Pointer<CThostFtdcQryContractBankField> pQryContractBank, int nRequestID);
	
	@Name("ReqQryParkedOrder")
	@Virtual(66)
	public native int ReqQryParkedOrder(Pointer<CThostFtdcQryParkedOrderField> pQryParkedOrder, int nRequestID);
	
	@Name("ReqQryParkedOrderAction")
	@Virtual(67)
	public native int ReqQryParkedOrderAction(Pointer<CThostFtdcQryParkedOrderActionField> pQryParkedOrderAction, int nRequestID);
	
	@Name("ReqQryTradingNotice")
	@Virtual(68)
	public native int ReqQryTradingNotice(Pointer<CThostFtdcQryTradingNoticeField> pQryTradingNotice, int nRequestID);
	
	@Name("ReqQryBrokerTradingParams")
	@Virtual(69)
	public native int ReqQryBrokerTradingParams(Pointer<CThostFtdcQryBrokerTradingParamsField> pQryBrokerTradingParams, int nRequestID);
	
	@Name("ReqQryBrokerTradingAlgos")
	@Virtual(70)
	public native int ReqQryBrokerTradingAlgos(Pointer<CThostFtdcQryBrokerTradingAlgosField> pQryBrokerTradingAlgos, int nRequestID);
	
	@Name("ReqQueryCFMMCTradingAccountToken")
	@Virtual(71)
	public native int ReqQueryCFMMCTradingAccountToken(Pointer<CThostFtdcQueryCFMMCTradingAccountTokenField> pQueryCFMMCTradingAccountToken,
			int nRequestID);
	
	@Name("ReqFromBankToFutureByFuture")
	@Virtual(72)
	public native int ReqFromBankToFutureByFuture(Pointer<CThostFtdcReqTransferField> pReqTransfer, int nRequestID);
	
	@Name("ReqFromFutureToBankByFuture")
	@Virtual(73)
	public native int ReqFromFutureToBankByFuture(Pointer<CThostFtdcReqTransferField> pReqTransfer, int nRequestID);
	
	@Name("ReqQueryBankAccountMoneyByFuture")
	@Virtual(74)
	public native int ReqQueryBankAccountMoneyByFuture(Pointer<CThostFtdcReqQueryAccountField> pReqQueryAccount, int nRequestID);
	
	public CThostFtdcTraderApi() {
		super();
	}
	
	public CThostFtdcTraderApi(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
