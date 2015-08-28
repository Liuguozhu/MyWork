package iyunu.NewTLOL.net.protocol.activity;

import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.manager.ActivityRotationManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.activity.ActivityRotation;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class Rotatio extends TLOLMessageHandler {

	private int result = 0;
	private String reason;
	private int index = 0;
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		index = 0;
		reason = "开启成功";
		cellsMap.clear();

		int index = online.getBag().isInBagByType(EItem.bangpailing);
		if (index == -1) {
			result = 1;
			reason = "开启失败，帮派令不足";
			return;
		}

		if (online.getGang().getLevel() < 2) {
			result = 1;
			reason = "开启失败，帮派等级小于2级";
			return;
		}

		ActivityRotation activityRotation = ActivityRotationManager.random();
		Item item = ItemJson.instance().getItem(activityRotation.getItemId());

		if (item != null) {
			item.setIsDeal(activityRotation.getIsBind());
//			online.getBag().add(item, activityRotation.getNum(), cellsMap, EItemGet.rotatio);
			BagServer.add(online, item, activityRotation.getNum(), cellsMap, EItemGet.rotatio);
			online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.rotatio); // 删除物品
			BagMessage.sendBag(online, cellsMap);
			this.index = activityRotation.getIndex();
		} else {
			LogManager.exception("转盘脚本错误，物品【" + activityRotation.getItemId() + "】不存在");
			result = 1;
			reason = "开启失败";
			return;
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_rotation");
			message.write("result", result);
			message.write("reason", reason);
			message.write("index", index);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}