package iyunu.NewTLOL.model.role.res;

import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.role.instance.ContinueLogon;
import iyunu.NewTLOL.util.Translate;

import java.util.HashMap;
import java.util.Map;

public class ContinueLogonRes {
	int date;// 连续第几天
	String item;// 礼包ID

	public ContinueLogon toContinueLogon() {
		String[] strings = item.split(";");
		ContinueLogon rs = new ContinueLogon();
		Map<Integer, MonsterDropItem> map = new HashMap<>();

		for (int i = 0; i < strings.length; i++) {
			String[] string = strings[i].split(":");
			int itemId = Translate.stringToInt(string[0]);
			int num = Translate.stringToInt(string[1]);
			int bind = Translate.stringToInt(string[2]);
			MonsterDropItem m = new MonsterDropItem(itemId, num, 0, bind);
			map.put(itemId, m);
		}
		rs.setDate(date);
		rs.setItemIds(map);
		return rs;

	}

	/**
	 * @return the date
	 */
	public int getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(int date) {
		this.date = date;
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
