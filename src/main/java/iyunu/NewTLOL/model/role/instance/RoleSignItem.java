package iyunu.NewTLOL.model.role.instance;

import iyunu.NewTLOL.model.monster.MonsterDropItem;

import java.util.HashMap;
import java.util.Map;

public class RoleSignItem {
	private int day;// 签到ID
	private Map<Integer, MonsterDropItem> itemIds = new HashMap<>();// 签到物品

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day
	 *            the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * @return the itemIds
	 */
	public Map<Integer, MonsterDropItem> getItemIds() {
		return itemIds;
	}

	/**
	 * @param itemIds
	 *            the itemIds to set
	 */
	public void setItemIds(Map<Integer, MonsterDropItem> itemIds) {
		this.itemIds = itemIds;
	}

}
