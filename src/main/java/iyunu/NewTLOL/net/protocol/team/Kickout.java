package iyunu.NewTLOL.net.protocol.team;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.TeamMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 请离队伍
 * @author LSR
 * @date 2012-8-27
 */
public class Kickout extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Team team = null;
	private Role teamer = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "踢人成功";
		team = null;
		teamer = null;

		// ======获取参数======
		long teamerId = msg.readLong("teamerId");

		// ======检查条件======
		team = online.getTeam();
		if (team == null) {
			result = 1;
			reason = "没有队伍";
			return;
		}

		if (online.getId() != team.getLeader().getId()) {
			result = 1;
			reason = "您不是队长";
			return;
		}
		teamer = ServerManager.instance().getOnlinePlayer(teamerId);
		team.removeMember(teamer);// 从队伍中删除
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_kickout");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);

		} finally {
			if (message != null) {
				message.destory();
			}
		}
		if (result == 0) {
			TeamMessage.sendTeamState(teamer, 2);
			TeamMessage.teamInform(teamer, "您被踢出了队伍");

			List<Role> member = team.getMember();
			reason = teamer.getNick() + "被踢出了队伍";
			for (Role role : member) {
				TeamMessage.teamInform(role, reason);
			}
		}
	}
}
