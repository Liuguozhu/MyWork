package iyunu.NewTLOL.net.protocol.gang.fight;

import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.model.gang.FightApplyInfo;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 离开帮战地图
 * @author LuoSR
 * @date 2014年7月1日
 */
public class QuitGangFightMap extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "退出帮战地图成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		Team team = online.getTeam();
		int num = 1;
		if (team != null) {
			if (team.getLeader().getId() != online.getId()) {
				result = 1;
				reason = "您不是队长 ";
				return;
			}
			num = team.getMemberSize();
			GangFightManager.quitGangFight(online, online.getTeam());
		} else {
			GangFightManager.quitGangFight(online);
		}

		FightApplyInfo fightApplyInfo = GangFightManager.参赛列表.get(online.getGangId());
		if (fightApplyInfo != null) {
			fightApplyInfo.out(num);
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_quitGangFightMap");
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
