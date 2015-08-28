package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.redis.RedisCenter;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 安智充值
 * @author LuoSR
 * @date 2014年10月30日
 */
public class Anzhi extends TLOLMessageHandler {

	private String callbackInfo;
	private String orderNumber;

	@Override
	public void handleReceived(LlpMessage msg) {

		RedisCenter redisCenter = Spring.instance().getBean("redisCenter", RedisCenter.class);
		orderNumber = redisCenter.getTradeNo();
		String url = PayManager.instance().getUrl() + ":" + ServerManager.MX4J_PORT;

		callbackInfo = online.getId() + "*" + ServerManager.instance().getSrvId() + "*" + url;
		System.out.println("订单号：" + orderNumber);
		System.out.println("订单数据：" + callbackInfo);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_anzhi");
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
