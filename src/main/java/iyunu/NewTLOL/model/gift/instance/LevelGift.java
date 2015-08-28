package iyunu.NewTLOL.model.gift.instance;

import iyunu.NewTLOL.model.monster.GiftDropItem;

import java.util.ArrayList;

/**
 * @function 等级礼包实例类
 * @author LuoSR
 * @date 2014年4月29日
 */
public class LevelGift {

	/** 等级 **/
	private int level;
	/** 等级礼包物品 **/
	private ArrayList<GiftDropItem> items = new ArrayList<GiftDropItem>();

	public LevelGift copy() {
		LevelGift levelGift = new LevelGift();
		levelGift.setLevel(level);
		levelGift.setItems(items);
		return levelGift;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ArrayList<GiftDropItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<GiftDropItem> items) {
		this.items = items;
	}

}
