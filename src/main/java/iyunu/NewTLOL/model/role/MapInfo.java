package iyunu.NewTLOL.model.role;

import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.message.MapMessage;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.ItemOnMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;
import iyunu.NewTLOL.model.map.instance.MapRaidsInfo;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.model.task.ETaskType;
import iyunu.NewTLOL.model.task.instance.BattleOnMapTask;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.json.JsonTool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class MapInfo {

	private int mapId;
	private int x;
	private int y;
	private int arriveX = 0;
	private int arriveY = 0;
	private BaseMap baseMap; // 地图对象
	private MapAgent mapAgent; // 地图
	private HashMap<Long, MonsterOnMap> monsterOnMaps = new HashMap<Long, MonsterOnMap>(); // 怪物集合<唯一编号，怪物组合编号>
	private HashMap<Long, ItemOnMap> boxOnMap = new HashMap<Long, ItemOnMap>(); // 宝箱集合<唯一编号，宝箱编号>
	private Role role;

	public String toString() {

		for (MonsterOnMap monsterOnMap : monsterOnMaps.values()) {
			monsterOnMap.setFighting(false);
		}

		SerializeWriter out1 = new SerializeWriter();
		JSONSerializer serializer1 = new JSONSerializer(out1);
		serializer1.write(monsterOnMaps);

		SerializeWriter out2 = new SerializeWriter();
		JSONSerializer serializer2 = new JSONSerializer(out2);
		serializer2.write(boxOnMap);

		return mapId + "&" + x + "&" + y + "&" + serializer1.toString() + "&" + serializer2.toString();
	}

	public boolean isMapRaids() {
		if (baseMap instanceof MapRaidsInfo) {
			return true;
		}
		return false;
	}

	/**
	 * 清空目标坐标
	 */
	public void cleanArriveSite() {
		arriveX = 0;
		arriveY = 0;
	}

	public void addRole(Role role) {
		this.role = role;
	}

	public String encode() {
		SerializeWriter out1 = new SerializeWriter();
		JSONSerializer serializer1 = new JSONSerializer(out1);
		serializer1.write(monsterOnMaps);

		SerializeWriter out2 = new SerializeWriter();
		JSONSerializer serializer2 = new JSONSerializer(out2);
		serializer2.write(boxOnMap);

		return mapId + "&" + x + "&" + y + "&" + serializer1.toString() + "&" + serializer2.toString();
	}

	public static MapInfo decode(String string) {
		MapInfo mapInfo = new MapInfo();
		String[] strs = string.split("&");
		mapInfo.setMapId(Translate.stringToInt(strs[0]));
		mapInfo.setX(Translate.stringToInt(strs[1]));
		mapInfo.setY(Translate.stringToInt(strs[2]));
		if (strs.length > 3) {
			if (strs[3] != null && !"".equals(strs[3]) && !"{}".equals(strs[3])) {
				HashMap<Long, MonsterOnMap> map = JsonTool.decode(strs[3], new TypeReference<HashMap<Long, MonsterOnMap>>() {
				});

				Iterator<Entry<Long, MonsterOnMap>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Entry<Long, MonsterOnMap> entry = it.next();
					MonsterOnMap monsterOnMap = entry.getValue();
					if (monsterOnMap.getMonsterGroup() != null) {
						monsterOnMap.setMonsterGroupId(monsterOnMap.getMonsterGroup().getId());
						monsterOnMap.setMonsterName(monsterOnMap.getMonsterGroup().getName());
						monsterOnMap.setRes(monsterOnMap.getMonsterGroup().getRes());
					}
				}

				mapInfo.setMonsterOnMaps(map);
			}
			if (strs[4] != null && !"".equals(strs[4]) && !"{}".equals(strs[4])) {
				HashMap<Long, ItemOnMap> map = JsonTool.decode(strs[4], new TypeReference<HashMap<Long, ItemOnMap>>() {
				});
				mapInfo.setBoxOnMap(map);
			}
		}
		mapInfo.setBaseMap(MapManager.instance().getMapById(mapInfo.getMapId()));
		return mapInfo;
	}

	public void addMonsterOnMap(MonsterOnMap monsterOnMap) {
		this.monsterOnMaps.put(monsterOnMap.getUid(), monsterOnMap);
		MapMessage.sendMonsterOnMap(role, baseMap, monsterOnMap);
	}

	public void addBoxOnMap(ItemOnMap itemOnMap) {
		this.boxOnMap.put(itemOnMap.getUid(), itemOnMap);
		MapMessage.sendItemOnMap(role, baseMap, itemOnMap);
	}

	public void removeMonsterOnMap(MonsterOnMap monsterOnMap) {

		monsterOnMaps.remove(monsterOnMap.getUid());
		for (BaseTask baseTask : role.getTasks().values()) {
			if (baseTask.getState().equals(ETaskState.during) && baseTask.getType().equals(ETaskType.battleOnMapTask)) {

				BattleOnMapTask battleOnMapTask = (BattleOnMapTask) baseTask;
				if (battleOnMapTask.getMonsterGroup() == monsterOnMap.getMonsterGroupId()) {

					baseTask.finishTask();
				}
			}
		}
		MapMessage.removeMonsterOnMap(role, monsterOnMap.getUid());
	}

	public void removeMonsterOnMap(long uid) {
		if (monsterOnMaps.containsKey(uid)) {
			monsterOnMaps.remove(uid);
			for (BaseTask baseTask : role.getTasks().values()) {
				if (baseTask.getState().equals(ETaskState.during)) {

					if (baseTask.getType().equals(ETaskType.battleOnMapTask)) {
						BattleOnMapTask battleOnMapTask = (BattleOnMapTask) baseTask;
						if (battleOnMapTask.getUid() == uid) {
							baseTask.finishTask();
						}
					}
				}
			}

			MapMessage.removeMonsterOnMap(role, uid);
		}
	}

	public void removeBoxOnMap(long uid) {
		if (boxOnMap.containsKey(uid)) {
			boxOnMap.remove(uid);
			MapMessage.removeItemOnMap(baseMap, uid);
		}
	}

	/**
	 * @return the mapId
	 */
	public int getMapId() {
		return mapId;
	}

	/**
	 * @param mapId
	 *            the mapId to set
	 */
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the baseMap
	 */
	public BaseMap getBaseMap() {
		return baseMap;
	}

	/**
	 * @param baseMap
	 *            the baseMap to set
	 */
	public void setBaseMap(BaseMap baseMap) {
		this.baseMap = baseMap;
	}

	/**
	 * @return the mapAgent
	 */
	public MapAgent getMapAgent() {
		return mapAgent;
	}

	/**
	 * @param mapAgent
	 *            the mapAgent to set
	 */
	public void setMapAgent(MapAgent mapAgent) {
		this.mapAgent = mapAgent;
	}

	/**
	 * @return the monsterOnMaps
	 */
	public HashMap<Long, MonsterOnMap> getMonsterOnMaps() {
		return monsterOnMaps;
	}

	/**
	 * @param monsterOnMaps
	 *            the monsterOnMaps to set
	 */
	public void setMonsterOnMaps(HashMap<Long, MonsterOnMap> monsterOnMaps) {
		this.monsterOnMaps = monsterOnMaps;
	}

	/**
	 * @return the boxOnMap
	 */
	public HashMap<Long, ItemOnMap> getBoxOnMap() {
		return boxOnMap;
	}

	/**
	 * @param boxOnMap
	 *            the boxOnMap to set
	 */
	public void setBoxOnMap(HashMap<Long, ItemOnMap> boxOnMap) {
		this.boxOnMap = boxOnMap;
	}

	/**
	 * @return the arriveX
	 */
	public int getArriveX() {
		return arriveX;
	}

	/**
	 * @param arriveX
	 *            the arriveX to set
	 */
	public void setArriveX(int arriveX) {
		this.arriveX = arriveX;
	}

	/**
	 * @return the arriveY
	 */
	public int getArriveY() {
		return arriveY;
	}

	/**
	 * @param arriveY
	 *            the arriveY to set
	 */
	public void setArriveY(int arriveY) {
		this.arriveY = arriveY;
	}

}
