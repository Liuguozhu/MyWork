package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.enumeration.common.EGang;
import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.ChatMessage;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 解散帮派
 * 
 * @author fenghaiyu
 * 
 */
public class DelGang extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "解散帮派成功";

	@Override
	public void handleReceived(LlpMessage msg) {

		result = 0;
		reason = "解散帮派成功";

		if (online.getGangId() == 0) {
			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}

		// 检查帮派是否解散
		if (!GangManager.instance().map.containsKey(online.getGangId())) {
			Chat chat = new Chat(EChat.system, 0, "帮派", "您所在的帮派已解散！");
			ChatMessage.sendChat(online, chat);
			online.setGangId(0);
//			online.setJobTitle(GangJobTitle.NULL);
			// online.setGangName("");
			online.setGang(null);
			online.setTribute(0); // 清除个人帮贡
			online.setTotalTribute(0);
			online.setGangActivity(0); // 清除个人帮派活跃值
			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}

		if (!(GangManager.instance().getGang(online.getGangId()) != null && online.getId() == GangManager.instance().getGang(online.getGangId()).getLeader())) {
			result = 1;
			reason = "操作失败,无权限！";
			return;
		}

		if (GangFightManager.GANG_FIGHT_STATE != 2) {
			result = 1;
			reason = "您的帮派已参加帮战活动，活动期间不可以解散帮派！";
			return;
		}

		Gang gang = GangManager.instance().map.get(online.getGangId());
		if (gang != null) {
			// 解散帮派
			gang.delGang();
		}

		LogManager.gang(online.getId(), online.getUserId(), online.getGangId(), gang.getId(), gang.getName(), 0, 0, EGang.解散帮派.ordinal());
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_delGang");
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
