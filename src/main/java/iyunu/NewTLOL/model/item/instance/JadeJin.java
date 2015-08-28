package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;

import java.util.Map;

/**
 * @function 紧那罗残玉
 * @author LuoSR
 * @date 2014年3月11日
 */
public class JadeJin extends Item {

	@Override
	public JadeJin copy() {
		JadeJin item = new JadeJin();
		Item.init(item, this);
		
		return item;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		return false;
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
	}

}
