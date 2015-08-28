package iyunu.NewTLOL.net.protocol.activity;

import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.MapMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Drawing;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.map.MapServer;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 寻宝获得物品
 * 
 * @author SunHonglei
 * 
 */
public class XiangziGet extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "藏宝图使用成功";

		int index = msg.readInt("index");

		Cell cell = online.getBag().getCells()[index];

		Item item = cell.getItem();
		if (item == null) {
			result = 1;
			reason = "物品不存在";
			return;
		}

		if (item.getType().equals(EItem.drawing)) {
			Drawing drawing = (Drawing) item;

			if (!MapServer.isArrive(online, drawing.getMapId(), drawing.getX(), drawing.getY())) {
				MapMessage.sendTargetSite(online, drawing.getMapId(), drawing.getX(), drawing.getY());
				result = 1;
				reason = "少侠还未到达藏宝图指定地点";
				return;
			}

			// 使用物品
			Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
			if (!item.use(online, 1, cellsMap)) {
				result = 1;
				reason = "物品不能使用";
				return;
			}

			online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.use); // 删除物品
			BagMessage.sendBag(online, cellsMap);
//			LogManager.item(online, item, 1, EItemCost.use); // 物品使用日志
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_drawingGet");
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