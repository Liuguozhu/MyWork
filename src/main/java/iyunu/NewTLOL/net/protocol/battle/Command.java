package iyunu.NewTLOL.net.protocol.battle;

import iyunu.NewTLOL.manager.BattleManager;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.CommandInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.processor.BattleProcessorCenter;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 战斗指令
 * 
 * @author SunHonglei
 * 
 */
public class Command extends TLOLMessageHandler {

	private int result;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======重置数据======
		result = 0;
		reason = "";

		// ======获取参数======
		long battleId = msg.readLong("battleId");
		int index = msg.readInt("index");
		int type = msg.readInt("type");
		long skill = msg.readLong("skill");
		int foeIndex = msg.readInt("foeIndex");
		int turn = msg.readInt("turn");
		int pFoeIndex = msg.readInt("pFoeIndex");
		int pType = msg.readInt("pType");
		int pItemIndex = msg.readInt("pItemIndex");

//		LogManager.info("客户端发送信息[" + online.getNick() + "]battleId=" + battleId + "，index=" + index + "，type=" + type + "，skill=" + skill + "，foeIndex=" + foeIndex + "，turn=" + turn + "，pFoeIndex=" + pFoeIndex + "，pType=" + pType + "，pItemIndex=" + pItemIndex);

		// ======检查条件======
		if (battleId <= 0) {
			result = 1;
			reason = "请求失败";
			return;
		}

		BattleProcessorCenter battleProcessorCenter = BattleManager.instance().getBattleProcessorCenter(battleId);
		if (battleProcessorCenter == null) {
			result = 1;
			reason = "战斗已结束";
			return;
		}

		if (!battleProcessorCenter.checkTurn(turn)) {
			result = 1;
			reason = "请求超时";
			return;
		}

		BattleInfo battleInfo = battleProcessorCenter.getBattleInfo();
		if (battleInfo == null) {
			result = 1;
			reason = "战斗不存在";
			return;
		}

		// ======角色操作指令======
		BattleCharacter battleCharacter = battleInfo.getBattleCharacter(index);
		if (battleCharacter.getCommandInfo() == null || battleCharacter.getCommandInfo().getTurn() < turn) {

			if (type == 0) {
				if (online.getSkillMap().get((int) skill) != null) {
					skill = online.getSkillMap().get((int) skill);
				} else {
					skill = 0;
				}
			}

			battleCharacter.setCommandInfo(new CommandInfo(index, foeIndex, type, skill, turn));
		}

		// ======伙伴操作指令======
		BattleCharacter partnerBattleCharacter = battleInfo.getBattleCharacter(index + 3);
		if (partnerBattleCharacter != null && (partnerBattleCharacter.getCommandInfo() == null || battleCharacter.getCommandInfo().getTurn() < turn)) {
			if (pType == 0) { // 伙伴攻击，默认4000技能，普通攻击
				pItemIndex = 40000;
			}
			partnerBattleCharacter.setCommandInfo(new CommandInfo(index + 3, pFoeIndex, pType, pItemIndex, turn));
		}

		battleInfo.counterIncrease(); // 命令计数器

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_command");
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
