package iyunu.NewTLOL.net.protocol.trials;

import iyunu.NewTLOL.json.MonsterJson;
import iyunu.NewTLOL.json.RaidsJson;
import iyunu.NewTLOL.manager.BattleManager;
import iyunu.NewTLOL.message.FightMessage;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.map.EMapType;
import iyunu.NewTLOL.model.monster.instance.MonsterGroup;
import iyunu.NewTLOL.model.trials.instance.TrialsInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.processor.BattleProcessorCenter;
import iyunu.NewTLOL.server.battle.BattleServer;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 击杀怪物
 * @author LuoSR
 * @date 2014年4月17日
 */
public class TrialsKill extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private BattleInfo battleInfo;

	@Override
	public void handleReceived(LlpMessage msg) {

		// ======重置数据======
		result = 0;
		reason = "遇怪成功";

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
		// ======获取参数======
		int trialsId = msg.readInt("trialsId");
		int position = msg.readInt("position");

		if (online.getTeam() != null) {
			result = 1;
			reason = "组队状态时不能试炼";
			return;
		}

		if (!online.getMapInfo().getBaseMap().getType().equals(EMapType.common)) {
			result = 1;
			reason = "此地图不能试炼";
			return;
		}

		TrialsInfo trialsInfo = RaidsJson.instance().getTrialsMap().get(trialsId);
		if (trialsInfo == null) {
			result = 1;
			reason = "本层试练已经全部击杀完毕！";
			return;
		}
		if (!trialsInfo.getMonsterMap().containsKey(position)) {
			result = 1;
			reason = "本层试练已经全部击杀！";
			return;
		}
		int monsterGroupId = trialsInfo.getMonsterMap().get(position);
		MonsterGroup monsterGroup = MonsterJson.instance().getMonsterGroup(monsterGroupId);
		battleInfo = BattleServer.preBattle(online, monsterGroup, 3, online.getMapInfo().getBaseMap(), false);

		battleInfo.setTrialsId(trialsId);

		LogManager.trials(online, trialsId, position); // 试炼日志
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_kill");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
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