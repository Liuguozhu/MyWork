package iyunu.NewTLOL.net.protocol.bag;

import iyunu.NewTLOL.enumeration.EEquipEvent;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.message.TeamMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.EItemGet;
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
 * @function 卸装备
 * @author LuoSR
 * @date 2013-11-28
 */
public class UnloadEquip extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "";
		cellsMap.clear();

		int part = msg.readInt("part");

		// ======检查背包======
		if (online.getBag().isFull()) {
			result = 1;
			reason = "背包已满！";
			return;
		}

		// ======获取要卸下装备的对象======
		Map<EEquip, Equip> equipments = online.getEquipments();
		Equip equip = equipments.get(EEquip.values()[part]);
		if (equip == null) {
			result = 1;
			reason = "卸装备失败，装备不存在！";
			return;
		}

		// ======背包添加卸下的装备======
		// online.getBag().add(equip, 1, cellsMap, EItemGet.unloadEquip);
		BagServer.add(online, equip, 1, cellsMap, EItemGet.unloadEquip);

		// ======卸下并重新计算武将属性======
		RoleServer.unwield(online, equip.getPart());

		LogManager.equip(online, equip.getId(), EEquipEvent.卸装备);

		if (equip.getPart().equals(EEquip.shenbing)) {
			MapManager.instance().addShenbingQueue(online);
		} else if (equip.getPart().equals(EEquip.shizhuang)) {
			MapManager.instance().addShizhuangQueue(online);
			if (online.getTeam() != null) {
				TeamMessage.sendTeamAllMsg(online.getTeam());
			}
		}

		SendMessage.sendSttribute(online);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_unloadEquip");
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
		}
	}
}