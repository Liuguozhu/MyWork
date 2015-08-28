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

import java.util.List;

public class 一阳指 implements SkillSpecies {

	private 一阳指() {
	}

	private static 一阳指 instance = new 一阳指();

	public static 一阳指 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo, BaseSkill baseSkill) {

		List<BattleCharacter> foes = battleInfo.getOppositeBattleCharacter(foeIndex, self.getDirection(), baseSkill.getNum());
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

		boolean result = true;
		for (BattleCharacter foe : foes) {
			BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
			battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

			float harm = BattleForm.harm(self.getPropertyBattle().getMattack(), foe.getPropertyBattle().getMdefence(), baseSkill.getPercent()) + baseSkill.getValue(); // 计算伤害值
			int finalHarm = BattleServer.harm(harm, defenderAmin, self, foe, 1, true); // 计算最终伤害值

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
