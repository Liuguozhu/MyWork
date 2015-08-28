package iyunu.NewTLOL.net.protocol.qiancengta;

import iyunu.NewTLOL.json.MonsterJson;
import iyunu.NewTLOL.json.QiancengtaJson;
import iyunu.NewTLOL.manager.BattleManager;
import iyunu.NewTLOL.message.FightMessage;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.map.EMapType;
import iyunu.NewTLOL.model.monster.instance.MonsterGroup;
import iyunu.NewTLOL.model.qiancengta.instance.QiancengtaInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.processor.BattleProcessorCenter;
import iyunu.NewTLOL.server.battle.BattleServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 挑战千层塔
 * @author LuoSR
 * @date 2014年8月14日
 */
public class ChallengeQct extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private BattleInfo battleInfo;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "挑战成功";

		if (online.getLevel() < 40) {
			result = 1;
			reason = "等级不足40级，不能挑战";
			return;
		}
		if (!online.getMapInfo().getBaseMap().getType().equals(EMapType.common)) {
			result = 1;
			reason = "此地图不能挑战";
			return;
		}
		int currentFloorId = msg.readInt("currentFloorId");

		if (online.isBattle()) {
			result = 1;
			reason = "已战斗";
			return;
		}

		if (online.isPrBattle()) {
			result = 1;
			reason = "已申请战斗";
			return;
		}
		online.setPrBattle(true);

		if (online.getTeam() != null) {
			result = 1;
			reason = "组队状态时不能挑战千层塔";
			return;
		}

		if (currentFloorId > QiancengtaJson.instance().getQiancengtas().size()) {
			result = 1;
			reason = "已经全部挑战完成";
			return;
		}

		if (online.getCurrentFloorId() != currentFloorId) {
			result = 1;
			reason = "不能挑战";
			return;
		}

		QiancengtaInfo qiancengtaInfo = QiancengtaJson.instance().getQiancengtaMap().get(currentFloorId);
		MonsterGroup monsterGroup = MonsterJson.instance().getMonsterGroup(qiancengtaInfo.getGroupId());
		battleInfo = BattleServer.preBattle(online, monsterGroup, 5, online.getMapInfo().getBaseMap(), false);
		battleInfo.setCurrentFloorId(currentFloorId);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_challengeQct");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0) {
			online.setBattle(true);
			BattleManager.instance().addBattleProcessor(new BattleProcessorCenter(battleInfo));
			online.setBattleId(battleInfo.getBattleId()); // 设置战斗编号
			FightMessage.sendEncounterResult(battleInfo, online);
		}
		online.setPrBattle(false);
	}

}
