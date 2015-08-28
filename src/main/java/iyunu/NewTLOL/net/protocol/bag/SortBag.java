package iyunu.NewTLOL.net.protocol.bag;

import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 背包整理
 * 
 * @author SunHonglei
 * 
 */
public class SortBag extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "";

		int type = msg.readInt("type");
		if (type == 1) {
			// 整里背包
			online.getBag().sortBag();
			BagMessage.logonSendBag(online);
		} else if (type == 2) {
			online.getBagStone().sortBag();
			BagMessage.sendStoneBag(online);
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_sortBag");
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