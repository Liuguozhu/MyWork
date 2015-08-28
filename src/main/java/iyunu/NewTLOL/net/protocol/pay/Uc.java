package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function UC充值
 * @author LuoSR
 * @date 2014年10月15日
 */
public class Uc extends TLOLMessageHandler {

	private String callbackInfo;

	@Override
	public void handleReceived(LlpMessage msg) {
		String url = PayManager.instance().getUrl() + ":" + ServerManager.MX4J_PORT;
		callbackInfo = online.getId() + "*" + ServerManager.instance().getSrvId() + "*" + url;
		System.out.println("订单数据：" + callbackInfo);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_uc");
			message.write("callbackInfo", callbackInfo);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

	}

}
