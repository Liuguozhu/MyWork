package iyunu.NewTLOL.net.protocol.shenbing;

import iyunu.NewTLOL.enumeration.common.ECostType;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.MallJson;
import iyunu.NewTLOL.json.ShenbingJson;
import iyunu.NewTLOL.manager.ActivityPayManager;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.AddProperty;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.shenbing.res.ShenbingUpRes;
import iyunu.NewTLOL.model.shenbing.res.ShenbingUpStarRes;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 神兵升级
 * @author LuoSR
 * @date 2014年7月14日
 */
public class ShenbingUp extends TLOLMessageHandler {

	private int result = 0;
	private String reason;
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "升级成功！";
		cellsMap.clear();

		int type = msg.readInt("type");
		type = 0;

		Map<EEquip, Equip> equipments = online.getEquipments();
		Equip equip = equipments.get(EEquip.shenbing);
		if (equip == null) {
			result = 1;
			reason = "升级失败，您没有装备神兵！";
			return;
		}

		ShenbingUpRes shenbingUpStartRes = ShenbingJson.instance().getShenbingUpById(equip.getId());
		if (shenbingUpStartRes.getUpId() == 0) {
			result = 1;
			reason = "已是满级神兵";
			return;
		}

		if (equip.getShowLevel() >= online.getLevel()) {
			result = 1;
			reason = "神兵等级不能超过角色等级！";
			return;
		}

