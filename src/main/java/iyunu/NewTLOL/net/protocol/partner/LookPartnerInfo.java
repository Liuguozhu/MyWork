package iyunu.NewTLOL.net.protocol.partner;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.Iterator;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class LookPartnerInfo extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Role role;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "查看";

		long roleId = msg.readLong("roleId");

		if (!ServerManager.instance().isOnline(roleId)) {
			result = 1;
			reason = "该玩家不在线！";
			return;
		}

		role = ServerManager.instance().getOnlinePlayer(roleId);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_lookPartnerInfo");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			if (result == 0) {

				Iterator<Entry<Integer, Long>> it = role.getPartnerFight().entrySet().iterator();
				while (it.hasNext()) {
					Entry<Integer, Long> entry = it.next();
					LlpMessage message = llpMessage.write("lookPartnerList");
					message.write("index", entry.getKey());
					Partner partner = role.findPartner(entry.getValue());
					if (partner != null) {
						LlpMessage msg = message.write("partner");
						PartnerMessage.packagePartner(msg, partner);
					}
				}
			}
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}
