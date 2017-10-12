package com.inspiration.redis;

import java.util.Map;

/**
 * 说明:Redis操作基础类
 *
 */
public abstract class Operator4Redis {
	protected final int maxTryTimes = 10;

	private final byte[] PONGByts = "PONG".getBytes();
	private final byte[] pongByts = "pong".getBytes();
	private final byte[] OKByts = "OK".getBytes();
	private final byte[] okByts = "ok".getBytes();

	/**
	 * 回应的消息是否是redis的系统应答
	 *
	 * @param redisRespond
	 * @return
	 */
	public boolean isRedisPong(String redisRespond) {
		if (redisRespond == null || redisRespond.length() < 1) {
			return false;
		}
		String message = redisRespond.toLowerCase();
		if (message.equals("pong") || message.equals("ok")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 回应的消息是否是redis的系统应答
	 *
	 * @param redisRespond
	 * @return
	 */
	public boolean isRedisPong(byte[] redisRespond) {
		if (redisRespond == null || redisRespond.length < 1) {
			return false;
		}
		if (redisRespond.equals(PONGByts) || redisRespond.equals(pongByts) || redisRespond.equals(OKByts)
				|| redisRespond.equals(okByts)) {
			return true;
		} else {
			return false;
		}
	}

	public abstract boolean set(byte[] key, byte[] value);

	public abstract boolean setex(byte[] key, byte[] value, int seconds);

	public abstract byte[] get(byte[] key);

	public abstract boolean del(byte[] key);

	public abstract boolean exists(byte[] key);

	public abstract boolean set(String key, String value);

	public abstract boolean setex(String key, String value, int seconds);

	public abstract String get(String key);

	public abstract boolean del(String key);

	public abstract boolean exists(String key);

	public abstract boolean expire(byte[] key, int seconds);

	public abstract boolean expire(String key, int seconds);

	public abstract long getTTL(byte[] key);

	public abstract String getType(byte[] key);

	public abstract long getTTL(String key);

	public abstract String getType(String key);

	public abstract boolean hexists(byte[] redisTopic, byte[] key);

	public abstract Map<byte[], byte[]> hgetAll(byte[] redisTopic);

	public abstract byte[] hget(byte[] redisTopic, byte[] key);

	public abstract boolean hdel(byte[] redisTopic, byte[] key);

	public abstract boolean hset(byte[] redisTopic, byte[] key, byte[] value);

	public abstract boolean hsetnx(String redisTopic, String key, String value);

	public abstract boolean hexists(String redisTopic, String key);

	public abstract Map<String, String> hgetAll(String redisTopic);

	public abstract String hget(String redisTopic, String key);

	public abstract boolean hdel(String redisTopic, String key);

	public abstract boolean hset(String redisTopic, String key, String value);

	public abstract boolean incrByFloat(String key, double value);

	public abstract long hincrBy(String redisTopic, String key, long addValue);
}
