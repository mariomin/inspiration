package com.huatai.platform.downstream.adaptor.ctp.common;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Library;
import org.bridj.ann.Name;
import org.bridj.ann.Virtual;
import org.bridj.cpp.CPPObject;

import com.huatai.common.ctp.CThostFtdcAccountregisterField;
import com.huatai.common.ctp.CThostFtdcBrokerTradingAlgosField;
import com.huatai.common.ctp.CThostFtdcBrokerTradingParamsField;
import com.huatai.common.ctp.CThostFtdcCFMMCTradingAccountKeyField;
import com.huatai.common.ctp.CThostFtdcCFMMCTradingAccountTokenField;
import com.huatai.common.ctp.CThostFtdcCancelAccountField;
import com.huatai.common.ctp.CThostFtdcChangeAccountField;
import com.huatai.common.ctp.CThostFtdcCombActionField;
import com.huatai.common.ctp.CThostFtdcCombInstrumentGuardField;
import com.huatai.common.ctp.CThostFtdcContractBankField;
import com.huatai.common.ctp.CThostFtdcDepthMarketDataField;
import com.huatai.common.ctp.CThostFtdcEWarrantOffsetField;
import com.huatai.common.ctp.CThostFtdcErrorConditionalOrderField;
import com.huatai.common.ctp.CThostFtdcExchangeField;
import com.huatai.common.ctp.CThostFtdcExchangeMarginRateAdjustField;
import com.huatai.common.ctp.CThostFtdcExchangeMarginRateField;
import com.huatai.common.ctp.CThostFtdcExchangeRateField;
import com.huatai.common.ctp.CThostFtdcExecOrderActionField;
import com.huatai.common.ctp.CThostFtdcExecOrderField;
import com.huatai.common.ctp.CThostFtdcForQuoteField;
import com.huatai.common.ctp.CThostFtdcForQuoteRspField;
import com.huatai.common.ctp.CThostFtdcInputCombActionField;
import com.huatai.common.ctp.CThostFtdcInputExecOrderActionField;
import com.huatai.common.ctp.CThostFtdcInputExecOrderField;
import com.huatai.common.ctp.CThostFtdcInputForQuoteField;
import com.huatai.common.ctp.CThostFtdcInputOrderActionField;
import com.huatai.common.ctp.CThostFtdcInputOrderField;
import com.huatai.common.ctp.CThostFtdcInputQuoteActionField;
import com.huatai.common.ctp.CThostFtdcInputQuoteField;
import com.huatai.common.ctp.CThostFtdcInstrumentCommissionRateField;
import com.huatai.common.ctp.CThostFtdcInstrumentField;
import com.huatai.common.ctp.CThostFtdcInstrumentMarginRateField;
import com.huatai.common.ctp.CThostFtdcInstrumentOrderCommRateField;
import com.huatai.common.ctp.CThostFtdcInstrumentStatusField;
import com.huatai.common.ctp.CThostFtdcInvestorField;
import com.huatai.common.ctp.CThostFtdcInvestorPositionCombineDetailField;
import com.huatai.common.ctp.CThostFtdcInvestorPositionDetailField;
import com.huatai.common.ctp.CThostFtdcInvestorPositionField;
import com.huatai.common.ctp.CThostFtdcInvestorProductGroupMarginField;
import com.huatai.common.ctp.CThostFtdcNoticeField;
import com.huatai.common.ctp.CThostFtdcNotifyQueryAccountField;
import com.huatai.common.ctp.CThostFtdcOpenAccountField;
import com.huatai.common.ctp.CThostFtdcOptionInstrCommRateField;
import com.huatai.common.ctp.CThostFtdcOptionInstrTradeCostField;
import com.huatai.common.ctp.CThostFtdcOrderActionField;
import com.huatai.common.ctp.CThostFtdcOrderField;
import com.huatai.common.ctp.CThostFtdcParkedOrderActionField;
import com.huatai.common.ctp.CThostFtdcParkedOrderField;
import com.huatai.common.ctp.CThostFtdcProductField;
import com.huatai.common.ctp.CThostFtdcProductGroupField;
import com.huatai.common.ctp.CThostFtdcQueryCFMMCTradingAccountTokenField;
import com.huatai.common.ctp.CThostFtdcQueryMaxOrderVolumeField;
import com.huatai.common.ctp.CThostFtdcQuoteActionField;
import com.huatai.common.ctp.CThostFtdcQuoteField;
import com.huatai.common.ctp.CThostFtdcRemoveParkedOrderActionField;
import com.huatai.common.ctp.CThostFtdcRemoveParkedOrderField;
import com.huatai.common.ctp.CThostFtdcReqQueryAccountField;
import com.huatai.common.ctp.CThostFtdcReqRepealField;
import com.huatai.common.ctp.CThostFtdcReqTransferField;
import com.huatai.common.ctp.CThostFtdcRspAuthenticateField;
import com.huatai.common.ctp.CThostFtdcRspInfoField;
import com.huatai.common.ctp.CThostFtdcRspRepealField;
import com.huatai.common.ctp.CThostFtdcRspTransferField;
import com.huatai.common.ctp.CThostFtdcRspUserLoginField;
import com.huatai.common.ctp.CThostFtdcSecAgentACIDMapField;
import com.huatai.common.ctp.CThostFtdcSettlementInfoConfirmField;
import com.huatai.common.ctp.CThostFtdcSettlementInfoField;
import com.huatai.common.ctp.CThostFtdcTradeField;
import com.huatai.common.ctp.CThostFtdcTradingAccountField;
import com.huatai.common.ctp.CThostFtdcTradingAccountPasswordUpdateField;
import com.huatai.common.ctp.CThostFtdcTradingCodeField;
import com.huatai.common.ctp.CThostFtdcTradingNoticeField;
import com.huatai.common.ctp.CThostFtdcTradingNoticeInfoField;
import com.huatai.common.ctp.CThostFtdcTransferBankField;
import com.huatai.common.ctp.CThostFtdcTransferSerialField;
import com.huatai.common.ctp.CThostFtdcUserLogoutField;
import com.huatai.common.ctp.CThostFtdcUserPasswordUpdateField;

