package iyunu.NewTLOL.net.protocol.chat;

import iyunu.NewTLOL.manager.IlllegalWordManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.chat.ChatServer;
import iyunu.NewTLOL.util.Time;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class WhisperSend extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "发送成功！";

	@Override
	public void handleReceived(LlpMessage msg) {

		// ======数据重置======
		result = 0;
		reason = "发送成功！";

		// ======获取参数======
		long receiverId = msg.readLong("receiverId");
		String content = msg.readString("content");

		Role receiver = ServerManager.instance().getOnlinePlayer(receiverId);
		if (receiver == null) {
			result = 1;
			reason = "发送失败，对方不在线！";
			return;
		}
		
		if (receiver.isCloseWhisper()) {
			result = 1;
			reason = "发送失败，对方已屏蔽私聊！";
			return;
		}

		content = IlllegalWordManager.instance().checkString(content); // 替换非法字符

		String time = Time.getTimeToStr(System.currentTimeMillis());

		Chat chat = new Chat(online.getId(), online.getNick(), receiverId, receiver.getNick(), content, time);

		if (online.getOpenVipChat() == 0) {
			chat.setVip(online.getVip().getLevel().ordinal());
		}

		ChatServer.getChatRoles(EChat.whisper, online, receiver, chat);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage ackChatSend = null;
		try {
			ackChatSend = LlpJava.instance().getMessage("s_whisperSend");
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
