package iyunu.NewTLOL.net.protocol.base;

import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 提交问题
 * @author LuoSR
 * @date 2014年4月14日
 */
public class KeFu extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "提交成功";

		int type = msg.readInt("type");
		String title = msg.readString("title");
		String content = msg.readString("content");
		LogManager.kefu(type, title, content,online);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_kefu");
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
