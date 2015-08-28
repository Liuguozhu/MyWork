package iyunu.NewTLOL.model.auction;

import iyunu.NewTLOL.model.item.Item;

public class AuctionItem extends Auction {

	private int num; // 数量
	private Item item; // 物品
	private String itemStr;

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return the itemStr
	 */
	public String getItemStr() {
		return itemStr;
	}

	/**
	 * @param itemStr
	 *            the itemStr to set
	 */
	public void setItemStr(String itemStr) {
		this.itemStr = itemStr;
	}

}
