package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 应用汇
 * @author LuoSR
 * @date 2014年11月7日
 */
public class Yingyonghui extends TLOLMessageHandler {

	private String callbackInfo;
	private String orderNumber;
	private String appKey;
	private String yingyonghuiUrl;

	@Override
	public void handleReceived(LlpMessage msg) {

		String url = PayManager.instance().getUrl() + ":" + ServerManager.MX4J_PORT;
		appKey = PayManager.instance().getYingyonghuiAppKey();
		yingyonghuiUrl = PayManager.instance().getYingyonghuiUrl();
		orderNumber = String.valueOf(UidManager.instance().orderNum());
		callbackInfo = online.getId() + "*" + ServerManager.instance().getSrvId() + "*" + url;
		System.out.println("订单数据：" + callbackInfo);
		System.out.println("订单号：" + orderNumber);
		System.out.println("应用私钥：" + appKey);
		System.out.println("回调地址：" + yingyonghuiUrl);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_yingyonghui");
			message.write("callbackInfo", callbackInfo);
			message.write("orderNumber", orderNumber);
			message.write("yingyonghuiAppKey", appKey);
			message.write("yingyonghuiUrl", yingyonghuiUrl);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
