package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 支付宝充值
 * @author LuoSR
 * @date 2014年3月10日
 */
public class Alipay extends TLOLMessageHandler {

	private String out_trade_no;
	private String body;

	@Override
	public void handleReceived(LlpMessage msg) {

		out_trade_no = String.valueOf(UidManager.instance().orderNum());
		LogManager.logOut("订单号=============== " + out_trade_no);
		String url = PayManager.instance().getUrl() + ":" + ServerManager.MX4J_PORT;
		body = online.getId() + "&" + ServerManager.instance().getSrvId()  + "&" + url;
		LogManager.logOut("body=============== " + body);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_alipay");
			message.write("service", PayManager.instance().getService());
			message.write("partner", PayManager.instance().getPartner());
			message.write("privateKey", PayManager.instance().getPrivateKey());
			message.write("_input_charset", PayManager.instance().getCharset());
			message.write("sign_type", PayManager.instance().getSignType());
			message.write("sign", "");
			message.write("notify_url", PayManager.instance().getAlipayUrl());
			message.write("app_id", "");
			message.write("appenv", "");
			message.write("out_trade_no", out_trade_no);
			message.write("subject", PayManager.instance().getSubject());
			message.write("payment_type", PayManager.instance().getPaymentType());
			message.write("seller_id", PayManager.instance().getSellerId());
			message.write("total_fee", "");
			message.write("body", body);
			message.write("it_b_pay", "");
			message.write("show_url", "");
			message.write("extern_token", "");

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

	}

}
