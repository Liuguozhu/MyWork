package iyunu.NewTLOL.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisLogon {

	private StringRedisTemplate redisTemplate;

	/**
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 验证登录信息
	 * 
	 * @param userId
	 *            用户编号
	 * @return 验证成功
	 */
	public boolean checkLogonInfo(String userId) {
		String key = RedisKey.getSession();
		Double serverId = redisTemplate.opsForZSet().score(key, userId);
		if (serverId == null || serverId != -1) {
			return false;
		}
		return true;
	}

	/**
	 * 保存登录信息
	 * 
	 * @param userId
	 *            用户编号
	 */
	public void saveLogonInfo(String userId, int serverId) {
		redisTemplate.opsForZSet().add(RedisKey.getSession(), userId, serverId);
	}

}
