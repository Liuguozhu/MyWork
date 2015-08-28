package iyunu.NewTLOL.model.task.taskCycle.instance;

import iyunu.NewTLOL.model.task.TaskAwardItem;
import iyunu.NewTLOL.model.task.TaskAwardPartner;

import java.util.ArrayList;
import java.util.List;

public class TaskCycleAward {

	private int levelMin;
	private int levelMax;
	private int num;
	private List<TaskAwardItem> item = new ArrayList<TaskAwardItem>();
	private List<TaskAwardPartner> partner = new ArrayList<TaskAwardPartner>();

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
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the item
	 */
	public List<TaskAwardItem> getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(List<TaskAwardItem> item) {
		this.item = item;
	}

	/**
	 * @return the partner
	 */
	public List<TaskAwardPartner> getPartner() {
		return partner;
	}

	/**
	 * @param partner
	 *            the partner to set
	 */
	public void setPartner(List<TaskAwardPartner> partner) {
		this.partner = partner;
	}

}
