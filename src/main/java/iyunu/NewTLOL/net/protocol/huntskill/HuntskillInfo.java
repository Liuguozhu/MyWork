package iyunu.NewTLOL.net.protocol.huntskill;

import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.CommonConst;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 初始化猎技
 * @author LSR
 * @date 2012-8-27
 */
public class HuntskillInfo extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "初始化猎技成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		if (online.getLevel() < CommonConst.INT_FOURTY) {
			result = 1;
			reason = "初始化猎技失败，40级开启";
			return;
		}

		result = 1;
		reason = "初始化猎技成功";
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_huntskillInfo");

			message.write("result", result);
			message.write("reason", reason);
			message.write("score", online.getHuntskillScore());
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}

}
