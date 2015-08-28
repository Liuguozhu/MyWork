package iyunu.NewTLOL.model.gang.res;

import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.Translate;

public class GangTaskRes {

	private int index;
	private String item;
	private String award;
	private int zone;

	public GangTask toGangTask() {
		GangTask gangTask = new GangTask();
		gangTask.setIndex(index);

		String[] stings = item.split(";");
		for (int i = 0; i < stings.length; i++) {
			String[] itemStr = stings[i].split(":");
			gangTask.getItems().add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), 0, Translate.stringToInt(itemStr[2])));
		}

		String[] stings2 = award.split(";");
		for (int i = 0; i < stings2.length; i++) {
			String[] itemStr2 = stings2[i].split(":");
			gangTask.getAwards().add(new MonsterDropItem(Translate.stringToInt(itemStr2[0]), Translate.stringToInt(itemStr2[1]), 0, Translate.stringToInt(itemStr2[2])));
		}

		return gangTask;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
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
	 * @return the zone
	 */
	public int getZone() {
		return zone;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(int zone) {
		this.zone = zone;
	}

}
