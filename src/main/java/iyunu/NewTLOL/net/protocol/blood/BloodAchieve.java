package iyunu.NewTLOL.net.protocol.blood;

import iyunu.NewTLOL.manager.BloodManager;
import iyunu.NewTLOL.model.blood.Blood;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class BloodAchieve extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "血战成就成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "血战成就成功";
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_bloodAchieve");
			message.write("result", result);
			if (result == 0) {
				Blood sb = BloodManager.instance().bloodMap.get(BloodManager.instance().shouSha);
				if (sb != null) {
					LlpMessage msg1 = message.write("shouSha");
					msg1.write("id", sb.getId());
					msg1.write("level", sb.getLevel());
					msg1.write("nick", sb.getNick());
					msg1.write("mark", sb.getMark());
					msg1.write("vocation", sb.getVocation().ordinal());
					msg1.write("blood", sb.getBlood());
					msg1.write("multiKill", sb.getMultiKill());
					msg1.write("killNum", sb.getKillNum());
					msg1.write("dead", sb.getDead());
					msg1.write("hkill", sb.getHkill());
				}
				Blood sbm = BloodManager.instance().bloodMap.get(BloodManager.instance().shouShaMonster);
				if (sbm != null) {
					LlpMessage msg2 = message.write("shouShaMonster");
					msg2.write("id", sbm.getId());
					msg2.write("level", sbm.getLevel());
					msg2.write("nick", sbm.getNick());
					msg2.write("mark", sbm.getMark());
					msg2.write("vocation", sbm.getVocation().ordinal());
					msg2.write("blood", sbm.getBlood());
					msg2.write("multiKill", sbm.getMultiKill());
					msg2.write("killNum", sbm.getKillNum());
					msg2.write("dead", sbm.getDead());
					msg2.write("hkill", sbm.getHkill());
				}
				Blood mtk = BloodManager.instance().bloodMap.get(BloodManager.instance().hKill);
				if (mtk != null) {
					LlpMessage msg3 = message.write("multiKill");
					msg3.write("id", mtk.getId());
					msg3.write("level", mtk.getLevel());
					msg3.write("nick", mtk.getNick());
					msg3.write("mark", mtk.getMark());
					msg3.write("vocation", mtk.getVocation().ordinal());
					msg3.write("blood", mtk.getBlood());
					msg3.write("multiKill", mtk.getMultiKill());
					msg3.write("killNum", mtk.getKillNum());
					msg3.write("dead", mtk.getDead());
					msg3.write("hkill", mtk.getHkill());
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
