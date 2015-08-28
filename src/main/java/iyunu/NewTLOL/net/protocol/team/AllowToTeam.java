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
 * @function 队长选择同意或是拒绝申请进队
 * @author LSR
 * @date 2012-8-27
 */
public class AllowToTeam extends TLOLMessageHandler {

	private int chooseResult;
	private int result = 0;
	private String reason = "";
	private Role teamer = null; // 队员

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "允许成功";
		chooseResult = 1;

		// ======获取参数======
		long teamerId = msg.readLong("teamerId");
		chooseResult = msg.readInt("chooseResult"); // 选择结果(0,同意;1,取消)
		teamer = ServerManager.instance().getOnlinePlayer(teamerId);
		// ======检查条件======
		Team team = online.getTeam();
		if (team == null) {
			result = 1;
			reason = "队伍不存在或已解散";
			return;
		}

		// 不管选择同意还是拒绝，都删除掉
		team.getApplyList().clear();

		if (team.getLeader().getId() != online.getId()) {
			result = 1;
			reason = "您不是队长";
			return;
		}

		if (team.size() >= 3) {
			result = 1;
			reason = "队伍成员已满";
			return;
		}

		if (chooseResult == 1) {
			result = 0;
			reason = "已拒绝";
			return;
		}

		if (!ServerManager.instance().isOnline(teamerId)) {
			result = 1;
			reason = "该玩家不在线";
			return;
		}
		if (!online.getMapInfo().getMapAgent().isNearBy(teamer.getId())) {
//		if (teamer.getMapInfo().getMapId() != online.getMapInfo().getMapId()) {
			result = 1;
			reason = "对方不在当前地图";
			return;
		}

		if (teamer.isBattle()) {
			result = 1;
			reason = "对方在战斗中";
			return;
		}

		if (teamer.isPrBattle()) {
			result = 1;
			reason = "对方在战斗中";
			return;
		}

		if (teamer.getTeam() != null) {
			result = 1;
			reason = "对方已有队伍";
			return;
		}

		for (Role role : team.getMember()) {
			if (teamer.getBlood() != role.getBlood()) {
				result = 1;
				reason = "血战与非血战成员，不能加入到同一队伍！";
				return;
			}
		}

		// ======设置角色队伍信息======
		teamer.setTeam(team);
		// ======将角色添加到队伍中======
		team.addMember(teamer);
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_allowToTeam");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);

		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
		if (result == 0 && chooseResult == 0) {

			TeamMessage.sendTeamAllMsg(online.getTeam());

			List<Role> member = online.getTeam().getMember();
			String reason = teamer.getNick() + "加入了队伍";
			for (Role role : member) {
				if (role.getId() != teamer.getId()) {
					TeamMessage.teamInform(role, reason);
				} else {
//					TeamMessage.refreshLeaderSite(teamer, online.getTeam());
				}
			}

			reason = "成功 加入了队伍";
			TeamMessage.teamInform(teamer, reason);
		}

		if (result == 0 && chooseResult == 1) {
			String reason = online.getNick() + "拒绝您加入队伍";
			TeamMessage.teamInform(teamer, reason);
		}
	}
}
