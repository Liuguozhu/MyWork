package iyunu.NewTLOL.model.skill.instance;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.EBattleBuffType;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.battle.buff.BattleBuff;
import iyunu.NewTLOL.model.skill.ESkillCategory;
import iyunu.NewTLOL.model.skill.species.ParamTemp;

public class PartnerSkill extends BaseSkill {

	/** 技能种类 **/
	protected ESkillCategory category;

	public boolean count(BattleCharacter slef, Integer foeIndex, BattleInfo battleInfo) {
		return species.getSpecies().effect(slef, foeIndex, battleInfo, this);
	}

	@Override
	public boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, ParamTemp paramTemp) {
		if (paramTemp == null) {
			paramTemp = new ParamTemp();
		}
		if (attacker.getBuffs().containsKey(EBattleBuffType.b19)) {
			BattleBuff battleBuff = attacker.getBuffs().get(EBattleBuffType.b19);
			finalHarm = (int) (finalHarm * (1 + battleBuff.getValue() / 10000f));
		}
		return species.getSpecies().defend(battleInfo, defender, attacker, defenderAmin, attackerAmin, finalHarm, turn, harmType, this, paramTemp);
	}

	/**
	 * 复制
	 * 
	 * @return 自身对象
	 */
	public PartnerSkill copy() {
		PartnerSkill skill = new PartnerSkill();
		skill.setId(id);
		skill.setName(name);
		skill.setDesc(desc);
		skill.setCategory(category);
		skill.setVocation(vocation);
		skill.setType(type);
		skill.setProbability(probability);
		skill.setPercent(percent);
		skill.setValue(value);
		skill.setBuff(buff);
		skill.setBuffValue(buffValue);
		skill.setDuration(duration);
		skill.setNum(num);
		skill.setSpecies(species);
		return skill;
	}

	/**
	 * @return the category
	 */
	public ESkillCategory getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(ESkillCategory category) {
		this.category = category;
	}

}
