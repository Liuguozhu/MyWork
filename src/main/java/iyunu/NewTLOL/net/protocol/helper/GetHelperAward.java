package iyunu.NewTLOL.net.protocol.helper;

import iyunu.NewTLOL.json.HelperJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.HelperMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.helper.instance.HelperAward;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.vip.EVip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.mail.MailServer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 领取活跃度初始化
 * 
 * @function
 * @author LuoSR
 * @date 2014年5月16日
 */
public class GetHelperAward extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
	private int score = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "领取成功";
		cellsMap.clear();

		score = msg.readInt("score");

		if (online.getLivenessScore() < score) {
			result = 1;
			reason = "活跃度不足  ！";
			return;
		}

		if (online.getLivenessScoreMap().get(score) == null) {
			result = 1;
			reason = "数据有误";
			return;
		}

		if (online.getLivenessScoreMap().get(score) == 0) {
			result = 1;
			reason = "不可领取";
			return;
		}

		if (online.getLivenessScoreMap().get(score) == 2) {
			result = 1;
			reason = "已领取";
			return;
		}

		// 修改礼包状态
		online.getLivenessScoreMap().put(score, 2);

		HelperAward helperAward = HelperJson.instance().getHelperAwardMap().get(score);
		Map<Item, Integer> itemMap = new HashMap<Item, Integer>();

		for (MonsterDropItem monsterDropItem : helperAward.getItems()) {

			Item item = ItemJson.instance().getItem(monsterDropItem.getItemId());
			item.setIsDeal(monsterDropItem.getIsBind());

			if (online.getVip().isVip(EVip.diamond)) {
				itemMap.put(item, monsterDropItem.getNum() * 4);
			} else if (online.getVip().isVip(EVip.platinum)) {
				itemMap.put(item, monsterDropItem.getNum() * 3);
			} else if (online.getVip().isVip(EVip.gold)) {
				itemMap.put(item, monsterDropItem.getNum() * 2);
			} else {
				itemMap.put(item, monsterDropItem.getNum());
			}
		}

		// 背包已满,邮件下发
		if (online.getBag().isFull(itemMap.size())) {
			String content = "活跃度礼包奖励";
			MailServer.send(online.getId(), "活跃度礼包", content, itemMap, 0, 0, 0, 0, null);

			reason = "背包已满,邮件下发";
			return;
		}

		Set<Entry<Item, Integer>> set = itemMap.entrySet();
		for (Iterator<Entry<Item, Integer>> itemIt = set.iterator(); itemIt.hasNext();) {
			Entry<Item, Integer> itemEntry = itemIt.next();
			Item item = itemEntry.getKey().copy();
			int itemNum = itemEntry.getValue();
			BagServer.add(online, item, itemNum, cellsMap, EItemGet.liveness);
		}
		BagMessage.sendBag(online, cellsMap); // 刷新背包
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_getHelperAward");
			message.write("result", result);
			message.write("reason", reason);
			if (result == 0) {
				HelperMessage.refreshHelperAward(online);
				// ======检查活跃度礼包通知======
//				InformMamager.instance().checkHelperAward(online);
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}