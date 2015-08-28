package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.Time;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 活跃度领奖
 * @author LuoSR
 * @date 2014年9月3日
 */
public class ActiveAward extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "领取成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "领取成功";

		if (Time.getWeek() != 6) {
			result = 1;
			reason = "请周五领取！";
			return;
		}

		if (online.getActivityType() == 1) {
			result = 1;
			reason = "本周已领取，请下周领取";
			return;
		}

		if (online.getGangId() == 0) {
			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}

		if (GangManager.championId == online.getGangId()) {
			RoleServer.addCoin(online, online.getGangActivity() * 100, EGold.activeAward);
		}

		RoleServer.addGold(online, online.getGangActivity() * 1000, EGold.activeAward);

		online.setActivityType(1);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_activeAward");
			message.write("result", result);
			message.write("reason", reason);

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
