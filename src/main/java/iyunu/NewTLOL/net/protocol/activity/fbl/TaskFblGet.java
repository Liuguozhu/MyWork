package iyunu.NewTLOL.net.protocol.activity.fbl;

import iyunu.NewTLOL.json.TaskJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.activity.fbl.ActivityFblInfo;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.task.TaskServer;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class TaskFblGet extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		LogManager.logOut("------>接取发布令任务<------");
		result = 0;
		reason = "接取成功";

		long id = msg.readLong("id");

		ActivityFblInfo activityFblInfo = ActivityManager.activityFblInfos.get(id);
		if (activityFblInfo == null) {
			result = 1;
			reason = "接取失败，任务已不存在";
			return;
		}
		if (activityFblInfo.getState() == 1) {
			result = 1;
			reason = "接取失败，任务已被接取";
			return;
		}
		if (activityFblInfo.getState() == 2) {
			result = 1;
			reason = "接取失败，任务已完成";
			return;
		}

		// 不能接取自己发布的任务
		if (online.getId() == activityFblInfo.getRoleId()) {
			result = 1;
			reason = "接取失败，不能接取自己发布的任务";
			return;
		}

		if (online.getFabuling() != null || online.getTaskFblId() != 0) {
			result = 1;
			reason = "接取失败，请先完成当前的发布任务";
			return;
		}

		long timeOut = System.currentTimeMillis() + Time.HOUR_MILLISECOND;
		// 修改任务状态
		activityFblInfo.setState(1);
		activityFblInfo.setReceverId(online.getId());
		activityFblInfo.setTimeOut(timeOut);

		// 添加新任务
		BaseTask task = TaskJson.instance().getTask(activityFblInfo.getTaskId());
		task.setCoin(activityFblInfo.getCoin());
		task.setTimeOut(timeOut);
		online.getTasks().put(task.getId(), task);
		online.setFabuling(activityFblInfo);
		online.setTaskFblId(activityFblInfo.getId());
		TaskServer.getTask(task, online);
		TaskMessage.sendTask(online);

		ActivityManager.activityFblTimer.put(activityFblInfo.getId(), activityFblInfo);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_taskFblGet");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
		LogManager.logOut("======>接取发布令任务<======");
	}
}