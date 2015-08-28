package iyunu.NewTLOL.model.gang.res;

import iyunu.NewTLOL.model.monster.MonsterDropItem;

import java.util.ArrayList;

public class GangTask {

	private int index;
	private ArrayList<MonsterDropItem> items = new ArrayList<MonsterDropItem>();
	private ArrayList<MonsterDropItem> awards = new ArrayList<MonsterDropItem>();
	private int zone;

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the items
	 */
	public ArrayList<MonsterDropItem> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(ArrayList<MonsterDropItem> items) {
		this.items = items;
	}

	/**
	 * @return the awards
	 */
	public ArrayList<MonsterDropItem> getAwards() {
		return awards;
	}

	/**
	 * @param awards
	 *            the awards to set
	 */
	public void setAwards(ArrayList<MonsterDropItem> awards) {
		this.awards = awards;
	}

	/**
	 * @return the zone
	 */
	public int getZone() {
		return zone;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(int zone) {
		this.zone = zone;
	}

}
