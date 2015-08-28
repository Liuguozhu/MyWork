package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.json.MiningJson;
import iyunu.NewTLOL.message.MapMessage;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.EMonsterOnMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;
import iyunu.NewTLOL.model.mining.emoMap.EmoMapRes;
import iyunu.NewTLOL.util.Time;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 恶魔岛
 * 
 * @author fhy
 * 
 */
public class EmoMapManager {
	private static EmoMapManager instance = new EmoMapManager();

	/**
	 * 私有构造方法
	 */
	private EmoMapManager() {
	}

	public static EmoMapManager instance() {
		return instance;
	}

	public static final int MAX = 2;// 每天可进入的次数
	public static final int COIN = 10000;// 购买次数单价
	public List<MonsterOnMap> monsterIds = new ArrayList<>();
	public Set<MonsterOnMap> hasKillMonsterIds = new HashSet<>();
	public static boolean EMO1_STATE = false;
	public static long EMO1_START_TIME;
	public static long EMO1_END_TIME;
	private static int EMO1_START_TIME_H1 = 12;
	private static int EMO1_START_TIME_M1 = 30;
	private static int EMO1_END_TIME_H1 = 12;
	private static int EMO1_END_TIME_M1 = 50;
	// ==================================================================
	public static boolean EMO2_STATE = false;
	public static long EMO2_START_TIME;
	public static long EMO2_END_TIME;
	private static int EMO2_START_TIME_H1 = 19;
	private static int EMO2_START_TIME_M1 = 30;
	private static int EMO2_END_TIME_H1 = 19;
	private static int EMO2_END_TIME_M1 = 50;

	/**
	 * 恶魔岛1初始化
	 */
	public static void emo1Init() {
		long time = Time.getTodayTime(EMO1_END_TIME_H1, EMO1_END_TIME_M1, 00);
		long now = System.currentTimeMillis();
		if (now < time) {
			EMO1_START_TIME = Time.getTodayTime(EMO1_START_TIME_H1, EMO1_START_TIME_M1, 00);
			EMO1_END_TIME = time;
		} else {
			EMO1_START_TIME = Time.getNextTime(EMO1_START_TIME_H1, EMO1_START_TIME_M1, 00);
			EMO1_END_TIME = Time.getNextTime(EMO1_END_TIME_H1, EMO1_END_TIME_M1, 00);
		}
	}

	/**
	 * 恶魔岛2初始化
	 */
	public static void emo2Init() {
		long time = Time.getTodayTime(EMO2_END_TIME_H1, EMO2_END_TIME_M1, 00);
		long now = System.currentTimeMillis();
		if (now < time) {
			EMO2_START_TIME = Time.getTodayTime(EMO2_START_TIME_H1, EMO2_START_TIME_M1, 00);
			EMO2_END_TIME = time;
		} else {
			EMO2_START_TIME = Time.getNextTime(EMO2_START_TIME_H1, EMO2_START_TIME_M1, 00);
			EMO2_END_TIME = Time.getNextTime(EMO2_END_TIME_H1, EMO2_END_TIME_M1, 00);
		}
	}

	/**
	 * 删除地图上的怪
	 * 
	 * @param list
	 */
	public void deleteMonster(List<MonsterOnMap> list) {
		for (int i = 0; i < list.size(); i++) {
			if (MapManager.instance().getMapById(list.get(i).getMapId()).getMonsters().containsKey(list.get(i).getUid())) {
				MapManager.instance().getMapById(list.get(i).getMapId()).removeMonster(list.get(i).getUid());
			}
		}
	}

	public void fresh1(int type) {
		// List<MonsterOnMap> mons = new ArrayList<MonsterOnMap>();
		// 第一次刷
		if (type == 1) {
			List<MonsterOnMap> mons = new ArrayList<MonsterOnMap>();
			int mapId = MiningJson.instance().getEmoMapInfoRes().getEmo1Map();
			BaseMap baseMap = MapManager.instance().getMapById(mapId);
			for (EmoMapRes b : MiningJson.instance().getEmoMap1().values()) {
				MonsterOnMap monsterOnMap = new MonsterOnMap(mapId, b.getX(), b.getY(), b.getMonsterGroupId(), EMonsterOnMap.emo);
				monsterIds.add(monsterOnMap);
				mons.add(monsterOnMap);
				baseMap.addMonsterNoSend(monsterOnMap);
			}
			if (baseMap != null) {
				MapMessage.sendMonsterOnMap(baseMap, mons);
			}
			// 其它刷
		} else {
			BaseMap baseMap = null;
			Iterator<MonsterOnMap> it = hasKillMonsterIds.iterator();
			while (it.hasNext()) {
				MonsterOnMap m = it.next();
				baseMap = MapManager.instance().getMapById(m.getMapId());
				baseMap.addMonster(m);
				it.remove();
			}
		}
	}

	public void fresh2(int type) {
		// List<MonsterOnMap> mons = new ArrayList<MonsterOnMap>();
		// 第一次刷
		if (type == 1) {
			List<MonsterOnMap> mons = new ArrayList<MonsterOnMap>();
			int mapId = MiningJson.instance().getEmoMapInfoRes().getEmo2Map();
			BaseMap baseMap = MapManager.instance().getMapById(mapId);
			for (EmoMapRes b : MiningJson.instance().getEmoMap2().values()) {
				MonsterOnMap monsterOnMap = new MonsterOnMap(mapId, b.getX(), b.getY(), b.getMonsterGroupId(), EMonsterOnMap.emo);
				monsterIds.add(monsterOnMap);
				mons.add(monsterOnMap);
				baseMap.addMonsterNoSend(monsterOnMap);
			}
			if (baseMap != null) {
				MapMessage.sendMonsterOnMap(baseMap, mons);
			}
			// 其它刷
		} else {
			BaseMap baseMap = null;
			Iterator<MonsterOnMap> it = hasKillMonsterIds.iterator();
			while (it.hasNext()) {
				MonsterOnMap m = it.next();
				baseMap = MapManager.instance().getMapById(m.getMapId());
				baseMap.addMonster(m);
				it.remove();
			}
		}
	}

	/**
	 * @return the monsterIds
	 */
	public List<MonsterOnMap> getMonsterIds() {
		return monsterIds;
	}

	/**
	 * @param monsterIds
	 *            the monsterIds to set
	 */
	public void setMonsterIds(List<MonsterOnMap> monsterIds) {
		this.monsterIds = monsterIds;
	}

	/**
	 * @return the hasKillMonsterIds
	 */
	public Set<MonsterOnMap> getHasKillMonsterIds() {
		return hasKillMonsterIds;
	}

	/**
	 * @param hasKillMonsterIds
	 *            the hasKillMonsterIds to set
	 */
	public void setHasKillMonsterIds(Set<MonsterOnMap> hasKillMonsterIds) {
		this.hasKillMonsterIds = hasKillMonsterIds;
	}

}
