package iyunu.NewTLOL.redis;

import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.gang.FightApplyInfo;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.json.JsonTool;

import java.util.HashMap;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class RedisGangFight {

	private StringRedisTemplate redisTemplate;

	/**
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 保存帮战报名编号
	 * 
	 * @param gangId
	 *            帮派编号
	 */
	public void saveApply() {
		// 报名列表
		String key = RedisKey.getGangFightApply();
		redisTemplate.delete(key);

		for (Long gangId : GangFightManager.报名列表) {
			redisTemplate.opsForList().rightPush(key, String.valueOf(gangId));
		}

		// 参赛列表
		String gangFightKey = RedisKey.getGangFight();
		redisTemplate.delete(gangFightKey);

		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.write(GangFightManager.参赛列表);
		redisTemplate.opsForValue().set(gangFightKey, serializer.toString());
	}

	/**
	 * 获取帮战报名列表
	 * 
	 */
	public void getApply() {
		// 参赛列表
		String gangFightKey = RedisKey.getGangFight();
		String v = redisTemplate.opsForValue().get(gangFightKey);
		HashMap<Long, FightApplyInfo> map = JsonTool.decode(v, new TypeReference<HashMap<Long, FightApplyInfo>>() {
		});
		if (map != null && !map.isEmpty()) {
			GangFightManager.参赛列表 = map;
		}

		for (FightApplyInfo fightApplyInfo : GangFightManager.参赛列表.values()) {
			GangFightManager.循环赛周积分排名.add(fightApplyInfo);
		}
		GangFightManager.周积分排序();

		// 报名列表
		String key = RedisKey.getGangFightApply();
		for (String string : redisTemplate.opsForList().range(key, 0, -1)) {
			long gangId = Translate.stringToLong(string);
			Gang gang = GangManager.instance().getGang(gangId);
			if (gang != null) {
				GangFightManager.报名(gang);
			}
		}
	}

}
