package iyunu.NewTLOL.net.protocol.raids;

import iyunu.NewTLOL.json.MapJson;
import iyunu.NewTLOL.message.RaidsMessage;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.raids.instance.RaidsInfo;
import iyunu.NewTLOL.model.raids.instance.RaidsTeamInfo;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.map.MapServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 退出副本
 * 
 * @author SunHonglei
 * 
 */
public class RaidsComeOut extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private RaidsInfo raidsInfo;
	private BaseMap baseMap;
	private RaidsTeamInfo raidsTeamInfo;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "退出副本成功";

		// ======获取数据======
		raidsTeamInfo = online.getRaidsTeamInfo();
		raidsInfo = online.getRaidsTeamInfo().getRaidsInfo();
		int mapId = raidsInfo.getOutMap();
		baseMap = MapJson.instance().getMapById(mapId);
		if (baseMap == null) {
			result = 1;
			reason = "地图不存在";
			return;
		}

		if (online.getTeam() != null) {
			Team team = online.getTeam();
			if (team.getLeader().getId() != online.getId()) {
				result = 1;
				reason = "请先离开队伍";
				return;
			}

		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_raids_comeOut");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0) {
			int x = raidsInfo.getOutX();
			int y = raidsInfo.getOutY();
			// 判断有没有队伍,
			if (online.getTeam() != null) {
				Team team = online.getTeam();
				MapServer.changeMap(team, x, y, baseMap, online.getMapInfo().getBaseMap());
			} else {
				MapServer.changeMap(online, x, y, baseMap, online.getMapInfo().getBaseMap());
				if (online.getRaidsTeamInfo() != null) {
					if (online.getMapInfo().isMapRaids()) {
						RaidsMessage.refreshMiniMap(raidsTeamInfo); // 刷新副本小地图
					}
				}
			}
		}
	}
}