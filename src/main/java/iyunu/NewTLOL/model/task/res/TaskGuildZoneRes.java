package iyunu.NewTLOL.model.task.res;

import iyunu.NewTLOL.model.task.zone.TaskGuildZone;
import iyunu.NewTLOL.util.Translate;

/**
 * 门派任务区间
 * 
 * @author sunhonglei
 * 
 */
public class TaskGuildZoneRes {

	private int zone;
	private int minLevel;
	private int maxLevel;
	private String taskIds;

	public TaskGuildZone toTaskGuildZone() {
		TaskGuildZone taskGuildZone = new TaskGuildZone();
		taskGuildZone.setZone(zone);
		taskGuildZone.setMinLevel(minLevel);
		taskGuildZone.setMaxLevel(maxLevel);

		if (taskIds != null && !"".equals(taskIds)) {
			String[] str = taskIds.split(";");
			for (String string : str) {
				int taskId = Translate.stringToInt(string);
				taskGuildZone.getTaskIds().add(taskId);
			}
		}
		return taskGuildZone;
	}

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
	public String getTaskIds() {
		return taskIds;
	}

	/**
	 * @param taskIds
	 *            the taskIds to set
	 */
	public void setTaskIds(String taskIds) {
		this.taskIds = taskIds;
	}

}
