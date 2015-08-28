package iyunu.NewTLOL.net.protocol.chat;

import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 聊天发送VIP开关
 * @author LuoSR
 * @date 2014年9月1日
 */
public class OpenVipChat extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "发送成功！";

	@Override
	public void handleReceived(LlpMessage msg) {

		// ======数据重置======
		result = 0;
		reason = "发送成功！";

		int openVipChat = msg.readInt("openVipChat");

		online.setOpenVipChat(openVipChat);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage ackChatSend = null;
		try {
			ackChatSend = LlpJava.instance().getMessage("s_openVipChat");
			ackChatSend.write("result", result);
			ackChatSend.write("reason", reason);
			channel.write(ackChatSend);
		} finally {
			if (ackChatSend != null) {
				ackChatSend.destory();
			}
		}
	}
}
