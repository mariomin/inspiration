package com.inspiration.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 说明:redisCluster 操作
 *
 */
public class OperatorUtilSucc extends Operator4Redis {

	private static Logger logger = LoggerFactory.getLogger(OperatorUtilSucc.class);

	private static OperatorUtilSucc instance = new OperatorUtilSucc();

	private final int DEFAULT_TIMEOUT = 3000;
	private final long DEFAULT_TRY = 200;

	private OperatorUtilSucc() {
		;
	}

	/**
	 * @return
	 * @description 单例初始化SharedJedis操作类
	 */
	public static OperatorUtilSucc getInstance() {
		return instance;
	}

	private JedisCluster jedisCluster = null;

	/**
	 * 说明:返回jedis集群
	 *
	 * @return
	 */
	public JedisCluster getJedisCluster() {
		return this.jedisCluster;
	}

	/**
	 * Note: 初始化redis连接池
	 *
	 * @param redisAddresses
	 *            jedis列表
	 * @param maxIdle
	 *            最大idle数量
	 * @param maxTotal
	 *            连接最大数量
	 * @param maxRedirections
	 *            最大重定向数量
	 */
	public synchronized void initOperator(String redisAddresses, int maxIdle, int maxTotal, int maxRedirections) {
		Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
		String[] addressArray = redisAddresses.split(",");
		for (int i = 0; i < addressArray.length; i++) {
			String[] everyHost = addressArray[i].split(":");
			jedisClusterNode.add(new HostAndPort(everyHost[0], Integer.parseInt(everyHost[1])));
		}
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		config.setTestWhileIdle(true);
		config.setTimeBetweenEvictionRunsMillis(30000L);
		config.setNumTestsPerEvictionRun(3);
		config.setMaxIdle(maxIdle);
		config.setMaxTotal(maxTotal);
		config.setMaxWaitMillis(DEFAULT_TIMEOUT);

		jedisCluster = new JedisCluster(jedisClusterNode, DEFAULT_TIMEOUT, maxRedirections, config);
	}

