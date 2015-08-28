package iyunu.NewTLOL.net.protocol.blood;

import iyunu.NewTLOL.manager.BloodManager;
import iyunu.NewTLOL.model.blood.Blood;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class BloodSelf extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_bloodSelf");

			Blood self = BloodManager.instance().bloodMap.get(online.getId());
			if (self != null) {
				LlpMessage msg1 = message.write("self");
				msg1.write("id", self.getId());
				msg1.write("level", self.getLevel());
				msg1.write("nick", self.getNick());
				msg1.write("mark", self.getMark());
				msg1.write("vocation", self.getVocation().ordinal());
				msg1.write("blood", self.getBlood());
				msg1.write("multiKill", self.getMultiKill());
				msg1.write("killNum", self.getKillNum());
				msg1.write("dead", self.getDead());

			}

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
