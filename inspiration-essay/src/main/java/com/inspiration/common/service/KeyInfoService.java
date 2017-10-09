package com.inspiration.common.service;

import java.sql.SQLException;

import com.inspiration.common.dao.SequenceDaoImp;
import com.inspiration.common.util.SpringContextUtil;

public class KeyInfoService {
	private long maxKey; // 当前Sequence载体的最大值
	private long minKey; // 当前Sequence载体的最小值
	private long nextKey; // 下一个Sequence值
	private int poolSize; // Sequence值缓存大小
	private String keyName; // Sequence的名称

	private SequenceDaoImp seqDao;

	public KeyInfoService(String keyName, int poolSize) {
		seqDao = SpringContextUtil.getBean("sequenceDaoImp");
		this.poolSize = poolSize;
		this.keyName = keyName;
		retrieveFromDB();
	}

	public String getKeyName() {
		return keyName;
	}

	public long getMaxKey() {
		return maxKey;
	}

	public long getMinKey() {
		return minKey;
	}

	public int getPoolSize() {
		return poolSize;
	}

	/**
	 * 获取下一个Sequence值
	 * 
	 * @return 下一个Sequence值
	 * @throws SQLException
	 */
	public synchronized long getNextKey() throws SQLException {
		if (nextKey > maxKey) {
			retrieveFromDB();
		}
		return nextKey++;
	}

	/**
	 * 执行Sequence表信息初始化和更新工作
	 * 
	 * @throws SQLException
	 */
	private void retrieveFromDB() {
		Long index = seqDao.queryValue(keyName);
		if (index != null) {
			maxKey = index + poolSize;
			minKey = maxKey - poolSize + 1;
			nextKey = minKey;
		} else {
			System.out.println("执行Sequence数据库初始化工作！");
			String init_sql = "INSERT INTO CSX_SEQUENCE_INFO(KEY_NAME,KEY_VALUE) VALUES('" + keyName + "', " + poolSize
					+ ")";
			seqDao.excuteSql(init_sql);
			maxKey = poolSize;
			minKey = maxKey - poolSize + 1;
			nextKey = minKey;
			return;
		}

		Object[] args = new Object[2];
		args[0] = poolSize;
		args[1] = keyName;
		seqDao.updateValue(args);
	}

}