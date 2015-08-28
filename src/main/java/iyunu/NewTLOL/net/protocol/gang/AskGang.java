package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Time;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 请求加入帮派
 * 
 * @author fenghaiyu
 * 
 */
public class AskGang extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "申请入帮成功，请等待申批";

	@Override
	public void handleReceived(LlpMessage msg) {
		long gangId = msg.readLong("gangId");
		result = 0;
		reason = "申请入帮成功，请等待申批";

		if (online.getGangId() != 0) {
			result = 1;
			reason = "您必须退出现在的帮派，才能申请加入其它帮派！";
			return;
		}

		if (!Time.beforeYesterday(online.getLeaveGangTime())) {
			result = 1;
			reason = "退出帮派当天不可以申请加入其它帮派！";
			return;
		}

		Gang gang = GangManager.instance().getGang(gangId);
		if (gang == null) {
			result = 1;
			reason = "帮派不存在！";
			return;
		}
		if (gang.getAskGang().size() > 10) {
			result = 1;
			reason = "该帮派申请列表已满！";
			return;
		}

		if (gang.getMembers().size() > gang.getSize()) {
			result = 1;
			reason = "该帮派已满员！";
			return;
		}

		gang.addAskGang(online.getId());
		online.getAskGang().add(gangId);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_askGang");
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
