package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 百度IOS充值
 * @author LuoSR
 * @date 2014年10月20日
 */
public class BaiduIos extends TLOLMessageHandler {

	private String callbackInfo;
	private String orderNumber;
	private String appKey;

	@Override
	public void handleReceived(LlpMessage msg) {

		orderNumber = String.valueOf(UidManager.instance().orderNum());
		String url = PayManager.instance().getUrl() + ":" + ServerManager.MX4J_PORT;
		callbackInfo = online.getId() + "*" + ServerManager.instance().getSrvId() + "*" + url;
		appKey = PayManager.instance().getBaiduIosAppKey();
		System.out.println("订单号：" + orderNumber);
		System.out.println("订单数据：" + callbackInfo);
		System.out.println("密钥：" + appKey);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_baiduIos");
			message.write("callbackInfo", callbackInfo);
			message.write("orderNumber", orderNumber);
			message.write("appKey", appKey);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

	}

}
