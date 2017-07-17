package com.huatai.platform;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huatai.common.IPlugin;
import com.huatai.common.SystemInfo;
import com.huatai.common.event.AsyncTimerEvent;
import com.huatai.common.event.IAsyncEventManager;
import com.huatai.common.event.platform.PluginReadyEvent;
import com.huatai.common.event.platform.ServerShutdownEvent;
import com.huatai.common.util.IdGenerator;
import com.huatai.common.util.TimeUtil;
import com.huatai.platform.event.AsyncEventProcessor;
import com.huatai.platform.schedule.ScheduleManager;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

public class Platform implements BeanNameAware {
	private static final Logger log = LoggerFactory.getLogger(Platform.class);
	private ReadyList readyList;
	private String name;
	private int pluginReportInterval = 10000;
	private int nodeReportInterval = 60000;
	
	@Autowired
	private SystemInfo systemInfo;
	
	@Autowired
	private IAsyncEventManager eventManager;
	
	@Autowired
	private ScheduleManager scheduleManager;
	
	private int bufferSize = 1024 * 1024;
	private int defaultCheckPluginTimeout = 200;
	private List<IPlugin> plugins;
	private String inbox;
	private String shutdownTime;
	private final AsyncTimerEvent shutdownEvent = new AsyncTimerEvent();
	
	private final AsyncEventProcessor eventProcessor = new AsyncEventProcessor() {
		
		@Override
		public void subscribeToEvents() {
			subscribeToEvent(AsyncTimerEvent.class, null);
			subscribeToEvent(PluginReadyEvent.class, null);
		}
		
		@Override
		public IAsyncEventManager getEventManager() {
			return eventManager;
		}
		
		@Override
		public ProducerType getDisruptorProducerType() {
			return ProducerType.MULTI;
		}
		
		@Override
		public int getBufferSize() {
			return bufferSize;
		}
		
		@Override
		public int getNormalPriorityProcessorThreads() {
			return 0;
		}
		
		@Override
		public int getHighPriorityEventProcessorThreads() {
			return 1;
		}
		
		@Override
		public WaitStrategy getWaitStrategy() {
			return new BlockingWaitStrategy();
		}
		
	};
	
	public void processPluginReadyEvent(PluginReadyEvent event) {
		log.info("{} is ready: {}", event.getName(), event.isReady());
		readyList.update(event.getName(), event.isReady());
	}
	
	private void registerShutdownTime() throws ParseException {
		if (shutdownTime == null) return;
		Date endTime = TimeUtil.parseTime("HH:mm:ss", shutdownTime);
		scheduleManager.scheduleTimerEvent(endTime, eventProcessor, shutdownEvent);
	}
	
	public void init() throws Exception {
		IdGenerator.getInstance().setPrefix(systemInfo.getId() + "-");
		
		// create node.info subscriber and publisher
		log.info("SystemInfo: {}", systemInfo);
		
		inbox = systemInfo.getInbox();
		System.setProperty("node.inbox", inbox);
		IdGenerator.getInstance().setSystemId(inbox);
		
		readyList = new ReadyList(plugins);
		
		eventProcessor.setHandler(this);
		eventProcessor.init();
		if (eventProcessor.getThread() != null) {
			eventProcessor.getThread().setName(name);
		}
		
		if (plugins != null) {
			for (IPlugin plugin : plugins) {
				plugin.init();
				plugin.recover();
			}
		}
		
		// 等待系统插件启动成功
		readyList.checkPluginsReady(defaultCheckPluginTimeout);
		log.info("Server is ready: true");
		
		registerShutdownTime();
	}
	
	public void uninit() {
		log.info("uninitialising");
		if (plugins != null) {
			for (int i = plugins.size(); i > 0; i--) {
				plugins.get(i - 1).uninit();
			}
		}
		
		if (eventProcessor != null) {
			eventProcessor.uninit();
		}
	}
	
	public void shutdown() {
		log.debug("");
		log.debug(">>>> CLOSING DOWN SERVER, PLEASE WAIT FOR ALL COMPONENTS CLEAN SHUTDOWN <<<");
		log.debug("");
		
		eventManager.sendEvent(new ServerShutdownEvent());
		try { // give it 2 seconds for an opportunity of clean shutdown
			Thread.sleep(2000);
		} catch (InterruptedException e) {}
		uninit();
	}
	
	public void setPlugins(List<IPlugin> plugins) {
		this.plugins = plugins;
	}
	
	public void setShutdownTime(String shutdownTime) {
		this.shutdownTime = shutdownTime;
	}
	
	@Override
	public void setBeanName(String name) {
		this.name = name;
	}
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	public void setDefaultCheckPluginTimeout(int defaultCheckPluginTimeout) {
		this.defaultCheckPluginTimeout = defaultCheckPluginTimeout;
	}
	
	public int getPluginReportInterval() {
		return pluginReportInterval;
	}
	
	public void setPluginReportInterval(int pluginReportInterval) {
		this.pluginReportInterval = pluginReportInterval;
	}
	
	public int getNodeReportInterval() {
		return nodeReportInterval;
	}
	
	public void setNodeReportInterval(int nodeReportInterval) {
		this.nodeReportInterval = nodeReportInterval;
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			log.error("Missing configuration, application will quit.");
			System.exit(-1);
		}
		
		AbstractApplicationContext context = null;
		Platform platform = null;
		try {
			String config = args[0];
			context = new ClassPathXmlApplicationContext(config);
			platform = (Platform) context.getBean("platform");
			platform.init();
			
			context.start();// 触发ContextStartedEvent事件
			synchronized (platform) {
				platform.wait();
			}
		}
		finally {
			if (null != platform) {
				platform.uninit();
			}
			if (context != null) {
				context.close();
			}
		}
	}
	
}
