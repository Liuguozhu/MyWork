package iyunu.NewTLOL.model.map;

import iyunu.NewTLOL.json.MonsterJson;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.MapMessage;
import iyunu.NewTLOL.model.role.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public abstract class BaseMap {

	/** 地图编号 **/
	protected int id;
	/** 地图名称 **/
	protected String name;
	protected EMapType type; // 地图类型
	/** 是否可以遇怪(0.可以，1.不可以) **/
	protected int pve;
	/** 是否可以战斗(0.可以，1.不可以) **/
	protected int pvp;
	/** 是否可以组队(0.可以，1.不可以) **/
	protected boolean isTeam;
	/** NPC集合 **/
	protected List<Integer> npcs = new ArrayList<Integer>();
	/** 传送点集合 **/
	protected List<Integer> transfers = new ArrayList<Integer>();
	/** 采集物品集合 **/
	protected List<Integer> collecteds = new ArrayList<Integer>();
	/** 宽 **/
	protected int width = 120;
	/** 高 **/
	protected int height = 67;
	/** 网格集合 **/
	protected ConcurrentSkipListSet<Long> roles = new ConcurrentSkipListSet<>();
	// protected ConcurrentSkipListSet<Long> moveRoles = new
	// ConcurrentSkipListSet<>();
	// protected ConcurrentSkipListSet<Long> newRoles = new
	// ConcurrentSkipListSet<>();
	// protected HashSet<Long> roles = new HashSet<Long>();
	/** 传送X坐标 **/
	protected int transmitX;
	/** 传送Y坐标 **/
	protected int transmitY;

	protected List<Integer> teamList = new ArrayList<Integer>(); // 队伍集合
	protected ConcurrentHashMap<Long, MonsterOnMap> monsters = new ConcurrentHashMap<Long, MonsterOnMap>(); // 怪物集合<唯一编号，怪物组合编号>
	protected Map<Long, ItemOnMap> boxs = new HashMap<Long, ItemOnMap>(); // 宝箱集合<唯一编号，宝箱编号>

	/**
	 * 怪物是否存在
	 * 
	 * @param uid
	 *            怪物唯一编号
	 * @return 怪物存在
	 */
	public boolean isMonsterExist(long uid) {
		return monsters.containsKey(uid);
	}

	/**
	 * 根据编号获取地图上怪物
	 * 
	 * @param uid
	 *            唯一编号
	 * @return 地图上怪物
	 */
	public MonsterOnMap getMonsterOnMap(long uid) {
		return monsters.get(uid);
	}

	/**
	 * 根据编号获取地图上物品
	 * 
	 * @param uid
	 *            唯一编号
	 * @return 地图上物品
	 */
	public ItemOnMap getItemOnMap(long uid) {
		return boxs.get(uid);
	}

	public void addItem(int x, int y, int monsterBoxId) {
		ItemOnMap itemOnMap = new ItemOnMap(id, x, y, MonsterJson.instance().getMonsterBox(monsterBoxId));
		boxs.put(itemOnMap.getUid(), itemOnMap);
		MapMessage.sendItemOnMap(this, itemOnMap);
	}

	public void addItem(int x, int y, int monsterBoxId, Set<Long> roleIds) {
		ItemOnMap itemOnMap = new ItemOnMap(id, x, y, MonsterJson.instance().getMonsterBox(monsterBoxId));
		itemOnMap.getOwnerIds().addAll(roleIds);
		boxs.put(itemOnMap.getUid(), itemOnMap);
		MapMessage.sendItemOnMap(this, itemOnMap);
	}

	// public void addMonster(int x, int y, MonsterGroup monsterGroup) {
	// if (monsterGroup != null) {
	// MonsterOnMap monsterOnMap = new MonsterOnMap(id, x, y, monsterGroup);
	// addMonster(monsterOnMap);
	// }
	// }
	public void addMonster(int x, int y, int monsterGroupId, EMonsterOnMap eMonsterOnMap) {
		// if (monsterGroup != null) {
		MonsterOnMap monsterOnMap = new MonsterOnMap(id, x, y, monsterGroupId, eMonsterOnMap);
		addMonster(monsterOnMap);
		// }
	}

	public void addMonster(MonsterOnMap monsterOnMap) {
		monsters.put(monsterOnMap.getUid(), monsterOnMap);
		MapMessage.sendMonsterOnMap(this, monsterOnMap);
	}

	public void addMonsterNoSend(MonsterOnMap monsterOnMap) {
		monsters.put(monsterOnMap.getUid(), monsterOnMap);
	}

	/**
	 * 删除物品
	 * 
	 * @param uid
	 *            唯一编号
	 */
	public void removeItem(long uid) {
		boxs.remove(uid);
		MapMessage.removeItemOnMap(this, uid);
	}

	/**
	 * @function 删除地图上的宝箱
	 * @author LuoSR
	 * @date 2014年5月27日
	 */
	public void removeAllItem() {
		List<Long> list = new ArrayList<Long>();
		Set<Entry<Long, ItemOnMap>> set = boxs.entrySet();
		for (Iterator<Entry<Long, ItemOnMap>> it = set.iterator(); it.hasNext();) {
			Entry<Long, ItemOnMap> entry = it.next();
			ItemOnMap itemOnMap = entry.getValue();
			if (!list.contains(itemOnMap.getUid())) {
				list.add(itemOnMap.getUid());
			}
		}
		MapMessage.removeItemOnMap(this, list);
	}

	/**
	 * 删除怪物
	 * 
	 * @param uid
	 *            唯一编号
	 */
	public void removeMonster(long uid) {
		if (monsters.containsKey(uid)) {
			MonsterOnMap monsterOnMap = monsters.get(uid);
			monsters.remove(uid);
			MapMessage.removeMonsterOnMap(this, uid);

			if (monsterOnMap.isGhost()) {
				for (Long roleId : monsterOnMap.getOwnerIds()) {
					if (ServerManager.instance().isOnline(roleId)) {
						Role role = ServerManager.instance().getOnlinePlayer(roleId);
						if (role.getGhostTask() != null) {
							role.getGhostTask().finishTask();
						}
					}
				}
			}
		}
	}

	/**
	 * 删除活动怪物（战斗前删除）
	 * 
	 * @param uid
	 *            唯一编号
	 */
	public void removeMonsterOnMap(long uid) {
		if (monsters.containsKey(uid)) {
			monsters.remove(uid);
			MapMessage.removeMonsterOnMap(this, uid);
		}
	}

	/**
	 * 复制自身
	 * 
	 * @return 帮派地图
	 */
	public abstract BaseMap copy();

	/**
	 * 初始化网格
	 */
	// public abstract void init();

	/**
	 * 计算所在格子
	 * 
	 * @param role
	 *            角色对象
	 * @return 所在格子索引
	 */
	// public abstract int calcGrid(final Role role);

	/**
	 * 计算周围网格
	 * 
	 * @param role
	 *            角色对象
	 * @return 网格集合
	 */
	// public abstract List<Integer> countGrid(Role role);

	/**
	 * 获取周围玩家列表
	 * 
	 * @param role
	 *            角色对象
	 * @return 玩家编号列表
	 */
	// public final Set<Long> getNearby() {
	// return roles;
	// }

	/**
	 * 从地图中删除角色
	 * 
	 * @param role
	 *            角色对象
	 */
	public final void remove(final Role role) {
		try {
			// synchronized(roles){
			roles.remove(role.getId());
			// }
			// rolesAtGrids.get(calcGrid(role)).remove(role.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 地图添加角色对象
	 * 
	 * @param role
	 *            角色对象
	 */
	public final void add(final Role role) {
		roles.add(role.getId());
		// newRoles.add(role.getId());
		// MapMessage.refreshNearbyAll(this, role);
		// int grid = calcGrid(role);
		// if (grid >= 0 && grid < rolesAtGrids.size()) {
		// rolesAtGrids.get(grid).add(role.getId());
		// } else {
		// rolesAtGrids.get(0).add(role.getId());
		// }
	}

	// public boolean check(long roleId) {
	// return roles.contains(roleId);
	// }

	/**
	 * 获取地图中所有角色
	 * 
	 * @return 角色集合
	 */
	public final ConcurrentSkipListSet<Long> allInMap() {
		// ArrayList<Long> sameMap = new ArrayList<Long>();
		// for (HashSet<Long> grid : rolesAtGrids) {
		// sameMap.addAll(grid);
		// }
		return roles;
	}

	/**
	 * 获取地图上人数
	 * 
	 * @return 人数
	 */
	public final int numberOnMap() {
		// int sum = 0;
		// for (HashSet<Long> grid : rolesAtGrids) {
		// sum += grid.size();
		// }
		return roles.size();
	}

	/**
	 * 获取最大x坐标
	 * 
	 * @return 最大x坐标
	 */
	public int xMax() {
		return width;
	}

	/**
	 * 获取最大y坐标
	 * 
	 * @return 最大y坐标
	 */
	public int yMax() {
		return height;
	}

	/**
	 * 添加队伍
	 * 
	 * @param teamId
	 *            队伍编号
	 */
	public void addTeam(Integer teamId) {
		teamList.add(teamId);
	}

	/**
	 * 删除队伍
	 * 
	 * @param teamId
	 *            队伍编号
	 */
	public void removeTeam(Integer teamId) {
		teamList.remove(teamId);
	}

	/**
	 * @function 获得该地图中所有队伍
	 * @author LuoSR
	 * @return 所有队伍
	 * @date 2014年1月15日
	 */
	public List<Integer> allTeamInMap() {
		return teamList;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the pve
	 */
	public int getPve() {
		return pve;
	}

	/**
	 * @param pve
	 *            the pve to set
	 */
	public void setPve(int pve) {
		this.pve = pve;
	}

	/**
	 * @return the pvp
	 */
	public int getPvp() {
		return pvp;
	}

	/**
	 * @param pvp
	 *            the pvp to set
	 */
	public void setPvp(int pvp) {
		this.pvp = pvp;
	}

	/**
	 * @return the npcs
	 */
	public List<Integer> getNpcs() {
		return npcs;
	}

	/**
	 * @param npcs
	 *            the npcs to set
	 */
	public void setNpcs(List<Integer> npcs) {
		this.npcs = npcs;
	}

	/**
	 * @return the transfers
	 */
	public List<Integer> getTransfers() {
		return transfers;
	}

	/**
	 * @param transfers
	 *            the transfers to set
	 */
	public void setTransfers(List<Integer> transfers) {
		this.transfers = transfers;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the type
	 */
	public EMapType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EMapType type) {
		this.type = type;
	}

	/**
	 * @return the teamList
	 */
	public List<Integer> getTeamList() {
		return teamList;
	}

	/**
	 * @param teamList
	 *            the teamList to set
	 */
	public void setTeamList(List<Integer> teamList) {
		this.teamList = teamList;
	}

	/**
	 * @return the monsters
	 */
	public Map<Long, MonsterOnMap> getMonsters() {
		return monsters;
	}

	/**
	 * @return the boxs
	 */
	public Map<Long, ItemOnMap> getBoxs() {
		return boxs;
	}

	public int getTransmitX() {
		return transmitX;
	}

	public void setTransmitX(int transmitX) {
		this.transmitX = transmitX;
	}

	public int getTransmitY() {
		return transmitY;
	}

	public void setTransmitY(int transmitY) {
		this.transmitY = transmitY;
	}

	/**
	 * @return the isTeam
	 */
	public boolean isTeam() {
		return isTeam;
	}

	/**
	 * @param isTeam
	 *            the isTeam to set
	 */
	public void setTeam(boolean isTeam) {
		this.isTeam = isTeam;
	}

	/**
	 * @return the collecteds
	 */
	public List<Integer> getCollecteds() {
		return collecteds;
	}

	/**
	 * @param collecteds
	 *            the collecteds to set
	 */
	public void setCollecteds(List<Integer> collecteds) {
		this.collecteds = collecteds;
	}

}
