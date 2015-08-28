package iyunu.NewTLOL.model.battle.amin;

import java.util.ArrayList;
import java.util.List;

public class BattleAmin {

	/** 索引 **/
	private int index;
	/** 生命值变化量（正数加血，负数减血） **/
	// private int hp = 0;
	private List<Integer> hps = new ArrayList<>();
	/** 内力值变化量 **/
	private int mp = 0;
	/** 是否死亡（1.是，0.否） **/
	private int isDead = 0;
	/** 是否防御（0.否，1.是） **/
	private int isDefense = 0;
	private int crit = 0; // 暴击（0.未暴击，1.暴击）
	private int parry = 0; // 格挡（0.未格挡，1.格挡）
	private int miss = 0; // 闪避（0.未闪避，1.闪避）
	private int buff = 0; // BUFF编号
	private int type; // 操作类型（0.攻，1.防，2.逃跑，3.使用物品，4.召唤伙伴）
	private long skill = 0; // 使用技能编号

	private List<Integer> addBuffs = new ArrayList<Integer>();
	private List<Integer> subBuffs = new ArrayList<Integer>();

	public void addBuff() {

	}

	public void addHp(int hp) {
		hps.add(hp);
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the isDead
	 */
	public int getIsDead() {
		return isDead;
	}

	/**
	 * @param isDead
	 *            the isDead to set
	 */
	public void setIsDead(int isDead) {
		this.isDead = isDead;
	}

	/**
	 * @return the crit
	 */
	public int getCrit() {
		return crit;
	}

	/**
	 * @param crit
	 *            the crit to set
	 */
	public void setCrit(int crit) {
		this.crit = crit;
	}

	/**
	 * @return the parry
	 */
	public int getParry() {
		return parry;
	}

	/**
	 * @param parry
	 *            the parry to set
	 */
	public void setParry(int parry) {
		this.parry = parry;
	}

	/**
	 * @return the isDefense
	 */
	public int getIsDefense() {
		return isDefense;
	}

	/**
	 * @param isDefense
	 *            the isDefense to set
	 */
	public void setIsDefense(int isDefense) {
		if (isDead == 1) {
			setType(1); // 战斗动画——被攻击者——记录死亡
		}
		this.isDefense = isDefense;
	}

	/**
	 * @return the mp
	 */
	public int getMp() {
		return mp;
	}

	/**
	 * @param mp
	 *            the mp to set
	 */
	public void setMp(int mp) {
		this.mp = mp;
	}

	/**
	 * @return the addBuffs
	 */
	public List<Integer> getAddBuffs() {
		return addBuffs;
	}

	/**
	 * @return the subBuffs
	 */
	public List<Integer> getSubBuffs() {
		return subBuffs;
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
	 * @param addBuffs
	 *            the addBuffs to set
	 */
	public void setAddBuffs(List<Integer> addBuffs) {
		this.addBuffs = addBuffs;
	}

	/**
	 * @param subBuffs
	 *            the subBuffs to set
	 */
	public void setSubBuffs(List<Integer> subBuffs) {
		this.subBuffs = subBuffs;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the skill
	 */
	public long getSkill() {
		return skill;
	}

	/**
	 * @param skill
	 *            the skill to set
	 */
	public void setSkill(long skill) {
		this.skill = skill;
	}

	/**
	 * @return the hps
	 */
	public List<Integer> getHps() {
		return hps;
	}

	/**
	 * @return the miss
	 */
	public int getMiss() {
		return miss;
	}

	/**
	 * @param miss
	 *            the miss to set
	 */
	public void setMiss(int miss) {
		this.miss = miss;
	}

}
