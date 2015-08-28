package iyunu.NewTLOL.model.task.res;

/**
 * 任务级别区间
 * 
 * @author sunhonglei
 * 
 */
public class TaskZoneRes {

	private int zone;
	private int minLevel;
	private int maxLevel;

	/**
	 * @return the zone
	 */
	public int getZone() {
		return zone;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(int zone) {
		this.zone = zone;
	}

	/**
	 * @return the minLevel
	 */
	public int getMinLevel() {
		return minLevel;
	}

	/**
	 * @param minLevel
	 *            the minLevel to set
	 */
	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	/**
	 * @return the maxLevel
	 */
	public int getMaxLevel() {
		return maxLevel;
	}

	/**
	 * @param maxLevel
	 *            the maxLevel to set
	 */
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

}
