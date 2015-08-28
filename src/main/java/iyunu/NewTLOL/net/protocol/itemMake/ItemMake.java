package iyunu.NewTLOL.net.protocol.itemMake;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.json.IntensifyJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.intensify.res.ItemMakeRes;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.role.RoleServer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 物品合成
 * @author LuoSR
 * @date 2014年10月11日
 */
public class ItemMake extends TLOLMessageHandler {

	private int result = 0;
	private String reason;
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "合成成功！";
		cellsMap.clear();

		int type = msg.readInt("type");
		int index = msg.readInt("index");
		int isBind = msg.readInt("isBind"); // 0不绑定，1.绑定，2.混合
		int num = msg.readInt("num");

		// 绑定状态（0为不绑定，1为绑定）
		int bindType = 0;
		// 非绑定物品数量
		int notBindNum = 0;
		// 绑定物品数量
		int bindNum = 0;
		// 通过绑银真正可以制作物品数量
		int reallyMakeNumByGold = 0;
		// 通过银两真正可以制作物品数量
		int reallyMakeNumByCoin = 0;
		// 混合使用真正可以制作物品数量
		int reallyMakeNum = 0;
		// 使用绑定物品的数量
		int useBindNum = 0;
		// 使用非绑定物品的数量
		int useNotBindNum = 0;
		// 混合使用消耗物品数量
		int useNum = 0;

		if (type == 1) { // ========================================背包========================================

			if (online.getBag().isFull()) {
				result = 1;
				reason = "合成失败，背包空间不足";
				return;
			}

			Cell cell = online.getBag().getCells()[index];

			Item item = cell.getItem();
			if (item == null) {
				result = 1;
				reason = "物品不存在";
				return;
			}

			ItemMakeRes itemMakeRes = IntensifyJson.instance().getItemMakeRes(item.getId());

			if (itemMakeRes == null) {
				result = 1;
				reason = "物品不能合成";
				return;
			}

			// 绑银可制作的数量
			int makeNumByGold = (int) (online.getGold() / itemMakeRes.getMakeGold());
			// 银两可制作的数量
			int makeNumByCoin = (int) (online.getCoin() / itemMakeRes.getMakeGold());

			if (isBind == 0) {
				if (makeNumByCoin < 1) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 2);
					return;
				}

				// 根据物品类型获取物品所在背包索引及数量
				Map<Integer, Integer> notBindMap = online.getBag().isInBagMapById(item.getId(), 0);
				Iterator<Entry<Integer, Integer>> notBindMapIt = notBindMap.entrySet().iterator();
				while (notBindMapIt.hasNext()) {
					Entry<Integer, Integer> entry = notBindMapIt.next();
					notBindNum += entry.getValue();
				}

				if (notBindNum < itemMakeRes.getMakeNum()) {
					result = 1;
					reason = "物品不足，请重新选择合成方式";
					return;
				}

				int makeNumByItem = (int) (notBindNum / itemMakeRes.getMakeNum());

				if (makeNumByItem >= num) {
					makeNumByItem = num;
				}

				if (makeNumByCoin >= makeNumByItem) {
					reallyMakeNumByCoin = makeNumByItem;
					useNotBindNum = reallyMakeNumByCoin * itemMakeRes.getMakeNum();
				} else {
					reallyMakeNumByCoin = makeNumByCoin;
					useNotBindNum = reallyMakeNumByCoin * itemMakeRes.getMakeNum();
				}

				// 消耗物品
				Map<Integer, Integer> useNotMap = online.getBag().isInBagById(item.getId(), 0, useNotBindNum);
				if (useNotMap.isEmpty()) {
					result = 1;
					reason = "物品不足，请重新选择合成方式";
					return;
				}

