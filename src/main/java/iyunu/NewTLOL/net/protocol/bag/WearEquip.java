package iyunu.NewTLOL.net.protocol.bag;

import iyunu.NewTLOL.enumeration.EEquipEvent;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.message.TeamMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 穿装备
 * @author LuoSR
 * @date 2013-11-28
 */
public class WearEquip extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		cellsMap.clear();
		result = 0;
		reason = "";

		int index = msg.readInt("index");
		Cell cell = online.getBag().getCells()[index];

		Item item = cell.getItem();
		if (item == null) {
			result = 1;
			reason = "物品不存在";
			return;
		}

		if (!item.getType().equals(EItem.equip)) {
			result = 1;
			reason = "请选择装备！";
			return;
		}
		if (item.getLevel() > online.getLevel()) {
			result = 1;
			reason = "只能穿戴不高于人物等级的装备";
			return;
		}
		Equip equip = (Equip) cell.getItem();
		if (!equip.getFigure().check(online.getFigure())) {
			result = 1;
			reason = "这件装备不适合您使用";
			return;
		}

		Map<EEquip, Equip> equipments = online.getEquipments();
		// ======获取身上装备======
		Equip equipment = equipments.get(equip.getPart());

		// ======穿上并重新计算属性======
		RoleServer.wield(online, equip);
		online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.wearEquip); // 删除物品

		// ======保存卸下装备======
		if (equipment != null) {
			// online.getBag().add(equipment, 1, cellsMap, EItemGet.wearEquip);
			BagServer.add(online, equipment, 1, cellsMap, EItemGet.wearEquip);
		}

		LogManager.equip(online, equip.getId(), EEquipEvent.穿装备);

		// 神兵和时装特殊处理——通知周围玩家
		if (equip.getPart().equals(EEquip.shenbing)) {
			MapManager.instance().addShenbingQueue(online);
		} else if (equip.getPart().equals(EEquip.shizhuang)) {
			MapManager.instance().addShizhuangQueue(online);
			if (online.getTeam() != null) {
				TeamMessage.sendTeamAllMsg(online.getTeam());
			}
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_wearEquip");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
		if (result == 0) {
			BagMessage.sendBag(online, cellsMap, false);
			BagMessage.sendEquip(online);
			SendMessage.sendSttribute(online);
		}
	}
}