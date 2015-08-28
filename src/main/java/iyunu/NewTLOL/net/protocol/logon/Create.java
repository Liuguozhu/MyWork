package iyunu.NewTLOL.net.protocol.logon;

import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.manager.IlllegalWordManager;
import iyunu.NewTLOL.manager.OperationManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.base.PayBackInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class Create extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "创建角色成功";

		String nick = msg.readString("nick");
		String userId = msg.readString("userId");
		long figure = msg.readLong("figure");
		int serverId = msg.readInt("serverId");
		int guildId = msg.readInt("guildId");
		int change = msg.readInt("change");

		// 检查名称是否过长
		int nickLength = nick.length();
		if (nickLength < 2 || nickLength > 6) {
			reason = "角色名称长度过" + (nickLength < 2 ? "短" : "长") + "！";
			result = 1;
			return;
		}

		// 检查名字是否由汉字，字母，数字组成
		if (!IlllegalWordManager.match(nick)) {
			reason = "角色名称只能由汉字，字母，数字组成";
			result = 1;
			return;
		}

		// 检查是否有非法字符
		String str = IlllegalWordManager.instance().existStr(nick);
		if (str != null) {
			reason = "角色名包含非法字符[" + str + "]，请您更换！";
			result = 1;
			return;
		}

		nick = "s" + serverId + "." + nick;
		if (ServerManager.instance().checkNick(nick) || this.getRoleService().checkNick(nick)) {
			result = 1;
			reason = "角色名已存在";
			return;
		}

		long oldRoleId = this.getRoleService().queryRoleId(userId, serverId);
		if (oldRoleId != 0) {
			result = 1;
			reason = "已有角色";
			return;
		}

		// ======插入角色，分配角色ID======
		Role role = RoleServer.newRole(nick, figure, serverId, userId, guildId);
		if (role == null) {
			result = 1;
			reason = "角色形象选择错误";
			return;
		}
		long roleId = redisCenter.getRoleId();
		role.setId(roleId);

		this.getRoleService().insert(role);
		LogManager.roleCreate(role); // 创建角色日志
		// ======角色登录======
		role = roleService.query(roleId);
		if (role == null) {
			result = 1;
			reason = "登录失败！角色信息异常！";
			return;
		}

		roleService.initRole(role, change, channel);
		Logon.testCode(role);
		online = role;

		// 内测补偿
		if (OperationManager.PAY_BACK) {
			PayBackInfo payBackInfo = payBackService.query(userId);
			if (payBackInfo != null) {
				int money = payBackInfo.getAmt() * payBackInfo.getMultiple() * 10;
				online.setMoney(money);
				LogManager.money(role, EMoney.payBack.ordinal(), money, 1);
				payBackService.delete(userId);
			}
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_create");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0) {
			RoleServer.sendLogonMsg(online);
		}
	}
}
