package iyunu.NewTLOL.model.partner.res;

import iyunu.NewTLOL.enumeration.EColor;

/**
 * 伙伴评分
 * 
 * @author SunHonglei
 * 
 */
public class PartnerWorthRes {
	private int worth;
	private String title;
	private EColor color;
	private int show;

	/**
	 * @return the worth
	 */
	public int getWorth() {
		return worth;
	}

	/**
	 * @param worth
	 *            the worth to set
	 */
	public void setWorth(int worth) {
		this.worth = worth;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the color
	 */
	public EColor getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(EColor color) {
		this.color = color;
	}

	/**
	 * @return the show
	 */
	public int getShow() {
		return show;
	}

	/**
	 * @param show
	 *            the show to set
	 */
	public void setShow(int show) {
		this.show = show;
	}

}
