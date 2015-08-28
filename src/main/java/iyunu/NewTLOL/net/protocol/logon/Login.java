package iyunu.NewTLOL.net.protocol.logon;

import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpMessage;

public class Login extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
		LogManager.error("收到login协议");
	}

	@Override
	public void handleReply() throws Exception {

	}
}