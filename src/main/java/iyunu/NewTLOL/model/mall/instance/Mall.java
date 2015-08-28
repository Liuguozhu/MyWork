package iyunu.NewTLOL.model.mall.instance;

import iyunu.NewTLOL.model.mall.EMall;
import iyunu.NewTLOL.model.mall.EPrice;

/**
 * 商城
 * 
 * @author SunHonglei
 * 
 */
public class Mall {

	/** 序列 **/
	private int index;
	/** 物品编号 **/
	private int itemId;
	/** 名称 **/
	private String name;
	/** 价格 **/
	private int price;
	/** 图标 **/
	private String icon;
	/** 消费类型 **/
	private EPrice priceType;
	/** 等级 **/
	private int level;
	/** 折扣 **/
	private int off;
	/** 是否绑定 **/
	private int isBind;
	private EMall type;

	/**
	 * 复制
	 * 
	 * @return 自身对象
	 */
	public Mall copy() {
		Mall mall = new Mall();
		mall.setIndex(index);
		mall.setItemId(itemId);
		mall.setName(name);
		mall.setPrice(price);
		mall.setIcon(icon);
		mall.setPriceType(priceType);
		mall.setLevel(level);
		mall.setOff(off);
		mall.setIsBind(isBind);
		mall.setType(type);
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
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
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

	/**
	 * @return the type
	 */
	public EMall getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(EMall type) {
		this.type = type;
	}

}
