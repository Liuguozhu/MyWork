package iyunu.NewTLOL.message;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class TeamMessage {

	/**
	 * 发送队伍状态信息
	 * 
	 * @param role
	 *            角色对象
	 * @param state
	 *            队伍状态
	 */
	public static void sendTeamState(Role role, int state) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_teamState");
				llpMessage.write("state", state);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送队伍状态信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 给所有队伍成员发送队伍信息
	 * 
	 * @param team
	 *            队伍对象
	 */
	public static void sendTeamAllMsg(Team team) {

		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_teamInfo");
			llpMessage.write("teamId", team.getId());
			llpMessage.write("teanName", team.getName());

			ArrayList<Role> members = team.getMember();
			for (int j = 0; j < members.size(); j++) {
				LlpMessage teamerInfo = llpMessage.write("teamerInfoList");
				Role member = members.get(j);
				teamerInfo.write("id", member.getId());
				teamerInfo.write("level", member.getLevel());
				teamerInfo.write("nick", member.getNick());
				teamerInfo.write("power", 0);
				teamerInfo.write("position", j);
				teamerInfo.write("figure", member.getFigure());
				teamerInfo.write("vocation", member.getVocation().getName());
				
				Equip equip = member.getEquipments().get(EEquip.shizhuang);
				if (equip == null) {
					teamerInfo.write("shizhuangId", 0);
				} else {
					teamerInfo.write("shizhuangId", 1);
				}
				
				
			}

			for (Role role : members) {
				if (role != null && role.getChannel() != null && role.isLogon()) {
					role.getChannel().write(llpMessage);
				}
			}

		} catch (Exception e) {
			LogManager.info("异常报告：发送全体队伍信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}

	}

	/**
	 * 发送队伍信息
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendTeamMsg(Role role, Team team) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_teamInfo");
				llpMessage.write("teamId", team.getId());
				llpMessage.write("teanName", team.getName());

				ArrayList<Role> members = team.getMember();
				for (int i = 0; i < members.size(); i++) {
					LlpMessage teamerInfo = llpMessage.write("teamerInfoList");
					Role member = members.get(i);
					teamerInfo.write("id", member.getId());
					teamerInfo.write("level", member.getLevel());
					teamerInfo.write("nick", member.getNick());
					teamerInfo.write("power", 0);
					teamerInfo.write("position", i);
					teamerInfo.write("figure", member.getFigure());
					teamerInfo.write("vocation", member.getVocation().getName());
					
					Equip equip = member.getEquipments().get(EEquip.shizhuang);
					if (equip == null) {
						teamerInfo.write("shizhuangId", 0);
					} else {
						teamerInfo.write("shizhuangId", 1);
					}
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送队伍信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}

	}

	/**
	 * @function 队伍通知
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param reason
	 *            通知
	 * @date 2014年5月23日
	 */
	public static void teamInform(Role role, String reason) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_teamInform");
				llpMessage.write("reason", reason);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：队伍通知");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	public static void refreshLeaderSite(Role role, Team team) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_coord");
			llpMessage.write("x", team.getLeader().getMapInfo().getX());
			llpMessage.write("y", team.getLeader().getMapInfo().getY());

			if (role != null && role.getId() != team.getLeader().getId() && role.getChannel() != null) { // 只给队员同步坐标即可
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：同步队长坐标");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 同步队长坐标
	 * 
	 * @param team
	 *            队伍
	 */
	public static void refreshLeaderSite(Team team) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_coord");
			llpMessage.write("x", team.getLeader().getMapInfo().getX());
			llpMessage.write("y", team.getLeader().getMapInfo().getY());

			for (Role role : team.getMember()) {
				if (role != null && role.getId() != team.getLeader().getId() && role.getChannel() != null && ServerManager.instance().isOnline(role.getId())) { // 只给队员同步坐标即可
					role.getChannel().write(llpMessage);
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：同步队长坐标");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}

	}
}
