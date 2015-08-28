package iyunu.NewTLOL.model.shenbing.res;

import iyunu.NewTLOL.enumeration.EColor;

public class ShenbingUpStarRes {

	private int index;
	private int steps;
	private EColor color;
	private int star;
	private int rate;
	private int money;
	private int luckLimit; // 幸运值上限
	private int propertyPercent; // 增加属性百分比
	private int levelLimit;

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
	 * @return the steps
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * @param steps
	 *            the steps to set
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}

	/**
	 * @return the color
	 */
	public EColor getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(EColor color) {
		this.color = color;
	}

	/**
	 * @return the rate
	 */
	public int getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(int rate) {
		this.rate = rate;
	}

	/**
	 * @return the star
	 */
	public int getStar() {
		return star;
	}

	/**
	 * @param star
	 *            the star to set
	 */
	public void setStar(int star) {
		this.star = star;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getLuckLimit() {
		return luckLimit;
	}

	public void setLuckLimit(int luckLimit) {
		this.luckLimit = luckLimit;
	}

	public int getPropertyPercent() {
		return propertyPercent;
	}

	public void setPropertyPercent(int propertyPercent) {
		this.propertyPercent = propertyPercent;
	}

	/**
	 * @return the levelLimit
	 */
	public int getLevelLimit() {
		return levelLimit;
	}

	/**
	 * @param levelLimit
	 *            the levelLimit to set
	 */
	public void setLevelLimit(int levelLimit) {
		this.levelLimit = levelLimit;
	}

}
