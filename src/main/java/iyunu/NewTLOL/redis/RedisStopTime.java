package iyunu.NewTLOL.redis;

import iyunu.NewTLOL.util.Translate;

import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisStopTime {

	private StringRedisTemplate redisTemplate;

	/**
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 保存停服时间
	 */
	public void saveStopTime() {
		String key = RedisKey.getStopTime();
		redisTemplate.delete(key);
		redisTemplate.opsForValue().set(key, "" + System.currentTimeMillis());
	}

	/**
	 * 获取停服时间
	 * 
	 */
	public long getStopTime() {
		String key = RedisKey.getStopTime();
		String stopTime = redisTemplate.opsForValue().get(key);
		return Translate.stringToLong(stopTime);
	}

}
