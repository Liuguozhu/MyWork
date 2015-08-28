package iyunu.NewTLOL.net.protocol.activity;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.activity.HuntTreasureInfo;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.helper.EHelper;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.helper.HelperServer;
import iyunu.NewTLOL.server.role.RoleServer;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 寻宝获得物品
 * 
 * @author SunHonglei
 * 
 */
public class HuntTreasureGet extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "寻宝获得成功";

		int index = msg.readInt("index");
		int type = msg.readInt("type"); // 2.1000银两，3.500银两

		HuntTreasureInfo huntTreasureInfo = ActivityJson.instance().getHuntTreasure(index - 1);
		if (huntTreasureInfo == null) {
			result = 1;
			reason = "寻宝失败";
			return;
		}
		Item item = ItemJson.instance().getItem(huntTreasureInfo.getItem());
		if (item == null) {
			result = 1;
			reason = "寻宝失败";
			return;
		}
		if (type == 2) {
			if (!RoleServer.costCoin(online, 1000, EGold.huntTreasure)) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 2);
				return;
			}
		} else {
			if (!RoleServer.costCoin(online, 500, EGold.huntTreasure)) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 2);
				return;
			}
		}
		item.setIsDeal(huntTreasureInfo.getIsBind());

		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		// online.getBag().add(item, 1, cellsMap, EItemGet.huntTreasure);
		BagServer.add(online, item, 1, cellsMap, EItemGet.huntTreasure);
		BagMessage.sendBag(online, cellsMap);
		
		HelperServer.helper(online, EHelper.xunbao); // 小助手记录
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_huntTreasureGet");
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