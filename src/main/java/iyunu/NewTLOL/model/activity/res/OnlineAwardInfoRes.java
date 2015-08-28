package iyunu.NewTLOL.model.activity.res;

import iyunu.NewTLOL.model.activity.instance.OnlineAwardInfo;
import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.util.Translate;

public class OnlineAwardInfoRes {

	private int id;
	private int time;
	private int gold;
	private int money;
	private EOpen reward; // 获取类型
	private String items; // 物品

	public OnlineAwardInfo toOnlineAwardInfo() {
		OnlineAwardInfo onlineAwardInfo = new OnlineAwardInfo();
		onlineAwardInfo.setId(id);
		onlineAwardInfo.setTime(time);
		onlineAwardInfo.setGold(gold);
		onlineAwardInfo.setMoney(money);
		onlineAwardInfo.setReward(reward);

		String[] stings = items.split(":");
		onlineAwardInfo.setItemId(Translate.stringToInt(stings[0]));
		onlineAwardInfo.setItemNum(Translate.stringToInt(stings[1]));
		onlineAwardInfo.setIsBind(Translate.stringToInt(stings[2]));
		return onlineAwardInfo;
	}

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

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
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

}
