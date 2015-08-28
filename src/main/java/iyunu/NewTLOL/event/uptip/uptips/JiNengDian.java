package iyunu.NewTLOL.event.uptip.uptips;

import iyunu.NewTLOL.event.uptip.Uptips;
import iyunu.NewTLOL.model.role.Role;

public class JiNengDian extends Uptips {

	public static final JiNengDian INSTANCE = new JiNengDian();
	public int ordinal = 0;

	/**
	 * 构造函数
	 */
	public JiNengDian() {

	}

	public boolean count(Role role) {
		if (role.getFreeSkill() > 0) {
			role.getUpTipsInit().put(this.getOrdinal(), true);
			return true;
		}
		role.getUpTipsInit().put(this.getOrdinal(), false);
		return false;
	}

	/**
	 * @return the ordinal
	 */
	public int getOrdinal() {
		return ordinal;
	}

	/**
	 * @param ordinal
	 *            the ordinal to set
	 */
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

}
