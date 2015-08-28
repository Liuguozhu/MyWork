package iyunu.NewTLOL.net.protocol.activity.change;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 兑换银两
 * 
 * @author SunHonglei
 * 
 */
public class ChangeGold extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "兑换银两成功";

		int money = msg.readInt("money");

		if (money <= 0) {
			result = 1;
			reason = "元宝不能小于0";
			return;
		}

		if (!RoleServer.costMoney(online, money, EMoney.changeGold)) {
			result = 1;
			reason = "";
			SendMessage.refreshNoCoin(online, 3);
			return;
		}

		int coin = ActivityJson.instance().getChangeGold(money);
		RoleServer.addGold(online, coin, EGold.changeGold);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_changeGold");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}