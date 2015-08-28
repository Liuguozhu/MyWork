package iyunu.NewTLOL.model.activity.instance;

import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.monster.DropMonster;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class DrawingAward {

	private int index;
	private EOpen reward;

	private Map<Integer, Integer> type = new HashMap<>();
	/** 掉落物品 **/
	private ArrayList<MonsterDropItem> items = new ArrayList<MonsterDropItem>();
	private Map<Integer, Integer> fight = new HashMap<>();
	private ArrayList<DropMonster> monsters = new ArrayList<DropMonster>();

	public int awardType() {
		int num = 0;
		int finalRate = Util.getRandom(10000);
		Iterator<Entry<Integer, Integer>> it = type.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Integer> entry = it.next();
			num += entry.getValue();
			if (finalRate < num) {
				return entry.getKey();
			}
		}
		return 1;
	}

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
	 * @return the type
	 */
	public Map<Integer, Integer> getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Map<Integer, Integer> type) {
		this.type = type;
	}

	/**
	 * @return the fight
	 */
	public Map<Integer, Integer> getFight() {
		return fight;
	}

	/**
	 * @param fight
	 *            the fight to set
	 */
	public void setFight(Map<Integer, Integer> fight) {
		this.fight = fight;
	}

	/**
	 * @return the monsters
	 */
	public ArrayList<DropMonster> getMonsters() {
		return monsters;
	}

	/**
	 * @param monsters
	 *            the monsters to set
	 */
	public void setMonsters(ArrayList<DropMonster> monsters) {
		this.monsters = monsters;
	}

}
