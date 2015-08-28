package iyunu.NewTLOL.net.protocol.intensify;

import iyunu.NewTLOL.enumeration.EIntensifyEvent;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.intensify.instance.Rabbet;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.instance.Stone;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 取下宝石
 * @author LSR
 * @date 2012-8-9
 */
public class StoneUnload extends TLOLMessageHandler {

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

		if (online.getBag().isFull()) {
			result = 1;
			reason = "背包已满！";
			return;
		}

		// 卸下已有的配件
		EEquip eEquip = EEquip.values()[part];
		Map<Integer, Rabbet> map = online.getBodyRabbet().get(eEquip);
		Rabbet rabbet = map.get(position);

		Stone stone = (Stone) ItemJson.instance().getItem(rabbet.getStoneId());
		if (stone == null) {
			result = 1;
			reason = "配件不存在";
			return;
		}

		stone.setIsDeal(rabbet.getIsBing());
		BagServer.add(online, stone, 1, cellsMap, EItemGet.stoneUnload);
		rabbet.setStoneId(0);

		RoleServer.countStone(online); // 计算角色属性
		BagMessage.sendBag(online, cellsMap); // 刷新背包
		SendMessage.sendBodyRabbet(online);

		LogManager.intensify(online, EIntensifyEvent.宝石取下, part, position, 0);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_stoneUnload");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
		if (result == 0) {
			SendMessage.sendSttribute(online);
		}
	}
}
