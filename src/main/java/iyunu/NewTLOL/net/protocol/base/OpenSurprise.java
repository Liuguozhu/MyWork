package iyunu.NewTLOL.net.protocol.base;

import iyunu.NewTLOL.json.GiftJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.MapJson;
import iyunu.NewTLOL.manager.TeamManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.gift.SurpriseInfo;
import iyunu.NewTLOL.model.gift.instance.Surprise;
import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.map.instance.MapRaidsInfo;
import iyunu.NewTLOL.model.raids.instance.RaidsTeamInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.server.map.MapServer;
import iyunu.NewTLOL.util.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class OpenSurprise extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "开启成功";
		int id = msg.readInt("id");

		if (!online.getSurprise().contains(id)) {
			result = 1;
			reason = "开启失败，礼包不存在";
			return;
		}

		Surprise surprise = GiftJson.instance().getSurpriseMap().get(id);
		if (surprise == null) {
			result = 1;
			reason = "开启失败，礼包不存在";
			return;
		}

		if (surprise.getReward().equals(EOpen.all)) {
			Map<Item, Integer> itemMap = new HashMap<Item, Integer>();
			for (Iterator<Entry<Integer, Integer>> it = surprise.getAllItemMap().entrySet().iterator(); it.hasNext();) {
				Entry<Integer, Integer> entry = it.next();
				Item item = ItemJson.instance().getItem(entry.getKey());
				itemMap.put(item, entry.getValue());
			}
			String content = "副本大礼包奖励";
			MailServer.send(online.getId(), "副本大礼包", content, itemMap, 0, 0, 0, 0, null);
		} else if (surprise.getReward().equals(EOpen.one)) {
			List<SurpriseInfo> itemList = surprise.getOneItemList();
			int probable = 0;
			for (SurpriseInfo surpriseInfo : itemList) {
				probable += surpriseInfo.getProbable();
				int finalRate = Util.getRandom(10000);
				if (finalRate < probable) {
					int itemId = surpriseInfo.getItemId();
					Item item = ItemJson.instance().getItem(itemId);
					int itemMum = surpriseInfo.getItemNum();
					String content = "副本大礼包奖励";
					MailServer.send(online.getId(), "副本大礼包", content, item, itemMum, 0, 0, 0, 0, null, "系统");
					break;
				}
			}
		}

		online.getSurprise().remove(Integer.valueOf(id)); // 删除大礼包

		if (online.getMapInfo().getBaseMap() instanceof MapRaidsInfo) {
			if (online.getTeam() != null) {
				// ======解散队伍======
				TeamManager.instance().removeTeam(online.getTeam());
			}

			MapRaidsInfo mapRaidsInfo = (MapRaidsInfo) online.getMapInfo().getBaseMap();
			RaidsTeamInfo raidsTeamInfo = mapRaidsInfo.getRaidsTeamInfo();
			int x = raidsTeamInfo.getRaidsInfo().getOutX();
			int y = raidsTeamInfo.getRaidsInfo().getOutY();
			MapServer.changeMap(online, x, y, MapJson.instance().getMapById(raidsTeamInfo.getRaidsInfo().getOutMap()), online.getMapInfo().getBaseMap());
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_openSurprise");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (!online.getSurprise().isEmpty()) {
			SendMessage.refreshSurprise(online);
		}
	}
}