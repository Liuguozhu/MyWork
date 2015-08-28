package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.enumeration.common.EGang;
import iyunu.NewTLOL.manager.IOProcessManager;
import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.io.gang.GangIOTask;
import iyunu.NewTLOL.model.io.gang.instance.GangUpdateTask;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.gang.GangServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 退出帮派
 * 
 * @author fenghaiyu
 * 
 */
public class QuitGang extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "退出成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "退出成功";

		if (online.getGangId() == 0) {
			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}
		Gang gang = GangManager.instance().getGang(online.getGangId());
		if (gang == null) {
			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}

		if (gang.getLeader() == online.getId()) {
			result = 1;
			reason = "帮主不能退帮";
			return;
		}

		if (GangFightManager.GANG_FIGHT_STATE != 2) {
			result = 1;
			reason = "您的帮派已参加帮战活动，活动期间不可以退出帮派！";
			return;
		}

		try {

			// 删除
			gang.del(online.getId());

			// 发送双方帮派提醒
			GangManager.instance().sendGangWarn(online, "您已退出帮派！");
			GangManager.instance().sendGangWarnToAll(gang, StringControl.yel(online.getNick()) + "退出帮派！");

			// 清除个人信息
			GangServer.quitGang(online);

		} catch (Exception e) {
			result = 1;
			reason = "操作失败";
			e.printStackTrace();
			return;
		}
		GangIOTask task = new GangUpdateTask(gang);
		IOProcessManager.instance().addGangTask(task);

		// 退帮减少活跃值
		// gang.minnusActive(EGangActiveEvent.退帮.getActive(),
		// EGangActiveEvent.退帮.name(), online.getNick());
		GangMessage.refreshGangNum(gang);
		// gang.addActive(EGangActiveEvent.退帮.getActive());
		LogManager.gang(online.getId(), online.getUserId(), online.getGangId(), gang.getId(), gang.getName(), 0, 0, EGang.退出帮派.ordinal());

		// 帮战地图中退帮，将其传送出帮战地图，并且减少帮战人员数量
		// BaseMap oldMapInfo = online.getMapInfo().getBaseMap();
		// if (oldMapInfo.getId() == 16) {
		// Team team = online.getTeam();
		// if (team != null) {
		// // 从队伍中删除
		// team.removeMember(online);
		// // 更换队长
		// Role newLeader = team.getMember().get(0);
		// if (newLeader != null) {
		// team.changeLeader(newLeader);
		// }
		//
		// MapManager.instance().addTeamQueue(newLeader);
		// MapManager.instance().addTeamQueue(online);
		// }
		// GangFightManager.instance().quitGangFight(online);
		// }
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_quitGang");
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
