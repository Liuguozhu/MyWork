package iyunu.NewTLOL.model.role.res;

import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.role.instance.RoleSignItem;
import iyunu.NewTLOL.util.Translate;

import java.util.HashMap;
import java.util.Map;

public class RoleSignItemRes {
	int day;// 签到天数
	String items;// 签到物品

	public RoleSignItem toRoleSignItem() {
		String[] strings = items.split(";");
		RoleSignItem rsi = new RoleSignItem();
		Map<Integer, MonsterDropItem> map = new HashMap<>();
		for (int i = 0; i < strings.length; i++) {
			String[] string = strings[i].split(":");
			int itemId = Translate.stringToInt(string[0]);
			int num = Translate.stringToInt(string[1]);
			int bind = Translate.stringToInt(string[2]);
			MonsterDropItem m = new MonsterDropItem(itemId, num, 0, bind);
			map.put(itemId, m);
		}

		rsi.setDay(day);
		rsi.setItemIds(map);
		return rsi;

	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day
	 *            the day to set
	 */
	public void setDay(int day) {
		this.day = day;
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
