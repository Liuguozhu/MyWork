package iyunu.NewTLOL.server.item;

import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;

import java.util.List;

public class ItemServer {

	/**
	 * 取得要发放的物品要占用几个格
	 * 
	 * @param list
	 * @return
	 */
	public static int getOwnCellCount(List<MonsterDropItem> list) {
		int allNum = 0;
		for (MonsterDropItem monsterDropItem : list) {
			Item item = ItemJson.instance().getItem(monsterDropItem.getItemId());
			int ownNum = monsterDropItem.getNum();
			int max = item.getMax();
			if (ownNum <= item.getMax()) {
				allNum = allNum + 1;
			} else {
				if (ownNum % max == 0) {
					allNum = allNum + ownNum / max;
				} else {
					allNum = allNum + ownNum / max + 1;
				}
			}
		}
		return allNum;
	}
}
