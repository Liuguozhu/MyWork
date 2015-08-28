package iyunu.NewTLOL.model.skill.species.sl;

import iyunu.NewTLOL.common.BattleForm;
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

import java.util.List;

public class 如来千手法 implements SkillSpecies {

	private 如来千手法() {
	}

	private static 如来千手法 instance = new 如来千手法();

	public static 如来千手法 instance() {
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

		boolean result = true;
		for (BattleCharacter foe : foes) {
			BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
			battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

			float harm = BattleForm.harm(self.getPropertyBattle().getPattack(), foe.getPropertyBattle().getPdefence(), baseSkill.getPercent()) + baseSkill.getValue(); // 内功伤害值
			int finalHarm = BattleServer.harm(harm, defenderAmin, self, foe, 0, true);

			ParamTemp paramTemp = new ParamTemp();
			// 生命值计算
			result = foe.defend(false).defend(battleInfo, foe, self, defenderAmin, battleAminInfo.getAttackerAmin(), finalHarm, battleInfo.getTurn(), 0, paramTemp);

			if (self.getBuffs().containsKey(EBattleBuffType.b21) && !foe.getPropertyBattle().isFanzhen()) { // 吸血
				BattleBuff battleBuff = self.getBuffs().get(EBattleBuffType.b21);
				int selfHarm = (int) (paramTemp.getFinalHarm() * (battleBuff.getValue() / 10000f));
				selfHarm = self.getPropertyBattle().addHp(selfHarm); // 攻击者——记录血量变化
				battleAminInfo.getAttackerAmin().addHp(selfHarm); // 战斗动画——攻击者——记录血量变化
			}
		}
		return result;
	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, BaseSkill baseSkill, ParamTemp paramTemp) {
		return BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
	}
}
