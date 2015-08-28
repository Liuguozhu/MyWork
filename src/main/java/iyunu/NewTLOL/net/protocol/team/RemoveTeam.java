package iyunu.NewTLOL.net.protocol.team;

import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.TeamManager;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 解散队伍
 * @author LSR
 * @date 2012-8-27
 */
public class RemoveTeam extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "解散队伍成功";

		// ======检查条件======
		Team team = online.getTeam();
		if (team == null) {
			result = 1;
			reason = "没有队伍";
			return;
		}

		if (team.getLeader().getId() != online.getId()) {
			result = 1;
			reason = "只有队长才能解散队伍";
			return;
		}

		// ======解散队伍======
		TeamManager.instance().removeTeam(team);
		MapManager.instance().addTeamQueue(online);
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_removeTeam");
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