/**
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcTraderSpi")
@Library("thosttraderapi")
public class CThostFtdcTraderSpi extends CPPObject {
	static {
		BridJ.register();
	}
	
	@Name("OnFrontConnected")
	@Virtual(0)
	public native void OnFrontConnected();
	
	@Name("OnFrontDisconnected")
	@Virtual(1)
	public native void OnFrontDisconnected(int nReason);
	
	@Name("OnHeartBeatWarning")
	@Virtual(2)
	public native void OnHeartBeatWarning(int nTimeLapse);
	
	@Name("OnRspAuthenticate")
	@Virtual(3)
	public native void OnRspAuthenticate(Pointer<CThostFtdcRspAuthenticateField> pRspAuthenticateField, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspUserLogin")
	@Virtual(4)
	public native void OnRspUserLogin(Pointer<CThostFtdcRspUserLoginField> pRspUserLogin, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast);
	
	@Name("OnRspUserLogout")
	@Virtual(5)
	public native void OnRspUserLogout(Pointer<CThostFtdcUserLogoutField> pUserLogout, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast);
	
	@Name("OnRspUserPasswordUpdate")
	@Virtual(6)
	public native void OnRspUserPasswordUpdate(Pointer<CThostFtdcUserPasswordUpdateField> pUserPasswordUpdate,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspTradingAccountPasswordUpdate")
	@Virtual(7)
	public native void OnRspTradingAccountPasswordUpdate(Pointer<CThostFtdcTradingAccountPasswordUpdateField> pTradingAccountPasswordUpdate,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspOrderInsert")
	@Virtual(8)
	public native void OnRspOrderInsert(Pointer<CThostFtdcInputOrderField> pInputOrder, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast);
	
	@Name("OnRspParkedOrderInsert")
	@Virtual(9)
	public native void OnRspParkedOrderInsert(Pointer<CThostFtdcParkedOrderField> pParkedOrder, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspParkedOrderAction")
	@Virtual(10)
	public native void OnRspParkedOrderAction(Pointer<CThostFtdcParkedOrderActionField> pParkedOrderAction, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspOrderAction")
	@Virtual(11)
	public native void OnRspOrderAction(Pointer<CThostFtdcInputOrderActionField> pInputOrderAction, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQueryMaxOrderVolume")
	@Virtual(12)
	public native void OnRspQueryMaxOrderVolume(Pointer<CThostFtdcQueryMaxOrderVolumeField> pQueryMaxOrderVolume,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspSettlementInfoConfirm")
	@Virtual(13)
	public native void OnRspSettlementInfoConfirm(Pointer<CThostFtdcSettlementInfoConfirmField> pSettlementInfoConfirm,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspRemoveParkedOrder")
	@Virtual(14)
	public native void OnRspRemoveParkedOrder(Pointer<CThostFtdcRemoveParkedOrderField> pRemoveParkedOrder, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspRemoveParkedOrderAction")
	@Virtual(15)
	public native void OnRspRemoveParkedOrderAction(Pointer<CThostFtdcRemoveParkedOrderActionField> pRemoveParkedOrderAction,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspExecOrderInsert")
	@Virtual(16)
	public native void OnRspExecOrderInsert(Pointer<CThostFtdcInputExecOrderField> pInputExecOrder, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspExecOrderAction")
	@Virtual(17)
	public native void OnRspExecOrderAction(Pointer<CThostFtdcInputExecOrderActionField> pInputExecOrderAction,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspForQuoteInsert")
	@Virtual(18)
	public native void OnRspForQuoteInsert(Pointer<CThostFtdcInputForQuoteField> pInputForQuote, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQuoteInsert")
	@Virtual(19)
	public native void OnRspQuoteInsert(Pointer<CThostFtdcInputQuoteField> pInputQuote, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast);
	
	@Name("OnRspQuoteAction")
	@Virtual(20)
	public native void OnRspQuoteAction(Pointer<CThostFtdcInputQuoteActionField> pInputQuoteAction, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspCombActionInsert")
	@Virtual(21)
	public native void OnRspCombActionInsert(Pointer<CThostFtdcInputCombActionField> pInputCombAction, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryOrder")
	@Virtual(22)
	public native void OnRspQryOrder(Pointer<CThostFtdcOrderField> pOrder, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryTrade")
	@Virtual(23)
	public native void OnRspQryTrade(Pointer<CThostFtdcTradeField> pTrade, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryInvestorPosition")
	@Virtual(24)
	public native void OnRspQryInvestorPosition(Pointer<CThostFtdcInvestorPositionField> pInvestorPosition, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryTradingAccount")
	@Virtual(25)
	public native void OnRspQryTradingAccount(Pointer<CThostFtdcTradingAccountField> pTradingAccount, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryInvestor")
	@Virtual(26)
	public native void OnRspQryInvestor(Pointer<CThostFtdcInvestorField> pInvestor, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast);
	
	@Name("OnRspQryTradingCode")
	@Virtual(27)
	public native void OnRspQryTradingCode(Pointer<CThostFtdcTradingCodeField> pTradingCode, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast);
	
	@Name("OnRspQryInstrumentMarginRate")
	@Virtual(28)
	public native void OnRspQryInstrumentMarginRate(Pointer<CThostFtdcInstrumentMarginRateField> pInstrumentMarginRate,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryInstrumentCommissionRate")
	@Virtual(29)
	public native void OnRspQryInstrumentCommissionRate(Pointer<CThostFtdcInstrumentCommissionRateField> pInstrumentCommissionRate,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryExchange")
	@Virtual(30)
	public native void OnRspQryExchange(Pointer<CThostFtdcExchangeField> pExchange, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast);
	
	@Name("OnRspQryProduct")
	@Virtual(31)
	public native void OnRspQryProduct(Pointer<CThostFtdcProductField> pProduct, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast);
	
	@Name("OnRspQryInstrument")
	@Virtual(32)
	public native void OnRspQryInstrument(Pointer<CThostFtdcInstrumentField> pInstrument, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast);
	
	@Name("OnRspQryDepthMarketData")
	@Virtual(33)
	public native void OnRspQryDepthMarketData(Pointer<CThostFtdcDepthMarketDataField> pDepthMarketData, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQrySettlementInfo")
	@Virtual(34)
	public native void OnRspQrySettlementInfo(Pointer<CThostFtdcSettlementInfoField> pSettlementInfo, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryTransferBank")
	@Virtual(35)
	public native void OnRspQryTransferBank(Pointer<CThostFtdcTransferBankField> pTransferBank, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryInvestorPositionDetail")
	@Virtual(36)
	public native void OnRspQryInvestorPositionDetail(Pointer<CThostFtdcInvestorPositionDetailField> pInvestorPositionDetail,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryNotice")
	@Virtual(37)
	public native void OnRspQryNotice(Pointer<CThostFtdcNoticeField> pNotice, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast);
	
	@Name("OnRspQrySettlementInfoConfirm")
	@Virtual(38)
	public native void OnRspQrySettlementInfoConfirm(Pointer<CThostFtdcSettlementInfoConfirmField> pSettlementInfoConfirm,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryInvestorPositionCombineDetail")
	@Virtual(39)
	public native void OnRspQryInvestorPositionCombineDetail(Pointer<CThostFtdcInvestorPositionCombineDetailField> pInvestorPositionCombineDetail,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryCFMMCTradingAccountKey")
	@Virtual(40)
	public native void OnRspQryCFMMCTradingAccountKey(Pointer<CThostFtdcCFMMCTradingAccountKeyField> pCFMMCTradingAccountKey,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryEWarrantOffset")
	@Virtual(41)
	public native void OnRspQryEWarrantOffset(Pointer<CThostFtdcEWarrantOffsetField> pEWarrantOffset, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryInvestorProductGroupMargin")
	@Virtual(42)
	public native void OnRspQryInvestorProductGroupMargin(Pointer<CThostFtdcInvestorProductGroupMarginField> pInvestorProductGroupMargin,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryExchangeMarginRate")
	@Virtual(43)
	public native void OnRspQryExchangeMarginRate(Pointer<CThostFtdcExchangeMarginRateField> pExchangeMarginRate,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryExchangeMarginRateAdjust")
	@Virtual(44)
	public native void OnRspQryExchangeMarginRateAdjust(Pointer<CThostFtdcExchangeMarginRateAdjustField> pExchangeMarginRateAdjust,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryExchangeRate")
	@Virtual(45)
	public native void OnRspQryExchangeRate(Pointer<CThostFtdcExchangeRateField> pExchangeRate, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQrySecAgentACIDMap")
	@Virtual(46)
	public native void OnRspQrySecAgentACIDMap(Pointer<CThostFtdcSecAgentACIDMapField> pSecAgentACIDMap, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryProductGroup")
	@Virtual(47)
	public native void OnRspQryProductGroup(Pointer<CThostFtdcProductGroupField> pProductGroup, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryInstrumentOrderCommRate")
	@Virtual(48)
	public native void OnRspQryInstrumentOrderCommRate(Pointer<CThostFtdcInstrumentOrderCommRateField> pInstrumentOrderCommRate,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryOptionInstrTradeCost")
	@Virtual(49)
	public native void OnRspQryOptionInstrTradeCost(Pointer<CThostFtdcOptionInstrTradeCostField> pOptionInstrTradeCost,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryOptionInstrCommRate")
	@Virtual(50)
	public native void OnRspQryOptionInstrCommRate(Pointer<CThostFtdcOptionInstrCommRateField> pOptionInstrCommRate,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryExecOrder")
	@Virtual(51)
	public native void OnRspQryExecOrder(Pointer<CThostFtdcExecOrderField> pExecOrder, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast);
	
	@Name("OnRspQryForQuote")
	@Virtual(52)
	public native void OnRspQryForQuote(Pointer<CThostFtdcForQuoteField> pForQuote, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast);
	
	@Name("OnRspQryQuote")
	@Virtual(53)
	public native void OnRspQryQuote(Pointer<CThostFtdcQuoteField> pQuote, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryCombInstrumentGuard")
	@Virtual(54)
	public native void OnRspQryCombInstrumentGuard(Pointer<CThostFtdcCombInstrumentGuardField> pCombInstrumentGuard,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryCombAction")
	@Virtual(55)
	public native void OnRspQryCombAction(Pointer<CThostFtdcCombActionField> pCombAction, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast);
	
	@Name("OnRspQryTransferSerial")
	@Virtual(56)
	public native void OnRspQryTransferSerial(Pointer<CThostFtdcTransferSerialField> pTransferSerial, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryAccountregister")
	@Virtual(57)
	public native void OnRspQryAccountregister(Pointer<CThostFtdcAccountregisterField> pAccountregister, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspError")
	@Virtual(58)
	public native void OnRspError(Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRtnOrder")
	@Virtual(59)
	public native void OnRtnOrder(Pointer<CThostFtdcOrderField> pOrder);
	
	@Name("OnRtnTrade")
	@Virtual(60)
	public native void OnRtnTrade(Pointer<CThostFtdcTradeField> pTrade);
	
	@Name("OnErrRtnOrderInsert")
	@Virtual(61)
	public native void OnErrRtnOrderInsert(Pointer<CThostFtdcInputOrderField> pInputOrder, Pointer<CThostFtdcRspInfoField> pRspInfo);
	
	@Name("OnErrRtnOrderAction")
	@Virtual(62)
	public native void OnErrRtnOrderAction(Pointer<CThostFtdcOrderActionField> pOrderAction, Pointer<CThostFtdcRspInfoField> pRspInfo);
	
	@Name("OnRtnInstrumentStatus")
	@Virtual(63)
	public native void OnRtnInstrumentStatus(Pointer<CThostFtdcInstrumentStatusField> pInstrumentStatus);
	
	@Name("OnRtnTradingNotice")
	@Virtual(64)
	public native void OnRtnTradingNotice(Pointer<CThostFtdcTradingNoticeInfoField> pTradingNoticeInfo);
	
	@Name("OnRtnErrorConditionalOrder")
	@Virtual(65)
	public native void OnRtnErrorConditionalOrder(Pointer<CThostFtdcErrorConditionalOrderField> pErrorConditionalOrder);
	
	@Name("OnRtnExecOrder")
	@Virtual(66)
	public native void OnRtnExecOrder(Pointer<CThostFtdcExecOrderField> pExecOrder);
	
	@Name("OnErrRtnExecOrderInsert")
	@Virtual(67)
	public native void OnErrRtnExecOrderInsert(Pointer<CThostFtdcInputExecOrderField> pInputExecOrder, Pointer<CThostFtdcRspInfoField> pRspInfo);
	
	@Name("OnErrRtnExecOrderAction")
	@Virtual(68)
	public native void OnErrRtnExecOrderAction(Pointer<CThostFtdcExecOrderActionField> pExecOrderAction, Pointer<CThostFtdcRspInfoField> pRspInfo);
	
	@Name("OnErrRtnForQuoteInsert")
	@Virtual(69)
	public native void OnErrRtnForQuoteInsert(Pointer<CThostFtdcInputForQuoteField> pInputForQuote, Pointer<CThostFtdcRspInfoField> pRspInfo);
	
	@Name("OnRtnQuote")
	@Virtual(70)
	public native void OnRtnQuote(Pointer<CThostFtdcQuoteField> pQuote);
	
	@Name("OnErrRtnQuoteInsert")
	@Virtual(71)
	public native void OnErrRtnQuoteInsert(Pointer<CThostFtdcInputQuoteField> pInputQuote, Pointer<CThostFtdcRspInfoField> pRspInfo);
	
	@Name("OnErrRtnQuoteAction")
	@Virtual(72)
	public native void OnErrRtnQuoteAction(Pointer<CThostFtdcQuoteActionField> pQuoteAction, Pointer<CThostFtdcRspInfoField> pRspInfo);
	
	@Name("OnRtnForQuoteRsp")
	@Virtual(73)
	public native void OnRtnForQuoteRsp(Pointer<CThostFtdcForQuoteRspField> pForQuoteRsp);
	
	@Name("OnRtnCFMMCTradingAccountToken")
	@Virtual(74)
	public native void OnRtnCFMMCTradingAccountToken(Pointer<CThostFtdcCFMMCTradingAccountTokenField> pCFMMCTradingAccountToken);
	
	@Name("OnRtnCombAction")
	@Virtual(75)
	public native void OnRtnCombAction(Pointer<CThostFtdcCombActionField> pCombAction);
	
	@Name("OnErrRtnCombActionInsert")
	@Virtual(76)
	public native void OnErrRtnCombActionInsert(Pointer<CThostFtdcInputCombActionField> pInputCombAction, Pointer<CThostFtdcRspInfoField> pRspInfo);
	
	@Name("OnRspQryContractBank")
	@Virtual(77)
	public native void OnRspQryContractBank(Pointer<CThostFtdcContractBankField> pContractBank, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryParkedOrder")
	@Virtual(78)
	public native void OnRspQryParkedOrder(Pointer<CThostFtdcParkedOrderField> pParkedOrder, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast);
	
	@Name("OnRspQryParkedOrderAction")
	@Virtual(79)
	public native void OnRspQryParkedOrderAction(Pointer<CThostFtdcParkedOrderActionField> pParkedOrderAction,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryTradingNotice")
	@Virtual(80)
	public native void OnRspQryTradingNotice(Pointer<CThostFtdcTradingNoticeField> pTradingNotice, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryBrokerTradingParams")
	@Virtual(81)
	public native void OnRspQryBrokerTradingParams(Pointer<CThostFtdcBrokerTradingParamsField> pBrokerTradingParams,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQryBrokerTradingAlgos")
	@Virtual(82)
	public native void OnRspQryBrokerTradingAlgos(Pointer<CThostFtdcBrokerTradingAlgosField> pBrokerTradingAlgos,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRspQueryCFMMCTradingAccountToken")
	@Virtual(83)
	public native void OnRspQueryCFMMCTradingAccountToken(Pointer<CThostFtdcQueryCFMMCTradingAccountTokenField> pQueryCFMMCTradingAccountToken,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRtnFromBankToFutureByBank")
	@Virtual(84)
	public native void OnRtnFromBankToFutureByBank(Pointer<CThostFtdcRspTransferField> pRspTransfer);
	
	@Name("OnRtnFromFutureToBankByBank")
	@Virtual(85)
	public native void OnRtnFromFutureToBankByBank(Pointer<CThostFtdcRspTransferField> pRspTransfer);
	
	@Name("OnRtnRepealFromBankToFutureByBank")
	@Virtual(86)
	public native void OnRtnRepealFromBankToFutureByBank(Pointer<CThostFtdcRspRepealField> pRspRepeal);
	
	@Name("OnRtnRepealFromFutureToBankByBank")
	@Virtual(87)
	public native void OnRtnRepealFromFutureToBankByBank(Pointer<CThostFtdcRspRepealField> pRspRepeal);
	
	@Name("OnRtnFromBankToFutureByFuture")
	@Virtual(88)
	public native void OnRtnFromBankToFutureByFuture(Pointer<CThostFtdcRspTransferField> pRspTransfer);
	
	@Name("OnRtnFromFutureToBankByFuture")
	@Virtual(89)
	public native void OnRtnFromFutureToBankByFuture(Pointer<CThostFtdcRspTransferField> pRspTransfer);
	
	@Name("OnRtnRepealFromBankToFutureByFutureManual")
	@Virtual(90)
	public native void OnRtnRepealFromBankToFutureByFutureManual(Pointer<CThostFtdcRspRepealField> pRspRepeal);
	
	@Name("OnRtnRepealFromFutureToBankByFutureManual")
	@Virtual(91)
	public native void OnRtnRepealFromFutureToBankByFutureManual(Pointer<CThostFtdcRspRepealField> pRspRepeal);
	
	@Name("OnRtnQueryBankBalanceByFuture")
	@Virtual(92)
	public native void OnRtnQueryBankBalanceByFuture(Pointer<CThostFtdcNotifyQueryAccountField> pNotifyQueryAccount);
	
	@Name("OnErrRtnBankToFutureByFuture")
	@Virtual(93)
	public native void OnErrRtnBankToFutureByFuture(Pointer<CThostFtdcReqTransferField> pReqTransfer, Pointer<CThostFtdcRspInfoField> pRspInfo);
	
	@Name("OnErrRtnFutureToBankByFuture")
	@Virtual(94)
	public native void OnErrRtnFutureToBankByFuture(Pointer<CThostFtdcReqTransferField> pReqTransfer, Pointer<CThostFtdcRspInfoField> pRspInfo);
	
	@Name("OnErrRtnRepealBankToFutureByFutureManual")
	@Virtual(95)
	public native void OnErrRtnRepealBankToFutureByFutureManual(Pointer<CThostFtdcReqRepealField> pReqRepeal,
			Pointer<CThostFtdcRspInfoField> pRspInfo);
	
	@Name("OnErrRtnRepealFutureToBankByFutureManual")
	@Virtual(96)
	public native void OnErrRtnRepealFutureToBankByFutureManual(Pointer<CThostFtdcReqRepealField> pReqRepeal,
			Pointer<CThostFtdcRspInfoField> pRspInfo);
	
	@Name("OnErrRtnQueryBankBalanceByFuture")
	@Virtual(97)
	public native void OnErrRtnQueryBankBalanceByFuture(Pointer<CThostFtdcReqQueryAccountField> pReqQueryAccount,
			Pointer<CThostFtdcRspInfoField> pRspInfo);
	
	@Name("OnRtnRepealFromBankToFutureByFuture")
	@Virtual(98)
	public native void OnRtnRepealFromBankToFutureByFuture(Pointer<CThostFtdcRspRepealField> pRspRepeal);
	
	@Name("OnRtnRepealFromFutureToBankByFuture")
	@Virtual(99)
	public native void OnRtnRepealFromFutureToBankByFuture(Pointer<CThostFtdcRspRepealField> pRspRepeal);
	
	@Name("OnRspFromBankToFutureByFuture")
	@Virtual(100)
	public native void OnRspFromBankToFutureByFuture(Pointer<CThostFtdcReqTransferField> pReqTransfer, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspFromFutureToBankByFuture")
	@Virtual(101)
	public native void OnRspFromFutureToBankByFuture(Pointer<CThostFtdcReqTransferField> pReqTransfer, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast);
	
	@Name("OnRspQueryBankAccountMoneyByFuture")
	@Virtual(102)
	public native void OnRspQueryBankAccountMoneyByFuture(Pointer<CThostFtdcReqQueryAccountField> pReqQueryAccount,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast);
	
	@Name("OnRtnOpenAccountByBank")
	@Virtual(103)
	public native void OnRtnOpenAccountByBank(Pointer<CThostFtdcOpenAccountField> pOpenAccount);
	
	@Name("OnRtnCancelAccountByBank")
	@Virtual(104)
	public native void OnRtnCancelAccountByBank(Pointer<CThostFtdcCancelAccountField> pCancelAccount);
	
	@Name("OnRtnChangeAccountByBank")
	@Virtual(105)
	public native void OnRtnChangeAccountByBank(Pointer<CThostFtdcChangeAccountField> pChangeAccount);
	
	public CThostFtdcTraderSpi() {
		super();
	}
	
	public CThostFtdcTraderSpi(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
