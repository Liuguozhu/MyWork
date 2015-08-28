package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.monster.instance.Monster;
import iyunu.NewTLOL.model.monster.instance.MonsterBox;
import iyunu.NewTLOL.model.monster.instance.MonsterGroup;
import iyunu.NewTLOL.model.monster.res.MonsterBoxRes;
import iyunu.NewTLOL.model.monster.res.MonsterGroupRes;
import iyunu.NewTLOL.model.monster.res.MonsterInvasionRes;
import iyunu.NewTLOL.model.monster.res.MonsterRes;
import iyunu.NewTLOL.server.battle.BattleServer;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ArrayListMultimap;

public final class MonsterJson {

	/**
	 * 私有构造方法
	 */
	private MonsterJson() {

	}

	private static MonsterJson instance = new MonsterJson();
	private static final String MONSTER = "json/" + ServerManager.SERVER_RES + "/Monster.json.txt";
	private static final String MONSTER_ACTIVITY = "json/" + ServerManager.SERVER_RES + "/MonsterActivity.json.txt";
	private static final String MONSTER_BOX = "json/" + ServerManager.SERVER_RES + "/MonsterBox.json.txt";
	private static final String MONSTER_GROUP = "json/" + ServerManager.SERVER_RES + "/MonsterGroup.json.txt";
	private static final String MONSTER_TRIALS = "json/" + ServerManager.SERVER_RES + "/MonsterTrials.json.txt";
	private static final String MONSTER_RAIDS = "json/" + ServerManager.SERVER_RES + "/MonsterRaids.json.txt";
	private static final String MONSTER_QIANCENGTA = "json/" + ServerManager.SERVER_RES + "/MonsterQiancengta.json.txt";
	private static final String MONSTER_INVASION = "json/" + ServerManager.SERVER_RES + "/MonsterInvasion.json.txt";

	private Map<Long, Monster> monsterMap = new HashMap<Long, Monster>();
	private Map<Integer, MonsterGroup> monsterGroupMap = new HashMap<Integer, MonsterGroup>();
	private Map<Integer, MonsterBox> monsterBoxMap = new HashMap<Integer, MonsterBox>();
	private List<Integer> skillBoxs = new ArrayList<Integer>(); // 猎技宝箱
	private ArrayListMultimap<Integer, MonsterInvasionRes> monsterInvasionMap = ArrayListMultimap.create();

	// private Map<Integer, ArrayList<MonsterInvasionRes>> monsterInvasionMap =
	// new HashMap<Integer, ArrayList<MonsterInvasionRes>>();

