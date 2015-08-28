package iyunu.NewTLOL.net.protocol.map;

import iyunu.NewTLOL.json.MapJson;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.message.RaidsMessage;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.instance.MapRaidsInfo;
import iyunu.NewTLOL.model.map.instance.TransferInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.map.MapServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 过地图
 * 
 * @author SunHonglei
 * 
 */
public class ChangeMap extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "";

		// ======获取参数======
		int transferId = msg.readInt("transferId");

		// ======条件检查======
		BaseMap oldMapInfo = online.getMapInfo().getBaseMap();
		if (!oldMapInfo.getTransfers().contains(transferId)) {
			result = 1;
			reason = "传送点不存在";
			return;
		}
		TransferInfo transferInfo = MapJson.instance().getTransferById(transferId);

		if (transferInfo == null) {
			result = 1;
			reason = "传送点不存在";
			return;
		}

		BaseMap mapInfo = null;
		if (oldMapInfo instanceof MapRaidsInfo) {
			MapRaidsInfo oldMapRaidsInfo = (MapRaidsInfo) oldMapInfo;
			MapRaidsInfo mapRaidsInfo = oldMapRaidsInfo.getRaidsTeamInfo().getRaidsFloor().getCell(transferInfo.getOrientation(), oldMapRaidsInfo.getIndex());
			mapInfo = mapRaidsInfo;
		} else {
			mapInfo = MapManager.instance().getMapById(transferInfo.getDestination());
		}

		if (mapInfo == null) {
			result = 1;
			reason = "地图不存在";
			return;
		}

		int x = transferInfo.getArriveX();
		int y = transferInfo.getArriveY();

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

		if (online.getRaidsTeamInfo() != null) {
			if (online.getMapInfo().isMapRaids()) {
				RaidsMessage.refreshMiniMap(online.getRaidsTeamInfo()); // 刷新副本小地图
			}
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_changeMap");
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