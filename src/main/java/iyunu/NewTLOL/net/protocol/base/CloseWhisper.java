package iyunu.NewTLOL.net.protocol.base;

import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class CloseWhisper extends TLOLMessageHandler {

	private int result = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		
		int close = msg.readInt("close");
		if (close == 1) {
			online.setCloseWhisper(true);
		} else {
			online.setCloseWhisper(false);
		}
	}

	@Override
	public void handleReply() throws Exception {

		if (result == 0) {
			LlpMessage message = null;
			try {
				message = LlpJava.instance().getMessage("s_closeWhisper");

				message.write("result", result);
				channel.write(message);
			} finally {
				if (message != null) {
					message.destory();
				}
			}
		}

	}
}