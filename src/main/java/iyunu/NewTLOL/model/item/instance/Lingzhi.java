package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.buffRole.EBuffType;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.buff.BuffServer;

import java.util.Map;

/**
 * 灵芝
 * 
 * @author SunHonglei
 * 
 */
public class Lingzhi extends Item {

	public Lingzhi copy() {
		Lingzhi item = new Lingzhi();
		Item.init(item, this);
		
		return item;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}

	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		BuffServer.removeBuff(role, EBuffType.xunyicao);
		SendMessage.sendCloaking(role);
		return true;
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
	}
}
