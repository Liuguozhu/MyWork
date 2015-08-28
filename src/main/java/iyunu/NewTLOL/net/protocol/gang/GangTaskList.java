package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.manager.gang.GangTaskManager;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 帮派任务初始化
 * @author fhy
 * @date 2014年10月20日
 */
public class GangTaskList extends TLOLMessageHandler {
	private int type = 0;
	private int result = 0;
	private String reason = "帮派任务初始化成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "帮派任务初始化成功";
		type = msg.readInt("type");
		if (online.getGang() == null || online.getGang().getLevel() < 2) {
			result = 1;
			reason = "帮派等级达到2级才可进行帮派任务";
			return;
		}
		// 如果是换任务
		if (type == 1) {
			if (online.getMoney() < 40) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 3);
				return;
			}
			RoleServer.costMoney(online, 40, EMoney.gangTask);
			GangTaskManager.instance().initRoleGangTask(online.getId());
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_gangTaskList");
			message.write("result", result);
			if (result == 0) {
				GangMessage.refreshGangTaskList(online);
			}
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
