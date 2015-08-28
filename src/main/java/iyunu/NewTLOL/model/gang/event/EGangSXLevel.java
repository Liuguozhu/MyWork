package iyunu.NewTLOL.model.gang.event;

public enum EGangSXLevel {

	Lv1(1, 2, 7500), //
	Lv2(3, 4, 15000), //
	Lv3(5, 6, 30000), //
	Lv4(7, 8, 60000), //
	Lv5(9, 10, 90000), //
	;

	private int goldBuffId; // 绑银BUFF编号
	private int expBuffId; // 经验BUFF编号
	private int exp; // 升级所需经验

	EGangSXLevel(int goldBuffId, int expBuffId, int exp) {
		this.goldBuffId = goldBuffId;
		this.expBuffId = expBuffId;
		this.exp = exp;
	}

	public static EGangSXLevel getEGangSXLevel(int level) {
		switch (level) {
		case 5:
			return EGangSXLevel.Lv5;
		case 4:
			return EGangSXLevel.Lv4;
		case 3:
			return EGangSXLevel.Lv3;
		case 2:
			return EGangSXLevel.Lv2;
		default:
			return EGangSXLevel.Lv1;
		}
	}

	public static int getExp(int level) {
		switch (level) {
		case 1:
			return EGangSXLevel.Lv1.exp;
		case 2:
			return EGangSXLevel.Lv2.exp;
		case 3:
			return EGangSXLevel.Lv3.exp;
		case 4:
			return EGangSXLevel.Lv4.exp;
		default:
			return EGangSXLevel.Lv5.exp;
		}
	}

	/**
	 * @return the goldBuffId
	 */
	public int getGoldBuffId() {
		return goldBuffId;
	}

	/**
	 * @param goldBuffId
	 *            the goldBuffId to set
	 */
	public void setGoldBuffId(int goldBuffId) {
		this.goldBuffId = goldBuffId;
	}

	/**
	 * @param expBuffId
	 *            the expBuffId to set
	 */
	public void setExpBuffId(int expBuffId) {
		this.expBuffId = expBuffId;
	}

	/**
	 * @return the expBuffId
	 */
	public int getExpBuffId() {
		return expBuffId;
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
