package iyunu.NewTLOL.net.protocol.intensify;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.json.IntensifyJson;
import iyunu.NewTLOL.json.MallJson;
import iyunu.NewTLOL.manager.ActivityPayManager;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.intensify.res.EquipStarRes;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class EquipStar extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "升星成功";

		int part = msg.readInt("part");
		int isBind = msg.readInt("isBind");

		if (part < 0 || part > 5) {
			result = 1;
			reason = "升星失败，请选择装备";
			return;
		}

		Equip equip = online.getEquipments().get(EEquip.values()[part]);
		int star = equip.getStar() + 1;
		if (star > 25) {
			result = 1;
			reason = "已满星！";
			return;
		}

		EquipStarRes equipStarRes = IntensifyJson.instance().getEquipStarRes(star);
		if (equipStarRes == null) {
			result = 1;
			reason = "装备升星错误！";
			return;
		}

		if (isBind == 0) { // 使用银两
			if (online.getCoin() < equipStarRes.getGold()) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 2);
				return;
			}
		} else { // 使用绑银
			if (!RoleServer.isCanCostGold(online, equipStarRes.getGold())) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 1);
				return;
			}
		}

		// Map<Integer, Integer> map =
		// online.getBag().isInBagByType(EItem.shengxingshi, isBind,
		// equipStarRes.getNum());
		Map<Integer, Integer> map = online.getBag().isInBagById(equipStarRes.getStone(), isBind, equipStarRes.getNum());

		if (map.isEmpty()) {
			result = 1;
			reason = "升星石不足！";
			// 指向单独购买
			if (MallJson.instance().getLessItem().containsKey(equipStarRes.getStone())) {
				if (isBind == 0) {
					MallJson.instance().countLessItem(online, equipStarRes.getStone(), equipStarRes.getNum(), 0);
				} else {
					MallJson.instance().countLessItem(online, equipStarRes.getStone(), equipStarRes.getNum(), 1);
				}
			}
			return;
		}

		// 扣钱
		if (isBind == 0) {
			RoleServer.costCoin(online, equipStarRes.getGold(), EGold.equipStar);
		} else {
			if (!RoleServer.costGold(online, equipStarRes.getGold(), EGold.equipStar)) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 2);
				return;
			}
		}

		// 扣物品
		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		Iterator<Entry<Integer, Integer>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Integer> entry = it.next();
			online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.equipStar);
		}

		if (isBind == 1 && equip.getIsDeal() == 0) { // 使用绑定材料或者绑定银两，物品绑定
			equip.bind();
		}

		// 升星
		if (Util.probable(equipStarRes.getRate())) {
			equip.setStar(star);
			equip.setPropertyPercent(equipStarRes.getPropertyPercent());

		} else { // 升星失败，将至本阶1星
			int newStar = 0;
			if (equip.getStar() > 20) {
				newStar = 21;
			} else if (equip.getStar() > 15) {
				newStar = 16;
			} else if (equip.getStar() > 10) {
				newStar = 11;
			} else if (equip.getStar() > 5) {
				newStar = 6;
			} else {
				newStar = 1;
			}

			equip.setStar(newStar);
			EquipStarRes newEquipStarRes = IntensifyJson.instance().getEquipStarRes(newStar);
			equip.setPropertyPercent(newEquipStarRes.getPropertyPercent());

			result = 2;
			reason = "升星失败";
			// return;
		}

		RoleServer.countEquipProperty(online);
		BagMessage.sendEquip(online);
		SendMessage.sendSttribute(online);

		// 刷背包
		BagMessage.sendBag(online, cellsMap);

		if (star == 25) { // 升至满星发公告
			String content = StringControl.grn(online.getNick()) + "鸿运当头，成功的将" + "装备升至5阶5星";
			BulletinManager.instance().addBulletinRock(content, 2);
		}

		// 新服活动
		ActivityPayManager.saveRoleIdByEquipStart(online, equip);
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_equipStar");
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