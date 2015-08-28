package iyunu.NewTLOL.model.partner.res;

/**
 * @function 伙伴招募数据模板
 * @author LuoSR
 * @date 2014年2月20日
 */
public class PartnerRecruitRes {

	private int id; // 编号 1普通 2元宝 3十连抽

	private int green;
	private int blue;
	private int purple;
	private int orange;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the green
	 */
	public int getGreen() {
		return green;
	}

	/**
	 * @param green
	 *            the green to set
	 */
	public void setGreen(int green) {
		this.green = green;
	}

	/**
	 * @return the blue
	 */
	public int getBlue() {
		return blue;
	}

	/**
	 * @param blue
	 *            the blue to set
	 */
	public void setBlue(int blue) {
		this.blue = blue;
	}

	/**
	 * @return the purple
	 */
	public int getPurple() {
		return purple;
	}

	/**
	 * @param purple
	 *            the purple to set
	 */
	public void setPurple(int purple) {
		this.purple = purple;
	}

	/**
	 * @return the orange
	 */
	public int getOrange() {
		return orange;
	}

	/**
	 * @param orange
	 *            the orange to set
	 */
	public void setOrange(int orange) {
		this.orange = orange;
	}

}
