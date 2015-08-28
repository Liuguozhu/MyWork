package iyunu.NewTLOL.net.protocol.raids;

import iyunu.NewTLOL.message.RaidsMessage;
import iyunu.NewTLOL.model.map.EOrientation;
import iyunu.NewTLOL.model.map.instance.MapRaidsInfo;
import iyunu.NewTLOL.model.raids.instance.RaidsTeamInfo;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.map.MapServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 副本下层
 * 
 * @author SunHonglei
 * 
 */
public class RaidsNext extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private RaidsTeamInfo raidsInfo;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "进入下一层成功";

		// ======获取数据======
		int floor = msg.readInt("floor") + 1;

		raidsInfo = online.getRaidsTeamInfo();

		if (raidsInfo == null) {
			result = 1;
			reason = "进入下一层失败";
			return;
		}

		if (raidsInfo.checkRaidsFloor(floor)) {
			// 重置副本层信息
			if (!raidsInfo.resetRaidsFloor(floor)) {
				result = 1;
				reason = "进入下一层失败，已经在最后一层";
				return;
			}
		}

		MapRaidsInfo mapInfo = raidsInfo.getRaidsFloor().getCell(EOrientation.none, -1);

		int x = raidsInfo.getRaidsInfo().getArriveX();
		int y = raidsInfo.getRaidsInfo().getArriveY();
		// 判断有没有队伍
		if (online.getTeam() != null) { // 如果组队

			Team team = online.getTeam();
			if (team.getLeader().getId() != online.getId()) {
				result = 1;
				reason = "您不是队长";
				return;
			}

			MapServer.changeMap(online.getTeam(), x, y, mapInfo, online.getMapInfo().getBaseMap());
			RaidsMessage.refreshMiniMap(raidsInfo); // 刷新副本小地图

		} else {
			MapServer.changeMap(online, x, y, mapInfo, online.getMapInfo().getBaseMap());
			RaidsMessage.refreshMiniMap(raidsInfo); // 刷新副本小地图
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_raids_next");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}