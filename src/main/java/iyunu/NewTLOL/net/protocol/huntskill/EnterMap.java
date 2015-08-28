package iyunu.NewTLOL.net.protocol.huntskill;

import iyunu.NewTLOL.json.HuntskillJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.skillBook.HuntskillMapInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.map.MapServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Util;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 请求进入猎技地图
 * @author LSR
 * @date 2012-8-27
 */
public class EnterMap extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "请求进入猎技地图成功";
	private int x = 0;
	private int y = 0;
	private BaseMap oldMapInfo = null;
	private BaseMap mapInfo = null;

	@Override
	public void handleReceived(LlpMessage msg) {

		if (online.getLevel() < 35) {
			result = 1;
			reason = "等级不足35级！";
			return;
		}

		if (!ActivityManager.HUNTSKILL_STATE) {
			result = 1;
			reason = "现在不是猎技时间！";
			return;
		}

		oldMapInfo = online.getMapInfo().getBaseMap();
		mapInfo = MapManager.instance().getMapById(10);

		if (mapInfo == null) {
			result = 1;
			reason = "地图不存在";
			return;
		}

		if (online.getGang() == null) {
			result = 1;
			reason = "无帮派";
			return;
		}

		if (online.getGang().getLevel() < 3) {
			result = 1;
			reason = "帮派等级小于3级";
			return;
		}

		HuntskillMapInfo huntskillMapInfo = HuntskillJson.instance().getHuntskillMapInfo();
		x = huntskillMapInfo.getComeInX();
		y = huntskillMapInfo.getComeInY();

		// 判断有没有队伍,
		if (online.getTeam() != null) {
			result = 1;
			reason = "不能组队进入";
			return;
		}

		// if (online.getHuntskillNum() <= 0) {
		// result = 1;
		// reason = "猎技次数不足";
		// return;
		// }

		// 扣除一次地宫进入次数
		// online.setHuntskillNum(online.getHuntskillNum() - 1);
		// 增加帮派活跃度
		RoleServer.addGangActivity(online, 6);

		result = 0;
		reason = "请求进入猎技地图成功";
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_enterMap");
			message.write("result", result);
			message.write("reason", reason);
			int time = (int) Util.matchZero((ActivityManager.HUNTSKILL_END_TIME - System.currentTimeMillis()) / Time.MILLISECOND);
			message.write("time", time);

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0) {
			MapServer.changeMap(online, x, y, mapInfo, oldMapInfo);
		}
	}

}
