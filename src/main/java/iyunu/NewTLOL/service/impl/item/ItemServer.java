package iyunu.NewTLOL.service.impl.item;

import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.Item;

public class ItemServer {

	/**
	 * 设置物品唯一编号
	 * 
	 * @param item
	 *            物品
	 * @return 物品
	 */
	public static Item setUid(Item item) {
		if (item.getType().equals(EItem.equip)) {
			item.setUid(UidManager.instance().ItemUid());
		} else {
			item.setUid(String.valueOf(item.getId()));
		}
		return item;
	}
}
