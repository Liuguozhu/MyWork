package iyunu.NewTLOL.model.activity.plusMoney;

import iyunu.NewTLOL.model.monster.MonsterDropItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @function 累计充值
 * @author fhy
 */
public class PlusMoney {

	/** id **/
	private int id;
	/** 金额 */
	private int money;
	/** 物品 **/
	private List<MonsterDropItem> items = new ArrayList<MonsterDropItem>();//
	/** 描述 **/
	private String dec;

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

	/**
	 * @return the items
	 */
	public List<MonsterDropItem> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<MonsterDropItem> items) {
		this.items = items;
	}

	/**
	 * @return the des
	 */
	public String getDec() {
		return dec;
	}

	/**
	 * @param des
	 *            the des to set
	 */
	public void setDec(String dec) {
		this.dec = dec;
	}

}
