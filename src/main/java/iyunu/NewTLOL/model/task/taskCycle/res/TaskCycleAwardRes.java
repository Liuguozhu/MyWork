package iyunu.NewTLOL.model.task.taskCycle.res;

import iyunu.NewTLOL.model.task.TaskAwardItem;
import iyunu.NewTLOL.model.task.TaskAwardPartner;
import iyunu.NewTLOL.model.task.taskCycle.instance.TaskCycleAward;
import iyunu.NewTLOL.util.Translate;

public class TaskCycleAwardRes {

	private int levelMin;
	private int levelMax;
	private int num;
	private String item;
	private String partner;

	public TaskCycleAward toTaskCycleAward() {
		TaskCycleAward taskCycleAward = new TaskCycleAward();
		taskCycleAward.setLevelMin(levelMin);
		taskCycleAward.setLevelMax(levelMax);
		taskCycleAward.setNum(num);

		if (item != null && !"".equals(item)) {
			String[] strings = item.split(";");
			for (String string : strings) {
				String[] strs = string.split(":");
				taskCycleAward.getItem().add(new TaskAwardItem(Translate.stringToInt(strs[0]), Translate.stringToInt(strs[1]), Translate.stringToInt(strs[2]), Translate.stringToInt(strs[3])));
			}
		}

		if (partner != null && !"".equals(partner)) {
			String[] strings = partner.split(";");
			for (String string : strings) {
				String[] strs = string.split(":");
				taskCycleAward.getPartner().add(new TaskAwardPartner(Translate.stringToInt(strs[0]), Translate.stringToInt(strs[1]), Translate.stringToInt(strs[2]), Translate.stringToInt(strs[3])));
			}
		}
		return taskCycleAward;
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
	public String getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * @return the partner
	 */
	public String getPartner() {
		return partner;
	}

	/**
	 * @param partner
	 *            the partner to set
	 */
	public void setPartner(String partner) {
		this.partner = partner;
	}

}
