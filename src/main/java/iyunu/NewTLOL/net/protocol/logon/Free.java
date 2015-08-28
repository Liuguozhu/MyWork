package iyunu.NewTLOL.net.protocol.logon;

import iyunu.NewTLOL.event.uptip.UptipEvent;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 修改属性点
 * 
 * @author SunHonglei
 * 
 */
public class Free extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "属性点修改成功！";

		int strength = msg.readInt("strength");
		int intellect = msg.readInt("intellect");
		int physique = msg.readInt("physique");
		int agility = msg.readInt("agility");

		if (strength < 0 || intellect < 0 || physique < 0 || agility < 0) {
			result = 1;
			reason = "任意属性点不能为负数";
			return;
		}

		int sum = strength + intellect + physique + agility;
		if (sum <= 0) {
			result = 1;
			reason = "任意属性点不能为零或负数";
			return;
		}
		if (online.getFree() < sum || online.getFree() < strength || online.getFree() < intellect || online.getFree() < physique || online.getFree() < agility) {
			result = 1;
			reason = "自由属性点不足";
			return;
		}
		online.setFree(online.getFree() - sum);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, strength);
		map.put(2, intellect);
		map.put(3, physique);
		map.put(4, agility);
		RoleServer.changeFree(online, map);

		SendMessage.sendSttribute(online);
		UptipEvent.属性点.check(online, online.getUptipBoolean(UptipEvent.属性点.getOrdinal()));
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_free");
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