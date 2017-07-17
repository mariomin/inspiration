package com.huatai.platform.algo.integration;

import java.util.ArrayList;
import java.util.List;

import com.huatai.common.algo.SecurityAccountDetail;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.TradingAccountIndex;
import com.huatai.common.type.AssetAccountType;
import com.huatai.common.type.Currency;
import com.huatai.common.type.ExchangeType;
import com.huatai.common.util.IdGenerator;

public abstract class AlgoTradeTestCaseSettings {
	protected static final IdGenerator idGenerator = IdGenerator.getInstance();
	protected static final TradingAccountIndex tradingAccountIndex;
	protected static final String investorAccount = "TestInvestorId";
	protected static final String productAccount = "TestProductId";
	protected static final String assetAccount = "TestAssetAccountType";
	protected static final AssetAccountType assetAccountType = AssetAccountType.cash;
	protected static final String tradingAccount = "TestTradingAccount";
	protected static final String securityAccount = "TestSecurityAccount";
	protected static final ExchangeType exchangeType = ExchangeType.SIM;
	protected static final List<SecurityAccountDetail> securityAccountDetails = new ArrayList<SecurityAccountDetail>();
	protected static final Currency currency = Currency.CNY;
	protected static final String strategy = "MockStrategy";
	protected static final String invalidStrategy = "non-existent strategy";
	protected static final String strategyParas = "{\"AutoSendReport\": false}";
	protected static final char Running = '0';
	protected static final char Paused = '1';
	protected static final char Stopped = '2';
	protected static final char Init = '3';
	protected static final char Undefined = '4';
	
	static {
		tradingAccountIndex = new TradingAccountIndex(new AssetAccountIndex(investorAccount, productAccount, assetAccount, assetAccountType),
				tradingAccount);
		securityAccountDetails.add(new SecurityAccountDetail(securityAccount, exchangeType, currency));
	}
	
}
