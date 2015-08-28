package iyunu.NewTLOL.net.protocol.gang.fight;

import iyunu.NewTLOL.json.GangJson;
import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.model.gang.FightApplyInfo;
import iyunu.NewTLOL.model.gang.GangFightMapInfo;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.EMapType;
import iyunu.NewTLOL.model.role.ERoleFightState;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.map.MapServer;
import iyunu.NewTLOL.util.Time;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 进入帮战地图
 * @author LuoSR
 * @date 2014年6月30日
 */
public class EnterGangFightMap extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "进入帮战地图成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "进入帮战地图成功";

		if (GangFightManager.STATE != 3) {
			result = 1;
			reason = "现在不是进场时间";
			return;
		}

		if (!online.getMapInfo().getBaseMap().getType().equals(EMapType.common)) {
			result = 1;
			reason = "此地图不能帮战地图";
			return;
		}

		BaseMap oldMapInfo = online.getMapInfo().getBaseMap();
		if (oldMapInfo.getType().equals(EMapType.raids)) {
			result = 1;
			reason = "特殊地图中不能进入帮战地图";
			return;
		}

		if (online.getGangId() == 0) {
			result = 1;
			reason = "进入失败，无帮派";
			return;
		}

		if (GangFightManager.GANG_FIGHT_STATE == 0) { // 循环赛
			if (!GangFightManager.报名列表.contains(online.getGangId())) {
				result = 1;
				reason = "您的帮派没有报名帮派战";
				return;
			}
		} else if(GangFightManager.GANG_FIGHT_STATE == 1){
			if (!GangFightManager.参加当前淘汰赛帮派编号.contains(online.getGangId())) {
				result = 1;
				reason = "您的帮派没有进入淘汰赛";
				return;
			}
		}

		FightApplyInfo fightApplyInfo = GangFightManager.参赛列表.get(online.getGangId());

		// if (EGangFightState.state() == 1 &&
		// fightApplyInfo.getResultStateMap().size() != 0) {
		// Set<Entry<Integer, Integer>> set =
		// fightApplyInfo.getResultStateMap().entrySet();
		// for (Iterator<Entry<Integer, Integer>> iterator = set.iterator();
		// iterator.hasNext();) {
		// Entry<Integer, Integer> entry = iterator.next();
		//
		// if (entry.getValue() == 2) {
		// result = 1;
		// reason = "你的帮派已被淘汰，不能进入";
		// return;
		// }
		// }
		// }

		int x = 0;
		int y = 0;

		GangFightMapInfo gangFightMapInfo = GangJson.instance().getGangFightMapInfo();
		if (fightApplyInfo.getUpOrDown() == 1) {
			x = gangFightMapInfo.getComeInUpX();
			y = gangFightMapInfo.getComeInUpY();
		} else {
			x = gangFightMapInfo.getComeInDownX();
			y = gangFightMapInfo.getComeInDownY();
		}
		// 判断有没有队伍
		if (online.getTeam() != null) {
			if (online.getTeam().getLeader().getId() != online.getId()) {
				result = 1;
				reason = "您不是队长";
				return;
			}

			for (Role role : online.getTeam().getMember()) {
				if (role.getId() != online.getId() && role.getGangId() != online.getGangId()) {
					result = 1;
					reason = "队伍中有不是一个帮派的队员";
					return;
				}
			}

			if (fightApplyInfo.getUpOrDown() == 1) {
				for (Role role : online.getTeam().getMember()) {
					role.setFightState(ERoleFightState.left);
					fightApplyInfo.enter(role.getId());
				}
			} else {
				for (Role role : online.getTeam().getMember()) {
					role.setFightState(ERoleFightState.right);
					fightApplyInfo.enter(role.getId());
				}
			}

			MapServer.changeMap(online.getTeam(), x, y, fightApplyInfo.mapGangFightInfo(), oldMapInfo);
		} else {
			if (fightApplyInfo.getUpOrDown() == 1) {
				online.setFightState(ERoleFightState.left);
			} else {
				online.setFightState(ERoleFightState.right);
			}
			fightApplyInfo.enter(online.getId());
			MapServer.changeMap(online, x, y, fightApplyInfo.mapGangFightInfo(), oldMapInfo);
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_enterGangFightMap");
			message.write("result", result);
			message.write("reason", reason);

			if (result == 0) {
				long now = System.currentTimeMillis();
				int fightTime = (int) ((GangFightManager.开战时间 - now) / Time.MILLISECOND_L);
				int outTime = (int) ((GangFightManager.结束时间 - now) / Time.MILLISECOND_L);
				message.write("fightTime", fightTime);
				message.write("outTime", outTime);
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
