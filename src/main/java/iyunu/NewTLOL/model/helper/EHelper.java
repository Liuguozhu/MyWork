package iyunu.NewTLOL.model.helper;

public enum EHelper {

	answer(20, 10), // 每日答题
	guild(10, 15), // 门派任务
	ghost(100, 25), // 江湖追杀令
	xuezhan(1, 10), // 参加血战
	yingxiong(5, 10), // 英雄帖
	xunbao(1, 15), // 寻宝
	fbl(1, 15), // 发布令
	raids(1, 10), // 副本
	taskCycle(1, 10), // 环任务
	;

	private int need;
	private int score;

	EHelper(int need, int score) {
		this.need = need;
		this.score = score;
	}

	/**
	 * @return the need
	 */
	public int getNeed() {
		return need;
	}

	/**
	 * @param need
	 *            the need to set
	 */
	public void setNeed(int need) {
		this.need = need;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

}
