package iyunu.NewTLOL.net.protocol.skill;

import iyunu.NewTLOL.event.uptip.UptipEvent;
import iyunu.NewTLOL.json.SkillJson;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.skill.instance.RoleSkill;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 角色学习技能
 * @author LuoSR
 * @date 2013-11-28
 */
public class RoleStudySkill extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "成功";

		int position = msg.readInt("position");

		if (online.getFreeSkill() <= 0) {
			result = 1;
			reason = "技能点不足";
			return;
		}
		if (online.getSkillMap().isEmpty()) {
			LogManager.exception("学习技能异常：技能列表为null");
			result = 1;
			reason = "学习失败";
			return;
		}
		RoleSkill roleSkill = SkillJson.instance().getRoleSkillById(online.getSkillMap().get(position));
		if (roleSkill == null) {
			LogManager.exception("学习技能异常：获取角色技能为null");
			result = 1;
			reason = "学习失败";
			return;
		}
		if (roleSkill.getNextSkill() == 0) {
			result = 1;
			reason = "技能已满级";
			return;
		}

		RoleSkill nextRoleSkill = SkillJson.instance().getRoleSkillById(roleSkill.getNextSkill());
		if (nextRoleSkill.getLimitPosition() != 0 && nextRoleSkill.getLimitLevel() > SkillJson.instance().getRoleSkillById(online.getSkillMap().get(nextRoleSkill.getLimitPosition())).getLevel()) {
			result = 1;
			reason = "前置条件不满足";
			return;
		}

		online.setFreeSkill(online.getFreeSkill() - 1);
		online.getSkillMap().put(roleSkill.getPosition(), nextRoleSkill.getId());

		RoleServer.countProperty(online); // 重新计算技能属性
		SendMessage.sendSttribute(online); // 通知客户端属性变化
		UptipEvent.技能点.check(online, online.getUptipBoolean(UptipEvent.技能点.getOrdinal()));
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_roleStudySkill");
			message.write("result", result);
			message.write("reason", reason);
			message.write("freeSkill", online.getFreeSkill());
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
		if (result == 0) {
			// ======发送角色技能======
			SendMessage.sendSkill(online);
		}

	}

}
