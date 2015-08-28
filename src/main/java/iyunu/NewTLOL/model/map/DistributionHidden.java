package iyunu.NewTLOL.model.map;

/**
 * 暗怪分布
 * 
 * @author SunHonglei
 * 
 */
public class DistributionHidden {

	private int xMin; // 最小x坐标
	private int yMin; // 最小y坐标
	private int xMax; // 最大x坐标
	private int yMax; // 最大y坐标
	private int monsterGroup; // 怪物组合

	/**
	 * 判断是否在该区域范围内
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @return 在范围内
	 */
	public boolean isInHere(int x, int y) {
		if (x >= xMin && x < xMax && y >= yMin && y < yMax) {
			return true;
		}
		return false;
	}

	/**
	 * @return the xMin
	 */
	public int getxMin() {
		return xMin;
	}

	/**
	 * @param xMin
	 *            the xMin to set
	 */
	public void setxMin(int xMin) {
		this.xMin = xMin;
	}

	/**
	 * @return the yMin
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * @param yMin
	 *            the yMin to set
	 */
	public void setyMin(int yMin) {
		this.yMin = yMin;
	}

	/**
	 * @return the xMax
	 */
	public int getxMax() {
		return xMax;
	}

	/**
	 * @param xMax
	 *            the xMax to set
	 */
	public void setxMax(int xMax) {
		this.xMax = xMax;
	}

	/**
	 * @return the yMax
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * @param yMax
	 *            the yMax to set
	 */
	public void setyMax(int yMax) {
		this.yMax = yMax;
	}

	/**
	 * @return the monsterGroup
	 */
	public int getMonsterGroup() {
		return monsterGroup;
	}

	/**
	 * @param monsterGroup
	 *            the monsterGroup to set
	 */
	public void setMonsterGroup(int monsterGroup) {
		this.monsterGroup = monsterGroup;
	}

}
