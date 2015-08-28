package iyunu.NewTLOL.net.protocol.team;

import iyunu.NewTLOL.manager.IlllegalWordManager;
import iyunu.NewTLOL.manager.TeamManager;
import iyunu.NewTLOL.message.TeamMessage;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 创建队伍
 * @author LSR
 * @date 2012-8-27
 */
public class CreateTeam extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "创建队伍成功";

		// ======获取参数======
		String teanName = msg.readString("teanName");

		// ======检查条件======
//		if (online.getLevel() < TeamManager.TEAM_LEVEL) {
//			result = 1;
//			reason = "创建队伍失败，" + TeamManager.TEAM_LEVEL + "级开启组队功能";
//			return;
//		}

		if (online.getTeam() != null) {
			result = 1;
			reason = "创建队伍失败，您已经在队伍中了";
			return;
		}

		if (!online.getMapInfo().getBaseMap().isTeam()) {
			result = 1;
			reason = "创建队伍失败，当前地图不允许组队";
			return;
		}

		String str = IlllegalWordManager.instance().existStr(teanName);
		if (str != null) {
			reason = "队伍名包含非法字符[" + str + "]，请您更换！";
			result = 1;
			return;
		}

		Team team = new Team(online, teanName);
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
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_createTeam");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);

		} finally {
			if (message != null) {
				message.destory();
			}
		}
		// ======发送队伍信息======
		if (result == 0) {
			TeamMessage.sendTeamMsg(online, online.getTeam());
		}
	}

}
