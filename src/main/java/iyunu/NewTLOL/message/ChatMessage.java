package iyunu.NewTLOL.message;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Set;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class ChatMessage {

	public static void sendChat(Chat chat, Set<Long> roleIds) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_chatReceive");
			llpMessage.write("chatId", chat.getChatId().ordinal());
			llpMessage.write("type", chat.getType());
			llpMessage.write("senderId", chat.getSenderId());
			llpMessage.write("senderName", chat.getSenderName());
			llpMessage.write("content", chat.getContent());
			llpMessage.write("vip", chat.getVip());
			llpMessage.write("mapId", chat.getMapId());
			for (Long roleId : roleIds) {
				if (ServerManager.instance().isOnline(roleId)) {
					Role role = ServerManager.instance().getOnlinePlayer(roleId);
					role.getChannel().write(llpMessage);
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送聊天1信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 发送聊天信息
	 * 
	 * @param roles
	 *            角色集合
	 * @param chat
	 *            聊天对象
	 */
	public static void sendChat(Chat chat) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_chatReceive");
			llpMessage.write("chatId", chat.getChatId().ordinal());
			llpMessage.write("type", chat.getType());
			llpMessage.write("senderId", chat.getSenderId());
			llpMessage.write("senderName", chat.getSenderName());
			llpMessage.write("content", chat.getContent());
			llpMessage.write("vip", chat.getVip());
			llpMessage.write("mapId", chat.getMapId());
			for (Long roleId : ServerManager.instance().getIdChannelPair().keySet()) {
				Role role = ServerManager.instance().getOnlinePlayer(roleId);
				if (role != null && role.getChannel() != null && role.isLogon()) {
					role.getChannel().write(llpMessage);
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送聊天1信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	public static void sendChat(Set<Role> roles, Chat chat) {
		if (roles == null || roles.isEmpty()) {
			return;
		}
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_chatReceive");
			llpMessage.write("chatId", chat.getChatId().ordinal());
			llpMessage.write("type", chat.getType());
			llpMessage.write("senderId", chat.getSenderId());
			llpMessage.write("senderName", chat.getSenderName());
			llpMessage.write("content", chat.getContent());
			llpMessage.write("vip", chat.getVip());
			llpMessage.write("mapId", chat.getMapId());
			for (Role role : roles) {
				if (role != null && role.getChannel() != null && role.isLogon()) {
					role.getChannel().write(llpMessage);
				}
			}

		} catch (Exception e) {
			LogManager.info("异常报告：发送聊天1信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 单独发送聊天信息
	 * 
	 * @param role
	 *            角色
	 * @param chat
	 *            聊天对象
	 */
	public static void sendChat(Role role, Chat chat) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_chatReceive");
				llpMessage.write("chatId", chat.getChatId().ordinal());
				llpMessage.write("type", chat.getType());
				llpMessage.write("senderId", chat.getSenderId());
				llpMessage.write("senderName", chat.getSenderName());
				llpMessage.write("content", chat.getContent());
				llpMessage.write("vip", chat.getVip());
				llpMessage.write("mapId", chat.getMapId());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送聊天2信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 私聊
	 * 
	 * @param roles
	 *            角色集合
	 * @param chat
	 *            聊天对象
	 */
	public static void sendWhisper(Set<Role> roles, Chat chat) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_whisperReceive");
			llpMessage.write("senderId", chat.getSenderId());
			llpMessage.write("senderName", chat.getSenderName());
			llpMessage.write("receiverId", chat.getReceiverId());
			llpMessage.write("receiverName", chat.getReceiverName());
			llpMessage.write("content", chat.getContent());
			llpMessage.write("time", chat.getSendTime());
			llpMessage.write("vip", chat.getVip());
			for (Role role : roles) {
				if (role != null && role.getChannel() != null && role.isLogon()) {
					role.getChannel().write(llpMessage);
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送私聊信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 
	 * @param role
	 *            角色
	 * @param chat
	 *            聊天对象
	 */
	public static void sendTip(Role role, String content) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_chatReceive");
				llpMessage.write("chatId", EChat.tip.ordinal());
				llpMessage.write("type", 0);
				llpMessage.write("senderId", 0l);
				llpMessage.write("senderName", "提示");
				llpMessage.write("content", content);
				llpMessage.write("vip", 0);
				llpMessage.write("mapId", 0);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送提示信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}
