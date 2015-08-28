package iyunu.NewTLOL.model.base;

/**
 * 内测充值补偿
 * 
 * @author SunHonglei
 * 
 */
public class PayBackInfo {

	private String userId; // 用户编号
	private int amt; // 金额
	private int state; // 状态
	private int multiple; // 倍数

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the amt
	 */
	public int getAmt() {
		return amt;
	}

	/**
	 * @param amt
	 *            the amt to set
	 */
	public void setAmt(int amt) {
		this.amt = amt;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the multiple
	 */
	public int getMultiple() {
		return multiple;
	}

	/**
	 * @param multiple
	 *            the multiple to set
	 */
	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

}
