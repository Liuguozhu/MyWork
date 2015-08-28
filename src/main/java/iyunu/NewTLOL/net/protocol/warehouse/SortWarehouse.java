package iyunu.NewTLOL.net.protocol.warehouse;

import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 仓库整理
 * @author LuoSR
 * @date 2014年2月27日
 */
public class SortWarehouse extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "";

		// 整里仓库
		online.getWarehouse().sortWarehouse();

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