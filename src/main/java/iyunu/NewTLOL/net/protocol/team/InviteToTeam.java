package iyunu.NewTLOL.net.protocol.team;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.TeamManager;
import iyunu.NewTLOL.message.TeamMessage;
import iyunu.NewTLOL.model.role.ERoleFightState;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 邀请入队
 * @author LSR
 * @date 2012-8-27
 */
public class InviteToTeam extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Role teamer = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "邀请成功";
		teamer = null;
		// ======获取参数======
		long teamerId = msg.readLong("teamerId");

		if (!online.getMapInfo().getBaseMap().isTeam()) {
			result = 1;
			reason = "当前地图不允许组队";
			return;
		}

		// ======检查条件======
		Team team = online.getTeam();

//		if (online.getLevel() < TeamManager.TEAM_LEVEL) {
//			result = 1;
//			reason = TeamManager.TEAM_LEVEL + "级开启组队功能";
//			return;
//		}

		if (!ServerManager.instance().isOnline(teamerId)) {
			result = 1;
			reason = "该玩家不在线";
			return;
		}

		teamer = ServerManager.instance().getOnlinePlayer(teamerId);
		// ======检查条件======
//		if (teamer.getLevel() < TeamManager.TEAM_LEVEL) {
//			result = 1;
//			reason = "对方等级不足" + TeamManager.TEAM_LEVEL + "级";
//			return;
//		}

		if (teamer.getTeam() != null) {
			result = 1;
			reason = "该玩家已有队伍";
			return;
		}

		if (!online.getMapInfo().getMapAgent().isNearBy(teamer.getId())) {
			result = 1;
			reason = "玩家不在当前地图";
			return;
		}

		if (!ERoleFightState.same(online.getFightState(), teamer.getFightState())) {
			result = 1;
			reason = "您目前不可以与此玩家组队";
			return;
		}

		if (teamer.isCloseTeam()) {
			result = 1;
			reason = "对方已屏蔽组队";
			return;
		}

		if (team != null) {
			if (team.getLeader().getId() != online.getId()) {
				result = 1;
				reason = "您不是队长";
				return;
			}
		} else {

			String teanName = online.getNick() + "的队伍";
			team = new Team(online, teanName);
			int teamId = team.getId();
			boolean flag = TeamManager.instance().saveTeam(teamId, team);
			if (!flag) {
				result = 1;
				reason = "创建队伍失败";
				return;
			}

			online.setTeam(team);
			// ======将创建的队伍加入到地图中======
			online.getMapInfo().getBaseMap().addTeam(teamId);
			// ======发送队伍信息======
			TeamMessage.sendTeamMsg(online, online.getTeam());
		}

		// if (teamer.getInviteList().contains(team.getId())) {
		// result = 1;
		// reason = "已在邀请列表";
		// return;
		// }

		for (Role role : team.getMember()) {
			if (teamer.getBlood() != role.getBlood()) {
				result = 1;
				reason = "血战与非血战成员，不能加入到同一队伍！";
				return;
			}
		}

		teamer.getInviteList().add(team.getId());
		if (teamer.getInviteList().size() > 8) {
			result = 1;
			reason = "邀请该玩家的人数过多！";
			return;
		}

	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		LlpMessage sendMessage = null;
		try {
			message = LlpJava.instance().getMessage("s_inviteToTeam");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);

			if (result == 0) {
				sendMessage = LlpJava.instance().getMessage("s_sendToTeam");
				int num = 0;
				for (Integer teamId : teamer.getInviteList()) {
					Team team = TeamManager.instance().getTeam(teamId);
					if (team != null) {
						LlpMessage teamMessageInfo = sendMessage.write("teamMessageInfoList");
						teamMessageInfo.write("teamId", teamId);
						teamMessageInfo.write("headerId", team.getLeader().getId());
						teamMessageInfo.write("teamerId", teamer.getId()); // 可删除
						teamMessageInfo.write("describe", team.getLeader().getNick() + "邀请您加入队伍");
						num++;
					}

					if (num > 3) {
						break;
					}
				}

				if (teamer != null && teamer.getChannel() != null) {
					teamer.getChannel().write(sendMessage);
				}

			}
		} finally {
			if (message != null) {
				message.destory();
			}
			if (sendMessage != null) {
				sendMessage.destory();
			}
		}
	}
}
