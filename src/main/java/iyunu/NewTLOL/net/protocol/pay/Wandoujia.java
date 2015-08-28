package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 豌豆荚充值
 * @author LuoSR
 * @date 2014年11月6日
 */
public class Wandoujia extends TLOLMessageHandler {

	private String callbackInfo;

	@Override
	public void handleReceived(LlpMessage msg) {

		String url = PayManager.instance().getUrl() + ":" + ServerManager.MX4J_PORT;
		String orderNumber = String.valueOf(UidManager.instance().orderNum());
		callbackInfo = online.getId() + "*" + ServerManager.instance().getSrvId() + "*" + url + "*" + orderNumber;
		System.out.println("订单数据：" + callbackInfo);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_wandoujia");
			message.write("callbackInfo", callbackInfo);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
