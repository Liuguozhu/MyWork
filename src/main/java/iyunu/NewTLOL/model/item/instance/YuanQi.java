package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.role.RoleServer;

import java.util.Map;

/**
 * @function 培元丹
 * @author LuoSR
 * @date 2014年3月11日
 */
public class YuanQi extends Item {

	@Override
	public YuanQi copy() {
		YuanQi item = new YuanQi();
		Item.init(item, this);
		
		return item;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		RoleServer.addYuanQi(role, value * num);
		return true;
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
	}

}
