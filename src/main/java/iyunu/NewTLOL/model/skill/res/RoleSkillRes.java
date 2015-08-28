package iyunu.NewTLOL.model.skill.res;

import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.model.skill.ESkillSpecies;
import iyunu.NewTLOL.model.skill.ESkillType;
import iyunu.NewTLOL.model.skill.instance.RoleSkill;
import iyunu.NewTLOL.util.Translate;

public class RoleSkillRes {

	/** 技能编号 **/
	protected int id;
	/** 技能名称 **/
	protected String name;
	/** 职业 **/
	protected Vocation vocation;
	/** 等级 **/
	protected int level;
	/** 等级上限 **/
	protected int levelMax;
	/** 技能类型 **/
	protected ESkillType type;
	/** 技能描述 **/
	protected String desc;
	/** 特效 **/
	protected int specialEffects;
	/** 触发概率 **/
	protected int probability;
	/** 伤害百分比 **/
	protected int percent;
	/** 数值 **/
	protected int value;
	/** 消耗内力 **/
	protected int cost;
	/** 位置 **/
	protected int position;
	/** 前置条件 **/
	protected String precondition;
	/** 下一个技能开启编号 **/
	protected int nextSkill;
	/** buff **/
	protected int buff;
	protected int buffValue;
	protected int duration;
	/** 种类 **/
	protected ESkillSpecies species;
	/** 攻击方式 **/
	protected int attackType;
	/** 时间 **/
	protected float time;
	/** 特效播放方式 **/
	protected int playtype;
	/** 攻击人数 **/
	protected int num = 1;
	/** 目标 **/
	protected int goal = 1;
	/** 图标 **/
	private String icon;

	public RoleSkill toRoleSkill() {
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
		skill.setPosition(position);
		skill.setNextSkill(nextSkill);
		skill.setBuff(buff);
		skill.setBuffValue(buffValue);
		skill.setDuration(duration);
		skill.setSpecies(species);
		skill.setNum(num);
		skill.setGoal(goal);
		skill.setLimitPosition(Translate.stringToInt(precondition.split(";")[0]));
		skill.setLimitLevel(Translate.stringToInt(precondition.split(";")[1]));
		return skill;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vocation getVocation() {
		return vocation;
	}

	public void setVocation(Vocation vocation) {
		this.vocation = vocation;
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

	public ESkillType getType() {
		return type;
	}

	public void setType(ESkillType type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getSpecialEffects() {
		return specialEffects;
	}

	public void setSpecialEffects(int specialEffects) {
		this.specialEffects = specialEffects;
	}

	public int getProbability() {
		return probability;
	}

	public void setProbability(int probability) {
		this.probability = probability;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
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

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getPrecondition() {
		return precondition;
	}

	public void setPrecondition(String precondition) {
		this.precondition = precondition;
	}

	public int getNextSkill() {
		return nextSkill;
	}

	public void setNextSkill(int nextSkill) {
		this.nextSkill = nextSkill;
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

	public int getAttackType() {
		return attackType;
	}

	public void setAttackType(int attackType) {
		this.attackType = attackType;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	/**
	 * @return the playtype
	 */
	public int getPlaytype() {
		return playtype;
	}

	/**
	 * @param playtype
	 *            the playtype to set
	 */
	public void setPlaytype(int playtype) {
		this.playtype = playtype;
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

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
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

}
