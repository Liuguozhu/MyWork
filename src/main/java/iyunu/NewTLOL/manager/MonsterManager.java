package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class MonsterManager {

	/**
	 * 私有构造方法
	 */
	private MonsterManager() {

	}

	private static MonsterManager instance = new MonsterManager();

	/**
	 * @return MapManager实例
	 */
	public static MonsterManager instance() {
		return instance;
	}

	private List<MonsterOnMap> monsterOnMaps = new ArrayList<MonsterOnMap>(); // <地图上怪物对象>

	public void addMonsterOnMap(MonsterOnMap monsterOnMap, long time) {
		monsterOnMap.setDisappearTime(System.currentTimeMillis() + time);
		monsterOnMaps.add(monsterOnMap);
	}

	public void check() {
		long now = System.currentTimeMillis();
		Iterator<MonsterOnMap> it = monsterOnMaps.iterator();
		while (it.hasNext()) {
			MonsterOnMap monsterOnMap = it.next();

			if (!monsterOnMap.isFighting() && monsterOnMap.getDisappearTime() > 0 && monsterOnMap.getDisappearTime() <= now) {
				it.remove();

				BaseMap baseMap = MapManager.instance().getMapById(monsterOnMap.getMapId());
				baseMap.removeMonster(monsterOnMap.getUid());

//				if (monsterOnMap.isGhost()) {
//					for (Long roleId : monsterOnMap.getOwnerIds()) {
//						if (ServerManager.instance().isOnline(roleId)) {
//							Role role = ServerManager.instance().getOnlinePlayer(roleId);
//							if (role.getGhostTask() != null) {
//								role.getGhostTask().finishTask();
//							}
//						}
//					}
//				}
			}
		}
	}
}
