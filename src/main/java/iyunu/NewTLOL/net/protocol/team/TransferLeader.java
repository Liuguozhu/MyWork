package iyunu.NewTLOL.net.protocol.team;

import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.TeamMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 移交队长
 * @author LuoSR
 * @date 2014年5月9日
 */
public class TransferLeader extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Role header = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "移交队长成功";

		long headerId = msg.readLong("headerId");

		// ======检查条件======
		if (online.getTeam().getLeader().getId() != online.getId()) {
			result = 1;
			reason = "您不是队长";
			return;
		}

		if (headerId == online.getId()) {
			result = 1;
			reason = "不能移交给自己";
			return;
		}

		if (ServerManager.instance().isOnline(headerId)) {
			header = ServerManager.instance().getOnlinePlayer(headerId);
			if (!online.getTeam().isTeammate(header)) {
				result = 1;
				reason = "不在队伍";
				return;
			}
			online.getTeam().changeLeader(header);
		}
		
		MapManager.instance().addTeamQueue(header);
		MapManager.instance().addTeamQueue(online);
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_transferLeader");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0) {
			TeamMessage.sendTeamAllMsg(online.getTeam());
			TeamMessage.teamInform(header, "您已变更为队长");
		}
	}

}
