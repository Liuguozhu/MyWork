package iyunu.NewTLOL.net.protocol.blood;

import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.blood.BloodServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class QueryMark extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "查询血战总分成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "查询血战总分成功";
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_queryMark");
			message.write("result", result);
			if (result == 0) {
				message.write("markHong", BloodServer.getHongMark());
				message.write("markLan", BloodServer.getLanMark());
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
