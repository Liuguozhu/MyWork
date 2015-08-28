package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.base.http.HttpUtils;
import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 银联充值请求
 * @author LuoSR
 * @date 2014年5月5日
 */
public class Upmp extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private String tn = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "请求成功";

		int orderAmount = msg.readInt("orderAmount") * 100; // 银联的充值为保留到分，且没有隔点（
															// 我们的单位是元）
		String orderNumber = String.valueOf(UidManager.instance().orderNum());
		String url = PayManager.instance().getUrl() + ":" + ServerManager.MX4J_PORT;
		String upmpReqUrl = PayManager.instance().getUpmpReqUrl();
		String backEndUrl = PayManager.instance().getUpmpUrl();

		String reqReserved =  online.getId() + "&" + ServerManager.instance().getSrvId() + "&" + url + "&" + orderAmount + "&" + orderNumber + "&" + backEndUrl;
		LogManager.info(reqReserved);
		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("reqReserved", reqReserved);
		try {
			String responseStr = HttpUtils.urlPost(upmpReqUrl, reqParams);
			if (responseStr.split(":")[1] == null) {
				reason = "交易流水号为空";
				result = 1;
				return;
			}

			tn = responseStr.split(":")[1];
			LogManager.info("tn:" + tn);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_upmp");
			message.write("result", result);
			message.write("reason", reason);
			message.write("tn", tn);

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
