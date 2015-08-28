package iyunu.NewTLOL.net.protocol.partner;

import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.partner.PartnerServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 设置出战伙伴
 * 
 * @author SunHonglei
 * 
 */
public class FightPartner extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private long partnerId;

	@Override
	public void handleReceived(LlpMessage msg) {

		// ======重置数据======
		result = 0;
		reason = "设置成功";

		// ======获取参数======
		int index = msg.readInt("index");
		partnerId = msg.readLong("partnerId");
		// ======检查条件======
		Partner partner = online.findPartner(partnerId);
		if (partner == null) {
			result = 1;
			reason = "设置出战伙伴失败，伙伴不存在！";
			return;
		}

		if (partner.getFightLevel() > online.getLevel()) {
			result = 1;
			reason = "出战" + partner.getSteps() + "阶伙伴需要" + partner.getFightLevel() + "级！";
			return;
		}

		if (partner.getLevel() > online.getLevel() + 5) {
			result = 1;
			reason = "出战伙伴等级不能超过角色等级5级！";
			return;
		}

		online.getPartnerFight().put(index, partnerId);

		PartnerServer.autoRecoverHp(online);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_fightPartner");
			message.write("result", result);
			message.write("reason", reason);
			if (result == 0) {
				PartnerMessage.refreshFightPartner(online);
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
