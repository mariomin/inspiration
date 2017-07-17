package com.huatai.platform.downstream.adaptor.ctp;

import java.util.concurrent.atomic.AtomicInteger;

import org.bridj.Pointer;
import org.bridj.ann.Virtual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.ctp.CThostFtdcAccountregisterField;
import com.huatai.common.ctp.CThostFtdcBrokerTradingAlgosField;
import com.huatai.common.ctp.CThostFtdcBrokerTradingParamsField;
import com.huatai.common.ctp.CThostFtdcCFMMCTradingAccountKeyField;
import com.huatai.common.ctp.CThostFtdcCancelAccountField;
import com.huatai.common.ctp.CThostFtdcChangeAccountField;
import com.huatai.common.ctp.CThostFtdcContractBankField;
import com.huatai.common.ctp.CThostFtdcDepthMarketDataField;
import com.huatai.common.ctp.CThostFtdcEWarrantOffsetField;
import com.huatai.common.ctp.CThostFtdcErrorConditionalOrderField;
import com.huatai.common.ctp.CThostFtdcExchangeField;
import com.huatai.common.ctp.CThostFtdcInputOrderActionField;
import com.huatai.common.ctp.CThostFtdcInputOrderField;
import com.huatai.common.ctp.CThostFtdcInstrumentCommissionRateField;
import com.huatai.common.ctp.CThostFtdcInstrumentField;
import com.huatai.common.ctp.CThostFtdcInstrumentMarginRateField;
import com.huatai.common.ctp.CThostFtdcInstrumentStatusField;
import com.huatai.common.ctp.CThostFtdcInvestorField;
import com.huatai.common.ctp.CThostFtdcInvestorPositionCombineDetailField;
import com.huatai.common.ctp.CThostFtdcInvestorPositionDetailField;
import com.huatai.common.ctp.CThostFtdcInvestorPositionField;
import com.huatai.common.ctp.CThostFtdcNoticeField;
import com.huatai.common.ctp.CThostFtdcNotifyQueryAccountField;
import com.huatai.common.ctp.CThostFtdcOpenAccountField;
import com.huatai.common.ctp.CThostFtdcOrderActionField;
import com.huatai.common.ctp.CThostFtdcOrderField;
import com.huatai.common.ctp.CThostFtdcParkedOrderActionField;
import com.huatai.common.ctp.CThostFtdcParkedOrderField;
import com.huatai.common.ctp.CThostFtdcQueryMaxOrderVolumeField;
import com.huatai.common.ctp.CThostFtdcRemoveParkedOrderActionField;
import com.huatai.common.ctp.CThostFtdcRemoveParkedOrderField;
import com.huatai.common.ctp.CThostFtdcReqAuthenticateField;
import com.huatai.common.ctp.CThostFtdcReqQueryAccountField;
import com.huatai.common.ctp.CThostFtdcReqRepealField;
import com.huatai.common.ctp.CThostFtdcReqTransferField;
import com.huatai.common.ctp.CThostFtdcReqUserLoginField;
import com.huatai.common.ctp.CThostFtdcRspAuthenticateField;
import com.huatai.common.ctp.CThostFtdcRspInfoField;
import com.huatai.common.ctp.CThostFtdcRspRepealField;
import com.huatai.common.ctp.CThostFtdcRspTransferField;
import com.huatai.common.ctp.CThostFtdcRspUserLoginField;
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
import com.huatai.platform.downstream.adaptor.ctp.common.CThostFtdcTraderApi;
import com.huatai.platform.downstream.adaptor.ctp.common.CThostFtdcTraderSpi;

public class CTPTraderSpi extends CThostFtdcTraderSpi {
	private static final Logger log = LoggerFactory.getLogger(CTPTraderSpi.class);
	private final CThostFtdcTraderApi api;
	private final ICTPTradeListener listener;
	private final CTPSettings settings;
	private final AtomicInteger idGenerator;
	
	public CTPTraderSpi(CThostFtdcTraderApi api, ICTPTradeListener listener, CTPSettings settings, AtomicInteger idGenerator) {
		this.api = api;
		this.listener = listener;
		this.settings = settings;
		this.idGenerator = idGenerator;
	}
	
