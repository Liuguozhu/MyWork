package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.enumeration.common.EGang;
import iyunu.NewTLOL.manager.IOProcessManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.gang.GangJobTitle;
import iyunu.NewTLOL.model.io.gang.GangIOTask;
import iyunu.NewTLOL.model.io.gang.instance.GangUpdateTask;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 更改帮派公告
 * 
 * @author fenghaiyu
 * 
 */
public class UpdateBulletin extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "修改成功！";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "修改成功！";

		if (online.getGangId() == 0) {
			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}

		Gang gang = GangManager.instance().getGang(online.getGangId());
		if (gang.getJobTitle(online) != GangJobTitle.Leader) {
			result = 1;
			reason = "操作无效,无权限！";
			return;
		}

		String gangBulletin = msg.readString("gangBulletin");
		if (gangBulletin.length() > 100) {
			result = 1;
			reason = "字数过多！";
			return;
		}
		GangManager.instance().getGang(online.getGangId()).setGangBulletin(gangBulletin);

		GangIOTask task = new GangUpdateTask(gang);
		IOProcessManager.instance().addGangTask(task);
		LogManager.gang(online.getId(), online.getUserId(), online.getGangId(), gang.getId(), gangBulletin, 0, 0, EGang.更改公告.ordinal());
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_updateBulletin");
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
