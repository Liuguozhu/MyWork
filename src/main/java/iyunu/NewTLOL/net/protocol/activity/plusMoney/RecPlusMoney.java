package iyunu.NewTLOL.net.protocol.activity.plusMoney;

import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.activity.plusMoney.PlusMoney;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 查询累计充值
 * 
 * @author SunHonglei
 * 
 */
public class RecPlusMoney extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private int id = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "获得累计充值成功";

		id = msg.readInt("id");
		Map<Integer, PlusMoney> p = ActivityJson.instance().getPlusMoney();
		if (!p.keySet().contains(id)) {
			result = 1;
			reason = "累计充值请求错误";
			return;
		}
		if (p.get(id).getMoney() > online.getPlusMoney()) {
			result = 1;
			reason = "没有达到领取的要求";
			return;
		}
		if (online.getRecPlusMoney().contains(id)) {
			result = 1;
			reason = "已经领取过";
			return;
		}

		// 设置为已领取
		online.getRecPlusMoney().add(id);
		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		List<MonsterDropItem> m = p.get(id).getItems();
		if (m != null) {
			// 发放新物品
			for (int i = 0; i < m.size(); i++) {
				Item newItem = ItemJson.instance().getItem(m.get(i).getItemId());
				newItem.setIsDeal(m.get(i).getIsBind());
				BagServer.add(online, newItem, m.get(i).getNum(), cellsMap, EItemGet.plusMoney);
			}
			// 刷新背包
			BagMessage.sendBag(online, cellsMap);
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_recPlusMoney");
			llpMessage.write("result", result);
			if (result == 0) {
				if (ActivityJson.instance().getPlusMoney().size() == online.getRecPlusMoney().size()) {
					SendMessage.refreshPlusMoneyFlag(online, 1);
				}
			}
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}