package iyunu.NewTLOL.net.protocol.team;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.TeamMessage;
import iyunu.NewTLOL.model.role.ERoleFightState;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 申请加入队伍
 * @author LSR
 * @date 2012-8-27
 */
public class AddTeam extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Role leader = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "申请成功";
		leader = null;

		// ======获取参数======
		long headerId = msg.readLong("headerId");
		// ======检查条件======
//		if (online.getLevel() < TeamManager.TEAM_LEVEL) {
//			result = 1;
//			reason = "您的等级不足" + TeamManager.TEAM_LEVEL + "级";
//			return;
//		}
		// ======检查条件======
		if (online.getTeam() != null) {
			result = 1;
			reason = "您已有队伍";
			return;
		}

		if (!online.getMapInfo().getBaseMap().isTeam()) {
			result = 1;
			reason = "当前地图不允许组队";
			return;
		}

		if (!ServerManager.instance().isOnline(headerId)) {
			result = 1;
			reason = "队长离线，队伍已解散";
			return;
		}

		leader = ServerManager.instance().getOnlinePlayer(headerId); // 获取队长角色对象
		Team team = leader.getTeam();
		if (team == null) {
			result = 1;
			reason = "队伍已解散";
			return;
		}

		leader = leader.getTeam().getLeader();
		if (!online.getMapInfo().getMapAgent().isNearBy(leader.getId())) {
			// if (leader.getMapInfo().getMapId() !=
			// online.getMapInfo().getMapId()) {
			result = 1;
			reason = "队伍不在当前地图";
			return;
		}

		if (team.size() >= Team.MAX_MEMBER) {
			result = 1;
			reason = "队伍成员已满";
			return;
		}

		if (leader.isCloseTeam()) {
			result = 1;
			reason = "对方已屏蔽组队";
			return;
		}

		team.apply(online.getId());
		// if (team.apply(online.getId())) {
		// result = 1;
		// reason = "您已经在申请列表中，请等待队长同意";
		// return;
		// }
		for (Role role : team.getMember()) {
			if (online.getBlood() != role.getBlood()) {
				result = 1;
				reason = "血战与非血战成员，不能加入到同一队伍！";
				return;
			}
		}
		if (!ERoleFightState.same(online.getFightState(), leader.getFightState())) {
			result = 1;
			reason = "您目前不可以与此玩家组队";
			return;
		}

	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		LlpMessage sendMessage = null;
		try {
			message = LlpJava.instance().getMessage("s_addTeam");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);

			if (result == 0) {
				sendMessage = LlpJava.instance().getMessage("s_sendAddTeam");

				Team team = leader.getTeam();
				int num = 0;
				for (Long roleId : team.getApplyList()) {
					LlpMessage teamMessageInfo = sendMessage.write("teamMessageInfoList");
					Role role = ServerManager.instance().getOnlinePlayer(roleId);
					if (role != null) {
						teamMessageInfo.write("teamId", team.getId()); // 可以删除
						teamMessageInfo.write("headerId", leader.getId()); // 可以删除
						teamMessageInfo.write("teamerId", roleId);
						teamMessageInfo.write("describe", role.getNick() + "申请加入您的队伍");
						num++;
					}
					if (num > 3) {
						break;
					}
				}
				if (leader != null && leader.getChannel() != null) {
					leader.getChannel().write(sendMessage);
				}

				String reason = "您的组队请求已发出，请等待！";
				TeamMessage.teamInform(online, reason);

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
