package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function OPPO
 * @author LuoSR
 * @date 2014年11月10日
 */
public class Oppo extends TLOLMessageHandler {

	private String callbackInfo;
	private String orderNumber;
	private String notifyUri;

	@Override
	public void handleReceived(LlpMessage msg) {

		String url = PayManager.instance().getUrl() + ":" + ServerManager.MX4J_PORT;
		notifyUri = PayManager.instance().getOppoUrl();
		orderNumber = String.valueOf(UidManager.instance().orderNum());
		callbackInfo = online.getId() + "*" + ServerManager.instance().getSrvId() + "*" + url;
		System.out.println("订单号：" + orderNumber);
		System.out.println("订单数据：" + callbackInfo);
		System.out.println("回调地址：" + notifyUri);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_oppo");
			message.write("callbackInfo", callbackInfo);
			message.write("orderNumber", orderNumber);
			message.write("notifyUri", notifyUri);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
