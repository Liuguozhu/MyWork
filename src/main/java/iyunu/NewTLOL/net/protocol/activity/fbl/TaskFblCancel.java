package iyunu.NewTLOL.net.protocol.activity.fbl;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.model.activity.fbl.ActivityFblInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class TaskFblCancel extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		LogManager.logOut("------>撤销发布令任务<------");
		result = 0;
		reason = "撤销成功";

		long id = msg.readLong("id");

		ActivityFblInfo activityFblInfo = ActivityManager.activityFblInfos.get(id);
		if (activityFblInfo == null) {
			result = 0;
			reason = "撤销失败";
			return;
		}
		if (activityFblInfo.getState() == 1) {
			result = 0;
			reason = "撤销失败，任务已被接取";
			return;
		}
		if (activityFblInfo.getState() == 2) {
			result = 0;
			reason = "撤销失败，任务已完成";
			return;
		}

		RoleServer.addCoin(online, activityFblInfo.getCoin(), EGold.fabulingReturn); // 返还银两
		ActivityManager.cancelActivityFbl(activityFblInfo); // 取消发布令
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_taskFblCancel");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
		LogManager.logOut("======>撤销发布令任务<======");
	}
}