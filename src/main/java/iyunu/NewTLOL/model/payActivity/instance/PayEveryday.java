package iyunu.NewTLOL.model.payActivity.instance;

import iyunu.NewTLOL.model.monster.MonsterDropItem;

import java.util.ArrayList;
import java.util.List;

public class PayEveryday {

	private int id;
	private int singleValue;
	private MonsterDropItem award;
	private List<MonsterDropItem> awardShow = new ArrayList<MonsterDropItem>();
	private List<Integer> values = new ArrayList<Integer>();
	private List<MonsterDropItem> reward = new ArrayList<MonsterDropItem>();
	private long startTime;
	private long endTime;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the singleValue
	 */
	public int getSingleValue() {
		return singleValue;
	}

	/**
	 * @param singleValue
	 *            the singleValue to set
	 */
	public void setSingleValue(int singleValue) {
		this.singleValue = singleValue;
	}

	/**
	 * @return the award
	 */
	public MonsterDropItem getAward() {
		return award;
	}

	/**
	 * @param award
	 *            the award to set
	 */
	public void setAward(MonsterDropItem award) {
		this.award = award;
	}

	/**
	 * @return the awardShow
	 */
	public List<MonsterDropItem> getAwardShow() {
		return awardShow;
	}

	/**
	 * @param awardShow
	 *            the awardShow to set
	 */
	public void setAwardShow(List<MonsterDropItem> awardShow) {
		this.awardShow = awardShow;
	}

	/**
	 * @return the values
	 */
	public List<Integer> getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(List<Integer> values) {
		this.values = values;
	}

	/**
	 * @return the reward
	 */
	public List<MonsterDropItem> getReward() {
		return reward;
	}

	/**
	 * @param reward
	 *            the reward to set
	 */
	public void setReward(List<MonsterDropItem> reward) {
		this.reward = reward;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

}
