package iyunu.NewTLOL.net.protocol.team;

import iyunu.NewTLOL.manager.TeamManager;
import iyunu.NewTLOL.message.TeamMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 玩家选择同意或是拒绝加入队伍
 * @author LSR
 * @date 2012-8-27
 */
public class ChooseToTeam extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private int chooseResult;

	private Team team = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "选择成功";
		chooseResult = 1;

		// ======获取参数======
		int teamId = msg.readInt("teamId"); // 队伍编号
		chooseResult = msg.readInt("chooseResult"); // 选择结果(0,同意;1,取消)

		// 不管选择同意还是拒绝，都删除掉
		online.getInviteList().clear();

		// ======检查条件======
		team = TeamManager.instance().getTeam(teamId);

		if (chooseResult == 1) {
			result = 0;
			reason = "已拒绝";
			return;
		}

		if (team == null) {
			result = 1;
			reason = "队伍不存在或已解散";
			return;
		}

		if (team.size() >= 3) {
			result = 1;
			reason = "队伍成员已满";
			return;
		}

		if (!online.getMapInfo().getMapAgent().isNearBy(team.getLeader().getId())) {
//		if (team.getLeader().getMapInfo().getMapId() != online.getMapInfo().getMapId()) {
			result = 1;
			reason = "队伍不在当前地图";
			return;
		}

		// if (team.getLeader().isBattle()) {
		// result = 1;
		// reason = "队伍正在战斗中";
		// return;
		// }
		// if (team.getLeader().isPrBattle()) {
		// result = 1;
		// reason = "队伍正在战斗中";
		// return;
		// }
		for (Role role : team.getMember()) {
			if (online.getBlood() != role.getBlood()) {
				result = 1;
				reason = "血战与非血战成员，不能加入到同一队伍！";
				return;
			}
		}
		// ======设置角色队伍信息======
		online.setTeam(team);
		// ======将角色添加到队伍中======
		team.addMember(online);

	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_chooseToTeam");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);

		} finally {
			if (message != null) {
				message.destory();
			}
		}
		if (result == 0 && chooseResult == 0) {
			TeamMessage.sendTeamAllMsg(online.getTeam());

			List<Role> member = online.getTeam().getMember();
			String reason = online.getNick() + "加入了队伍";
			for (Role role : member) {
				if (role.getId() != online.getId()) {
					TeamMessage.teamInform(role, reason);
				}
			}

//			TeamMessage.refreshLeaderSite(online, online.getTeam());

			reason = "成功加入队伍！";
			TeamMessage.teamInform(online, reason);
		}

		if (result == 0 && chooseResult == 1) {
			String reason = online.getNick() + "拒绝加入队伍";
			if (team != null) {
				TeamMessage.teamInform(team.getLeader(), reason);
			}
		}
	}
}
