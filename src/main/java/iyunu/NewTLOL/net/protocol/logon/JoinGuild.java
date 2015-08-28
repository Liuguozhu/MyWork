package iyunu.NewTLOL.net.protocol.logon;

import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.task.ETaskType;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.server.task.TaskServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 加入门派
 * 
 * @author SunHonglei
 * 
 */
public class JoinGuild extends TLOLMessageHandler {

	private int result = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;

		int guildId = msg.readInt("guildId");
		if (online.getLevel() < 1) {
			result = 1;
			return;
		}
		if (!online.getVocation().equals(Vocation.none)) {
			result = 2;
			return;
		}
		online.setVocation(Vocation.values()[guildId]);
		ServerManager.instance().addChatChannel(online);

		// 加入门派重置技能
		RoleServer.resetSkill(online);

		// 增加技能点数
		RoleServer.addSkill(online, 1);

//		 online.setSumSkill(online.getSumSkill() + 1);
//		// 加入门派默认添加技能
//		RoleSkill roleSkill = SkillJson.instance().getRoleSkillById(online.getSkillMap().get(1));
//		RoleSkill nextRoleSkill = SkillJson.instance().getRoleSkillById(roleSkill.getNextSkill());
//		online.getSkillMap().put(roleSkill.getPosition(), nextRoleSkill.getId());

		// ======检查是否有任务（加入门派任务）======
		TaskServer.finishTaskByType(online, ETaskType.joinGuildTask);

		MapManager.instance().addBaseQueue(online);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_joinGuild");
			message.write("result", result);
			message.write("vocation", online.getVocation().getName());
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