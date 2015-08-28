package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.EBattleBuffType;

/**
 * 战斗状态
 * 
 * @author SunHonglei
 * 
 */
public class BattleBuff {

	private int id; // 编号
	private String name; // 名称
	private EBattleBuffType type; // 类型
	private EBattleBuffEffect debuff; // 负面效果
	private String res; // 形象名称
	private int probability; // 概率
	private int value; // 数值
	private int duration; // 剩余持续回合数
	private int skillId; // 技能编号
	private String desc; // 描述

	public boolean effect(BattleCharacter character, BattleInfo battleInfo) {
		return type.getBuff().effect(character, battleInfo, this);
	}

	public BattleBuff copy() {
		BattleBuff battleBuff = new BattleBuff();
		battleBuff.setId(id);
		battleBuff.setName(name);
		battleBuff.setType(type);
		battleBuff.setDebuff(debuff);
		battleBuff.setRes(res);
		battleBuff.setProbability(probability);
		return battleBuff;
	}

	public BattleBuff copy(int value, int duration) {
		BattleBuff battleBuff = new BattleBuff();
		battleBuff.setId(id);
		battleBuff.setName(name);
		battleBuff.setType(type);
		battleBuff.setRes(res);
		battleBuff.setDebuff(debuff);
		battleBuff.setProbability(probability);
		battleBuff.setDuration(duration);
		battleBuff.setValue(value);

		return battleBuff;
	}

	public BattleBuff copy(int value, int duration, int probability) {
		BattleBuff battleBuff = new BattleBuff();
		battleBuff.setId(id);
		battleBuff.setName(name);
		battleBuff.setType(type);
		battleBuff.setRes(res);
		battleBuff.setDebuff(debuff);

		battleBuff.setProbability(probability);
		battleBuff.setDuration(duration);
		battleBuff.setValue(value);

		return battleBuff;
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
	 * @return the type
	 */
	public EBattleBuffType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EBattleBuffType type) {
		this.type = type;
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
	 * @return the debuff
	 */
	public EBattleBuffEffect getDebuff() {
		return debuff;
	}

	/**
	 * @param debuff
	 *            the debuff to set
	 */
	public void setDebuff(EBattleBuffEffect debuff) {
		this.debuff = debuff;
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
	 * @return the res
	 */
	public String getRes() {
		return res;
	}

	/**
	 * @param res
	 *            the res to set
	 */
	public void setRes(String res) {
		this.res = res;
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
	 * @return the skillId
	 */
	public int getSkillId() {
		return skillId;
	}

	/**
	 * @param skillId
	 *            the skillId to set
	 */
	public void setSkillId(int skillId) {
		this.skillId = skillId;
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

}