		if (type == 0) {

			if (shenbingUpStartRes.getCostType().equals(ECostType.money)) {

				// 元宝
				if (online.getMoney() < shenbingUpStartRes.getCostValue()) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 3);
					return;
				}

			} else if (shenbingUpStartRes.getCostType().equals(ECostType.coin)) {

				// 银两
				if (online.getCoin() < shenbingUpStartRes.getCostValue()) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 2);
					return;
				}

			} else {

				// 绑银
				if (online.getGold() < shenbingUpStartRes.getCostValue()) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 1);
					return;
				}

			}

			// 基础石
			int jichushiId = shenbingUpStartRes.getStoneId();
			int jichushiNum = shenbingUpStartRes.getStoneNum();
			// 背包中基础石索引
			Map<Integer, Integer> jichushiIndexMap = online.getBag().isInBagById(jichushiId, 1, jichushiNum);

			if (jichushiIndexMap.isEmpty()) {
				result = 1;
				reason = "需要消耗" + jichushiNum + "个神兵基础石，您的基础石不足！";
				// 指向单独购买
				if (MallJson.instance().getLessItem().containsKey(jichushiId)) {
					MallJson.instance().countLessItem(online, jichushiId, jichushiNum, 1);
				}
				return;
			}

			// 灵石
			int lingshiId = shenbingUpStartRes.getLingshiId();
			int lingshiNum = shenbingUpStartRes.getLingshiNumber();
			// 背包中灵石索引
			Map<Integer, Integer> lingshiIndexMap = online.getBag().isInBagById(lingshiId, 1, lingshiNum);

			if (lingshiIndexMap.isEmpty()) {
				result = 1;
				reason = "需要消耗" + lingshiNum + "个灵石，您的灵石不足！";
				// 指向单独购买
				if (MallJson.instance().getLessItem().containsKey(lingshiId)) {
					MallJson.instance().countLessItem(online, lingshiId, lingshiNum, 1);
				}
				return;
			}

			// 水晶石
			int shuijingshiId = shenbingUpStartRes.getCstoneId();
			int shuijingshiNum = shenbingUpStartRes.getCstoneNum();
			// 背包中水晶石索引
			Map<Integer, Integer> shuijingshiIndexMap = online.getBag().isInBagById(shuijingshiId, 1, shuijingshiNum);

			if (shuijingshiIndexMap.isEmpty()) {
				result = 1;
				reason = "需要消耗" + shuijingshiNum + "个水晶石，您的水晶石不足！";
				// 指向单独购买
				if (MallJson.instance().getLessItem().containsKey(shuijingshiId)) {
					MallJson.instance().countLessItem(online, shuijingshiId, shuijingshiNum, 1);
				}
				return;
			}

			if (shenbingUpStartRes.getCostType().equals(ECostType.money)) {

				// 消耗元宝
				if (!RoleServer.costMoney(online, shenbingUpStartRes.getCostValue(), EMoney.upShenBing)) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 3);
					return;
				}

			} else if (shenbingUpStartRes.getCostType().equals(ECostType.coin)) {

				// 消耗银两
				if (!RoleServer.costCoin(online, shenbingUpStartRes.getCostValue(), EGold.upShenBing)) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 2);
					return;
				}

			} else {

				// 消耗绑银
				if (!RoleServer.costGoldOnly(online, shenbingUpStartRes.getCostValue(), EGold.upShenBing, false)) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 1);
					return;
				}

			}

			// 扣基础石
			Iterator<Entry<Integer, Integer>> jichushiIt = jichushiIndexMap.entrySet().iterator();
			while (jichushiIt.hasNext()) {
				Entry<Integer, Integer> entry = jichushiIt.next();
				online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.shenbingUp);
			}

			// 扣灵石
			Iterator<Entry<Integer, Integer>> lingshiIt = lingshiIndexMap.entrySet().iterator();
			while (lingshiIt.hasNext()) {
				Entry<Integer, Integer> entry = lingshiIt.next();
				online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.shenbingUp);
			}

			// 扣水晶石
			Iterator<Entry<Integer, Integer>> shuijingshiIt = shuijingshiIndexMap.entrySet().iterator();
			while (shuijingshiIt.hasNext()) {
				Entry<Integer, Integer> entry = shuijingshiIt.next();
				online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.shenbingUp);
			}

			Item newItem = ItemJson.instance().getItem(shenbingUpStartRes.getUpId(), equip.getUid());

			Equip newEquip = (Equip) newItem;
			for (AddProperty addProperty : equip.getAddPropertyList()) {
				newEquip.getAddPropertyList().add(addProperty);
			}

			newEquip.setStar(equip.getStar());
			newEquip.bind();

			// 神兵星变化,重新赋值
			ShenbingUpStarRes newShenbingUpStarRes = ShenbingJson.instance().getShenbingUpStarResMap().get(newEquip.getStar());
			newEquip.setLuckLimit(newShenbingUpStarRes.getLuckLimit());
			newEquip.setPropertyPercent(newShenbingUpStarRes.getPropertyPercent());

			// ======穿上并重新计算属性======
			RoleServer.wield(online, newEquip);
			// System.out.println(online.getHpMax() + "~~~~~~~");
			BagMessage.sendEquip(online);
			BagMessage.sendBag(online, cellsMap, false);

			if (newEquip.getSteps() > equip.getSteps()) {
				MapManager.instance().addShenbingQueue(online);
			}
			LogManager.shenbing(1, newEquip.getUid(), newEquip.getSteps(), online, newEquip.getShowLevel()); // 重置神兵日志

			if (newEquip.getShowLevel() == 20 || newEquip.getShowLevel() == 40 || newEquip.getShowLevel() == 60 || newEquip.getShowLevel() == 80) { // 发公告
				String content = StringControl.grn(online.getNick()) + "在不懈的努力下，终于将神兵升至" + StringControl.grn(newEquip.getShowLevel()) + "级，大家快来围观啊~！";
				BulletinManager.instance().addBulletinRock(content, 2);
			}
			SendMessage.sendSttribute(online);
		} else {

			if (!RoleServer.costMoney(online, shenbingUpStartRes.getMoney(), EMoney.shenbingUp)) {
				result = 1;
				reason = "元宝不足！";
				return;
			}

			Item newItem = ItemJson.instance().getItem(shenbingUpStartRes.getUpId(), equip.getUid());

			Equip newEquip = (Equip) newItem;
			newEquip.getAddPropertyList().clear();
			for (AddProperty addProperty : equip.getAddPropertyList()) {
				newEquip.getAddPropertyList().add(addProperty);
			}

			newEquip.setStar(equip.getStar());

			// 神兵星变化,重新赋值
			ShenbingUpStarRes newShenbingUpStarRes = ShenbingJson.instance().getShenbingUpStarResMap().get(newEquip.getStar());
			newEquip.setLuckLimit(newShenbingUpStarRes.getLuckLimit());
			newEquip.setPropertyPercent(newShenbingUpStarRes.getPropertyPercent());

			// ======穿上并重新计算属性======
			RoleServer.wield(online, newEquip);
			BagMessage.sendEquip(online);
			BagMessage.sendBag(online, cellsMap, false);

			if (newEquip.getSteps() > equip.getSteps()) {
				MapManager.instance().addShenbingQueue(online);
			}
			LogManager.shenbing(1, newEquip.getUid(), newEquip.getSteps(), online, newEquip.getShowLevel()); // 重置神兵日志
			SendMessage.sendSttribute(online);
		}

		// 开服活动
		ActivityPayManager.saveRoleIdByShenbing(online, equip);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_shenbingUp");
			message.write("result", result);
			message.write("reason", reason);
			// System.out.println("神兵升级 result=" + result + "     reason=" +
			// reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}