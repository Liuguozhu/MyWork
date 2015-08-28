package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.EBattleBuffType;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.battle.amin.BattleAminInfo;
import iyunu.NewTLOL.model.battle.buff.BattleBuff;
import iyunu.NewTLOL.model.battle.buff.EBattleBuffEffect;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.battle.BattleServer;
import iyunu.NewTLOL.util.Util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @function 解封丹
 * @author LuoSR
 * @date 2014年3月11日
 */
public class Jiefeng extends Item {

	@Override
	public Jiefeng copy() {
		Jiefeng item = new Jiefeng();
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

		if (this.apply == 0 || this.apply == 2) {
			BattleAminInfo battleAminInfo = BattleServer.createBattleAminInfo(battleInfo, self, 3, id); // 创建战斗动画对象
			BattleCharacter foe = battleInfo.getBattleCharacter(foeIndex, self.getDirection(), false);
			if (foe == null) {
				return false;
			}

			BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
			battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

			int recoverHp = Util.matchSmaller(foe.getPropertyBattle().getHpMax() - foe.getPropertyBattle().getHp(), value);
			defenderAmin.addHp(recoverHp); // 战斗动画——防守者——记录血量变化
			foe.getPropertyBattle().addHp(recoverHp);

			Iterator<Entry<EBattleBuffType, BattleBuff>> it = foe.getBuffs().entrySet().iterator();
			while (it.hasNext()) {
				Entry<EBattleBuffType, BattleBuff> entry = it.next();
				if (entry.getValue().getDebuff().equals(EBattleBuffEffect.seal)) {
					defenderAmin.getSubBuffs().add(entry.getValue().getId()); // 战斗动画——被攻击者——去除debuff
					it.remove();
				}
			}
			return true;
		}

		return false;
	}

}
