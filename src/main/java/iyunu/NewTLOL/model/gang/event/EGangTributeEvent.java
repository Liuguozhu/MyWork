package iyunu.NewTLOL.model.gang.event;

public enum EGangTributeEvent {

	贡酒(50, 50, 50, 2), //
	香炉(80, 80, 80, 4), //
	烧鸡(150, 150, 150, 10), //
	帮派入侵(0, 100, 100, 0), //
	帮战(0, 200, 200, 0), //

	;

	private int exp; // 增加烧香经验
	private int tribute; // 增加个人帮贡
	private int gangTribute; // 增加帮派贡献
	private int gangActivity;// 个人活跃值

	EGangTributeEvent(int exp, int tribute, int gangTribute, int gangActivity) {
		this.exp = exp;
		this.tribute = tribute;
		this.gangTribute = gangTribute;
		this.gangActivity = gangActivity;
	}

	/**
	 * @return the exp
	 */
	public int getExp() {
		return exp;
	}

	/**
	 * @return the tribute
	 */
	public int getTribute() {
		return tribute;
	}

	/**
	 * @return the gangTribute
	 */
	public int getGangTribute() {
		return gangTribute;
	}

	/**
	 * @param exp
	 *            the exp to set
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}

	/**
	 * @param tribute
	 *            the tribute to set
	 */
	public void setTribute(int tribute) {
		this.tribute = tribute;
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
