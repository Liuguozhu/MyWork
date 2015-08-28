package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.role.RoleServer;

import java.util.Map;

/**
 * @function 金币袋
 * @author LuoSR
 * @date 2014年3月11日
 */
public class CoinCard extends Item {

	@Override
	public CoinCard copy() {
		CoinCard item = new CoinCard();
		Item.init(item, this);
		
		return item;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		RoleServer.addCoin(role, value * num, EGold.item);
		return true;
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
	}

}
