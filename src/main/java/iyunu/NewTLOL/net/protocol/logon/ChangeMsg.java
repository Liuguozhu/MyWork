package iyunu.NewTLOL.net.protocol.logon;

import iyunu.NewTLOL.manager.IlllegalWordManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Util;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class ChangeMsg extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "修改成功";

		String userName = msg.readString("userName").toLowerCase();
		String password = msg.readString("password");
		if (online.getChange() == 1) {
			result = 1;
			reason = "修改失败，您不是快速登录或者已修改过帐户信息！";
			return;
		}

		if (userName.length() < 6 || userName.length() > 20) {
			result = 1;
			reason = "修改失败，用户名格式错误！";
			return;
		}
		if (password.length() < 6 || password.length() > 20) {
			result = 1;
			reason = "修改失败，密码格式错误！";
			return;
		}
		if (!Util.matchWithoutZn(userName)) {
			result = 1;
			reason = "修改失败，名称不能含有特殊字符！";
			return;
		}
		if (!Util.matchWithoutZn(password)) {
			result = 1;
			reason = "修改失败，密码不能含有特殊字符！";
			return;
		}
		if (IlllegalWordManager.instance().exist(userName)) {
			result = 1;
			reason = "修改失败，非法名称！";
			return;
		}
		if (userName.startsWith("iyunu")) {
			result = 1;
			reason = "修改失败，非法名称";
			return;
		}
		if (this.getUserService().checkName(userName)) {
			result = 1;
			reason = "用户名已存在";
			return;
		}

		online.setChange(1); // 记录已修改帐户信息
		this.getUserService().update(online.getUserId(), userName, password);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_changeMsg");
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
