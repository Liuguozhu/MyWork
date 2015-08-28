package iyunu.NewTLOL.model.battle.amin;

public class CopyOfAttackerAmin {

	/** 索引 **/
	private int index;
	/** 血量变化量（正数加血，负数减血） **/
	private int hp = 0;
	private int mp = 0;
	private int buff = 0; // BUFF编号
	/** 是否死亡（1.是，0.否） **/
	private int isDead = 0;
	/** 是否逃跑（1.是，0.否） **/
	private int isEscape = 0;
	private int skill; // 技能编号

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
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @param hp
	 *            the hp to set
	 */
	public void setHp(int hp) {
		this.hp = hp;
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
	 * @return the isEscape
	 */
	public int getIsEscape() {
		return isEscape;
	}

	/**
	 * @param isEscape
	 *            the isEscape to set
	 */
	public void setIsEscape(int isEscape) {
		this.isEscape = isEscape;
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
	 * @return the skill
	 */
	public int getSkill() {
		return skill;
	}

	/**
	 * @param skill
	 *            the skill to set
	 */
	public void setSkill(int skill) {
		this.skill = skill;
	}

}
