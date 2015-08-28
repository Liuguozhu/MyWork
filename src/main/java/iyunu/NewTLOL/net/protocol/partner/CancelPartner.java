package iyunu.NewTLOL.net.protocol.partner;

import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 取消出战伙伴
 * 
 * @author SunHonglei
 * 
 */
public class CancelPartner extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "取消成功";

		int index = msg.readInt("index");
		online.getPartnerFight().put(index, 0l);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_cancelParnter");
			message.write("result", result);
			message.write("reason", reason);
			if (result == 0) {
				PartnerMessage.refreshFightPartner(online);
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
