package iyunu.NewTLOL.net.protocol.gang.fight;

import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Time;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 帮派战初始化
 * @author LuoSR
 * @date 2014年6月23日
 */
public class GangFightInit extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_gangFightInit");
			message.write("type", 0);

			int time = (int) ((GangFightManager.FINISH_TIME - System.currentTimeMillis()) / Time.MILLISECOND);
			message.write("state", GangFightManager.STATE); // 0.不显示，1.报名，2.距离开始，3.入场，4.战斗，5.距离报名
			message.write("time", time);

			message.write("champion", GangFightManager.CHAMPION);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
