package iyunu.NewTLOL.net.protocol.bag;

import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Xiangzi;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.util.Util;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 物品使用
 * 
 * @author SunHonglei
 * 
 */
public class OpenXiangzi extends TLOLMessageHandler {

	private int result = 0;
	private String reason;
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
	private String icon;
	private int num;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		icon = "";
		num = 0;
		reason = "使用成功";
		cellsMap.clear();

		int index = msg.readInt("index");

		Cell cell = online.getBag().getCells()[index];

		Item item = cell.getItem();
		if (item == null) {
			result = 1;
			reason = "物品不存在";
			return;
		}
		if (cell.getNum() < 1) {
			result = 1;
			reason = "物品数量不足";
			return;
		}

		if (online.getBag().isFull()) {
			result = 1;
			reason = "背包空间不足";
			return;
		}

		if (!item.getType().equals(EItem.xiangzi)) {
			result = 1;
			reason = "使用失败";
			return;
		}

		Xiangzi xiangzi = (Xiangzi) item;
		if (xiangzi.dropItems().size() <= 0) {
			result = 1;
			reason = "使用失败";
			return;
		}

		// LogManager.item(online, item, 1, EItemCost.use); // 物品使用日志
		online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.use); // 删除物品

		int probable = 0;
		int finalRate = Util.getRandom(10000);
		for (MonsterDropItem dropItem : xiangzi.dropItems()) {
			probable += dropItem.getProbability();
			if (probable > finalRate) {
				Item newItem = ItemJson.instance().getItem(dropItem.getItemId());
				if (newItem != null) {
					newItem.setIsDeal(dropItem.getIsBind());
//					online.getBag().add(newItem, dropItem.getNum(), cellsMap, EItemGet.openXiangzi);
					BagServer.add(online, newItem, dropItem.getNum(), cellsMap, EItemGet.openXiangzi);
					icon = newItem.getIcon();
					num = dropItem.getNum();
					break;
				}
			}
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_openXiangzi");
			message.write("result", result);
			message.write("reason", reason);
			message.write("icon", icon);
			message.write("num", num);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		BagMessage.sendBag(online, cellsMap);
	}
}