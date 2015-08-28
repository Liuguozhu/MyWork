package iyunu.NewTLOL.net.protocol.base;

import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 周围玩家下发开关
 * 
 * @author SunHonglei
 * 
 */
public class Confirm extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "";
		int open = msg.readInt("open"); // 0.关，1.开
		if (open == 1) {
			online.setAround(true);
			online.getMapInfo().getMapAgent().clear();
		} else {
			online.setAround(false);
//			online.getMapInfo().getMapAgent().cleanNearby();
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_confirm");
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