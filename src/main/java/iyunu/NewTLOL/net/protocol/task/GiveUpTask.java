package iyunu.NewTLOL.net.protocol.task;

import iyunu.NewTLOL.json.TaskJson;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskCategory;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.model.task.ETaskType;
import iyunu.NewTLOL.model.task.instance.BattleOnMapTask;
import iyunu.NewTLOL.model.task.instance.GhostTask;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 放弃任务
 * 
 * @author SunHonglei
 * 
 */
public class GiveUpTask extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private int taskId;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "任务已放弃";

		taskId = msg.readInt("taskId");

		if (!online.getTasks().containsKey(taskId)) {
			result = 1;
			reason = "任务不存在";
			return;
		}

		BaseTask oldTask = online.getTasks().get(taskId);
		if (oldTask.getState().equals(ETaskState.finish)) {
			result = 1;
			reason = "任务已完成，不能放弃";
			return;
		}

		if (oldTask.getCategory().equals(ETaskCategory.main)) {
			result = 1;
			reason = "主线任务不能放弃";
			return;
		} else if (oldTask.getCategory().equals(ETaskCategory.guild)) {

			// 从任务列表中删除任务
			online.getTasks().remove(taskId);
			online.setGuildTask(null);
			if (oldTask.getType().equals(ETaskType.battleOnMapTask)) {
				BattleOnMapTask battleOnMapTask = (BattleOnMapTask) oldTask;
				online.getMapInfo().removeMonsterOnMap(battleOnMapTask.getUid());
			}
		} else if (oldTask.getCategory().equals(ETaskCategory.ghost)) {

			// 从任务列表中删除任务
			online.getTasks().remove(taskId);
			online.setGhostTask(null);

			BaseMap baseMap = MapManager.instance().getMapById(oldTask.getArriveMap());
			GhostTask ghostTask = (GhostTask) oldTask;
			MonsterOnMap monsterOnMap = baseMap.getMonsterOnMap(ghostTask.getUid());
			if (monsterOnMap != null) {
				monsterOnMap.removeOwner(online.getId());
			}
		} else if (oldTask.getCategory().equals(ETaskCategory.yingxiongtie)) { // =============英雄帖任务====================
			// 从任务列表中删除任务
			online.getTasks().remove(taskId);
			online.setYingxiongtie(null);

		} else if (oldTask.getCategory().equals(ETaskCategory.fabuling)) { // =============发布令任务====================

			// 从任务列表中删除任务
			online.getTasks().remove(taskId);
			if (oldTask.getType().equals(ETaskType.battleOnMapTask)) {
				BattleOnMapTask battleOnMapTask = (BattleOnMapTask) oldTask;
				online.getMapInfo().removeMonsterOnMap(battleOnMapTask.getUid());
			}

			if (oldTask.getState().equals(ETaskState.during)) {
				online.getFabuling().setState(0);
				online.getFabuling().setReceverId(0);
			}

			online.setFabuling(null);
			online.setTaskFblId(0);
		} else if (oldTask.getCategory().equals(ETaskCategory.cycle)) { // =============环任务====================

			// 从任务列表中删除任务
			online.getTasks().remove(taskId);
			if (oldTask.getType().equals(ETaskType.battleOnMapTask)) {
				BattleOnMapTask battleOnMapTask = (BattleOnMapTask) oldTask;
				online.getMapInfo().removeMonsterOnMap(battleOnMapTask.getUid());
			}

			online.setTaskCycle(null);
			online.setTaskCycleNum(0);

		} else {
			// 从任务列表中删除任务
			online.getTasks().remove(taskId);
			if (oldTask.getType().equals(ETaskType.battleOnMapTask)) {
				BattleOnMapTask battleOnMapTask = (BattleOnMapTask) oldTask;
				online.getMapInfo().removeMonsterOnMap(battleOnMapTask.getUid());
			}

			// 重新copy任务，并改变任务状态为可接取
			BaseTask task = TaskJson.instance().getTask(taskId);
			task.setState(ETaskState.not);
			// 将任务加到任务列表中
			online.getTasks().put(task.getId(), task);
		}
		LogManager.taskLog(online, oldTask.getId(), 2); // 游戏日志
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;

		try {
			llpMessage = LlpJava.instance().getMessage("s_giveUpTask");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
		TaskMessage.sendTask(online);
	}

}
