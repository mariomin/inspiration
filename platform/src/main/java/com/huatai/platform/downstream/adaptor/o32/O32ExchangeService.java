package com.huatai.platform.downstream.adaptor.o32;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.downstream.IDownStreamListener;
import com.huatai.common.o32.tags.O32Tags;
import com.huatai.platform.downstream.adaptor.AbstractDownStreamService;
import com.huatai.platform.downstream.adaptor.o32.common.ExecutionPosition;
import com.huatai.platform.downstream.adaptor.o32.common.ExecutionPositionDao;
import com.huatai.platform.downstream.adaptor.o32.common.O32Util;
import com.huatai.platform.downstream.adaptor.o32.converter.DownstreamToO32Converter;
import com.huatai.platform.downstream.adaptor.o32.processor.O32StreamProcessor;
import com.hundsun.mcapi.exception.MCException;
import com.hundsun.mcapi.exception.MCSubscribeException;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

import nl.knaw.dans.common.dbflib.CorruptedTableException;
import nl.knaw.dans.common.dbflib.DbfLibException;

public class O32ExchangeService extends AbstractDownStreamService {
	private static final Logger logger = LoggerFactory.getLogger(O32ExchangeService.class);
	@Autowired
	private IDownStreamListener downstreamListener;
	private O32StreamProcessor o32StreamProcessor;
	private final String id;
	private String dataPath;
	private FileWriter reportNoWriter;
	
	private final ConcurrentHashMap<String, String> reportNoMap = new ConcurrentHashMap<String, String>(1000);
	
	private final ScheduledExecutorService scheduler;
	private final ExecutionQueryTask executionQueryTask = new ExecutionQueryTask();
	/**
	 * 回报查询周期，单位s
	 */
	private int executionQueryPeriod = 5;
	
	// assetNo:positionStr
	private final Map<String, ExecutionPosition> executionPositionMap = new ConcurrentHashMap<String, ExecutionPosition>();
	
	private ExecutionPositionDao executionPositionDao;
	private final ConcurrentHashMap<String, String> execIdMap = new ConcurrentHashMap<String, String>(1000);
	
