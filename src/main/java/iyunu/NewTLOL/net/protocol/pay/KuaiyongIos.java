package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 快用IOS充值
 * @author LuoSR
 * @date 2014年10月20日
 */
public class KuaiyongIos extends TLOLMessageHandler {

	private String callbackInfo;
	private String orderNumber;
	private String md5Key;

	@Override
	public void handleReceived(LlpMessage msg) {

		orderNumber = String.valueOf(UidManager.instance().orderNum());
		String url = PayManager.instance().getUrl() + ":" + ServerManager.MX4J_PORT;
		callbackInfo = orderNumber + "*" + online.getId() + "*" + ServerManager.instance().getSrvId() + "*" + url;
		md5Key = PayManager.instance().getKuaiyongIosMD5Key();
		System.out.println("订单号：" + orderNumber);
		System.out.println("订单数据：" + callbackInfo);
		System.out.println("MD5密钥：" + md5Key);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_kuaiyongIos");
			message.write("callbackInfo", callbackInfo);
			message.write("orderNumber", orderNumber);
			message.write("appKey", md5Key);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

	}

}
