package iyunu.NewTLOL.model.skill.species.partner;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.skill.instance.BaseSkill;
import iyunu.NewTLOL.model.skill.species.ParamTemp;
import iyunu.NewTLOL.model.skill.species.SkillSpecies;
import iyunu.NewTLOL.server.battle.BattleServer;

public class 反震 implements SkillSpecies {

	private 反震() {
	}

	private static 反震 instance = new 反震();

	public static 反震 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo, BaseSkill baseSkill) {
		return false;
	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, BaseSkill baseSkill, ParamTemp paramTemp) {
		boolean result = false;
		if (harmType == 0 && !attacker.getPropertyBattle().isQixi()) { // 攻击者没有【奇袭】
			defenderAmin.setSkill(baseSkill.getId()); // 战斗动画——被攻击者——触发被动技能
			int otherHarm = (int) (finalHarm * (baseSkill.getPercent() / 10000f));
			result = BattleServer.hpChange(battleInfo, attacker, attackerAmin, otherHarm, paramTemp);
		}
		if (result) {
			BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
		} else {
			result = BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
		}

		return result;
	}
}
