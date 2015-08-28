package iyunu.NewTLOL.net.protocol.mall;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.MallJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.mall.EMall;
import iyunu.NewTLOL.model.mall.EPrice;
import iyunu.NewTLOL.model.mall.instance.Mall;
import iyunu.NewTLOL.model.vip.EVip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.CommonConst;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class BuyMall extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "购买成功";
		cellsMap.clear();

		int index = msg.readInt("index");
		int number = msg.readInt("number");

		if (number <= CommonConst.INT_ZORE) {
			result = 1;
			reason = "请选择物品数量！";
			return;
		}

		Mall mall = MallJson.instance().getMallByIndex(index);

		if (mall == null) {
			result = 1;
			reason = "没有该物品！";
			return;
		}

		if (mall.getType().equals(EMall.vip) && !online.getVip().isVip(EVip.diamond)) {
			result = 1;
			reason = "只有钻石VIP才能购买！";
			return;
		}

		Item item = ItemJson.instance().getItem(mall.getItemId());
		item.setIsDeal(mall.getIsBind());
		if (item.getMax() < number) {
			number = item.getMax();
			reason = "购买成功，一次最多购买" + number + "个";
		}

		int price = (int) Math.ceil(mall.getPrice() * (mall.getOff() / 100f));
		price = price * number;
		if (mall.getPriceType().equals(EPrice.money)) {

			if (!RoleServer.costMoney(online, price, EMoney.buyMall)) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 3);
				return;
			}

		} else if (mall.getPriceType().equals(EPrice.coin)) {

			if (!RoleServer.costCoin(online, price, EGold.buyMall)) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 2);
				return;
			}

		} else if (mall.getPriceType().equals(EPrice.gold)) {

			if (!RoleServer.costGold(online, price, EGold.buyShop)) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 2);
				return;
			}

		} else {
			if (!RoleServer.costTribute(online, price)) {
				result = 1;
				reason = "您的帮贡不足！";
				return;
			}
		}

		// 添加商城物品
		BagServer.add(online, item, number, cellsMap, EItemGet.mall);

		// 商城购买日志
		if (mall.getPriceType().equals(EPrice.money)) {
			LogManager.mall(online, item, number, price);
		} else if (mall.getPriceType().equals(EPrice.gold)) {

		} else {

		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;

		try {
			message = LlpJava.instance().getMessage("s_buyMall");
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
