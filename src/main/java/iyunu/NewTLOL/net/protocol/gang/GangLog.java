package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.gang.GangLogInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 帮派日志
 * 
 * @author SunHonglei
 * 
 */
public class GangLog extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_gangLog");

			Gang gang = GangManager.instance().getGang(online.getGangId());
			if (gang != null) {
				for (GangLogInfo gangLogInfo : gang.getGangLog()) {
					llpMessage.write("logList", gangLogInfo.getName() + " " + gangLogInfo.getLog());
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