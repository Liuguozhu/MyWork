package iyunu.NewTLOL.net.protocol.mining;

import iyunu.NewTLOL.json.MiningJson;
import iyunu.NewTLOL.manager.MiningManger;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.mining.Mining;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class GetMining extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "占矿成功";
	private int index = 0;
	Mining mining = null;
	private int page = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "占矿成功";
		index = msg.readInt("index");
		page = msg.readInt("page");
		SendMessage.refreshMiningList(online, page);
		if (index < 1 || index > MiningJson.instance().getMining().size()) {
			result = 1;
			reason = "占矿错误！";
			return;
		}
		mining = MiningManger.instance().getMiningMap().get(index);
		if (mining == null) {
			result = 1;
			reason = "占矿信息错误！";
			return;
		}
		if (!MiningManger.instance().ifPageIndex(page, index)) {
			result = 1;
			reason = "占矿页数信息发生错误！";
			return;
		}

		if (mining.getFlag() == 1) {
			result = 1;
			reason = "本矿区已有人，可以刷新后抢夺！";
			return;
		}
		if (MiningManger.instance().ifHadAMining(online.getId())) {
			result = 1;
			reason = "每人只能同时占一个矿！";
			return;
		}
		if (online.getTeam() != null) {
			result = 1;
			reason = "不可组队进入！";
			return;
		}
		int index = online.getBag().isInBagByType(EItem.fragment, 1);
		if (index == -1) {
			index = online.getBag().isInBagByType(EItem.fragment, 0);
			if (index == -1) {
				result = 1;
				reason = "没有残玉碎片！";
				return;
			}
		}

		// 删除1个残玉碎片
		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.mining);
		BagMessage.sendBag(online, cellsMap);

		mining.setFlag(1);
		mining.setRole(online.copyMinning());// 调用role的 copy方法
		mining.setStartTime(System.currentTimeMillis());
		SendMessage.refreshMiningList(online, page);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_getMining");
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
