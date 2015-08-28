package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.json.GangJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.gang.res.GangActivityRes;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 帮派活动列表
 * 
 * @author SunHonglei
 * 
 */
public class GangActivityList extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_gangActivityList");

			Gang gang = GangManager.instance().getGang(online.getGangId());
			if (gang != null) {

				for (GangActivityRes gangActivity : GangJson.instance().getGangActivities()) {
					LlpMessage message = llpMessage.write("gangActivityInfoList");
					message.write("name", gangActivity.getName());
					LogManager.logOut("帮派活动 name=" + gangActivity.getName());
					message.write("award", gangActivity.getAward());
					message.write("icon", gangActivity.getIcon());
					LogManager.logOut("帮派活动 icon=" + gangActivity.getIcon());
					message.write("level", gangActivity.getLevel());
					if (gangActivity.getTime() == 0) {
						message.write("time", "全天");
						if (gang.getLevel() >= gangActivity.getLevel()) {
							message.write("state", 1);
							message.write("levelFlag", 1);
						} else {
							message.write("state", 0);
							message.write("levelFlag", 0);
						}
						message.write("timeFlag", 1);

					} else {

						switch (gangActivity.getIndex()) {
						case 2:
							message.write("time", "19:00:00至19:30:00");
							if (ActivityManager.INVASION_STATE) {
								message.write("timeFlag", 1);
								if (gang.getLevel() >= gangActivity.getLevel()) {
									message.write("levelFlag", 1);
									message.write("state", 1);
								} else {
									message.write("levelFlag", 0);
									message.write("state", 0);
								}

							} else {
								message.write("timeFlag", 0);
								message.write("state", 0);
								if (gang.getLevel() >= gangActivity.getLevel()) {
									message.write("levelFlag", 1);
								} else {
									message.write("levelFlag", 0);
								}
							}
							break;

						default:
							message.write("time", "全天");
							message.write("state", 1);
							message.write("levelFlag", 1);
							message.write("timeFlag", 1);
							break;
						}

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