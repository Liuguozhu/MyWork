package iyunu.NewTLOL.model.gang;

public class GangShop {

	/** 序列 **/
	private int index;
	/** 物品编号 **/
	private int itemId;
	/** 名称 **/
	private String name;
	/** 描述 **/
	private String desc;
	/** 价格(帮贡) **/
	private int price;
	/** 帮派等级 */
	private int level;

	/**
	 * 复制
	 * 
	 * @return 自身对象
	 */
	public GangShop copy() {
		GangShop shop = new GangShop();
		shop.setIndex(index);
		shop.setItemId(itemId);
		shop.setName(name);
		shop.setDesc(desc);
		shop.setPrice(price);
		shop.setLevel(level);
		return shop;
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
