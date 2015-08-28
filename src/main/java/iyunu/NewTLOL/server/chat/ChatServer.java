package iyunu.NewTLOL.server.chat;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.ChatMessage;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {

	public static void getChatRoles(EChat chatId, Role sender, Role receiver, Chat chat) {
		Set<Role> roles = new HashSet<Role>();

		switch (chatId) {
		case system: // 系统
			ChatMessage.sendChat(chat);
			break;
		case world: // 世界
			ChatMessage.sendChat(chat);
			break;
		case area: // 区域
			Set<Long> mapRoleIdList = sender.getMapInfo().getBaseMap().allInMap();
			for (Long roleId : mapRoleIdList) {
				if (ServerManager.instance().isOnline(roleId)) {
					roles.add(ServerManager.instance().getOnlinePlayer(roleId));
				}
			}
			ChatMessage.sendChat(roles, chat);
			break;
		case guild: // 门派
			ChatMessage.sendChat(ServerManager.instance().getChatChannel(sender.getVocation()), chat);
			break;
		case gang: // 帮派
			ArrayList<RoleCard> roleCardList = GangManager.instance().getGang(sender.getGangId()).getMembers();
			for (RoleCard roleCard : roleCardList) {
				if (ServerManager.instance().isOnline(roleCard.getId())) {
					roles.add(ServerManager.instance().getOnlinePlayer(roleCard.getId()));
				}
			}
			ChatMessage.sendChat(roles, chat);
			break;
		case team: // 队伍
			roles.addAll(sender.getTeam().getMember());
			ChatMessage.sendChat(roles, chat);
			break;
		case trumpet: // 喇叭
			ChatMessage.sendChat(chat);
			break;
		case whisper:
			roles.add(sender);
			roles.add(receiver);
			ChatMessage.sendWhisper(roles, chat);
			break;

		default:
			break;
		}
		LogManager.chat(sender, chatId, chat);
	}

	/**
	 * 发送系统通知
	 * 
	 * @param role
	 *            角色对象
	 * @param content
	 *            内容
	 */
	public static void sendSystem(Role role, String sender, String content) {
		Chat chat = new Chat(EChat.system, 0, sender, content);
		ChatMessage.sendChat(role, chat);
	}

	/**
	 * 帮派频道发送公告
	 * 
	 * @param content
	 *            公告内容
	 */
	public static void sendGangSystem(Gang gang, String content) {
		Chat chat = new Chat(EChat.gang, 0, "帮派系统", content);
		for (RoleCard roleCard : gang.getMembers()) {
			if (ServerManager.instance().isOnline(roleCard.getId())) {
				Role role = ServerManager.instance().getOnlinePlayer(roleCard.getId());
				ChatMessage.sendChat(role, chat);
			}
		}
	}
}
