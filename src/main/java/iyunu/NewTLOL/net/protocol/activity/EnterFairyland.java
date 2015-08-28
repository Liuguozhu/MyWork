package iyunu.NewTLOL.net.protocol.activity;

import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.model.activity.ActivityFairyland;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.EMapType;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.map.MapServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 秘境
 * 
 * @author SunHonglei
 * 
 */
public class EnterFairyland extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "领取成功";

		int id = msg.readInt("id");

		if (online.getTeam() != null) {
			result = 1;
			reason = "不可以组队进入";
			return;
		}

		ActivityFairyland activityFairyland = ActivityJson.instance().getActivityFairyland(id);
		if (activityFairyland == null) {
			result = 1;
			reason = "进入失败";
			return;
		}

		if (activityFairyland.getLevelMin() > online.getLevel()) {
			result = 1;
			reason = "角色等级低于" + activityFairyland.getLevelMin() + "级不能进入";
			return;
		}

		if (activityFairyland.getLevelMax() < online.getLevel()) {
			result = 1;
			reason = "角色等级超过" + activityFairyland.getLevelMax() + "级不能进入";
			return;
		}

		BaseMap oldMap = online.getMapInfo().getBaseMap();
		if (oldMap.getType().equals(EMapType.gangFight) || oldMap.getType().equals(EMapType.huntSkill) || oldMap.getType().equals(EMapType.raids)) {
			result = 1;
			reason = "当前地图不能进入此地图";
			return;
		}
		BaseMap mapInfo = MapManager.instance().getMapById(activityFairyland.getMapId());
		if (mapInfo == null) {
			result = 1;
			reason = "地图不存在";
			return;
		}

		MapServer.changeMap(online, activityFairyland.getX(), activityFairyland.getY(), mapInfo, oldMap);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_enterFairyland");
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