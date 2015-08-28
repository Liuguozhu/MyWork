package iyunu.NewTLOL.model.pay;

import iyunu.NewTLOL.model.monster.MonsterDropItem;

import java.util.ArrayList;

public class PayFirstInfo {

	/** 手充物品 **/
	private ArrayList<MonsterDropItem> items = new ArrayList<MonsterDropItem>();
	private int index; // 伙伴索引
	private int isBind; // 伙伴是否绑定

	public ArrayList<MonsterDropItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<MonsterDropItem> items) {
		this.items = items;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIsBind() {
		return isBind;
	}

	public void setIsBind(int isBind) {
		this.isBind = isBind;
	}

}
