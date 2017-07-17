package com.huatai.backtest.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.htsc.mdc.model.ESecurityTypeProtos.ESecurityType;
import com.htsc.mdc.model.MDBoundRecordProtos.MDBoundRecord;
import com.htsc.mdc.model.MDCommissionRecordProtos.MDCommissionRecord;
import com.htsc.mdc.model.MDFundRecordProtos.MDFundRecord;
import com.htsc.mdc.model.MDFutureRecordProtos.MDFutureRecord;
import com.htsc.mdc.model.MDIndexRecordProtos.MDIndexRecord;
import com.htsc.mdc.model.MDKLineProtos.MDKLine;
import com.htsc.mdc.model.MDOptionRecordProtos.MDOptionRecord;
import com.htsc.mdc.model.MDStockRecordProtos.MDStockRecord;
import com.htsc.mdc.model.MDTransactionRecordProtos.MDTransactionRecord;
import com.htsc.mdc.model.MDWarrantRecordProtos.MDWarrantRecord;
import com.htsc.mdc.model.comm.MdcSysPacketProtos.MdcSysPacket;
import com.htsc.mdc.model.rps.ReplayPayloadProtos.ReplayPayload;
import com.htsc.mdc.model.rps.ReplayTaskProtos.ReplayTask.TaskResponse;
import com.htsc.mdc.model.rps.TaskStatusProtos.TaskStatus.TaskStatusResponse;
import com.htsc.mdc.rps.client.ReplayClient;
import com.htsc.mdc.rps.client.rcv.IReplayTaskCallback;
import com.htsc.mdc.rps.common.EExrightsType;
import com.htsc.mdc.rps.common.EReplayDataType;
import com.htsc.mdc.rps.common.ETaskStatus;
import com.huatai.common.backtest.IBackTestMarketDataLoader;
import com.huatai.common.marketdata.CandleStick;
import com.huatai.common.marketdata.MDCMarketDataUtil;
import com.huatai.common.marketdata.MarketData;
import com.huatai.common.marketdata.Quote;
import com.huatai.common.marketdata.TimeFrame;

