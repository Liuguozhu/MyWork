package iyunu.NewTLOL.model.chat;

public class Chat {

	private EChat chatId;
	private int type; // 0.普通，1.语音
	private String senderName;
	private long senderId;
	private String receiverName;
	private long receiverId;
	private String content;
	private String sendTime;
	private int vip;
	private int mapId = 0;

	/**
	 * 构造方法
	 * 
	 * @param chatId
	 *            聊天类型
	 * @param senderId
	 *            发送者编号
	 * @param senderName
	 *            发送者名称
	 * @param content
	 *            聊天内容
	 */
	public Chat(EChat chatId, long senderId, String senderName, String content) {
		this(chatId, senderId, senderName, content, 0);
	}

	/**
	 * 构造方法
	 * 
	 * @param chatId
	 *            聊天类型
	 * @param senderId
	 *            发送者编号
	 * @param senderName
	 *            发送者名称
	 * @param content
	 *            聊天内容
	 */
	public Chat(EChat chatId, long senderId, String senderName, String content, int type) {
		this.chatId = chatId;
		this.senderId = senderId;
		this.senderName = senderName;
		this.content = content;
		this.type = type;
	}

	/**
	 * @param senderId
	 *            发送者编号
	 * @param senderName
	 *            发送者名称
	 * @param content
	 *            聊天内容
	 * @param sendTime
	 *            发送时间
	 */
	public Chat(long senderId, String senderName, long receiverId, String receiverName, String content, String sendTime) {
		this.senderId = senderId;
		this.senderName = senderName;
		this.receiverId = receiverId;
		this.receiverName = receiverName;
		this.content = content;
		this.sendTime = sendTime;
	}

	/**
	 * @return the chatId
	 */
	public EChat getChatId() {
		return chatId;
	}

	/**
	 * @param chatId
	 *            the chatId to set
	 */
	public void setChatId(EChat chatId) {
		this.chatId = chatId;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	/**
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}

	/**
	 * @param senderName
	 *            the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}
