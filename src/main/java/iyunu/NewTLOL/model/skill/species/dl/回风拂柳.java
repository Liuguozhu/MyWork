package iyunu.NewTLOL.model.skill.species.dl;

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

public class 回风拂柳 implements SkillSpecies {

	private 回风拂柳() {
	}

	private static 回风拂柳 instance = new 回风拂柳();

	public static 回风拂柳 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo, BaseSkill baseSkill) {
		BattleCharacter foe = battleInfo.getBattleCharacter(foeIndex, self.getDirection(), true);

		int cost = baseSkill.getCost();
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

		BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
		battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

		if (foe.isDead()) {
			foe.setDead(false);
			int hp = (int) (foe.getPropertyBattle().getHpMax() * (baseSkill.getPercent() / 10000f));
			defenderAmin.addHp(hp); // 战斗动画——防守者——记录血量变化
			foe.getPropertyBattle().setHp(hp);
			defenderAmin.setIsDead(0); // 战斗动画——防守者——记录死亡
			// battleInfo.addCharacterInToOrder(foe); // 添加至战斗队列中
		}
		return false;

	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, BaseSkill baseSkill, ParamTemp paramTemp) {
		return BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
	}
}
