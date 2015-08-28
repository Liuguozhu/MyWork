package iyunu.NewTLOL.model.skill.species.dl;

import iyunu.NewTLOL.common.BattleForm;
import iyunu.NewTLOL.json.SkillJson;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.battle.amin.BattleAminInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.skill.instance.BaseSkill;
import iyunu.NewTLOL.model.skill.instance.RoleSkill;
import iyunu.NewTLOL.model.skill.species.ParamTemp;
import iyunu.NewTLOL.model.skill.species.SkillSpecies;
import iyunu.NewTLOL.server.battle.BattleServer;
import iyunu.NewTLOL.util.Util;

import java.util.List;

public class 金玉满堂 implements SkillSpecies {

	private 金玉满堂() {
	}

	private static 金玉满堂 instance = new 金玉满堂();

	public static 金玉满堂 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo, BaseSkill baseSkill) {
		List<BattleCharacter> foes = battleInfo.getBattleCharacter(foeIndex, self.getDirection(), baseSkill.getNum());
		if (foes.size() == 0) {
			return true;
		}
		int cost = baseSkill.getCost() * foes.size();
		if (self.getCharacter() instanceof Role) {
			Role role = (Role) self.getCharacter();
			RoleSkill roleSkill = SkillJson.instance().getRoleSkillById(role.getSkillMap().get(8));
			if (roleSkill != null) {
				cost = (int) (cost * (roleSkill.getPercent() / 10000f));
			}
		}
		if (cost > self.getPropertyBattle().getMp()) { // 内力值不足
			return BattleServer.hpNotEnough(self, foeIndex, battleInfo);
		}

		BattleAminInfo battleAminInfo = BattleServer.createBattleAminInfo(battleInfo, self, 0, baseSkill.getId(), cost); // 创建战斗动画对象

		for (BattleCharacter foe : foes) {
			BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
			battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

			int recoverHp = (int) (baseSkill.getValue() + self.getCharacter().getMattack() * (baseSkill.getPercent() / 10000f));

			if (BattleForm.crit(self.getPropertyBattle().getCrit(), self.getPropertyBattle().getSkillAddCrit(), self.getCharacter().getLevel(), foe.getCharacter().getLevel())) { // 暴击
				recoverHp = (int) (recoverHp * 2f);
				defenderAmin.setCrit(1); // 战斗动画——暴击
			}

			recoverHp = Util.matchSmaller(foe.getPropertyBattle().getHpMax() - foe.getPropertyBattle().getHp(), recoverHp);
			defenderAmin.addHp(recoverHp); // 战斗动画——防守者——记录血量变化
			foe.getPropertyBattle().addHp(recoverHp);
		}
		return false;
	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, BaseSkill baseSkill, ParamTemp paramTemp) {
		return BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
	}
}
