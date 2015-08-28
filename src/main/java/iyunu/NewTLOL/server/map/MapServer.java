package iyunu.NewTLOL.server.map;

import iyunu.NewTLOL.json.BloodJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.message.MapMessage;
import iyunu.NewTLOL.message.RaidsMessage;
import iyunu.NewTLOL.model.blood.ReBornRes;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.EMapType;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.team.Team;

public class MapServer {

	/**
	 * 改变地图
	 * 
	 * @param role
	 *            角色对象
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	public static void changeMap(Role role, int x, int y, BaseMap newMap, BaseMap oldMap) {

		if (MapManager.checkCoord(newMap, x, y)) {
			role.setAround(false);
			oldMap.remove(role);

			MapManager.instance().changeGrid(role, newMap, x, y, x, y); // 改变所在格子

			MapMessage.sendMap(role, newMap);
			if (role.getRaidsTeamInfo() != null) {

				if (role.getMapInfo().isMapRaids()) {
					RaidsMessage.refreshRaids(role); // 刷新副本信息
				} else {
					role.setRaidsTeamInfo(null);
				}

			}
			role.getMapInfo().getMapAgent().clear();
		}
	}

	/**
	 * 组队过地图
	 * 
	 * @param team
	 *            队伍
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param mapInfo
	 *            地图对象
	 */
	public static void changeMap(Team team, int x, int y, BaseMap mapInfo, BaseMap oldMapInfo) {

		if (oldMapInfo.allTeamInMap().contains(team.getId())) {
			oldMapInfo.removeTeam(Integer.valueOf(team.getId()));
		}

		if (!mapInfo.allTeamInMap().contains(team.getId())) {
			mapInfo.addTeam(team.getId());
		}

		for (Role role : team.getMember()) {
			MapServer.changeMap(role, x, y, mapInfo, oldMapInfo);
		}

		// TeamMessage.refreshLeaderSite(team);
	}

	/**
	 * 检查角色所在地图
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void checkMap(Role role) {
		// 如果处于血战状态,重新登录，传送回血战复活点
		if (ActivityManager.BLOOD_STATE && role.getBlood() != 0) {
			ReBornRes r = BloodJson.instance().getReBorn().get(role.getBlood());
			role.getMapInfo().setBaseMap(MapManager.instance().getMapById(r.getMap()));
			role.getMapInfo().setMapId(r.getMap());
			role.getMapInfo().setX(r.getX());
			role.getMapInfo().setY(r.getY());
			return;
		}
		BaseMap baseMap = role.getMapInfo().getBaseMap();
		if (baseMap == null || !baseMap.getType().equals(EMapType.common)) { // 重置地图和坐标
			role.getMapInfo().setBaseMap(MapManager.instance().getMapById(RoleManager.BASE_MAP)); // 设置地图信息
			role.getMapInfo().setMapId(RoleManager.BASE_MAP); // 初始地图编号
			role.getMapInfo().setX(RoleManager.BASE_X); // 初始X坐标
			role.getMapInfo().setY(RoleManager.BASE_Y); // 初始Y坐标
		}
	}

	/**
	 * 是否到达指定位置
	 * 
	 * @param role
	 *            角色对象
	 * @param mapId
	 *            地图编号
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @return 到达指定位置
	 */
	public static boolean isArrive(Role role, int mapId, int x, int y) {
		if (role.getMapInfo().getMapId() == mapId && role.getMapInfo().getX() >= x - 3 && role.getMapInfo().getX() <= x + 3 && role.getMapInfo().getY() >= y - 3 && role.getMapInfo().getY() <= y + 3) {
			return true;
		}
		return false;
	}

	public static void transmit(Role role, int mapId, int x, int y) {
		BaseMap mapInfo = MapManager.instance().getMapById(mapId);
		if (mapInfo != null) {
			if (!mapInfo.getType().equals(EMapType.gangFight)) {

				if (!MapManager.checkCoord(mapInfo, x, y)) {
					x = mapInfo.getTransmitX();
					y = mapInfo.getTransmitY();
				}
				if (role.getTeam() != null) {
					MapServer.changeMap(role.getTeam(), x, y, mapInfo, role.getMapInfo().getBaseMap());
				} else {
					MapServer.changeMap(role, x, y, mapInfo, role.getMapInfo().getBaseMap());
				}
			}
		}
	}
}
