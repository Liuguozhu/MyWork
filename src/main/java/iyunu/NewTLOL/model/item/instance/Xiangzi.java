package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.role.Role;

import java.util.ArrayList;
import java.util.Map;

/**
 * 神奇宝盒实例类
 * 
 * @author SunHonglei
 * 
 */
public class Xiangzi extends Item {

	private EOpen reward;
	/** 掉落物品 **/
	private ArrayList<MonsterDropItem> dropItems = new ArrayList<MonsterDropItem>();
//	private String items;

	@Override
	public Xiangzi copy() {
		Xiangzi item = new Xiangzi();
		Item.init(item, this);
		
		item.setReward(reward);
//		item.setItems(items);
		item.initDropItems(dropItems);
		
		return item;
	}

	@Override
	public void change() {
//		if (items != null && !"".equals(items)) {
//			String[] str = items.split(";");
//			for (String string : str) {
//				String[] itemStr = string.split(":");
//				dropItems.add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2]), Translate.stringToInt(itemStr[3])));
//			}
//		}
	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
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

	/**
	 * @return the items
	 */
//	public String getItems() {
//		return items;
//	}
//
//	/**
//	 * @param items
//	 *            the items to set
//	 */
//	public void setItems(String items) {
//		this.items = items;
//	}
}
