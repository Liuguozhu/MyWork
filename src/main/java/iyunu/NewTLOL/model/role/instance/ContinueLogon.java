package iyunu.NewTLOL.model.role.instance;

import iyunu.NewTLOL.model.monster.MonsterDropItem;

import java.util.HashMap;
import java.util.Map;

public class ContinueLogon {
	private int date;
	private Map<Integer, MonsterDropItem> itemIds = new HashMap<>();

	/**
	 * @return the date
	 */
	public int getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(int date) {
		this.date = date;
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
