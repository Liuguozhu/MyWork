package iyunu.NewTLOL.model.skill.res;

import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.model.skill.ESkillCategory;
import iyunu.NewTLOL.model.skill.ESkillSpecies;
import iyunu.NewTLOL.model.skill.ESkillType;
import iyunu.NewTLOL.model.skill.instance.PartnerSkill;

public class PartnerSkillRes {

	/** 技能编号 **/
	private int id;
	/** 技能名称 **/
	private String name;
	/** 技能描述 **/
	private String desc;
	/** 技能种类 **/
	private ESkillCategory category;
	/** 职业 **/
	private Vocation vocation;
	/** 技能类型 **/
	private ESkillType type;
	private int probability; // 触发概率
	private int percent; // 伤害百分比
	private int value; // 数值
	private int buff; // BUFF
	private int buffValue;
	private int duration;
	/** 特效 **/
	private int specialEffects;
	/** 种类 **/
	private ESkillSpecies species;
	private int num; // 攻击人数
	/** 攻击方式 **/
	private int attackType;
	/** 时间 **/
	private float time;
	/** 特效播放方式 **/
	private int playtype;
	/** 图标 **/
	private String icon;

	public PartnerSkill toPartnerSkill() {
		PartnerSkill partnerSkill = new PartnerSkill();
		partnerSkill.setId(id);
		partnerSkill.setName(name);
		partnerSkill.setDesc(desc);
		partnerSkill.setCategory(category);
		partnerSkill.setVocation(vocation);
		partnerSkill.setType(type);
		partnerSkill.setProbability(probability);
		partnerSkill.setPercent(percent);
		partnerSkill.setValue(value);
		partnerSkill.setBuff(buff);
		partnerSkill.setBuffValue(buffValue);
		partnerSkill.setDuration(duration);
		partnerSkill.setSpecies(species);
		partnerSkill.setNum(num);
		return partnerSkill;
	}

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
	 * @return the attackType
	 */
	public int getAttackType() {
		return attackType;
	}

	/**
	 * @param attackType
	 *            the attackType to set
	 */
	public void setAttackType(int attackType) {
		this.attackType = attackType;
	}

	/**
	 * @return the time
	 */
	public float getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
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
	 * @return the specialEffects
	 */
	public int getSpecialEffects() {
		return specialEffects;
	}

	/**
	 * @param specialEffects
	 *            the specialEffects to set
	 */
	public void setSpecialEffects(int specialEffects) {
		this.specialEffects = specialEffects;
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
	 * @param vocation the vocation to set
	 */
	public void setVocation(Vocation vocation) {
		this.vocation = vocation;
	}

}
