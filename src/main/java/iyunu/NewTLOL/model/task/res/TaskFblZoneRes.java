package iyunu.NewTLOL.model.task.res;

import iyunu.NewTLOL.model.task.zone.TaskFblZone;
import iyunu.NewTLOL.util.Translate;

/**
 * 发布令任务区间
 * 
 * @author SunHonglei
 * 
 */
public class TaskFblZoneRes {

	private int zone;
	private String taskIds;

	public TaskFblZone toTaskFblZone() {
		TaskFblZone taskFblZone = new TaskFblZone();
		taskFblZone.setZone(zone);

		if (taskIds != null && !taskIds.equals("")) {
			String[] strings = taskIds.split(";");
			for (String string : strings) {
				taskFblZone.getTaskIds().add(Translate.stringToInt(string));
			}
		}

		return taskFblZone;
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
