package iyunu.NewTLOL.net.protocol.activity.daily;

import iyunu.NewTLOL.enumeration.EDaily;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.enumeration.partner.EGetPartner;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.manager.DailyManager;
import iyunu.NewTLOL.manager.PartnerManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.daily.DailyModel;
import iyunu.NewTLOL.model.daily.DailyModelRole;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.award.AwardServer;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.server.role.RoleServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 日常任务领奖
 * 
 * @author fhy
 * 
 */
public class RecDaily extends TLOLMessageHandler {
	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "日常任务领奖成功";
		int id = msg.readInt("id");
		Map<Integer, DailyModelRole> roleDailyMap = online.getDailyMap();
		DailyModel dm = DailyManager.instance().getMap().get(id);
		if (!roleDailyMap.keySet().contains(id) || dm == null) {
			result = 1;
			reason = "领取日常任务不存在";
			return;
		}
		if (roleDailyMap.get(id).getRec() != 1) {
			result = 1;
			reason = "未达成领取条件";
			return;
		}
		if (online.getBag().isFull(dm.getAward().size())) {
			result = 1;
			reason = "请先清理背包";
			return;
		}
		if (dm.getType() == EDaily.物品) {
			if (BagServer.getTheItemNum(dm.getItemId(), online) < dm.getTarget()) {
				result = 1;
				reason = "收集物品不足，不可领取";
				return;
			}

		}
		int partnerIndex = DailyManager.instance().getMap().get(id).getPartner();
		// 如果有伙伴奖励
		Partner p = PartnerJson.instance().getNewPartner(partnerIndex);
		if (p != null) {
			if (online.getPartnerMap().size() >= PartnerManager.MAX_NUM) {
				result = 1;
				reason = "伙伴已满，请先清理伙伴";
				return;
			}
			p.setIsBind(1);
			p = PartnerServer.addPartner(online, p, EGetPartner.daily);
			List<Partner> partnerList = new ArrayList<>();
			p.setOperateFlag(EpartnerOperate.add);
			partnerList.add(p);
			PartnerMessage.sendPartners(online, partnerList);

			AwardServer.addPartner(online, p);
		}
		if (dm.getType() == EDaily.物品) {
			Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
			online.getBag().removeById(dm.getItemId(), dm.getTarget(), cellsMap, EItemCost.gangTask);
			// 刷新背包
			BagMessage.sendBag(online, cellsMap);
		}
		// 设置为已领取
		roleDailyMap.get(id).setRec(2);

		// 发奖励
		List<MonsterDropItem> items = DailyManager.instance().getMap().get(id).getAward();
		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		if (items != null && items.size() > 0) {
			for (MonsterDropItem m : items) {
				Item newItem = ItemJson.instance().getItem(m.getItemId());
				newItem.setIsDeal(m.getIsBind());
				BagServer.add(online, newItem, m.getNum(), cellsMap, EItemGet.daily);
			}
		}
		// 增加元宝
		int money = DailyManager.instance().getMap().get(id).getMoney();
		if (money > 0) {
			RoleServer.addMoney(online, money, EMoney.daily);
		}
		// 刷新背包
		BagMessage.sendBag(online, cellsMap);

		// 刷新已领取
		SendMessage.refreshRecDaily(online, id, roleDailyMap.get(id).getRec());
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_recDaily");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}