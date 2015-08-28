package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 禁言功能
 * 
 * @author fenghaiyu
 * 
 */
public class Mute extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "禁言成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		long roleId = msg.readLong("roleId");
		result = 0;
		reason = "禁言成功";

		// 城主禁言功能
		if (GangManager.championLeader != 0 && online.getId() == GangManager.championLeader) {

			if (ServerManager.instance().getOnlinePlayer(roleId) == null) {
				result = 1;
				reason = "发送失败，玩家不在线或ID错误";
				return;
			}
			if (GangManager.muteIds.contains(roleId)) {
				result = 1;
				reason = "发送失败，同一天不可重复禁言同一人";
				return;
			}
			if (GangManager.muteIds.size() >= 3) {
				result = 1;
				reason = "发送失败，同一天只可以使用3次禁言功能";
				return;
			}
			// 加禁言10分钟
			RoleServer.addMute(ServerManager.instance().getOnlinePlayer(roleId), 10);
			GangManager.muteIds.add(roleId);
		} else {
			result = 1;
			reason = "发送失败，没有权限";
			return;
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_gangMute");
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
