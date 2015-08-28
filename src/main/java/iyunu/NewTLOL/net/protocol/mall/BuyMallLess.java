package iyunu.NewTLOL.net.protocol.mall;

import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.MallJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.lessItem.LessItemRes;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.CommonConst;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class BuyMallLess extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "购买成功";
		cellsMap.clear();

		int id = msg.readInt("id");
		int number = msg.readInt("number");

		if (number <= CommonConst.INT_ZORE) {
			result = 1;
			reason = "请选择物品数量！";
			return;
		}

		LessItemRes lessItem = MallJson.instance().getLessItem().get(id);

		if (lessItem == null) {
			result = 1;
			reason = "没有该物品！";
			return;
		}

		Item item = ItemJson.instance().getItem(lessItem.getId());
		if (item.getMax() < number) {
			number = item.getMax();
			reason = "购买成功，一次最多购买" + number + "个";
		}

		int price = lessItem.getPrice();
		price = price * number;

		if (!RoleServer.costMoney(online, price, EMoney.buyMall)) {
			result = 1;
			reason = "";
			SendMessage.refreshNoCoin(online, 3);
			return;
		}

		// 添加商城物品
		BagServer.add(online, item, number, cellsMap, EItemGet.mall);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;

		try {
			message = LlpJava.instance().getMessage("s_buyMallLess");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0) {
			// 刷新背包
			BagMessage.sendBag(online, cellsMap);
		}
	}

}
