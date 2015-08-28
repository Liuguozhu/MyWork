package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.chat.ChatServer;
import iyunu.NewTLOL.util.Time;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 邀请帮派
 * 
 * @author fenghaiyu
 * 
 */
public class InviteGang extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "邀请成功";

	@Override
	public void handleReceived(LlpMessage msg) {

		long roleId = msg.readLong("roleId");
		result = 0;
		reason = "邀请成功";

		// 检查帮派是否解散
		if (!GangManager.instance().map.containsKey(online.getGangId())) {

			ChatServer.sendSystem(online, "帮派系统", "您所在的帮派已解散！");
			online.setGangId(0);
//			online.setJobTitle(GangJobTitle.NULL);
			online.setTribute(0);
			online.setTotalTribute(0);
			GangMessage.sendJoinGang(online); // 刷新角色帮派信息

			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}

		if (online.getGang() == null) {
			result = 1;
			reason = "您没有帮派！";
			return;
		}

		if (online.getGang().getJobTitle(online).ordinal() > 2) {
			result = 1;
			reason = "无操作权限";
			return;
		}
		
		if (!ServerManager.instance().isOnline(roleId)) {
			result = 1;
			reason = "玩家不在线";
			return;
		}
		Role role = ServerManager.instance().getOnlinePlayer(roleId);
		if (role.getGangId() != 0) {
			result = 1;
			reason = "对方已有帮派";
			return;
		}
		if (!Time.beforeYesterday(online.getLeaveGangTime())) {
			result = 1;
			reason = "对方，退出帮派当天不可以加入其它帮派！";
			return;
		}
		if (GangManager.instance().getGang(online.getGangId()).getMembers().size() >= GangManager.instance().getGang(online.getGangId()).getSize()) {
			result = 1;
			reason = "帮派人数已满，升级帮派会增加帮派人数上限！";
			return;
		}

		GangMessage.sendInvite(role, online.getGang().getName(), online.getNick(), online.getGangId(), online.getId());
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_inviteGang");
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
