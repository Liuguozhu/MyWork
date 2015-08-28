package iyunu.NewTLOL.server.friend;

import iyunu.NewTLOL.model.role.Role;

import java.util.List;

public final class FriendServer {

	/**
	 * @function 添加好友
	 * @author LuoSR
	 * @param role
	 *            玩家对象
	 * @param id
	 *            玩家Id
	 * @date 2014年1月14日
	 */
	public static void addFriend(Role role, long id) {
		List<Long> list = role.getFriendList();
		if (!list.contains(id)) {
			list.add(id);
		}
	}

	/**
	 * @function 删除好友
	 * @author LuoSR
	 * @param role
	 *            玩家对象
	 * @param id
	 *            玩家Id
	 * @date 2014年1月14日
	 */
	public static void delFriend(Role role, long id) {
		List<Long> list = role.getFriendList();
		if (list.contains(id)) {
			list.remove(id);
		}
	}

}
