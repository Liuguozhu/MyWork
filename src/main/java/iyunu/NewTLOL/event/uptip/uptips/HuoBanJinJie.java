package iyunu.NewTLOL.event.uptip.uptips;

import iyunu.NewTLOL.event.uptip.Uptips;
import iyunu.NewTLOL.model.role.Role;

public class HuoBanJinJie extends Uptips {

	public static final HuoBanJinJie INSTANCE = new HuoBanJinJie();
	public int ordinal = 0;

	/**
	 * 构造函数
	 */
	public HuoBanJinJie() {

	}

	public boolean count(Role role) {
//		Partner partner = role.getPartner();
//		if (partner == null) {
//			return false;
//		}
//		if (role.getPartner().getGrade() >= 4) {
//			return false;
//		}
//		int num = 0;
//		for (Partner p : role.getPartners()) {
//			if (p.getIndex() == partner.getIndex()) {
//				num = num + 1;
//			}
//		}
//		if (num >= 2) {
//			return true;
//		}
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
