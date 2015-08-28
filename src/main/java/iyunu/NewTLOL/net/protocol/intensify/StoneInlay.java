package iyunu.NewTLOL.net.protocol.intensify;

import iyunu.NewTLOL.enumeration.EIntensifyEvent;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.intensify.instance.Rabbet;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.instance.Stone;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 宝石镶嵌
 * @author LSR
 * @date 2012-8-9
 */
public class StoneInlay extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "";

		int part = msg.readInt("part"); // 部位
		int position = msg.readInt("position"); // 配件位置
		int stoneIndex = msg.readInt("stoneIndex"); // 配件的背包索引

		EEquip eEquip = EEquip.values()[part];
		Map<Integer, Rabbet> map = online.getBodyRabbet().get(eEquip); // 部位镶嵌集合

		Rabbet rabbet = map.get(position);
		if (rabbet.getOpen() == 0) {
			result = 1;
			reason = "需要打孔";
			return;
		}

		if (rabbet.getStoneId() != 0) {
			result = 1;
			reason = "已镶嵌宝石，请升级宝石";
			return;
		}

		Cell stoneCell = online.getBagStone().getCells()[stoneIndex];

		if (stoneCell.getItem() == null) {
			result = 1;
			reason = "配件不存在！";
			return;
		}

		if (!stoneCell.getItem().getType().equals(EItem.stone)) {
			result = 1;
			reason = "不是宝石";
			return;
		}

		Stone newStone = (Stone) stoneCell.getItem();
		for (Rabbet rabbetInfo : map.values()) {
			if (rabbetInfo.getOpen() == 1 && rabbetInfo.getStoneId() != 0) {
				Stone stone = (Stone) ItemJson.instance().getItem(rabbetInfo.getStoneId());
				if (stone != null && newStone.getSpecies() == stone.getSpecies()) {
					result = 1;
					reason = "同一部位不能镶嵌相同类型的宝石";
					return;
				}
			}
		}

		// 装备新配件
		rabbet.setStoneId(stoneCell.getItem().getId());
		rabbet.setIsBing(1);

		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		// 删除背包中的新配件
		online.getBagStone().removeByIndex(stoneIndex, 1, cellsMap, EItemCost.slot);

		RoleServer.countStone(online); // 计算角色属性
		BagMessage.sendBagStone(online, cellsMap); // 刷新背包
		SendMessage.sendBodyRabbet(online);

		LogManager.intensify(online, EIntensifyEvent.宝石镶嵌, part, position, 0);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_stoneInlay");
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