	/**
	 *
	 * @param id 报盘唯一标识
	 * @param config o32配置文件路径
	 * @param serverName o32服务名
	 * @throws MCSubscribeException
	 * @throws MCException
	 * @throws T2SDKException
	 */
	public O32ExchangeService(String id, String config, String serverName) throws T2SDKException, MCException, MCSubscribeException {
		super();
		this.id = id;
		o32StreamProcessor = new O32StreamProcessor(config, serverName);
		
		scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "O32ExecutionQueryThread");
			}
		});
	}
	
	public void setExecutionQueryPeriod(int executionQueryPeriod) {
		this.executionQueryPeriod = executionQueryPeriod;
	}
	
	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}
	
	public String getDataPath() {
		return dataPath;
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public O32StreamProcessor getO32StreamProcessor() {
		return o32StreamProcessor;
	}
	
	@Override
	public void start() throws Exception {
		File path = new File(dataPath);
		if (!path.exists()) {
			FileUtils.forceMkdir(path);
		}
		
		String date = FastDateFormat.getInstance("yyyyMMdd", Locale.getDefault()).format(System.currentTimeMillis());
		
		File reportNoFile = new File(dataPath + File.separator + id + "-" + date + ".reportNo");
		if (reportNoFile.exists()) {
			loadReportNos(reportNoFile);
		} else {
			if (!reportNoFile.createNewFile()) throw new IOException("Create data file:" + reportNoFile.getAbsolutePath() + " fail");
		}
		
		reportNoWriter = new FileWriter(reportNoFile);
		
		File executionPositionFile = new File(dataPath + File.separator + id + "-" + date + "-execPos.dbf");
		
		if (executionPositionFile.exists()) {
			executionPositionDao = new ExecutionPositionDao(executionPositionFile);
			loadExecutionPositions();
		} else {
			executionPositionDao = new ExecutionPositionDao(executionPositionFile);
		}
		
		scheduler.scheduleWithFixedDelay(executionQueryTask, 5, executionQueryPeriod, TimeUnit.SECONDS);
		
		logger.info("O32ExchangeService[{}] started", id);
	}
	
	private void loadExecutionPositions() throws IOException {
		try {
			Collection<ExecutionPosition> executionPositions = executionPositionDao.loadAll();
			if (null != executionPositions) {
				for (ExecutionPosition executionPosition : executionPositions) {
					executionPositionMap.put(executionPosition.getAssetNo(), executionPosition);
				}
			}
		} catch (CorruptedTableException | IOException e) {
			throw new IOException(e);
		}
		
	}
	
	private void loadReportNos(File reportNoFile) throws IOException {
		List<String> lines = FileUtils.readLines(reportNoFile);
		if (null != lines) {
			for (String line : lines) {
				String reportNo = line.trim();
				reportNoMap.put(reportNo, reportNo);
			}
		}
	}
	
	@Override
	public boolean addReportNoIfAbsent(String reportNo) {
		try {
			reportNoWriter.write(reportNo + "\n");
			reportNoWriter.flush();
		} catch (IOException e) {
			logger.error("Write reportNo fail, reportNo=" + reportNo, e);
			
			// TODO 目前以重单处理，阻止报单
			return false;
		}
		String old = reportNoMap.putIfAbsent(reportNo, reportNo);
		return null == old ? true : false;
	}
	
	@Override
	public void stop() {
		try {
			o32StreamProcessor.stop();
			reportNoWriter.close();
			executionPositionDao.close();
		} catch (IOException e) {
			logger.error("", e);
		}
		
		logger.info("O32ExchangeService[{}] stopped", id);
	}
	
	@Override
	public boolean getState() {
		return true;
	}
	
	@Override
	public void newOrder(ExchangeOrder order) {
		try {
			ExecutionPosition executionPosition = new ExecutionPosition(order.getAssetAccount(), order.getInvestorAccount(), "0");
			ExecutionPosition old = executionPositionMap.putIfAbsent(order.getAssetAccount(), executionPosition);
			if (null == old) {
				saveOrUpdateExecutionPosition(executionPosition, true);
			}
			IEvent request = DownstreamToO32Converter.toNewOrderRequest(order);
			
			o32StreamProcessor.send(request);
		} catch (T2SDKException | IOException e) {
			logger.error("Send newOrder to O32 fail, order=" + order, e);
			downstreamListener.onNewOrderReject(order.getReportNo(), e.getMessage());
		}
	}
	
	@Override
	public void cancelOrder(ExchangeOrder order) {
		IEvent request = DownstreamToO32Converter.toCancelOrderRequest(order);
		try {
			o32StreamProcessor.send(request);
		} catch (T2SDKException e) {
			logger.error("Send cancelOrder to O32 fail, order=" + order, e);
			downstreamListener.onCancelOrderReject(order.getReportNo(), e.getMessage());
		}
		
	}
	
	@Override
	public void setDownstreamListener(IDownStreamListener downstreamListener) {
		this.downstreamListener = downstreamListener;
	}
	
	/**
	 *
	 * @param position
	 * @param save true:插入记录，update:更新记录
	 * @throws IOException
	 */
	private synchronized void saveOrUpdateExecutionPosition(ExecutionPosition executionPosition, boolean save) throws IOException {
		try {
			if (save) {
				int index = executionPositionDao.insert(executionPosition);
				executionPosition.setIndex(index);
			} else {
				executionPositionDao.update(executionPosition.getIndex(), executionPosition);
			}
		} catch (IOException | DbfLibException e) {
			throw new IOException(e);
		}
		
	}
	
	/**
	 * 调用回报查询接口获取回报。<P>
	 * @author 010350
	 *
	 */
	private class ExecutionQueryTask implements Runnable {
		@Override
		public void run() {
			// TODO 多账户的频繁调用会不会引起O32流控？
			Set<Entry<String, ExecutionPosition>> entries = executionPositionMap.entrySet();
			for (Entry<String, ExecutionPosition> entry : entries) {
				ExecutionPosition position = entry.getValue();
				IEvent request = DownstreamToO32Converter.toExecutionQueryRequest(position);
				try {
					IEvent response = o32StreamProcessor.sendRecieve(request);
					String newPostionStr = null;
					if (O32Util.isSuccess(response)) {
						IDatasets datasets = response.getEventDatas();
						if (datasets.getDatasetCount() > 1) {
							IDataset dataset = datasets.getDataset(1);
							dataset.beforeFirst();
							while (dataset.hasNext()) {
								dataset.next();
								String reportNo = dataset.getString(O32Tags.Field.extsystemId);
								String execId = dataset.getString(O32Tags.Field.dealNo);
								String dealAmountStr = dataset.getString(O32Tags.Field.dealAmount);
								String dealPriceStr = dataset.getString(O32Tags.Field.dealPrice);
								newPostionStr = dataset.getString(O32Tags.Field.positionStr);
								downstreamListener.onPartiallyFilled(reportNo, execId, Double.valueOf(dealAmountStr), Double.valueOf(dealPriceStr));
							}
						}
					}
					
					if (null != newPostionStr) {
						position.setPositionStr(newPostionStr);
						saveOrUpdateExecutionPosition(position, false);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Scheduler ExecutionQuery task fail, executionPosition=" + position, e);
				}
			}
		}
	}
	
	public void onNewOrderAccept(String reportNo) {
		downstreamListener.onNewOrderAccept(reportNo);
	}
	
	public void onNewOrderReject(String reportNo, String reason) {
		downstreamListener.onNewOrderReject(reportNo, reason);
		
	}
	
	public void onCancelOrderReject(String reportNo, String reason) {
		downstreamListener.onCancelOrderReject(reportNo, reason);
	}
	
	public void onCancelled(String reportNo, double cancelQty) {
		downstreamListener.onCancelled(reportNo, cancelQty);
	}
	
	public void onFilled(String reportNo, String execID, double lastQty, double price) {
		String old = execIdMap.putIfAbsent(execID, execID);
		if (null == old) {
			downstreamListener.onFilled(reportNo, execID, lastQty, price);
		}
	}
	
	public void onPartiallyFilled(String reportNo, String execID, double lastQty, double price) {
		String old = execIdMap.putIfAbsent(execID, execID);
		if (null == old) {
			downstreamListener.onPartiallyFilled(reportNo, execID, lastQty, price);
		}
	}
	
	public void onDuplicateNewOrder(String reportNo) {
		downstreamListener.onDuplicateNewOrder(reportNo);
	}
	
	public void onDuplicateCancelOrder(String cancelReportNo) {
		downstreamListener.onDuplicateCancelOrder(cancelReportNo);
	}
	
}
