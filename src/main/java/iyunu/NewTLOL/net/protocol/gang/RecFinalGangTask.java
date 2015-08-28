package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.json.GangJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.manager.gang.GangTaskManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.gang.GangTaskModel;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 帮派任务领取最终奖品
 * @author fhy
 * @date 2014年10月20日
 */
public class RecFinalGangTask extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "领取成功";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "领取成功";
		cellsMap.clear();

		if (online.getBag().isFull()) {
			result = 1;
			reason = "背包已满，请先清空背包！";
			return;
		}
		GangTaskModel gangTaskModel = GangTaskManager.instance().getGangTaskMap().get(online.getId());
		if (gangTaskModel == null) {
			result = 1;
			reason = "帮派任务数据有误！";
			return;
		}
		boolean flag1 = true;
		for (int i = 0; i < gangTaskModel.getTaskList().size(); i++) {
			if (gangTaskModel.getTaskList().get(i).getFinish() == 0) {
				flag1 = false;
			}
		}
		if (!flag1) {
			result = 1;
			reason = "未达到帮派任务领取条件！！";
			return;
		}
		if (gangTaskModel.getFinish() == 1) {
			result = 1;
			reason = "您已领取过，不可重复领取！！";
			return;
		}

		// 设置为已领取
		gangTaskModel.setFinish(1);
		// 发放新物品
		MonsterDropItem msd = GangJson.instance().getGangTaskAllAward().get(gangTaskModel.getAwardIndex());
		Item newItem = ItemJson.instance().getItem(msd.getItemId());
		newItem.setIsDeal(msd.getIsBind());
		BagServer.add(online, newItem, msd.getNum(), cellsMap, EItemGet.gangTask);
		// 刷新背包
		BagMessage.sendBag(online, cellsMap);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_getGangTaskItem");
			message.write("result", result);
			if (result == 0) {
				GangMessage.refreshGangTaskList(online);
			}
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
