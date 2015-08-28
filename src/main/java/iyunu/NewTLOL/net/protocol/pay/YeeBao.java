package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.base.http.HttpUtils;
import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Translate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 易宝充值请求
 * @author LuoSR
 * @date 2014年5月9日
 */
public class YeeBao extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "请求成功,充值结果请查看邮件";

		String cardNo = msg.readString("cardNo");
		String cardPwd = msg.readString("cardPwd");
		String frpId = msg.readString("frpId");
		String amt = msg.readString("amt");

		String yeeBaoReqUrl = PayManager.instance().getYeeBaoReqUrl();
		String yeeBaoUrl = PayManager.instance().getYeeBaoUrl();
		String P_MP = online.getId() + "&" + PayManager.instance().getUrl() + ":" + ServerManager.MX4J_PORT;
		String game = PayManager.instance().getGame();
		String orderNumber = String.valueOf(UidManager.instance().orderNum());

		Map<String, String> reqParams = new HashMap<String, String>();
		amt = "10000";
		reqParams.put("P_Url", yeeBaoUrl);
		reqParams.put("platform", online.getPlatform());
		reqParams.put("P_Amt", amt);
		reqParams.put("P_MP", P_MP);

		if (frpId.equals("SZX")) {
			reqParams.put("P_CardAmt", "10");
		} else if (frpId.equals("UNICOM")) {
			reqParams.put("P_CardAmt", "20");
		} else if (frpId.equals("TELECOM")) {
			reqParams.put("P_CardAmt", "50");
		} else if (frpId.equals("SNDACARD")) {
			reqParams.put("P_CardAmt", "5");
		} else if (frpId.equals("JUNNET")) {
			reqParams.put("P_CardAmt", "1");
		}

		reqParams.put("P_CardNo", cardNo);
		reqParams.put("P_CardPwd", cardPwd);
		reqParams.put("P_FrpId", frpId);
		reqParams.put("userid", online.getUserId());
		reqParams.put("game", game);
		reqParams.put("roleid", String.valueOf(online.getId()));
		reqParams.put("srvid", String.valueOf(ServerManager.instance().getSrvId()));
		reqParams.put("orderNumber", orderNumber);

		try {
			String responseStr = HttpUtils.urlPost(yeeBaoReqUrl, reqParams);
			if (Translate.stringToInt(responseStr.split(";")[0].split(":")[1]) != 1) {
				reason = "提交失败";
				result = 1;
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_yeeBao");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

	}

}
