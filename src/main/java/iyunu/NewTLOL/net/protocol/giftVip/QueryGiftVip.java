package iyunu.NewTLOL.net.protocol.giftVip;

import iyunu.NewTLOL.json.GiftJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.model.gift.instance.GiftVip;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 请求VIP礼包
 * @author fhy
 */
public class QueryGiftVip extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_queryGiftVip");

			Map<Integer, GiftVip> map = GiftJson.instance().getGiftVipMap();
			Iterator<GiftVip> it = map.values().iterator();
			while (it.hasNext()) {
				GiftVip giftVip = it.next();
				LlpMessage msg1 = message.write("giftList");

				msg1.write("type", giftVip.getType());
				msg1.write("money", giftVip.getMoney());

				Iterator<Entry<Integer, MonsterDropItem>> itemIt = giftVip.getItems().entrySet().iterator();
				while (itemIt.hasNext()) {
					LlpMessage msg = msg1.write("giftVipList");
					Entry<Integer, MonsterDropItem> entry = itemIt.next();
					msg.write("id", entry.getKey().intValue());
					msg.write("num", entry.getValue().getNum());
					// msg.write("bind", entry.getValue().getIsBind());
					msg.write("icon", ItemJson.instance().getItem(entry.getKey()).getIcon());
				}
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
