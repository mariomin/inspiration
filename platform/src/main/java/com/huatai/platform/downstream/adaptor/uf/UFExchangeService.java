package com.huatai.platform.downstream.adaptor.uf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.downstream.DownStreamException;
import com.huatai.common.downstream.IDownStreamListener;
import com.huatai.common.type.OrderAction;
import com.huatai.common.type.OrderField;
import com.huatai.common.util.DecimalUtil;
import com.huatai.common.util.DualMap;
import com.huatai.platform.downstream.adaptor.IExchangeService;
import com.huatai.platform.downstream.adaptor.uf.common.CommandInfo;
import com.huatai.platform.downstream.adaptor.uf.common.EntrustInfo;
import com.huatai.platform.downstream.count.CountGeneratorFactory;
import com.huatai.platform.downstream.count.CountReferenceId;
import com.huatai.platform.downstream.count.ICountGenerator;
import com.huatai.platform.schedule.Scheduler;
import com.hundsun.mcapi.exception.MCSubscribeException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;

public class UFExchangeService implements IExchangeService {
	private static final Logger log = LoggerFactory.getLogger(UFExchangeService.class);
	private UFUtil ufInvokeService;
	// map<clientid,sysnodeid>
	private final Map<String, Integer> clientId2SysnodeId = new ConcurrentHashMap<String, Integer>();
	// map<entrustNo,orderId>
	private final DualMap<Integer, String> entrustNo2OrderId = new DualMap<Integer, String>();
	
	// scheduler
	private final Scheduler dealReturnScheduler = new Scheduler("UF-Fetch-DealReturn");
	private final Scheduler entrustScheduler = new Scheduler("UF-Fetch-Entrust");
	// un-finished entrust<entrustno,orderid>
	private final Map<Integer, EntrustInfo> unFinishedEntrust = new ConcurrentHashMap<Integer, EntrustInfo>();
	// un-confirmed entrust<batchNo,order>
	private final Map<Integer, CommandInfo> unConfirmedEntrust = new ConcurrentHashMap<Integer, CommandInfo>();
	
	private String id;
	private String config;
	private String serviceName;
	
	private boolean active;
	@SuppressWarnings("unused")
	private ICountGenerator generator;
	
	public UFExchangeService() {
		super();
	}
	
	@Override
	public void start() throws Exception {
		ufInvokeService = new UFUtil(config, serviceName);
		ufInvokeService.init();
		generator = CountGeneratorFactory.getInstance().getGenerator(CountReferenceId.UFExchangeService);
		dealReturnScheduler.scheduleWithFixedDelay(new FetchDealReturnTask(), 1L, 1L, TimeUnit.SECONDS);
		entrustScheduler.scheduleWithFixedDelay(new FetchEntrustTask(), 1L, 1L, TimeUnit.SECONDS);
		active = true;
		log.info("UFExchangeService[{}] started", id);
		
	}
	
	@Override
	public void stop() {
		try {
			ufInvokeService.unsubscribe();
		} catch (MCSubscribeException e) {
			e.printStackTrace();
		}
		active = false;
		log.info("UFExchangeService[{}] shutdown", id);
	}
	
	@Override
	public boolean addReportNoIfAbsent(String reportNo) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private Integer login(ExchangeOrder order) throws DownStreamException {
		log.info("Login to Hundsun.");
		IDataset dataset = ufInvokeService.login(order);
		return dataset.getInt("sysnode_id");
	}
	
	@Override
	public void newOrder(ExchangeOrder order) {
		//		String clientId = order.getField(String.class, OrderField.ClientId.name());
		//		int sysnodeId = getSysnodeId(clientId, order);
		//		ufInvokeService.subscribe(clientId);
		//		log.info("Send new order for client[{}].", clientId);
		//		IDataset dataset = null;
		//		try {
		//			dataset = ufInvokeService.sendNewOrder(sysnodeId, order);
		//			if (dataset.getInt("error_no") < 0) {
		//				onNewOrderReject(dataset, dataset.getInt("error_no"), UFUtil.convertExchangeType(order.getExchangeType().name()), order.getId());
		//				return;
		//			}
		//		} catch (DownStreamException e) {
		//			log.info("Send new order[{}] for client[{}] failed.", order, clientId);
		//			unConfirmedEntrust.put(order.getField(Integer.class, OrderField.BatchNo.name()), new CommandInfo(OrderAction.NEW, order));
		//			return;
		//		}
		//		int entrustNo = dataset.getInt("entrust_no");
		//		order.putField(OrderField.EntrustNo.name(), entrustNo);
		//		unFinishedEntrust.put(entrustNo, new EntrustInfo(order));
		//		entrustNo2OrderId.put(entrustNo, order.getId());
		//		onNewOrderAccept(dataset, entrustNo, UFUtil.convertExchangeType(order.getExchangeType().name()), order);
	}
	
