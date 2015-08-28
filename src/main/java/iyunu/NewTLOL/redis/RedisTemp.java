package iyunu.NewTLOL.redis;

import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.Translate;

import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

public class RedisTemp {

	private StringRedisTemplate redisTemplate;
	private RedisAtomicLong counter;

	/**
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * @param counter
	 *            the counter to set
	 */
	public void setCounter(RedisAtomicLong counter) {
		this.counter = counter;
	}

	public long getId() {
		return counter.incrementAndGet();
	}

	public void set() {
		redisTemplate.opsForValue().set("testValue", "temp");

	}

	public String get() {
		return redisTemplate.opsForValue().get("testValue");
	}

	public void addList() {
		redisTemplate.opsForList().leftPush("testList", "temp1");
		redisTemplate.opsForList().leftPush("testList", "temp2");

	}

	public List<String> getList() {

		return redisTemplate.opsForList().range("testList", 0, -1);
	}

	public Double getCheckInfo(int userId) {
		String key = "totleSession";
		Set<TypedTuple<String>> set = redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
		for (TypedTuple<String> typedTuple : set) {
			if (Translate.stringToInt(typedTuple.getValue()) == userId) {
				return typedTuple.getScore();
			}
		}
		return null;
	}

	public void saveCheckInfo(Role role) {
		String key = "totleSession";
		redisTemplate.opsForZSet().add(key, String.valueOf(role.getUserId()), role.getServerId());
	}

}
