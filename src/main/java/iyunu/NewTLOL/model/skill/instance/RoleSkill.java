package iyunu.NewTLOL.model.skill.instance;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.EBattleBuffType;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.battle.buff.BattleBuff;
import iyunu.NewTLOL.model.skill.species.ParamTemp;

public class RoleSkill extends BaseSkill {

	/** 等级 **/
	protected int level;
	/** 等级上限 **/
	protected int levelMax;
	/** 特效 **/
	protected int specialEffects;
	/** 位置 **/
	protected int position;
	/** 下一个技能开启编号 **/
	protected int nextSkill;
	/** 前置条件 **/
	protected int limitPosition;
	protected int limitLevel;
	/** 目标(0.全部，1.己方，2.对方) **/
	protected int goal;

	@Override
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

	public RoleSkill copy() {
		RoleSkill skill = new RoleSkill();
		skill.setId(id);
		skill.setName(name);
		skill.setVocation(vocation);
		skill.setLevel(level);
		skill.setLevelMax(levelMax);
		skill.setType(type);
		skill.setDesc(desc);
		skill.setSpecialEffects(specialEffects);
		skill.setProbability(probability);
		skill.setPercent(percent);
		skill.setValue(value);
		skill.setCost(cost);
		skill.setNum(num);
		skill.setPosition(position);
		skill.setNextSkill(nextSkill);
		skill.setBuff(buff);
		skill.setBuffValue(buffValue);
		skill.setDuration(duration);
		skill.setSpecies(species);
		skill.setGoal(goal);

		skill.setLimitPosition(limitPosition);
		skill.setLimitLevel(limitLevel);
		return skill;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevelMax() {
		return levelMax;
	}

	public void setLevelMax(int levelMax) {
		this.levelMax = levelMax;
	}

	public int getSpecialEffects() {
		return specialEffects;
	}

	public void setSpecialEffects(int specialEffects) {
		this.specialEffects = specialEffects;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getNextSkill() {
		return nextSkill;
	}

	public void setNextSkill(int nextSkill) {
		this.nextSkill = nextSkill;
	}

	public int getLimitPosition() {
		return limitPosition;
	}

	public void setLimitPosition(int limitPosition) {
		this.limitPosition = limitPosition;
	}

	public int getLimitLevel() {
		return limitLevel;
	}

	public void setLimitLevel(int limitLevel) {
		this.limitLevel = limitLevel;
	}

	/**
	 * @return the goal
	 */
	public int getGoal() {
		return goal;
	}

	/**
	 * @param goal
	 *            the goal to set
	 */
	public void setGoal(int goal) {
		this.goal = goal;
	}
}
