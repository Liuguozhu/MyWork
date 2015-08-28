package iyunu.NewTLOL.model.skill.species.sl;

import iyunu.NewTLOL.common.BattleForm;
import iyunu.NewTLOL.json.SkillJson;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.EBattleBuffType;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.battle.amin.BattleAminInfo;
import iyunu.NewTLOL.model.battle.buff.BattleBuff;
import iyunu.NewTLOL.model.skill.instance.BaseSkill;
import iyunu.NewTLOL.model.skill.species.ParamTemp;
import iyunu.NewTLOL.model.skill.species.SkillSpecies;
import iyunu.NewTLOL.server.battle.BattleServer;

public class 亦枯亦荣 implements SkillSpecies {

	private 亦枯亦荣() {
	}

	private static 亦枯亦荣 instance = new 亦枯亦荣();

	public static 亦枯亦荣 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo, BaseSkill baseSkill) {

		BattleCharacter foe = battleInfo.getOppositeBattleCharacter(foeIndex, self.getDirection());
		if (foe == null) {
			return true;
		}
		int cost = baseSkill.getCost();
		if (cost > self.getPropertyBattle().getMp()) { // 内力值不足
			return BattleServer.hpNotEnough(self, foeIndex, battleInfo);
		}

		BattleBuff battleBuff = null;
		if (self.getBuffs().containsKey(EBattleBuffType.b21) && !foe.getPropertyBattle().isFanzhen()) { // 吸血
			battleBuff = self.getBuffs().get(EBattleBuffType.b21);
		}

		BattleAminInfo battleAminInfo = BattleServer.createBattleAminInfo(battleInfo, self, 0, baseSkill.getId(), cost); // 创建战斗动画对象

		BattleAmin defenderAmin1 = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
		battleAminInfo.getDefenderAmins().add(defenderAmin1); // 存储被攻击者动画

		float harm1 = BattleForm.harm(self.getPropertyBattle().getPattack(), foe.getPropertyBattle().getPdefence(), baseSkill.getPercent())+ baseSkill.getValue(); // 计算伤害值
		int finalHarm1 = BattleServer.harm(harm1, defenderAmin1, self, foe, 0, true); // 计算最终伤害值
		// 生命值计算
		ParamTemp paramTemp1 = new ParamTemp();
		boolean result1 = foe.defend(false).defend(battleInfo, foe, self, defenderAmin1, battleAminInfo.getAttackerAmin(), finalHarm1, battleInfo.getTurn(), 0, paramTemp1);

		if (battleBuff != null) { // 吸血
			int selfHarm = (int) (paramTemp1.getFinalHarm() * (battleBuff.getValue() / 10000f));
			selfHarm = self.getPropertyBattle().addHp(selfHarm); // 攻击者——记录血量变化
			battleAminInfo.getAttackerAmin().addHp(selfHarm); // 战斗动画——攻击者——记录血量变化
		}

		// ======BUFF动画======
		if (self.addBuff(SkillJson.instance().getBattleBuff(baseSkill.getBuff(), baseSkill.getBuffValue(), baseSkill.getDuration()))) {
			battleAminInfo.getAttackerAmin().setBuff(baseSkill.getBuff());
		}

		if (result1) {
			return true;
		} else {

			if (!self.isDead()) {
				BattleAmin defenderAmin2 = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
				battleAminInfo.getDefenderAmins().add(defenderAmin2); // 存储被攻击者动画

				float harm2 = harm1 * ((baseSkill.getPercent() + 1000) / 10000f);
				int finalHarm2 = BattleServer.harm(harm2, defenderAmin2, self, foe, 0, true); // 计算最终伤害值
				// 生命值计算
				ParamTemp paramTemp2 = new ParamTemp();
				boolean result2 = foe.defend(false).defend(battleInfo, foe, self, defenderAmin2, battleAminInfo.getAttackerAmin(), finalHarm2, battleInfo.getTurn(), 0, paramTemp2);
				if (battleBuff != null) { // 吸血
					int selfHarm = (int) (paramTemp2.getFinalHarm() * (battleBuff.getValue() / 10000f));
					selfHarm = self.getPropertyBattle().addHp(selfHarm); // 攻击者——记录血量变化
					battleAminInfo.getAttackerAmin().addHp(selfHarm); // 战斗动画——攻击者——记录血量变化
				}
				if (result2) {
					return true;
				} else {

					if (!self.isDead()) {
						BattleAmin defenderAmin3 = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
						battleAminInfo.getDefenderAmins().add(defenderAmin3); // 存储被攻击者动画

						float harm3 = harm1 * ((baseSkill.getPercent() + 2000) / 10000f);
						int finalHarm3 = BattleServer.harm(harm3, defenderAmin3, self, foe, 0, true); // 计算最终伤害值
						// 生命值计算
						ParamTemp paramTemp3 = new ParamTemp();
						boolean result3 = foe.defend(false).defend(battleInfo, foe, self, defenderAmin3, battleAminInfo.getAttackerAmin(), finalHarm3, battleInfo.getTurn(), 0, paramTemp3);
						if (battleBuff != null) { // 吸血
							int selfHarm = (int) (paramTemp3.getFinalHarm() * (battleBuff.getValue() / 10000f));
							selfHarm = self.getPropertyBattle().addHp(selfHarm); // 攻击者——记录血量变化
							battleAminInfo.getAttackerAmin().addHp(selfHarm); // 战斗动画——攻击者——记录血量变化
						}
						return result3;
					} else {
						return true;
					}
				}
			} else {
				return true;
			}
		}
	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, BaseSkill baseSkill, ParamTemp paramTemp) {
		return BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
	}
}