	private static void checkError(Pointer<CThostFtdcRspInfoField> pRspInfo) {
		CThostFtdcRspInfoField rspInfo = pRspInfo.get();
		int errorId = rspInfo.ErrorID();
		if (errorId != 0) {
			log.error("RspInfo.ErrorID = " + rspInfo.ErrorID() + " ErrorMsg = " + rspInfo.ErrorMsg().getCString());
		}
	}
	
	/**
	 * \ufffd\ufffd\ufffd\u037b\ufffd\ufffd\ufffd\ufffd\ubf7b\ufffd\u05fa\ufffd\u0328\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0368\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02b1\ufffd\ufffd\ufffd\ufffd\u03b4\ufffd\ufffd\u00bc\u01f0\ufffd\ufffd\ufffd\ufffd\ufffd\u00f7\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u00e1\ufffd<br>
	 * Original signature : <code>void OnFrontConnected()</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:22</i>
	 */
	@Override
	@Virtual(0)
	public void OnFrontConnected() {
		log.debug("OnFrontConnected");
		
		CThostFtdcReqAuthenticateField authenticate = new CThostFtdcReqAuthenticateField();
		authenticate.setBrokerID(settings.getBrokerID());
		authenticate.setUserID(settings.getUserID());
		authenticate.setUserProductInfo(settings.getUserProductInfo());
		authenticate.setAuthCode(settings.getAuthCode());
		
		api.ReqAuthenticate(Pointer.getPointer(authenticate), idGenerator.getAndIncrement());
	}
	
	/**
	 * 0x2003 \ufffd\u0575\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd<br>
	 * Original signature : <code>void OnFrontDisconnected(int)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:31</i>
	 */
	@Override
	@Virtual(1)
	public void OnFrontDisconnected(int nReason) {
		log.debug("OnFrontDisconnected");
	}
	
	/**
	 * @param nTimeLapse
	 *            \ufffd\ufffd\ufffd\ufffd\ufffd\u03f4\u03bd\ufffd\ufffd\u0571\ufffd\ufffd\u0135\ufffd\u02b1\ufffd\ufffd<br>
	 *            Original signature :
	 *            <code>void OnHeartBeatWarning(int)</code><br>
	 *            <i>native declaration :
	 *            ctpapi/linux/ThostFtdcTraderApi.h:35</i>
	 */
	@Override
	@Virtual(2)
	public void OnHeartBeatWarning(int nTimeLapse) {
		log.debug("OnHeartBeatWarning");
	}
	
