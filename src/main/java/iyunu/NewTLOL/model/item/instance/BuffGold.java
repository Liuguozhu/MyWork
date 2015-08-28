package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.buff.BuffServer;

import java.util.Map;

/**
 * BUFF实例类
 * 
 * @author SunHonglei
 * 
 */
public class BuffGold extends Item {

	public BuffGold copy() {
		BuffGold item = new BuffGold();
		Item.init(item, this);
		
		return item;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}

	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		BuffServer.addBuff(role, value, num);
		return true;
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
	}
}
