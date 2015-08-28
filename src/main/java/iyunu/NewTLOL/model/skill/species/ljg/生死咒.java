package iyunu.NewTLOL.model.skill.species.ljg;

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

import java.util.List;

public class 生死咒 implements SkillSpecies {

	private 生死咒() {
	}

	private static 生死咒 instance = new 生死咒();

	public static 生死咒 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo, BaseSkill baseSkill) {

		List<BattleCharacter> foes = battleInfo.getOppositeBattleCharacter(foeIndex, self.getDirection(), baseSkill.getNum());
		if (foes.size() == 0) {
			return true;
		}
		int cost = baseSkill.getCost();
		if (cost > self.getPropertyBattle().getMp()) { // 内力值不足
			return BattleServer.hpNotEnough(self, foeIndex, battleInfo);
		}

		BattleAminInfo battleAminInfo = BattleServer.createBattleAminInfo(battleInfo, self, 0, baseSkill.getId(), cost); // 创建战斗动画对象

		boolean result = true;
		for (BattleCharacter foe : foes) {
			BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
			battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

			int finalHarm = self.getCharacter().getLevel();
			defenderAmin.setMp(foe.getPropertyBattle().addMp(finalHarm * -1)); // 战斗动画——攻击者——记录内力值消耗

			// 生命值计算
			result = foe.defend(true).defend(battleInfo, foe, self, defenderAmin, battleAminInfo.getAttackerAmin(), finalHarm, battleInfo.getTurn(), 1, null);

			// ======BUFF动画======
			BattleBuff buff = SkillJson.instance().getBattleBuff(baseSkill.getBuff(), baseSkill.getBuffValue(), baseSkill.getDuration());
			if (foe.addBuff(buff)) {
				defenderAmin.getAddBuffs().add(baseSkill.getBuff());
				buff.effect(foe, battleInfo); // 计算buff效果
			}
		}
		return result;

	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, BaseSkill baseSkill, ParamTemp paramTemp) {
		return BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
	}
}
