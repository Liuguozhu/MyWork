package iyunu.NewTLOL.net.protocol.task;

import iyunu.NewTLOL.json.TaskJson;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.task.TaskServer;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 接取任务
 * 
 * @author SunHonglei
 * 
 */
public class GetTask extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private int taskId;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "任务领取成功";
		taskId = msg.readInt("taskId");

		if (!TaskJson.instance().exist(taskId)) { // 任务不存在
			result = 1;
			reason = "任务不存在";
			return;
		}

		if (TaskServer.checkFinishTask(online, taskId)) { // 任务已完成
			result = 1;
			reason = "任务已完成";
			return;
		}

		BaseTask task = online.getTasks().get(taskId);
		if (task == null) { // 任务不存在
			result = 1;
			reason = "任务已完成";
			return;
		}

		if (online.getLevel() < task.getLevel()) { // 等级不足
			result = 1;
			reason = "等级不足";
			return;
		}

		int precondition = task.getPrecondition();
		if (!TaskServer.checkFinishTask(online, precondition)) { // 检查前置任务
			result = 1;
			reason = "前置条件不满足";
			return;
		}
		TaskServer.getTask(task, online);
		LogManager.taskLog(online, task.getId(), 0); // 游戏日志

		if (online.getTeam() != null) { // 组队情况下，队员一起接任务
			for (Role role : online.getTeam().getMember()) {
				if (role.getId() != online.getId()) {
					if (TaskServer.checkFinishTask(role, taskId)) { // 任务已完成
						continue;
					}

					BaseTask memberTask = role.getTasks().get(taskId);
					if (memberTask == null) { // 任务不存在
						continue;
					}
					if (role.getLevel() < task.getLevel()) { // 等级不足
						continue;
					}
					int memberPrecondition = task.getPrecondition();
					if (!TaskServer.checkFinishTask(role, memberPrecondition)) { // 检查前置任务
						continue;
					}

					TaskServer.getTask(memberTask, role);
					TaskMessage.sendTask(role);
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

		if (result == 0) {
			TaskMessage.sendTask(online);
		}
	}

}
