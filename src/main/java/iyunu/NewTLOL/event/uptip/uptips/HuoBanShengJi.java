package iyunu.NewTLOL.event.uptip.uptips;

import iyunu.NewTLOL.event.uptip.Uptips;
import iyunu.NewTLOL.model.role.Role;

public class HuoBanShengJi extends Uptips {

	public static final HuoBanShengJi INSTANCE = new HuoBanShengJi();
	public int ordinal = 0;

	/**
	 * 构造函数
	 */
	public HuoBanShengJi() {

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
//			if ("经验伙伴".equals(p.getNick())) {
//				num = num + 1;
//			}
//		}
//		if (num > 1 || (num == 1 && !"经验伙伴".equals(partner.getNick()))) {
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
