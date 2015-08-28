package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.event.uptip.UptipEvent;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.role.RoleServer;

import java.util.Map;

/**
 * 角色属性重置
 * 
 * @author SunHonglei
 * 
 */
public class RoleReset extends Item {

	@Override
	public RoleReset copy() {
		RoleReset item = new RoleReset();
		Item.init(item, this);

		return item;
	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		UptipEvent.属性点.check(role, role.getUptipBoolean(UptipEvent.属性点.getOrdinal()));
		return RoleServer.reset(role);
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
