package iyunu.NewTLOL.net.protocol.shenbing;

import iyunu.NewTLOL.json.MallJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.server.shenbing.ShenbingServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 神兵重置
 * @author LuoSR
 * @date 2014年7月14日
 */
public class ShenbingReset extends TLOLMessageHandler {

	private int result = 0;
	private String reason;
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "成功";
		cellsMap.clear();

		int isBind = msg.readInt("isBind");

		Map<EEquip, Equip> equipments = online.getEquipments();
		Equip equip = equipments.get(EEquip.shenbing);
		if (equip == null) {
			result = 1;
			reason = "升级失败，您没有装备神兵！";
			return;
		}
		// 背包中符石索引
		Map<Integer, Integer> fushiIndexMap = online.getBag().isInBagById(21010, 1, equip.getSteps());

		if (fushiIndexMap.isEmpty()) {
			result = 1;
			reason = "符石不足，无法重置！";
			// 指向单独购买
			if (MallJson.instance().getLessItem().containsKey(21010)) {
				MallJson.instance().countLessItem(online, 21010, equip.getSteps(), 1);
			}
			return;
		}
		// 扣除符石
		Iterator<Entry<Integer, Integer>> fushi = fushiIndexMap.entrySet().iterator();
		while (fushi.hasNext()) {
			Entry<Integer, Integer> entry = fushi.next();
			online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.shenbingReset);
		}

		equip.getAddPropertyList().clear();
		ShenbingServer.reset(equip);

		if (isBind == 1) {
			equip.bind();
		}

		RoleServer.wield(online, equip);
		BagMessage.sendEquip(online);
		BagMessage.sendBag(online, cellsMap, false);
		SendMessage.sendSttribute(online);

		LogManager.shenbing(2, equip.getUid(), equip.getSteps(), online, equip.getShowLevel()); // 重置神兵日志
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_shenbingReset");
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