package iyunu.NewTLOL.model.mall.res;

import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.mall.EPrice;
import iyunu.NewTLOL.model.mall.instance.Mall;

/**
 * 商城
 * 
 * @author SunHonglei
 * 
 */
public class MallRes {

	/** 序列 **/
	private int index;
	/** 物品编号 **/
	private int itemId;
	/** 名称 **/
	private String name;
	/** 消费类型 **/
	private EPrice priceType;
	/** 价格 **/
	private int price;
	/** 等级 **/
	private int level;
	/** 折扣 **/
	private int off;
	/** 是否绑定 **/
	private int isBind;

	public Mall toMall() {
		Mall mall = new Mall();
		mall.setIndex(index);
		mall.setItemId(itemId);
		mall.setName(name);
		mall.setPriceType(priceType);
		mall.setPrice(price);
		mall.setLevel(level);
		mall.setOff(off);
		Item item = ItemJson.instance().getItem(itemId);
		mall.setIcon(item.getIcon());
		mall.setIsBind(isBind);
		return mall;
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
	 * @return the priceType
	 */
	public EPrice getPriceType() {
		return priceType;
	}

	/**
	 * @param priceType
	 *            the priceType to set
	 */
	public void setPriceType(EPrice priceType) {
		this.priceType = priceType;
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

	/**
	 * @return the off
	 */
	public int getOff() {
		return off;
	}

	/**
	 * @param off
	 *            the off to set
	 */
	public void setOff(int off) {
		this.off = off;
	}

	public int getIsBind() {
		return isBind;
	}

	public void setIsBind(int isBind) {
		this.isBind = isBind;
	}

}
