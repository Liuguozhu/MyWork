package iyunu.NewTLOL.model.task.taskCycle.instance;

import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskCycleMsg {

	private int levelMin;
	private int levelMax;
	private int npcIdsSize;
	private int taskIdsSize;
	private int chuanshuoIdsSize;
	private List<Integer> npcIds = new ArrayList<>();
	private List<Integer> taskIds = new ArrayList<>();
	private List<Integer> chuanshuoIds = new ArrayList<>();
	private Map<Integer, Integer> chuanshuoNum = new HashMap<Integer, Integer>();

	public int randomNpc() {
		return npcIds.get(Util.getRandom(npcIdsSize));
	}

	public int randomTask() {
		return taskIds.get(Util.getRandom(taskIdsSize));
	}

	public int randomChuanshuoTask() {
		return chuanshuoIds.get(Util.getRandom(chuanshuoIdsSize));
	}

	/**
	 * 是否传说
	 * 
	 * @param num
	 *            环数
	 * @return
	 */
	public boolean isChuanshuo(int num) {
		if (chuanshuoNum.containsKey(num)) {
			return Util.probable(chuanshuoNum.get(num));
		}
		return false;
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
	 * @return the npcIdsSize
	 */
	public int getNpcIdsSize() {
		return npcIdsSize;
	}

	/**
	 * @param npcIdsSize
	 *            the npcIdsSize to set
	 */
	public void setNpcIdsSize(int npcIdsSize) {
		this.npcIdsSize = npcIdsSize;
	}

	/**
	 * @return the taskIdsSize
	 */
	public int getTaskIdsSize() {
		return taskIdsSize;
	}

	/**
	 * @param taskIdsSize
	 *            the taskIdsSize to set
	 */
	public void setTaskIdsSize(int taskIdsSize) {
		this.taskIdsSize = taskIdsSize;
	}

	/**
	 * @return the npcIds
	 */
	public List<Integer> getNpcIds() {
		return npcIds;
	}

	/**
	 * @param npcIds
	 *            the npcIds to set
	 */
	public void setNpcIds(List<Integer> npcIds) {
		this.npcIds = npcIds;
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

	/**
	 * @return the chuanshuoIds
	 */
	public List<Integer> getChuanshuoIds() {
		return chuanshuoIds;
	}

	/**
	 * @param chuanshuoIds
	 *            the chuanshuoIds to set
	 */
	public void setChuanshuoIds(List<Integer> chuanshuoIds) {
		this.chuanshuoIds = chuanshuoIds;
	}

	/**
	 * @return the chuanshuoIdsSize
	 */
	public int getChuanshuoIdsSize() {
		return chuanshuoIdsSize;
	}

	/**
	 * @param chuanshuoIdsSize
	 *            the chuanshuoIdsSize to set
	 */
	public void setChuanshuoIdsSize(int chuanshuoIdsSize) {
		this.chuanshuoIdsSize = chuanshuoIdsSize;
	}

	/**
	 * @return the chuanshuoNum
	 */
	public Map<Integer, Integer> getChuanshuoNum() {
		return chuanshuoNum;
	}

	/**
	 * @param chuanshuoNum
	 *            the chuanshuoNum to set
	 */
	public void setChuanshuoNum(Map<Integer, Integer> chuanshuoNum) {
		this.chuanshuoNum = chuanshuoNum;
	}

}
