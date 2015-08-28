package iyunu.NewTLOL.net.protocol.base;

import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.MapJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.map.instance.CollectedInfo;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.model.task.instance.PickTask;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 采集
 * 
 * @author SunHonglei
 * 
 */
public class Pick extends TLOLMessageHandler {

	private int result;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 1;
		reason = "失败";

		if (online.getBag().isFull()) {
			result = 1;
			reason = "采集失败，背包空间不足";
			return;
		}

		int id = msg.readInt("id");

		if (!online.getMapInfo().getBaseMap().getCollecteds().contains(id)) {
			result = 1;
			reason = "采集失败";
			return;
		}

		CollectedInfo collectedInfo = MapJson.instance().getCollectedInfoById(id);

		Item item = ItemJson.instance().getItem(collectedInfo.getItemId());
		if (item == null) {
			result = 1;
			reason = "采集失败，收集物品不存在";
			return;
		}

		for (int taskId : collectedInfo.getTaskIds()) {

			if (!online.getTasks().containsKey(taskId)) {
				continue;
			}

			BaseTask baseTask = online.getTasks().get(taskId);
			if (!baseTask.getState().equals(ETaskState.during)) {
				continue;
			}

			if (!(baseTask instanceof PickTask)) {
				continue;
			}

			PickTask pickTask = (PickTask) baseTask;
			pickTask.add(collectedInfo.getItemId(), 1);

			result = 0;
			reason = "采集成功";
		}

		if (result == 0) {
			// 添加采集到的物品
			Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
//			online.getBag().add(item, 1, cellsMap, EItemGet.pick);
			BagServer.add(online, item, 1, cellsMap, EItemGet.pick);
			BagMessage.sendBag(online, cellsMap);
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_pick");
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