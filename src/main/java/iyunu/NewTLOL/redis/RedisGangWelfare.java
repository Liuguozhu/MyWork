package iyunu.NewTLOL.redis;

import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.util.json.JsonTool;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class RedisGangWelfare {

	private StringRedisTemplate redisTemplate;

	/**
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 删除已领取帮派福利的ID
	 */
	public void deleteGangWelFare() {
		String key = RedisKey.getGangWelfare();
		redisTemplate.delete(key);
	}

	/**
	 * 保存已领取帮派福利的ID
	 */
	public void saveGangWelFare() {
		String key = RedisKey.getGangWelfare();
		redisTemplate.delete(key);

		SerializeWriter fare_Out = new SerializeWriter();
		JSONSerializer fare_Serializer = new JSONSerializer(fare_Out);
		fare_Serializer.write(GangManager.instance().hadGetWelfare);
		redisTemplate.opsForValue().set(key, fare_Serializer.toString());
	}

	/**
	 * 获取已领取帮派的ID
	 * 
	 */
	public List<Long> getGangWelfare() {
		String key = RedisKey.getGangWelfare();
		String gangWelfareString = redisTemplate.opsForValue().get(key);
		List<Long> gangWelfare = JsonTool.decode(gangWelfareString, new TypeReference<ArrayList<Long>>() {
		});
		return gangWelfare;
	}
}
