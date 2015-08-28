package iyunu.NewTLOL.net.protocol.activity;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.manager.InformMamager;
import iyunu.NewTLOL.manager.OperationManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.activity.instance.OnlineAwardInfo;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Util;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 领取在线礼包
 * 
 * @author SunHonglei
 * 
 */
public class OnlineAward extends TLOLMessageHandler {

	private int result = 0;
	private String reason;
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "领取成功";
		cellsMap.clear();

		int id = msg.readInt("id");
		if (online.getOnlineAwardStateMap().get(id) == 0) {
			result = 1;
			reason = "不能领取";
			return;
		}

		if (online.getOnlineAwardStateMap().get(id) == 2) {
			result = 1;
			reason = "已领完";
			return;
		}

		OnlineAwardInfo onlineAwardInfo = ActivityJson.instance().getOnlineAwardInfo(id);

		int time = Util.matchZero(onlineAwardInfo.getTime() * Time.MINUTE_SECOND - (online.getOnlineTime() + (int) ((System.currentTimeMillis() - online.getLogonTime()) / Time.MILLISECOND)));

		if (time > 0) {
			result = 1;
			reason = "领取失败，在线时间不足";
			return;
		}

		long now = System.currentTimeMillis();
		if (OperationManager.ONLINE_START > now && OperationManager.ONLINE_END < now) {
			result = 1;
			reason = "领取失败，活动已结束";
			return;
		}

		Item item = ItemJson.instance().getItem(onlineAwardInfo.getItemId());
		item.setIsDeal(onlineAwardInfo.getIsBind());
		int itemNum = onlineAwardInfo.getItemNum();
		if (item != null) {
			if (online.getBag().isFull((itemNum - 1) / item.getMax() + 1)) {
				result = 1;
				reason = "领取失败，背包已满";
				return;
			}

//			online.getBag().add(item, itemNum, cellsMap, EItemGet.onlineGift);
			BagServer.add(online, item, itemNum, cellsMap, EItemGet.onlineGift);
		}

		online.getOnlineAwardStateMap().put(id, 2);

		RoleServer.addGold(online, onlineAwardInfo.getGold(), EGold.onlineAward);
		RoleServer.addMoney(online, onlineAwardInfo.getMoney(), EMoney.onlineAward);
		BagMessage.sendBag(online, cellsMap);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_onlineAward");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
		if (result == 0) {
			// ======检查在线奖励通知======
			InformMamager.instance().checkOnlineAward(online);
		}
	}
}