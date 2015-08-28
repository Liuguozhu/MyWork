package iyunu.NewTLOL.model.gang.event;

public enum EGangActiveEvent {

	加人(2), //
	每日登录(1), //
	拜神(2), //
	帮派入侵(2), //
	帮战(5), //
	商店兑换(1), //
	退帮(2), //
	活跃税(10), //
	;

	public int active; // 活跃值

	EGangActiveEvent(int active) {
		this.active = active;
	}

	/**
	 * @return the active
	 */
	public int getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(int active) {
		this.active = active;
	}

}
