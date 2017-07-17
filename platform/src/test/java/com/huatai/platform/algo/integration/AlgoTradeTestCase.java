package com.huatai.platform.algo.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.huatai.common.fix.FixConvertor;
import com.huatai.common.fix.message.RegisterredStrategyListRequest;
import com.huatai.common.fix.message.StrategyAlterRequest;
import com.huatai.common.fix.tag.FixTags;
import com.huatai.common.strategy.StrategyInstruction;
import com.huatai.platform.algo.AlgoTradeManager;
import com.huatai.platform.algo.StrategyContainer;
import com.huatai.platform.client.FixBaseClient;
import com.huatai.xtrade.xstep.app.value.XValues.MsgType;
import com.huatai.xtrade.xstep.commu.application.IXStepApplication;
import com.huatai.xtrade.xstep.commu.connector.XStepConnector;
import com.huatai.xtrade.xstep.commu.processor.XStepChannel;
import com.huatai.xtrade.xstep.event.IXStepEvent;
import com.huatai.xtrade.xstep.event.XStepEvent;

import quickfix.Group;
import quickfix.Message;
import quickfix.field.SubscriptionRequestType;
import quickfix.field.UserRequestID;

public class AlgoTradeTestCase extends AlgoTradeTestCaseSettings {
	
	private static void testCreateInvalidNewStrategy(FixBaseClient client) throws Exception {
		String requestId = idGenerator.getNextID();
		
		// 1. create request
		StrategyAlterRequest request = FixConvertor.convertToStrategyAlterRequest(requestId, null, tradingAccountIndex, securityAccountDetails,
				invalidStrategy, strategyParas, StrategyInstruction.Create.value());
		client.sendFixMessage(request);
		
		// 2. assert response, expect the strategy initialized
		Message message = client.takeFixMessage();
		assertFalse(message.getBoolean(FixTags.AlterReport));
	}
	
	private static void testCreateValidNewStrategy(FixBaseClient client, AlgoTradeManager algoTradeManager) throws Exception {
		String requestId = idGenerator.getNextID();
		
		// 1. create strategy
		StrategyAlterRequest request = FixConvertor.convertToStrategyAlterRequest(requestId, null, tradingAccountIndex, securityAccountDetails,
				strategy, strategyParas, StrategyInstruction.Create.value());
		client.sendFixMessage(request);
		
		// 2. assert response, expect the strategy initialized
		Message message = client.takeFixMessage();
		assertTrue(message.getBoolean(FixTags.AlterReport));
		String strategyId = message.getString(FixTags.StrategyID);
		assertNotNull(strategyId);
		assertEquals(message.getChar(FixTags.StrategyState), Init);
		
		// 3. start strategy
		request = FixConvertor.convertToStrategyAlterRequest(requestId, strategyId, tradingAccountIndex, null, null, null,
				StrategyInstruction.Start.value());
		client.sendFixMessage(request);
		message = client.takeFixMessage();
		
		// 4. assert response, expect the strategy running
		assertEquals(message.getString(FixTags.StrategyID), strategyId);
		assertEquals(message.getChar(FixTags.StrategyState), Running);
		
		// 5. suspend strategy
		request = FixConvertor.convertToStrategyAlterRequest(requestId, strategyId, tradingAccountIndex, null, null, null,
				StrategyInstruction.Suspend.value());
		client.sendFixMessage(request);
		
		// 6. assert response, expect the strategy paused
		message = client.takeFixMessage();
		assertEquals(message.getString(FixTags.StrategyID), strategyId);
		assertEquals(message.getChar(FixTags.StrategyState), Paused);
		
		// 7. resume strategy
		request = FixConvertor.convertToStrategyAlterRequest(requestId, strategyId, tradingAccountIndex, null, null, null,
				StrategyInstruction.Resume.value());
		client.sendFixMessage(request);
		
		// 8. assert response, expect the strategy running
		message = client.takeFixMessage();
		assertEquals(message.getString(FixTags.StrategyID), strategyId);
		assertEquals(message.getChar(FixTags.StrategyState), Running);
		
		// 9. stop strategy
		request = FixConvertor.convertToStrategyAlterRequest(requestId, strategyId, tradingAccountIndex, null, null, null,
				StrategyInstruction.Stop.value());
		client.sendFixMessage(request);
		
		// 10. assert response, expect the strategy stopped
		message = client.takeFixMessage();
		assertEquals(message.getString(FixTags.StrategyID), strategyId);
		assertEquals(message.getChar(FixTags.StrategyState), Stopped);
		assertNull(algoTradeManager.getStrategyContainer(strategyId));
	}
	
