package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.bag.BagServer;

import java.util.Map;

public class Fragment extends Item {

	@Override
	public Fragment copy() {
		Fragment item = new Fragment();
		Item.init(item, this);
		
		return item;
	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		int itemNum = num / 10;
		Item item = ItemJson.instance().getItem(value);
		if (item != null && itemNum > 0) {
			BagServer.add(role, item, itemNum, cellsMap, EItemGet.fragmentMake);
		}

		return true;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
	}

}
