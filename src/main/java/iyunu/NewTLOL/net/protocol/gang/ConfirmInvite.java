package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.gang.GangJobTitle;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.gang.GangServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Time;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 确认邀请帮派
 * 
 * @author fenghaiyu
 * 
 */
public class ConfirmInvite extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "成功";

	@Override
	public void handleReceived(LlpMessage msg) {

		long gangId = msg.readLong("gangId");
		int type = msg.readInt("type");
		long roleId = msg.readLong("roleId");
		// 1拒绝0同意
		result = 0;
		reason = "成功";
		Role role = ServerManager.instance().getOnlinePlayer(roleId);
		if (type == 1) {
			if (role != null) {
				GangMessage.sendConfirmInvite(role, online.getNick() + " 拒绝了你的帮派邀请");
			}
		}
		if (type == 0) {
			Gang gang = GangManager.instance().getGang(gangId);
			if (gang == null) {
				result = 1;
				reason = "要加入的帮派不存在";
				return;
			}
			if (gang.getMembers().size() >= gang.getSize()) {
				result = 1;
				reason = "要加入的帮派人数已满";
				return;
			}
			if (!Time.beforeYesterday(online.getLeaveGangTime())) {
				result = 1;
				reason = "退出帮派当天不可以申请加入其它帮派！";
				return;
			}
			GangMessage.sendConfirmInvite(role, online.getNick() + " 同意了你的帮派邀请");
			gang.addMembers(online.toCard());

			GangServer.jionGang(online, gang, GangJobTitle.Member);

			GangManager.instance().sendGangWarn(online, "您已加入帮派！");
			GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(online.getNick()) + "加入帮派！");
			MapManager.instance().addGangStateQueue(online);
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_confirmInvite");
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
