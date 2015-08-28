package iyunu.NewTLOL.model.role.res;

import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.role.instance.RoleSign;
import iyunu.NewTLOL.util.Translate;

import java.util.HashMap;
import java.util.Map;

public class RoleSignRes {
	int id;// 签到ID
	String itemId;// 签到礼包ID
	int day;

	public RoleSign toRoleSign() {
		String[] strings = itemId.split(";");
		RoleSign rs = new RoleSign();
		Map<Integer, MonsterDropItem> map = new HashMap<>();
		for (int i = 0; i < strings.length; i++) {
			String[] string = strings[i].split(":");
			int itemId = Translate.stringToInt(string[0]);
			int num = Translate.stringToInt(string[1]);
			int bind = Translate.stringToInt(string[2]);
			MonsterDropItem m = new MonsterDropItem(itemId, num, 0, bind);
			map.put(itemId, m);
		}
		rs.setId(id);
		rs.setDay(day);
		rs.setItemIds(map);
		return rs;

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
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
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

}
