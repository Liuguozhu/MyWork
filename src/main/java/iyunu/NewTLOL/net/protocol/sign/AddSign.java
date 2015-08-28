package iyunu.NewTLOL.net.protocol.sign;

import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.RoleJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.role.RoleServer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 补签
 * 
 * @author fenghaiyu
 * 
 */
public class AddSign extends TLOLMessageHandler {
	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		// System.out.println("进入补签---------------------------------------");
		result = 0;
		reason = "补签成功";
		if (online.getMoney() < 10) {
			result = 1;
			reason = "";
			SendMessage.refreshNoCoin(online, 3);
			// System.out.println("补签失败,因为签到元宝不足10个");
			return;
		}
		List<Integer> list = online.getSignList();
		if (list.size() >= 30) {
			result = 1;
			reason = "补签失败，没有可补签的日期！";
			// System.out.println("补签失败,因为签到天数大于等于30");
			return;
		}
		RoleServer.costMoney(online, 10, EMoney.addSign);
		Collections.sort(list);
		// System.out.print("补签中排过序的已签结果：");
		// for (Integer integer : list) {
		// System.out.print(integer + ",");
		// }
		// System.out.println();
		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i) - list.get(i - 1) != 1) {
				list.add(list.get(i - 1) + 1);

				Iterator<Entry<Integer, MonsterDropItem>> it = RoleJson.instance().getSignItem().get(list.get(i - 1) + 1).getItemIds().entrySet().iterator();
				while (it.hasNext()) {
					Entry<Integer, MonsterDropItem> entry = it.next();
					Item item = ItemJson.instance().getItem(entry.getValue().getItemId());
					item.setIsDeal(entry.getValue().getIsBind());
					BagServer.add(online, item, entry.getValue().getNum(), cellsMap, EItemGet.signItem);
				}
				// System.out.println("计算补签出的补签结果：" + (list.get(i - 1) + 1));
				SendMessage.sendSign(online, 0, list.get(i - 1) + 1);
				return;
			}
		}
		BagMessage.sendBag(online, cellsMap, false);

	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_addSign");
			message.write("result", result);
			message.write("reason", reason);
			// System.out.println("进入补签结束 result=" + result + "---reason=" +
			// reason + "------------------------------------");
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
