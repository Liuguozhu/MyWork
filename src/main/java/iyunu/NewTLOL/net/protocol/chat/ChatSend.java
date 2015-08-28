package iyunu.NewTLOL.net.protocol.chat;

import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.manager.IlllegalWordManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.chat.ChatServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.Time;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class ChatSend extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "发送成功！";

	@Override
	public void handleReceived(LlpMessage msg) {

		// ======数据重置======
		result = 0;
		reason = "发送成功！";

		// ======获取参数======
		int index = msg.readInt("chatId");
		int type = msg.readInt("type"); // 0.普通，1.语音
		String content = msg.readString("content");

		// ======判断聊天频道是否符合条件======
		if (index < 0 || index >= EChat.values().length) {
			result = 1;
			reason = "发送失败！";
			return;
		}

		long now = System.currentTimeMillis();
		if (type == 0 && content.equals(online.getContent()) && online.getContentNum() >= 5) {
			online.setMute(System.currentTimeMillis() + 5 * Time.MINUTE_MILLISECOND);
			online.setContentNum(0);
		}

		if (online.getMute() > now) {
			result = 1;
			reason = "发送失败，由于您的不正当游戏，您的已被禁言，距离解禁时间还剩" + ((online.getMute() - now - 1) / Time.MINUTE_MILLISECOND + 1) + "分钟！";
			return;
		}

		EChat chatId = EChat.values()[index];
		if (chatId.equals(EChat.system)) {
			result = 1;
			reason = "发送失败，没有权限！";
			return;
		}
		if (chatId.equals(EChat.world) && online.getLevel() < 20) {
			result = 1;
			reason = "发送失败，等级不足20级！";
			return;
		}
		if (chatId.equals(EChat.gang) && online.getGangId() == 0) {
			result = 1;
			reason = "发送失败，无帮派！";
			return;
		}
		if (chatId.equals(EChat.team) && online.getTeam() == null) {
			result = 1;
			reason = "发送失败，无队伍！";
			return;
		}
		if (chatId.equals(EChat.guild) && online.getVocation().equals(Vocation.none)) {
			result = 1;
			reason = "发送失败，无门派！";
			return;
		}
		if (chatId.equals(EChat.trumpet)) {

			int itemIndex = online.getBag().isInBagByType(EItem.trumpet);

			if (itemIndex == -1) {
				result = 1;
				reason = "发送失败，没有大喇叭";
				return;
			}

			Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
			online.getBag().removeByIndex(itemIndex, 1, cellsMap, EItemCost.chat); // 删除物品
			BagMessage.sendBag(online, cellsMap, false);

		}

		if (chatId.equals(EChat.area)) {
			Team team = online.getTeam();
			if (team != null) {
				if (team.getLeader().getId() != online.getId()) {
					result = 1;
					reason = "队长才可以使用队伍区域喊话";
					return;
				}
			}
		}

		content = IlllegalWordManager.instance().checkString(content); // 替换非法字符
		// 查看当前时间功能
		if (content.equals("时间") || content.toLowerCase().equals("time")) {
			content = Time.getTimeToStr(System.currentTimeMillis());
		}
		// 城主禁言功能
		if (content.startsWith("@禁言") && online.getId() == GangManager.championLeader) {
			String[] strings = content.split(" ");
			if (strings.length == 2 && strings[1].length() <= 15 && strings[1].matches("[0-9]+")) {
				long roleId = Integer.parseInt(strings[1]);
				if (ServerManager.instance().getOnlinePlayer(roleId) == null) {
					result = 1;
					reason = "发送失败，玩家不在线或ID错误";
					return;
				}
				if (GangManager.muteIds.contains(roleId)) {
					result = 1;
					reason = "发送失败，同一天不可重复禁言同一人";
					return;
				}
				if (GangManager.muteIds.size() > 3) {
					result = 1;
					reason = "发送失败，同一天只可以使用3次禁言功能";
					return;
				}
				// 加禁言10分钟
				RoleServer.addMute(ServerManager.instance().getOnlinePlayer(roleId), 10);
				GangManager.muteIds.add(roleId);
			}
		}

		if (online.getContent() == null || content.equals(online.getContent())) {
			online.setContentNum(online.getContentNum() + 1);
		} else {
			online.setContentNum(0);
		}

		online.setContent(content);
		Chat chat = new Chat(chatId, online.getId(), online.getNick(), content, type);

		if (online.getOpenVipChat() == 0) {
			chat.setVip(online.getVip().getLevel().ordinal());
		}

		ChatServer.getChatRoles(chatId, online, null, chat);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage ackChatSend = null;
		try {
			ackChatSend = LlpJava.instance().getMessage("s_chatSend");
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
