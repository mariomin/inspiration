package com.huatai.platform.upstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.SystemInfo;
import com.huatai.common.SystemNodeType;
import com.huatai.common.business.command.StrategyAlterCommand;
import com.huatai.common.event.IAsyncEventManager;
import com.huatai.common.event.account.RequestPositionEvent;
import com.huatai.common.event.algo.RegisterredStrategyListRequestEvent;
import com.huatai.common.event.algo.ResumeStrategyEvent;
import com.huatai.common.event.algo.StartStrategyEvent;
import com.huatai.common.event.algo.StopStrategyEvent;
import com.huatai.common.event.algo.StrategyAlterCommandEvent;
import com.huatai.common.event.algo.StrategyQueryEvent;
import com.huatai.common.event.algo.StrategySubscribeEvent;
import com.huatai.common.event.algo.SuspendStrategyEvent;
import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.index.TradingAccountIndex;
import com.huatai.common.strategy.StrategyInstruction;
import com.huatai.common.strategy.StrategySubscriptionType;
import com.huatai.platform.upstream.common.IUpStreamConnection;
import com.huatai.platform.upstream.common.IUpStreamListener;

public class UpStreamListener implements IUpStreamListener {
	private static final Logger log = LoggerFactory.getLogger(UpStreamListener.class);
	private final IUpStreamConnection connection;
	private final IAsyncEventManager eventManager;
	
	public UpStreamListener(SystemInfo systemInfo, IUpStreamConnection connection, IAsyncEventManager eventManager) {
		this.connection = connection;
		this.eventManager = eventManager;
	}
	
	@Override
	public void onState(boolean on) {
		if (!on) {
			log.warn("Upstream connection is down: {}", connection.getId());
		}
	}
	
	@Override
	public void onPositionRequest(String posReqId, int posReqType, TradingAccountIndex tradingAccountIndex) {
		RequestPositionEvent event = new RequestPositionEvent(connection.getId(), posReqId, posReqType, tradingAccountIndex);
		eventManager.sendEvent(event);
		log.debug("send {} to OMS, requestId={}, posReqType={}, account={}", event.getClass().getSimpleName(), posReqId, posReqType,
				tradingAccountIndex);
	}
	
	@Override
	public void onStrategySubscribe(String requestID, SecurityTradingIndex securityTradingIndex, String subscribeID, String strategyID,
			StrategySubscriptionType subType) {
		StrategySubscribeEvent event = new StrategySubscribeEvent(connection.getId(), requestID, securityTradingIndex, subscribeID, strategyID,
				subType);
		eventManager.sendEvent(event);
	}
	
	@Override
	public void onStrategyAlterCommand(StrategyAlterCommand strategyAlterCommand) {
		AsyncEvent event = null;
		SystemNodeType target = SystemNodeType.Undefined;
		
		StrategyInstruction strategyInstruction = strategyAlterCommand.getStrategyInstruction();
		if (strategyInstruction == StrategyInstruction.Create) {
			target = SystemNodeType.OMS;
			event = new StrategyAlterCommandEvent(connection.getId(), strategyAlterCommand);
		} else {
			target = SystemNodeType.Algo;
			if (strategyInstruction == StrategyInstruction.Start) {
				event = new StartStrategyEvent(connection.getId(), strategyAlterCommand);
			} else if (strategyInstruction == StrategyInstruction.Suspend) {
				event = new SuspendStrategyEvent(connection.getId(), strategyAlterCommand);
			} else if (strategyInstruction == StrategyInstruction.Resume) {
				event = new ResumeStrategyEvent(connection.getId(), strategyAlterCommand);
			} else if (strategyInstruction == StrategyInstruction.Stop) {
				event = new StopStrategyEvent(connection.getId(), strategyAlterCommand);
			}
		}
		
		if (event != null) {
			eventManager.sendEvent(event);
			log.debug("send {} to {}, key={}, clOrdId={}, instruction={}", event.getClass().getSimpleName(), target, connection.getId(),
					strategyAlterCommand.getClOrdId(), strategyAlterCommand.getStrategyInstruction());
		}
	}
	
	@Override
	public void onStrategyQuery(String requestID, SecurityTradingIndex index) {
		StrategyQueryEvent event = new StrategyQueryEvent(connection.getId(), requestID, index);
		eventManager.sendEvent(event);
	}
	
	@Override
	public void onStrategyNameRequest(String requestID) {
		RegisterredStrategyListRequestEvent event = new RegisterredStrategyListRequestEvent(connection.getId(), requestID);
		eventManager.sendEvent(event);
	}
	
}
