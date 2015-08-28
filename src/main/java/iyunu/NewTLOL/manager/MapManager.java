package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.json.MapJson;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.instance.MapGangInfo;
import iyunu.NewTLOL.model.role.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class MapManager {

	/**
	 * 私有构造方法
	 */
	private MapManager() {

	}

	private static MapManager instance = new MapManager();

	/**
	 * @return MapManager实例
	 */
	public static MapManager instance() {
		return instance;
	}

	private Map<Long, MapGangInfo> mapGangInfos = new HashMap<Long, MapGangInfo>(); // <帮派编号，帮派地图>

	private ConcurrentLinkedQueue<Role> baseQueue = new ConcurrentLinkedQueue<Role>();
	private ConcurrentLinkedQueue<Role> battleQueue = new ConcurrentLinkedQueue<Role>();
	private ConcurrentLinkedQueue<Role> gangStateQueue = new ConcurrentLinkedQueue<Role>();
	private ConcurrentLinkedQueue<Role> teamQueue = new ConcurrentLinkedQueue<Role>();
	private ConcurrentLinkedQueue<Role> upgradeQueue = new ConcurrentLinkedQueue<Role>();
	private ConcurrentLinkedQueue<Role> bloodQueue = new ConcurrentLinkedQueue<Role>();
	private ConcurrentLinkedQueue<Role> shenbingQueue = new ConcurrentLinkedQueue<Role>();
	private ConcurrentLinkedQueue<Role> shizhuangQueue = new ConcurrentLinkedQueue<Role>();

	public Role getBaseQueue() {
		return baseQueue.poll();
	}

	public Role getBattleQueue() {
		return battleQueue.poll();
	}

	public Role getGangStateQueue() {
		return gangStateQueue.poll();
	}

	public Role getTeamQueue() {
		return teamQueue.poll();
	}

	public Role getUpgradeQueue() {
		return upgradeQueue.poll();
	}

	public Role getBloodQueue() {
		return bloodQueue.poll();
	}

	public Role getShenbingQueue() {
		return shenbingQueue.poll();
	}

	public Role getShizhuangQueue() {
		return shizhuangQueue.poll();
	}

	public void addBaseQueue(Role role) {
		baseQueue.add(role);
	}

	public void addBloodQueue(Role role) {
		bloodQueue.add(role);
	}

	public void addBattleQueue(Role role) {
		battleQueue.add(role);
	}

	public void addGangStateQueue(Role role) {
		gangStateQueue.add(role);
	}

	public void addTeamQueue(Role role) {
		teamQueue.add(role);
	}

	public void addUpgradeQueue(Role role) {
		upgradeQueue.add(role);
	}

	public void addShenbingQueue(Role role) {
		shenbingQueue.add(role);
	}

	public void addShizhuangQueue(Role role) {
		shizhuangQueue.add(role);
	}

	/**
	 * 初始化帮派地图
	 * 
	 * @param gangId
	 *            帮派编号
	 * @param mapGangInfo
	 *            帮派地图
	 */
	public void initGangMap(Gang gang, MapGangInfo mapGangInfo) {
		gang.setMapGangInfo(mapGangInfo);
		mapGangInfos.put(gang.getId(), mapGangInfo);
	}

	public MapGangInfo getMapGangByGangId(long gangId) {
		return mapGangInfos.get(gangId);
	}

	/**
	 * 根据编号获取地图信息
	 * 
	 * @param id
	 *            地图编号
	 * @return 地图信息对象
	 */
	public BaseMap getMapById(int id) {
		return MapJson.instance().getMapById(id);
	}

	/**
	 * 从地图中删除队伍信息
	 * 
	 * @param mapId
	 *            地图编号
	 * @param teamId
	 *            队伍编号
	 */
	public void removeTeam(int mapId, int teamId) {
		BaseMap mapInfo = MapJson.instance().getMapById(mapId);
		if (mapInfo != null) {
			mapInfo.removeTeam(teamId);
		}
	}

	/**
	 * 检查坐标是否合法
	 * 
	 * @param mapId
	 *            地图编号
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @return 坐标合法
	 */
	public static boolean checkCoord(final BaseMap map, final int x, final int y) {
		if (map == null || x <= 0 || y <= 0 || x >= map.xMax() || y >= map.yMax()) {
			return false;
		}
		return true;
	}

	/**
	 * 改变所在格子
	 * 
	 * @param role
	 *            角色对象
	 * @param newMap
	 *            新地图
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	public void changeGrid(final Role role, final BaseMap newMap, final int x, final int y, final int arriveX, final int arriveY) {
		role.freshCoords(newMap.getId(), x, y, arriveX, arriveY);
		role.getMapInfo().setBaseMap(newMap);
		newMap.add(role);
	}

	public void moveGrid(final Role role, final BaseMap newMap, final int x, final int y, final int arriveX, final int arriveY) {
		role.freshCoords(x, y, arriveX, arriveY);
	}

	/**
	 * 从地图中删除角色
	 * 
	 * @param role
	 *            角色对象
	 */
	public void remove(final Role role) {
		BaseMap map = role.getMapInfo().getBaseMap();
		if (map != null) {
			map.remove(role);
		}
	}

}
