package iyunu.NewTLOL.model.task.zone;

import java.util.ArrayList;
import java.util.List;

/**
 * 门派任务区间
 * 
 * @author sunhonglei
 * 
 */
public class TaskGuildZone {

	private int zone;
	private int minLevel;
	private int maxLevel;
	private List<Integer> taskIds = new ArrayList<Integer>();

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

	/**
	 * @return the taskIds
	 */
	public List<Integer> getTaskIds() {
		return taskIds;
	}

	/**
	 * @param taskIds
	 *            the taskIds to set
	 */
	public void setTaskIds(List<Integer> taskIds) {
		this.taskIds = taskIds;
	}

}
