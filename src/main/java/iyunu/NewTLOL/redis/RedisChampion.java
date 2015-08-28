package iyunu.NewTLOL.redis;

import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.util.Translate;

import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisChampion {

	private StringRedisTemplate redisTemplate;

	/**
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 保存帮战冠军
	 */
	public void saveChampionId() {
		String key = RedisKey.getChampionId();
		redisTemplate.opsForValue().set(key, String.valueOf(GangManager.championId));
	}

	/**
	 * 获取帮派冠军ID
	 * 
	 */
	public int getChampionId() {
		String key = RedisKey.getChampionId();
		return Translate.stringToInt(redisTemplate.opsForValue().get(key));
	}

}
