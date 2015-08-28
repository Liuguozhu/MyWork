package iyunu.NewTLOL.net.protocol.activity.fbl;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.json.TaskJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.TaskManager;
import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.message.ActivityMessage;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.activity.fbl.ActivityFblInfo;
import iyunu.NewTLOL.model.activity.fbl.ActivityFblRes;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.helper.EHelper;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.task.zone.TaskFblZone;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.helper.HelperServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 发布任务
 * 
 * @author SunHonglei
 * 
 */
public class TaskFblRelease extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
//		LogManager.logOut("------>发布任务<------");
		result = 0;
		reason = "发布成功";

		int index = msg.readInt("index");
		int coin = msg.readInt("coin");

		ActivityFblRes activityFblRes = ActivityJson.instance().getActivityFbls().get(index);
		if (activityFblRes == null) {
			result = 1;
			reason = "发布失败";
			return;
		}

		int bagIndex = online.getBag().isInBagIndexById(activityFblRes.getItemId(), 1);
		if (bagIndex == -1) {
			result = 1;
			reason = "发布失败，发布令不足！";
			return;
		}

		if (activityFblRes.getMinCoin() > coin) {
			result = 1;
			reason = "发布失败，最少需要奖励" + StringControl.grn(activityFblRes.getMinCoin()) + "银两";
			return;
		}

		if (online.getCoin() < coin) {
			result = 1;
			reason = "";
			SendMessage.refreshNoCoin(online, 2);
			return;
		}

		// 每日最多发布5个
		if (online.getTaskFblNum() <= 0) {
			result = 1;
			reason = "发布失败，每日最多发布" + TaskManager.FBL_TASK_NUM + "个任务！";
			return;
		}
		// 一共最多同时发布10个
		if (ActivityManager.countFblNum(online.getId()) >= TaskManager.FBL_TASK_SUM) {
			result = 1;
			reason = "发布失败，最多同时发布" + TaskManager.FBL_TASK_SUM + "个任务！";
			return;
		}

		TaskFblZone taskFblZone = TaskJson.instance().getTaskFblZone(activityFblRes.getZone());
		if (taskFblZone == null) {
			result = 1;
			reason = "发布失败";
			return;
		}

		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		online.getBag().removeByIndex(bagIndex, 1, cellsMap, EItemCost.fabuling); // 删除物品
		BagMessage.sendBag(online, cellsMap);

		RoleServer.costCoin(online, coin, EGold.fabuling); // 扣除银两

		ActivityFblInfo activityFblInfo = new ActivityFblInfo();
		activityFblInfo.setId(UidManager.instance().uid_fabuling());
		activityFblInfo.setRoleId(online.getId());
		activityFblInfo.setSenderName(online.getNick());
		activityFblInfo.setColor(activityFblRes.getColor());
		activityFblInfo.setCoin(coin);
		activityFblInfo.setExp(activityFblRes.getExp());
		activityFblInfo.setState(0);
		activityFblInfo.setTaskId(taskFblZone.randomTaskId());

		ActivityManager.addActivityFbl(activityFblInfo); // 保存

		online.setTaskFblNum(online.getTaskFblNum() - 1);
		HelperServer.helper(online, EHelper.fbl); // 小助手记录
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_taskFblRelease");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);

			ActivityMessage.refreshTaskFblNum(online);

			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
//		LogManager.logOut("======>发布任务<======");
	}
}