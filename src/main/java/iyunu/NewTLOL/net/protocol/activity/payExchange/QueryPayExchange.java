package iyunu.NewTLOL.net.protocol.activity.payExchange;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.manager.PayExchangeManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.activity.PayExchange;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 查询积分榜
 * 
 * @author fhy
 * 
 */
public class QueryPayExchange extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_queryPayExchange");

			llpMessage.write("partnerIndex", ActivityJson.instance().getPayExchangeShow().getPartnerIndex());
			llpMessage.write("despartner", ActivityJson.instance().getPayExchangeShow().getDespartner());
			llpMessage.write("des", ActivityJson.instance().getPayExchangeShow().getDes());
			llpMessage.write("desmoney", ActivityJson.instance().getPayExchangeShow().getDesmoney());

			Iterator<Entry<Integer, PayExchange>> it = ActivityJson.instance().getPayExchange().entrySet().iterator();

			while (it.hasNext()) {
				Entry<Integer, PayExchange> entry = it.next();
				LlpMessage msg = llpMessage.write("payExs");
				msg.write("id", entry.getKey());
				msg.write("partnerIndex", entry.getValue().getPartnerIndex());
				msg.write("left", entry.getValue().getLeft());
				msg.write("right", entry.getValue().getRight());
				for (MonsterDropItem m : entry.getValue().getItemId()) {
					LlpMessage msg1 = msg.write("items");
					msg1.write("itemId", m.getItemId());
					msg1.write("num", m.getNum());
					msg1.write("bind", m.getIsBind());
				}
			}
			channel.write(llpMessage);
			SendMessage.refreshPayExchangeContent(online);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}