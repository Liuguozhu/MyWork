package iyunu.NewTLOL.net.protocol.bag;

import iyunu.NewTLOL.manager.PartnerManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.MapMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Drawing;
import iyunu.NewTLOL.model.map.EMapType;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.map.MapServer;

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
public class UseItem extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "";
		cellsMap.clear();

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
			reason = "物品数量不足";
			return;
		}
		if (item.getLevel() > online.getLevel()) {
			result = 1;
			reason = "使用失败，等级不足";
			return;
		}

		if (item.getType().equals(EItem.fragment)) {
			int itemNum = num / 10;
			if (itemNum == 0) {
				result = 1;
				reason = "物品数量不足以合成";
				return;
			}
			num = itemNum * 10;

			// 使用物品
			if (!item.use(online, num, cellsMap)) {
				result = 1;
				reason = "物品不能使用";
				return;
			}

			// LogManager.item(online, item, num, EItemCost.use); // 物品使用日志
			online.getBag().removeByIndex(index, num, cellsMap, EItemCost.use); // 删除物品
			BagMessage.sendBag(online, cellsMap, false);

		} else if (item.getType().equals(EItem.drawing)) {

			if (online.getMapInfo().getBaseMap().getType().equals(EMapType.gangFight) || online.getMapInfo().getBaseMap().getType().equals(EMapType.huntSkill)
					|| online.getMapInfo().getBaseMap().getType().equals(EMapType.raids)) {
				result = 1;
				reason = "当前地图不能使用藏宝图";
				return;
			}

			Drawing drawing = (Drawing) item;

			if (!MapServer.isArrive(online, drawing.getMapId(), drawing.getX(), drawing.getY())) {
				MapMessage.sendTargetSite(online, drawing.getMapId(), drawing.getX(), drawing.getY());
				result = 1;
				reason = "少侠还未到达藏宝图指定地点";
				return;
			}

		} else if (item.getType().equals(EItem.partnerExp)) {
			// 使用物品
			if (!item.use(online, num, cellsMap)) {
				result = 1;
				reason = "伙伴已经已达到当前上限";
				return;
			}

			// LogManager.item(online, item, num, EItemCost.use); // 物品使用日志
			online.getBag().removeByIndex(index, num, cellsMap, EItemCost.use); // 删除物品
			BagMessage.sendBag(online, cellsMap, false);

		} else if (item.getType().equals(EItem.gift) || item.getType().equals(EItem.weaponGift) || item.getType().equals(EItem.xiangzi)) {
			// 使用物品
			if (!item.use(online, num, cellsMap)) {
				result = 1;
				reason = "背包空间不足！";
				return;
			}
			// LogManager.item(online, item, num, EItemCost.use); // 物品使用日志
			online.getBag().removeByIndex(index, num, cellsMap, EItemCost.use); // 删除物品
			BagMessage.sendBag(online, cellsMap);

		} else if (item.getType().equals(EItem.partnerGift)) {
			if (online.getPartnerMap().size() >= PartnerManager.MAX_NUM) {
				result = 1;
				reason = "伙伴已满！";
				return;
			}
			if (!item.use(online, num, cellsMap)) {
				result = 1;
				reason = "伙伴背包不足！";
				return;
			}
			online.getBag().removeByIndex(index, num, cellsMap, EItemCost.use); // 删除物品
			BagMessage.sendBag(online, cellsMap, false);
		} else if (item.getType().equals(EItem.yingxiongtie)) {
			if (online.getYingxiongtie() != null) {
				result = 1;
				reason = "已有英雄帖任务";
				return;
			}

			if (!item.use(online, 1, cellsMap)) {
				result = 1;
				reason = "物品不能使用！";
				return;
			}
			online.getBag().removeByIndex(index, num, cellsMap, EItemCost.use); // 删除物品
			BagMessage.sendBag(online, cellsMap, false);
		} else {
			// 使用物品
			if (!item.use(online, num, cellsMap)) {
				result = 1;
				reason = "物品不能使用";
				return;
			}

			// LogManager.item(online, item, num, EItemCost.use); // 物品使用日志
			online.getBag().removeByIndex(index, num, cellsMap, EItemCost.use); // 删除物品
			BagMessage.sendBag(online, cellsMap, false);
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_useItem");
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