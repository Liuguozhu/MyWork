package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 当乐充值
 * @author LuoSR
 * @date 2014年8月12日
 */
public class DownJoyPay extends TLOLMessageHandler {
	private String ext;

	@Override
	public void handleReceived(LlpMessage msg) {
		String url = PayManager.instance().getUrl() + ":" + ServerManager.MX4J_PORT;
		ext = online.getId() + "&" + ServerManager.instance().getSrvId() + "&" + url;
		LogManager.info("当乐充值透传参数：" + ext);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_downJoyPay");
			message.write("ext", ext);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
