package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.base.http.HttpUtils;
import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.model.pay.PayAnzhiResponse;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Base64;
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
public class AnzhiQuery extends TLOLMessageHandler {

	private PayAnzhiResponse rsp = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		String tradenum = msg.readString("tradenum");

		String appkey = PayManager.instance().getAnzhiAppId();
		String anzhiAppSecret = PayManager.instance().getAnzhiAppSecret();
		String anzhiQueryUrl = PayManager.instance().getAnzhiQueryUrl();
		String mintradetime = "";
		String maxtradetime = "";
		String time = String.valueOf(System.currentTimeMillis());

		try {

			String sign = Base64.encodeToString(appkey + tradenum + mintradetime + maxtradetime + anzhiAppSecret);
			System.out.println("[签名结果]" + sign);

			Map<String, String> params = new HashMap<String, String>();
			params.put("appkey", appkey);
			params.put("tradenum", tradenum);
			params.put("mintradetime", mintradetime);
			params.put("maxtradetime", maxtradetime);
			params.put("sign", sign);
			params.put("time", time);

			String result = HttpUtils.urlPost(anzhiQueryUrl, params);
			System.out.println("[响应结果]" + result);// 结果也是一个json格式字符串
			// 反序列化
			rsp = (PayAnzhiResponse) Http.decodeJson(result, PayAnzhiResponse.class);
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
			message = LlpJava.instance().getMessage("s_anzhiQuery");
			message.write("sc", rsp.getSc());
			message.write("st", rsp.getSt());
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
