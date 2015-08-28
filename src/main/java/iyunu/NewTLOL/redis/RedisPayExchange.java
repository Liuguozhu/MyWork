package iyunu.NewTLOL.redis;

import iyunu.NewTLOL.manager.PayExchangeManager;
import iyunu.NewTLOL.util.json.JsonTool;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class RedisPayExchange {

	private StringRedisTemplate redisTemplate;

	/**
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 删除积分榜
	 */
	public void delPayExchange() {
		String key = RedisKey.getPayExchange();
		redisTemplate.delete(key);
	}
	
	/**
	 * 保存积分榜
	 */
	public void savePayExchange() {
		String key = RedisKey.getPayExchange();
		redisTemplate.delete(key);

		SerializeWriter pay_Out = new SerializeWriter();
		JSONSerializer pay_Serializer = new JSONSerializer(pay_Out);
		pay_Serializer.write(PayExchangeManager.instance().getPayExchangeMap());
		redisTemplate.opsForValue().set(key, pay_Serializer.toString());
	}

	/**
	 * 获取积分榜
	 * 
	 */
	public Map<Long, Integer> getPayExchange() {
		String key = RedisKey.getPayExchange();
		String payString = redisTemplate.opsForValue().get(key);
		Map<Long, Integer> pay = JsonTool.decode(payString, new TypeReference<HashMap<Long, Integer>>() {
		});
		return pay;
	}
}
