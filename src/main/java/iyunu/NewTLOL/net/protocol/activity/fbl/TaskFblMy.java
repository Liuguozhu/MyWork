package iyunu.NewTLOL.net.protocol.activity.fbl;

import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.model.activity.fbl.ActivityFblInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class TaskFblMy extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
//		LogManager.logOut("------>我发布的任务<------");
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_taskFblMy");
			if (ActivityManager.activityFblMy.containsKey(online.getId())) {
				for (ActivityFblInfo activityFblInfo : ActivityManager.activityFblMy.get(online.getId())) {
					LlpMessage message = llpMessage.write("taskFblInfoList");
					message.write("id", activityFblInfo.getId());
					message.write("color", activityFblInfo.getColor().ordinal());
					message.write("senderName", activityFblInfo.getSenderName());
					message.write("awardCoin", activityFblInfo.getCoin());
					message.write("awardExp", activityFblInfo.getExp());
					message.write("taskId", activityFblInfo.getTaskId());
					message.write("state", activityFblInfo.getState());
					message.write("time", 0);
				}
			}

			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
//		LogManager.logOut("======>我发布的任务<======");
	}
}