				Iterator<Entry<Integer, Integer>> useNotIt = useNotMap.entrySet().iterator();
				while (useNotIt.hasNext()) {
					Entry<Integer, Integer> entry = useNotIt.next();
					online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.itemMake);
				}

				// 消耗银两
				RoleServer.costCoin(online, itemMakeRes.getMakeGold() * reallyMakeNumByCoin, EGold.itemMake);
			} else if (isBind == 1) {

				if (makeNumByGold < 1) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 1);
					return;
				}

				Map<Integer, Integer> bindMap = online.getBag().isInBagMapById(item.getId(), 1);
				Iterator<Entry<Integer, Integer>> bindIt = bindMap.entrySet().iterator();
				while (bindIt.hasNext()) {
					Entry<Integer, Integer> entry = bindIt.next();
					bindNum += entry.getValue();
				}

				if (bindNum < itemMakeRes.getMakeNum()) {
					result = 1;
					reason = "物品不足，请重新选择合成方式";
					return;
				}

				int makeNumByItem = (int) (bindNum / itemMakeRes.getMakeNum());

				if (makeNumByItem >= num) {
					makeNumByItem = num;
				}

				if (makeNumByGold >= makeNumByItem) {
					reallyMakeNumByGold = makeNumByItem;
					useBindNum = reallyMakeNumByGold * itemMakeRes.getMakeNum();
				} else {
					reallyMakeNumByGold = makeNumByCoin;
					useBindNum = reallyMakeNumByGold * itemMakeRes.getMakeNum();
				}

				// 消耗物品
				Map<Integer, Integer> useBindMap = online.getBag().isInBagById(item.getId(), 1, useBindNum);
				if (useBindMap.isEmpty()) {
					result = 1;
					reason = "物品不足，请重新选择合成方式";
					return;
				}

				Iterator<Entry<Integer, Integer>> useBindIt = useBindMap.entrySet().iterator();
				while (useBindIt.hasNext()) {
					Entry<Integer, Integer> entry = useBindIt.next();
					online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.itemMake);
				}

				// 消耗银两
				RoleServer.costGold(online, itemMakeRes.getMakeGold() * reallyMakeNumByGold, EGold.itemMake);
				bindType = 1;
			} else {
				if (makeNumByGold + makeNumByCoin < 1) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 2);
					return;
				}

				Map<Integer, Integer> notBindMap = online.getBag().isInBagMapById(item.getId(), 0);
				Iterator<Entry<Integer, Integer>> notBindIt = notBindMap.entrySet().iterator();
				while (notBindIt.hasNext()) {
					Entry<Integer, Integer> entry = notBindIt.next();
					notBindNum += entry.getValue();
				}

				Map<Integer, Integer> bindMap = online.getBag().isInBagMapById(item.getId(), 1);
				Iterator<Entry<Integer, Integer>> bindIt = bindMap.entrySet().iterator();
				while (bindIt.hasNext()) {
					Entry<Integer, Integer> entry = bindIt.next();
					bindNum += entry.getValue();
				}

				int makeNumByItem = (int) ((notBindNum + bindNum) / itemMakeRes.getMakeNum());

				if (makeNumByItem >= num) {
					makeNumByItem = num;
				}

				if (makeNumByCoin + makeNumByGold >= makeNumByItem) {
					reallyMakeNum = makeNumByItem;
					useNum = reallyMakeNum * itemMakeRes.getMakeNum();
				} else {
					reallyMakeNum = makeNumByCoin + makeNumByGold;
					useNum = reallyMakeNum * itemMakeRes.getMakeNum();
				}

				// 消耗物品
				Map<Integer, Integer> useMap = online.getBag().isInBagById(item.getId(), 1, useNum);
				if (useMap.isEmpty()) {
					result = 1;
					reason = "物品不足，请重新选择合成方式";
					return;
				}

				Iterator<Entry<Integer, Integer>> useIt = useMap.entrySet().iterator();
				while (useIt.hasNext()) {
					Entry<Integer, Integer> entry = useIt.next();
					online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.itemMake);
				}

				// 消耗银两
				RoleServer.costGold(online, itemMakeRes.getMakeGold() * reallyMakeNum, EGold.itemMake);
				bindType = 1;
			}

			// 获得一个新物品
			for (int i = 0; i < reallyMakeNumByGold; i++) {
				Item newItem = ItemJson.instance().getItem(itemMakeRes.getNewId());
				newItem.setIsDeal(bindType);
				BagServer.add(online, newItem, 1, cellsMap, EItemGet.itemMake);
			}

			for (int i = 0; i < reallyMakeNumByCoin; i++) {
				Item newItem = ItemJson.instance().getItem(itemMakeRes.getNewId());
				newItem.setIsDeal(bindType);
				BagServer.add(online, newItem, 1, cellsMap, EItemGet.itemMake);
			}

			for (int i = 0; i < reallyMakeNum; i++) {
				Item newItem = ItemJson.instance().getItem(itemMakeRes.getNewId());
				newItem.setIsDeal(bindType);
				BagServer.add(online, newItem, 1, cellsMap, EItemGet.itemMake);
			}

			BagMessage.sendBag(online, cellsMap, false);

		} else if (type == 2) { // ========================================宝石背包===============================================

			if (online.getBagStone().isFull()) {
				result = 1;
				reason = "合成失败，背包空间不足";
				return;
			}

			Cell cell = online.getBagStone().getCells()[index];

			Item item = cell.getItem();
			if (item == null) {
				result = 1;
				reason = "物品不存在";
				return;
			}

			ItemMakeRes itemMakeRes = IntensifyJson.instance().getItemMakeRes(item.getId());

			if (itemMakeRes == null) {
				result = 1;
				reason = "物品不能合成";
				return;
			}

			// 绑银可制作的数量
			int makeNumByGold = (int) (online.getGold() / itemMakeRes.getMakeGold());
			// 银两可制作的数量
			int makeNumByCoin = (int) (online.getCoin() / itemMakeRes.getMakeGold());

			if (isBind == 0) {
				if (makeNumByCoin < 1) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 2);
					return;
				}

				// 根据物品类型获取物品所在背包索引及数量
				Map<Integer, Integer> notBindMap = online.getBagStone().isInBagMapById(item.getId(), 0);
				Iterator<Entry<Integer, Integer>> notBindMapIt = notBindMap.entrySet().iterator();
				while (notBindMapIt.hasNext()) {
					Entry<Integer, Integer> entry = notBindMapIt.next();
					notBindNum += entry.getValue();
				}

				if (notBindNum < itemMakeRes.getMakeNum()) {
					result = 1;
					reason = "物品不足，请重新选择合成方式";
					return;
				}

				int makeNumByItem = (int) (notBindNum / itemMakeRes.getMakeNum());

				if (makeNumByItem >= num) {
					makeNumByItem = num;
				}

				if (makeNumByCoin >= makeNumByItem) {
					reallyMakeNumByCoin = makeNumByItem;
					useNotBindNum = reallyMakeNumByCoin * itemMakeRes.getMakeNum();
				} else {
					reallyMakeNumByCoin = makeNumByCoin;
					useNotBindNum = reallyMakeNumByCoin * itemMakeRes.getMakeNum();
				}

				// 消耗物品
				Map<Integer, Integer> useNotMap = online.getBagStone().isInBagById(item.getId(), 0, useNotBindNum);
				if (useNotMap.isEmpty()) {
					result = 1;
					reason = "物品不足，请重新选择合成方式";
					return;
				}

				Iterator<Entry<Integer, Integer>> useNotIt = useNotMap.entrySet().iterator();
				while (useNotIt.hasNext()) {
					Entry<Integer, Integer> entry = useNotIt.next();
					online.getBagStone().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.itemMake);
				}

				// 消耗银两
				RoleServer.costCoin(online, itemMakeRes.getMakeGold() * reallyMakeNumByCoin, EGold.itemMake);
			} else if (isBind == 1) {

				if (makeNumByGold < 1) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 1);
					return;
				}

				Map<Integer, Integer> bindMap = online.getBagStone().isInBagMapById(item.getId(), 1);
				Iterator<Entry<Integer, Integer>> bindIt = bindMap.entrySet().iterator();
				while (bindIt.hasNext()) {
					Entry<Integer, Integer> entry = bindIt.next();
					bindNum += entry.getValue();
				}

				if (bindNum < itemMakeRes.getMakeNum()) {
					result = 1;
					reason = "物品不足，请重新选择合成方式";
					return;
				}

				int makeNumByItem = (int) (bindNum / itemMakeRes.getMakeNum());

				if (makeNumByItem >= num) {
					makeNumByItem = num;
				}

				if (makeNumByGold >= makeNumByItem) {
					reallyMakeNumByGold = makeNumByItem;
					useBindNum = reallyMakeNumByGold * itemMakeRes.getMakeNum();
				} else {
					reallyMakeNumByGold = makeNumByCoin;
					useBindNum = reallyMakeNumByGold * itemMakeRes.getMakeNum();
				}

				// 消耗物品
				Map<Integer, Integer> useBindMap = online.getBagStone().isInBagById(item.getId(), 1, useBindNum);
				if (useBindMap.isEmpty()) {
					result = 1;
					reason = "物品不足，请重新选择合成方式";
					return;
				}

				Iterator<Entry<Integer, Integer>> useBindIt = useBindMap.entrySet().iterator();
				while (useBindIt.hasNext()) {
					Entry<Integer, Integer> entry = useBindIt.next();
					online.getBagStone().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.itemMake);
				}

				// 消耗银两
				RoleServer.costGold(online, itemMakeRes.getMakeGold() * reallyMakeNumByGold, EGold.itemMake);
				bindType = 1;
			} else {
				if (makeNumByGold + makeNumByCoin < 1) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 2);
					return;
				}

				Map<Integer, Integer> notBindMap = online.getBagStone().isInBagMapById(item.getId(), 0);
				Iterator<Entry<Integer, Integer>> notBindIt = notBindMap.entrySet().iterator();
				while (notBindIt.hasNext()) {
					Entry<Integer, Integer> entry = notBindIt.next();
					notBindNum += entry.getValue();
				}

				Map<Integer, Integer> bindMap = online.getBagStone().isInBagMapById(item.getId(), 1);
				Iterator<Entry<Integer, Integer>> bindIt = bindMap.entrySet().iterator();
				while (bindIt.hasNext()) {
					Entry<Integer, Integer> entry = bindIt.next();
					bindNum += entry.getValue();
				}

				int makeNumByItem = (int) ((notBindNum + bindNum) / itemMakeRes.getMakeNum());

				if (makeNumByItem >= num) {
					makeNumByItem = num;
				}

				if (makeNumByCoin + makeNumByGold >= makeNumByItem) {
					reallyMakeNum = makeNumByItem;
					useNum = reallyMakeNum * itemMakeRes.getMakeNum();
				} else {
					reallyMakeNum = makeNumByCoin + makeNumByGold;
					useNum = reallyMakeNum * itemMakeRes.getMakeNum();
				}

				// 消耗物品
				Map<Integer, Integer> useMap = online.getBagStone().isInBagById(item.getId(), 1, useNum);
				if (useMap.isEmpty()) {
					result = 1;
					reason = "物品不足，请重新选择合成方式";
					return;
				}

				Iterator<Entry<Integer, Integer>> useIt = useMap.entrySet().iterator();
				while (useIt.hasNext()) {
					Entry<Integer, Integer> entry = useIt.next();
					online.getBagStone().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.itemMake);
				}

				// 消耗银两
				RoleServer.costGold(online, itemMakeRes.getMakeGold() * reallyMakeNum, EGold.itemMake);
				bindType = 1;
			}

			// 获得一个新物品
			for (int i = 0; i < reallyMakeNumByGold; i++) {
				Item newItem = ItemJson.instance().getItem(itemMakeRes.getNewId());
				newItem.setIsDeal(bindType);
				BagServer.add(online, newItem, 1, cellsMap, EItemGet.itemMake);
			}

			for (int i = 0; i < reallyMakeNumByCoin; i++) {
				Item newItem = ItemJson.instance().getItem(itemMakeRes.getNewId());
				newItem.setIsDeal(bindType);
				BagServer.add(online, newItem, 1, cellsMap, EItemGet.itemMake);
			}

			for (int i = 0; i < reallyMakeNum; i++) {
				Item newItem = ItemJson.instance().getItem(itemMakeRes.getNewId());
				newItem.setIsDeal(bindType);
				BagServer.add(online, newItem, 1, cellsMap, EItemGet.itemMake);
			}

			BagMessage.sendBagStone(online, cellsMap);

		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_itemMake");
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