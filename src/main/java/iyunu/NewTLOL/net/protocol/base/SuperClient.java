package iyunu.NewTLOL.net.protocol.base;

import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class SuperClient extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "登录成功";

		int serverId = msg.readInt("serverId");

		// ======验证服务器编号======
		if (!ServerManager.instance().checkServerId(serverId)) {
			result = 2;
			reason = "验证失败登录异常！服务器选择错误！";
			return;
		}

		if (!ServerManager.isRunning()) {
			result = 2;
			reason = "登录失败，服务器维护中！！！";
			return;
		}

		long roleId = msg.readLong("roleId");

		if (ServerManager.instance().isOnline(roleId)) { // 如果在线，则踢人，需要重新登录
			RoleManager.logout(ServerManager.instance().getOnlinePlayer(roleId), "超级客户端登录踢人下线");
			result = 2;
			reason = "登录异常！请重新登录！";
			return;
		}

		Role role = roleService.query(roleId);
		if (role == null) {
			result = 1;
			reason = "登录失败！角色信息异常！";
			return;
		}

		if (role.getState() == 1) {
			result = 1;
			reason = "无角色";
			return;
		}

		role.setChannel(channel);
		role.setSrv(ServerManager.instance().getServer());
		role.setLogon(true);
		// ======保存在线角色信息======
		ServerManager.instance().online(role.getChannel(), role);

		online = role;
	}

	@Override
	public void handleReply() throws Exception {

		if (result == 0) {
			LlpMessage message = null;
			try {
				message = LlpJava.instance().getMessage("s_superClient");
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
}