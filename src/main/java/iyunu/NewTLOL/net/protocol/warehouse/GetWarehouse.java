package iyunu.NewTLOL.net.protocol.warehouse;

import iyunu.NewTLOL.enumeration.EWarehouseEvent;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 取出
 * @author LuoSR
 * @date 2014年2月27日
 */
public class GetWarehouse extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> bagMap = new HashMap<Integer, Cell>();
	private Map<Integer, Cell> warehouseMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "";
		bagMap.clear();
		warehouseMap.clear();

		int index = msg.readInt("index");
		int num = msg.readInt("num");

		Cell cell = online.getWarehouse().getCells()[index];

		Item item = cell.getItem();
		if (item == null) {
			result = 1;
			reason = "物品不存在";
			return;
		}

		if (cell.getNum() < num) {
			result = 1;
			reason = "取出物品数量有误";
			return;
		}

		if (online.getBag().isFull()) {
			result = 1;
			reason = "背包已满";
			return;
		}

		// 背包中添加物品
		BagServer.add(online, item, num, bagMap, EItemGet.getWarehouse);

		// 从仓库中删除物品
		online.getWarehouse().removeByIndex(index, num);
		warehouseMap.put(index, online.getWarehouse().getCells()[index]);

		LogManager.warehouse(online, item, num, EWarehouseEvent.仓库取出物品);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_getWarehouse");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
		if (result == 0) {
			BagMessage.sendWarehouse(online, warehouseMap);
			BagMessage.sendBag(online, bagMap, false);
		}
	}
}