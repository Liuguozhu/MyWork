package iyunu.NewTLOL.model.skill.species.ljg;

import iyunu.NewTLOL.common.BattleForm;
import iyunu.NewTLOL.json.SkillJson;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.battle.amin.BattleAminInfo;
import iyunu.NewTLOL.model.skill.instance.BaseSkill;
import iyunu.NewTLOL.model.skill.species.ParamTemp;
import iyunu.NewTLOL.model.skill.species.SkillSpecies;
import iyunu.NewTLOL.server.battle.BattleServer;

import java.util.List;

public class 融雪掌 implements SkillSpecies {

	private 融雪掌() {
	}

	private static 融雪掌 instance = new 融雪掌();

	public static 融雪掌 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo, BaseSkill baseSkill) {

		List<BattleCharacter> foes = battleInfo.getOppositeBattleCharacter(foeIndex, self.getDirection(), baseSkill.getNum());
		if (foes.size() == 0) {
			return true;
		}
		int cost = baseSkill.getCost() * foes.size();
		if (cost > self.getPropertyBattle().getMp()) { // 内力值不足
			return BattleServer.hpNotEnough(self, foeIndex, battleInfo);
		}
		BattleAminInfo battleAminInfo = BattleServer.createBattleAminInfo(battleInfo, self, 0, baseSkill.getId(), cost); // 创建战斗动画对象

		for (BattleCharacter foe : foes) {
			BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
			battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

			// ======BUFF动画======
			if (foe.isSeal() && BattleForm.seal(baseSkill.getProbability(), self.getCharacter().getLevel(), foe.getCharacter().getLevel(), self.getPropertyBattle().getHit(), foe.getPropertyBattle().getDodge(), self.getPropertyBattle().getMattack(), foe.getPropertyBattle().getMdefence())) {
				int duration = baseSkill.getDuration();
				if (foe.isBoss()) {
					duration = duration / 2;
				}
				if (foe.addBuff(SkillJson.instance().getBattleBuff(baseSkill.getBuff(), baseSkill.getBuffValue(), duration))) {
					defenderAmin.getAddBuffs().add(baseSkill.getBuff());
				}
			}
		}
		return false;

	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, BaseSkill baseSkill, ParamTemp paramTemp) {
		return BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
	}
}
