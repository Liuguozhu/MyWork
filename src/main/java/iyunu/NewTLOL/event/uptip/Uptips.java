package iyunu.NewTLOL.event.uptip;

import iyunu.NewTLOL.model.role.Role;

public class Uptips implements UptipInstance {

	public Uptips() {

	}

	public int ordinal = 0;

	/** 查看是否有变化，有变化需要刷 */
	@Override
	public boolean check(Role role, boolean tag) {
		boolean flag = countBefore(role);
		if (flag != tag) {
			return true;
		}
		return false;
	}

	@Override
	public boolean countBefore(Role role) {
		boolean b = count(role);// 调用的是子类的count方法
		role.getUpTipsInit().put(this.getOrdinal(), b);
		return b;
	}

	public boolean count(Role role) {
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
