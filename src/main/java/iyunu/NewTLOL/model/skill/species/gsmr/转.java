package iyunu.NewTLOL.model.skill.species.gsmr;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.battle.amin.BattleAminInfo;
import iyunu.NewTLOL.model.skill.instance.BaseSkill;
import iyunu.NewTLOL.model.skill.species.ParamTemp;
import iyunu.NewTLOL.model.skill.species.SkillSpecies;
import iyunu.NewTLOL.server.battle.BattleServer;

public class 转 implements SkillSpecies {

	private 转() {
	}

	private static 转 instance = new 转();

	public static 转 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo, BaseSkill baseSkill) {

		

		if ((self.getPropertyBattle().getHp() * 1f / self.getPropertyBattle().getHpMax()) < 0.3f) { // 生命值小于30%
			return BattleServer.hpNotEnough(self, foeIndex, battleInfo);
		}

		BattleAminInfo battleAminInfo = BattleServer.createBattleAminInfo(battleInfo, self, 0, baseSkill.getId(), 0); // 创建战斗动画对象
		BattleCharacter foe = self;

		BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
		battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

		int finalHarm = (int) (foe.getPropertyBattle().getHp() * (baseSkill.getPercent() / 10000f));

		defenderAmin.addHp(finalHarm * -1); // 战斗动画——被攻击者——记录血量变化
		foe.getPropertyBattle().addHp(finalHarm * -1);

		defenderAmin.setMp(foe.getPropertyBattle().addMp(finalHarm)); // 战斗动画——攻击者——记录内力值消耗
		return false;
	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, BaseSkill baseSkill, ParamTemp paramTemp) {
		return BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
	}

}