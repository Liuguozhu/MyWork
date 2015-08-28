package iyunu.NewTLOL.model.item.res;

import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Gift;
import iyunu.NewTLOL.model.item.instance.GiftPartner;
import iyunu.NewTLOL.model.item.instance.GiftShizhuang;
import iyunu.NewTLOL.model.item.instance.GiftWeapon;
import iyunu.NewTLOL.model.item.instance.Xiangzi;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.monster.MonsterDropPartner;
import iyunu.NewTLOL.util.Translate;

/**
 * 礼包实例类
 * 
 * @author SunHonglei
 * 
 */
public class ItemGiftRes extends BaseItemRes {

	private EOpen reward;
	private String items;

	public Item toItem() {

		Item item = super.toItem();
		if (item instanceof Gift) {
			Gift gift = (Gift) item;
			gift.setReward(reward);
			if (items != null && !"".equals(items)) {
				String[] str = items.split(";");
				for (String string : str) {
					String[] itemStr = string.split(":");
					gift.dropItems().add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2]), Translate.stringToInt(itemStr[3])));
				}
			}
		} else if (item instanceof GiftPartner) {
			GiftPartner giftPartner = (GiftPartner) item;
			giftPartner.setReward(reward);
			if (items != null && !"".equals(items)) {
				String[] str = items.split(";");
				for (String string : str) {
					String[] itemStr = string.split(":");
					giftPartner.dropItems().add(new MonsterDropPartner(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2]), Translate.stringToInt(itemStr[3])));
				}
			}
		} else if (item instanceof GiftWeapon) {
			GiftWeapon giftWeapon = (GiftWeapon) item;
			giftWeapon.setItems(items);
			giftWeapon.setReward(reward);
		} else if (item instanceof GiftShizhuang) {
			GiftShizhuang giftShizhuang = (GiftShizhuang) item;
			giftShizhuang.setItems(items);
			giftShizhuang.setReward(reward);
		} else if (item instanceof Xiangzi) {
			Xiangzi xiangzi = (Xiangzi) item;
			xiangzi.setReward(reward);
			if (items != null && !"".equals(items)) {
				String[] str = items.split(";");
				for (String string : str) {
					String[] itemStr = string.split(":");
					xiangzi.dropItems().add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2]), Translate.stringToInt(itemStr[3])));
				}
			}
		}

		return item;
	}

	/**
	 * @return the reward
	 */
	public EOpen getReward() {
		return reward;
	}

	/**
	 * @param reward
	 *            the reward to set
	 */
	public void setReward(EOpen reward) {
		this.reward = reward;
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
