package com.huatai.common.o32;

import java.util.ArrayList;
import java.util.List;

import com.huatai.common.o32.account.O32FundDetailsMapping;
import com.huatai.common.o32.account.O32PositionDetailsMapping;
import com.huatai.common.o32.account.O32TradingAccountMapping;
import com.huatai.common.type.Currency;
import com.huatai.common.util.DecimalUtil;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;

public class O32SynchronizationServiceUtil {
	
	public static List<O32TradingAccountMapping> parseCombination(IDatasets datasets) {
		List<O32TradingAccountMapping> o32TradingAccountMappings = new ArrayList<O32TradingAccountMapping>();
		
		IDataset dataset = datasets.getDataset(1);
		if (dataset != null) {
			int columnCount = dataset.getColumnCount();
			
			String[] columns = new String[columnCount + 1];
			for (int j = 1; j <= columnCount; j++) {
				columns[j] = dataset.getColumnName(j);
			}
			
			dataset.beforeFirst();
			while (dataset.hasNext()) {
				dataset.next();
				
				O32TradingAccountMapping o32TradingAccountMapping = new O32TradingAccountMapping();
				for (int j = 1; j <= columnCount; j++) {
					String column = columns[j];
					String value = dataset.getString(j);
					
					if (column.equals("account_code")) {
						o32TradingAccountMapping.setAccountCode(value);
					} else if (column.equals("asset_no")) {
						o32TradingAccountMapping.setAssetNo(value);
					} else if (column.equals("combi_no")) {
						o32TradingAccountMapping.setCombinationNo(value);
					} else if (column.equals("combi_name")) {
						o32TradingAccountMapping.setCombinationName(value);
					} else if (column.equals("market_no_list")) {
						o32TradingAccountMapping.setAuthorizedMarket(value);
					} else if (column.equals("futu_invest_type_list")) {
						o32TradingAccountMapping.setAuthorizedFuture(value);
					} else if (column.equals("entrust_direction_list")) {
						o32TradingAccountMapping.setAuthorizedTradeSide(value);
					}
				}
				o32TradingAccountMappings.add(o32TradingAccountMapping);
			}
		}
		
		return o32TradingAccountMappings;
	}
	
	public static List<O32FundDetailsMapping> parseFundAccountDetails(IDatasets datasets) {
		List<O32FundDetailsMapping> fundDetailsMappings = new ArrayList<O32FundDetailsMapping>();
		
		IDataset dataset = datasets.getDataset(1);
		if (dataset != null) {
			int columnCount = dataset.getColumnCount();
			
			String[] columns = new String[columnCount + 1];
			for (int j = 1; j <= columnCount; j++) {
				columns[j] = dataset.getColumnName(j);
			}
			
			dataset.beforeFirst();
			while (dataset.hasNext()) {
				dataset.next();
				
				O32FundDetailsMapping fundDetailsMapping = new O32FundDetailsMapping();
				for (int j = 1; j <= columnCount; j++) {
					String column = columns[j];
					String value = dataset.getString(j);
					
					if (column.equals("account_code")) {
						fundDetailsMapping.setAccountCode(value);
					} else if (column.equals("asset_no")) {
						fundDetailsMapping.setAssetNo(value);
					} else if (column.equals("currency_code")) {
						fundDetailsMapping.setCurrency(Currency.getType(value));
					} else if (column.equals("current_balance")) {
						fundDetailsMapping.setCurrentBalance(Double.parseDouble(value));
					}
				}
				fundDetailsMappings.add(fundDetailsMapping);
			}
		}
		
		return fundDetailsMappings;
	}
	
	public static List<O32PositionDetailsMapping> parsePositionDetails(IDatasets datasets) {
		List<O32PositionDetailsMapping> positionDetailsMappings = new ArrayList<O32PositionDetailsMapping>();
		
		IDataset dataset = datasets.getDataset(1);
		if (dataset != null) {
			int columnCount = dataset.getColumnCount();
			
			String[] columns = new String[columnCount + 1];
			for (int j = 1; j <= columnCount; j++) {
				columns[j] = dataset.getColumnName(j);
			}
			
			dataset.beforeFirst();
			while (dataset.hasNext()) {
				dataset.next();
				
				O32PositionDetailsMapping positionDetailsMapping = new O32PositionDetailsMapping();
				double currentCost = 0;
				double currentAmount = 0;
				for (int j = 1; j <= columnCount; j++) {
					String column = columns[j];
					String value = dataset.getString(j);
					if (column.equals("account_code")) {
						positionDetailsMapping.setAccountCode(value);
					} else if (column.equals("asset_no")) {
						positionDetailsMapping.setAssetNo(value);
					} else if (column.equals("combi_no")) {
						positionDetailsMapping.setCombiNo(value);
					} else if (column.equals("stockholder_id")) {
						positionDetailsMapping.setStockHolderId(value);
					} else if (column.equals("current_amount")) {
						currentAmount = Double.parseDouble(value);
					} else if (column.equals("enable_amount")) {
						positionDetailsMapping.setEnableAmount(Double.parseDouble(value));
					} else if (column.equals("current_cost")) {
						currentCost = Double.parseDouble(value);
					} else if (column.equals("stock_code")) {
						positionDetailsMapping.setStockCode(value);
					} else if (column.equals("market_no")) {
						positionDetailsMapping.setMarketNo(value);
					}
				}
				
				positionDetailsMapping.setCurrentAmount(currentAmount);
				if (DecimalUtil.isZero(currentAmount)) {
					positionDetailsMapping.setAvgCost(0d);
				} else {
					positionDetailsMapping.setAvgCost(currentCost / currentAmount);
				}
				positionDetailsMappings.add(positionDetailsMapping);
			}
		}
		
		return positionDetailsMappings;
	}
}
