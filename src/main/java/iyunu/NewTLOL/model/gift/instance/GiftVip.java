package iyunu.NewTLOL.model.gift.instance;

import iyunu.NewTLOL.model.monster.MonsterDropItem;

import java.util.HashMap;
import java.util.Map;

/**
 * @function VIP奖励
 * @author fhy
 */
public class GiftVip {

	/** vip等级 **/
	private int type;
	/** 物品 **/
	private Map<Integer, MonsterDropItem> items = new HashMap<Integer, MonsterDropItem>();
	/** 元宝 */
	private int money;

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
	 * @return the items
	 */
	public Map<Integer, MonsterDropItem> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(Map<Integer, MonsterDropItem> items) {
		this.items = items;
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
