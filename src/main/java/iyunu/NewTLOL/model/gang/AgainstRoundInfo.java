package iyunu.NewTLOL.model.gang;

/**
 * @function 帮派战循环赛对阵信息
 * @author LuoSR
 * @date 2014年6月25日
 */
public class AgainstRoundInfo {

	/** 第一个帮派编号 **/
	private long firstGangId;
	/** 第一个帮派名字 **/
	private String firstGangName;
	/** 帮派战积分 **/
	private int firstScore;
	/** 第二个帮派编号 **/
	private long secondGangId;
	/** 第二个帮派名字 **/
	private String secondGangName;
	/** 帮派战积分 **/
	private int secondScore;

	public long getFirstGangId() {
		return firstGangId;
	}

	public void setFirstGangId(long firstGangId) {
		this.firstGangId = firstGangId;
	}

	public String getFirstGangName() {
		return firstGangName;
	}

	public void setFirstGangName(String firstGangName) {
		this.firstGangName = firstGangName;
	}

	public int getFirstScore() {
		return firstScore;
	}

	public void setFirstScore(int firstScore) {
		this.firstScore = firstScore;
	}

	public long getSecondGangId() {
		return secondGangId;
	}

	public void setSecondGangId(long secondGangId) {
		this.secondGangId = secondGangId;
	}

	public String getSecondGangName() {
		return secondGangName;
	}

	public void setSecondGangName(String secondGangName) {
		this.secondGangName = secondGangName;
	}

	public int getSecondScore() {
		return secondScore;
	}

	public void setSecondScore(int secondScore) {
		this.secondScore = secondScore;
	}

}
