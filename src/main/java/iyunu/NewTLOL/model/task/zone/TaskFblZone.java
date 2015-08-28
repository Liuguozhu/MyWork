package iyunu.NewTLOL.model.task.zone;

import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.List;

public class TaskFblZone {

	private int zone; // 区间编号
	private List<Integer> taskIds = new ArrayList<Integer>(); // 任务集合
	
	public int randomTaskId(){
		return taskIds.get(Util.getRandom(taskIds.size()));
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
