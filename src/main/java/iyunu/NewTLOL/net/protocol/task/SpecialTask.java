package iyunu.NewTLOL.net.protocol.task;

import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.instance.NPCFightTask;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpMessage;

/**
 * 特殊任务
 * 
 * @author SunHonglei
 * 
 */
public class SpecialTask extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
		int type = msg.readInt("type");

		int taskId = 0;
		if (type == 1) {
			taskId = 3;
		} else if (type == 2) {
			taskId = 705;
		}

		BaseTask baseTask = online.getTasks().get(taskId);
		if (baseTask != null) {
			NPCFightTask fightTask = (NPCFightTask) baseTask;
			fightTask.finish();
		}
	}

	@Override
	public void handleReply() throws Exception {
	}

}
