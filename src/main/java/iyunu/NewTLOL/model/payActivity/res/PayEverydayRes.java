package iyunu.NewTLOL.model.payActivity.res;

import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.payActivity.instance.PayEveryday;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Translate;

public class PayEverydayRes {

	private int id;
	private int singleValue;
	private String award;
	private String awardShow;
	private String values;
	private String reward;
	private String startTime;
	private String endTime;

	public PayEveryday toPayEveryday() {
		PayEveryday payEveryday = new PayEveryday();
		payEveryday.setId(id);
		payEveryday.setSingleValue(singleValue);

		if (award != null && !award.equals("")) {
			String[] itemStr = award.split(":");
			payEveryday.setAward(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), 0, Translate.stringToInt(itemStr[2])));
		}

		if (awardShow != null && !awardShow.equals("")) {
			String[] str = awardShow.split(";");
			for (String string : str) {
				String[] itemStr = string.split(":");
				payEveryday.getAwardShow().add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), 0, Translate.stringToInt(itemStr[2])));
			}
		}

		if (values != null && !"".equals(values)) {
			String[] str = values.split(";");
			for (String string : str) {
				payEveryday.getValues().add(Translate.stringToInt(string));
			}
		}

		if (reward != null && !reward.equals("")) {
			String[] str = reward.split(";");
			for (String string : str) {
				String[] itemStr = string.split(":");
				payEveryday.getReward().add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), 0, Translate.stringToInt(itemStr[2])));
			}
		}

		payEveryday.setStartTime(Time.getStrToTime(startTime));
		payEveryday.setEndTime(Time.getStrToTime(endTime));
		return payEveryday;
	}

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
	public String getAward() {
		return award;
	}

	/**
	 * @param award
	 *            the award to set
	 */
	public void setAward(String award) {
		this.award = award;
	}

	/**
	 * @return the awardShow
	 */
	public String getAwardShow() {
		return awardShow;
	}

	/**
	 * @param awardShow
	 *            the awardShow to set
	 */
	public void setAwardShow(String awardShow) {
		this.awardShow = awardShow;
	}

	/**
	 * @return the values
	 */
	public String getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(String values) {
		this.values = values;
	}

	/**
	 * @return the reward
	 */
	public String getReward() {
		return reward;
	}

	/**
	 * @param reward
	 *            the reward to set
	 */
	public void setReward(String reward) {
		this.reward = reward;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
