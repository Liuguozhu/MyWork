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
 * @function 筛选周围玩家
 * @author LSR
 * @date 2012-8-27
 */
public class ChooseAroundAllRole extends TLOLMessageHandler {

	private int minLevel;
	private int maxLevel;
	private List<Long> newList = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		minLevel = msg.readInt("minLevel");
		maxLevel = msg.readInt("maxLevel");
		newList = new ArrayList<Long>();
		Set<Long> list = online.getMapInfo().getBaseMap().allInMap();
		for (Long id : list) {
			Role role = ServerManager.instance().getOnlinePlayer(id);
			if (role.getTeam() == null && id != online.getId()) {
				newList.add(id);
			}
		}
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_chooseAroundAllRole");

			if (newList != null) {
				for (Long roleId : newList) {
					LlpMessage aroundAllRoleInfo = message.write("aroundAllRoleInfoList");

					if (ServerManager.instance().isOnline(roleId)) {

						Role role = ServerManager.instance().getOnlinePlayer(roleId);
						if (role.getLevel() >= minLevel && role.getLevel() <= maxLevel) {
							aroundAllRoleInfo.write("roleId", role.getId());
							aroundAllRoleInfo.write("name", role.getNick());
							aroundAllRoleInfo.write("figure", role.getFigure());
							aroundAllRoleInfo.write("vocation", role.getVocation().getName());
							aroundAllRoleInfo.write("level", role.getLevel());
						}
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
