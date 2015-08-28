package iyunu.NewTLOL.model.intensify.instance;

import iyunu.NewTLOL.util.Translate;

/**
 * @function 配件插槽
 * @author LuoSR
 * @date 2014年3月12日
 */
public class Rabbet {

	/** 开启状态 （0未开启，1开启） **/
	private int open = 0;
	/** 插槽中的宝石 **/
	private int stoneId = 0;
	/** 绑定状态 **/
	private int isBing = 0;

	public String encode() {
		return open + "#" + stoneId + "#" + isBing;
	}

	public static Rabbet decode(String string) {
		Rabbet rabbet = new Rabbet();
		if (string != null && !"".equals(string)) {
			String[] strings = string.split("#");
			rabbet.setOpen(Translate.stringToInt(strings[0]));
			rabbet.setStoneId(Translate.stringToInt(strings[1]));
			if (strings.length == 3) {
				rabbet.setIsBing(Translate.stringToInt(strings[2]));
			}
		}
		return rabbet;
	}

	public int getOpen() {
		return open;
	}

	public void setOpen(int open) {
		this.open = open;
	}

	/**
	 * @return the stoneId
	 */
	public int getStoneId() {
		return stoneId;
	}

	/**
	 * @param stoneId
	 *            the stoneId to set
	 */
	public void setStoneId(int stoneId) {
		this.stoneId = stoneId;
	}

	/**
	 * @return the isBing
	 */
	public int getIsBing() {
		return isBing;
	}

	/**
	 * @param isBing
	 *            the isBing to set
	 */
	public void setIsBing(int isBing) {
		this.isBing = isBing;
	}

}
