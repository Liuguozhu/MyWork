package iyunu.NewTLOL.event.uptip.uptips;

import iyunu.NewTLOL.event.uptip.Uptips;
import iyunu.NewTLOL.json.JingMaiJson;
import iyunu.NewTLOL.model.jingMai.instance.JingMai;
import iyunu.NewTLOL.model.role.Role;

public class YuanQiZhi extends Uptips {

	public static final YuanQiZhi INSTANCE = new YuanQiZhi();
	public int ordinal = 0;

	/**
	 * 构造函数
	 */
	public YuanQiZhi() {

	}

	public boolean count(Role role) {
		JingMai jingMai = JingMaiJson.instance().getJingMai(role.getJingMaiId() + 1);
		if (jingMai == null) {
			return false;
		}
		if (role.getYuanQi() > jingMai.getExpend()) {
			return true;
		}
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
