package iyunu.NewTLOL.model.payActivity.res;

import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.monster.MonsterDropPartner;
import iyunu.NewTLOL.model.payActivity.instance.PayActivityInfo;
import iyunu.NewTLOL.util.Translate;

public class PayActivityInfoRes {

	private int id;
	private int type;
	private int index;
	private int value;
	private String item;
	private String partner;
	private int gold;
	private int coin;
	private int money;
	private String desc;

	public PayActivityInfo toPaySingle() {
		PayActivityInfo activityInfo = new PayActivityInfo();
		activityInfo.setId(id);
		activityInfo.setType(type);
		activityInfo.setIndex(index);
		activityInfo.setValue(value);
		activityInfo.setGold(gold);
		activityInfo.setCoin(coin);
		activityInfo.setMoney(money);
		activityInfo.setDesc(desc);

		if (item != null && !"".equals(item)) {
			String[] str = item.split(";");
			for (String string : str) {
				String[] itemStr = string.split(":");
				activityInfo.getItems().add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), 0, Translate.stringToInt(itemStr[2])));
			}
		}
		if (partner != null && !"".equals(partner)) {
			String[] str = partner.split(";");
			for (String string : str) {
				String[] partnerStr = string.split(":");
				activityInfo.getPartners().add(new MonsterDropPartner(Translate.stringToInt(partnerStr[0]), Translate.stringToInt(partnerStr[1]), 0, Translate.stringToInt(partnerStr[2])));
			}
		}
		return activityInfo;
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
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		this.value = value;
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
	 * @return the partner
	 */
	public String getPartner() {
		return partner;
	}

	/**
	 * @param partner
	 *            the partner to set
	 */
	public void setPartner(String partner) {
		this.partner = partner;
	}

	/**
	 * @return the gold
	 */
	public int getGold() {
		return gold;
	}

	/**
	 * @param gold
	 *            the gold to set
	 */
	public void setGold(int gold) {
		this.gold = gold;
	}

	/**
	 * @return the coin
	 */
	public int getCoin() {
		return coin;
	}

	/**
	 * @param coin
	 *            the coin to set
	 */
	public void setCoin(int coin) {
		this.coin = coin;
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
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
