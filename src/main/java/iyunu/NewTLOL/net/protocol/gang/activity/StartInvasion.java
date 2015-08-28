package iyunu.NewTLOL.net.protocol.gang.activity;

import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.processor.activity.GangInvasionProcessorCenter;
import iyunu.NewTLOL.util.Time;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 帮主开启帮派入侵
 * 
 * @author SunHonglei
 * 
 */
public class StartInvasion extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "开启成功";

		if (online.getGangId() == 0 || online.getGang() == null) {
			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}

		if (!(GangManager.instance().getGang(online.getGangId()) != null && online.getId() == GangManager.instance().getGang(online.getGangId()).getLeader())) {
			result = 1;
			reason = "操作失败,只有帮主能开启活动！";
			return;
		}

		if (!Time.isYesterday(online.getGang().getInvasionTime())) {
			result = 1;
			reason = "操作失败,今日已开启过活动！";
			return;
		}

		long now = System.currentTimeMillis();
		GangInvasionProcessorCenter gangInvasionProcessorCenter = new GangInvasionProcessorCenter(online.getGang(), now + Time.MINUTE_MILLISECOND * 30);
		gangInvasionProcessorCenter.start();

		online.getGang().setInvasionTime(now);
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_startInvasion");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}