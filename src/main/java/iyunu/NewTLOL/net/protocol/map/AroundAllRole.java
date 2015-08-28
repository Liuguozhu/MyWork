package iyunu.NewTLOL.net.protocol.map;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 请求周围玩家列表
 * @author LuoSR
 * @date 2014年1月13日
 */
public class AroundAllRole extends TLOLMessageHandler {

	private List<Long> newList = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		Set<Long> list = online.getMapInfo().getBaseMap().allInMap();
		newList = new ArrayList<Long>();
		
		for (Long id : list) {
			Role role = ServerManager.instance().getOnlinePlayer(id);
			if (role != null && role.getTeam() == null && id != online.getId() && !newList.contains(id)) {
				newList.add(id);
			}
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_aroundAllRole");
			if (newList != null) {
				for (Long roleId : newList) {
					LlpMessage aroundAllRoleInfo = message.write("aroundAllRoleInfoList");

					if (ServerManager.instance().isOnline(roleId)) {
						Role role = ServerManager.instance().getOnlinePlayer(roleId);
						aroundAllRoleInfo.write("roleId", role.getId());
						aroundAllRoleInfo.write("name", role.getNick());
						aroundAllRoleInfo.write("figure", role.getFigure());
						aroundAllRoleInfo.write("vocation", role.getVocation().getName());
						aroundAllRoleInfo.write("level", role.getLevel());
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