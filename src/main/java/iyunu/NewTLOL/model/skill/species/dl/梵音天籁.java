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

import java.util.List;

public class 梵音天籁 implements SkillSpecies {

	private 梵音天籁() {
	}

	private static 梵音天籁 instance = new 梵音天籁();

	public static 梵音天籁 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo, BaseSkill baseSkill) {

		List<BattleCharacter> foes = battleInfo.getBattleCharacter(foeIndex, self.getDirection(), baseSkill.getNum());
		if (foes.size() == 0) {
			return true;
		}
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
		if (!battleInfo.isAuto()) { // 自动战斗不同步生命值和内力值、伙伴生命值
			BattleServer.syncHpAndMp(self);
		}

		for (BattleCharacter foe : foes) {
			BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
			battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

			int recoverHp = foe.getPropertyBattle().getHpMax() - foe.getPropertyBattle().getHp();

			defenderAmin.addHp(recoverHp); // 战斗动画——防守者——记录血量变化
			foe.getPropertyBattle().addHp(recoverHp);

			if (!battleInfo.isAuto()) { // 自动战斗不同步生命值和内力值、伙伴生命值
				BattleServer.syncHpAndMp(foe);
			}
		}

		self.getPropertyBattle().addAddPdefence(-1 * (int) (self.getPropertyBattle().getOriginalPdefence() * (baseSkill.getPercent() / 10000f)));
		self.getPropertyBattle().addAddMdefence(-1 * (int) (self.getPropertyBattle().getOriginalMdefence() * (baseSkill.getPercent() / 10000f)));
		return false;

	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, BaseSkill baseSkill, ParamTemp paramTemp) {
		return BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
	}
}
