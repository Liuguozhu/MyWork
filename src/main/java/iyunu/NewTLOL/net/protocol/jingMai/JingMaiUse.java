package iyunu.NewTLOL.net.protocol.jingMai;

import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 经脉使用培元丹
 * 
 * @author SunHonglei
 * 
 */
public class JingMaiUse extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "使用成功";

		int index = online.getBag().isInBagByType(EItem.yuanqi);
		if (index == -1) {
			result = 1;
			reason = "培元丹不足";
			return;
		}

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
		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		// 使用物品
		if (!item.use(online, 1, cellsMap)) {
			result = 1;
			reason = "物品不能使用";
			return;
		}

		online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.use); // 删除物品
		BagMessage.sendBag(online, cellsMap, false);
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_jingMaiUse");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}
