package iyunu.NewTLOL.model.gang.res;

import iyunu.NewTLOL.model.gang.GangShop;

public class GangShopRes {
	
	private int index;// 索引
	private int itemId;// 物品ID
	private String name;// 名称
	private int price;// 价格
	private String desc;// 描述
	private int level;// 等级

	public GangShop toGangShop() {
		GangShop gangShop = new GangShop();
		gangShop.setIndex(index);
		gangShop.setItemId(itemId);
		gangShop.setName(name);
		gangShop.setPrice(price);
		gangShop.setDesc(desc);
		gangShop.setLevel(level);
		return gangShop;
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
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
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

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

}
