package iyunu.NewTLOL.net.protocol.blood;

import iyunu.NewTLOL.manager.BloodManager;
import iyunu.NewTLOL.model.blood.Blood;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.List;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class QueryBloodLan extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "查询血战榜蓝方成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "查询血战榜蓝方成功";

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_queryBloodLan");
			message.write("result", result);
			if (result == 0) {
				List<Long> lan = BloodManager.instance().getLan();
				BloodManager.instance().sort(lan);
				Map<Long, Blood> map = BloodManager.instance().getBloodMap();
				for (int i = 0; i < (lan.size() > BloodManager.MAX_BLOOD ? BloodManager.MAX_BLOOD : lan.size()); i++) {
					Blood blood = map.get(lan.get(i));
					LlpMessage msg = message.write("bloodInfos");
					msg.write("id", blood.getId());
					msg.write("level", blood.getLevel());
					msg.write("nick", blood.getNick());
					msg.write("mark", blood.getMark());
					msg.write("vocation", blood.getVocation().ordinal());
					msg.write("blood", blood.getBlood());
					msg.write("multiKill", blood.getMultiKill());
					msg.write("killNum", blood.getKillNum());
					msg.write("dead", blood.getDead());
					msg.write("hkill", blood.getHkill());
				}
			}
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