	/**
	 * 获取MonsterJson对象
	 * 
	 * @return MonsterJson对象
	 */
	public static MonsterJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		monsterMap.clear();
		monsterGroupMap.clear();
		monsterBoxMap.clear();
		skillBoxs.clear();
		monsterInvasionMap.clear();
	}

	/**
	 * 初始化物品模板
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<MonsterRes> monsterResList = JsonImporter.fileImporter(MONSTER, new TypeReference<List<MonsterRes>>() {
		});
		for (MonsterRes monsterRes : monsterResList) {
			monsterMap.put(monsterRes.getId(), monsterRes.toMonster());
		}

		List<MonsterRes> monsterActivityList = JsonImporter.fileImporter(MONSTER_ACTIVITY, new TypeReference<List<MonsterRes>>() {
		});
		for (MonsterRes monsterRes : monsterActivityList) {
			monsterMap.put(monsterRes.getId(), monsterRes.toMonster());
		}

		List<MonsterBoxRes> monsterBoxList = JsonImporter.fileImporter(MONSTER_BOX, new TypeReference<List<MonsterBoxRes>>() {
		});
		for (MonsterBoxRes monsterBoxRes : monsterBoxList) {
			monsterBoxMap.put(monsterBoxRes.getId(), monsterBoxRes.toMonsterBox());

			if (monsterBoxRes.getBoxType() == 1) {
				skillBoxs.add(monsterBoxRes.getId());
			}
		}

		List<MonsterRes> trialsMonsterResList = JsonImporter.fileImporter(MONSTER_TRIALS, new TypeReference<List<MonsterRes>>() {
		});
		for (MonsterRes monsterRes : trialsMonsterResList) {
			monsterMap.put(monsterRes.getId(), monsterRes.toMonster());
		}

		List<MonsterRes> raidsMonsterResList = JsonImporter.fileImporter(MONSTER_RAIDS, new TypeReference<List<MonsterRes>>() {
		});
		for (MonsterRes monsterRes : raidsMonsterResList) {
			monsterMap.put(monsterRes.getId(), monsterRes.toMonster());
		}

		List<MonsterRes> monsterQiancengtaResList = JsonImporter.fileImporter(MONSTER_QIANCENGTA, new TypeReference<List<MonsterRes>>() {
		});
		for (MonsterRes monsterRes : monsterQiancengtaResList) {
			monsterMap.put(monsterRes.getId(), monsterRes.toMonster());
		}

		List<MonsterGroupRes> monsterGroupResList = JsonImporter.fileImporter(MONSTER_GROUP, new TypeReference<List<MonsterGroupRes>>() {
		});
		for (MonsterGroupRes monsterGroupRes : monsterGroupResList) {
			monsterGroupMap.put(monsterGroupRes.getId(), monsterGroupRes.toMonsterGroupRes());
		}

		List<MonsterInvasionRes> monsterInvasionResList = JsonImporter.fileImporter(MONSTER_INVASION, new TypeReference<List<MonsterInvasionRes>>() {
		});
		for (MonsterInvasionRes monsterInvasionRes : monsterInvasionResList) {

			monsterInvasionMap.put(monsterInvasionRes.getGangLevel(), monsterInvasionRes);
			// if
			// (monsterInvasionMap.containsKey(monsterInvasionRes.getGangLevel()))
			// {
			// monsterInvasionMap.get(monsterInvasionRes.getGangLevel()).add(monsterInvasionRes);
			// } else {
			// ArrayList<MonsterInvasionRes> list = new
			// ArrayList<MonsterInvasionRes>();
			// list.add(monsterInvasionRes);
			// monsterInvasionMap.put(monsterInvasionRes.getGangLevel(), list);
			// }
		}

		long end = System.currentTimeMillis();
		System.out.println("怪物脚本加载耗时：" + (end - start));
	}

	/**
	 * 检查怪物编号是否存在
	 * 
	 * @param monsterId
	 *            怪物编号
	 * @return 怪物编号存在
	 */
	public boolean checkMonsterId(long monsterId) {
		return monsterMap.containsKey(monsterId);
	}

	/**
	 * 检查怪物组合编号是否存在
	 * 
	 * @param monsterGroupId
	 *            怪物组合编号
	 * @return 怪物编号存在
	 */
	public boolean checkMonsterGroupId(int monsterGroupId) {
		return monsterGroupMap.containsKey(monsterGroupId);
	}

	/**
	 * 获取怪物集合
	 * 
	 * @param monsterGroup
	 *            怪物组合编号
	 * @param num
	 *            数量
	 * @param level
	 *            等级
	 * @return 怪物集合
	 */
	public List<Monster> getMonstersByGroupId(MonsterGroup monsterGroup, int num, int level) {
		List<Monster> monsters = new ArrayList<Monster>();
		if (monsterGroup != null) {
			int size = monsterGroup.getNumber();
			if (size == -1) {
				size = BattleServer.randomMonsterNumber(num);
				int i = 0;
				while (i < size) {
					long monsterId = monsterGroup.getRandomMonster();
					if (monsterId != 0 && monsterMap.containsKey(monsterId)) {
						if (monsterGroup.getType() == 0) {
							monsters.add(monsterMap.get(monsterId).copy());
						} else {
							monsters.add(monsterMap.get(monsterId).copy(level));
						}

						i++;
					}
				}
			} else {
				for (Long monsterId : monsterGroup.getMonsters()) {
					if (monsterId != 0 && monsterMap.containsKey(monsterId)) {
						if (monsterGroup.getType() == 0) {
							monsters.add(monsterMap.get(monsterId).copy());
						} else {
							monsters.add(monsterMap.get(monsterId).copy(level));
						}
					}
				}
			}
		}
		return monsters;
	}

	/**
	 * 根据怪物组合编号获取怪物组合对象
	 * 
	 * @param monsterGroupId
	 *            怪物组合编号
	 * @return 怪物组合对象
	 */
	public MonsterGroup getMonsterGroup(int monsterGroupId) {
		return monsterGroupMap.get(monsterGroupId);
	}

	/**
	 * 根据编号获取地图上物品
	 * 
	 * @param id
	 *            编号
	 * @return 地图上物品
	 */
	public MonsterBox getMonsterBox(int id) {
		if (monsterBoxMap.containsKey(id)) {
			return monsterBoxMap.get(id);
		}
		return null;
	}

	/**
	 * 随机获取一个猎技宝箱
	 * 
	 * @return 猎技宝箱
	 */
	public Integer randomSkillBoxs() {
		return skillBoxs.get(Util.getRandom(skillBoxs.size()));
	}

	/**
	 * 根据帮派等级获取怪物入侵怪物
	 * 
	 * @param gangLevel
	 *            帮派等级
	 * @return 怪物入侵怪物集合
	 */
	public List<MonsterInvasionRes> getMonsterInvasion(int gangLevel) {
		return monsterInvasionMap.get(gangLevel);
	}

	public Monster getMonster(long monsterId) {
		if (monsterMap.containsKey(monsterId)) {
			return monsterMap.get(monsterId);
		}
		return null;
	}

}
