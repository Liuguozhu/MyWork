package iyunu.NewTLOL.model.skill.species.gsmr;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.skill.instance.BaseSkill;
import iyunu.NewTLOL.model.skill.species.ParamTemp;
import iyunu.NewTLOL.model.skill.species.SkillSpecies;
import iyunu.NewTLOL.server.battle.BattleServer;
import iyunu.NewTLOL.util.Util;

public class 抵 implements SkillSpecies {

	private 抵() {
	}

	private static 抵 instance = new 抵();

	public static 抵 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo, BaseSkill baseSkill) {
		return false;
	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, BaseSkill baseSkill, ParamTemp paramTemp) {
		if (harmType == 0 && Util.probable(baseSkill.getProbability())) {
			defenderAmin.setSkill(baseSkill.getId()); // 战斗动画——被攻击者——触发被动技能
			finalHarm = (int) (finalHarm * (baseSkill.getPercent() / 10000f));
		}
		return BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
	}
}
