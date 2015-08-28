package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.enumeration.EFigure;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.util.Translate;

import java.util.Map;

/**
 * 礼包实例类
 * 
 * @author SunHonglei
 * 
 */
public class GiftWeapon extends Item {

	private EOpen reward;
	private String items;

	@Override
	public GiftWeapon copy() {
		GiftWeapon item = new GiftWeapon();
		Item.init(item, this);
		item.setReward(reward);
		item.setItems(items);
		
		return item;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		if (this.way == 0 && (this.apply == 0 || this.apply == 1)) {

			String[] stings = items.split(";");
			if (role.getBag().isFull()) {
				return false;
			}

			for (String string : stings) {
				String[] itemStr = string.split(":");
				if (EFigure.valueOf(itemStr[0]).check(role.getFigure())) {
					Item item = ItemJson.instance().getItem(Translate.stringToInt(itemStr[1]));
					item.setIsDeal(Translate.stringToInt(itemStr[2]));
					if (item != null) {
						BagServer.add(role, item, num, cellsMap, EItemGet.openGift);
						return true;
					}
				}
			}
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
	 * @return the items
	 */
	public String getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(String items) {
		this.items = items;
	}
}
