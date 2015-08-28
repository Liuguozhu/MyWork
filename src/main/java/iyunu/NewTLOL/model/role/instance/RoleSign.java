package iyunu.NewTLOL.model.role.instance;

import iyunu.NewTLOL.model.monster.MonsterDropItem;

import java.util.HashMap;
import java.util.Map;

public class RoleSign {
	private int id;
	private Map<Integer, MonsterDropItem> itemIds = new HashMap<>();
	private int day;
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

}
