package iyunu.NewTLOL.net.protocol.activity.fbl;

import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class TaskFblAward extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		LogManager.logOut("------>发布令任务领奖<------");
		result = 0;
		reason = "任务成功";

//		long id = msg.readLong("id");
//
//		ActivityFblInfo activityFblInfo = ActivityManager.activityFblInfos.get(id);
//		if (activityFblInfo == null) {
//			result = 0;
//			reason = "任务失败";
//			return;
//		}
//		if (activityFblInfo.getState() != 2) {
//			result = 0;
//			reason = "任务失败，任务未完成";
//			return;
//		}
//
//		RoleServer.addExp(online, activityFblInfo.getExp(), EExp.fabuling);
//		ActivityManager.cancelActivityFbl(activityFblInfo); // 取消发布令
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_taskFblAward");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
		LogManager.logOut("======>发布令任务领奖<======");
	}
}