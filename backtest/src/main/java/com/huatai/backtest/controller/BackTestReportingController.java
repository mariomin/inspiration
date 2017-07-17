package com.huatai.backtest.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.huatai.backtest.BackTestManager;
import com.huatai.backtest.config.CustomSpringConfigurator;
import com.huatai.backtest.core.BackTestContainer;
import com.huatai.common.backtest.NetValueRecord;
import com.huatai.common.backtest.ProfitVO;
import com.huatai.common.backtest.report.TradeRecord;
import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.type.StrategyState;

@ServerEndpoint(value = "/overview", configurator = CustomSpringConfigurator.class)
@Controller
public class BackTestReportingController {
	private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	@Autowired
	private BackTestManager backTestManager;
	
	private Session session;
	
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
	}
	
	@OnClose
	public void onClose() {}
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		if (backTestManager.getContainers().size() != 1) return;
		
		BackTestContainer container = (BackTestContainer) backTestManager.getContainers().values().iterator().next();
		JsonObject resultObj = new JsonObject();
		
		if (message.equals("update")) {
			Queue<NetValueRecord> netValues = container.getBackTestTradingService().getNetValues();
			List<ProfitVO> profitList = new ArrayList<>();
			
			// TODO ConcurrentModificationException
			for (NetValueRecord record : netValues) {
				ProfitVO profitVO = new ProfitVO(formatter.format(new Date(record.created)), record.netValue, record.netValue);
				profitList.add(profitVO);
			}
			JsonElement profits = gson.toJsonTree(profitList, new TypeToken<List<ProfitVO>>() {}.getType());
			resultObj.add("PnLRecords", profits);
			
			Collection<ExchangeOrder> orders = container.getBackTestTradingService().getExchangeOrders().values();
			List<TradeRecord> tradeRecords = new ArrayList<TradeRecord>();
			for (ExchangeOrder order : orders) {
				TradeRecord tradeRecord = new TradeRecord(new Date(order.getCreated().getTime()), new Date(order.getModified().getTime()),
						order.getSymbol(), order.getOrderSide().name(), order.getLastQty(), order.getLastPx(), order.getCommissionCost(),
						order.getStampDutyCost());
				tradeRecords.add(tradeRecord);
			}
			
			JsonElement reports = gson.toJsonTree(tradeRecords, new TypeToken<List<TradeRecord>>() {}.getType());
			resultObj.add("TradeRecords", reports);
			
			this.session.getBasicRemote().sendText(resultObj.toString());
			
			if (container.getStrategyState() == StrategyState.Stopped) {
				this.session.getBasicRemote().sendText("{\"status\":\"stopped\"}");
				return;
			}
			
		} else if (message.equals("start")) {
			container.start();
		} else if (message.equals("stop")) {
			container.stop();
			container.uninit();
		}
	}
	
}
