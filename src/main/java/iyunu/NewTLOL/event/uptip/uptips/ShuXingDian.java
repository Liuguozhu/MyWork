package iyunu.NewTLOL.event.uptip.uptips;

import iyunu.NewTLOL.event.uptip.Uptips;
import iyunu.NewTLOL.model.role.Role;

public class ShuXingDian extends Uptips {

	public static final ShuXingDian INSTANCE = new ShuXingDian();
	public int ordinal = 0;

	/**
	 * 构造函数
	 */
	public ShuXingDian() {

	}

	public boolean count(Role role) {
		if (role.getFree() > 0) {
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
