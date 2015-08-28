package iyunu.NewTLOL.net.protocol.seven;

import iyunu.NewTLOL.json.GiftJson;
import iyunu.NewTLOL.manager.OperationManager;
import iyunu.NewTLOL.model.gift.res.SevenGiftRes;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.seven.Seven;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Time;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 开服七天领礼包查询
 * 
 * @author fenghaiyu
 * 
 */
public class QuerySeven extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_querySeven");
			int day = Time.getDaysBetween(OperationManager.OPEN_FU) + 1;
			Seven seven = online.getSevenMap().get(day);

			if (seven != null) {
				message.write("common", seven.getCommon());
				message.write("vip", seven.getVip());
			}
			List<MonsterDropItem> commonList = GiftJson.instance().getCommonGiftByDay(day);
			if (commonList != null) {
				for (MonsterDropItem monsterDropItem : commonList) {
					LlpMessage msg = message.write("commonItem");
					msg.write("id", monsterDropItem.getItemId());
					msg.write("num", monsterDropItem.getNum());
				}
			}

			List<MonsterDropItem> vipList = GiftJson.instance().getVipGiftByDay(day);
			if (vipList != null) {
				for (MonsterDropItem monsterDropItem : vipList) {
					LlpMessage msg = message.write("vipItem");
					msg.write("id", monsterDropItem.getItemId());
					msg.write("num", monsterDropItem.getNum());
				}
			}
			List<MonsterDropItem> commonPList = GiftJson.instance().getCommonPGiftByDay(day);
			if (commonPList != null) {
				for (MonsterDropItem monsterDropItem : commonPList) {
					LlpMessage msg = message.write("commonPartner");
					msg.write("id", monsterDropItem.getItemId());
					msg.write("num", monsterDropItem.getNum());
				}
			}
			List<MonsterDropItem> vipPList = GiftJson.instance().getVipPGiftByDay(day);
			if (vipPList != null) {
				for (MonsterDropItem monsterDropItem : vipPList) {
					LlpMessage msg = message.write("vipPartner");
					msg.write("id", monsterDropItem.getItemId());
					msg.write("num", monsterDropItem.getNum());
				}
			}
			SevenGiftRes sevenGiftRes = GiftJson.instance().getSevenGiftMap().get(day);
			if (sevenGiftRes != null) {
				message.write("commonGold", sevenGiftRes.getCommonG());
				message.write("vipGold", sevenGiftRes.getVipG());
				message.write("commonCoin", sevenGiftRes.getCommonC());
				message.write("vipCoin", sevenGiftRes.getVipC());
				message.write("commonMoney", sevenGiftRes.getCommonM());
				message.write("vipMoney", sevenGiftRes.getVipM());
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
