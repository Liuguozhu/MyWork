package iyunu.NewTLOL.model.gang.res;

import iyunu.NewTLOL.model.monster.MonsterDropItem;

import java.util.ArrayList;

public class GangTaskAllAward {

	private int index;
	private ArrayList<MonsterDropItem> items = new ArrayList<MonsterDropItem>();

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

}
