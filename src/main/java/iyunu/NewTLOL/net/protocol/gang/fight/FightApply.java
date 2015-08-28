package iyunu.NewTLOL.net.protocol.gang.fight;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.ChatMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 帮派战报名
 * @author LuoSR
 * @date 2014年6月23日
 */
public class FightApply extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "报名成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "报名成功";

		if (online.getGangId() == 0) {
			result = 1;
			reason = "无帮派";
			return;
		}

//		if (!CopyOfGangFightManager.报名状态) {
//			result = 1;
//			reason = "现在不是报名时间";
//			return;
//		}

		// 检查帮派是否解散
		if (!GangManager.instance().map.containsKey(online.getGangId())) {
			Chat chat = new Chat(EChat.system, 0, "帮派", "您所在的帮派已解散！");
			ChatMessage.sendChat(online, chat);
			online.setGangId(0);
//			online.setJobTitle(GangJobTitle.NULL);
			online.setTribute(0);
			online.setTotalTribute(0);
			result = 1;
			reason = "帮派已被解散";
			return;
		}

		Gang gang = GangManager.instance().getGang(online.getGangId());
		if (gang == null) {
			result = 1;
			reason = "帮派不存在";
			return;
		}

		if (gang.getLeader() != online.getId() && !gang.getViceLeader().contains(online.getId())) {
			result = 1;
			reason = "只有帮战或副帮主才能报名";
			return;
		}

		if (GangFightManager.报名列表.contains(gang.getId())) {
			result = 1;
			reason = "帮派已报名";
			return;
		}

		if (gang.getLevel() < 2) {
			result = 1;
			reason = "二级以上帮派才能报名";
			return;
		}

		if (!RoleServer.costGold(online, 1000, EGold.fightApply)) {
			result = 1;
			reason = "";
			SendMessage.refreshNoCoin(online, 2);
			return;
		}

		GangFightManager.报名(gang);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_fightApply");
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
