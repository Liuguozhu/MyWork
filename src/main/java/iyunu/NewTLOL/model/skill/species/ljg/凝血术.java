package iyunu.NewTLOL.model.skill.species.ljg;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.battle.amin.BattleAminInfo;
import iyunu.NewTLOL.model.skill.instance.BaseSkill;
import iyunu.NewTLOL.model.skill.species.ParamTemp;
import iyunu.NewTLOL.model.skill.species.SkillSpecies;
import iyunu.NewTLOL.server.battle.BattleServer;
import iyunu.NewTLOL.util.Util;

import java.util.List;

public class 凝血术 implements SkillSpecies {

	private 凝血术() {
	}

	private static 凝血术 instance = new 凝血术();

	public static 凝血术 instance() {
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

		float maxHarm = self.getCharacter().getLevel() * 100;
		if (battleInfo.getType() == 4) {
			maxHarm = self.getCharacter().getLevel() * 50;
		}

		boolean result = true;
		for (BattleCharacter foe : foes) {
			BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
			battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

			float harm = 0;
			if (Util.probable(baseSkill.getProbability())) {
				harm = foe.getPropertyBattle().getHp() * (baseSkill.getPercent() / 10000f);
				int harmMp = (int) Util.matchSmaller(foe.getPropertyBattle().getMp() * (baseSkill.getPercent() / 10000f), maxHarm);
				defenderAmin.setMp(foe.getPropertyBattle().addMp(harmMp * -1)); // 战斗动画——攻击者——记录内力值消耗
			} else {
				harm = foe.getPropertyBattle().getHp() * 0.05f;
			}
			harm = Util.matchSmaller(harm, maxHarm) + baseSkill.getValue();
			int finalHarm = BattleServer.harm(harm, defenderAmin, self, foe, -1, true); // 计算最终伤害值
			// 生命值计算
			result = foe.defend(true).defend(battleInfo, foe, self, defenderAmin, battleAminInfo.getAttackerAmin(), finalHarm, battleInfo.getTurn(), 1, null);
		}
		return result;
	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, BaseSkill baseSkill, ParamTemp paramTemp) {
		return BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
	}
}
