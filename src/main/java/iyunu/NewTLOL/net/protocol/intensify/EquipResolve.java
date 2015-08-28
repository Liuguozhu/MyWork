package iyunu.NewTLOL.net.protocol.intensify;

import iyunu.NewTLOL.json.IntensifyJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.bag.Bag;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.intensify.instance.IntensifyResolve;
import iyunu.NewTLOL.model.intensify.res.EquipStarRes;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.util.Translate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class EquipResolve extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "分解成功";

		String str = msg.readString("indexStr");
		String[] strings = str.split("=");

		int total = 0;
		int totalBind = 0;
		List<Integer> list = new ArrayList<Integer>();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Map<Integer, Integer> mapBind = new HashMap<Integer, Integer>();
		for (String string : strings) {
			int index = Translate.bagStringToInt(string);
			if (index != -1 && index < Bag.MAX_CELLS) {

				Cell cell = online.getBag().getCells()[index];
				Item item = cell.getItem();
				if (item != null && item.getType().equals(EItem.equip)) {
					Equip equip = (Equip) item;

					if (equip.getPart().equals(EEquip.shenbing)) {
						result = 1;
						reason = "神兵不能分解";
						return;
					}

					if (equip.getIsDeal() == 0) { // 不绑定

						if (equip.getPart().equals(EEquip.shenbing)) {
							int num = equip.getResolve();
							total += (num > 0 ? num : 1);
						} else {
							int num = equip.getResolve();
							total += (num > 0 ? num : 1);
							EquipStarRes equipStarRes = IntensifyJson.instance().getEquipStarRes(equip.getStar());
							if (equipStarRes != null) {
								total += equipStarRes.getSum();
							}
						}

						// =========星分解=======
						IntensifyResolve intensifyResolve = IntensifyJson.instance().getIntensifyResolve(equip.getStar());
						if (intensifyResolve != null && intensifyResolve.getItem() != null) {

							Iterator<Entry<Integer, Integer>> it = intensifyResolve.getItem().entrySet().iterator();
							while (it.hasNext()) {
								Entry<Integer, Integer> entry = it.next();

								if (map.containsKey(entry.getKey())) {
									map.put(entry.getKey(), map.get(entry.getKey()) + entry.getValue());
								} else {
									map.put(entry.getKey(), entry.getValue());
								}
							}
						}

					} else { // 绑定

						if (equip.getPart().equals(EEquip.shenbing)) {
							int num = equip.getResolve();
							totalBind += (num > 0 ? num : 1);
						} else {
							int num = equip.getResolve();
							totalBind += (num > 0 ? num : 1);
							EquipStarRes equipStarRes = IntensifyJson.instance().getEquipStarRes(equip.getStar());
							if (equipStarRes != null) {
								totalBind += equipStarRes.getSum();
							}
						}

						// =========星分解=======
						IntensifyResolve intensifyResolve = IntensifyJson.instance().getIntensifyResolve(equip.getStar());
						if (intensifyResolve != null && intensifyResolve.getItem() != null) {
							Iterator<Entry<Integer, Integer>> it = intensifyResolve.getItem().entrySet().iterator();
							while (it.hasNext()) {
								Entry<Integer, Integer> entry = it.next();

								if (mapBind.containsKey(entry.getKey())) {
									mapBind.put(entry.getKey(), mapBind.get(entry.getKey()) + entry.getValue());
								} else {
									mapBind.put(entry.getKey(), entry.getValue());
								}
							}
						}
					}

					list.add(index); // 清除格子记录
				}
			}
		}

		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

		for (Integer index : list) {
			online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.equipResolve); // 删除物品
		}

		if (total > 0) {
			Item item = ItemJson.instance().getItem(20049);
			BagServer.add(online, item, total, cellsMap, EItemGet.equipResolve);
		}

		if (totalBind > 0) {
			Item bindItem = ItemJson.instance().getItem(20049).bind();
			BagServer.add(online, bindItem, totalBind, cellsMap, EItemGet.equipResolve);
		}

		Iterator<Entry<Integer, Integer>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Integer> entry = it.next();

			Item item = ItemJson.instance().getItem(entry.getKey());
			BagServer.add(online, item, entry.getValue(), cellsMap, EItemGet.equipResolve);
		}

		Iterator<Entry<Integer, Integer>> itBind = mapBind.entrySet().iterator();
		while (itBind.hasNext()) {
			Entry<Integer, Integer> entry = itBind.next();

			Item item = ItemJson.instance().getItem(entry.getKey()).bind();
			BagServer.add(online, item, entry.getValue(), cellsMap, EItemGet.equipResolve);
		}

		BagMessage.sendBag(online, cellsMap);
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_equipResolve");

			message.write("result", result);
			message.write("reason", reason);
			// System.out.println("result=" + result + "    reason=" + reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

	}
}