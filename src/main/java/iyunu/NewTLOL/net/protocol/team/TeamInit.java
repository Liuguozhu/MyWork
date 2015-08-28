package iyunu.NewTLOL.net.protocol.team;

import iyunu.NewTLOL.message.TeamMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 请求队伍信息
 * @author LSR
 * @date 2012-8-27
 */
public class TeamInit extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "";

//		if (online.getLevel() < TeamManager.TEAM_LEVEL) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
//			result = 1;
//			reason = TeamManager.TEAM_LEVEL + "级开启组队功能";
//			return;
//		}

		if (!online.getMapInfo().getBaseMap().isTeam()) {
			result = 1;
			reason = "当前地图不允许组队";
			return;
		}

	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;

		try {
			message = LlpJava.instance().getMessage("s_teamInit");
			message.write("result", result);
			message.write("reason", reason);
			message.write("state", (online.getTeam() == null ? 2 : 1));
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		// ======发送队伍信息======
		if (online.getTeam() != null) {
			TeamMessage.sendTeamMsg(online, online.getTeam());
		}
	}

}
