package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.Map;

/**
 * 礼包实例类
 * 
 * @author SunHonglei
 * 
 */
public class Gift extends Item {

	private EOpen reward;
	/** 掉落物品 **/
	private ArrayList<MonsterDropItem> dropItems = new ArrayList<MonsterDropItem>();

	// private String items;

	@Override
	public Gift copy() {
		Gift item = new Gift();
		Item.init(item, this);

		item.setReward(reward);
		// item.setItems(items);
		item.initDropItems(dropItems);

		return item;
	}

	@Override
	public void change() {
		// if (items != null && !"".equals(items)) {
		// String[] str = items.split(";");
		// for (String string : str) {
		// String[] itemStr = string.split(":");
		// dropItems.add(new MonsterDropItem(Translate.stringToInt(itemStr[0]),
		// Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2]),
		// Translate.stringToInt(itemStr[3])));
		// }
		// }

	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		if (this.way == 0 && (this.apply == 0 || this.apply == 1)) {
			if (reward.equals(EOpen.all)) {
				if (role.getBag().isFull(dropItems.size())) {
					return false;
				}
				for (MonsterDropItem dropItem : dropItems) {
					if (Util.probable(dropItem.getProbability())) {
						Item item = ItemJson.instance().getItem(dropItem.getItemId());
						if (item != null && num > 0) {
							item.setIsDeal(dropItem.getIsBind());
							BagServer.add(role, item, num * dropItem.getNum(), cellsMap, EItemGet.openGift);
						}
					}
				}
			} else if (reward.equals(EOpen.one)) {
				if (role.getBag().isFull(num)) {
					return false;
				}
				for (int i = 0; i < num; i++) {
					int probable = 0;
					int finalRate = Util.getRandom(10000);
					for (MonsterDropItem dropItem : dropItems) {
						probable += dropItem.getProbability();
						if (probable > finalRate) {
							Item item = ItemJson.instance().getItem(dropItem.getItemId());
							if (item != null) {
								item.setIsDeal(dropItem.getIsBind());
								BagServer.add(role, item, dropItem.getNum(), cellsMap, EItemGet.openGift);
							}
							break;
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
	}

	/**
	 * @return the reward
	 */
	public EOpen getReward() {
		return reward;
	}

	/**
	 * @param reward
	 *            the reward to set
	 */
	public void setReward(EOpen reward) {
		this.reward = reward;
	}

	/**
	 * @return the dropItems
	 */
	public ArrayList<MonsterDropItem> dropItems() {
		return dropItems;
	}

	/**
	 * @param dropItems
	 *            the dropItems to set
	 */
	public void initDropItems(ArrayList<MonsterDropItem> dropItems) {
		this.dropItems = dropItems;
	}
}
