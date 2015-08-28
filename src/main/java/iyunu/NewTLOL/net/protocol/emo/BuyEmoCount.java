package iyunu.NewTLOL.net.protocol.emo;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.manager.EmoMapManager;
import iyunu.NewTLOL.model.vip.EVip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class BuyEmoCount extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "购买恶魔岛次数成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "购买恶魔岛次数成功";
		if (online.getEmoCount() < 0) {
			online.setEmoCount(0);
			result = 1;
			reason = "次数已满，不需购买";
			return;
		}
		if (online.getEmoCount() == 0) {
			result = 1;
			reason = "次数已满，不需购买";
			return;
		}
		if (online.getVip().getLevel() == EVip.common) {
			result = 1;
			reason = "非VIP，不能购买";
			return;
		}
		int coin = EmoMapManager.COIN;
		if (online.getVip().getLevel() == EVip.platinum) {
			coin = coin * 8 / 10;
		}
		if (online.getVip().getLevel() == EVip.diamond) {
			coin = coin * 5 / 10;
		}

		if (!RoleServer.costCoin(online, coin, EGold.buyEmoCount)) {
			result = 1;
			reason = "银两不足";
			return;
		}
		online.setEmoCount(online.getEmoCount() - 1);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_buyEmoCount");
			message.write("result", result);
			message.write("reason", reason);
			message.write("restCount", EmoMapManager.MAX - online.getEmoCount());
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
