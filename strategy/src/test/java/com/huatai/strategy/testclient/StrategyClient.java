package com.huatai.strategy.testclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huatai.common.algo.SecurityAccountDetail;
import com.huatai.common.fix.FixConvertor;
import com.huatai.common.fix.message.StrategyAlterRequest;
import com.huatai.common.fix.tag.FixTags;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.TradingAccountIndex;
import com.huatai.common.strategy.StrategyInstruction;
import com.huatai.common.type.AssetAccountType;
import com.huatai.common.type.Currency;
import com.huatai.common.type.ExchangeType;
import com.huatai.common.util.IdGenerator;
import com.huatai.platform.client.FixBaseClient;

import quickfix.Message;

public class StrategyClient {
	private static final Logger log = LoggerFactory.getLogger(StrategyClient.class);
	private static final IdGenerator idGenerator = IdGenerator.getInstance();
	
	@Autowired
	private FixBaseClient fixBaseClient;
	
	private TradingAccountIndex tradingAccountIndex;
	private final String investorAccount = "invsetor01";
	private final String productAccount = "product01";
	private final String assetAccount = "assetAccount01";
	private final AssetAccountType assetAccountType = AssetAccountType.cash;
	private final String tradingAccount = "tradingAccount01";
	private final String securityAccount = "sc01";
	private final ExchangeType exchangeType = ExchangeType.SH;
	private final List<SecurityAccountDetail> securityAccountDetails = new ArrayList<SecurityAccountDetail>();
	private final Currency currency = Currency.CNY;
	private final String strategy = "IntradayStrategy";
	private String strategyParams;
	private final char Running = '0';
	
	public void init() throws Exception {
		tradingAccountIndex = new TradingAccountIndex(new AssetAccountIndex(investorAccount, productAccount, assetAccount, assetAccountType),
				tradingAccount);
		securityAccountDetails.add(new SecurityAccountDetail(securityAccount, exchangeType, currency));
		fixBaseClient.init();
		fixBaseClient.waitServerReady();
	}
	
	public static void main(String[] args) {
		if (args.length < 1) {
			log.error("Missing configuration, application will quit.");
			System.exit(-1);
		}
		AbstractApplicationContext context = null;
		
		StrategyClient strategyTestClient = null;
		
		try {
			String config = args[0];
			context = new ClassPathXmlApplicationContext(config);
			strategyTestClient = (StrategyClient) context.getBean("strategyClient");
			strategyTestClient.init();
			strategyTestClient.testStrategy();
			
			synchronized (strategyTestClient) {
				strategyTestClient.wait();
			}
			
		} catch (Exception e) {
			log.error("found exception: {}", e);
		}
		finally {
			if (null != strategyTestClient.fixBaseClient) {
				strategyTestClient.fixBaseClient.uninit();
			}
			if (context != null) {
				context.close();
			}
		}
	}
	
	private void testStrategy() throws Exception {
		String requestId = idGenerator.getNextID();
		// 1. create strategy
		StrategyAlterRequest request = FixConvertor.convertToStrategyAlterRequest(requestId, null, tradingAccountIndex, securityAccountDetails,
				strategy, strategyParams, StrategyInstruction.Create.value());
		fixBaseClient.sendFixMessage(request);
		
		Message message = fixBaseClient.takeFixMessage();
		assertTrue(message.getBoolean(FixTags.AlterReport));
		String strategyId = message.getString(FixTags.StrategyID);
		
		//2. start strategy
		request = FixConvertor.convertToStrategyAlterRequest(requestId, strategyId, tradingAccountIndex, null, null, null,
				StrategyInstruction.Start.value());
		fixBaseClient.sendFixMessage(request);
		message = fixBaseClient.takeFixMessage();
		assertEquals(message.getString(FixTags.StrategyID), strategyId);
		assertEquals(message.getChar(FixTags.StrategyState), Running);
	}
	
	public void setStrategyParams(String strategyParams) {
		this.strategyParams = strategyParams;
	}
}
