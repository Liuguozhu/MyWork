package iyunu.NewTLOL.model.gang.res;

import iyunu.NewTLOL.model.gang.event.EGangActiveEvent;

/**
 * 帮派活跃值表
 * 
 * @author fenghaiyu
 * 
 */
public class GangActiveEventRes {

	private EGangActiveEvent action;// 行为
	private int exp;// 加的活跃

	/**
	 * @return the action
	 */
	public EGangActiveEvent getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(EGangActiveEvent action) {
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

}
