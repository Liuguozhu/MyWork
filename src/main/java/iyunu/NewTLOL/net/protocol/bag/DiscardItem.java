package iyunu.NewTLOL.net.protocol.bag;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.manager.TaskManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.award.TipServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 丢弃物品
 * @author LSR
 * @date 2012-8-9
 */
public class DiscardItem extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "出售成功";

		int type = msg.readInt("type"); // 1.背包，2.宝石
		int itemIndex = msg.readInt("itemIndex");

		if (type == 1) {

			Cell[] cells = online.getBag().getCells();
			Cell itemCell = cells[itemIndex];

			if (itemCell.getItem() == null) {
				result = 1;
				reason = "物品不存在！";
				return;
			}

			Item item = itemCell.getItem();
			if (item.getIsSell() == 1) {
				result = 1;
				reason = "该物品不可出售！";
				return;
			}
			int num = itemCell.getNum();

			online.getBag().clean(itemIndex);

			// ======收集任务======
			TaskManager.deleteCollectionTaskItem(online, item, num);

			Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
			cellsMap.put(itemIndex, online.getBag().getCells()[itemIndex]);
			RoleServer.addGold(online, item.getReturnGold() * num, EGold.sell);

			BagMessage.sendBag(online, cellsMap, false);

			TipServer.costItem(online, item, num); // ======提示======

			LogManager.item(online, item, num, EItemCost.discard); // 物品消耗日志
			if (item instanceof Equip) {
				Equip equip = (Equip) item;
				LogManager.shenbing(3, equip.getUid(), equip.getSteps(), online, equip.getShowLevel()); // 丢弃神兵日志
			}

		} else if (type == 2) {

			Cell cell = online.getBagStone().getCells()[itemIndex];

			if (cell.getItem() == null) {
				result = 1;
				reason = "物品不存在！";
				return;
			}

			Item item = cell.getItem();
			if (item.getIsSell() == 1) {
				result = 1;
				reason = "该物品不可出售！";
				return;
			}
			int num = cell.getNum();

			online.getBagStone().clean(itemIndex);

			// ======收集任务======
			TaskManager.deleteCollectionTaskItem(online, item, num);
			Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
			cellsMap.put(itemIndex, online.getBag().getCells()[itemIndex]);
			RoleServer.addGold(online, item.getReturnGold() * num, EGold.sell);

			BagMessage.sendBagStone(online, cellsMap);

			LogManager.item(online, item, num, EItemCost.discard); // 物品消耗日志
			
			TipServer.costItem(online, item, num); // ======提示======
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_discardItem");
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