	public synchronized void shutdown() {
		if (jedisCluster != null) {
			try {
				jedisCluster.close();
				jedisCluster = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 说明:向channel推送数据
	 *
	 * @param channel
	 * @param data
	 * @return
	 */
	public boolean publish(byte[] channel, byte[] data) {
		boolean retValue = false;
		try {
			long result = this.jedisCluster.publish(channel, data);
			if (result >= 0) {
				retValue = true;
			}
		} catch (Exception e) {
			logger.warn("Publish data error", e);
		}
		return retValue;
	}

	/**
	 * 说明:向channel推送数据
	 *
	 * @param channel
	 * @param data
	 * @return
	 */
	public boolean publish(String channel, String data) {
		boolean retValue = false;
		try {
			long result = this.jedisCluster.publish(channel, data);
			if (result >= 0) {
				retValue = true;
			}
		} catch (Exception e) {
			logger.warn("Publish data error", e);
		}
		return retValue;
	}

	private void sleepAfterError() {
		try {
			Thread.sleep(DEFAULT_TRY);
		} catch (InterruptedException e1) {
			try {
				throw new Exception(e1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean set(byte[] key, byte[] value) {

		while (true) {
			try {
				jedisCluster.set(key, value);
				return true;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean incrByFloat(String key, double value) {

		while (true) {
			try {
				jedisCluster.incrByFloat(key, value);
				return true;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean setex(byte[] key, byte[] value, int seconds) {

		while (true) {
			try {
				jedisCluster.setex(key, seconds, value);
				return true;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public byte[] get(byte[] key) {

		while (true) {
			try {
				byte[] retValue = jedisCluster.get(key);
				if (this.isRedisPong(retValue)) {
					continue;
				}
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean del(byte[] key) {

		while (true) {
			try {
				long isSuccess = jedisCluster.del(key);

				if (isSuccess >= 0)
					return true;
				else
					return false;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean exists(byte[] key) {

		while (true) {
			try {
				boolean retValue = jedisCluster.exists(key);
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean set(String key, String value) {

		while (true) {
			try {
				jedisCluster.set(key, value);
				return true;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				if (e.getMessage().contains("WRONGTYPE")) {
					jedisCluster.del(key);
				} else {
					sleepAfterError();
				}
			} finally {
				;
			}
		}
	}

	@Override
	public boolean setex(String key, String value, int seconds) {

		while (true) {
			try {
				jedisCluster.setex(key, seconds, value);
				return true;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public String get(String key) {

		while (true) {
			try {
				String retValue = jedisCluster.get(key);
				if (this.isRedisPong(retValue)) {
					continue;
				}
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean del(String key) {

		while (true) {
			try {
				long isSuccess = jedisCluster.del(key);
				return isSuccess >= 0;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean exists(String key) {
		while (true) {
			try {
				boolean retValue = jedisCluster.exists(key);
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean expire(byte[] key, int seconds) {
		while (true) {
			try {
				long isSuccess = jedisCluster.expire(key, seconds);

				if (isSuccess >= 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean expire(String key, int seconds) {
		while (true) {
			try {
				long isSuccess = jedisCluster.expire(key, seconds);

				if (isSuccess >= 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public long getTTL(byte[] key) {

		while (true) {
			try {
				long ttl = jedisCluster.ttl(key);
				return ttl;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public String getType(byte[] key) {

		while (true) {
			try {
				String type = jedisCluster.type(key);
				if (this.isRedisPong(type)) {
					continue;
				}
				return type;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public long getTTL(String key) {

		while (true) {
			try {
				long ttl = jedisCluster.ttl(key);
				return ttl;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public String getType(String key) {

		while (true) {
			try {
				String type = jedisCluster.type(key);
				if (this.isRedisPong(type)) {
					continue;
				}
				return type;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean hexists(byte[] redisTopic, byte[] key) {

		while (true) {
			try {
				boolean retValue = jedisCluster.hexists(redisTopic, key);
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public Map<byte[], byte[]> hgetAll(byte[] redisTopic) {

		while (true) {
			try {
				Map<byte[], byte[]> retValue = jedisCluster.hgetAll(redisTopic);
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public byte[] hget(byte[] redisTopic, byte[] key) {

		while (true) {
			try {
				byte[] retValue = jedisCluster.hget(redisTopic, key);
				if (this.isRedisPong(retValue)) {
					continue;
				}
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean hdel(byte[] redisTopic, byte[] key) {

		while (true) {
			try {
				long isSuccess = jedisCluster.hdel(redisTopic, key);

				if (isSuccess >= 0)
					return true;
				else
					return false;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean hset(byte[] redisTopic, byte[] key, byte[] value) {

		while (true) {
			try {
				long isSuccess = jedisCluster.hset(redisTopic, key, value);

				if (isSuccess >= 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean hsetnx(String redisTopic, String key, String value) {
		while (true) {

			try {

				long isSuccess = jedisCluster.hsetnx(redisTopic, key, value);

				if (isSuccess > 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				if (e.getMessage().contains("WRONGTYPE")) {
					jedisCluster.del(redisTopic);
				} else {
					sleepAfterError();
				}
			} finally {
				;
			}
		}
	}

	@Override
	public boolean hexists(String redisTopic, String key) {

		while (true) {
			try {
				boolean retValue = jedisCluster.hexists(redisTopic, key);
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public Map<String, String> hgetAll(String redisTopic) {

		while (true) {

			try {
				Map<String, String> retValue = jedisCluster.hgetAll(redisTopic);
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}

	}

	@Override
	public String hget(String redisTopic, String key) {

		while (true) {

			try {
				String retValue = jedisCluster.hget(redisTopic, key);
				if (this.isRedisPong(retValue)) {
					continue;
				}
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean hdel(String redisTopic, String key) {

		while (true) {

			try {
				long isSuccess = jedisCluster.hdel(redisTopic, key);

				if (isSuccess >= 0)
					return true;
				else
					return false;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	@Override
	public boolean hset(String redisTopic, String key, String value) {

		while (true) {

			try {

				long isSuccess = jedisCluster.hset(redisTopic, key, value);

				if (isSuccess >= 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				if (e.getMessage().contains("WRONGTYPE")) {
					jedisCluster.del(redisTopic);
				} else {
					sleepAfterError();
				}
			} finally {
				;
			}
		}
	}

	public boolean zadd(byte[] key, double score, byte[] member) {

		while (true) {

			try {

				long isSuccess = jedisCluster.zadd(key, score, member);
				if (isSuccess >= 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	public boolean zadd(byte[] key, Map<byte[], Double> scoreMembers) {

		while (true) {

			try {
				long isSuccess = jedisCluster.zadd(key, scoreMembers);

				if (isSuccess >= 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
	}

	public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {

		Set<byte[]> sets = null;
		while (true) {

			try {
				sets = jedisCluster.zrangeByScore(key, min, max);
				if (sets != null) {
					break;
				}
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				sleepAfterError();
			} finally {
				;
			}
		}
		return sets;
	}

	@Override
	public long hincrBy(String redisTopic, String key, long addValue) {

		while (true) {
			try {
				return jedisCluster.hincrBy(redisTopic, key, addValue);
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
	}
}
