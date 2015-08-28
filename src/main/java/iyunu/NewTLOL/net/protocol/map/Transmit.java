package iyunu.NewTLOL.net.protocol.map;

import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.EMapType;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.map.MapServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 传送
 * 
 * @author SunHonglei
 * 
 */
public class Transmit extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "";
		// 血战限制
		if (online.getBlood() != 0) {
			result = 1;
			reason = "血战中不可以传送！";
			return;
		}
		// ======获取参数======
		int mapId = msg.readInt("mapId");
		int x = msg.readInt("x");
		int y = msg.readInt("y");

		// ======条件检查======
//		if (!online.getVip().isVip(EVip.gold)) {
//			result = 1;
//			reason = "不是黄金VIP";
//			return;
//		}

		BaseMap oldMapInfo = online.getMapInfo().getBaseMap();

		BaseMap mapInfo = MapManager.instance().getMapById(mapId);
		if (mapInfo == null) {
			result = 1;
			reason = "地图不存在";
			return;
		}

		if (mapInfo.getType().equals(EMapType.gangFight)) {
			result = 1;
			reason = "此地图不能传送";
			return;
		}

		if (x == 0 || y == 0) {
			x = mapInfo.getTransmitX();
			y = mapInfo.getTransmitY();
		}

		// 判断有没有队伍,
		if (online.getTeam() != null) {
			if (online.getTeam().getLeader().getId() != online.getId()) {
				result = 1;
				reason = "您不是队长";
				return;
			}
			MapServer.changeMap(online.getTeam(), x, y, mapInfo, oldMapInfo);
		} else {
			MapServer.changeMap(online, x, y, mapInfo, oldMapInfo);
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_transmit");
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