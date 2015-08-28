package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.enumeration.partner.EGetPartner;
import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.manager.PartnerManager;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropPartner;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.award.AwardServer;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 伙伴礼包实例类
 * 
 * @author SunHonglei
 * 
 */
public class GiftPartner extends Item {

	private EOpen reward;
	/** 掉落伙伴 **/
	private ArrayList<MonsterDropPartner> dropItems = new ArrayList<MonsterDropPartner>();

	// private String items;

	@Override
	public GiftPartner copy() {
		GiftPartner item = new GiftPartner();
		Item.init(item, this);
		item.setReward(reward);
		item.initDropItems(dropItems);

		return item;
	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		if (this.way == 0 && (this.apply == 0 || this.apply == 1)) {
			if (reward.equals(EOpen.all)) {
				if (role.getPartnerMap().size() + dropItems.size() * num > PartnerManager.MAX_NUM) {
					return false;
				}
				// 发送伙伴
				List<Partner> partnerList = new ArrayList<>();
				for (int i = 0; i < num; i++) {
					for (MonsterDropPartner dropItem : dropItems) {
						Partner partner = PartnerServer.addPartner(role, PartnerJson.instance().getPartner(dropItem.getPartnerIndex()), EGetPartner.openBox);
						partner.setOperateFlag(EpartnerOperate.add);
						partner.setIsBind(dropItem.getIsBind());
						partnerList.add(partner);
						AwardServer.addPartner(role, partner);
					}
				}
				PartnerMessage.sendPartners(role, partnerList);
			} else if (reward.equals(EOpen.one)) {
				if (role.getPartnerMap().size() + dropItems.size() * num > PartnerManager.MAX_NUM) {
					return false;
				}
				// 发送伙伴
				List<Partner> partnerList = new ArrayList<>();
				for (int i = 0; i < num; i++) {
					int probable = 0;
					int finalRate = Util.getRandom(10000);
					for (MonsterDropPartner dropItem : dropItems) {
						probable += dropItem.getProbability();
						if (probable > finalRate) {
							Partner partner = PartnerServer.addPartner(role, PartnerJson.instance().getPartner(dropItem.getPartnerIndex()), EGetPartner.openBox);
							partner.setOperateFlag(EpartnerOperate.add);
							partner.setIsBind(dropItem.getIsBind());
							partnerList.add(partner);
							AwardServer.addPartner(role, partner);
							return true;
						}
					}
				}
				PartnerMessage.sendPartners(role, partnerList);
			}
			return true;
		}
		return false;
	}

	@Override
	public void change() {
		// if (items != null && !"".equals(items)) {
		// String[] str = items.split(";");
		// for (String string : str) {
		// String[] itemStr = string.split(":");
		// dropItems.add(new MonsterDropItem(Translate.stringToInt(itemStr[0]),
		// Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2]),
		// Translate.stringToInt(itemStr[3])));
		// }
		// }
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
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
	 * @return the dropItems
	 */
	public ArrayList<MonsterDropPartner> dropItems() {
		return dropItems;
	}

	/**
	 * @param dropItems
	 *            the dropItems to set
	 */
	public void initDropItems(ArrayList<MonsterDropPartner> dropItems) {
		this.dropItems = dropItems;
	}

	/**
	 * @return the items
	 */
	// public String getItems() {
	// return items;
	// }
	//
	// /**
	// * @param items
	// * the items to set
	// */
	// public void setItems(String items) {
	// this.items = items;
	// }
}
