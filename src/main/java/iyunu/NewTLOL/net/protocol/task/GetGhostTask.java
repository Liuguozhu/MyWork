package iyunu.NewTLOL.net.protocol.task;

import iyunu.NewTLOL.common.RoleForm;
import iyunu.NewTLOL.json.TaskJson;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.MonsterManager;
import iyunu.NewTLOL.manager.TaskManager;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.EMonsterOnMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.model.task.TaskAwardItem;
import iyunu.NewTLOL.model.task.instance.GhostTask;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.task.TaskServer;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 接取江湖追杀令
 * 
 * @author SunHonglei
 * 
 */
public class GetGhostTask extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private int taskId;

	@Override
	public void handleReceived(LlpMessage msg) {

		result = 0;
		reason = "任务领取成功";

		if (online.getLevel() < 30) { // 等级不足
			result = 1;
			reason = "等级不足";
			return;
		}

		if (online.getGhostTaskSum() >= TaskManager.GHOST_TASK_MAX) {
			result = 1;
			reason = "今天" + TaskManager.GHOST_TASK_MAX + "个江湖追杀令您已经全部完成，表现非常好！明天再接再厉！";
			return;
		}

		if (online.getGhostTask() != null) {
			result = 1;
			reason = "领取失败，上次的任务还未完成";
			return;
		}

		int levelSum = online.getLevel();
		int num = 1;
		if (online.getTeam() != null) { // 组队情况下，队员一起接任务
			for (Role role : online.getTeam().getMember()) {
				if (role.getId() != online.getId()) {

					if (role.getLevel() < 40) { // 等级不足
						result = 1;
						reason = "玩家【" + role.getNick() + "】等级不足";
						return;
					}

					if (role.getGhostTaskNum() >= 50) {
						result = 1;
						reason = "玩家【" + role.getNick() + "】今天的江湖追杀令已经全部做完！";
						return;
					}

					if (role.getGhostTask() != null) {
						result = 1;
						reason = "玩家【" + role.getNick() + "】上次的任务还未完成";
						return;
					}

					levelSum += role.getLevel();
					num++;
				}
			}
		}

		int avgLevel = (int) (levelSum * 1f / num);
		GhostTask ghostTask = (GhostTask) TaskJson.instance().randomGhostTask(TaskManager.getZoneByLevel(avgLevel));

		ghostTask.setGold(RoleForm.ghostTaskGold(online.getLevel()));
		ghostTask.setExp(RoleForm.ghostTaskExp(online.getLevel()));
		ghostTask.setRole(online);
		ghostTask.setState(ETaskState.during);

		TaskAwardItem taskAwardItem = TaskServer.getGhostTaskAward(online.getGhostTaskSum());
		if (taskAwardItem != null) {
			ghostTask.getItemList().add(taskAwardItem);
		}

//		DrawingSite drawingSite = ActivityJson.instance().randomSite(ghostTask.getArriveMap());
//		MonsterOnMap ghostMonsterOnMap = new MonsterOnMap(ghostTask.getArriveMap(), drawingSite.getX(), drawingSite.getY(), ghostTask.getMonsterGroup(), EMonsterOnMap.taskGhost);
		MonsterOnMap ghostMonsterOnMap = new MonsterOnMap(ghostTask.getArriveMap(),ghostTask.getArriveX(), ghostTask.getArriveY(), ghostTask.getMonsterGroup(), EMonsterOnMap.taskGhost);
		ghostMonsterOnMap.setMonsterName(online.getNick() + "的" + ghostMonsterOnMap.getMonsterName());
		ghostMonsterOnMap.addOwner(online.getId()); // 设置怪物所有者
		ghostMonsterOnMap.setGhost(true);

		MonsterManager.instance().addMonsterOnMap(ghostMonsterOnMap, Time.MILLISECOND * 30 * 60); // 添加至怪物定时器

		BaseMap baseMap = MapManager.instance().getMapById(ghostTask.getArriveMap());
		if (baseMap != null) {
			baseMap.addMonster(ghostMonsterOnMap);
		}

		ghostTask.setUid(ghostMonsterOnMap.getUid()); // 设置任务中怪物的唯一编号

		taskId = ghostTask.getId();
		online.getTasks().put(ghostTask.getId(), ghostTask);
		online.setGhostTask(ghostTask);

		LogManager.taskLog(online, taskId, 0); // 游戏日志

		if (online.getTeam() != null) { // 组队情况下，队员一起接任务
			for (Role role : online.getTeam().getMember()) {
				if (role.getId() != online.getId()) {

					GhostTask memberTask = (GhostTask) TaskJson.instance().getTask(taskId);
					memberTask.setGold(RoleForm.ghostTaskGold(role.getLevel()));
					memberTask.setExp(RoleForm.ghostTaskExp(role.getLevel()));
					memberTask.setRole(role);
					memberTask.setState(ETaskState.during);

					TaskAwardItem memberTaskAwardItem = TaskServer.getGhostTaskAward(role.getGhostTaskSum());
					if (memberTaskAwardItem != null) {
						memberTask.getItemList().add(memberTaskAwardItem);
					}

					ghostMonsterOnMap.addOwner(role.getId()); // 设置怪物所有者

					memberTask.setUid(ghostMonsterOnMap.getUid()); // 设置任务中怪物的唯一编号

					role.getTasks().put(memberTask.getId(), memberTask);
					role.setGhostTask(memberTask);

					TaskServer.addGhostTaskNum(role);
					TaskMessage.sendTask(role);
					TaskMessage.refreshGhostTaskNum(role);
					LogManager.taskLog(role, taskId, 0); // 游戏日志
				}
			}
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;

		try {
			llpMessage = LlpJava.instance().getMessage("s_getTask");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			llpMessage.write("taskId", taskId);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
		TaskMessage.refreshGhostTaskNum(online);
		TaskMessage.sendTask(online);
	}

}
