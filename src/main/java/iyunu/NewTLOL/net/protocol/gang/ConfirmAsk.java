package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.enumeration.common.EGang;
import iyunu.NewTLOL.manager.IOProcessManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.gang.GangJobTitle;
import iyunu.NewTLOL.model.gang.event.EGangActiveEvent;
import iyunu.NewTLOL.model.io.gang.GangIOTask;
import iyunu.NewTLOL.model.io.gang.instance.GangUpdateTask;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.chat.ChatServer;
import iyunu.NewTLOL.server.gang.GangServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 加入帮派审批
 * 
 * @author fenghaiyu
 * 
 */
public class ConfirmAsk extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "操作成功";

	@Override
	public void handleReceived(LlpMessage msg) {

		result = 0;
		reason = "操作成功";

		long roleId = msg.readLong("roleId");
		int ratify = msg.readInt("ratify");

		if (online.getGangId() == 0) {
			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}
		// 检查帮派是否解散
		if (!GangManager.instance().map.containsKey(online.getGangId())) {

			ChatServer.sendSystem(online, "帮派系统", "您所在的帮派已解散！");
			online.setGangId(0);
			// online.setJobTitle(GangJobTitle.NULL);
			online.setTribute(0);
			online.setTotalTribute(0);
			GangMessage.sendJoinGang(online); // 刷新角色帮派信息

			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}

		Gang gang = GangManager.instance().getGang(online.getGangId());
		if (gang.getJobTitle(online).ordinal() > 2) {
			result = 1;
			reason = "无操作权限";
			return;
		}

		if (ratify == 0) { // 批准加入
			Role role = ServerManager.instance().getOnlinePlayer(roleId);
			if (role != null) {
				if (role.getGangId() != 0) {
					result = 1;
					reason = "对方已加入其它帮派。";
					gang.getAskGang().remove(roleId);
					return;
				}
				if (!Time.beforeYesterday(online.getLeaveGangTime())) {
					result = 1;
					reason = "申请人，退出帮派当天不可以加入其它帮派！";
					gang.getAskGang().remove(roleId);
					role.getAskGang().remove(gang.getId());
					return;
				}

			} else {
				if (RoleManager.getRoleCardMap().get(roleId) != null) {
					if (RoleManager.getRoleCardMap().get(roleId).getGangId() != 0) {
						result = 1;
						reason = "对方已加入其它帮派。";
						gang.getAskGang().remove(roleId);
						return;
					}

					if (!Time.beforeYesterday(online.getLeaveGangTime())) {
						result = 1;
						reason = "申请人，退出帮派当天不可以加入其它帮派！";
						gang.getAskGang().remove(roleId);
						return;
					}

				} else {
					// 由于不存在RoleCard这种情况只发生在，重启服务器后，所以忽略
					result = 1;
					reason = "请求已过时。";
					gang.getAskGang().remove(roleId);
					return;
				}
			}

			if (gang.getMembers().size() >= gang.getSize()) {
				result = 1;
				reason = "人员已满。";
				gang.getAskGang().remove(roleId);
				return;
			}

			if (role != null) {
				// 添加进帮派前，刷新一下roleCard
				gang.addMembers(role.toCard());
				GangServer.jionGang(role, gang, GangJobTitle.Member);
				MapManager.instance().addGangStateQueue(role);
				GangManager.instance().sendGangWarn(role, "您已加入帮派！");
				GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(role.getNick()) + "加入帮派！");
			} else {
				RoleCard roleCard = RoleManager.getRoleCardByRoleId(roleId);
				gang.addMembers(roleCard);
				roleCard.setGang(gang);
				roleCard.setGangId(gang.getId());
				GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(roleCard.getNick()) + "加入帮派！");
			}
			gang.getAskGang().remove(roleId);

			if (role != null) { // 此人在线，清除所有申请
				role.getAskGang().clear();
			} else { // 此人不在线，清除roleCard中的所有申请
				RoleCard rc = RoleManager.getRoleCardMap().get(roleId);
				if (rc != null) {
					rc.getAskGang().clear();
				}
			}
			GangIOTask task = new GangUpdateTask(gang);
			IOProcessManager.instance().addGangTask(task);
			// 加人增加活跃值
			gang.addActive(EGangActiveEvent.加人.getActive(), EGangActiveEvent.加人.name(), online.getNick());
			GangMessage.refreshGangNum(gang);
		} else if (ratify == 1) { // 拒绝，把帮派列表清除此人

			gang.getAskGang().remove(roleId);
			Role role = ServerManager.instance().getOnlinePlayer(roleId);

			if (role != null && role.getAskGang().contains(gang.getId())) { // 此人在线清除此条申请
				role.getAskGang().remove(gang.getId());
			} else { // 此人不在线，清除roleCard中的此条申请
				RoleCard rc = RoleManager.getRoleCardMap().get(roleId);
				if (rc != null && rc.getAskGang().contains(gang.getId())) {
					rc.getAskGang().remove(gang.getId());
				}
			}

		} else { // 清空申请列表
			gang.getAskGang().clear();
		}
		LogManager.gang(online.getId(), online.getUserId(), online.getGangId(), roleId, "", 0, ratify, EGang.加人确认.ordinal());
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_confirmAsk");
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
