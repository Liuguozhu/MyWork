package iyunu.NewTLOL.net.protocol.map;

import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpMessage;

/**
 * 刷新坐标位置
 * 
 * @author SunHonglei
 * 
 */
public class Coord extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {

		int mapId = msg.readInt("mapId");
		if (mapId == online.getMapInfo().getMapId()) {
			int x = msg.readInt("x");
			int y = msg.readInt("y");
			int arriveX = msg.readInt("arriveX");
			int arriveY = msg.readInt("arriveY");
			if (online.getMapInfo().getX() != x || online.getMapInfo().getY() != y) {
				BaseMap baseMap = online.getMapInfo().getBaseMap();
				if (baseMap != null) {
					if (MapManager.checkCoord(baseMap, arriveX, arriveY)) {
						MapManager.instance().moveGrid(online, baseMap, x, y, arriveX, arriveY); // 改变所在格子
					} else {
						MapManager.instance().moveGrid(online, baseMap, x, y, x, y); // 改变所在格子
					}
				}
			}
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			// if (online.getTeam() != null &&
			// online.getTeam().getLeader().getId() == online.getId()) { //
			// 如果是是队长，则给队员同步坐标
			// message = LlpJava.instance().getMessage("s_coord");
			// message.write("x", online.getMapInfo().getX());
			// message.write("y", online.getMapInfo().getY());
			//
			// List<Role> list = online.getTeam().getMember();
			// for (int i = 1; i < list.size(); i++) { // 只给队员同步坐标即可
			// Role role = list.get(i);
			// if (role != null && role.getChannel() != null &&
			// ServerManager.instance().isOnline(role.getId())) {
			// role.getChannel().write(message);
			// }
			// }
			// }
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}