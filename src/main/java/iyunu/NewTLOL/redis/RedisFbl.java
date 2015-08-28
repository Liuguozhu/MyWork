package iyunu.NewTLOL.redis;

import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.model.activity.fbl.ActivityFblInfo;
import iyunu.NewTLOL.util.json.JsonTool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class RedisFbl {

	private StringRedisTemplate redisTemplate;

	/**
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 保存发布令
	 * 
	 */
	public void save() {
		String key = RedisKey.getFbl();
		redisTemplate.delete(key);

		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.write(ActivityManager.activityFblInfos);
		redisTemplate.opsForValue().set(key, serializer.toString());
	}

	/**
	 * 获取发布令
	 * 
	 */
	public void query() {
		String key = RedisKey.getFbl();
		String v = redisTemplate.opsForValue().get(key);
		ConcurrentHashMap<Long, ActivityFblInfo> map = JsonTool.decode(v, new TypeReference<ConcurrentHashMap<Long, ActivityFblInfo>>() {
		});
		if (map != null && !map.isEmpty()) {

			ActivityManager.activityFblInfos = map;
			Iterator<Entry<Long, ActivityFblInfo>> it = ActivityManager.activityFblInfos.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Long, ActivityFblInfo> entry = it.next();
				ActivityManager.activityFblIds.add(entry.getKey());

				if (ActivityManager.activityFblMy.containsKey(entry.getValue().getRoleId())) {
					ActivityManager.activityFblMy.get(entry.getValue().getRoleId()).add(entry.getValue());
				} else {
					ArrayList<ActivityFblInfo> list = new ArrayList<ActivityFblInfo>();
					list.add(entry.getValue());
					ActivityManager.activityFblMy.put(entry.getValue().getRoleId(), list);
				}

				if (entry.getValue().getState() == 1) {
					ActivityManager.activityFblTimer.put(entry.getKey(), entry.getValue());
				}
			}
		}

	}

}
