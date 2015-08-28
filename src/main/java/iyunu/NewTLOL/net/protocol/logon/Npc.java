package iyunu.NewTLOL.net.protocol.logon;

import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class Npc extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
		int npcId = msg.readInt("npcId");
		LogManager.info("npcId=" + npcId);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_npc");
			message.write("content", "您好，我是NPC，请问你有什么事吗？");
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}