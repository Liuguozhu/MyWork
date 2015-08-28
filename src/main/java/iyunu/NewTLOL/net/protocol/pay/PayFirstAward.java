package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.enumeration.partner.EGetPartner;
import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.manager.InformMamager;
import iyunu.NewTLOL.manager.PartnerManager;
import iyunu.NewTLOL.message.AwardMessage;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.pay.PayFirstInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.partner.PartnerServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 首冲领奖
 * @author LuoSR
 * @date 2014年4月25日
 */
public class PayFirstAward extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
	private List<Partner> partnerList = new ArrayList<>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "首冲领奖成功";
		partnerList.clear();
		cellsMap.clear();
		if (online.getVip().payState() != 1) {
			result = 1;
			reason = "不可领取";
			return;
		}

		Map<Item, Integer> itemMap = new HashMap<Item, Integer>();
		PayFirstInfo payFirstInfo = ActivityJson.instance().getPayFirstInfo();
		if (online.getBag().isFull(payFirstInfo.getItems().size()) || online.getPartnerMap().size() >= PartnerManager.MAX_NUM) {
			result = 1;
			reason = "背包或伙伴已满，请先清空";
			return;
		}

		// 修改领奖状态
		online.getVip().reward();

		for (MonsterDropItem monsterDropItem : payFirstInfo.getItems()) {
			Item item = ItemJson.instance().getItem(monsterDropItem.getItemId());
			item.setIsDeal(monsterDropItem.getIsBind());
			itemMap.put(item, monsterDropItem.getNum());
		}

		// 直接下发
		Set<Entry<Item, Integer>> set = itemMap.entrySet();
		for (Iterator<Entry<Item, Integer>> it = set.iterator(); it.hasNext();) {
			Entry<Item, Integer> entry = it.next();
			BagServer.add(online, entry.getKey(), entry.getValue(), cellsMap, EItemGet.firstPayGift);
		}
		// 刷新物品
		BagMessage.sendBag(online, cellsMap);
		// 添加伙伴
		Partner newPartner = PartnerJson.instance().getNewPartner(payFirstInfo.getIndex());
		newPartner = PartnerServer.addPartner(online, newPartner, EGetPartner.openBox);
		newPartner.setIsBind(payFirstInfo.getIsBind());
		newPartner.setOperateFlag(EpartnerOperate.add);
		partnerList.add(newPartner);
		PartnerMessage.sendPartners(online, partnerList);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_payFirstAward");

			message.write("result", result);
			message.write("reason", reason);
			if (result == 0) {
				// PartnerMessage.sendPartners(online); // 刷新伙伴
				// ======检查首冲礼包通知======
				InformMamager.instance().checkPayFirst(online);
				AwardMessage.sendPayFristState(online); // 刷首冲领奖状态
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