	@Override
	public void cancelOrder(ExchangeOrder order) {
		String clientId = order.getField(String.class, OrderField.ClientId.name());
		log.info("Send cancel order for client[{}].", clientId);
		try {
			IDataset dataset = ufInvokeService.sendCancelOrder(getSysnodeId(clientId, order), order);
			if (dataset.getInt("error_no") < 0) {
				onCancelOrderReject(dataset, dataset.getInt("error_no"), UFUtil.convertExchangeType(order.getExchangeType()), order.getId());
				return;
			}
		} catch (DownStreamException e) {
			log.info("Send cancel order[{}] for client[{}] failed.", order, clientId);
			unConfirmedEntrust.put(order.getField(Integer.class, OrderField.BatchNo.name()), new CommandInfo(OrderAction.NEW, order));
		}
	}
	
	public void onNewOrderAccept(IDataset dataset, int entrustNo, String exchangeType, ExchangeOrder order) {
		//listener.onNewOrderAccept(UFUtil.antiConvertExchangeType(exchangeType), id, order.getId(), entrustNo, order);
	}
	
	// 当返回dataset为错误信息时，entrustNo的取值为errorno，rejectreason的取值为orderid
	public void onNewOrderReject(IDataset dataset, int entrustNo, String exchangeType, String rejectReason) {
		//		if (entrustNo < 0) {
		//			String orderId = rejectReason;
		//			int errorNo = entrustNo;
		//			String errorInfo = dataset.getString("error_info");
		//			listener.onNewOrderReject(UFUtil.antiConvertExchangeType(exchangeType), id, orderId, generator.next(), errorNo, errorInfo, null);
		//		} else {
		//			String orderId = entrustNo2OrderId.get(entrustNo);
		//			if (!StringUtils.isEmpty(orderId)) {
		//				listener.onNewOrderReject(UFUtil.antiConvertExchangeType(exchangeType), id, orderId, generator.next(), 999, rejectReason, null);
		//			}
		//		}
	}
	
	public void onCancelOrderReject(IDataset dataset, int entrustNo, String exchangeType, String rejectReason) {
		//		if (entrustNo < 0) {
		//			String orderId = rejectReason;
		//			int errorNo = entrustNo;
		//			String errorInfo = dataset.getString("error_info");
		//			listener.onCancelOrderReject(UFUtil.antiConvertExchangeType(exchangeType), id, orderId, generator.next(), errorNo, errorInfo);
		//		} else {
		//			String orderId = entrustNo2OrderId.get(entrustNo);
		//			if (!StringUtils.isEmpty(orderId)) {
		//				listener.onCancelOrderReject(UFUtil.antiConvertExchangeType(exchangeType), id, orderId, generator.next(), 999, rejectReason);
		//			}
		//		}
	}
	
	/**
	 * 处理成交回报
	 * @param dataset		成交回报数据
	 * @param entrustNo		被成交订单的委托编号
	 * @param exchangeType	交易所类型
	 * @param execID		成交回报编号
	 * @param lastQty		最近一次成交数量
	 * @param lastPx		最近一次成交价格
	 */
	public void onPartiallyFilled(IDataset dataset, int entrustNo, String exchangeType, String execID, double lastQty, double lastPx) {
		//		log.info("Process partially filled. EntrustNo[{}]", entrustNo);
		//		String orderId = entrustNo2OrderId.get(entrustNo);
		//		if (!StringUtils.isEmpty(orderId)) {
		//			listener.onPartiallyFilled(UFUtil.antiConvertExchangeType(exchangeType), id, orderId, execID, generator.next(), lastQty, lastPx);
		//		}
	}
	
	/**
	 * 处理撤单回报
	 * @param dataset		撤单回报数据
	 * @param entrustNo		撤单的委托编号
	 * @param entrustNo_old	被撤单的委托编号
	 * @param exchangeType	交易所类型
	 * @param cancelQty		撤单数量
	 */
	public void onCancelled(IDataset dataset, int entrustNo, int entrustNo_old, String exchangeType, double cancelQty) {
		//		log.info("Process canceled. EntrustNo[{}]", entrustNo);
		//		String orderId = entrustNo2OrderId.get(entrustNo_old);
		//		if (!StringUtils.isEmpty(orderId)) {
		//			listener.onCancelled(UFUtil.antiConvertExchangeType(exchangeType), id, orderId, generator.next(), cancelQty);
		//		}
	}
	
