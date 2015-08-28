package iyunu.NewTLOL.net.protocol.battle;

import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.BattleManager;
import iyunu.NewTLOL.manager.BloodManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.FightMessage;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.role.ERoleFightState;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.processor.BattleProcessorCenter;
import iyunu.NewTLOL.server.battle.FightServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * PVP
 * 
 * @author SunHonglei
 * 
 */
public class Fight extends TLOLMessageHandler {

	private int result = 0;
	private String reason;
	private BattleInfo battleInfo;
	private Role role;

	@Override
	public void handleReceived(LlpMessage msg) {

		// ======重置数据======
		result = 0;
		reason = "切磋成功";
		role = null;
		if (online.getMapInfo().getBaseMap().getPvp() == 1 && online.getBlood() == 0) {
			result = 1;
			reason = "您所在地图不可以战斗";
			return;
		}

		if (online.isBattle()) {
			result = 1;
			reason = "已战斗";
			return;
		}
		long roleId = msg.readLong("roleId");
		role = ServerManager.instance().getOnlinePlayer(roleId);
		if (!ERoleFightState.check(online.getFightState(), role.getFightState())) {
			result = 1;
			reason = "您目前不可以与此玩家发生战斗";
			return;
		}

		if (online.isPrBattle()) {
			result = 1;
			reason = "已申请战斗";
			return;
		}

		if (online.getLevel() < 30) {
			result = 1;
			reason = "少侠您的等级还不能与人战斗（30级开启）";
			return;
		}

		// ======获取参数======

		if (role == null) {
			result = 1;
			reason = "玩家不存在";
			return;
		}

		if (role.getTeam() != null) {
			role = role.getTeam().getLeader();
		}

		if (role.getLevel() < 30) {
			result = 1;
			reason = "对方处于等级保护阶段（30级开启）";
			return;
		}

		if (role.getTeam() != null && online.getTeam() != null && role.getTeam().getId() == online.getTeam().getId()) {
			result = 1;
			reason = "同一队伍不能战斗";
			return;
		}

		// 判断是否在同一地图上
		if (role.getMapInfo().getMapId() != online.getMapInfo().getMapId()) {
			result = 1;
			reason = "不在同一地图";
			return;
		}

		if (role.isPrBattle()) {
			result = 1;
			reason = "对方已申请战斗";
			return;
		}

		if (role.isBattle()) {
			result = 1;
			reason = "对方已战斗";
			return;
		}
		if (System.currentTimeMillis() - role.getBloodKillTime() < 15 * 1000l) {
			result = 1;
			reason = "对方处于血战保护时间";
			return;
		}
		if (ActivityManager.BLOOD_STATE) {
			if (!BloodManager.instance().checkBlood(online.getBlood(), role.getBlood())) {
				result = 1;
				reason = "不可以与该玩家战斗！！！";
				return;
			}
		}
		online.setPrBattle(true);
		role.setPrBattle(true);

		battleInfo = FightServer.preBattle(online, role, online.getMapInfo().getBaseMap(), false);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_fight");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0) {
			BattleManager.instance().addBattleProcessor(new BattleProcessorCenter(battleInfo));
			online.setBattle(true);
			online.setBattleId(battleInfo.getBattleId()); // 设置战斗编号
			role.setBattle(true);
			role.setBattleId(battleInfo.getBattleId()); // 设置战斗编号
			FightMessage.sendEncounterResult(battleInfo);
			online.setPrBattle(false);
			role.setPrBattle(false);
		}

	}

}
