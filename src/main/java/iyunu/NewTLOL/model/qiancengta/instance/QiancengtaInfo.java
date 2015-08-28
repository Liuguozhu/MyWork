package iyunu.NewTLOL.model.qiancengta.instance;

import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;

public class QiancengtaInfo {
	private int id; // 层数编号
	private int groupId; // 怪物组合
	private String des; // 掉落说明
	private int exp; // 掉落经验
	private int gold; // 掉落金钱
	private String photo; // 头像
	/** 掉落物品 **/
	private ArrayList<MonsterDropItem> items = new ArrayList<MonsterDropItem>();

	/**
	 * 物品掉落
	 * 
	 * @return 掉落物品
	 */
	public MonsterDropItem drop() {
		int totleProbability = 0;
		for (MonsterDropItem monsterItem : items) {
			totleProbability += monsterItem.getProbability();
			if (Util.probable(totleProbability)) {
				return monsterItem;
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public ArrayList<MonsterDropItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<MonsterDropItem> items) {
		this.items = items;
	}
}
