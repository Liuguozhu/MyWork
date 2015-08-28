package iyunu.NewTLOL.net.protocol.team;

import iyunu.NewTLOL.manager.TeamManager;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.ArrayList;
import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 请求周围队伍
 * @author LSR
 * @date 2012-8-27
 */
public class AroundTeams extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_aroundTeams");

			List<Integer> list = online.getMapInfo().getBaseMap().allTeamInMap();

			// for (int i = start; i < end; i++) {
			for (int i = 0; i < list.size(); i++) {
				Team team = TeamManager.instance().getTeam(list.get(i));

				if (team != null) {
					// 只显示与自己blood相同的队伍
					if (team.getLeader().getBlood() == online.getBlood()) {
						LlpMessage teamInfo = message.write("teamInfoList");
						teamInfo.write("teamId", team.getId());
						teamInfo.write("teanName", team.getName());

						ArrayList<Role> members = team.getMember();
						for (int j = 0; j < members.size(); j++) {
							LlpMessage teamerInfo = teamInfo.write("teamerInfoList");
							Role member = members.get(j);
							teamerInfo.write("id", member.getId());
							teamerInfo.write("level", member.getLevel());
							teamerInfo.write("nick", member.getNick());
							teamerInfo.write("power", 0);
							teamerInfo.write("position", j);
							teamerInfo.write("figure", member.getFigure());
							teamerInfo.write("vocation", member.getVocation().getName());
						}
					}
				}
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

	}

}
