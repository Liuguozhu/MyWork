package iyunu.NewTLOL.net.protocol.base;

import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpMessage;

/**
 * @function 客户端错误日志
 * @author LuoSR
 * @date 2014年6月5日
 */
public class ClientLog extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {

		String clientLog = msg.readString("clientLog");
		LogManager.clientLog(online, clientLog);
	}

	@Override
	public void handleReply() throws Exception {
	}

}
