package iyunu.NewTLOL.model.activity.plusMoney;

import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.Translate;

import java.util.ArrayList;
import java.util.List;

/**
 * @function 累计充值
 * @author fhy
 */
public class PlusMoneyRes {

	/** id **/
	private int id;
	/** 金额 */
	private int money;
	/** 物品 **/
	private String items;
	/** 描述 **/
	private String dec;

	public PlusMoney toPlusMoney() {
		PlusMoney p = new PlusMoney();
		p.setId(id);
		p.setMoney(money);
		p.setDec(dec);
		List<MonsterDropItem> item = new ArrayList<MonsterDropItem>();
		if (items != null && !"".equals(items)) {
			String[] stings = items.split(";");
			for (int i = 0; i < stings.length; i++) {
				String[] itemStr = stings[i].split(":");
				item.add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), 0, Translate.stringToInt(itemStr[2])));
			}
		}
		p.setItems(item);
		return p;
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
	public String getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(String items) {
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
