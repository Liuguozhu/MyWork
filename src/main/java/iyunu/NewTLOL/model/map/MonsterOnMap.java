package iyunu.NewTLOL.model.map;

import iyunu.NewTLOL.json.MonsterJson;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.model.monster.instance.MonsterGroup;

import java.util.ArrayList;
import java.util.List;

public class MonsterOnMap {

	private long uid; // 唯一编号
	private int mapId;
	private int x;
	private int y;
	private MonsterGroup monsterGroup;
	private String monsterName = "";
	private int monsterGroupId;
	private int levelLimit;
	private boolean isFighting = false; // 是否在战斗中

	private boolean isGhost = false; // 是否要求指定玩家击杀
	private List<Long> ownerIds = new ArrayList<Long>();
	private long disappearTime = -1;
	private String res;
	// private boolean isTeam; // 是否需要组队
	private int mark;// 怪物组合 头顶图标
	private int taskCategory = -1; // 绑定任务种类（-1.即没有）
	private EMonsterOnMap type = EMonsterOnMap.none;

	public MonsterOnMap() {

	}

	public MonsterOnMap(int mapId, int x, int y, int monsterGroupId, EMonsterOnMap eMonsterOnMap) {
		this.uid = UidManager.instance().uid();
		this.mapId = mapId;
		this.x = x;
		this.y = y;
		this.monsterGroupId = monsterGroupId;
		MonsterGroup monsterGroup = MonsterJson.instance().getMonsterGroup(monsterGroupId);
		this.monsterName = monsterGroup.getName();
		this.res = monsterGroup.getRes();
		this.mark = monsterGroup.getMark();
		this.levelLimit = monsterGroup.getLevelLimit();
		this.type = eMonsterOnMap;
	}

	public MonsterOnMap copy() {
		MonsterOnMap monsterOnMap = new MonsterOnMap();
		monsterOnMap.setUid(UidManager.instance().uid());
		monsterOnMap.setMapId(mapId);
		monsterOnMap.setX(x);
		monsterOnMap.setY(y);
		monsterOnMap.setMonsterGroupId(monsterGroupId);
		MonsterGroup monsterGroup = MonsterJson.instance().getMonsterGroup(monsterGroupId);
		monsterOnMap.setMonsterName(monsterGroup.getName());
		monsterOnMap.setRes(monsterGroup.getRes());
		monsterOnMap.setMark(monsterGroup.getMark());
		monsterOnMap.setLevelLimit(monsterGroup.getLevelLimit());
		monsterOnMap.setType(type);
		return monsterOnMap;
	}

	public void addOwner(long roleId) {
		ownerIds.add(roleId);
	}

	public void removeOwner(long roleId) {
		ownerIds.remove(roleId);

		if (ownerIds.size() <= 0) {
			BaseMap baseMap = MapManager.instance().getMapById(mapId);
			baseMap.removeMonster(uid);
		}
	}

	public boolean canFight(Long roleId) {
		if (isGhost) {
			return ownerIds.contains(roleId);
		}
		return true;
	}

	/**
	 * @return the uid
	 */
	public long getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(long uid) {
		this.uid = uid;
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
	 * @return the monsterGroup
	 */
	public MonsterGroup getMonsterGroup() {
		return monsterGroup;
	}

	/**
	 * @param monsterGroup
	 *            the monsterGroup to set
	 */
	public void setMonsterGroup(MonsterGroup monsterGroup) {
		this.monsterGroup = monsterGroup;
	}

	/**
	 * @return the isFighting
	 */
	public boolean isFighting() {
		return isFighting;
	}

	/**
	 * @param isFighting
	 *            the isFighting to set
	 */
	public void setFighting(boolean isFighting) {
		this.isFighting = isFighting;
	}

	/**
	 * @return the ownerIds
	 */
	public List<Long> getOwnerIds() {
		return ownerIds;
	}

	/**
	 * @return the isGhost
	 */
	public boolean isGhost() {
		return isGhost;
	}

	/**
	 * @param isGhost
	 *            the isGhost to set
	 */
	public void setGhost(boolean isGhost) {
		this.isGhost = isGhost;
	}

	/**
	 * @return the disappearTime
	 */
	public long getDisappearTime() {
		return disappearTime;
	}

	/**
	 * @param disappearTime
	 *            the disappearTime to set
	 */
	public void setDisappearTime(long disappearTime) {
		this.disappearTime = disappearTime;
	}

	/**
	 * @return the monsterGroupId
	 */
	public int getMonsterGroupId() {
		return monsterGroupId;
	}

	/**
	 * @param monsterGroupId
	 *            the monsterGroupId to set
	 */
	public void setMonsterGroupId(int monsterGroupId) {
		this.monsterGroupId = monsterGroupId;
	}

	/**
	 * @return the monsterName
	 */
	public String getMonsterName() {
		return monsterName;
	}

	/**
	 * @param monsterName
	 *            the monsterName to set
	 */
	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}

	/**
	 * @return the res
	 */
	public String getRes() {
		return res;
	}

	/**
	 * @param res
	 *            the res to set
	 */
	public void setRes(String res) {
		this.res = res;
	}

	/**
	 * @return the mark
	 */
	public int getMark() {
		return mark;
	}

	/**
	 * @param mark
	 *            the mark to set
	 */
	public void setMark(int mark) {
		this.mark = mark;
	}

	/**
	 * @return the taskCategory
	 */
	public int getTaskCategory() {
		return taskCategory;
	}

	/**
	 * @param taskCategory
	 *            the taskCategory to set
	 */
	public void setTaskCategory(int taskCategory) {
		this.taskCategory = taskCategory;
	}

	/**
	 * @return the levelLimit
	 */
	public int getLevelLimit() {
		return levelLimit;
	}

	/**
	 * @param levelLimit
	 *            the levelLimit to set
	 */
	public void setLevelLimit(int levelLimit) {
		this.levelLimit = levelLimit;
	}

	/**
	 * @return the type
	 */
	public EMonsterOnMap getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EMonsterOnMap type) {
		this.type = type;
	}

}
