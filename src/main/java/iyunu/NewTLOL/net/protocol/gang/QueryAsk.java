package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.ChatMessage;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.HashSet;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 请求申请加入人员的列表
 * 
 * @author fenghaiyu
 * 
 */
public class QueryAsk extends TLOLMessageHandler {

	private int result = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;

		if (online.getGangId() == 0) {
			result = 1;
			return;
		}

		// 检查帮派是否解散
		if (online.getGang() == null) {
			Chat chat = new Chat(EChat.system, 0, "帮派", "您所在的帮派已解散！");
			ChatMessage.sendChat(online, chat);
			online.setGangId(0);
			online.setTribute(0);
			online.setTotalTribute(0);
			result = 1;
			return;
		}

		if (online.getGang().getJobTitle(online).ordinal() > 2) {
			result = 1;
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_queryAsk");

			if (result == 0) {
				HashSet<Long> set = GangManager.instance().getGang(online.getGangId()).getAskGang();
				for (Long l : set) {
					LlpMessage msg = message.write("gangPros");
					RoleCard r = RoleManager.getRoleCardByRoleId(l);
					msg.write("roleId", r.getId());
					msg.write("name", r.getNick());
					msg.write("vocation", r.getVocation().getName());
					msg.write("level", r.getLevel());
				}
			}

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
