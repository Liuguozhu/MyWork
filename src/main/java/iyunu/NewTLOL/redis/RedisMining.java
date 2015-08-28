package iyunu.NewTLOL.redis;

import iyunu.NewTLOL.manager.MiningManger;
import iyunu.NewTLOL.model.mining.Mining;
import iyunu.NewTLOL.model.mining.MiningRole;
import iyunu.NewTLOL.util.json.JsonTool;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class RedisMining {

	private StringRedisTemplate redisTemplate;

	/**
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 保存矿区
	 */
	public void saveMiningMap() {
		String key = RedisKey.getMiningMap();
		redisTemplate.delete(key);

		SerializeWriter mining_Out = new SerializeWriter();
		JSONSerializer mining_Serializer = new JSONSerializer(mining_Out);
		TreeMap<Integer, Mining> miningMap = MiningManger.instance().getMiningMap();
		TreeMap<Integer, MiningRole> miningRoleMap = new TreeMap<>();
		Iterator<Entry<Integer, Mining>> it = miningMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Mining> entry = it.next();
			MiningRole miningRole = new MiningRole();
			Mining mining = entry.getValue();
			miningRole.setFlag(mining.getFlag());
			miningRole.setIndex(mining.getIndex());
			if (mining.getRole() == null) {
				miningRole.setRoleId(0);
			} else {
				miningRole.setRoleId(mining.getRole().getId());
			}
			miningRole.setStartTime(mining.getStartTime());
			miningRoleMap.put(entry.getKey(), miningRole);
		}

		mining_Serializer.write(miningRoleMap);
		redisTemplate.opsForValue().set(key, mining_Serializer.toString());
	}

	/**
	 * 获取矿区
	 * 
	 */
	public TreeMap<Integer, MiningRole> getMiningMap() {
		String key = RedisKey.getMiningMap();
		String miningString = redisTemplate.opsForValue().get(key);
		TreeMap<Integer, MiningRole> miningMap = JsonTool.decode(miningString, new TypeReference<TreeMap<Integer, MiningRole>>() {
		});
		return miningMap;
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
