package iyunu.NewTLOL.net.protocol.logon;

import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class Logout extends TLOLMessageHandler {

	private int result = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		try {
			RoleManager.logout(online, "下线协议");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_logout");
			message.write("result", result);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

	}

}
