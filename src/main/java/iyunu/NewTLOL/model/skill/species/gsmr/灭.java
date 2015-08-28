package iyunu.NewTLOL.model.skill.species.gsmr;

import iyunu.NewTLOL.json.SkillJson;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.battle.amin.BattleAminInfo;
import iyunu.NewTLOL.model.battle.buff.BattleBuff;
import iyunu.NewTLOL.model.skill.instance.BaseSkill;
import iyunu.NewTLOL.model.skill.species.ParamTemp;
import iyunu.NewTLOL.model.skill.species.SkillSpecies;
import iyunu.NewTLOL.server.battle.BattleServer;

public class 灭 implements SkillSpecies {

	private 灭() {
	}

	private static 灭 instance = new 灭();

	public static 灭 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo, BaseSkill baseSkill) {

		if (baseSkill.getCost() > self.getPropertyBattle().getMp()) { // 内力值不足
			return BattleServer.hpNotEnough(self, foeIndex, battleInfo);
		}
		BattleAminInfo battleAminInfo = BattleServer.createBattleAminInfo(battleInfo, self, 0, baseSkill.getId(), baseSkill.getCost()); // 创建战斗动画对象
		BattleCharacter foe = self;
		BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
		battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

		// ======BUFF动画======
		BattleBuff buff = SkillJson.instance().getBattleBuff(baseSkill.getBuff(), baseSkill.getBuffValue(), baseSkill.getDuration());
		buff.setProbability(baseSkill.getProbability());
		if (foe.addBuff(buff)) {
			defenderAmin.getAddBuffs().add(baseSkill.getBuff());
		}
		return false;
	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, BaseSkill baseSkill, ParamTemp paramTemp) {
		return BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
	}
}
