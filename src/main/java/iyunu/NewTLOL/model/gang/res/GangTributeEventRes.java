package iyunu.NewTLOL.model.gang.res;

import iyunu.NewTLOL.model.gang.event.EGangTributeEvent;

/**
 * 帮派贡献
 * 
 * @author fenghaiyu
 * 
 */
public class GangTributeEventRes {

	private EGangTributeEvent action;// 行为
	private int exp;// 烧香经验
	private int tribute;// 个人帮贡
	private int gangTribute;// 帮派贡献
	private int gangActivity;// 个人贡献值

	/**
	 * @return the action
	 */
	public EGangTributeEvent getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(EGangTributeEvent action) {
		this.action = action;
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
	 * @return the tribute
	 */
	public int getTribute() {
		return tribute;
	}

	/**
	 * @param tribute
	 *            the tribute to set
	 */
	public void setTribute(int tribute) {
		this.tribute = tribute;
	}

	/**
	 * @return the gangTribute
	 */
	public int getGangTribute() {
		return gangTribute;
	}

	/**
	 * @param gangTribute
	 *            the gangTribute to set
	 */
	public void setGangTribute(int gangTribute) {
		this.gangTribute = gangTribute;
	}

	/**
	 * @return the gangActivity
	 */
	public int getGangActivity() {
		return gangActivity;
	}

	/**
	 * @param gangActivity
	 *            the gangActivity to set
	 */
	public void setGangActivity(int gangActivity) {
		this.gangActivity = gangActivity;
	}

}
