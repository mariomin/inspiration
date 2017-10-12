package com.inspiration.redis;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * 说明:redisCluster 操作
 *
 */
public class Operator4Cluster extends Operator4Redis {

	private static Logger logger = LoggerFactory.getLogger(Operator4Cluster.class);

	private static Operator4Cluster instance = new Operator4Cluster();

	private final int DEFAULT_TIMEOUT = 3000;

	private Operator4Cluster() {
		;
	}

	/**
	 * @return
	 * @description 单例初始化SharedJedis操作类
	 */
	public static Operator4Cluster getInstance() {
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

	@Override
	public boolean set(byte[] key, byte[] value) {

		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				jedisCluster.set(key, value);
				return true;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public boolean incrByFloat(String key, double value) {

		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				jedisCluster.incrByFloat(key, value);
				return true;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public boolean setex(byte[] key, byte[] value, int seconds) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				jedisCluster.setex(key, seconds, value);
				return true;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public byte[] get(byte[] key) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				byte[] retValue = jedisCluster.get(key);
				if (this.isRedisPong(retValue)) {
					continue;
				}
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return null;
	}

	@Override
	public boolean del(byte[] key) {

		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				long isSuccess = jedisCluster.del(key);

				if (isSuccess >= 0)
					return true;
				else
					return false;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public boolean exists(byte[] key) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				boolean retValue = jedisCluster.exists(key);
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public boolean set(String key, String value) {

		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				jedisCluster.set(key, value);
				return true;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
				if (e.getMessage().contains("WRONGTYPE")) {
					jedisCluster.del(key);
				}
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public boolean setex(String key, String value, int seconds) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				jedisCluster.setex(key, seconds, value);
				return true;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public String get(String key) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				String retValue = jedisCluster.get(key);
				if (this.isRedisPong(retValue)) {
					continue;
				}
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return null;
	}

	@Override
	public boolean del(String key) {
		int tryTimes = 0;
		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				long isSuccess = jedisCluster.del(key);

				if (isSuccess >= 0)
					return true;
				else
					return false;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public boolean exists(String key) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				boolean retValue = jedisCluster.exists(key);
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public boolean expire(byte[] key, int seconds) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				long isSuccess = jedisCluster.expire(key, seconds);

				if (isSuccess >= 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public boolean expire(String key, int seconds) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				long isSuccess = jedisCluster.expire(key, seconds);

				if (isSuccess >= 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public long getTTL(byte[] key) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				long ttl = jedisCluster.ttl(key);
				return ttl;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return Long.MIN_VALUE;
	}

	@Override
	public String getType(byte[] key) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				String type = jedisCluster.type(key);
				if (this.isRedisPong(type)) {
					continue;
				}
				return type;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return null;
	}

	@Override
	public long getTTL(String key) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				long ttl = jedisCluster.ttl(key);
				return ttl;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return Long.MIN_VALUE;
	}

	@Override
	public String getType(String key) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				String type = jedisCluster.type(key);
				if (this.isRedisPong(type)) {
					continue;
				}
				return type;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return null;
	}

	@Override
	public boolean hexists(byte[] redisTopic, byte[] key) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				boolean retValue = jedisCluster.hexists(redisTopic, key);
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public Map<byte[], byte[]> hgetAll(byte[] redisTopic) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				Map<byte[], byte[]> retValue = jedisCluster.hgetAll(redisTopic);
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return null;
	}

	@Override
	public byte[] hget(byte[] redisTopic, byte[] key) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				byte[] retValue = jedisCluster.hget(redisTopic, key);
				if (this.isRedisPong(retValue)) {
					continue;
				}
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return null;
	}

	@Override
	public boolean hdel(byte[] redisTopic, byte[] key) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				long isSuccess = jedisCluster.hdel(redisTopic, key);

				if (isSuccess >= 0)
					return true;
				else
					return false;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public boolean hset(byte[] redisTopic, byte[] key, byte[] value) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				long isSuccess = jedisCluster.hset(redisTopic, key, value);

				if (isSuccess >= 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public boolean hexists(String redisTopic, String key) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				boolean retValue = jedisCluster.hexists(redisTopic, key);
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public Map<String, String> hgetAll(String redisTopic) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				Map<String, String> retValue = jedisCluster.hgetAll(redisTopic);
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return null;
	}

	@Override
	public String hget(String redisTopic, String key) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				String retValue = jedisCluster.hget(redisTopic, key);
				if (this.isRedisPong(retValue)) {
					continue;
				}
				return retValue;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return null;
	}

	@Override
	public boolean hdel(String redisTopic, String key) {
		int tryTimes = 0;
		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				long isSuccess = jedisCluster.hdel(redisTopic, key);

				if (isSuccess >= 0)
					return true;
				else
					return false;
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public boolean hset(String redisTopic, String key, String value) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
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
				}
			} finally {
				;
			}
		}
		return false;
	}

	public boolean zadd(byte[] key, double score, byte[] member) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {

				long isSuccess = jedisCluster.zadd(key, score, member);
				if (isSuccess >= 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	public boolean zadd(byte[] key, Map<byte[], Double> scoreMembers) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				long isSuccess = jedisCluster.zadd(key, scoreMembers);

				if (isSuccess >= 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return false;
	}

	public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
		int tryTimes = 0;
		Set<byte[]> sets = null;
		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				sets = jedisCluster.zrangeByScore(key, min, max);
				if (sets != null) {
					break;
				}
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return sets;
	}

	public boolean hsetnx(String redisTopic, String key, String value) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
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
				}
			} finally {
				;
			}
		}
		return false;
	}

	@Override
	public long hincrBy(String redisTopic, String key, long addValue) {
		int tryTimes = 0;

		while (tryTimes < this.maxTryTimes) {
			tryTimes = tryTimes + 1;
			try {
				return jedisCluster.hincrBy(redisTopic, key, addValue);
			} catch (Exception e) {
				logger.error("Operator Redis Error:" + e);
			} finally {
				;
			}
		}
		return Long.MIN_VALUE;
	}
}
