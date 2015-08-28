package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.json.GangJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.manager.gang.GangTaskManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.gang.GangTaskModel;
import iyunu.NewTLOL.model.gang.GangTaskSingle;
import iyunu.NewTLOL.model.gang.res.GangTask;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 帮派任务领取奖品
 * @author fhy
 * @date 2014年10月20日
 */
public class RecGangTask extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "领取成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "领取成功";
		int index = msg.readInt("index");
		if (!GangJson.instance().getGangTask().keySet().contains(index)) {
			result = 1;
			reason = "帮派任务数据错误";
			return;
		}

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
		GangTaskSingle gangTaskSingle = null;
		boolean flag1 = false;//
		for (int i = 0; i < gangTaskModel.getTaskList().size(); i++) {
			if (gangTaskModel.getTaskList().get(i).getIndex() == index) {
				flag1 = true;
				gangTaskSingle = gangTaskModel.getTaskList().get(i);
			}
		}
		if (!flag1) {
			result = 1;
			reason = "帮派任务数据有误！！";
			return;
		}
		if (gangTaskSingle == null) {
			result = 1;
			reason = "帮派任务数据有误！！！";
			return;
		}

		GangTask gt = GangJson.instance().getGangTask().get(gangTaskSingle.getIndex());
		for (MonsterDropItem md : gt.getItems()) {
			if (BagServer.getTheItemNum(md.getItemId(), online) < md.getNum()) {
				result = 1;
				reason = "帮派任务需要的物品不足！";
				return;
			}
		}
		if (gangTaskSingle.getFinish() == 1) {
			result = 1;
			reason = "您已领取，不可重复领取！！";
			return;
		}
		// 扣除物品
		for (MonsterDropItem md : gt.getItems()) {
			Item item = ItemJson.instance().getItem(md.getItemId());
			if (item.getType() == EItem.stone) {
				Map<Integer, Integer> useMap = online.getBagStone().isInBagById(md.getItemId(), 1, md.getNum());
				Iterator<Entry<Integer, Integer>> useIt = useMap.entrySet().iterator();
				while (useIt.hasNext()) {
					Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
					Entry<Integer, Integer> entry = useIt.next();
					online.getBagStone().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.gangTask);
					// 刷新宝石背包
					BagMessage.sendBagStone(online, cellsMap);
				}
			} else {
				Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
				Map<Integer, Integer> useMap = online.getBag().isInBagById(md.getItemId(), 1, md.getNum());
				Iterator<Entry<Integer, Integer>> useIt = useMap.entrySet().iterator();
				while (useIt.hasNext()) {
					Entry<Integer, Integer> entry = useIt.next();
					online.getBag().removeByIndex(entry.getKey(), entry.getValue(), cellsMap, EItemCost.gangTask);
				}
				// 刷新背包
				BagMessage.sendBag(online, cellsMap);
			}

		}
		// 设置为已领取
		gangTaskSingle.setFinish(1);
		// 发放新物品
		for (int i = 0; i < gt.getAwards().size(); i++) {
			Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
			Item newItem = ItemJson.instance().getItem(gt.getAwards().get(i).getItemId());
			newItem.setIsDeal(gt.getAwards().get(i).getIsBind());
			BagServer.add(online, newItem, gt.getAwards().get(i).getNum(), cellsMap, EItemGet.gangTask);
			if (newItem.getType() == EItem.stone) {
				// 刷新宝石背包
				BagMessage.sendBagStone(online, cellsMap);
			} else {
				// 刷新背包
				BagMessage.sendBag(online, cellsMap);
			}
		}

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
