package iyunu.NewTLOL.model.pay;

import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.Translate;

public class PayFirstRes {

	private String items; // 物品
	private String index; // 伙伴

	public PayFirstInfo toPayFirstInfo() {
		PayFirstInfo payFirstInfo = new PayFirstInfo();

		if (items != null && !"".equals(items)) {
			String[] strs = items.split(";");
			for (String string : strs) {
				String[] itemStr = string.split(":");
				payFirstInfo.getItems().add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), 0, Translate.stringToInt(itemStr[2])));
			}
		}
		payFirstInfo.setIndex(Translate.stringToInt(index.split(":")[0]));
		payFirstInfo.setIsBind(Translate.stringToInt(index.split(":")[1]));
		return payFirstInfo;
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

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

}
