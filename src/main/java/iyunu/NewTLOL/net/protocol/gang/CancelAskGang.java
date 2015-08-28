package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 请求加入帮派
 * 
 * @author fenghaiyu
 * 
 */
public class CancelAskGang extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "操作成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		long gangId = msg.readLong("gangId");
		try {
			Gang gang = GangManager.instance().getGang(gangId);
			if (gang != null) {
				gang.getAskGang().remove(online.getId());
			}
			online.getAskGang().remove(gang.getId());
		} catch (Exception e) {
			e.printStackTrace();
			result = 1;
			reason = "撤消申请加入帮派失败！";
			return;
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_cancelAskGang");
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
