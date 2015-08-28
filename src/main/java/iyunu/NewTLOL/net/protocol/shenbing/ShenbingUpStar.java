package iyunu.NewTLOL.net.protocol.shenbing;

import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.json.ShenbingJson;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.shenbing.res.ShenbingUpStarRes;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 神兵升星
 * @author LuoSR
 * @date 2014年8月6日
 */
public class ShenbingUpStar extends TLOLMessageHandler {

	private int result = 0;
	private String reason;
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "成功";
		cellsMap.clear();

		int isBind = msg.readInt("isBind");
		int type = msg.readInt("type");

		Map<EEquip, Equip> equipments = online.getEquipments();
		Equip equip = equipments.get(EEquip.shenbing);
		if (equip == null) {
			result = 1;
			reason = "升星失败，您没有装备神兵！";
			return;
		}

		// 是否满星
		if (equip.getStar() == 25) {
			result = 1;
			reason = "神兵已满星！";
			return;
		}

		// HelperServer.helper(online, EHelper.shenbingStar); // 小助手记录

		ShenbingUpStarRes shenbingUpStarRes = ShenbingJson.instance().getShenbingUpStarResMap().get(equip.getStar());

		if (shenbingUpStarRes.getLevelLimit() > equip.getShowLevel()) {
			result = 1;
			reason = "继续升星需要神兵达到" + StringControl.red(shenbingUpStarRes.getLevelLimit()) + "级";
			return;
		}

		if (type == 0) {

			// 判断材料
			List<Integer> cellIndex = new ArrayList<>();
			for (EItem eItem : EItem.getJade()) {
				int index = online.getBag().isInBagByType(eItem, isBind);
				if (index == -1) {
					result = 1;
					reason = "材料不足";
					return;
				}
				cellIndex.add(index);
			}

			for (Integer index : cellIndex) {
				online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.shenbingUpStar); // 删除物品
			}

			if (equip.getLuck() >= equip.getLuckLimit()) {
				// 星加一
				equip.setStar(equip.getStar() + 1);
				equip.setLuck(0);
			} else {
				if (Util.probable(shenbingUpStarRes.getRate())) {
					// 星加一
					equip.setStar(equip.getStar() + 1);
					equip.setLuck(0);
				} else {
					result = 2;
					reason = "升星失败";

					if (equip.getStar() > 20) {
						equip.setStar(21);
					} else if (equip.getStar() > 15) {
						equip.setStar(16);
					} else if (equip.getStar() > 10) {
						equip.setStar(11);
					} else if (equip.getStar() > 5) {
						equip.setStar(6);
					} else {
						equip.setStar(1);
					}

					equip.setLuck(equip.getLuck() + 1);
				}
			}

			if (isBind == 1) {
				equip.setIsDeal(isBind);
			}

		} else {
			if (!RoleServer.costMoney(online, shenbingUpStarRes.getMoney(), EMoney.shenbingUpStar)) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 3);
				return;
			}

			if (equip.getLuck() >= equip.getLuckLimit()) {
				// 星加一
				equip.setStar(equip.getStar() + 1);
				equip.setLuck(0);
			} else {
				if (Util.probable(shenbingUpStarRes.getRate())) {
					// 星加一
					equip.setStar(equip.getStar() + 1);
					equip.setLuck(0);
				} else {

					result = 2;
					reason = "升星失败";
					equip.setLuck(equip.getLuck() + 10);
				}
			}
		}

		// 神兵星变化,从新赋值
		ShenbingUpStarRes newShenbingUpStarRes = ShenbingJson.instance().getShenbingUpStarResMap().get(equip.getStar());
		equip.setLuckLimit(newShenbingUpStarRes.getLuckLimit());
		equip.setPropertyPercent(newShenbingUpStarRes.getPropertyPercent());

		// 计算属性
		RoleServer.countEquipProperty(online);

		// 刷新装备和背包
		BagMessage.sendEquip(online);
		BagMessage.sendBag(online, cellsMap, false);
		SendMessage.sendSttribute(online);

		if (equip.getStar() == 25) { // 升至满星发公告
			String content = "神兵在手天下我有~！！" + StringControl.grn(online.getNick()) + "成功的将神兵升到5阶5星~！";
			BulletinManager.instance().addBulletinRock(content, 2);
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_shenbingUpStar");
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