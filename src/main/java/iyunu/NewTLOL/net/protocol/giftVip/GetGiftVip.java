package iyunu.NewTLOL.net.protocol.giftVip;

import iyunu.NewTLOL.json.GiftJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.message.AwardMessage;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.vip.EVip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.mail.MailServer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 领取VIP礼包
 * @author fhy
 */
public class GetGiftVip extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {

		result = 0;
		reason = "领取成功";

		int type = msg.readInt("type");
		if (EVip.values()[type] != online.getVip().getLevel()) {
			result = 1;
			reason = "不符合领取条件";
			return;
		}
		if (online.getVipGift() >= type) {
			result = 1;
			reason = "不符合领取条件!!";
			return;
		}
		if (online.getMails().size() > RoleManager.MAX_MAIL) {
			result = 1;
			reason = "邮件已满，请先清理您的邮件!";
			return;
		}

		// 设置领过了
		online.setVipGift(online.getVip().getLevel().ordinal());

		Map<Item, Integer> itemIds = new HashMap<Item, Integer>();
		for (Iterator<Entry<Integer, MonsterDropItem>> it = GiftJson.instance().getGiftVipMap().get(online.getVip().getLevel().ordinal()).getItems().entrySet().iterator(); it.hasNext();) {
			Entry<Integer, MonsterDropItem> entry = it.next();
			Item item = ItemJson.instance().getItem(entry.getKey());
			item.setIsDeal(entry.getValue().getIsBind());
			itemIds.put(item, entry.getValue().getNum());
		}
		// 邮件下发
		MailServer.send(online.getId(), "VIP礼包", "VIP礼包奖励", itemIds, 0, 0, GiftJson.instance().getGiftVipMap().get(online.getVip().getLevel().ordinal()).getMoney(), 0, null);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_getGiftVip");

			message.write("result", result);
			message.write("reason", reason);
			AwardMessage.sendVipGift(online);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