	private static void testSubscribeStrategy(FixBaseClient client, AlgoTradeManager algoTradeManager) throws Exception {
		// 1. create strategy
		String requestId = idGenerator.getNextID();
		Message request = FixConvertor.convertToStrategyAlterRequest(requestId, null, tradingAccountIndex, securityAccountDetails, strategy,
				strategyParas, StrategyInstruction.Create.value());
		client.sendFixMessage(request);
		
		// 2. assert response, expect the strategy initialized
		Message message = client.takeFixMessage();
		assertTrue(message.getBoolean(FixTags.AlterReport));
		String strategyId = message.getString(FixTags.StrategyID);
		assertNotNull(strategyId);
		assertEquals(message.getChar(FixTags.StrategyState), Init);
		
		// 3. subscribe strategy
		String subscribeId = idGenerator.getNextID();
		request = FixConvertor.convertToStrategySubscribeRequest(requestId, strategyId, tradingAccountIndex, subscribeId,
				SubscriptionRequestType.SNAPSHOT_PLUS_UPDATES);
		client.sendFixMessage(request);
		
		// 4. assert response, expect the strategy subscribed
		message = client.takeFixMessage();
		assertEquals(message.getString(UserRequestID.FIELD), requestId);
		
		// 5. server sends report, expect client receiver it
		StrategyContainer container = algoTradeManager.getStrategyContainer(strategyId);
		String strategyReport = "sample report";
		container.sendStrategyReport(strategyReport);
		message = client.takeFixMessage();
		assertEquals(message.getString(FixTags.StrategyID), strategyId);
		assertEquals(message.getString(FixTags.StrategyReport), strategyReport);
		assertEquals(message.getString(FixTags.SubscribeID), subscribeId);
		
		// 6. un-subscribe strategy
		request = FixConvertor.convertToStrategySubscribeRequest(requestId, strategyId, tradingAccountIndex, subscribeId,
				SubscriptionRequestType.DISABLE_PREVIOUS_SNAPSHOT_PLUS_UPDATE_REQUEST);
		client.sendFixMessage(request);
		
		// 7. expect the strategy un-subscribed
		message = client.takeFixMessage();
		assertEquals(message.getString(UserRequestID.FIELD), requestId);
		
		// 8. server tries to send report, expect client will not receiver it
		container.sendStrategyReport(strategyReport);
		try {
			message = client.takeFixMessage();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Time out for waiting response...");
		}
		
		// 9. stop strategy
		request = FixConvertor.convertToStrategyAlterRequest(requestId, strategyId, tradingAccountIndex, null, null, null,
				StrategyInstruction.Stop.value());
		client.sendFixMessage(request);
		
		// 10. assert response, expect to the strategy stopped
		message = client.takeFixMessage();
		assertEquals(message.getString(FixTags.StrategyID), strategyId);
		assertEquals(message.getChar(FixTags.StrategyState), Stopped);
		assertNull(algoTradeManager.getStrategyContainer(strategyId));
	}
	
	private static void testStrategyQueryReqeust(FixBaseClient client, AlgoTradeManager algoTradeManager) throws Exception {
		// 1. create strategy
		String requestId = idGenerator.getNextID();
		Message request = FixConvertor.convertToStrategyAlterRequest(requestId, null, tradingAccountIndex, securityAccountDetails, strategy,
				strategyParas, StrategyInstruction.Create.value());
		client.sendFixMessage(request);
		
		// 2. assert response, expect the strategy initialized
		Message message = client.takeFixMessage();
		assertTrue(message.getBoolean(FixTags.AlterReport));
		String strategyId = message.getString(FixTags.StrategyID);
		assertNotNull(strategyId);
		assertEquals(message.getChar(FixTags.StrategyState), Init);
		
		// 3. request to query a specific strategy
		request = FixConvertor.convertToStrategyQueryRequest(requestId, tradingAccountIndex);
		client.sendFixMessage(request);
		
		// 4. assert response, expect the strategy details
		message = client.takeFixMessage();
		List<Group> groups = message.getGroups(FixTags.NoStrategies);
		assertEquals(groups.size(), 1);
		assertEquals(groups.get(0).getString(FixTags.StrategyID), strategyId);
		assertEquals(groups.get(0).getString(FixTags.StrategyName), strategy);
		assertEquals(groups.get(0).getChar(FixTags.StrategyState), Init);
		
		// 5. stop strategy
		request = FixConvertor.convertToStrategyAlterRequest(requestId, strategyId, tradingAccountIndex, null, null, null,
				StrategyInstruction.Stop.value());
		client.sendFixMessage(request);
		
		// 6. assert response, expect the strategy stopped
		message = client.takeFixMessage();
		assertEquals(message.getString(FixTags.StrategyID), strategyId);
		assertEquals(message.getChar(FixTags.StrategyState), Stopped);
		assertNull(algoTradeManager.getStrategyContainer(strategyId));
	}
	
	private static void testRegisterredStrategyListRequest(FixBaseClient client) throws Exception {
		// 1. create strategy list request
		String requestId = idGenerator.getNextID();
		RegisterredStrategyListRequest request = new RegisterredStrategyListRequest();
		request.setField(new UserRequestID(requestId));
		client.sendFixMessage(request);
		
		// 2. assert response, expect to receiver registered strategies
		Message message = client.takeFixMessage();
		List<Group> groups = message.getGroups(FixTags.NoStrategyNames);
		assertTrue(groups.size() > 1);
	}
	
	public static void testAlgoTrade(FixBaseClient client, AlgoTradeManager algoTradeManager) throws Exception {
		testCreateInvalidNewStrategy(client);
		testCreateValidNewStrategy(client, algoTradeManager);
		testSubscribeStrategy(client, algoTradeManager);
		testStrategyQueryReqeust(client, algoTradeManager);
		testRegisterredStrategyListRequest(client);
	}
	
	private static class ClientApplication implements IXStepApplication {
		private XStepChannel xstepChannel;
		
		@Override
		public void channelActive(XStepChannel xstepchannel) {
			xstepChannel = xstepchannel;
		}
		
		@Override
		public void channelInActive(XStepChannel xstepchannel) {}
		
		@Override
		public void channelRead(XStepChannel xstepchannel, IXStepEvent xstepevent) throws Exception {
			System.err.println("Receive reply:" + xstepevent);
		}
		
		public void sendRequest(IXStepEvent xstepevent) {
			xstepChannel.send((XStepEvent) xstepevent);
		}
	}
	
	public static void testAcceptXStepEvent() {
		XStepConnector client = new XStepConnector();
		client.setHost("localhost");
		client.setPort(20003);
		ClientApplication clientApplication = new ClientApplication();
		client.setApplictionProcessor(clientApplication);
		client.startConnector();
		
		try {
			Thread.sleep(1000);
			clientApplication.sendRequest(new XStepEvent(MsgType.orderCancelRequest));
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