public class BackTestMarketDataLoader implements IBackTestMarketDataLoader {
	private static final Logger log = LoggerFactory.getLogger(BackTestMarketDataLoader.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	// to record the data transportation of replay request is whether finished
	// or not;
	// key := id of replay request, value := whether finished;
	private final Map<String, Boolean> taskIds = new ConcurrentHashMap<String, Boolean>();
	private final Map<String, List<MarketData>> cache = new HashMap<String, List<MarketData>>();
	
	private String host;
	private int port;
	private String token;
	private ReplayClient replayClient;
	private boolean useCache;
	
	@Override
	public void init() {
		if (replayClient == null) {
			replayClient = new ReplayClient(host, port, token);
		}
		// 创建连接之前需要先初始化发送池和接收池的线程数和缓冲池大小
		// 接收数据较大，所以接收池不能设置过大
		replayClient.startRcvDisruptor(1024, 4).startReqDisruptor(65536, 2).creatConnections(2);
	}
	
	@Override
	public void uninit() {}
	
	@Override
	public List<MarketData> loadMarketData(Set<String> symbols, Date start, Date end, TimeFrame timeFrame) {
		return useCache ? getMarketDataFromCache(symbols, start, end, timeFrame) : loadMarketDataFromMDC(symbols, start, end, timeFrame);
	}
	
	private List<MarketData> getMarketDataFromCache(Set<String> symbols, Date start, Date end, TimeFrame timeFrame) {
		List<MarketData> results = new ArrayList<MarketData>();
		
		for (String symbol : symbols) {
			List<MarketData> sticks = cache.get(symbol);
			if (sticks != null) {
				for (int i = sticks.size() - 1; i > 0; i--) {
					MarketData stick = sticks.get(i);
					if (stick.getTimestamp().before(start)) {
						break;
					} else if (stick.getTimestamp().after(start) && stick.getTimestamp().before(end)) {
						results.add(stick);
					}
				}
			}
		}
		
		Collections.sort(results, new Comparator<MarketData>() {
			
			@Override
			public int compare(MarketData o1, MarketData o2) {
				if (o1.getTimestamp().before(o2.getTimestamp()))
					return -1;
				else if (o1.getTimestamp().after(o2.getTimestamp()))
					return 1;
				else return 0;
			}
			
		});
		
		return results;
	}
	
	private List<MarketData> loadMarketDataFromMDC(Set<String> symbols, Date start, Date end, TimeFrame timeFrame) {
		List<MarketData> results = new ArrayList<MarketData>();
		String taskId = null;
		
		IReplayTaskCallback callback = new IReplayTaskCallback() {
			String taskId;
			
			@Override
			public void onReplayPayload(ReplayPayload payload) {}
			
			@Override
			public void onException(Throwable e) {
				log.error("mdc-rps failed: ", e);
			}
			
			@Override
			public void onTaskStatus(TaskStatusResponse taskStatus) {
				if (ETaskStatus.COMPLETED.toString().equalsIgnoreCase(taskStatus.getStatusCode())) {
					taskIds.put(taskStatus.getTaskId(), true);
				}
			}
			
			@Override
			public void onTaskResponse(TaskResponse taskResponse) {
				taskId = taskResponse.getTaskId();
				if (taskResponse.getIsSuccessful()) {
					taskIds.put(taskId, false);
				} else {
					log.error("send replay request error.");
				}
			}
			
			@Override
			public void onMdcSysPacket(MdcSysPacket mdcPacket) {}
			
			@Override
			public void onReplayMDKLine(String taskId, int totalPayloads, int payloadNO, boolean isFinished, List<MDKLine> kLineList) {
				for (MDKLine mdKLine : kLineList) {
					try {
						results.add(new CandleStick(mdKLine.getHTSCSecurityID(), mdKLine.getOpenPx(), mdKLine.getClosePx(), mdKLine.getHighPx(),
								mdKLine.getLowPx(), mdKLine.getTotalVolumeTrade(), dateFormat.parse(mdKLine.getMDDate() + mdKLine.getMDTime())));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void onReplayStock(String taskId, int payloadNO, MDStockRecord stockRecord) {
				Quote quote = MDCMarketDataUtil.createMDCQuote(stockRecord, null);
				results.add(quote);
			}
			
			@Override
			public void onReplayFuture(String taskId, int payloadNO, MDFutureRecord futureRecord) {}
			
			@Override
			public void onReplayBond(String taskId, int payloadNO, MDBoundRecord mdRecord) {}
			
			@Override
			public void onReplayFund(String taskId, int payloadNO, MDFundRecord mdRecord) {}
			
			@Override
			public void onReplayOption(String taskId, int payloadNO, MDOptionRecord mdRecord) {}
			
			@Override
			public void onReplayWarrant(String taskId, int payloadNO, MDWarrantRecord mdRecord) {}
			
			@Override
			public void onReplayIndex(String taskId, int payloadNO, MDIndexRecord mdRecord) {}
			
			@Override
			public void onReplayTransaction(String taskId, int payloadNO, MDTransactionRecord transactionRecord) {}
			
			@Override
			public void onReplayCommission(String taskId, int payloadNO, MDCommissionRecord commissionRecord) {}
			
		};
		
		try {
			Set<ESecurityType> replaySecurityTypes = new HashSet<ESecurityType>();
			replaySecurityTypes.add(ESecurityType.StockType);
			taskId = replayClient.sendReplayTask(symbols, start, end, EExrightsType.NoExrights, EReplayDataType.MDKLine1D, replaySecurityTypes,
					1000000, callback);
			if (null != taskId) {
				taskIds.put(taskId, false);
			} else {
				log.error("send replay request error.");
			}
		} catch (Exception e) {
			log.error("send replay request failed: ", e);
			if (taskId != null) {
				taskIds.remove(taskId);
			}
		}
		
		// wait until receive all data;
		while (!taskIds.containsKey(taskId) || !taskIds.get(taskId)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.error("thread slepp failed", e);
			}
		}
		return results;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}
	
}
