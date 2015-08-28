package iyunu.NewTLOL.model.gift.instance;

import iyunu.NewTLOL.model.gift.SurpriseInfo;
import iyunu.NewTLOL.model.item.EOpen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @function 大礼包
 * @author LuoSR
 * @date 2014年6月11日
 */
public class Surprise {

	private int id;
	private EOpen reward; // 获取类型

	public Map<Integer, Integer> allItemMap = new HashMap<Integer, Integer>(); // 获取类型为all
	public List<SurpriseInfo> oneItemList = new ArrayList<SurpriseInfo>(); // 获取类型为one

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
	 * @return the reward
	 */
	public EOpen getReward() {
		return reward;
	}

	/**
	 * @param reward
	 *            the reward to set
	 */
	public void setReward(EOpen reward) {
		this.reward = reward;
	}

	public Map<Integer, Integer> getAllItemMap() {
		return allItemMap;
	}

	public List<SurpriseInfo> getOneItemList() {
		return oneItemList;
	}

}
