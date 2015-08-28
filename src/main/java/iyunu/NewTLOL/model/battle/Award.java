package iyunu.NewTLOL.model.battle;

import java.util.ArrayList;
import java.util.List;

/**
 * 奖励
 * 
 * @author SunHonglei
 * 
 */
public class Award {

	private List<String> awardList = new ArrayList<>();

	/**
	 * 添加奖励
	 * 
	 * @param str
	 *            奖励描述
	 */
	public void addAward(String str) {
		awardList.add(str);
	}

	/**
	 * @return the awardList
	 */
	public List<String> getAwardList() {
		return awardList;
	}

	/**
	 * @param awardList
	 *            the awardList to set
	 */
	public void setAwardList(List<String> awardList) {
		this.awardList = awardList;
	}

}
