package iyunu.NewTLOL.model.activity.instance;

import iyunu.NewTLOL.model.item.EOpen;

public class OnlineAwardInfo {

	private int id;
	private int time;
	private int gold;
	private int money;
	private EOpen reward; // 获取类型
	/** 在线奖励物品 **/
	private int itemId;
	private int itemNum;
	private int isBind;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return the reward
	 */
	public EOpen getReward() {
		return reward;
	}

	/**
	 * @param reward
	 *            the reward to set
	 */
	public void setReward(EOpen reward) {
		this.reward = reward;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	/**
	 * @return the gold
	 */
	public int getGold() {
		return gold;
	}

	/**
	 * @param gold
	 *            the gold to set
	 */
	public void setGold(int gold) {
		this.gold = gold;
	}

	/**
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	public int getIsBind() {
		return isBind;
	}

	public void setIsBind(int isBind) {
		this.isBind = isBind;
	}

}
