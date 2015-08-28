package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.enumeration.common.EGang;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.map.EMapType;
import iyunu.NewTLOL.model.map.instance.MapGangInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.map.MapServer;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 进入帮派地图
 * 
 * @author SunHonglei
 * 
 */
public class GangMapEnter extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "进入成功";

		if (online.getGangId() == 0) {
			result = 1;
			reason = "进入失败，帮派不存在";
			return;
		}

		Gang gang = GangManager.instance().getGang(online.getGangId());
		if (gang == null) {
			result = 1;
			reason = "进入失败，帮派不存在";
			return;
		}

		if (online.getMapInfo().getBaseMap().equals(EMapType.gang)) {
			result = 1;
			reason = "进入失败，您已经在帮派地图里";
			return;
		}

		MapGangInfo mapGangInfo = MapManager.instance().getMapGangByGangId(gang.getId());

		int x = 62;
		int y = 32;

		// 判断有没有队伍,
		if (online.getTeam() != null) {
			Team team = online.getTeam();

			if (team.getLeader().getId() != online.getId()) {
				result = 1;
				reason = "您不是队长";
				return;
			}

			for (Role member : team.getMember()) {
				if (member.getGangId() != gang.getId()) {
					result = 1;
					reason = "进入失败，玩家【" + member.getNick() + "】不是您帮派的成员！";
					return;
				}
			}

			MapServer.changeMap(team, x, y, mapGangInfo, online.getMapInfo().getBaseMap());
		} else {
			MapServer.changeMap(online, x, y, mapGangInfo, online.getMapInfo().getBaseMap());
		}
		LogManager.gang(online.getId(), online.getUserId(), online.getGangId(), gang.getId(), gang.getName(), 0, 0, EGang.进入帮派地图.ordinal());
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_gangMapEnter");
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