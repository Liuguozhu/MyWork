package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.enumeration.common.EGang;
import iyunu.NewTLOL.manager.IOProcessManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.gang.GangJobTitle;
import iyunu.NewTLOL.model.io.gang.GangIOTask;
import iyunu.NewTLOL.model.io.gang.instance.GangUpdateTask;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.gang.GangServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 1 提升为副帮主，2提升为长老，3踢人,4转让帮主,5副帮主降长老6 副帮主降帮众7长老降帮众
 * 
 * @author fenghaiyu
 * 
 */
public class GangOperate extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "操作成功！";

	@Override
	public void handleReceived(LlpMessage msg) {
		int type = msg.readInt("type"); // 1.提升为副帮主，2.提升为长老，3.踢人，4.转让帮主，5.副帮主降长老，6.副帮主降帮众，7.长老降帮众
		long beRoleId = msg.readLong("roleId");// 被操作者的ID
		result = 0;
		reason = "操作成功";

		if (online.getGang() == null) {
			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}

		if (type <= 0 || type > 7) {
			result = 1;
			reason = "非法的操作！";
			return;
		}

		Gang gang = GangManager.instance().map.get(online.getGangId());
		Role beRole = ServerManager.instance().getOnlinePlayer(beRoleId);

		if (!gang.isInGang(beRoleId)) {
			result = 1;
			reason = "被操作的角色不在帮派中！";
			return;
		}

		GangJobTitle gangJobTitle = gang.getJobTitle(online); // 操作者帮派职位
		GangJobTitle beGangJobTitle = gang.getJobTitle(beRoleId); // 被操作者帮派职位

		switch (type) {
		case 1: // 提升副帮主

			if (gangJobTitle != GangJobTitle.Leader) {
				result = 1;
				reason = "权限不足";
				return;
			}

			if (beGangJobTitle.ordinal() <= 1) {
				result = 1;
				reason = "对方已是副帮主以上级别！";
				return;
			}

			if (gang.getViceLeader().size() > 1) {
				result = 1;
				reason = "已有副帮主，不可提升！";
				return;
			}

			if (gang.getPresbyter().contains(beRoleId)) { // 如果原来是长老，从长老列表中删除
				gang.getPresbyter().remove(beRoleId);
			}
			gang.addViceLeader(beRoleId); // 添加至副帮主列表

			if (beRole != null) {
				GangMessage.sendJoinGang(beRole);
				GangManager.instance().sendGangWarn(beRole, "你已被提升为副帮主！");
				GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(beRole.getNick()) + "被提升为副帮主 ！");
			} else {
				RoleCard roleCard = RoleManager.getRoleCardByRoleId(beRoleId);
				if (roleCard != null) {
					GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(roleCard.getNick()) + "被提升为副帮主 ！");
				}
			}

			break;
		case 2: // 提升长老

			if (beGangJobTitle.ordinal() <= 2) {
				result = 1;
				reason = "对方已是长老以上级别！";
				return;
			}

			if (gangJobTitle.compareTo(beGangJobTitle) >= 0) {
				result = 1;
				reason = "权限不足";
				return;
			}

			if (gang.getPresbyter().size() > 2) {
				result = 1;
				reason = "长老职位已满，不可提升！";
				return;
			}

			gang.addPresbyter(beRoleId);

			if (beRole != null) {
				GangMessage.sendJoinGang(beRole);
				GangManager.instance().sendGangWarn(beRole, "你已被提升为长老！");
				GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(beRole.getNick()) + "被提升为长老！");
			} else {
				RoleCard roleCard = RoleManager.getRoleCardByRoleId(beRoleId);
				if (roleCard != null) {
					GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(roleCard.getNick()) + "被提升为长老！");
				}
			}

			break;
		case 3: // 踢出帮派
			if (gangJobTitle.ordinal() >= beGangJobTitle.ordinal()) {
				result = 1;
				reason = "权限不足";
				return;
			}

			if (GangFightManager.GANG_FIGHT_STATE != 2) {
				result = 1;
				reason = "帮派战比赛期间不可以逐出成员！";
				return;
			}

			gang.del(beRoleId);

			if (beRole != null) {
				// 退出帮派，更新个人信息
				GangServer.quitGang(beRole);
				GangManager.instance().sendGangWarn(beRole, "你已被踢出帮派！");
				GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(beRole.getNick()) + "被逐出帮派 ！");
			} else {
				RoleCard roleCard = RoleManager.getRoleCardMap().get(beRoleId);
				roleCard.setGangId(0);
				roleCard.setTribute(0); // 清除个人帮贡
				roleCard.setTotalTribute(0);
				roleCard.setGangActivity(0); // 清除个人帮派活跃值
				roleCard.setGang(null);
				roleCard.setLeaveGangTime(System.currentTimeMillis());
				GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(roleCard.getNick()) + "被逐出帮派 ！");
			}
			GangMessage.refreshGangNum(gang);

			break;
		case 4: // 转让帮主

			if (gang.getViceLeader().contains(beRoleId)) {
				gang.getViceLeader().remove(beRoleId);
			}

			if (gang.getPresbyter().contains(beRoleId)) {
				gang.getPresbyter().remove(beRoleId);
			}

			if (beRole != null) {
				gang.setLeader(beRoleId);
				gang.setLeaderName(beRole.getNick());
				GangMessage.sendJoinGang(beRole);
				GangManager.instance().sendGangWarn(beRole, "你被升为帮主！");
				GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(beRole.getNick()) + "被升为帮主！");
				GangMessage.sendJoinGang(online);
			} else {
				RoleCard roleCard = RoleManager.getRoleCardByRoleId(beRoleId);
				if (roleCard != null) {
					gang.setLeader(beRoleId);
					gang.setLeaderName(roleCard.getNick());
					GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(roleCard.getNick()) + "被升为帮主！");
					GangMessage.sendJoinGang(online);
				} else {
					result = 1;
					reason = "操作错误！";
					return;
				}
			}

			break;
		case 5: // 副帮主降长老

			if (gangJobTitle != GangJobTitle.Leader) {
				result = 1;
				reason = "没有权限！";
				return;
			}
			if (gang.getPresbyter().size() > 2) {
				result = 1;
				reason = "长老职位已满，不可降级！";
				return;
			}

			gang.getViceLeader().remove(beRoleId);
			gang.addPresbyter(beRoleId);

			if (beRole != null) {
				GangMessage.sendJoinGang(beRole);
				GangManager.instance().sendGangWarn(beRole, "你已被降为长老！");
				GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(beRole.getNick()) + "被降为长老！");
			} else {
				RoleCard roleCard = RoleManager.getRoleCardByRoleId(beRoleId);
				if (roleCard != null) {
					GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(roleCard.getNick()) + "被降为长老！");
				}
			}
			break;
		case 6: // 副帮主降帮众

			if (gangJobTitle != GangJobTitle.Leader) {
				result = 1;
				reason = "没有权限！";
				return;
			}

			gang.getViceLeader().remove(beRoleId);

			if (beRole != null) {
				GangMessage.sendJoinGang(beRole);
				GangManager.instance().sendGangWarn(beRole, "你已被降为帮众！");
				GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(beRole.getNick()) + "被降为帮众 ！");
			} else {
				RoleCard roleCard = RoleManager.getRoleCardByRoleId(beRoleId);
				if (roleCard != null) {
					GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(roleCard.getNick()) + "被降为帮众 ！");
				}
			}
			break;
		case 7: // 长老降帮众

			if (gangJobTitle.ordinal() > 1) {
				result = 1;
				reason = "没有权限！";
				return;
			}

			gang.getPresbyter().remove(beRoleId);

			if (beRole != null) {
				GangMessage.sendJoinGang(beRole);
				GangManager.instance().sendGangWarn(beRole, "你已被降为帮众！");
				GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(beRole.getNick()) + "被降为帮众 ！");
			} else {
				RoleCard roleCard = RoleManager.getRoleCardByRoleId(beRoleId);
				if (roleCard != null) {
					GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(roleCard.getNick()) + "被降为帮众 ！");
				}
			}
			break;
		default:
			break;
		}

		GangIOTask task = new GangUpdateTask(gang);
		IOProcessManager.instance().addGangTask(task);
		LogManager.gang(online.getId(), online.getUserId(), online.getGangId(), beRoleId, "", 0, type, EGang.帮派操作.ordinal());
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_gangOperate");
			message.write("result", result);
			message.write("reason", reason);

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
