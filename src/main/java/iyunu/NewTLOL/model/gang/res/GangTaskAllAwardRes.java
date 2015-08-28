package iyunu.NewTLOL.model.gang.res;

import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.Translate;

public class GangTaskAllAwardRes {

	private int index;
	private String item;

	public GangTaskAllAward toGangTaskAllAward() {
		GangTaskAllAward gangTaskAllAward = new GangTaskAllAward();
		gangTaskAllAward.setIndex(index);

		String[] stings = item.split(";");
		for (int i = 0; i < stings.length; i++) {
			String[] itemStr = stings[i].split(":");
			gangTaskAllAward.getItems().add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), 0, Translate.stringToInt(itemStr[2])));
		}
		return gangTaskAllAward;
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

}
