package iyunu.NewTLOL.net.protocol.base;

import iyunu.NewTLOL.enumeration.common.EExp;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.MonsterJson;
import iyunu.NewTLOL.manager.RaidsManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.ItemOnMap;
import iyunu.NewTLOL.model.map.instance.MapRaidsInfo;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.monster.instance.MonsterBox;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.role.RoleServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 拾取，捡箱子
 * 
 * @author SunHonglei
 * 
 */
public class Pickup extends TLOLMessageHandler {

	private int result;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "拾取成功";

		long uid = msg.readLong("uid");

		BaseMap baseMap = online.getMapInfo().getBaseMap();
		if (baseMap == null) {
			result = 1;
			reason = "拾取失败，地图错误";
			return;
		}
		if (!baseMap.getBoxs().containsKey(uid)) {
			result = 1;
			reason = "拾取失败，物品不存在";
			return;
		}

		ItemOnMap itemOnMap = baseMap.getBoxs().get(uid);
		if (itemOnMap == null) {
			result = 1;
			reason = "拾取失败，物品不存在";
			baseMap.removeItem(uid);
			return;
		}
		if (!itemOnMap.check(online.getId())) {
			result = 1;
			reason = "拾取失败，此宝箱不属于您";
			return;
		}
		MonsterBox monsterBox = MonsterJson.instance().getMonsterBox(itemOnMap.getMonsterBoxId());
		if (monsterBox == null) {
			result = 1;
			reason = "拾取失败，物品不存在";
			baseMap.removeItem(uid);
			return;
		}

		EItemGet eItemGet = EItemGet.box;
		int multiple = 1;
		if (online.getMapInfo().getBaseMap() instanceof MapRaidsInfo) { // 如果是在副本地图中
			if (RaidsManager.isDouble()) {
				multiple = 2;
			}

			eItemGet = EItemGet.raidsBox;
		}

		if (online.getTeam() != null) {
			boolean flag = false;
			for (Role role : online.getTeam().getMember()) {
				// ======掉落绑银======
				RoleServer.addGold(role, monsterBox.getGold() * multiple, EGold.box);
				// ======掉落经验======
				RoleServer.addExp(role, monsterBox.getRoleExp() * multiple, EExp.box);
				// ======掉落物品======
				Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
				for (int i = 0; i < multiple; i++) {
					Set<MonsterDropItem> monsterItems = monsterBox.drop();
					for (MonsterDropItem monsterItem : monsterItems) {
						Item item = ItemJson.instance().getItem(monsterItem.getItemId());
						item.setIsDeal(monsterItem.getIsBind());
						flag = true;
						// role.getBag().add(item, monsterItem.getNum(),
						// cellsMap, EItemGet.box);
						BagServer.add(role, item, monsterItem.getNum(), cellsMap, eItemGet);
					}
				}

				if (!cellsMap.isEmpty()) {
					BagMessage.sendBag(role, cellsMap);
				}
			}
			if (!flag) {
				result = 1;
				reason = "宝箱空空如也";
				baseMap.removeItem(uid); // 删除此物品
				return;
			}
		} else {
			boolean flag = false;
			// ======掉落绑银======
			RoleServer.addGold(online, monsterBox.getGold() * multiple, EGold.box);
			// ======掉落经验======
			RoleServer.addExp(online, monsterBox.getRoleExp() * multiple, EExp.box);
			// ======掉落物品======
			Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
			for (int i = 0; i < multiple; i++) {
				Set<MonsterDropItem> monsterItems = monsterBox.drop();
				for (MonsterDropItem monsterItem : monsterItems) {
					Item item = ItemJson.instance().getItem(monsterItem.getItemId());
					item.setIsDeal(monsterItem.getIsBind());
					flag = true;
					BagServer.add(online, item, monsterItem.getNum(), cellsMap, eItemGet);
				}
			}

			if (!flag) {
				result = 1;
				reason = "宝箱空空如也";
				baseMap.removeItem(uid); // 删除此物品
				return;
			} else {
				BagMessage.sendBag(online, cellsMap);
			}
		}

		baseMap.removeItem(uid); // 删除此物品
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_pickup");
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