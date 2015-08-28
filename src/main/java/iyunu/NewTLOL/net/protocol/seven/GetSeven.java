package iyunu.NewTLOL.net.protocol.seven;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.enumeration.partner.EGetPartner;
import iyunu.NewTLOL.json.GiftJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.manager.OperationManager;
import iyunu.NewTLOL.manager.PartnerManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.gift.res.SevenGiftRes;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.seven.Seven;
import iyunu.NewTLOL.model.vip.EVip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.item.ItemServer;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 开服七天领礼包
 * 
 * @author fenghaiyu
 * 
 */
public class GetSeven extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "领取成功!";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
	private List<Partner> partnerList = new ArrayList<>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "领取成功!";
		cellsMap.clear();
		partnerList.clear();
		int type = msg.readInt("type");

		int day = Time.getDaysBetween(OperationManager.OPEN_FU) + 1;
		if (day >= 8 || day < 1) {
			result = 1;
			reason = "数据异常!";
			return;
		}
		Seven seven = online.getSevenMap().get(day);
		if (seven == null) {
			result = 1;
			reason = "不符合条件!";
			return;
		}

		if (type == 0) {
			if (seven.getCommon() != 1) {
				result = 1;
				reason = "不符合条件!";
				return;
			}

			List<MonsterDropItem> commonGift = GiftJson.instance().getCommonGiftByDay(day);
			if (online.getBag().isFull(ItemServer.getOwnCellCount(commonGift))) {
				result = 1;
				reason = "背包不足!";
				return;
			}
			SevenGiftRes sevenGiftRes = GiftJson.instance().getSevenGiftMap().get(day);
			if (online.getGold() + sevenGiftRes.getCommonG() > RoleManager.MAX_GOLD) {
				result = 1;
				reason = "绑银已达上限!";
				return;
			}

			if (online.getCoin() + sevenGiftRes.getCommonC() > RoleManager.MAX_COIN) {
				result = 1;
				reason = "银两已达上限!";
				return;
			}

			for (MonsterDropItem monsterDropItem : commonGift) {
				Item item = ItemJson.instance().getItem(monsterDropItem.getItemId());
				item.setIsDeal(monsterDropItem.getIsBind());
				// 添加物品
				BagServer.add(online, item, monsterDropItem.getNum(), cellsMap, EItemGet.sevenGift);

				LogManager.activityNew(online.getId(), online.getUserId(), EItemGet.sevenGift.ordinal(), day, item.getId(), monsterDropItem.getNum(), 0, 0, 0, 0);

				// 刷新物品
				BagMessage.sendBag(online, cellsMap);
			}
			Partner partner = null;
			List<MonsterDropItem> commonPGift = GiftJson.instance().getCommonPGiftByDay(day);
			for (MonsterDropItem monsterDropItem : commonPGift) {
				partner = PartnerJson.instance().getPartner(monsterDropItem.getItemId());
				if (partner != null && online.getPartnerMap().size() < PartnerManager.MAX_NUM) {
					partner = PartnerServer.addPartner(online, partner, EGetPartner.seven);
					partner.setOperateFlag(EpartnerOperate.add);
					partnerList.add(partner);
					LogManager.activityNew(online.getId(), online.getUserId(), EItemGet.sevenGift.ordinal(), day, 0, 0, partner.getId(), 0, 0, 0);
				}
			}
			PartnerMessage.sendPartners(online, partnerList);
			RoleServer.addCoin(online, sevenGiftRes.getCommonC(), EGold.seven);
			RoleServer.addGold(online, sevenGiftRes.getCommonG(), EGold.seven);
			RoleServer.addMoney(online, sevenGiftRes.getCommonM(), EMoney.seven);
			LogManager.activityNew(online.getId(), online.getUserId(), EItemGet.sevenGift.ordinal(), day, 0, 0, 0, sevenGiftRes.getCommonG(), sevenGiftRes.getCommonC(), sevenGiftRes.getCommonM());
			seven.setCommon(2);
		} else {
			if (seven.getVip() != 1) {
				result = 1;
				reason = "不符合条件!";
				return;
			}
			if (online.getVip().getLevel() == EVip.common) {
				result = 1;
				reason = "不是VIP!";
				return;
			}
			List<MonsterDropItem> vipGift = GiftJson.instance().getVipGiftByDay(day);

			if (online.getBag().isFull(ItemServer.getOwnCellCount(vipGift))) {
				result = 1;
				reason = "背包不足!";
				return;
			}

			SevenGiftRes sevenGiftRes = GiftJson.instance().getSevenGiftMap().get(day);
			if (online.getGold() + sevenGiftRes.getVipG() > RoleManager.MAX_GOLD) {
				result = 1;
				reason = "绑银已达上限!";
				return;
			}

			if (online.getCoin() + sevenGiftRes.getVipC() > RoleManager.MAX_COIN) {
				result = 1;
				reason = "银两已达上限!";
				return;
			}

			for (MonsterDropItem monsterDropItem : vipGift) {
				Item item = ItemJson.instance().getItem(monsterDropItem.getItemId());
				item.setIsDeal(monsterDropItem.getIsBind());
				// 添加物品
				BagServer.add(online, item, monsterDropItem.getNum(), cellsMap, EItemGet.sevenGift);
				// 刷新物品
				BagMessage.sendBag(online, cellsMap);
			}
			Partner partner = null;
			List<MonsterDropItem> vipPGift = GiftJson.instance().getVipPGiftByDay(day);
			for (MonsterDropItem monsterDropItem : vipPGift) {
				partner = PartnerJson.instance().getPartner(monsterDropItem.getItemId());
				if (partner != null && online.getPartnerMap().size() < PartnerManager.MAX_NUM) {
					PartnerServer.addPartner(online, partner, EGetPartner.seven);
					partner.setOperateFlag(EpartnerOperate.add);
					partnerList.add(partner);
				}
			}
			PartnerMessage.sendPartners(online, partnerList);
			RoleServer.addCoin(online, sevenGiftRes.getVipC(), EGold.seven);
			RoleServer.addGold(online, sevenGiftRes.getVipG(), EGold.seven);
			RoleServer.addMoney(online, sevenGiftRes.getVipM(), EMoney.seven);
			seven.setVip(2);
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_getSeven");
			message.write("result", result);
			message.write("reason", reason);

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
