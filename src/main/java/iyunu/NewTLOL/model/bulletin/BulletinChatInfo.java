package iyunu.NewTLOL.model.bulletin;

import iyunu.NewTLOL.model.chat.Chat;

/**
 * @function 系统公告
 */
public class BulletinChatInfo {

	/** 聊天对象 **/
	private Chat chat;

	/**
	 * @return the chat
	 */
	public Chat getChat() {
		return chat;
	}

	/**
	 * @param chat
	 *            the chat to set
	 */
	public void setChat(Chat chat) {
		this.chat = chat;
	}

}
