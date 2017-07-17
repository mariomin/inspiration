package com.huatai.common.o32.tags;

public interface O32Tags {
	
	interface Field {
		String userToken = "user_token";
		String operatorNo = "operator_no";
		String password = "password";
		
		String batchNo = "batch_no";
		String accountCode = "account_code";
		String assetNo = "asset_no";
		String combiNo = "combi_no";
		String instanceNo = "instance_no";
		String instanceType = "instance_type"; // 交易实例类型
		String stockHolderId = "stockholder_id";// 股东代码
		String marketNo = "market_no"; // 交易市场
		String stockCode = "stock_code";// 证券代码
		String entrustDirection = "entrust_direction";//委托方向
		String futuresDirection = "futures_direction"; // 开平方向
		String coveredFlag = "covered_flag"; // 备兑标志
		String priceType = "price_type"; // 委托价格类型
		String entrustPrice = "entrust_price"; // 委托价格
		String entrustAmount = "entrust_amount"; // 委托数量
		String limitEntrustRatio = "limit_entrust_ratio"; // 最小委托比例
		String maxCancelRatio = "max_cancel_ratio"; // 最大禁止比例
		String investType = "invest_type"; // 投资类型
		String extsystemId = "extsystem_id";
		String thirdReff = "third_reff";
		
		String errorCode = "ErrorCode";
		String errorMsg = "ErrorMsg";
		
		String failCause = "fail_cause";
		
		String entrustNo = "entrust_no";
		String businessDate = "business_date";
		String businessTime = "business_time";
		
		String revokeCause = "revoke_cause"; // 废单原因
		
		String cancelEntrustNo = "cancel_entrust_no"; // 撤单的原委托编号
		String cancelAmount = "cancel_amount";
		
		String entrustStatus = "entrust_status";
		String entrustState = "entrust_state";
		String dealNo = "deal_no"; // 成交编号
		String totalDealAmount = "total_deal_amount"; // 累计成交数量
		String totalDealBalance = "total_deal_balance"; // 累计成交金额
		String dealAmount = "deal_amount"; // 本次成交数量
		String dealPrice = "deal_price"; // 本次成交价格
		String dealBalance = "deal_balance"; // 本次成交金额
		String entrustStateList = "entrust_state_list"; // 委托状态列表，逗号分隔
		String positionStr = "position_str"; // 定位串
		String requestNum = "request_num"; // 请求数
		
		String dealDate = "deal_date"; // 成交日期
		String dealTime = "deal_time"; // 成交时间
		
		String entrustDate = "entrust_date"; // 委托日期
		String entrustTime = "entrust_time"; // 委托时间
		
		String positionFlag = "position_flag";// 多空标志
		
		String currentAmount = "current_amount"; // 当前数量
		String targetAmount = "target_amount";// 目标数量
		String enableAmount = "enable_amount"; //卖出可用数量
		String beginBalance = "begin_balance"; // 期初成本
		String currentCost = "current_cost"; // 当前成本
		String preBuyAmount = "pre_buy_amount"; // 买挂单数量
		String preSellAmount = "pre_sell_amount";// 卖挂单数量
		String preBuyBalance = "pre_buy_balance"; // 买挂单金额
		String preSellBalance = "pre_sell_balance"; //卖挂单金额
		
		String todayBuyAmount = "today_buy_amount"; // 当日买入数量
		String todaySellAmount = "today_sell_amount";
		String todayBuyBalance = "today_buy_balance";
		String todaySellBalance = "today_sell_balance";
		
		String todayBuyFee = "today_buy_fee"; // 当日买费用
		String todaySellFee = "today_sell_fee"; //当日卖费用
		
		String buyAmount = "buy_amount";
		String sellAmount = "sell_amount";
		String buyBalance = "buy_balance";
		String sellBalance = "sell_balance";
		
		String buyFee = "buy_fee";
		String sellFee = "sell_fee";
		
		String templateNo = "template_no"; // 现货模板序号
		String longMarketNo = "long_market_no"; // 多头代码市场
		String longReportCode = "long_report_code";// 多头代码
		String shortMarketNo = "short_market_no";// 空头代码市场
		String shortReportCode = "short_report_code"; //空头代码
		String operNum = "oper_num"; //操作份数
		String investPlanNo = "invest_plan_no";//监控单元号
		
		String createDate = "create_date"; // 创建日期
		
		String currency = "currency_code";
		String currentBalance = "current_balance";
		
