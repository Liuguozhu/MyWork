package iyunu.NewTLOL.net.protocol.sign;

import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.RoleJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 领取签到奖励
 * 
 * @author fenghaiyu
 * 
 */
public class PickSign extends TLOLMessageHandler {
	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "礼包领取成功!";
		int pickId = msg.readInt("pickId");
		if (!RoleJson.instance().getRoleSign().keySet().contains(pickId)) {
			result = 1;
			reason = "领取失败，没有此礼包!";
			return;
		}
		if (online.getBag().isFull(1)) {
			result = 1;
			reason = "请先清理背包!";
			return;
		}
		int signSize = online.getSignList().size();

		if (signSize < RoleJson.instance().getRoleSign().get(pickId).getDay()) {
			result = 1;
			reason = "不符合条件";
			return;
		}

		if (online.getHavePickSign().contains(pickId)) {
			result = 1;
			reason = "领取失败，此礼包之前领取过!";
			// System.out.println("领取失败，已经领取过" + "pickID=" + pickId);
			return;
		}
		online.getHavePickSign().add(pickId);

		SendMessage.sendSign(online, pickId, 0);
		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		for (Iterator<Entry<Integer, MonsterDropItem>> it = RoleJson.instance().getRoleSign().get(pickId).getItemIds().entrySet().iterator(); it.hasNext();) {
			Entry<Integer, MonsterDropItem> entry = it.next();
			Item item = ItemJson.instance().getItem(entry.getKey());
			item.setIsDeal(entry.getValue().getIsBind());
			BagServer.add(online, item, entry.getValue().getNum(), cellsMap, EItemGet.signItem);
		}
		// 刷新背包
		BagMessage.sendBag(online, cellsMap, false);
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_pickSign");
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
