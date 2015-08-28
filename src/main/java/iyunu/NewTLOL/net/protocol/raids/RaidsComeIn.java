package iyunu.NewTLOL.net.protocol.raids;

import iyunu.NewTLOL.json.RaidsJson;
import iyunu.NewTLOL.manager.DailyManager;
import iyunu.NewTLOL.manager.RaidsManager;
import iyunu.NewTLOL.message.RaidsMessage;
import iyunu.NewTLOL.model.helper.EHelper;
import iyunu.NewTLOL.model.map.EOrientation;
import iyunu.NewTLOL.model.map.instance.MapRaidsInfo;
import iyunu.NewTLOL.model.raids.instance.RaidsInfo;
import iyunu.NewTLOL.model.raids.instance.RaidsTeamInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.helper.HelperServer;
import iyunu.NewTLOL.server.map.MapServer;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 进入副本
 * 
 * @author SunHonglei
 * 
 */
public class RaidsComeIn extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "进入副本成功";

		int raidsId = msg.readInt("raidsId");
		if (online.getTeam() != null) {

			RaidsInfo raidsInfo = RaidsJson.instance().getRaidsById(raidsId);
			if (raidsInfo == null) {
				result = 1;
				reason = "副本不存在";
				return;
			}

			for (Role role : online.getTeam().getMember()) {
				if (RaidsManager.getRaidsNumber(role, raidsInfo.getIndex()) == 0) {
					result = 1;
					reason = "队员【" + role.getNick() + "】今日次数已用完！";
					return;
				}
				if (role.getBlood() != 0) {
					result = 1;
					reason = "队员【" + role.getNick() + "】，血战状态下不可进入副本！";
					return;
				}

				if (role.getLevel() < raidsInfo.getLevelLimit()) {
					result = 1;
					reason = "队员【" + role.getNick() + "】等级不足";
					return;
				}
			}

			for (Role role : online.getTeam().getMember()) {
				RaidsManager.addRaidsNumber(role, raidsInfo.getIndex()); // 增加副本次数
				try {
					List<Integer> dailyFuben = DailyManager.instance().getDailyFuBen();
					if (dailyFuben != null && dailyFuben.size() > 0)
						for (Integer dailyId : dailyFuben) {
							if (DailyManager.instance().checkEvent(dailyId)) {
								DailyManager.instance().finishDaily(role.getDailyMap().get(dailyId), role, 1);
							}
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
				HelperServer.helper(role, EHelper.raids); // 小助手记录
			}

			RaidsTeamInfo raidsTeamInfo = new RaidsTeamInfo(raidsInfo, online.getTeam().getMember());
			MapRaidsInfo mapInfo = raidsTeamInfo.getRaidsFloor().getCell(EOrientation.none, -1);
			int x = raidsInfo.getArriveX();
			int y = raidsInfo.getArriveY();
			MapServer.changeMap(online.getTeam(), x, y, mapInfo, online.getMapInfo().getBaseMap());

			RaidsMessage.refreshMiniMap(raidsTeamInfo); // 刷新副本小地图
		} else {
			if (online.getRaidsTeamInfo() != null) {
				MapRaidsInfo mapInfo = online.getRaidsTeamInfo().getRaidsFloor().getCell(EOrientation.none, -1);
				int x = online.getRaidsTeamInfo().getRaidsInfo().getArriveX();
				int y = online.getRaidsTeamInfo().getRaidsInfo().getArriveY();
				MapServer.changeMap(online, x, y, mapInfo, online.getMapInfo().getBaseMap());
				RaidsMessage.refreshMiniMap(online.getRaidsTeamInfo()); // 刷新副本小地图
				return;
			}

			RaidsInfo raidsInfo = RaidsJson.instance().getRaidsById(raidsId);

			if (raidsInfo == null) {
				result = 1;
				reason = "副本不存在";
				return;
			}

			if (RaidsManager.getRaidsNumber(online, raidsInfo.getIndex()) == 0) {
				result = 1;
				reason = "今日次数已用完！";
				return;
			}

			if (online.getLevel() < raidsInfo.getLevelLimit()) {
				result = 1;
				reason = "等级不足";
				return;
			}
			RaidsManager.addRaidsNumber(online, raidsInfo.getIndex()); // 增加副本次数
			try {
				List<Integer> dailyFuben = DailyManager.instance().getDailyFuBen();
				if (dailyFuben != null && dailyFuben.size() > 0)
					for (Integer dailyId : dailyFuben) {
						if (DailyManager.instance().checkEvent(dailyId)) {
							DailyManager.instance().finishDaily(online.getDailyMap().get(dailyId), online, 1);
						}
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			HelperServer.helper(online, EHelper.raids); // 小助手记录
			RaidsTeamInfo raidsTeamInfo = new RaidsTeamInfo(raidsInfo, online);
			MapRaidsInfo mapInfo = raidsTeamInfo.getRaidsFloor().getCell(EOrientation.none, -1);
			int x = raidsInfo.getArriveX();
			int y = raidsInfo.getArriveY();
			MapServer.changeMap(online, x, y, mapInfo, online.getMapInfo().getBaseMap());
			RaidsMessage.refreshMiniMap(raidsTeamInfo); // 刷新副本小地图
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_raids_comeIn");
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