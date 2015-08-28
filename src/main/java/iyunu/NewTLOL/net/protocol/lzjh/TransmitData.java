package iyunu.NewTLOL.net.protocol.lzjh;

import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class TransmitData extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
		int index = msg.readInt("index");
		online.setGuide(index);
		// LogManager.logOut("*************************【" + online.getNick() +
		// "】获得新手引导索引 index=" + index);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_transmitData");
			llpMessage.write("result", 0);
			channel.write(llpMessage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

}
