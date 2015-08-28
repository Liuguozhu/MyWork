package iyunu.NewTLOL.net.protocol.intensify;

import iyunu.NewTLOL.enumeration.EIntensifyEvent;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.json.IntensifyJson;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.intensify.instance.BodyInfo;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.vip.EVip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 部位强化
 * @author LSR
 * @date 2012-8-9
 */
public class BodyIntensify extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "强化成功";

		int part = msg.readInt("part"); // 部位
		int number = msg.readInt("number"); // 强化次数

		EEquip eEquip = EEquip.values()[part];
		int level = online.getBodyIntensify().get(eEquip);

		if (level >= online.getLevel()) {
			result = 1;
			reason = "您已经强化到最高等级";
			return;
		}

		if (number + level >= online.getLevel()) {
			number = online.getLevel() - level;
		}

		int sum = 0;
		int i = 0;

		// int addMattack = 0;
		// int addPattack = 0;
		// int addMdefence = 0;
		// int addPdefence = 0;
		// int addHp = 0;
		// int addMp = 0;
		// int addSpeed = 0;

		while (i < number) {
			BodyInfo equipIntensify = IntensifyJson.instance().getEquipIntensifyMap(eEquip, level + i);
			if (online.getVip().isVip(EVip.diamond)) {
				sum += equipIntensify.getVipCost();
			} else {
				sum += equipIntensify.getGold();
			}

			// addMattack += equipIntensify.getAddMattack();
			// addPattack += equipIntensify.getAddPattack();
			// addMdefence += equipIntensify.getMdefence();
			// addPdefence += equipIntensify.getAddPdefence();
			// addHp += equipIntensify.getAddHp();
			// addMp += equipIntensify.getAddMp();
			// addSpeed += equipIntensify.getAddSpeed();

			i++;
			if (level + i >= online.getLevel()) {
				break;
			}

		}

		if (!RoleServer.costGoldOnly(online, sum, EGold.qianghua, false)) {
			result = 1;
			reason = "";
			SendMessage.refreshNoCoin(online, 1);
			return;
		}

		online.getBodyIntensify().put(eEquip, level + i); // 强化
		reason += "强化" + i + "次成功，消耗" + sum + "绑银";
		RoleServer.countIntensify(online); // 计算属性

		// HelperServer.helper(online, EHelper.intensify); // 小助手记录
		LogManager.intensify(online, EIntensifyEvent.部位强化, part, 0, number);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_bodyIntensify");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
		if (result == 0) {
			// ======发送部位强化======
			SendMessage.sendBodyIntensify(online);
			SendMessage.sendSttribute(online);
		}
	}
}
