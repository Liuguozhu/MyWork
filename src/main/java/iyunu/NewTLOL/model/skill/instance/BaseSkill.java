package iyunu.NewTLOL.model.skill.instance;

import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.skill.ESkillSpecies;
import iyunu.NewTLOL.model.skill.ESkillType;
import iyunu.NewTLOL.model.skill.species.ParamTemp;

public abstract class BaseSkill {

	/** 技能编号 **/
	protected int id;
	/** 消耗内力 **/
	protected int cost;
	/** 种类 **/
	protected ESkillSpecies species;
	/** 技能名称 **/
	protected String name;
	/** 触发概率 **/
	protected int probability;
	/** 伤害百分比 **/
	protected int percent;
	/** 数值 **/
	protected int value;
	/** 攻击人数 **/
	protected int num = 1;
	/** buff **/
	protected int buff;
	protected int buffValue;
	protected int duration;
	/** 职业 **/
	protected Vocation vocation;

	/** 技能类型 **/
	protected ESkillType type;
	/** 技能描述 **/
	protected String desc;

	public abstract boolean count(BattleCharacter slef, Integer foeIndex, BattleInfo battleInfo);

	public abstract boolean defend(BattleInfo battleInfo, BattleCharacter defender, BattleCharacter attacker, BattleAmin defenderAmin, BattleAmin attackerAmin, int finalHarm, int turn, int harmType, ParamTemp paramTemp);

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	/**
	 * @return the species
	 */
	public ESkillSpecies getSpecies() {
		return species;
	}

	/**
	 * @param species
	 *            the species to set
	 */
	public void setSpecies(ESkillSpecies species) {
		this.species = species;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the probability
	 */
	public int getProbability() {
		return probability;
	}

	/**
	 * @param probability
	 *            the probability to set
	 */
	public void setProbability(int probability) {
		this.probability = probability;
	}

	/**
	 * @return the percent
	 */
	public int getPercent() {
		return percent;
	}

	/**
	 * @param percent
	 *            the percent to set
	 */
	public void setPercent(int percent) {
		this.percent = percent;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the buff
	 */
	public int getBuff() {
		return buff;
	}

	/**
	 * @param buff
	 *            the buff to set
	 */
	public void setBuff(int buff) {
		this.buff = buff;
	}

	/**
	 * @return the type
	 */
	public ESkillType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(ESkillType type) {
		this.type = type;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the buffValue
	 */
	public int getBuffValue() {
		return buffValue;
	}

	/**
	 * @param buffValue
	 *            the buffValue to set
	 */
	public void setBuffValue(int buffValue) {
		this.buffValue = buffValue;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the vocation
	 */
	public Vocation getVocation() {
		return vocation;
	}

	/**
	 * @param vocation
	 *            the vocation to set
	 */
	public void setVocation(Vocation vocation) {
		this.vocation = vocation;
	}

}
