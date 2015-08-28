package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.buffRole.EBuffType;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.buff.BuffServer;
import iyunu.NewTLOL.server.partner.PartnerServer;

import java.util.Map;

/**
 * BUFF实例类
 * 
 * @author SunHonglei
 * 
 */
public class BuffPartnerHp extends Item {

	public BuffPartnerHp copy() {
		BuffPartnerHp item = new BuffPartnerHp();
		Item.init(item, this);

		return item;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}

	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {

		BuffRole buffRole = role.getBuffs().get(EBuffType.php);
		if (buffRole != null) {
			if (buffRole.getValue() + value * num > 99999999) {
				return false;
			}
		}

		BuffServer.addBuff(role, value, num);
		PartnerServer.autoRecoverHp(role);
		
		return true;
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
	}
}
