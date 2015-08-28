package iyunu.NewTLOL.net.protocol.intensify;

import iyunu.NewTLOL.enumeration.EIntensifyEvent;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.json.IntensifyJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.intensify.instance.StoneRecipe;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Stone;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 宝石制作
 * @author LSR
 * @date 2012-8-9
 */
public class StoneMake extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "制作成功";
		cellsMap.clear();

		int makeNum = 1;
		int bindType = 0; // 绑定状态（0为不绑定，1为绑定）
		int isBind = msg.readInt("isBind");
		int makeAll = msg.readInt("makeAll");

		if (online.getGuide() == 10037) {
			Item item = ItemJson.instance().getItem(30005);
			BagServer.add(online, item, 1, cellsMap, EItemGet.stoneMake);
			// ======检查是否有任务（合成宝石任务）======
			// TaskServer.finishTaskById(online,
			// TaskManager.instance().getStoneMakeTask());

			BagMessage.sendBag(online, cellsMap, false); // 刷新背包
			return;
		}

		// 通过索引查询宝石制作信息
		StoneRecipe stoneRecipe = IntensifyJson.instance().getEquipStoneByColor(1);

		// 通过物品编号查询物品在背包的索引列表
		List<Integer> firstList = online.getBag().getIndexListById(stoneRecipe.getMaterial1());
		List<Integer> secondList = online.getBag().getIndexListById(stoneRecipe.getMaterial2());
		List<Integer> thirdList = online.getBag().getIndexListById(stoneRecipe.getMaterial3());
		int firstBindNum = 0;
		int secondBindNum = 0;
		int thirdBindNum = 0;
		int firstNum = 0;
		int secondNum = 0;
		int thirdNum = 0;

		if (firstList == null) {
			result = 1;
			reason = "材料不存在！";
			return;
		}
		if (secondList == null) {
			result = 1;
			reason = "材料不存在！";
			return;
		}

		if (thirdList == null) {
			result = 1;
			reason = "材料不存在！";
			return;
		}

		Cell[] cells = online.getBag().getCells();
		for (Integer integer : firstList) {
			Cell firstIndexCell = cells[integer];
			firstBindNum += firstIndexCell.getNum();
			if (firstIndexCell.getItem().getIsDeal() == 0) {
				firstNum += firstIndexCell.getNum();
			}
		}

		for (Integer integer : secondList) {
			Cell secondIndexCell = cells[integer];
			secondBindNum += secondIndexCell.getNum();
			if (secondIndexCell.getItem().getIsDeal() == 0) {
				secondNum += secondIndexCell.getNum();
			}
		}

		for (Integer integer : thirdList) {
			Cell thirdIndexCell = cells[integer];
			thirdBindNum += thirdIndexCell.getNum();
			if (thirdIndexCell.getItem().getIsDeal() == 0) {
				thirdNum += thirdIndexCell.getNum();
			}
		}

		if (isBind == 1) {
			if (makeAll == 0) {
				if (stoneRecipe.getNumber1() > firstBindNum) {
					result = 1;
					reason = "宝石材料的数量不足！";
					return;
				}

				if (stoneRecipe.getNumber2() > secondBindNum) {
					result = 1;
					reason = "精油材料的数量不足！";
					return;
				}

				if (stoneRecipe.getNumber3() > thirdBindNum) {
					result = 1;
					reason = "净化水材料的数量不足！";
					return;
				}

				if (!RoleServer.isCanCostGold(online, stoneRecipe.getGold())) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 2);
					return;
				}
			} else {
				int firstMakeNum = firstBindNum / stoneRecipe.getNumber1();
				int secondMakeNum = secondBindNum / stoneRecipe.getNumber2();
				int thirdMakeNum = thirdBindNum / stoneRecipe.getNumber3();
				makeNum = Util.matchMin(firstMakeNum, secondMakeNum, thirdMakeNum, online.getBag().freeCell());

				if (!RoleServer.isCanCostGold(online, stoneRecipe.getGold() * makeNum)) {
					result = 1;
					reason = "已超出当天的绑银消耗";
					return;
				}
			}

			Map<Integer, Integer> firstMap = online.getBag().isInBagById(stoneRecipe.getMaterial1(), 1, stoneRecipe.getNumber1() * makeNum);
			if (firstMap.isEmpty()) {
				result = 1;
				reason = "宝石材料不足！";
				return;
			}
			Map<Integer, Integer> secondMap = online.getBag().isInBagById(stoneRecipe.getMaterial2(), 1, stoneRecipe.getNumber2() * makeNum);
			if (secondMap.isEmpty()) {
				result = 1;
				reason = "精油不足！";
				return;
			}
			Map<Integer, Integer> thirdMap = online.getBag().isInBagById(stoneRecipe.getMaterial3(), 1, stoneRecipe.getNumber3() * makeNum);
			if (thirdMap.isEmpty()) {
				result = 1;
				reason = "净化水不足！";
				return;
			}

			// 扣除绑银
			if (!RoleServer.costGold(online, stoneRecipe.getGold() * makeNum, EGold.stoneMake)) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 1);
				return;
			}
			
			// 扣材料石头
			Iterator<Entry<Integer, Integer>> firstIt = firstMap.entrySet().iterator();
			while (firstIt.hasNext()) {
				Entry<Integer, Integer> entry = firstIt.next();
				online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.stoneMake);
			}

			// 扣精油
			Iterator<Entry<Integer, Integer>> secondIt = secondMap.entrySet().iterator();
			while (secondIt.hasNext()) {
				Entry<Integer, Integer> entry = secondIt.next();
				online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.stoneMake);
			}

			// 扣净化水
			Iterator<Entry<Integer, Integer>> thirdIt = thirdMap.entrySet().iterator();
			while (thirdIt.hasNext()) {
				Entry<Integer, Integer> entry = thirdIt.next();
				online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.stoneMake);
			}

			bindType = 1;
		} else {
			if (makeAll == 0) {
				if (stoneRecipe.getNumber1() > firstNum) {
					result = 1;
					reason = "宝石材料的数量不足！";
					return;
				}

				if (stoneRecipe.getNumber2() > secondNum) {
					result = 1;
					reason = "精油材料的数量不足！";
					return;
				}

				if (stoneRecipe.getNumber3() > thirdNum) {
					result = 1;
					reason = "净化水材料的数量不足！";
					return;
				}

			} else {
				int firstMakeNum = firstNum / stoneRecipe.getNumber1();
				int secondMakeNum = secondNum / stoneRecipe.getNumber2();
				int thiredMakeNum = thirdNum / stoneRecipe.getNumber3();
				makeNum = Util.matchMin(firstMakeNum, secondMakeNum, thiredMakeNum, online.getBag().freeCell());
			}

			Map<Integer, Integer> firstMap = online.getBag().isInBagById(stoneRecipe.getMaterial1(), 0, stoneRecipe.getNumber1() * makeNum);
			if (firstMap.isEmpty()) {
				result = 1;
				reason = "宝石材料不足！";
				return;
			}
			Map<Integer, Integer> secondMap = online.getBag().isInBagById(stoneRecipe.getMaterial2(), 0, stoneRecipe.getNumber2() * makeNum);
			if (secondMap.isEmpty()) {
				result = 1;
				reason = "精油不足！";
				return;
			}
			Map<Integer, Integer> thirdMap = online.getBag().isInBagById(stoneRecipe.getMaterial3(), 0, stoneRecipe.getNumber3() * makeNum);
			if (thirdMap.isEmpty()) {
				result = 1;
				reason = "净化水不足！";
				return;
			}

			if (online.getBag().isFull(makeNum)) {
				result = 1;
				reason = "背包空间不足！";
				return;
			}

			// 扣除银两
			if (!RoleServer.costCoin(online, stoneRecipe.getGold() * makeNum, EGold.stoneMake)) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 2);
				return;
			}

			// 扣材料石头
			Iterator<Entry<Integer, Integer>> firstIt = firstMap.entrySet().iterator();
			while (firstIt.hasNext()) {
				Entry<Integer, Integer> entry = firstIt.next();
				online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.stoneMake);
			}

			// 扣精油
			Iterator<Entry<Integer, Integer>> secondIt = secondMap.entrySet().iterator();
			while (secondIt.hasNext()) {
				Entry<Integer, Integer> entry = secondIt.next();
				online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.stoneMake);
			}

			// 扣净化水
			Iterator<Entry<Integer, Integer>> thirdIt = thirdMap.entrySet().iterator();
			while (thirdIt.hasNext()) {
				Entry<Integer, Integer> entry = thirdIt.next();
				online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.stoneMake);
			}

		}

		// 生成宝石
		for (int i = 0; i < makeNum; i++) {
			MonsterDropItem monsterDropItem = stoneRecipe.drop();
			if (monsterDropItem != null) {
				Stone store = (Stone) ItemJson.instance().getItem(monsterDropItem.getItemId());
				store.setIsDeal(bindType);
				BagServer.add(online, store, monsterDropItem.getNum(), cellsMap, EItemGet.stoneMake);
			}
		}

		// ======检查是否有任务（合成宝石任务）======
		// TaskServer.finishTaskById(online,
		// TaskManager.instance().getStoneMakeTask());

		BagMessage.sendBag(online, cellsMap); // 刷新背包

		LogManager.intensify(online, EIntensifyEvent.宝石制作, 0, 0, makeNum);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_stoneMake");
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
