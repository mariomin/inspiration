package com.huatai.common.fix.tag;

public interface FixTags {
	
	/*
	 * Order Basic Tags
	 */
	int Created = 10004;
	int Modified = 10005;
	int AlgoOrderID = 10006;
	int SysNodeID = 10011;
	int OperatorPassword = 10013;
	int PortfolioNo = 10017;
	int PortfolioType = 10018;
	int PortfolioName = 10019;
	int NoCreatePortfolioes = 10020;
	int NoPortfolioItems = 10021;
	int ReturnValue = 10022;
	
	/*
	 * Fund  Tags
	 */
	int YesterdaySettledCash = 10112;
	int HoldingCash = 10113;
	int TradableCash = 10114;
	int NoTradingFunds = 10115;
	int SecurityAccount = 10201;
	int Exchange = 10202;
	int IsMainSecurityAccount = 10203;
	int SecurityAccountStatus = 10204;
	
	int FromTime = 10210;
	int ToTime = 10211;
	int Offset = 10212;
	int PageSize = 10213;
	int EndRecord = 10214;
	int OrdStatusSet = 10215;
	int UserCreatedTime = 10216;
	int BuyCommissionRate = 10217;
	int MinBuyCommission = 10218;
	int SellCommissionRate = 10219;
	int MinSellCommission = 10220;
	int SortOrder = 10230;
	int SortField = 10231;
	int NoExecutionReports = 10232;
	
	/*
	 * Reference Data Tags
	 */
	int EnglishName = 10301;
	int ChineseName = 10302;
	int IndustryClassification = 10303;
	int ParValue = 10304;
	int OutstandingShare = 10305;
	int PublicFloatShareQuantity = 10306;
	int QtyUnit = 10307;
	int CrdBuyUnderlying = 10308;
	int CrdSellUnderlying = 10309;
	int GageRatio = 10310;
	int SecurityTypeCN = 10311;
	int SecurityStatusCN = 10312;
	int SpellCode = 10313;
	
	/*
	 * HundsunSync Data Tags
	 */
	int BussinessFlag = 10401;
	int AssetType = 10402;
	int AssetTransferResult = 10403;
	int BranchNo = 10404;
	
	/*
	 * Position Tags
	 */
	int YesterdaySettledBoughtAmount = 10601;
	int YesterdaySettledBoughtQty = 10602;
	int YesterdaySettledSoldAmount = 10603;
	int YesterdaySettledSoldQty = 10604;
	int IntradayBoughtAmount = 10605;
	int IntradayBoughtQty = 10606;
	int IntradaySoldAmount = 10607;
	int IntradaySoldQty = 10608;
	int HoldingQty = 10609;
	int AvgCost = 10610;
	int TradableQty = 10611;
	int AssignedQty = 10613;
	int PortfolioExtensionDescription = 10614;
	
	/*
	 * Intraday Trading Tags
	 */
	int IntradayTradingCumBuyFreezedQty = 10615;
	int IntradayTradingCumSellFreezedQty = 10618;
	int NoIntradayTradingRecords = 10622;
	
	/*
	 * Account Tags
	 */
	int InvestorAccount = 10700;
	int ProductAccount = 10701;
	int AssetAccount = 10702;
	int AssetAccountType = 10703;
	int TradingAccount = 10704;
	
	/*
	 * Market Data Tags
	 */
	int MDDataType = 11001;
	int MDStatus = 11002;
	int MDTurnOver = 11003;
	
	/*
	 * CFFE CTP Tags
	 */
	int BrokerID = 12001;
	int CombHedgeFlag = 12002;
	int VolumeCondition = 12003;
	int OrderPriceType = 12004;
	int ForceCloseReason = 12005;
	int IsAutoSuspend = 12006;
	int ContingentCondition = 12007;
	int RequestID = 12008;
	int ExchangeID = 12009;
	int OrderSysID = 12010;
	int FrontAddress = 12011;
	int UserProductInfo = 12012;
	int AuthCode = 12013;
	int InitDate = 12014;
	
	/*
	 * Hundsun Data Tags
	 */
	int HundsunInterAccountMapping = 15000;
	int HundsunInterAccount = 15001;
	int HundsunProductID = 15002;
	int HundsunProductName = 15003;
	int HundsunCompanyID = 15004;
	int HundsunCompanyName = 15005;
	int HundsunRoleID = 15006;
	int HundsunRoleName = 15007;
	int HundsunAccountType = 15008;
	
	/*
	 * Fields Reserved for Testing
	 */
	int ClientCreatedNanoTime = 20007;
	int EnterUpStreamNanoTime = 20008;
	int QuitUpStreamNanoTime = 20009;
	int EnterBusinessNanoTime = 20010;
	int QuitBusinessNanoTime = 20011;
	int StartValidationNanoTime = 20012;
	int EndValidationNanoTime = 20013;
	
	/*
	 * SZSE Data Tags
	 */
	int ReportIndex = 10179;
	int StrategyParas = 10500;
	int StrategyReport = 10501;
	int Algo = 10502;
	int AlterReport = 10505;
	int StrategyName = 10506;
	int StrategyState = 10507;
	int StrategyInstruction = 10508;
	int SubscribeID = 10509;
	int NoStrategies = 10510;
	int StrategyID = 10511;
	int NoStrategyNames = 10512;
	int NoSecurityAccounts = 10513;
	
}
