package iyunu.NewTLOL.message;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.map.instance.MapRaidsInfo;
import iyunu.NewTLOL.model.raids.instance.RaidsTeamInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class RaidsMessage {

	/**
	 * 刷新副本状态
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshRaids(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refresh_raids");
				llpMessage.write("floorMax", role.getRaidsTeamInfo().getRaidsInfo().getFloorMax()); // 总层数
				llpMessage.write("state", role.getRaidsTeamInfo().getRaidsState()); // 整个副本状态（0.未通关，1.通关）
				llpMessage.write("floor", role.getRaidsTeamInfo().getRaidsFloor().getFloor()); // 当前层数
				llpMessage.write("floorState", role.getRaidsTeamInfo().getRaidsFloor().getState()); // 本层状态（0.未通关，1.通关）
				MapRaidsInfo mapRaidsInfo = (MapRaidsInfo) role.getMapInfo().getBaseMap();
				llpMessage.write("index", mapRaidsInfo.getIndex());
				llpMessage.write("cellState", mapRaidsInfo.getState()); // 状态（0.未通关，1.通关）
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：通知客户端副本信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	public static void refreshRaids(MapRaidsInfo mapRaidsInfo) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_refresh_raids");
			llpMessage.write("floorMax", mapRaidsInfo.getRaidsTeamInfo().getRaidsInfo().getFloorMax()); // 总层数
			llpMessage.write("state", mapRaidsInfo.getRaidsTeamInfo().getRaidsState()); // 整个副本状态（0.未通关，1.通关）
			llpMessage.write("floor", mapRaidsInfo.getRaidsTeamInfo().getRaidsFloor().getFloor()); // 当前层数
			llpMessage.write("floorState", mapRaidsInfo.getRaidsTeamInfo().getRaidsFloor().getState()); // 本层状态（0.未通关，1.通关）
			llpMessage.write("index", mapRaidsInfo.getIndex());
			llpMessage.write("cellState", mapRaidsInfo.getState()); // 状态（0.未通关，1.通关）

//			System.out.println("floorMax======>" + mapRaidsInfo.getRaidsTeamInfo().getRaidsInfo().getFloorMax());
//			System.out.println("state======>" + mapRaidsInfo.getRaidsTeamInfo().getRaidsState());
//			System.out.println("floor======>" + mapRaidsInfo.getRaidsTeamInfo().getRaidsFloor().getFloor());
//			System.out.println("floorState======>" + mapRaidsInfo.getRaidsTeamInfo().getRaidsFloor().getState());
//			System.out.println("index======>" + mapRaidsInfo.getIndex());
//			System.out.println("cellState======>" + mapRaidsInfo.getState());

			for (Long roleId : mapRaidsInfo.allInMap()) {
				if (ServerManager.instance().isOnline(roleId)) {
					Role role = ServerManager.instance().getOnlinePlayer(roleId);
//					System.out.println("roleId=" + roleId);
					role.getChannel().write(llpMessage);
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：通知客户端副本信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 小地图
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshMiniMap(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null) {
				llpMessage = LlpJava.instance().getMessage("s_refresh_miniMap");
				List<MapRaidsInfo> list = role.getRaidsTeamInfo().getRaidsFloor().getMapRaidsInfos();
				for (int i = 0; i < list.size(); i++) {
					MapRaidsInfo mapRaidsInfo = list.get(i);
					LlpMessage msg = llpMessage.write("raidsCellInfoList");
					msg.write("index", i);
					msg.write("state", mapRaidsInfo.getState());
					msg.write("number", mapRaidsInfo.numberOnMap());
				}
				role.getChannel().write(llpMessage);
			}

		} catch (Exception e) {
			LogManager.info("异常报告：通知客户端副本信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	public static void refreshMiniMap(RaidsTeamInfo raidsTeamInfo) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_refresh_miniMap");
			List<MapRaidsInfo> list = raidsTeamInfo.getRaidsFloor().getMapRaidsInfos();
			for (int i = 0; i < list.size(); i++) {
				MapRaidsInfo mapRaidsInfo = list.get(i);
				LlpMessage msg = llpMessage.write("raidsCellInfoList");
				msg.write("index", i);
				msg.write("state", mapRaidsInfo.getState());
				msg.write("number", mapRaidsInfo.numberOnMap());
			}
			for (Long roleId : raidsTeamInfo.getMemberIds()) {
				if (ServerManager.instance().isOnline(roleId)) {
					Role role = ServerManager.instance().getOnlinePlayer(roleId);
					role.getChannel().write(llpMessage);
				}
			}

		} catch (Exception e) {
			LogManager.info("异常报告：通知客户端副本信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

}
