package iyunu.NewTLOL.net.protocol.lzjh;

import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class TransmitSpecialData extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
		int index = msg.readInt("index");
		online.setSpecialGuide(index);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_transmitSpecialData");
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
