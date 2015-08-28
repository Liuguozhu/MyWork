package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.ArrayList;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 请求帮派中人员信息的列表
 * 
 * @author fenghaiyu
 * 
 */
public class QueryGangMember extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "请求帮派信息成功";
	private Gang gang = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "请求帮派人员信息成功";
		gang = null;

		if (online.getGang() == null) {
			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}
		long gangId = online.getGangId();
		// page = msg.readInt("page");
		gang = GangManager.instance().map.get(gangId);
		// 进行排序
		gang.sortMembers();

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_queryGang");
			message.write("reason", reason);
			message.write("result", result);
			if (result == 0) {

				ArrayList<RoleCard> cards = gang.getMembers();

				for (RoleCard roleCard : cards) {

					LlpMessage msg = message.write("gangMembers");
					Role role = ServerManager.instance().getOnlinePlayer(roleCard.getId());
					if (role != null) {
						msg.write("roleId", role.getId());
						msg.write("name", role.getNick());
						msg.write("vocation", role.getVocation().getName());
						msg.write("level", role.getLevel());
						msg.write("jobTitle", role.getGang().getJobTitle(role).ordinal());
						msg.write("totalTribute", role.getTotalTribute());
						msg.write("isOnline", ServerManager.instance().getOnlinePlayer(role.getId()) == null ? 0 : 1);
					} else {
						msg.write("roleId", roleCard.getId());
						msg.write("name", roleCard.getNick());
						msg.write("vocation", roleCard.getVocation().getName());
						msg.write("level", roleCard.getLevel());
						msg.write("jobTitle", roleCard.getGang().getJobTitle(roleCard).ordinal());
						msg.write("totalTribute", roleCard.getTotalTribute());
						msg.write("isOnline", ServerManager.instance().getOnlinePlayer(roleCard.getId()) == null ? 0 : 1);
					}
				}
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
