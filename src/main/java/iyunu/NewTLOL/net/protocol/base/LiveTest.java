package iyunu.NewTLOL.net.protocol.base;

import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class LiveTest extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
//		int index = msg.readInt("index");
//		LogManager.info("心跳测试===" + index);
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_liveTest");
			message.write("result", 1);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

	}
}