	private int getSysnodeId(String clientId, ExchangeOrder order) throws DownStreamException {
		if (clientId2SysnodeId.containsKey(clientId))
			return clientId2SysnodeId.get(clientId);
		else {
			synchronized (clientId2SysnodeId) {
				if (clientId2SysnodeId.containsKey(clientId))
					return clientId2SysnodeId.get(clientId);
				else {
					int sysnodeId = login(order);
					clientId2SysnodeId.put(clientId, sysnodeId);
					return sysnodeId;
				}
			}
		}
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public boolean getState() {
		return active;
	}
	
	@Override
	public void setDownstreamListener(IDownStreamListener downstreamListener) {}
	
	public String getConfig() {
		return config;
	}
	
	public void setConfig(String config) {
		this.config = config;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	private class FetchEntrustTask implements Runnable {
		@Override
		public void run() {
			for (Entry<Integer, CommandInfo> entry : unConfirmedEntrust.entrySet()) {
				// 获取查询未确认订单相关参数
				int batchNo = entry.getKey();
				CommandInfo info = entry.getValue();
				ExchangeOrder order = info.order;
				String clientId = order.getInvestorAccount();
				Integer sysnodeId = clientId2SysnodeId.get(clientId);
				if (sysnodeId == null) {
					continue;
				}
				// 查询未确认订单
				List<IDataset> list = new ArrayList<IDataset>();
				try {
					ufInvokeService.searchEntrust(sysnodeId, batchNo, order);
				} catch (DownStreamException e) {
					// 如果查询出现问题，则跳过该订单号的查询，等候下一次轮询
					continue;
				}
				// list长度为0则跳过，list长度为1则处理，list长度不会超过1
				if (list.size() == 0) {
					if (info.action.equals(OrderAction.NEW)) {
						//try {
						newOrder(order);
						unConfirmedEntrust.remove(batchNo);
						//						} catch (DownStreamException e) {
						//							// 由于登录和订阅导致的Exception忽略
						//							log.error("Login or subscribe failed. orde[{}]", order);
						//							continue;
						//						}
					} else if (info.action.equals(OrderAction.CANCEL)) {
						cancelOrder(order);
						// TODO 在新的订单撤单中，batch对订单撤单都是唯一的
						unConfirmedEntrust.remove(batchNo);
					}
				}
				
				for (IDataset dataset : list) {
					unConfirmedEntrust.remove(batchNo);
					if (dataset.getString("entrust_type") == "0") {// 0表示新订单委托
						int entrustNo = dataset.getInt("entrust_no");
						unFinishedEntrust.put(entrustNo, new EntrustInfo(order));
						entrustNo2OrderId.put(entrustNo, order.getId());
					}
				}
			}
		}
	}
	
	private class FetchDealReturnTask implements Runnable {
		@Override
		public void run() {
			// 从T2接口直接获取未处理的成交回报
			for (Entry<Integer, EntrustInfo> entry : unFinishedEntrust.entrySet()) {
				/**
				 * 获取查询成交回报相关参数
				 */
				EntrustInfo info = entry.getValue();
				ExchangeOrder order = info.order;
				String clientId = order.getInvestorAccount();
				Integer sysnodeId = clientId2SysnodeId.get(clientId);
				if (sysnodeId == null) {
					continue;
				}
				/**
				 * 查询成交回报
				 */
				List<IDataset> list = ufInvokeService.searchDealReturn(sysnodeId, order);
				// 如果查询到的成交回报数量等于已有成交回报数量，则跳过该记录
				if (list.size() == info.execIds.size()) {
					continue;
				}
				/**
				 * 处理未处理过的成交回报
				 */
				// 遍历查询到的成交回报
				for (IDataset dataset : list) {
					// 处理未处理的成交回报
					String execId = dataset.getString("business_id");
					if (!info.execIds.contains(execId)) {
						int type = dataset.getInt("real_type");
						// 如果成交回报为撤单回报，处理撤单回报
						if (type == UFInvokeCallback.RealType_Cancel) {
							double cancelQty = -dataset.getDouble("business_amount");
							onCancelled(dataset, dataset.getInt("withdraw_no"), dataset.getInt("entrust_no"), dataset.getString("exchange_type"),
									cancelQty);
							info.execIds.add(dataset.getString("exec_id"));
							info.remainingQty -= cancelQty;
						} else {
							double filledQty = dataset.getDouble("business_amount");
							onPartiallyFilled(dataset, dataset.getInt("entrust_no"), dataset.getString("exchange_type"), execId, filledQty,
									dataset.getDouble("business_price"));
							info.execIds.add(execId);
							info.remainingQty -= filledQty;
						}
					}
				}
				// 如果订单剩余数量为0，则移除该订单
				if (DecimalUtil.equal(0, info.remainingQty)) {
					unFinishedEntrust.remove(entry.getKey());
				}
			}
		}
	}
}
