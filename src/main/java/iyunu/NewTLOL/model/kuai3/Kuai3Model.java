package iyunu.NewTLOL.model.kuai3;

public class Kuai3Model {
	/** 角色ID */
	private long roleId;
	/** 号码 */
	private int num;
	/** 类型 */
	private Kuai3Enum type;
	/** 倍数 */
	private int times;
	/** 是否中奖 */
	private boolean win;

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the type
	 */
	public Kuai3Enum getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Kuai3Enum type) {
		this.type = type;
	}

	/**
	 * @return the times
	 */
	public int getTimes() {
		return times;
	}

	/**
	 * @param times
	 *            the times to set
	 */
	public void setTimes(int times) {
		this.times = times;
	}

	/**
	 * @return the win
	 */
	public boolean isWin() {
		return win;
	}

	/**
	 * @param win
	 *            the win to set
	 */
	public void setWin(boolean win) {
		this.win = win;
	}

	/**
	 * @return the roleId
	 */
	public long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

}
