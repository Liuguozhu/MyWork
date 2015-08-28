package iyunu.NewTLOL.model.battle;

public class CommandInfo {

	private int sender; // 命令发送方
	private int receiver; // 命令接收方
	private int type; // 命令类型（0.攻，1.防，2.逃跑，3.使用物品，4.召唤伙伴）
	private long skill; // 技能编号（0普通攻击）
	private int senderType; // 攻击者类型（0.角色，1.伙伴）
	private int turn; // 轮数

	public CommandInfo(int sender, int receiver, int type, long skill, int turn) {
		this.sender = sender;
		this.receiver = receiver;
		this.type = type;
		this.skill = skill;
		this.turn = turn;
	}

	/**
	 * @return the sender
	 */
	public int getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(int sender) {
		this.sender = sender;
	}

	/**
	 * @return the receiver
	 */
	public int getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver
	 *            the receiver to set
	 */
	public void setReceiver(int receiver) {
		this.receiver = receiver;
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

	/**
	 * @return the skill
	 */
	public long getSkill() {
		return skill;
	}

	/**
	 * @param skill
	 *            the skill to set
	 */
	public void setSkill(long skill) {
		this.skill = skill;
	}

	/**
	 * @return the senderType
	 */
	public int getSenderType() {
		return senderType;
	}

	/**
	 * @param senderType
	 *            the senderType to set
	 */
	public void setSenderType(int senderType) {
		this.senderType = senderType;
	}

	/**
	 * @return the turn
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * @param turn
	 *            the turn to set
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}

}
