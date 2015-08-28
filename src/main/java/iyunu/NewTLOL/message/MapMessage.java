package iyunu.NewTLOL.message;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.gang.GangJobTitle;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.ItemOnMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class MapMessage {

	/**
	 * 发送地图信息
	 * 
	 * @param role
	 *            角色对象
	 * @param mapInfo
	 *            地图信息
	 */
	public static void sendMap(Role role, BaseMap mapInfo) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshMap");
				llpMessage.write("mapId", mapInfo.getId());
				llpMessage.write("x", role.getMapInfo().getX());
				llpMessage.write("y", role.getMapInfo().getY());

				List<Integer> npcInfoIds = mapInfo.getNpcs(); // NPC信息
				for (Integer npcInfoId : npcInfoIds) {
					llpMessage.write("npcInfoList", npcInfoId);
				}
				List<Integer> transferInfoIds = mapInfo.getTransfers(); // 传送点信息
				for (Integer transferInfoId : transferInfoIds) {
					llpMessage.write("transferInfoList", transferInfoId);
				}

				List<Integer> collectedInfoIds = mapInfo.getCollecteds(); // 采集物品信息
				for (Integer collectedInfoId : collectedInfoIds) {
					llpMessage.write("collectedInfoList", collectedInfoId);
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送地图信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
		MapMessage.sendItemOnMap(role, mapInfo.getId(), role.getMapInfo().getBaseMap().getBoxs());
		MapMessage.sendMonsterOnMap(role, mapInfo.getId(), role.getMapInfo().getBaseMap().getMonsters());
	}

	/**
	 * 血战附近刷新周围列表
	 * 
	 * @param role
	 */
	public static void refreshNearbyBlood(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshNearbyBlood");

				LlpMessage message = llpMessage.write("aroundBloodInfoList");
				message.write("roleId", role.getId());
				message.write("blood", role.getBlood());
				// ======协议发送======
				for (Long roleId : role.getMapInfo().getBaseMap().allInMap()) {
					if (ServerManager.instance().isOnline(roleId)) {
						Role online = ServerManager.instance().getOnlinePlayer(roleId);

						if (online != null && online.getChannel() != null && online.isLogon()) {
							online.getChannel().write(llpMessage);
						}
					}
				}

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新周围玩家战斗信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新地图上传送点
	 * 
	 * @param role
	 *            角色对象
	 * @param transferInfoIds
	 *            新增传送点集合
	 */
	public static void refreshTransfer(Role role, List<Integer> transferInfoIds) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshTransfer");
				for (Integer transferInfoId : transferInfoIds) {
					llpMessage.write("transferInfoList", transferInfoId);
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送地图上传送点");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 删除地图上传送点
	 * 
	 * @param role
	 *            角色对象
	 * @param transferInfoIds
	 *            删除的传送点
	 */
	public static void removeTransfer(Role role, List<Integer> transferInfoIds) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshTransfer");
				for (Integer transferInfoId : transferInfoIds) {
					llpMessage.write("transferInfoList", transferInfoId);
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送地图上传送点");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新地图上物品
	 * 
	 * @param role
	 *            角色对象
	 * @param itemOnMaps
	 *            物品集合
	 */
	public static void sendItemOnMap(Role role, int mapId, Map<Long, ItemOnMap> itemOnMaps) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshItemOnMap");
				Set<Entry<Long, ItemOnMap>> set = itemOnMaps.entrySet();
				for (Iterator<Entry<Long, ItemOnMap>> it = set.iterator(); it.hasNext();) {
					ItemOnMap itemOnMap = it.next().getValue();
					LlpMessage message = llpMessage.write("itemOnMapInfoList");
					message.write("uid", itemOnMap.getUid());
					message.write("itemId", itemOnMap.getMonsterBoxId());
					message.write("name", itemOnMap.getName());
					message.write("icon", itemOnMap.getIcon());
					message.write("mapId", itemOnMap.getMapId());
					message.write("x", itemOnMap.getX());
					message.write("y", itemOnMap.getY());
				}

				for (ItemOnMap itemOnMap : role.getMapInfo().getBoxOnMap().values()) {
					if (mapId == itemOnMap.getMapId()) {
						LlpMessage message = llpMessage.write("itemOnMapInfoList");
						message.write("uid", itemOnMap.getUid());
						message.write("itemId", itemOnMap.getMonsterBoxId());
						message.write("name", itemOnMap.getName());
						message.write("icon", itemOnMap.getIcon());
						message.write("mapId", itemOnMap.getMapId());
						message.write("x", itemOnMap.getX());
						message.write("y", itemOnMap.getY());
					}
				}

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新地图上物品");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新地图上物品
	 * 
	 * @param baseMap
	 *            地图信息
	 * @param itemOnMaps
	 *            物品集合
	 */
	public static void sendItemOnMap(BaseMap baseMap, List<ItemOnMap> itemOnMaps) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_refreshItemOnMap");
			for (ItemOnMap itemOnMap : itemOnMaps) {
				if (baseMap.getId() == itemOnMap.getMapId()) {
					LlpMessage message = llpMessage.write("itemOnMapInfoList");
					message.write("uid", itemOnMap.getUid());
					message.write("itemId", itemOnMap.getMonsterBoxId());
					message.write("name", itemOnMap.getName());
					message.write("icon", itemOnMap.getIcon());
					message.write("mapId", itemOnMap.getMapId());
					message.write("x", itemOnMap.getX());
					message.write("y", itemOnMap.getY());
				}
			}

			for (Long roleId : baseMap.allInMap()) {
				if (ServerManager.instance().isOnline(roleId)) {
					Role role = ServerManager.instance().getOnlinePlayer(roleId);

					if (role != null && role.getChannel() != null && role.isLogon()) {
						role.getChannel().write(llpMessage);
					}
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新地图上物品");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新地图上物品
	 * 
	 * @param baseMap
	 *            地图信息
	 * @param itemOnMaps
	 *            物品集合
	 */
	public static void sendItemOnMap(Role role, BaseMap baseMap, ItemOnMap itemOnMap) {
		if (baseMap.getId() == itemOnMap.getMapId()) {
			LlpMessage llpMessage = null;
			try {
				llpMessage = LlpJava.instance().getMessage("s_refreshItemOnMap");
				LlpMessage message = llpMessage.write("itemOnMapInfoList");
				message.write("uid", itemOnMap.getUid());
				message.write("itemId", itemOnMap.getMonsterBoxId());
				message.write("name", itemOnMap.getName());
				message.write("icon", itemOnMap.getIcon());
				message.write("mapId", itemOnMap.getMapId());
				message.write("x", itemOnMap.getX());
				message.write("y", itemOnMap.getY());

				if (role != null && role.getChannel() != null && role.isLogon()) {
					role.getChannel().write(llpMessage);
				}
			} catch (Exception e) {
				LogManager.info("异常报告：刷新地图上物品");
				e.printStackTrace();
			} finally {
				if (llpMessage != null) {
					llpMessage.destory();
				}
			}
		}
	}

	/**
	 * 刷新地图上物品
	 * 
	 * @param baseMap
	 *            地图信息
	 * @param itemOnMaps
	 *            物品集合
	 */
	public static void sendItemOnMap(BaseMap baseMap, ItemOnMap itemOnMap) {
		if (baseMap.getId() == itemOnMap.getMapId()) {
			LlpMessage llpMessage = null;
			try {
				llpMessage = LlpJava.instance().getMessage("s_refreshItemOnMap");
				LlpMessage message = llpMessage.write("itemOnMapInfoList");
				message.write("uid", itemOnMap.getUid());
				message.write("itemId", itemOnMap.getMonsterBoxId());
				message.write("name", itemOnMap.getName());
				message.write("icon", itemOnMap.getIcon());
				message.write("mapId", itemOnMap.getMapId());
				message.write("x", itemOnMap.getX());
				message.write("y", itemOnMap.getY());
				for (Long roleId : baseMap.allInMap()) {
					if (ServerManager.instance().isOnline(roleId)) {
						Role role = ServerManager.instance().getOnlinePlayer(roleId);
						if (role != null && role.getChannel() != null && role.isLogon()) {
							role.getChannel().write(llpMessage);
						}
					}
				}
			} catch (Exception e) {
				LogManager.info("异常报告：刷新地图上物品");
				e.printStackTrace();
			} finally {
				if (llpMessage != null) {
					llpMessage.destory();
				}
			}
		}
	}

	/**
	 * 删除地图上物品
	 * 
	 * @param baseMap
	 *            地图信息
	 * @param uids
	 *            物品唯一编号集合
	 */
	public static void removeItemOnMap(BaseMap baseMap, List<Long> uids) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_removeItemOnMap");
			for (Long uid : uids) {
				llpMessage.write("uids", uid);
			}

			for (Long roleId : baseMap.allInMap()) {
				if (ServerManager.instance().isOnline(roleId)) {
					Role role = ServerManager.instance().getOnlinePlayer(roleId);
					if (role != null && role.getChannel() != null && role.isLogon()) {
						role.getChannel().write(llpMessage);
					}
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：删除地图上物品");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 删除地图上物品
	 * 
	 * @param baseMap
	 *            地图信息
	 * @param uids
	 *            物品唯一编号集合
	 */
	public static void removeItemOnMap(BaseMap baseMap, long uid) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_removeItemOnMap");
			llpMessage.write("uids", uid);

			for (Long roleId : baseMap.allInMap()) {
				if (ServerManager.instance().isOnline(roleId)) {
					Role role = ServerManager.instance().getOnlinePlayer(roleId);
					if (role != null && role.getChannel() != null && role.isLogon()) {
						role.getChannel().write(llpMessage);
					}
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：删除地图上物品");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新地图上怪物
	 * 
	 * @param role
	 *            角色对象
	 * @param monsterOnMaps
	 *            怪物集合
	 */
	public static void sendMonsterOnMap(Role role, int mapId, Map<Long, MonsterOnMap> monsterOnMaps) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshMonsterOnMap");

				Set<Entry<Long, MonsterOnMap>> set = monsterOnMaps.entrySet();
				for (Iterator<Entry<Long, MonsterOnMap>> it = set.iterator(); it.hasNext();) {
					MonsterOnMap monsterOnMap = it.next().getValue();

					if (monsterOnMap.isGhost()) {
						if (monsterOnMap.getOwnerIds().contains(role.getId())) {
							LlpMessage message = llpMessage.write("monsterOnMapInfoList");
							message.write("uid", monsterOnMap.getUid());
							message.write("id", monsterOnMap.getMonsterGroupId());
							message.write("name", monsterOnMap.getMonsterName());
							message.write("icon", monsterOnMap.getRes());
							message.write("mapId", monsterOnMap.getMapId());
							message.write("x", monsterOnMap.getX());
							message.write("y", monsterOnMap.getY());
							message.write("mark", monsterOnMap.getMark());
							message.write("taskCategory", monsterOnMap.getTaskCategory());
							message.write("own", 1);
						}
					} else {
						LlpMessage message = llpMessage.write("monsterOnMapInfoList");
						message.write("uid", monsterOnMap.getUid());
						message.write("id", monsterOnMap.getMonsterGroupId());
						message.write("name", monsterOnMap.getMonsterName());
						message.write("icon", monsterOnMap.getRes());
						message.write("mapId", monsterOnMap.getMapId());
						message.write("x", monsterOnMap.getX());
						message.write("y", monsterOnMap.getY());
						message.write("mark", monsterOnMap.getMark());
						message.write("taskCategory", monsterOnMap.getTaskCategory());
						message.write("own", 1);
					}
				}

				for (MonsterOnMap monsterOnMap : role.getMapInfo().getMonsterOnMaps().values()) {
					if (mapId == monsterOnMap.getMapId()) {
						LlpMessage message = llpMessage.write("monsterOnMapInfoList");
						message.write("uid", monsterOnMap.getUid());
						message.write("id", monsterOnMap.getMonsterGroupId());
						message.write("name", monsterOnMap.getMonsterName());
						message.write("icon", monsterOnMap.getRes());
						message.write("mapId", monsterOnMap.getMapId());
						message.write("x", monsterOnMap.getX());
						message.write("y", monsterOnMap.getY());
						message.write("mark", monsterOnMap.getMark());
						message.write("taskCategory", monsterOnMap.getTaskCategory());
						message.write("own", 1);
					}
				}

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新地图上怪物");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新地图上怪物
	 * 
	 * @param baseMap
	 *            地图信息
	 * @param monsterOnMaps
	 *            怪物集合
	 */
	public static void sendMonsterOnMap(BaseMap baseMap, List<MonsterOnMap> monsterOnMaps) {

		for (Long roleId : baseMap.allInMap()) {
			if (ServerManager.instance().isOnline(roleId)) {
				Role role = ServerManager.instance().getOnlinePlayer(roleId);
				if (role != null && role.getChannel() != null && role.isLogon()) {

					LlpMessage llpMessage = null;
					try {
						llpMessage = LlpJava.instance().getMessage("s_refreshMonsterOnMap");
						for (MonsterOnMap monsterOnMap : monsterOnMaps) {
							if (baseMap.getId() == monsterOnMap.getMapId()) {
								if (monsterOnMap.isGhost()) {
									if (monsterOnMap.getOwnerIds().contains(role.getId())) {
										LlpMessage message = llpMessage.write("monsterOnMapInfoList");
										message.write("uid", monsterOnMap.getUid());
										message.write("id", monsterOnMap.getMonsterGroupId());
										message.write("name", monsterOnMap.getMonsterName());
										message.write("icon", monsterOnMap.getRes());
										message.write("mapId", monsterOnMap.getMapId());
										message.write("x", monsterOnMap.getX());
										message.write("y", monsterOnMap.getY());
										message.write("mark", monsterOnMap.getMark());
										message.write("taskCategory", monsterOnMap.getTaskCategory());
										message.write("own", 1);
									}
								} else {
									LlpMessage message = llpMessage.write("monsterOnMapInfoList");
									message.write("uid", monsterOnMap.getUid());
									message.write("id", monsterOnMap.getMonsterGroupId());
									message.write("name", monsterOnMap.getMonsterName());
									message.write("icon", monsterOnMap.getRes());
									message.write("mapId", monsterOnMap.getMapId());
									message.write("x", monsterOnMap.getX());
									message.write("y", monsterOnMap.getY());
									message.write("mark", monsterOnMap.getMark());
									message.write("taskCategory", monsterOnMap.getTaskCategory());
									message.write("own", 1);
								}
							}
						}

						role.getChannel().write(llpMessage);
					} catch (Exception e) {
						LogManager.info("异常报告：刷新地图上怪物");
						e.printStackTrace();
					} finally {
						if (llpMessage != null) {
							llpMessage.destory();
						}
					}
				}
			}
		}

	}

	/**
	 * 刷新地图上怪物
	 * 
	 * @param baseMap
	 *            地图信息
	 * @param monsterOnMaps
	 *            怪物集合
	 */
	public static void sendMonsterOnMap(Role role, BaseMap baseMap, MonsterOnMap monsterOnMap) {
		if (baseMap.getId() == monsterOnMap.getMapId()) {
			LlpMessage llpMessage = null;
			try {
				llpMessage = LlpJava.instance().getMessage("s_refreshMonsterOnMap");

				if (monsterOnMap.isGhost()) {
					if (monsterOnMap.getOwnerIds().contains(role.getId())) {
						LlpMessage message = llpMessage.write("monsterOnMapInfoList");
						message.write("uid", monsterOnMap.getUid());
						message.write("id", monsterOnMap.getMonsterGroupId());
						message.write("name", monsterOnMap.getMonsterName());
						message.write("icon", monsterOnMap.getRes());
						message.write("mapId", monsterOnMap.getMapId());
						message.write("x", monsterOnMap.getX());
						message.write("y", monsterOnMap.getY());
						message.write("mark", monsterOnMap.getMark());
						message.write("taskCategory", monsterOnMap.getTaskCategory());
						message.write("own", 1);
					}
				} else {
					LlpMessage message = llpMessage.write("monsterOnMapInfoList");
					message.write("uid", monsterOnMap.getUid());
					message.write("id", monsterOnMap.getMonsterGroupId());
					message.write("name", monsterOnMap.getMonsterName());
					message.write("icon", monsterOnMap.getRes());
					message.write("mapId", monsterOnMap.getMapId());
					message.write("x", monsterOnMap.getX());
					message.write("y", monsterOnMap.getY());
					message.write("mark", monsterOnMap.getMark());
					message.write("taskCategory", monsterOnMap.getTaskCategory());
					message.write("own", 1);
				}

				if (role != null && role.getChannel() != null && role.isLogon()) {
					role.getChannel().write(llpMessage);
				}
			} catch (Exception e) {
				LogManager.info("异常报告：刷新地图上怪物");
				e.printStackTrace();
			} finally {
				if (llpMessage != null) {
					llpMessage.destory();
				}
			}
		}
	}

	/**
	 * 刷新地图上怪物
	 * 
	 * @param baseMap
	 *            地图信息
	 * @param monsterOnMaps
	 *            怪物集合
	 */
	public static void sendMonsterOnMap(BaseMap baseMap, MonsterOnMap monsterOnMap) {

		if (baseMap.getId() == monsterOnMap.getMapId()) {

			for (Long roleId : baseMap.allInMap()) {
				if (ServerManager.instance().isOnline(roleId)) {
					Role role = ServerManager.instance().getOnlinePlayer(roleId);
					if (role != null && role.getChannel() != null && role.isLogon()) {

						LlpMessage llpMessage = null;
						try {
							llpMessage = LlpJava.instance().getMessage("s_refreshMonsterOnMap");

							if (monsterOnMap.isGhost()) {
								if (monsterOnMap.getOwnerIds().contains(role.getId())) {
									LlpMessage message = llpMessage.write("monsterOnMapInfoList");
									message.write("uid", monsterOnMap.getUid());
									message.write("id", monsterOnMap.getMonsterGroupId());
									message.write("name", monsterOnMap.getMonsterName());
									message.write("icon", monsterOnMap.getRes());
									message.write("mapId", monsterOnMap.getMapId());
									message.write("x", monsterOnMap.getX());
									message.write("y", monsterOnMap.getY());
									message.write("mark", monsterOnMap.getMark());
									message.write("taskCategory", monsterOnMap.getTaskCategory());
									message.write("own", 1);
								}
							} else {
								LlpMessage message = llpMessage.write("monsterOnMapInfoList");
								message.write("uid", monsterOnMap.getUid());
								message.write("id", monsterOnMap.getMonsterGroupId());
								message.write("name", monsterOnMap.getMonsterName());
								message.write("icon", monsterOnMap.getRes());
								message.write("mapId", monsterOnMap.getMapId());
								message.write("x", monsterOnMap.getX());
								message.write("y", monsterOnMap.getY());
								message.write("mark", monsterOnMap.getMark());
								message.write("taskCategory", monsterOnMap.getTaskCategory());
								message.write("own", 1);
							}

							role.getChannel().write(llpMessage);
						} catch (Exception e) {
							LogManager.info("异常报告：刷新地图上怪物");
							e.printStackTrace();
						} finally {
							if (llpMessage != null) {
								llpMessage.destory();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 删除地图上怪物
	 * 
	 * @param baseMap
	 *            地图信息
	 * @param uids
	 *            怪物唯一编号集合
	 */
	public static void removeMonsterOnMap(BaseMap baseMap, List<Long> uids) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_removeMonsterOnMap");
			for (Long uid : uids) {
				llpMessage.write("uids", uid);
			}

			for (Long roleId : baseMap.allInMap()) {
				if (ServerManager.instance().isOnline(roleId)) {
					Role role = ServerManager.instance().getOnlinePlayer(roleId);
					if (role != null && role.getChannel() != null && role.isLogon()) {
						role.getChannel().write(llpMessage);
					}
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：删除地图上怪物");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 删除地图上怪物
	 * 
	 * @param baseMap
	 *            地图信息
	 * @param uids
	 *            怪物唯一编号
	 */
	public static void removeMonsterOnMap(BaseMap baseMap, long uid) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_removeMonsterOnMap");
			llpMessage.write("uids", uid);

			for (Long roleId : baseMap.allInMap()) {
				if (ServerManager.instance().isOnline(roleId)) {
					Role role = ServerManager.instance().getOnlinePlayer(roleId);
					if (role != null && role.getChannel() != null && role.isLogon()) {
						role.getChannel().write(llpMessage);
					}
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：删除地图上怪物");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 通知指定玩家删除地图上怪物
	 * 
	 * @param role
	 *            角色对象
	 * @param uid
	 *            怪物唯一编号
	 */
	public static void removeMonsterOnMap(Role role, long uid) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_removeMonsterOnMap");
			llpMessage.write("uids", uid);

			if (role != null && role.getChannel() != null && role.isLogon()) {
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：删除地图上怪物");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 通知客户端目标地图
	 * 
	 * @param role
	 *            角色编号
	 * @param mapId
	 *            地图编号
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	public static void sendTargetSite(Role role, int mapId, int x, int y) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshTargetSite");
				llpMessage.write("mapId", mapId);
				llpMessage.write("x", x);
				llpMessage.write("y", y);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：客户端目标地图");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	public static void refreshNearbyBase(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshNearbyBase");

				LlpMessage message = llpMessage.write("aroundBaseInfoList");
				message.write("roleId", role.getId());
				message.write("name", role.getNick());
				message.write("figure", role.getFigure());
				message.write("vocation", role.getVocation().getName());
				message.write("vip", role.getVip().getLevel().ordinal());

				// ======协议发送======
				for (Long roleId : role.getMapInfo().getBaseMap().allInMap()) {
					if (ServerManager.instance().isOnline(roleId)) {
						Role online = ServerManager.instance().getOnlinePlayer(roleId);

						if (online != null && online.getChannel() != null && online.isLogon()) {
							online.getChannel().write(llpMessage);
						}
					}
				}

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新周围玩家基本信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	public static void refreshNearbyTeam(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshNearbyTeam");

				LlpMessage message = llpMessage.write("aroundTeamInfoList");
				message.write("roleId", role.getId());
				int isLeader = 0;
				if (role.getTeam() != null && role.getTeam().getLeader().getId() == role.getId()) {
					isLeader = 1;
				}
				message.write("isLeader", isLeader);

				// ======协议发送======
				for (Long roleId : role.getMapInfo().getBaseMap().allInMap()) {
					if (ServerManager.instance().isOnline(roleId)) {
						Role online = ServerManager.instance().getOnlinePlayer(roleId);

						if (online != null && online.getChannel() != null && online.isLogon()) {
							online.getChannel().write(llpMessage);
						}
					}
				}

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新周围玩家队伍信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	public static void refreshNearbyLevel(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshNearbyLevel");

				LlpMessage message = llpMessage.write("aroundLevelInfoList");
				message.write("roleId", role.getId());
				message.write("level", role.getLevel());
				// ======协议发送======
				for (Long roleId : role.getMapInfo().getBaseMap().allInMap()) {
					if (ServerManager.instance().isOnline(roleId)) {
						Role online = ServerManager.instance().getOnlinePlayer(roleId);

						if (online != null && online.getChannel() != null && online.isLogon()) {
							online.getChannel().write(llpMessage);
						}
					}
				}

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新周围玩家等级信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	public static void refreshNearbyBattle(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshNearbyBattle");

				LlpMessage message = llpMessage.write("aroundBattleInfoList");
				message.write("roleId", role.getId());
				message.write("isBattle", Util.falseOrTrue(role.isBattle()));
				// ======协议发送======
				for (Long roleId : role.getMapInfo().getBaseMap().allInMap()) {
					if (ServerManager.instance().isOnline(roleId)) {
						Role online = ServerManager.instance().getOnlinePlayer(roleId);

						if (online != null && online.getChannel() != null && online.isLogon()) {
							online.getChannel().write(llpMessage);
						}
					}
				}

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新周围玩家战斗信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	public static void refreshNearbyGang(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshNearbyGang");

				LlpMessage message = llpMessage.write("aroundGangInfoList");
				message.write("roleId", role.getId());
				if (role.getGang() != null) {
					message.write("gangName", role.getGang().getName());
					message.write("gangJob", role.getGang().getJobTitle(role).getDes());
				} else {
					message.write("gangName", "");
					message.write("gangJob", GangJobTitle.NULL.getDes());
				}

				// ======协议发送======
				for (Long roleId : role.getMapInfo().getBaseMap().allInMap()) {
					if (ServerManager.instance().isOnline(roleId)) {
						Role online = ServerManager.instance().getOnlinePlayer(roleId);

						if (online != null && online.getChannel() != null && online.isLogon()) {
							online.getChannel().write(llpMessage);
						}
					}
				}

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新周围玩家帮派信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	public static void refreshNearbyShenbing(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshNearbyShenbing");

				LlpMessage message = llpMessage.write("aroundShenbingInfoList");
				message.write("roleId", role.getId());

				Equip equip = role.getEquipments().get(EEquip.shenbing);
				if (equip == null) {
					message.write("shenbingId", 0);
				} else {
					message.write("shenbingId", equip.getId());
				}
				// ======协议发送======
				for (Long roleId : role.getMapInfo().getBaseMap().allInMap()) {
					if (ServerManager.instance().isOnline(roleId)) {
						Role online = ServerManager.instance().getOnlinePlayer(roleId);

						if (online != null && online.getChannel() != null && online.isLogon()) {
							online.getChannel().write(llpMessage);
						}
					}
				}

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新周围玩家神兵信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	public static void refreshNearbyShizhuang(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshNearbyShizhuang");

				LlpMessage message = llpMessage.write("aroundShizhuangInfoList");
				message.write("roleId", role.getId());

				Equip equip = role.getEquipments().get(EEquip.shizhuang);
				if (equip == null) {
					message.write("shizhuangId", 0);
				} else {
					message.write("shizhuangId", 1);
				}
				// ======协议发送======
				for (Long roleId : role.getMapInfo().getBaseMap().allInMap()) {
					if (ServerManager.instance().isOnline(roleId)) {
						Role online = ServerManager.instance().getOnlinePlayer(roleId);

						if (online != null && online.getChannel() != null && online.isLogon()) {
							online.getChannel().write(llpMessage);
						}
					}
				}

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新周围玩家时装信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}
