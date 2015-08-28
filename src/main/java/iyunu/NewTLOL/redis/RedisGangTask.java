package iyunu.NewTLOL.redis;

import iyunu.NewTLOL.manager.MiningManger;
import iyunu.NewTLOL.manager.gang.GangTaskManager;
import iyunu.NewTLOL.model.gang.GangTaskModel;
import iyunu.NewTLOL.util.json.JsonTool;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class RedisGangTask {

	private StringRedisTemplate redisTemplate;

	/**
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 保存帮派任务
	 */
	public void saveGangTask() {
		String key = RedisKey.getGangTask();
		redisTemplate.delete(key);

		SerializeWriter gangtask_Out = new SerializeWriter();
		JSONSerializer gangtask_Serializer = new JSONSerializer(gangtask_Out);
		ConcurrentMap<Long, GangTaskModel> gangTaskMap = GangTaskManager.instance().getGangTaskMap();
		gangtask_Serializer.write(gangTaskMap);
		redisTemplate.opsForValue().set(key, gangtask_Serializer.toString());
	}

	/**
	 * 获取帮派任务
	 * 
	 */
	public ConcurrentMap<Long, GangTaskModel> getGangTask() {
		String key = RedisKey.getGangTask();
		String gangTaskString = redisTemplate.opsForValue().get(key);
		ConcurrentMap<Long, GangTaskModel> gangTaskMap = JsonTool.decode(gangTaskString, new TypeReference<ConcurrentMap<Long, GangTaskModel>>() {
		});
		return gangTaskMap;
	}

	/**
	 * 保存矿区被抢提示
	 */
	public void saveMiningRobMap() {
		String key = RedisKey.getMiningRobMap();
		redisTemplate.delete(key);
		SerializeWriter miningRob_Out = new SerializeWriter();
		JSONSerializer miningRob_Serializer = new JSONSerializer(miningRob_Out);
		miningRob_Serializer.write(MiningManger.instance().getTipMap());
		redisTemplate.opsForValue().set(key, miningRob_Serializer.toString());
	}

	/**
	 * 获取矿区被抢提示
	 * 
	 */
	public ConcurrentHashMap<Long, List<String>> getMiningRobMap() {
		String key = RedisKey.getMiningRobMap();
		String miningString = redisTemplate.opsForValue().get(key);
		ConcurrentHashMap<Long, List<String>> miningRobMap = JsonTool.decode(miningString, new TypeReference<ConcurrentHashMap<Long, List<String>>>() {
		});
		return miningRobMap;
	}
}
