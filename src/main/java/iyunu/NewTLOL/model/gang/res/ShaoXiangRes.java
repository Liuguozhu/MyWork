package iyunu.NewTLOL.model.gang.res;

import iyunu.NewTLOL.model.gang.event.EGangSXLevel;

public class ShaoXiangRes {

	private int level;// 等级
	private EGangSXLevel type; // 类型
	private int exp;// 升级经验
	private int goldBuff;
	private int expBuff;

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the exp
	 */
	public int getExp() {
		return exp;
	}

	/**
	 * @param exp
	 *            the exp to set
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}

	/**
	 * @return the type
	 */
	public EGangSXLevel getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EGangSXLevel type) {
		this.type = type;
	}

	/**
	 * @return the goldBuff
	 */
	public int getGoldBuff() {
		return goldBuff;
	}

	/**
	 * @param goldBuff
	 *            the goldBuff to set
	 */
	public void setGoldBuff(int goldBuff) {
		this.goldBuff = goldBuff;
	}

	/**
	 * @return the expBuff
	 */
	public int getExpBuff() {
		return expBuff;
	}

	/**
	 * @param expBuff
	 *            the expBuff to set
	 */
	public void setExpBuff(int expBuff) {
		this.expBuff = expBuff;
	}

}
