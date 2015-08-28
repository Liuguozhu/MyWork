package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Time;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 领取福利
 * 
 * @author fenghaiyu
 * 
 */
public class GetWelfareGang extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "领取福利成功";

	@Override
	public void handleReceived(LlpMessage msg) {

		result = 0;
		reason = "领取福利成功";

		if (online.getGang() == null) {
			result = 1;
			reason = "没有加入帮派，不可领取帮派福利";
			return;
		}
		long now = System.currentTimeMillis();
		if (now < Time.getThisDayOfWeek(6, 12, 00, 00) || now > Time.getThisDayOfWeek(6, 23, 59, 00)) {
			result = 1;
			reason = "帮派福利每周五" + StringControl.yel("12:00-23:59") + "可以领取";
			return;
		}
		if (GangManager.instance().hadGetWelfare.contains(online.getId())) {
			result = 1;
			reason = "本周福利领取过了！";
			return;
		}
		// 添加到已领取
		GangManager.instance().hadGetWelfare.add(online.getId());
		// 发绑银
		RoleServer.addGold(online, online.getGangActivity() * 1000, EGold.gangWelfare);
		// 发银两
		if (GangManager.instance().ifChampionGang(online)) {
			RoleServer.addCoin(online, online.getGangActivity() * 100, EGold.gangWelfare);
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_GetWelfareGang");
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
