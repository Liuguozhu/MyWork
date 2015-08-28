package iyunu.NewTLOL.model.gang;

/**
 * @function 帮派战淘汰赛对阵信息
 * @author LuoSR
 * @date 2014年6月25日
 */
public class AgainstOutInfo {
	/** 帮派Id **/
	private long gangId;
	/** 帮派名字 **/
	private String gangName;
	/** 对战列表位置索引 **/
	private int index;

	public long getGangId() {
		return gangId;
	}

	public void setGangId(long gangId) {
		this.gangId = gangId;
	}

	public String getGangName() {
		return gangName;
	}

	public void setGangName(String gangName) {
		this.gangName = gangName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
