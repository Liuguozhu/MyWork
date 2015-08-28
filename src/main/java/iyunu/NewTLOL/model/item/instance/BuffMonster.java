package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.buff.BuffServer;

import java.util.Map;

/**
 * BUFF实例类 杀怪加倍BUFF
 * 
 * @author SunHonglei
 * 
 */
public class BuffMonster extends Item {

	public BuffMonster copy() {
		BuffMonster item = new BuffMonster();
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
