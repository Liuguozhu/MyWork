package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.base.http.HttpUtils;
import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.model.pay.PayXiaomiResponse;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.HmacSHA1Encryption;
import iyunu.NewTLOL.util.Http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 小米充值查询
 * @author LuoSR
 * @date 2014年10月20日
 */
public class XiaomiQuery extends TLOLMessageHandler {

	private PayXiaomiResponse rsp = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		String cpOrderId = msg.readString("cpOrderId");
		String userId = msg.readString("userId");
		
		String xiaomiAppId = PayManager.instance().getXiaomiAppId();
		String xiaomiAppSecret = PayManager.instance().getXiaomiAppSecret();
		String xiaomiQueryUrl = PayManager.instance().getXiaomiQueryUrl();

		try {
			
			String signSource = "appId=" + xiaomiAppId + "&cpOrderId=" + cpOrderId + "&uid=" + userId;// 组装签名原文
			String sign = HmacSHA1Encryption.HmacSHA1Encrypt(signSource, xiaomiAppSecret);
			System.out.println("[签名原文]" + signSource);
			System.out.println("[签名结果]" + sign);

			Map<String, String> params = new HashMap<String, String>();
			params.put("appId", xiaomiAppId);
			params.put("cpOrderId", cpOrderId);
			params.put("uid", userId);
			params.put("signature", sign);
			
			String result = HttpUtils.urlPost(xiaomiQueryUrl, params);
			System.out.println("[响应结果]" + result);// 结果也是一个json格式字符串
			// 反序列化
			rsp = (PayXiaomiResponse) Http.decodeJson(result, PayXiaomiResponse.class);
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
			message.write("errorCode", rsp.getErrcode());
			message.write("errorMsg", rsp.getErrMsg());
			message.write("orderStatus", rsp.getOrderStatus());
			message.write("payFee", rsp.getPayFee());
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
