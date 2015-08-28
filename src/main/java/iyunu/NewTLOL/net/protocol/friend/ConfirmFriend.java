package iyunu.NewTLOL.net.protocol.friend;

import iyunu.NewTLOL.enumeration.EFriendEvent;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.friend.FriendServer;
import iyunu.NewTLOL.util.CommonConst;
import iyunu.NewTLOL.util.FriendConst;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 好友确认或拒绝
 * @author LSR
 * @date 2012-8-27
 */
public class ConfirmFriend extends TLOLMessageHandler {

	private int state;
	private long friendId;
	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {

		state = msg.readInt("state");
		friendId = msg.readLong("friendId");
		result = 0;
		reason = "加好友成功";

		// 确认
		if (state == CommonConst.INT_ONE) {
			if (!ServerManager.instance().isOnline(friendId)) {
				result = 1;
				reason = "加好友失败";
				return;
			}

			if (online.getFriendList().size() >= FriendConst.FRIEND_MAX) {
				result = 1;
				reason = "您的好友数量已达到上限！";
				return;
			}

			Role role = ServerManager.instance().getOnlinePlayer(friendId);
			FriendServer.addFriend(role, online.getId());
			FriendServer.addFriend(online, friendId);

			List<Long> list = online.getApplyFriendList();
			if (list.contains(friendId)) {
				list.remove(friendId);
			}

			LogManager.friend(online, friendId, EFriendEvent.添加好友);
		}

		// 拒绝
		if (state == CommonConst.INT_TWO) {
			List<Long> list = online.getApplyFriendList();
			if (list.contains(friendId)) {
				list.remove(friendId);
			}

			result = 0;
			reason = "拒绝";
			return;
		}

		// 一键拒绝
		if (state == CommonConst.INT_THREE) {
			List<Long> list = online.getApplyFriendList();
			list.clear();

			result = 0;
			reason = "一键拒绝";
			return;
		}
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_confirmFriend");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		// 刷新申请列表
		List<Long> applyFriendList = online.getApplyFriendList();
		SendMessage.refreshFriendOrBlackList(online, applyFriendList, 1);

		if (result == 0 && state == 1) {
			Role role = ServerManager.instance().getOnlinePlayer(friendId);
			String reason = "加" + online.getNick() + "好友成功";
			SendMessage.friendInform(role, reason);
		}

		if (result == 0 && (state == 2 || state == 3)) {
			Role role = ServerManager.instance().getOnlinePlayer(friendId);
			String reason = online.getNick() + "拒绝加您为好友";
			SendMessage.friendInform(role, reason);
		}
	}

}
