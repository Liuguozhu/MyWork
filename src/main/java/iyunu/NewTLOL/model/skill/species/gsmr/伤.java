package iyunu.NewTLOL.model.skill.species.gsmr;

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
import iyunu.NewTLOL.util.Util;

import java.util.List;

public class 伤 implements SkillSpecies {

	private 伤() {
	}

	private static 伤 instance = new 伤();

	public static 伤 instance() {
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
		BattleBuff battleBuff = null;
		if (self.getBuffs().containsKey(EBattleBuffType.b28)) { // 附加持续掉血
			battleBuff = self.getBuffs().get(EBattleBuffType.b28);
		}
		boolean result = true;
		for (BattleCharacter foe : foes) {
			BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
			battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

			float harm = BattleForm.harm(self.getPropertyBattle().getMattack(), foe.getPropertyBattle().getMdefence(), baseSkill.getPercent()) + baseSkill.getValue(); // 计算伤害值
			int finalHarm = BattleServer.harm(harm, defenderAmin, self, foe, 1, true); // 计算最终伤害值

			// 生命值计算
			result = foe.defend(true).defend(battleInfo, foe, self, defenderAmin, battleAminInfo.getAttackerAmin(), finalHarm, battleInfo.getTurn(), 1, null);

			if (battleBuff != null && Util.probable(battleBuff.getProbability())) {
				// ======BUFF动画======
				if (foe.addBuff(SkillJson.instance().getBattleBuff(6, battleBuff.getValue(), 3))) {
					defenderAmin.getAddBuffs().add(6);
				}
			}
		}
		return result;
	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, BaseSkill baseSkill, ParamTemp paramTemp) {
		return BattleServer.hpChange(battleInfo, defender, defenderAmin, finalHarm, paramTemp);
	}
}
