package iyunu.NewTLOL.model.task.taskCycle.res;

import iyunu.NewTLOL.model.task.taskCycle.instance.TaskCycleMsg;
import iyunu.NewTLOL.util.Translate;

public class TaskCycleMsgRes {

	private int levelMin;
	private int levelMax;
	private String npcIds;
	private String taskIds;
	private String chuanshuoIds;
	private String chuanshuoNum;

	public TaskCycleMsg toTaskCycleMsg() {
		TaskCycleMsg taskCycleMsg = new TaskCycleMsg();

		taskCycleMsg.setLevelMin(levelMin);
		taskCycleMsg.setLevelMax(levelMax);

		if (npcIds != null && !"".equals(npcIds)) {
			String[] strings = npcIds.split(";");
			taskCycleMsg.setNpcIdsSize(strings.length);
			for (String string : strings) {
				taskCycleMsg.getNpcIds().add(Translate.stringToInt(string));
			}
		}

		if (taskIds != null && !"".equals(taskIds)) {
			String[] strings = taskIds.split(";");
			taskCycleMsg.setTaskIdsSize(strings.length);
			for (String string : strings) {
				taskCycleMsg.getTaskIds().add(Translate.stringToInt(string));
			}
		}

		if (chuanshuoIds != null && !"".equals(chuanshuoIds)) {
			String[] strings = chuanshuoIds.split(";");
			taskCycleMsg.setChuanshuoIdsSize(strings.length);
			for (String string : strings) {
				taskCycleMsg.getChuanshuoIds().add(Translate.stringToInt(string));
			}
		}

		if (chuanshuoNum != null && !"".equals(chuanshuoNum)) {
			String[] strings = chuanshuoNum.split(";");
			for (String string : strings) {
				String[] strs = string.split(":");
				taskCycleMsg.getChuanshuoNum().put(Translate.stringToInt(strs[0]), Translate.stringToInt(strs[1]));
			}
		}
		return taskCycleMsg;
	}

	/**
	 * @return the levelMin
	 */
	public int getLevelMin() {
		return levelMin;
	}

	/**
	 * @param levelMin
	 *            the levelMin to set
	 */
	public void setLevelMin(int levelMin) {
		this.levelMin = levelMin;
	}

	/**
	 * @return the levelMax
	 */
	public int getLevelMax() {
		return levelMax;
	}

	/**
	 * @param levelMax
	 *            the levelMax to set
	 */
	public void setLevelMax(int levelMax) {
		this.levelMax = levelMax;
	}

	/**
	 * @return the npcIds
	 */
	public String getNpcIds() {
		return npcIds;
	}

	/**
	 * @param npcIds
	 *            the npcIds to set
	 */
	public void setNpcIds(String npcIds) {
		this.npcIds = npcIds;
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

	/**
	 * @return the chuanshuoIds
	 */
	public String getChuanshuoIds() {
		return chuanshuoIds;
	}

	/**
	 * @param chuanshuoIds
	 *            the chuanshuoIds to set
	 */
	public void setChuanshuoIds(String chuanshuoIds) {
		this.chuanshuoIds = chuanshuoIds;
	}

	/**
	 * @return the chuanshuoNum
	 */
	public String getChuanshuoNum() {
		return chuanshuoNum;
	}

	/**
	 * @param chuanshuoNum
	 *            the chuanshuoNum to set
	 */
	public void setChuanshuoNum(String chuanshuoNum) {
		this.chuanshuoNum = chuanshuoNum;
	}

}
