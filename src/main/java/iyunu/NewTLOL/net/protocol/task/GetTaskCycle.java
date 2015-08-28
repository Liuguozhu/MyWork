package iyunu.NewTLOL.net.protocol.task;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.manager.TaskManager;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.server.task.TaskServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 接取环任务
 * 
 * @author SunHonglei
 * 
 */
public class GetTaskCycle extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private int taskId;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "环任务领取成功";
		taskId = 0;

		if (online.getLevel() < 30) { // 等级不足
			result = 1;
			reason = "等级不足";
			return;
		}

		if (online.getTaskCycleRow() >= TaskManager.CYCLE_TASK_MAX) {
			result = 1;
			reason = "每周只能接取" + TaskManager.CYCLE_TASK_MAX + "轮环任务";
			return;
		}

		if (online.getTaskCycle() != null) {
			result = 1;
			reason = "当前已接取环任务";
			return;
		}

		if (online.getTeam() != null) {
			result = 1;
			reason = "环任务不能组队接取";
			return;
		}

		if (!RoleServer.costGold(online, online.getLevel() * 1000, EGold.taskCycle)) {
			result = 1;
			reason = "接取失败，银两不足";
			return;
		}

		taskId = TaskServer.getTaskCycle(online);
		online.setTaskCycleRow(online.getTaskCycleRow() + 1);
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
