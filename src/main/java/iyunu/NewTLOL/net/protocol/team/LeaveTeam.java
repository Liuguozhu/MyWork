package iyunu.NewTLOL.net.protocol.team;

import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.TeamManager;
import iyunu.NewTLOL.message.TeamMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 离开队伍
 * @author LSR
 * @date 2012-8-27
 */
public class LeaveTeam extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Team team = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "离开队伍成功";
		team = null;

		// ======检查条件======
		team = online.getTeam();
		if (team == null) {
			result = 1;
			reason = "没有队伍";
			return;
		}

		if (team.size() == 1) { // 如果队伍只有一个人，即解散队伍
			TeamManager.instance().removeTeam(team);
			result = 0;
			reason = "队伍解散";
			MapManager.instance().addTeamQueue(online);
			return;
		}

		// 从队伍中删除
		team.removeMember(online);
		// 更换队长
		Role newLeader = team.getMember().get(0);
		if (newLeader != null) {
			team.changeLeader(newLeader);
		}
		
		MapManager.instance().addTeamQueue(newLeader);
		MapManager.instance().addTeamQueue(online);
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_leaveTeam");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0) {
			List<Role> members = team.getMember();
			String reason = online.getNick() + "离开了队伍";
			for (Role member : members) {
				TeamMessage.teamInform(member, reason);
			}
		}
	}

}
