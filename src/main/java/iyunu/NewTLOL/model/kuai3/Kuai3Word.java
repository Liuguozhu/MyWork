package iyunu.NewTLOL.model.kuai3;

/**
 * 中奖者 发奖励时用到的。金币，奖励话语 类
 * 
 * @author fenghaiyu
 * 
 */
public class Kuai3Word {
	/** 角色ID */
	private long roleId;
	/** 银两 */
	private int coin;
	/** 话语 */
	private String word;
	/** 结果赢1输0 */
	private int win;

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

	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @return the coin
	 */
	public int getCoin() {
		return coin;
	}

	/**
	 * @param coin
	 *            the coin to set
	 */
	public void setCoin(int coin) {
		this.coin = coin;
	}

	/**
	 * @param word
	 *            the word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * @return the win
	 */
	public int getWin() {
		return win;
	}

	/**
	 * @param win
	 *            the win to set
	 */
	public void setWin(int win) {
		this.win = win;
	}

}
