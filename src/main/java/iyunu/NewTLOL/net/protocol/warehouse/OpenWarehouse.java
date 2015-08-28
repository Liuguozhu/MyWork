package iyunu.NewTLOL.net.protocol.warehouse;

import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.vip.EVip;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 打开仓库
 * @author LuoSR
 * @date 2014年3月13日
 */
public class OpenWarehouse extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "";

		int type = msg.readInt("type");

		if (type == 1 && online.getVip().isVip(EVip.common)) {
			result = 1;
			reason = "不是VIP";
			return;
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_sortWarehouse");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
		if (result == 0) {
			BagMessage.sendWarehouse(online);
		}
	}
}