package iyunu.NewTLOL.net.protocol.blood;

import iyunu.NewTLOL.manager.BloodManager;
import iyunu.NewTLOL.model.blood.Blood;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.List;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class QueryBloodHong extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "查询血战榜红方成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "查询血战榜红方成功";

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_queryBloodHong");
			message.write("result", result);
			if (result == 0) {
				List<Long> hong = BloodManager.instance().getHong();
				BloodManager.instance().sort(hong);
				Map<Long, Blood> map = BloodManager.instance().getBloodMap();
				for (int i = 0; i < (hong.size() > BloodManager.MAX_BLOOD ? BloodManager.MAX_BLOOD : hong.size()); i++) {
					Blood blood = map.get(hong.get(i));
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
