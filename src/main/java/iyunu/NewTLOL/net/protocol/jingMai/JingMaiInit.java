package iyunu.NewTLOL.net.protocol.jingMai;

import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 经脉初始化
 * @author LSR
 * @date 2012-8-27
 */
public class JingMaiInit extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_jingMaiInit");
			message.write("note", online.getJingMaiId() + 1);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
