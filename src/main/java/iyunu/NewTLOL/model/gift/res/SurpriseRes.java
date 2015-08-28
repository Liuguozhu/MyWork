package iyunu.NewTLOL.model.gift.res;

import iyunu.NewTLOL.model.gift.SurpriseInfo;
import iyunu.NewTLOL.model.gift.instance.Surprise;
import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.util.Translate;

/**
 * 大礼包
 * 
 * @author SunHonglei
 * 
 */
public class SurpriseRes {

	private int id;
	private EOpen reward; // 获取类型
	private String items;

	public Surprise toSurprise() {
		Surprise surprise = new Surprise();
		surprise.setId(id);
		surprise.setReward(reward);

		String[] stings = items.split(";");
		if (reward.equals(EOpen.all)) {
			for (int i = 0; i < stings.length; i++) {
				String[] itemStr = stings[i].split(":");
				int itemId = Translate.stringToInt(itemStr[0]);
				int itemNum = Translate.stringToInt(itemStr[1]);
				surprise.getAllItemMap().put(itemId, itemNum);
			}
		} else if (reward.equals(EOpen.one)) {
			for (int i = 0; i < stings.length; i++) {
				String[] itemStr = stings[i].split(":");
				int itemId = Translate.stringToInt(itemStr[0]);
				int itemNum = Translate.stringToInt(itemStr[1]);
				int probable = Translate.stringToInt(itemStr[2]);
				SurpriseInfo surpriseInfo = new SurpriseInfo();
				surpriseInfo.setItemId(itemId);
				surpriseInfo.setItemNum(itemNum);
				surpriseInfo.setProbable(probable);
				surprise.getOneItemList().add(surpriseInfo);
			}
		}

		return surprise;
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

	/**
	 * @return the items
	 */
	public String getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(String items) {
		this.items = items;
	}

}
