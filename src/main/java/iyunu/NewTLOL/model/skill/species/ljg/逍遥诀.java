package iyunu.NewTLOL.model.skill.species.ljg;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.EBattleBuffType;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.battle.amin.BattleAminInfo;
import iyunu.NewTLOL.model.battle.buff.BattleBuff;
import iyunu.NewTLOL.model.battle.buff.EBattleBuffEffect;
import iyunu.NewTLOL.model.skill.instance.BaseSkill;
import iyunu.NewTLOL.model.skill.species.ParamTemp;
import iyunu.NewTLOL.model.skill.species.SkillSpecies;
import iyunu.NewTLOL.server.battle.BattleServer;
import iyunu.NewTLOL.util.Util;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class 逍遥诀 implements SkillSpecies {

	private 逍遥诀() {
	}

	private static 逍遥诀 instance = new 逍遥诀();

	public static 逍遥诀 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo, BaseSkill baseSkill) {
		List<BattleCharacter> foes = battleInfo.getBattleCharacter(foeIndex, self.getDirection(), baseSkill.getNum());
		if (foes.size() == 0) {
			return true;
		}
		int cost = baseSkill.getCost();
		if (cost > self.getPropertyBattle().getMp()) { // 内力值不足
			return BattleServer.hpNotEnough(self, foeIndex, battleInfo);
		}
		BattleAminInfo battleAminInfo = BattleServer.createBattleAminInfo(battleInfo, self, 0, baseSkill.getId(), cost); // 创建战斗动画对象

		for (BattleCharacter foe : foes) {

			BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
			battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

			if (Util.probable(baseSkill.getProbability())) {
				Iterator<Entry<EBattleBuffType, BattleBuff>> it = foe.getBuffs().entrySet().iterator();
				while (it.hasNext()) {
					Entry<EBattleBuffType, BattleBuff> entry = it.next();
					if (entry.getValue().getDebuff().equals(EBattleBuffEffect.seal)) {
						defenderAmin.getSubBuffs().add(entry.getValue().getId()); // 战斗动画——被攻击者——去除debuff
						it.remove();
					}
				}
			}
			return true;

		}
		return false;
	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, BaseSkill baseSkill, ParamTemp paramTemp) {
		return BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
	}
}
