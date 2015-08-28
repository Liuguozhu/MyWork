package iyunu.NewTLOL.server.bag;

import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Map;

public class BagServer {

	public static boolean add(Role role, Item item, int num, Map<Integer, Cell> map, EItemGet eItemGet) {
		if (item == null || num <= 0 || map == null) { // 非法参数
			LogManager.exception("角色编号【" + role.getId() + "】角色名称【" + role.getNick() + "】背包添加物品异常item=" + item + ",num=" + num);
			return false;
		}
		if (item.getType().equals(EItem.stone)) { // 如果是宝石
			role.getBagStone().add(item, num, map, eItemGet);
		} else { // 其他物品
			role.getBag().add(item, num, map, eItemGet);
		}
		return false;
	}

	/**
	 * 判断物品一共有多少个
	 * 
	 * @param itemId
	 * @return
	 */
	public static int getTheItemNum(int itemId, Role role) {
		Item item = ItemJson.instance().getItem(itemId);
		if (item == null || role == null) {
			return 0;
		}
		if (item.getType().equals(EItem.stone)) { // 如果是宝石
			return role.getBagStone().getTheItemNum(itemId);
		} else { // 其他物品
			return role.getBag().getTheItemNum(itemId);
		}
	}
}
