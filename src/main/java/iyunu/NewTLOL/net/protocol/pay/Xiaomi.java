package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 小米充值
 * @author LuoSR
 * @date 2014年10月27日
 */
public class Xiaomi extends TLOLMessageHandler {

	private String callbackInfo;
	private String orderNumber;

	@Override
	public void handleReceived(LlpMessage msg) {

		orderNumber = String.valueOf(UidManager.instance().orderNum());
		String url = PayManager.instance().getUrl() + ":" + ServerManager.MX4J_PORT;
		callbackInfo = online.getId() + "^" + ServerManager.instance().getSrvId() + "^" + url;
		System.out.println("订单号：" + orderNumber);
		System.out.println("订单数据：" + callbackInfo);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_xiaomi");
			message.write("callbackInfo", callbackInfo);
			message.write("orderNumber", orderNumber);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

	}

}
