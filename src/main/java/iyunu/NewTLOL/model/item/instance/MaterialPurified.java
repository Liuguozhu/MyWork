package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;

import java.util.Map;

/**
 * @function 净化水
 * @author LuoSR
 * @date 2013-11-29
 */
public class MaterialPurified extends Item {

	@Override
	public MaterialPurified copy() {
		MaterialPurified item = new MaterialPurified();
		Item.init(item, this);
		
		return item;
	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		return false;
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}
}