	/**
	 * \ufffd\u037b\ufffd\ufffd\ufffd\ufffd\ufffd\u05a4\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspAuthenticate(CThostFtdcRspAuthenticateField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:38</i>
	 */
	@Override
	@Virtual(3)
	public void OnRspAuthenticate(Pointer<CThostFtdcRspAuthenticateField> pRspAuthenticateField, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {
		log.debug("OnRspAuthenticate");
		
		CThostFtdcReqUserLoginField login = new CThostFtdcReqUserLoginField();
		login.setBrokerID(settings.getBrokerID());
		login.setUserID(settings.getUserID());
		login.setUserProductInfo(settings.getUserProductInfo());
		login.setPassword(settings.getPassword());
		
		api.ReqUserLogin(Pointer.getPointer(login), idGenerator.getAndIncrement());
	}
	
	/**
	 * \ufffd\ufffd\u00bc\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspUserLogin(CThostFtdcRspUserLoginField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:42</i>
	 */
	@Override
	@Virtual(4)
	public void OnRspUserLogin(Pointer<CThostFtdcRspUserLoginField> pRspUserLogin, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast) {
		log.debug("OnRspUserLogin");
		
		CThostFtdcRspUserLoginField rspLogin = pRspUserLogin.get();
		long orderRef = Long.parseLong(rspLogin.MaxOrderRef().getCString()) + 1;
		int frontID = rspLogin.FrontID();
		int sessionID = rspLogin.SessionID();
		listener.onLogin(orderRef, frontID, sessionID);
		
		checkError(pRspInfo);
	}
	
	/**
	 * \ufffd\u01f3\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspUserLogout(CThostFtdcUserLogoutField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:45</i>
	 */
	@Override
	@Virtual(5)
	public void OnRspUserLogout(Pointer<CThostFtdcUserLogoutField> pUserLogout, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast) {
		log.debug("OnRspUserLogout");
	}
	
	/**
	 * \ufffd\u00fb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspUserPasswordUpdate(CThostFtdcUserPasswordUpdateField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:48</i>
	 */
	@Override
	@Virtual(6)
	public void OnRspUserPasswordUpdate(Pointer<CThostFtdcUserPasswordUpdateField> pUserPasswordUpdate, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\u02bd\ufffd\ufffd\u02fb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspTradingAccountPasswordUpdate(CThostFtdcTradingAccountPasswordUpdateField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:51</i>
	 */
	@Override
	@Virtual(7)
	public void OnRspTradingAccountPasswordUpdate(Pointer<CThostFtdcTradingAccountPasswordUpdateField> pTradingAccountPasswordUpdate,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\u00bc\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspOrderInsert(CThostFtdcInputOrderField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:54</i>
	 */
	@Override
	@Virtual(8)
	public void OnRspOrderInsert(Pointer<CThostFtdcInputOrderField> pInputOrder, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast) {
		log.debug("OnRspOrderInsert");
	}
	
	/**
	 * \u0524\ufffd\ufffd\u00bc\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspParkedOrderInsert(CThostFtdcParkedOrderField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:57</i>
	 */
	@Override
	@Virtual(9)
	public void OnRspParkedOrderInsert(Pointer<CThostFtdcParkedOrderField> pParkedOrder, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast) {}
	
	/**
	 * \u0524\ufffd\ud98f\uddf5\ufffd\u00bc\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspParkedOrderAction(CThostFtdcParkedOrderActionField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:60</i>
	 */
	@Override
	@Virtual(10)
	public void OnRspParkedOrderAction(Pointer<CThostFtdcParkedOrderActionField> pParkedOrderAction, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspOrderAction(CThostFtdcInputOrderActionField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:63</i>
	 */
	@Override
	@Virtual(11)
	public void OnRspOrderAction(Pointer<CThostFtdcInputOrderActionField> pInputOrderAction, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\u046f\ufffd\ufffd\udb86\ude35\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQueryMaxOrderVolume(CThostFtdcQueryMaxOrderVolumeField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:66</i>
	 */
	@Override
	@Virtual(12)
	public void OnRspQueryMaxOrderVolume(Pointer<CThostFtdcQueryMaxOrderVolumeField> pQueryMaxOrderVolume, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {}
	
	/**
	 * \u0376\ufffd\ufffd\ufffd\u07fd\ufffd\ufffd\ufffd\ufffd\ufffd\u0237\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspSettlementInfoConfirm(CThostFtdcSettlementInfoConfirmField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:69</i>
	 */
	@Override
	@Virtual(13)
	public void OnRspSettlementInfoConfirm(Pointer<CThostFtdcSettlementInfoConfirmField> pSettlementInfoConfirm,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast) {
		log.debug("OnRspSettlementInfoConfirm");
	}
	
	/**
	 * \u027e\ufffd\ufffd\u0524\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspRemoveParkedOrder(CThostFtdcRemoveParkedOrderField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:72</i>
	 */
	@Override
	@Virtual(14)
	public void OnRspRemoveParkedOrder(Pointer<CThostFtdcRemoveParkedOrderField> pRemoveParkedOrder, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {}
	
	/**
	 * \u027e\ufffd\ufffd\u0524\ufffd\ud98f\uddf5\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspRemoveParkedOrderAction(CThostFtdcRemoveParkedOrderActionField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:75</i>
	 */
	@Override
	@Virtual(15)
	public void OnRspRemoveParkedOrderAction(Pointer<CThostFtdcRemoveParkedOrderActionField> pRemoveParkedOrderAction,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryOrder(CThostFtdcOrderField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:78</i>
	 */
	@Override
	@Virtual(16)
	public void OnRspQryOrder(Pointer<CThostFtdcOrderField> pOrder, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\u027d\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryTrade(CThostFtdcTradeField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:81</i>
	 */
	@Override
	@Virtual(17)
	public void OnRspQryTrade(Pointer<CThostFtdcTradeField> pTrade, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u0376\ufffd\ufffd\ufffd\u07f3\u05b2\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryInvestorPosition(CThostFtdcInvestorPositionField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:84</i>
	 */
	@Override
	@Virtual(18)
	public void OnRspQryInvestorPosition(Pointer<CThostFtdcInvestorPositionField> pInvestorPosition, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\u02bd\ufffd\ufffd\u02fb\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryTradingAccount(CThostFtdcTradingAccountField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:87</i>
	 */
	@Override
	@Virtual(19)
	public void OnRspQryTradingAccount(Pointer<CThostFtdcTradingAccountField> pTradingAccount, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {
		log.debug("OnRspQryTradingAccount");
	}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u0376\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryInvestor(CThostFtdcInvestorField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:90</i>
	 */
	@Override
	@Virtual(20)
	public void OnRspQryInvestor(Pointer<CThostFtdcInvestorField> pInvestor, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\u05f1\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryTradingCode(CThostFtdcTradingCodeField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:93</i>
	 */
	@Override
	@Virtual(21)
	public void OnRspQryTradingCode(Pointer<CThostFtdcTradingCodeField> pTradingCode, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\u053c\ufffd\ufffd\u05a4\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryInstrumentMarginRate(CThostFtdcInstrumentMarginRateField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:96</i>
	 */
	@Override
	@Virtual(22)
	public void OnRspQryInstrumentMarginRate(Pointer<CThostFtdcInstrumentMarginRateField> pInstrumentMarginRate,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\u053c\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryInstrumentCommissionRate(CThostFtdcInstrumentCommissionRateField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:99</i>
	 */
	@Override
	@Virtual(23)
	public void OnRspQryInstrumentCommissionRate(Pointer<CThostFtdcInstrumentCommissionRateField> pInstrumentCommissionRate,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryExchange(CThostFtdcExchangeField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:102</i>
	 */
	@Override
	@Virtual(24)
	public void OnRspQryExchange(Pointer<CThostFtdcExchangeField> pExchange, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\u053c\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryInstrument(CThostFtdcInstrumentField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:105</i>
	 */
	@Override
	@Virtual(25)
	public void OnRspQryInstrument(Pointer<CThostFtdcInstrumentField> pInstrument, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast) {
		log.debug("OnRspQryInstrument");
	}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryDepthMarketData(CThostFtdcDepthMarketDataField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:108</i>
	 */
	@Override
	@Virtual(26)
	public void OnRspQryDepthMarketData(Pointer<CThostFtdcDepthMarketDataField> pDepthMarketData, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u0376\ufffd\ufffd\ufffd\u07fd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQrySettlementInfo(CThostFtdcSettlementInfoField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:111</i>
	 */
	@Override
	@Virtual(27)
	public void OnRspQrySettlementInfo(Pointer<CThostFtdcSettlementInfoField> pSettlementInfo, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {
		log.debug("OnRspQrySettlementInfo");
	}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u05ea\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryTransferBank(CThostFtdcTransferBankField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:114</i>
	 */
	@Override
	@Virtual(28)
	public void OnRspQryTransferBank(Pointer<CThostFtdcTransferBankField> pTransferBank, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u0376\ufffd\ufffd\ufffd\u07f3\u05b2\ufffd\ufffd\ufffd\u03f8\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryInvestorPositionDetail(CThostFtdcInvestorPositionDetailField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:117</i>
	 */
	@Override
	@Virtual(29)
	public void OnRspQryInvestorPositionDetail(Pointer<CThostFtdcInvestorPositionDetailField> pInvestorPositionDetail,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\u037b\ufffd\u0368\u05aa\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryNotice(CThostFtdcNoticeField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:120</i>
	 */
	@Override
	@Virtual(30)
	public void OnRspQryNotice(Pointer<CThostFtdcNoticeField> pNotice, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u03e2\u0237\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQrySettlementInfoConfirm(CThostFtdcSettlementInfoConfirmField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:123</i>
	 */
	@Override
	@Virtual(31)
	public void OnRspQrySettlementInfoConfirm(Pointer<CThostFtdcSettlementInfoConfirmField> pSettlementInfoConfirm,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast) {
		log.debug("OnRspQrySettlementInfoConfirm");
	}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u0376\ufffd\ufffd\ufffd\u07f3\u05b2\ufffd\ufffd\ufffd\u03f8\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryInvestorPositionCombineDetail(CThostFtdcInvestorPositionCombineDetailField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:126</i>
	 */
	@Override
	@Virtual(32)
	public void OnRspQryInvestorPositionCombineDetail(Pointer<CThostFtdcInvestorPositionCombineDetailField> pInvestorPositionCombineDetail,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\u046f\ufffd\ufffd\u05a4\ufffd\ufffd\ufffd\ufffd\u03f5\u0373\ufffd\ufffd\ufffd\u0379\ufffd\u02fe\ufffd\u02bd\ufffd\ufffd\u02fb\ufffd\ufffd\ufffd\u053f\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryCFMMCTradingAccountKey(CThostFtdcCFMMCTradingAccountKeyField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:129</i>
	 */
	@Override
	@Virtual(33)
	public void OnRspQryCFMMCTradingAccountKey(Pointer<CThostFtdcCFMMCTradingAccountKeyField> pCFMMCTradingAccountKey,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\u05b5\ufffd\ufffd\u06f5\ufffd\ufffd\ufffd\u03e2\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryEWarrantOffset(CThostFtdcEWarrantOffsetField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:132</i>
	 */
	@Override
	@Virtual(34)
	public void OnRspQryEWarrantOffset(Pointer<CThostFtdcEWarrantOffsetField> pEWarrantOffset, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u05ea\ufffd\ufffd\ufffd\ufffd\u02ee\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryTransferSerial(CThostFtdcTransferSerialField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:135</i>
	 */
	@Override
	@Virtual(35)
	public void OnRspQryTransferSerial(Pointer<CThostFtdcTransferSerialField> pTransferSerial, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\u01e9\u053c\ufffd\ufffd\u03f5\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryAccountregister(CThostFtdcAccountregisterField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:138</i>
	 */
	@Override
	@Virtual(36)
	public void OnRspQryAccountregister(Pointer<CThostFtdcAccountregisterField> pAccountregister, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\u04e6\ufffd\ufffd<br>
	 * Original signature :
	 * <code>void OnRspError(CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:141</i>
	 */
	@Override
	@Virtual(37)
	public void OnRspError(Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast) {
		log.debug("OnRspError");
		
		checkError(pRspInfo);
	}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnOrder(CThostFtdcOrderField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:144</i>
	 */
	@Override
	@Virtual(38)
	public void OnRtnOrder(Pointer<CThostFtdcOrderField> pOrder) {
		log.debug("OnRtnOrder");
		
		CThostFtdcOrderField order = pOrder.get();
		listener.onOrderConfirmed(order);
	}
	
	/**
	 * \ufffd\u027d\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnTrade(CThostFtdcTradeField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:147</i>
	 */
	@Override
	@Virtual(39)
	public void OnRtnTrade(Pointer<CThostFtdcTradeField> pTrade) {
		log.debug("OnRtnTrade");
		
		CThostFtdcTradeField trade = pTrade.get();
		String msg = String.format("OnRtnTrade : TradeID = %s InstrumentID = %s Price = %f Volume = %d TradeTime = %s",
				trade.TradeID().getCString().trim(), trade.InstrumentID().getCString(), trade.Price(), trade.Volume(),
				trade.TradeTime().getCString());
		log.info(msg);
	}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\u00bc\ufffd\ufffd\ufffd\ufffd\ufffd\u0631\ufffd<br>
	 * Original signature :
	 * <code>void OnErrRtnOrderInsert(CThostFtdcInputOrderField*, CThostFtdcRspInfoField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:150</i>
	 */
	@Override
	@Virtual(40)
	public void OnErrRtnOrderInsert(Pointer<CThostFtdcInputOrderField> pInputOrder, Pointer<CThostFtdcRspInfoField> pRspInfo) {
		log.debug("OnErrRtnOrderInsert");
		
		checkError(pRspInfo);
	}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0631\ufffd<br>
	 * Original signature :
	 * <code>void OnErrRtnOrderAction(CThostFtdcOrderActionField*, CThostFtdcRspInfoField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:153</i>
	 */
	@Override
	@Virtual(41)
	public void OnErrRtnOrderAction(Pointer<CThostFtdcOrderActionField> pOrderAction, Pointer<CThostFtdcRspInfoField> pRspInfo) {
		log.debug("OnErrRtnOrderAction");
		
		checkError(pRspInfo);
	}
	
	/**
	 * \ufffd\ufffd\u053c\ufffd\ufffd\ufffd\ufffd\u05f4\u032c\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnInstrumentStatus(CThostFtdcInstrumentStatusField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:156</i>
	 */
	@Override
	@Virtual(42)
	public void OnRtnInstrumentStatus(Pointer<CThostFtdcInstrumentStatusField> pInstrumentStatus) {
		log.debug("OnRtnInstrumentStatus");
	}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnTradingNotice(CThostFtdcTradingNoticeInfoField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:159</i>
	 */
	@Override
	@Virtual(43)
	public void OnRtnTradingNotice(Pointer<CThostFtdcTradingNoticeInfoField> pTradingNoticeInfo) {
		log.debug("OnRtnTradingNotice");
	}
	
	/**
	 * \ufffd\ufffd\u02be\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0423\ufffd\ufffd\ufffd\ufffd\ufffd<br>
	 * Original signature :
	 * <code>void OnRtnErrorConditionalOrder(CThostFtdcErrorConditionalOrderField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:162</i>
	 */
	@Override
	@Virtual(44)
	public void OnRtnErrorConditionalOrder(Pointer<CThostFtdcErrorConditionalOrderField> pErrorConditionalOrder) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u01e9\u053c\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryContractBank(CThostFtdcContractBankField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:165</i>
	 */
	@Override
	@Virtual(45)
	public void OnRspQryContractBank(Pointer<CThostFtdcContractBankField> pContractBank, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u0524\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryParkedOrder(CThostFtdcParkedOrderField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:168</i>
	 */
	@Override
	@Virtual(46)
	public void OnRspQryParkedOrder(Pointer<CThostFtdcParkedOrderField> pParkedOrder, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u0524\ufffd\ud98f\uddf5\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryParkedOrderAction(CThostFtdcParkedOrderActionField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:171</i>
	 */
	@Override
	@Virtual(47)
	public void OnRspQryParkedOrderAction(Pointer<CThostFtdcParkedOrderActionField> pParkedOrderAction, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\u0368\u05aa\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryTradingNotice(CThostFtdcTradingNoticeField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:174</i>
	 */
	@Override
	@Virtual(48)
	public void OnRspQryTradingNotice(Pointer<CThostFtdcTradingNoticeField> pTradingNotice, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID,
			boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\u0379\ufffd\u02fe\ufffd\ufffd\ufffd\u05f2\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryBrokerTradingParams(CThostFtdcBrokerTradingParamsField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:177</i>
	 */
	@Override
	@Virtual(49)
	public void OnRspQryBrokerTradingParams(Pointer<CThostFtdcBrokerTradingParamsField> pBrokerTradingParams,
			Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\u0379\ufffd\u02fe\ufffd\ufffd\ufffd\ufffd\ufffd\u3de8\ufffd\ufffd\u04e6<br>
	 * Original signature :
	 * <code>void OnRspQryBrokerTradingAlgos(CThostFtdcBrokerTradingAlgosField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:180</i>
	 */
	@Override
	@Virtual(50)
	public void OnRspQryBrokerTradingAlgos(Pointer<CThostFtdcBrokerTradingAlgosField> pBrokerTradingAlgos, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\u0437\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\u06bb\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnFromBankToFutureByBank(CThostFtdcRspTransferField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:183</i>
	 */
	@Override
	@Virtual(51)
	public void OnRtnFromBankToFutureByBank(Pointer<CThostFtdcRspTransferField> pRspTransfer) {}
	
	/**
	 * \ufffd\ufffd\ufffd\u0437\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\ufffd\ufffd\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnFromFutureToBankByBank(CThostFtdcRspTransferField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:186</i>
	 */
	@Override
	@Virtual(52)
	public void OnRtnFromFutureToBankByBank(Pointer<CThostFtdcRspTransferField> pRspTransfer) {}
	
	/**
	 * \ufffd\ufffd\ufffd\u0437\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u05ea\ufffd\u06bb\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnRepealFromBankToFutureByBank(CThostFtdcRspRepealField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:189</i>
	 */
	@Override
	@Virtual(53)
	public void OnRtnRepealFromBankToFutureByBank(Pointer<CThostFtdcRspRepealField> pRspRepeal) {}
	
	/**
	 * \ufffd\ufffd\ufffd\u0437\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\u05ea\ufffd\ufffd\ufffd\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnRepealFromFutureToBankByBank(CThostFtdcRspRepealField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:192</i>
	 */
	@Override
	@Virtual(54)
	public void OnRtnRepealFromFutureToBankByBank(Pointer<CThostFtdcRspRepealField> pRspRepeal) {}
	
	/**
	 * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\u06bb\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnFromBankToFutureByFuture(CThostFtdcRspTransferField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:195</i>
	 */
	@Override
	@Virtual(55)
	public void OnRtnFromBankToFutureByFuture(Pointer<CThostFtdcRspTransferField> pRspTransfer) {}
	
	/**
	 * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\ufffd\ufffd\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnFromFutureToBankByFuture(CThostFtdcRspTransferField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:198</i>
	 */
	@Override
	@Virtual(56)
	public void OnRtnFromFutureToBankByFuture(Pointer<CThostFtdcRspTransferField> pRspTransfer) {}
	
	/**
	 * \u03f5\u0373\ufffd\ufffd\ufffd\ufffd\u02b1\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\u05b9\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u05ea\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0434\ufffd\ufffd\ufffd\ufffd\ufffd\u03fa\ufffd\ufffd\u0337\ufffd\ufffd\u0635\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnRepealFromBankToFutureByFutureManual(CThostFtdcRspRepealField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:201</i>
	 */
	@Override
	@Virtual(57)
	public void OnRtnRepealFromBankToFutureByFutureManual(Pointer<CThostFtdcRspRepealField> pRspRepeal) {}
	
	/**
	 * \u03f5\u0373\ufffd\ufffd\ufffd\ufffd\u02b1\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\u05b9\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\u05ea\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0434\ufffd\ufffd\ufffd\ufffd\ufffd\u03fa\ufffd\ufffd\u0337\ufffd\ufffd\u0635\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnRepealFromFutureToBankByFutureManual(CThostFtdcRspRepealField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:204</i>
	 */
	@Override
	@Virtual(58)
	public void OnRtnRepealFromFutureToBankByFutureManual(Pointer<CThostFtdcRspRepealField> pRspRepeal) {}
	
	/**
	 * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnQueryBankBalanceByFuture(CThostFtdcNotifyQueryAccountField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:207</i>
	 */
	@Override
	@Virtual(59)
	public void OnRtnQueryBankBalanceByFuture(Pointer<CThostFtdcNotifyQueryAccountField> pNotifyQueryAccount) {}
	
	/**
	 * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\u0631\ufffd<br>
	 * Original signature :
	 * <code>void OnErrRtnBankToFutureByFuture(CThostFtdcReqTransferField*, CThostFtdcRspInfoField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:210</i>
	 */
	@Override
	@Virtual(60)
	public void OnErrRtnBankToFutureByFuture(Pointer<CThostFtdcReqTransferField> pReqTransfer, Pointer<CThostFtdcRspInfoField> pRspInfo) {}
	
	/**
	 * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\ufffd\ufffd\u0434\ufffd\ufffd\ufffd\u0631\ufffd<br>
	 * Original signature :
	 * <code>void OnErrRtnFutureToBankByFuture(CThostFtdcReqTransferField*, CThostFtdcRspInfoField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:213</i>
	 */
	@Override
	@Virtual(61)
	public void OnErrRtnFutureToBankByFuture(Pointer<CThostFtdcReqTransferField> pReqTransfer, Pointer<CThostFtdcRspInfoField> pRspInfo) {}
	
	/**
	 * \u03f5\u0373\ufffd\ufffd\ufffd\ufffd\u02b1\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\u05b9\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u05ea\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\u0631\ufffd<br>
	 * Original signature :
	 * <code>void OnErrRtnRepealBankToFutureByFutureManual(CThostFtdcReqRepealField*, CThostFtdcRspInfoField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:216</i>
	 */
	@Override
	@Virtual(62)
	public void OnErrRtnRepealBankToFutureByFutureManual(Pointer<CThostFtdcReqRepealField> pReqRepeal, Pointer<CThostFtdcRspInfoField> pRspInfo) {}
	
	/**
	 * \u03f5\u0373\ufffd\ufffd\ufffd\ufffd\u02b1\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\u05b9\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\u05ea\ufffd\ufffd\ufffd\u0434\ufffd\ufffd\ufffd\u0631\ufffd<br>
	 * Original signature :
	 * <code>void OnErrRtnRepealFutureToBankByFutureManual(CThostFtdcReqRepealField*, CThostFtdcRspInfoField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:219</i>
	 */
	@Override
	@Virtual(63)
	public void OnErrRtnRepealFutureToBankByFutureManual(Pointer<CThostFtdcReqRepealField> pReqRepeal, Pointer<CThostFtdcRspInfoField> pRspInfo) {}
	
	/**
	 * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0631\ufffd<br>
	 * Original signature :
	 * <code>void OnErrRtnQueryBankBalanceByFuture(CThostFtdcReqQueryAccountField*, CThostFtdcRspInfoField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:222</i>
	 */
	@Override
	@Virtual(64)
	public void OnErrRtnQueryBankBalanceByFuture(Pointer<CThostFtdcReqQueryAccountField> pReqQueryAccount,
			Pointer<CThostFtdcRspInfoField> pRspInfo) {}
	
	/**
	 * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u05ea\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0434\ufffd\ufffd\ufffd\ufffd\ufffd\u03fa\ufffd\ufffd\u0337\ufffd\ufffd\u0635\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnRepealFromBankToFutureByFuture(CThostFtdcRspRepealField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:225</i>
	 */
	@Override
	@Virtual(65)
	public void OnRtnRepealFromBankToFutureByFuture(Pointer<CThostFtdcRspRepealField> pRspRepeal) {}
	
	/**
	 * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\u05ea\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0434\ufffd\ufffd\ufffd\ufffd\ufffd\u03fa\ufffd\ufffd\u0337\ufffd\ufffd\u0635\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnRepealFromFutureToBankByFuture(CThostFtdcRspRepealField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:228</i>
	 */
	@Override
	@Virtual(66)
	public void OnRtnRepealFromFutureToBankByFuture(Pointer<CThostFtdcRspRepealField> pRspRepeal) {}
	
	/**
	 * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\u06bb\ufffd\u04e6\ufffd\ufffd<br>
	 * Original signature :
	 * <code>void OnRspFromBankToFutureByFuture(CThostFtdcReqTransferField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:231</i>
	 */
	@Override
	@Virtual(67)
	public void OnRspFromBankToFutureByFuture(Pointer<CThostFtdcReqTransferField> pReqTransfer, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\ufffd\ufffd\ufffd\u04e6\ufffd\ufffd<br>
	 * Original signature :
	 * <code>void OnRspFromFutureToBankByFuture(CThostFtdcReqTransferField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:234</i>
	 */
	@Override
	@Virtual(68)
	public void OnRspFromFutureToBankByFuture(Pointer<CThostFtdcReqTransferField> pReqTransfer, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6\ufffd\ufffd<br>
	 * Original signature :
	 * <code>void OnRspQueryBankAccountMoneyByFuture(CThostFtdcReqQueryAccountField*, CThostFtdcRspInfoField*, int, bool)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:237</i>
	 */
	@Override
	@Virtual(69)
	public void OnRspQueryBankAccountMoneyByFuture(Pointer<CThostFtdcReqQueryAccountField> pReqQueryAccount, Pointer<CThostFtdcRspInfoField> pRspInfo,
			int nRequestID, boolean bIsLast) {}
	
	/**
	 * \ufffd\ufffd\ufffd\u0437\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bf\ufffd\ufffd\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnOpenAccountByBank(CThostFtdcOpenAccountField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:240</i>
	 */
	@Override
	@Virtual(70)
	public void OnRtnOpenAccountByBank(Pointer<CThostFtdcOpenAccountField> pOpenAccount) {}
	
	/**
	 * \ufffd\ufffd\ufffd\u0437\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnCancelAccountByBank(CThostFtdcCancelAccountField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:243</i>
	 */
	@Override
	@Virtual(71)
	public void OnRtnCancelAccountByBank(Pointer<CThostFtdcCancelAccountField> pCancelAccount) {}
	
	/**
	 * \ufffd\ufffd\ufffd\u0437\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02fa\ufffd\u0368\u05aa<br>
	 * Original signature :
	 * <code>void OnRtnChangeAccountByBank(CThostFtdcChangeAccountField*)</code><br>
	 * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:246</i>
	 */
	@Override
	@Virtual(72)
	public void OnRtnChangeAccountByBank(Pointer<CThostFtdcChangeAccountField> pChangeAccount) {}
	
}