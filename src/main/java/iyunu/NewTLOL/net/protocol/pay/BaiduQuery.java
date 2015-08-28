package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.base.http.HttpUtils;
import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.model.pay.PayBaiduResponse;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 百度充值查询
 * @author LuoSR
 * @date 2014年10月20日
 */
public class BaiduQuery extends TLOLMessageHandler {

	private PayBaiduResponse rsp = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		String orderId = msg.readString("orderId");

		String appId = PayManager.instance().getBaiduAppId();
		String productKey = PayManager.instance().getProductKey();
		String productSecret = PayManager.instance().getProductSecret();
		String baiduQueryUrl = PayManager.instance().getBaiduQueryUrl();

		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appId);
		params.put("appkey", productKey);
		params.put("orderid", orderId);

		String signSource = appId + productKey + orderId + productSecret;
		String sign = Http.getMD5Str(signSource).toLowerCase();// MD5加密签名

		System.out.println("[签名原文]" + signSource);
		System.out.println("[签名结果]" + sign);
		params.put("clientsecret", sign);

		try {
			String result = HttpUtils.urlPost(baiduQueryUrl, params);
			System.out.println("[响应结果]" + result);// 结果也是一个json格式字符串
			// 反序列化
			rsp = (PayBaiduResponse) Http.decodeJson(result, PayBaiduResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_baiduQuery");
			message.write("errorCode", rsp.getError_code());
			message.write("errorMsg", rsp.getError_msg());
			message.write("result", rsp.getResult());
			message.write("amount", rsp.getAmount());
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
