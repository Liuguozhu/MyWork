package iyunu.NewTLOL.model.partner.res;


/**
 * 伙伴经验模版
 * 
 * @author SunHonglei
 * 
 */
public class PartnerExpRes {

	private int level; // 等级
	private int exp; // 经验
	private int upExp; // 被吃时返还经验

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
	 * @return the upExp
	 */
	public int getUpExp() {
		return upExp;
	}

	/**
	 * @param upExp
	 *            the upExp to set
	 */
	public void setUpExp(int upExp) {
		this.upExp = upExp;
	}

}
