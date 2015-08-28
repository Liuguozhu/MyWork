package iyunu.NewTLOL.net.protocol.intensify;

import iyunu.NewTLOL.enumeration.EIntensifyEvent;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.intensify.instance.Rabbet;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 打孔
 * @author LuoSR
 * @date 2014年3月12日
 */
public class StoneSlot extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "";
		cellsMap.clear();

		int part = msg.readInt("part"); // 部位
		int position = msg.readInt("position"); // 配件位置

		int index = online.getBag().isInBagByType(EItem.dakongqi);
		if (index == -1) {
			result = 1;
			reason = "没有打孔器";
			return;
		}

//		Cell cell = online.getBag().getCells()[index];
//		LogManager.item(online, cell.getItem(), 1, EItemCost.slot); // 物品消耗日志

		// 扣除背包中的打孔器
		online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.slot);

		// 开孔
		EEquip eEquip = EEquip.values()[part];
		Map<Integer, Rabbet> map = online.getBodyRabbet().get(eEquip);
		Rabbet rabbet = map.get(position);
		rabbet.setOpen(1);
		SendMessage.sendBodyRabbet(online);
		BagMessage.sendBag(online, cellsMap, false); // 刷新背包

		LogManager.intensify(online, EIntensifyEvent.打孔, part, position, 0);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_stoneSlot");
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
