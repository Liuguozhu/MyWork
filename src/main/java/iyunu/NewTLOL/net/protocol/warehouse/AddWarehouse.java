package iyunu.NewTLOL.net.protocol.warehouse;

import iyunu.NewTLOL.enumeration.EWarehouseEvent;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 存入
 * @author LuoSR
 * @date 2014年2月27日
 */
public class AddWarehouse extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
	private Map<Integer, Cell> warehouseMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "";
		cellsMap.clear();
		warehouseMap.clear();

		int index = msg.readInt("index");
		int num = msg.readInt("num");

		Cell cell = online.getBag().getCells()[index];

		Item item = cell.getItem();
		if (item == null) {
			result = 1;
			reason = "物品不存在";
			return;
		}

		if (cell.getNum() < num) {
			result = 1;
			reason = "存入物品数量有误";
			return;
		}

		if (online.getWarehouse().isFull()) {
			result = 1;
			reason = "仓库已满";
			return;
		}

		// 仓库中添加物品
		online.getWarehouse().add(item, num, warehouseMap);

		// 从背包中删除物品
		online.getBag().removeByIndex(index, num, cellsMap, EItemCost.warehouse);

		LogManager.warehouse(online, item, num, EWarehouseEvent.物品存入仓库);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_addWarehouse");
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
			BagMessage.sendBag(online, cellsMap, false);
		}
	}
}