package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.json.GangJson;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.ChatMessage;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 客户端请求帮派主要信息
 * 
 * @author fenghaiyu
 * 
 */
public class QueryGangMain extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "请求帮派信息成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "请求帮派信息成功";

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
			online.setTribute(0);
			online.setTotalTribute(0);
			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}

		// 如果人身上有帮派ID，帮派里没有此成员，这种情况只在强行停服时出现，roleCardMap没有存储，所以人物上线gangId没有被覆盖
		// roleCard中其它都不是关键信息
		if (GangManager.instance().map.containsKey(online.getGangId())) {
			boolean flag = false;
			List<RoleCard> list = GangManager.instance().map.get(online.getGangId()).getMembers();
			for (RoleCard roleCard : list) {
				if (roleCard.getId() == online.getId()) {
					flag = true;
				}
			}
			if (!flag) {
				Chat chat = new Chat(EChat.system, 0, "帮派", "您已无帮派~~！");
				ChatMessage.sendChat(online, chat);
				online.setGangId(0);
//				online.setJobTitle(GangJobTitle.NULL);
				online.setTribute(0);
				online.setTotalTribute(0);
				result = 1;
				reason = "操作失败,无帮派！";
				return;
			}
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_gangMain");
			message.write("result", result);
			message.write("reason", reason);
			if (result == 0) {
				Gang gang = GangManager.instance().map.get(online.getGangId());
				message.write("gangId", gang.getId());
				message.write("name", gang.getName());
				message.write("leaderId", gang.getLeader());
				message.write("leaderName", gang.getLeaderName());
				message.write("level", gang.getLevel());
				int nextExp = 0;
				if (gang.getLevel() < 5) {
					nextExp = GangJson.instance().getGangLevelByIndex(gang.getLevel() + 1).getExp();
				}
				message.write("exp", gang.getTotalTribute() + "/" + nextExp);
				message.write("size", gang.getSize());
				message.write("num", gang.getMembers().size());
				message.write("gangBulletin", gang.getGangBulletin());
				message.write("active", gang.getActive());
			}

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
