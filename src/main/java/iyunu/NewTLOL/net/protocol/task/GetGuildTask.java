package iyunu.NewTLOL.net.protocol.task;

import iyunu.NewTLOL.manager.TaskManager;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.task.TaskServer;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 接取师门任务
 * 
 * @author SunHonglei
 * 
 */
public class GetGuildTask extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private int taskId;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "任务领取成功";

		if (online.getLevel() < TaskManager.GUILD_TASK_LEVEL) { // 等级不足
			result = 1;
			reason = "等级不足";
			return;
		}

		if (online.getGuildTask() != null) {
			result = 1;
			reason = "领取失败，上次的任务还未完成";
			return;
		}

		if (online.getGuildTaskSum() >= TaskManager.GUILD_TASK_MAX) {
			result = 1;
			reason = "今天" + TaskManager.GUILD_TASK_MAX + "个门派任务您已经全部完成，表现非常好！明天再接再厉！";
			return;
		}

		taskId = TaskServer.getGuildTask(online);
		
		LogManager.taskLog(online, taskId, 0); // 游戏日志
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
		TaskMessage.sendTask(online);
	}

}
