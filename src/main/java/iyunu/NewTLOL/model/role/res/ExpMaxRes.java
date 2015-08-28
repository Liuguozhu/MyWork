package iyunu.NewTLOL.model.role.res;

import iyunu.NewTLOL.model.role.instance.ExpMax;

public class ExpMaxRes {

	private int level;
	private int exp;

	public ExpMax toExpMax() {
		ExpMax expMax = new ExpMax();
		expMax.setLevel(level);
		expMax.setExp(exp);
		return expMax;
	}

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

}