		String stockName = "stock_name";
		String futureKindName = "future_kind_name";
		String settlementMonth = "settlement_month";// 合约月份
		String targetMarketNo = "target_market_no"; // 标的物市场
		String targetStockCode = "target_stock_code"; //标的物代码
		String multiple = "multiple";// 合约乘数
		String lastTradeDate = "last_trade_date";// 最后交易日
		String lastTradeTime = "last_trade_time";// 最后交易时间
		String settlementDate = "settlement_date";// 交割日
		String settlementPrice = "settlement_price";// 结算价
		String preSettlementPrice = "pre_settlement_price";// 前结算价
		String marketPosition = "market_position"; // 市场持仓量
		String preMarketPosition = "pre_market_position";// 前市场持仓量
		String marketPricePermit = "market_price_permit";// 市价申报许可
		String upLimitPrice = "uplimited_price";
		String downLimitPrice = "downlimited_price";
		
		String closeDirection = "close_direction";
		
		String limitDealAmount = "limit_deal_amount";
		
		String successFlag = "success_flag";
		
		String occupyDepositBalance = "occupy_deposit_balance";
		
		String enableDepositBalance = "enable_deposit_balance";
		String enableBalanceT0 = "enable_balance_t0";
		
	}
	
	interface Value {
		interface SuccessFlag {
			String success = "1";
			String fail = "2";
		}
		
		interface EntrustDirection {
			String buy = "1";
			String sell = "2";
		}
		
		interface FuturesDirection {
			String open = "1"; // 开仓
			String close = "2"; // 平仓
		}
		
		interface MarketNo {
			String sh = "1"; // 上交所
			String sz = "2"; // 深交所
			String shf = "3"; // 上期所
			String czce = "4"; //郑商所
			String cffex = "7"; // 中金所
			String dcz = "9"; //大商所
			String neeq = "10"; //股转市场
			String ggt = "35"; //港股通
			
		}
		
		interface EntrustStatus {
			String unReported = "1"; //未报
			String prepareReport = "2"; // 待报
			String reporting = "3"; // 正报
			String reported = "4";// 已报
			String reject = "5";// 废单
			String partiallyFilled = "6"; // 部成
			String filled = "7"; // 已成
			String partiallyCancelled = "8"; // 部撤
			String cancelled = "9"; // 已撤
			String prepareCancel = "a";
			//
		}
		
		interface PriceType {
			String limit = "0";
			String arbitrary4Future = "1"; // 任意价(期货)
			String fifthIsLeftOff4SHMarket = "a"; // 五档即成剩撤(上交所市价）
			String fifthIsLeftTurn4SHMarket = "b";// 五档即成剩转(上交所市价）
			
			String fifthIsLeftOff4SZMarket = "A"; // 五档即成剩撤(深交所市价）
			String leftOff4SZMarket = "C";// 即成剩撤（深交所市价）
			String counterPartyOptimal4SZMarket = "D"; // 对手方最优（深交所市价）;
			String optimal4SZMarket = "E"; // 本方最优（深交所市价）
			String fok4SZMarket = "F";// 全额成或撤（深交所市价）
			String fok4SHF_CFFEX_SZ = "G";// 全额成或撤（FOK市价）（深交所市价）
			String fak4SHF_CFFEX = "K"; // 即成剩撤（FAK）（上期所、郑商所、中金所）
			
			String arbitrary2Limit4CFFEX = "X";// 任意价转限价(中金所）
			
			String fifthIsLeftOff4CFFEXMarket = "L"; // 五档即成剩撤(中金所五档市价）
			String fifthIsLeftTurn4CFFEXMarket = "M";// 五档即成剩转(中金所五档市价转限价）
			
			String firstIsLeftOff4CFFEXMarket = "N"; // 最优一档即成剩撤(中金所最优价）
			String firstIsLeftTurn4CFFEXMarket = "O";// 最优一档即成剩转(中金所最优价）
			
		}
	}
	
	interface FunctionId {
		String login = "10001";
		String heartbeat = "10000";
		String newOrderSingle = "91001";
		String newOrderList = "91090";
		
		String amEntrustQuery = "32001"; // 证券委托查询
		String amRealDealQuery = "33001"; // 证券成交查询
		
		String cancelOrder = "91101"; // 按委托号撤单
		
		String createInstance = "70001"; // 创建交易实例
		String queryInstance = "70002"; // 交易实例查询
		
		String queryPosition = "31001"; // 证券持仓查询
		String queryPortfolioPosition = "35002";// 实例持仓查询
		String queryAsset = "35011"; // 资产单元资产查询
		String queryFund = "34001";
		
		String queryStockHolder = "30004"; //查询默认股东账户
		
		String queryFuturesList = "30010"; // 期货信息查询
		
		String futureNewOrderSingle = "91004"; // 期货委托
		
		String queryFuturePosition = "31003";
		
		String futureCancelOrder = "91105";
		
		String futureAmEntrustQuery = "32003";
		String futureAmRealDealQuery = "33003";
		
		String futureMarginQuery = "34003";// 期货保证金查询
		
	}
}
