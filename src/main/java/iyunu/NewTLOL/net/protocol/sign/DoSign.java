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
 * 签到
 * 
 * @author fenghaiyu
 * 
 */
public class DoSign extends TLOLMessageHandler {
	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		// System.out.println("进入执行签到--------------------------------------------");
		result = 0;
		reason = "签到成功!";

		if (online.getHaveSign() == 0) {
			result = 1;
			reason = "今日已签到，无需重复签到!";
			// System.out.println("执行签到失败~~~~因为签到状态为已签");
			return;
		}

		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

		// 应该签到的天数
		int day = online.getSignDay();
		Map<Integer, MonsterDropItem> map = RoleJson.instance().getSignItem().get(day).getItemIds();
		if (online.getBag().isFull(map.size())) {
			result = 1;
			reason = "背包不足";
			return;
		}

		// System.out.println("应该签到的天数~~" + day);
		online.getSignList().add(day);
		online.setHaveSign(0);
		// 如果是第一天签到，第一次签到时间存为此时
		if (day == 1) {
			online.setFirstSign(System.currentTimeMillis());
		}
		SendMessage.sendSign(online, 0, day);

		Iterator<Entry<Integer, MonsterDropItem>> it = RoleJson.instance().getSignItem().get(day).getItemIds().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, MonsterDropItem> entry = it.next();
			Item item = ItemJson.instance().getItem(entry.getValue().getItemId());
			item.setIsDeal(entry.getValue().getIsBind());
			BagServer.add(online, item, entry.getValue().getNum(), cellsMap, EItemGet.signItem);
		}
		BagMessage.sendBag(online, cellsMap, true);
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_doSign");
			message.write("result", result);
			message.write("reason", reason);
			// System.out.println("进入执行签到结束 result=" + result + "---reason=" +
			// reason + "------------------------------------");
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
