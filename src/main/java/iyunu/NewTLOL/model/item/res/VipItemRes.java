package iyunu.NewTLOL.model.item.res;

import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.VipItem;
import iyunu.NewTLOL.model.vip.EVip;

/**
 * VIP道具资源类
 * 
 * @author SunHonglei
 * 
 */
public class VipItemRes extends BaseItemRes {

	/** 种类 **/
	private EVip category;

	public Item toItem() {
		VipItem item = (VipItem) super.toItem();
		item.setCategory(category);
		return item;
	}

	/**
	 * @return the category
	 */
	public EVip getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(EVip category) {
		this.category = category;
	}

}
