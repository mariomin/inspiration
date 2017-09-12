package com.inspiration.commonUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.commons.io.FileUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;

public class KafkaUtil {
	private static final Logger log = LoggerFactory.getLogger(KafkaUtil.class);
	private static ZkClient zkClient;
	private static ZkConnection zkConnection;
	private static ZkUtils zkUtils;
	
	private static ZkUtils getZkUtils(String address) {
		if (zkUtils == null) {
			zkClient = new ZkClient(address, 10000, 10000, ZKStringSerializer$.MODULE$);
			zkConnection = new ZkConnection(address);
			zkUtils = new ZkUtils(zkClient, zkConnection, false);
		}
		return zkUtils;
	}
	
	public static void deleteLog(String dir) throws FileNotFoundException, IOException {
		File file = new File(dir);
		FileUtils.deleteDirectory(file);
	}
	
	public static void asyncStartupZookeeper(String config) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				ZooKeeperServerMain.main(new String[] { config });
			}
		}).start();
		log.info("asynchrously startup zookeeper");
	}
	
	public static void asyncStartupKafka(String config) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				kafka.Kafka.main(new String[] { config });
			}
		}).start();
		log.info("asynchrously startup kafka");
	}
	
	public static void createTopicIfAbsent(String subject, String address, int partitions, int replicationFactor) {
		ZkUtils zkUtils = getZkUtils(address);
		if (!AdminUtils.topicExists(zkUtils, subject)) {
			AdminUtils.createTopic(zkUtils, subject, partitions, replicationFactor, new Properties());
			log.info("create topic: {}", subject);
		}
	}
	
	public static KafkaConsumer<String, byte[]> createKafkaConsumer(File setting, String groupId) {
		Properties props = new Properties();
		try {
			props.load(new FileReader(setting));
			props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
			props.setProperty("group.id", groupId);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return new KafkaConsumer<String, byte[]>(props);
	}
	
	public static KafkaProducer<String, byte[]> createKafkaProducer(File setting) {
		Properties props = new Properties();
		try {
			props.load(new FileReader(setting));
			props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			props.setProperty("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return new KafkaProducer<String, byte[]>(props);
	}
}
