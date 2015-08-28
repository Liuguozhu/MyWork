package iyunu.NewTLOL.model.gift.res;

import iyunu.NewTLOL.model.gift.instance.GiftVip;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.Translate;

/**
 * @function VIP奖励
 * @author fhy
 */
public class GiftVipRes {

	/** VIP等级 **/
	private int type;
	/** VIP物品 **/
	private String item;
	/** 元宝 */
	private int money;

	public GiftVip toGiftVip() {
		GiftVip giftVip = new GiftVip();
		giftVip.setType(type);
		giftVip.setMoney(money);

		String[] strings = item.split(";");
		for (int i = 0; i < strings.length; i++) {
			String[] itemStr = strings[i].split(":");
			int itemId = Translate.stringToInt(itemStr[0]);
			int itemNum = Translate.stringToInt(itemStr[1]);
			int bind = Translate.stringToInt(itemStr[2]);
			MonsterDropItem item = new MonsterDropItem(itemId, itemNum, 0, bind);
			giftVip.getItems().put(Translate.stringToInt(itemStr[0]), item);

		}
		return giftVip;
	}

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
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(String item) {
		this.item = item;
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
