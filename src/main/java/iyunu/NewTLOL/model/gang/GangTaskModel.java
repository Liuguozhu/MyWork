package iyunu.NewTLOL.model.gang;

import java.util.ArrayList;
import java.util.List;

public class GangTaskModel {

	private List<GangTaskSingle> taskList = new ArrayList<GangTaskSingle>();
	/** 最后奖励的index */
	private int awardIndex = 0;
	/** 最后奖励的完成情况 0未领取，1已领取 */
	private int finish = 0;

	public GangTaskModel() {

	}

	/**
	 * @return the awardIndex
	 */
	public int getAwardIndex() {
		return awardIndex;
	}

	/**
	 * @param awardIndex
	 *            the awardIndex to set
	 */
	public void setAwardIndex(int awardIndex) {
		this.awardIndex = awardIndex;
	}

	/**
	 * @return the taskList
	 */
	public List<GangTaskSingle> getTaskList() {
		return taskList;
	}

	/**
	 * @param taskList
	 *            the taskList to set
	 */
	public void setTaskList(List<GangTaskSingle> taskList) {
		this.taskList = taskList;
	}

	/**
	 * @return the finish
	 */
	public int getFinish() {
		return finish;
	}

	/**
	 * @param finish
	 *            the finish to set
	 */
	public void setFinish(int finish) {
		this.finish = finish;
	}

}
