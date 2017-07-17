package com.huatai.platform.downstream.adaptor.ctp;

import java.util.concurrent.atomic.AtomicInteger;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.ctp.CThostFtdcInputOrderActionField;
import com.huatai.common.ctp.CThostFtdcInputOrderField;
import com.huatai.common.ctp.CThostFtdcOrderField;
import com.huatai.common.ctp.ThosttraderapiLibrary;
import com.huatai.common.downstream.IDownStreamListener;
import com.huatai.platform.downstream.adaptor.AbstractDownStreamService;
import com.huatai.platform.downstream.adaptor.ctp.common.CThostFtdcTraderApi;

public class CTPExchangeService extends AbstractDownStreamService implements ICTPTradeListener {
	private static final Logger log = LoggerFactory.getLogger(CTPExchangeService.class);
	private final Pointer<CThostFtdcTraderApi> pointerThostFtdcTraderApi = CThostFtdcTraderApi.CreateFtdcTraderApi(Pointer.pointerToCString(""));
	private final CThostFtdcTraderApi api;
	private final CTPTraderSpi spi;
	private final CTPSettings settings;
	private final AtomicInteger idGenerator;
	
	private final String id = "CTP";
	private boolean active;
	private long orderRef;
	private int frontID;
	private int sessionID;
	
	public CTPExchangeService(CTPSettings settings, AtomicInteger idGenerator) {
		this.settings = settings;
		this.idGenerator = idGenerator;
		
		api = pointerThostFtdcTraderApi.get();
		/**
		 * 如果不加入这段代码，会导致 BridJ类中的public static synchronized Object
		 * getJavaObjectFromNativePeer(long peer) {
		 * 获取不到strongNativeObjects的对应对象。
		 */
		BridJ.protectFromGC(api);
		
		spi = new CTPTraderSpi(api, this, settings, idGenerator);
		/**
		 * 如果不加入这段代码，会导致 BridJ类中的public static synchronized Object
		 * getJavaObjectFromNativePeer(long peer) {
		 * 获取不到strongNativeObjects的对应对象。
		 */
		BridJ.protectFromGC(spi);
		
		api.RegisterSpi(Pointer.getPointer(spi));
		api.RegisterFront(Pointer.pointerToCString(settings.getServerAddress()));
		api.Init();
	}
	
	@Override
	public void start() throws Exception {
		active = true;
	}
	
	@Override
	public void stop() {
		active = false;
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public boolean getState() {
		return active;
	}
	
	@Override
	public void newOrder(ExchangeOrder order) {
		CThostFtdcInputOrderField ctpOrder = new CThostFtdcInputOrderField();
		
		ctpOrder.setBrokerID(settings.getBrokerID());
		ctpOrder.setInvestorID(settings.getUserID());
		ctpOrder.setInstrumentID(order.getSymbol());
		ctpOrder.setOrderRef(String.valueOf(orderRef++));
		ctpOrder.setUserID(settings.getUserID());
		ctpOrder.setOrderPriceType((byte) ThosttraderapiLibrary.THOST_FTDC_OPT_LimitPrice);
		ctpOrder.setDirection((byte) ThosttraderapiLibrary.THOST_FTDC_D_Buy);
		ctpOrder.setCombOffsetFlag(String.valueOf(ThosttraderapiLibrary.THOST_FTDC_OF_Open));
		ctpOrder.setCombHedgeFlag(String.valueOf(ThosttraderapiLibrary.THOST_FTDC_HF_Speculation));
		ctpOrder.setLimitPrice(3423.2);
		ctpOrder.setVolumeTotalOriginal(1);
		ctpOrder.setTimeCondition((byte) ThosttraderapiLibrary.THOST_FTDC_TC_GFD);
		ctpOrder.setVolumeCondition((byte) ThosttraderapiLibrary.THOST_FTDC_VC_AV);
		ctpOrder.setMinVolume(1);
		ctpOrder.setContingentCondition((byte) ThosttraderapiLibrary.THOST_FTDC_CC_Immediately);
		ctpOrder.setStopPrice(3420);
		ctpOrder.setForceCloseReason((byte) ThosttraderapiLibrary.THOST_FTDC_FCC_NotForceClose);
		ctpOrder.setIsAutoSuspend(1);
		ctpOrder.setUserForceClose(0);
		
		api.ReqOrderInsert(Pointer.getPointer(ctpOrder), idGenerator.getAndIncrement());
	}
	
	@Override
	public void cancelOrder(ExchangeOrder order) {
		CThostFtdcInputOrderActionField cancel = new CThostFtdcInputOrderActionField();
		
		cancel.setBrokerID(settings.getBrokerID());
		cancel.setInvestorID(settings.getUserID());
		cancel.setOrderRef(order.getId());
		cancel.setFrontID(frontID);
		cancel.setSessionID(sessionID);
		cancel.setOrderSysID(order.getId());
		cancel.setActionFlag((byte) ThosttraderapiLibrary.THOST_FTDC_AF_Delete);
		cancel.setInstrumentID(order.getSymbol());
		
		api.ReqOrderAction(Pointer.getPointer(cancel), idGenerator.getAndIncrement());
	}
	
	@Override
	public boolean addReportNoIfAbsent(String reportNo) {
		return true;
	}
	
	@Override
	public void onOrderConfirmed(CThostFtdcOrderField order) {
		char orderStatus = (char) order.OrderStatus();
		log.info("order confirmed:" + orderStatus);
		if (orderStatus != ThosttraderapiLibrary.THOST_FTDC_OST_Canceled) {
			log.info(order.toString());
			//downstreamListener.onNewOrderAccept(null);
		}
	}
	
	@Override
	public void setDownstreamListener(IDownStreamListener downstreamListener) {}
	
	@Override
	public void onLogin(long orderRef, int frontID, int sessionID) {
		this.orderRef = orderRef;
		this.frontID = frontID;
		this.sessionID = sessionID;
	}
	
}
