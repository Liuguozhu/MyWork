package iyunu.NewTLOL.model.gift.res;

import iyunu.NewTLOL.model.gift.instance.LevelGift;
import iyunu.NewTLOL.model.monster.GiftDropItem;
import iyunu.NewTLOL.util.Translate;

/**
 * @function 等级礼包实例类
 * @author LuoSR
 * @date 2014年4月29日
 */
public class LevelGiftRes {

	/** 等级 **/
	private int level;
	/** 等级礼包物品 **/
	private String items;

	public LevelGift toLevelGift() {
		LevelGift levelGift = new LevelGift();
		levelGift.setLevel(level);

		String[] stings = items.split(";");
		for (int i = 0; i < stings.length; i++) {
			String[] itemStr = stings[i].split(":");
			levelGift.getItems().add(new GiftDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), 0, Translate.stringToInt(itemStr[2])));
		}
		return levelGift;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

}
